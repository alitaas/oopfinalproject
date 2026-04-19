import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ScreeningService {

    private static Connection conn = database.connect();
    private static Scanner scanner = new Scanner(System.in);


    public static int getMovieDuration(int movieId) {

        String sql = "SELECT duration FROM movies WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("duration");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return -1;
    }


    public static boolean hasTimeConflict(int movieId, String time, String hall) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime newStart = LocalDateTime.parse(time, formatter);

        int duration = getMovieDuration(movieId);
        LocalDateTime newEnd = newStart.plusMinutes(duration);

        String sql =
                "SELECT screenings.id, screenings.screening_time, movies.duration " +
                        "FROM screenings " +
                        "JOIN movies ON screenings.movie_id = movies.id " +
                        "WHERE hall = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, hall.toUpperCase());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                LocalDateTime existingStart =
                        LocalDateTime.parse(rs.getString("screening_time"), formatter);

                LocalDateTime existingEnd =
                        existingStart.plusMinutes(rs.getInt("duration"));

                if (newStart.isBefore(existingEnd) && existingStart.isBefore(newEnd)) {
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return false;
    }

    public static int getConflictingScreeningId(String time, String hall) {

        String sql = "SELECT id FROM screenings WHERE screening_time = ? AND hall = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, time);
            stmt.setString(2, hall.toUpperCase());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return -1;
    }


    public static boolean isValidDuration(int duration) {
        return duration >= 30 && duration <= 300;
    }


    public static boolean isValidTime(String time) {
        return time.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}");
    }

    public static boolean isValidHall(String hall) {
        if (hall == null) return false;

        hall = hall.trim().toLowerCase();

        return hall.equals("1") || hall.equals("2") || hall.equals("3") || hall.equals("imax");
    }

    public static boolean movieExists(int movieId) {

        String sql = "SELECT id FROM movies WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }


    // ADD SCREENING (WITH LOOPS + VALIDATION)
    public static void addScreening() {

        //MOVIE ID LOOP
        int movieId;

        while (true) {
            System.out.print("Enter movie id: ");

            if (scanner.hasNextInt()) {
                movieId = scanner.nextInt();
                scanner.nextLine();

                if (movieId <= 0) {
                    System.out.println("Movie ID must be greater than 0!");
                    continue;
                }

                if (!movieExists(movieId)) {
                    System.out.println("Movie does not exist! Try again.");
                    continue;
                }

                break;

            } else {
                System.out.println("Please enter a valid number!");
                scanner.nextLine();
            }
        }

        //SCREENING TIME LOOP
        String time;
        while (true) {
            System.out.print("Enter screening time (YYYY-MM-DD HH:MM): ");
            time = scanner.nextLine();

            if (isValidTime(time)) break;

            System.out.println("Invalid format! Example: 2026-04-16 18:30");
        }

        //HALL LOOP
        String hall;
        while (true) {
            System.out.print("Enter hall (Available halls: 1, 2, 3, IMAX): ");
            hall = scanner.nextLine();

            if (isValidHall(hall)) break;

            System.out.println("Invalid hall! Choose: 1, 2, 3, IMAX");
        }

        //conflict checking
        if (hasTimeConflict(movieId, time, hall)) {

            System.out.println("Time conflict! Another movie is playing in this hall at that time.");

            System.out.print("Do you want to try another time? (yes/no): ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("yes")) {
                return;
            } else {
                System.out.println("Operation cancelled.");
                return;
            }
        }


        String sql = "INSERT INTO screenings(movie_id, screening_time, hall) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);
            stmt.setString(2, time);
            stmt.setString(3, hall.toUpperCase());

            stmt.executeUpdate();

            System.out.println("Screening added successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //VIEW SCREENINGS
    public static void getScreenings() {

        String sql =
                "SELECT screenings.id, screenings.movie_id, movies.title, screenings.screening_time, screenings.hall " +
                        "FROM screenings " +
                        "JOIN movies ON screenings.movie_id = movies.id";


        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n===== SCREENINGS =====");

            while (rs.next()) {
                screening screening = new screening(
                        rs.getInt("id"),
                        rs.getInt("movie_id"),
                        rs.getString("screening_time"),
                        rs.getString("hall")
                );

                System.out.println(
                        screening.getId() + ". " +
                                rs.getString("title") + " | " +   // title from JOIN
                                screening.getScreeningTime() + " | " +
                                screening.getHall()
                );
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //DELETE SCREENING (SAFE INPUT)
    public static void deleteScreening() {

        int id;

        while (true) {
            System.out.print("Enter screening id to delete: ");

            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("Please enter a valid number!");
                scanner.nextLine();
            }
        }

        String sql = "DELETE FROM screenings WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Screening deleted!");
            } else {
                System.out.println("Screening not found!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void updateScreening(int id, int movieId, String time, String hall) {

        String sql = "UPDATE screenings SET movie_id = ?, screening_time = ?, hall = ? WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);
            stmt.setString(2, time);
            stmt.setString(3, hall.toUpperCase());
            stmt.setInt(4, id);

            stmt.executeUpdate();

            System.out.println("Screening updated!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void updateScreeningManually() {

        int id;

        while (true) {
            System.out.print("Enter screening id to update: ");

            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("Enter a valid number!");
                scanner.nextLine();
            }
        }

        System.out.print("Enter new movie id: ");
        int movieId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new time (YYYY-MM-DD HH:MM): ");
        String time = scanner.nextLine();

        System.out.print("Enter new hall (1,2,3,IMAX): ");
        String hall = scanner.nextLine();

        updateScreening(id, movieId, time, hall); // 🔥 используем твой метод
    }

    public static void getScheduleByHall() {

        String[] halls = {"1", "2", "3", "IMAX"};

        String sql =
                "SELECT screenings.screening_time, movies.title " +
                        "FROM screenings " +
                        "JOIN movies ON screenings.movie_id = movies.id " +
                        "WHERE hall = ? " +
                        "ORDER BY screening_time";

        try {

            for (String hall : halls) {

                System.out.println("\n===== HALL " + hall + " =====");

                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, hall);

                ResultSet rs = stmt.executeQuery();

                boolean empty = true;

                while (rs.next()) {
                    empty = false;

                    String time = rs.getString("screening_time");
                    String title = rs.getString("title");

                    // красиво обрезаем дату → оставляем только время
                    String onlyTime = time.substring(11); // HH:MM

                    System.out.println(onlyTime + " | " + title);
                }

                if (empty) {
                    System.out.println("No screenings");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void exportScreeningsToCSV() {

        String sql =
                "SELECT screenings.id, screenings.movie_id, movies.title, " +
                        "screenings.screening_time, screenings.hall " +
                        "FROM screenings " +
                        "JOIN movies ON screenings.movie_id = movies.id";

        try (
                PrintWriter writer = new PrintWriter("screenings.csv");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {

            writer.println("id,movie_id,title,screening_time,hall");

            while (rs.next()) {
                writer.println(
                        rs.getInt("id") + "," +
                                rs.getInt("movie_id") + "," +
                                rs.getString("title") + "," +
                                rs.getString("screening_time") + "," +
                                rs.getString("hall")
                );
            }

            System.out.println("Screenings exported to screenings.csv!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static void importScreeningsFromCSV() {

        String file = "screenings.csv";

        String sql =
                "INSERT INTO screenings(movie_id, screening_time, hall) VALUES (?, ?, ?)";

        try (
                BufferedReader reader = new BufferedReader(new FileReader(file));
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            String line;
            reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                int movieId = Integer.parseInt(parts[1]);
                String time = parts[3];
                String hall = parts[4];

                if (!movieservice.movieExists(movieId)) {
                    System.out.println("Skipping invalid movie_id: " + movieId);
                    continue;
                }

                if (hasTimeConflict(movieId, time, hall)) {
                    System.out.println("Skipping conflict screening: " + time + " | " + hall);
                    continue;
                }

                stmt.setInt(1, movieId);
                stmt.setString(2, time);
                stmt.setString(3, hall);

                stmt.executeUpdate();
            }

            System.out.println("Screenings imported from CSV!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }




}
