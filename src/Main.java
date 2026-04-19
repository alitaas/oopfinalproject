import java.util.Scanner;
public class Main{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        person user;

        if (username.equalsIgnoreCase("admin")) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (password.equals("movie")){
                user = new admin(username);
            } else {
                System.out.println("Wrong password! Logged as normal user.");
                user = new person(username);
            }
        } else {
            user = new person(username);
        }

        user.showRole();

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. View Movies");
            System.out.println("2. View Screenings");
            System.out.println("3. View Schedule");

            if (user.isAdmin()) {
                System.out.println("\n===== MOVIE =====");
                System.out.println("4. Add Movie");
                System.out.println("5. Update Movie");
                System.out.println("6. Delete Movie");
                System.out.println("7. Export Movies");
                System.out.println("8. Import Movies");
                System.out.println("\n===== SCREENING =====");
                System.out.println("9. Add Screening");
                System.out.println("10. Delete Screening");
                System.out.println("11. Update screening");
                System.out.println("12. Export Screenings");
                System.out.println("13. Import Screenings");

            }

            System.out.println("0. Exit");

            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            //ADMIN ONLY ACTIONS
            if (user.isAdmin()) {

                switch (choice) {

                    case 1 -> movieservice.getMovies();
                    case 2 -> ScreeningService.getScreenings();
                    case 3 -> ScreeningService.getScheduleByHall();


                    case 4 -> movieservice.addMovie();
                    case 5 -> movieservice.updateMovie();
                    case 6 -> movieservice.deleteMovie();
                    case 7 -> movieservice.exportMoviesToCSV();
                    case 8 -> movieservice.importMoviesFromCSV();

                    case 9 -> ScreeningService.addScreening();
                    case 10 -> ScreeningService.deleteScreening();
                    case 11 -> ScreeningService.updateScreeningManually();
                    case 12 -> ScreeningService.exportScreeningsToCSV();
                    case 13 -> ScreeningService.importScreeningsFromCSV();




                    case 0 -> {
                        System.out.println("Goodbye!");
                        return;
                    }

                    default -> System.out.println("Invalid option!");
                }

            } else {

                // NORMAL USER (VIEW ONLY)
                switch (choice) {

                    case 1 -> movieservice.getMovies();
                    case 2 -> ScreeningService.getScreenings();
                    case 3 -> ScreeningService.getScheduleByHall();

                    case 0 -> {
                        System.out.println("Goodbye!");
                        return;
                    }

                    default -> System.out.println("You don't have permission for this action!");
                }
            }
        }
    }

}
