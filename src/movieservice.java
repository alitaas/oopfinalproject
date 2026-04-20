import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

public class movieservice {

    public static void exportMoviesToCSV() {

        String sql = "SELECT * FROM movies";

        try (
                PrintWriter writer = new PrintWriter("movies.csv");
                Connection conn = database.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {

            writer.println("id,title,duration,genre");

            while (rs.next()) {
                writer.println(
                        rs.getInt("id") + "," +
                                rs.getString("title") + "," +
                                rs.getInt("duration") + "," +
                                rs.getString("genre")
                );
            }

            System.out.println("Movies exported to movies.csv!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static void importMoviesFromCSV() {

        String file = "movies.csv";
        String sql = "INSERT INTO movies(title, duration, genre) VALUES (?, ?, ?)";

        try (
                BufferedReader reader = new BufferedReader(new FileReader(file));
                Connection conn = database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            String line;
            reader.readLine(); // пропускаем header

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                String title = parts[1];
                int duration = Integer.parseInt(parts[2]);
                String genre = parts[3];

                stmt.setString(1, title);
                stmt.setInt(2, duration);
                stmt.setString(3, genre);

                stmt.executeUpdate();
            }

            System.out.println("Movies imported from CSV!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static Scanner scanner = new Scanner(System.in);

    public static boolean isValidTitle(String title) {
        return title != null
                && !title.trim().isEmpty()
                && title.matches("[\\p{L}0-9\\s.,!?:'\"()-]+");
    }

    public static boolean isValidGenre(String genre) {
        return genre != null
                && !genre.trim().isEmpty()
                && genre.matches("[a-zA-Z\\s]+");
    }

    public static boolean movieExists(int movieId) {

        String sql = "SELECT id FROM movies WHERE id = ?";

        try {
            Connection conn = database.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }


    public static void addMovie() {

        String title;
        while (true) {
            System.out.print("Enter movie title: ");
            title = scanner.nextLine();

            if (isValidTitle(title)) break;

            System.out.println("Invalid title!");
            }


        int duration;
        while (true) {
            System.out.print("Enter duration (minutes): ");

            if (scanner.hasNextInt()) {
                duration = scanner.nextInt();
                scanner.nextLine();

                if (ScreeningService.isValidDuration(duration)) break;

                System.out.println("Duration must be between 30 and 300 minutes!");
            } else {
                System.out.println("Enter a valid number!");
                scanner.nextLine();

            }
        }


        String genre;
        while (true) {
            System.out.print("Enter genre: ");
            genre = scanner.nextLine();

            if (isValidGenre(genre)) break;

            System.out.println("Genre cannot be empty!");
        }

        String sql = "INSERT INTO movies(title, duration, genre) VALUES (?, ?, ?)";

        try {
            Connection conn = database.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setInt(2, duration);
            stmt.setString(3, genre);

            stmt.executeUpdate();

            System.out.println("Movie added successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static void getMovies() {

        String sql = "SELECT * FROM movies";

        try {
            Connection conn = database.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n===== MOVIES =====");

            while (rs.next()) {
                movie movie = new movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("duration"),
                        rs.getString("genre")
                );

                movie.displayInfo();
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static void deleteMovie() {

        int id;

        while (true) {
            System.out.print("Enter movie id to delete: ");

            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();

                break;
            } else {
                System.out.println("Please enter a valid number!");
                scanner.nextLine();
            }
        }

        String sql = "DELETE FROM movies WHERE id = ?";

        try {
            Connection conn = database.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Movie deleted!");
            } else {
                System.out.println("Movie not found!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static void updateMovie() {

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

        String title;
        while (true) {
            System.out.print("Enter new title: ");
            title = scanner.nextLine();

            if (!title.isEmpty()) break;

            System.out.println("Title cannot be empty!");
        }

        int duration;
        while (true) {
            System.out.print("Enter new duration: ");

            if (scanner.hasNextInt()) {
                duration = scanner.nextInt();
                scanner.nextLine();

                if (duration > 0) break;
                System.out.println("Duration must be greater than 0!");
            } else {
                System.out.println("Please enter a valid number!");
                scanner.nextLine();
            }
        }


        String genre;
        while (true) {
            System.out.print("Enter new genre: ");
            genre = scanner.nextLine();

            if (!genre.isEmpty()) break;

            System.out.println("Genre cannot be empty!");
        }

        String sql = "UPDATE movies SET title = ?, duration = ?, genre = ? WHERE id = ?";

        try {
            Connection conn = database.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setInt(2, duration);
            stmt.setString(3, genre);
            stmt.setInt(4, movieId);

            stmt.executeUpdate();

            System.out.println("Movie updated successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
