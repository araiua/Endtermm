import java.sql.*;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initDatabase();

        while (true) {
            System.out.println("\n--- MOVIE MANAGEMENT MENU ---");
            System.out.println("1. Add actor");
            System.out.println("2. List all actors");
            System.out.println("3. Add movie");
            System.out.println("4. List all movies");
            System.out.println("5. Add actor to movie");
            System.out.println("6. Who played in the movie?");
            System.out.println("7. In which movies did the actor play?");
            System.out.println("8. Delete actor");
            System.out.println("9. Find actor by ID");
            System.out.println("10. Find movie by title");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 0) break;

            try {
                switch (choice) {
                    case 1: addActor(); break;
                    case 2: listActors(); break;
                    case 3: addMovie(); break;
                    case 4: listMovies(); break;
                    case 5: addActorToMovie(); break;
                    case 6: actorsInMovie(); break;
                    case 7: moviesOfActor(); break;
                    case 8: deleteActor(); break;
                    case 9: findActorById(); break;
                    case 10: findMovieByTitle(); break;
                    default: System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Oops, error: " + e.getMessage());
            }
        }
    }
    }