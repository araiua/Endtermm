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

    public static void initDatabase() {
        try (Connection conn = ConnectionOfData.getConnection(); Statement st = conn.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS Actors (aid SERIAL PRIMARY KEY, firstname VARCHAR(50), lastname VARCHAR(50))");
            st.execute("CREATE TABLE IF NOT EXISTS Movies (mid SERIAL PRIMARY KEY, title VARCHAR(100), release_year INT)");
            st.execute("CREATE TABLE IF NOT EXISTS ActorMovie (aid INT REFERENCES Actors(aid), mid INT REFERENCES Movies(mid), PRIMARY KEY(aid, mid))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addActor() throws SQLException {
        System.out.print("First name: ");
        String fn = sc.nextLine();
        System.out.print("Last name: ");
        String ln = sc.nextLine();

        String sql = "INSERT INTO Actors (firstname, lastname) VALUES (?, ?)";
        try (Connection conn = ConnectionOfData.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fn);
            ps.setString(2, ln);
            ps.executeUpdate();
            System.out.println("Actor added successfully!");
        }
    }

    private static void listActors() throws SQLException {
        try (Connection conn = ConnectionOfData.getConnection(); Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM Actors");
            while (rs.next()) {
                System.out.println(rs.getInt("aid") + ": " +
                        rs.getString("firstname") + " " +
                        rs.getString("lastname"));
            }
        }
    }

    private static void addActorToMovie() throws SQLException {
        System.out.print("Actor ID: ");
        int aid = Integer.parseInt(sc.nextLine());
        System.out.print("Movie ID: ");
        int mid = Integer.parseInt(sc.nextLine());

        String sql = "INSERT INTO ActorMovie (aid, mid) VALUES (?, ?)";
        try (Connection conn = ConnectionOfData.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, aid);
            ps.setInt(2, mid);
            ps.executeUpdate();
            System.out.println("Connection created!");
        }
    }

    private static void actorsInMovie() throws SQLException {
        System.out.print("Enter movie ID: ");
        int mid = Integer.parseInt(sc.nextLine());

        String sql = "SELECT a.firstname, a.lastname FROM Actors a " +
                "JOIN ActorMovie am ON a.aid = am.aid WHERE am.mid = ?";

        try (Connection conn = ConnectionOfData.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mid);
            ResultSet rs = ps.executeQuery();

            System.out.println("Actors in this movie:");
            while (rs.next()) {
                System.out.println("- " + rs.getString("firstname") + " " +
                        rs.getString("lastname"));
            }
        }
    }

    private static void findActorById() throws SQLException {
        System.out.print("Enter actor ID: ");
        int id = Integer.parseInt(sc.nextLine());

        try (Connection conn = ConnectionOfData.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Actors WHERE aid = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Found: " +
                        rs.getString("firstname") + " " +
                        rs.getString("lastname"));
            } else {
                System.out.println("Actor not found.");
            }
        }
    }

    private static void deleteActor() throws SQLException {
        System.out.print("Enter actor ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        try (Connection conn = ConnectionOfData.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Actors WHERE aid = ?")) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Actor deleted (if existed).");
        }
    }

    private static void addMovie() throws SQLException {
        System.out.print("Movie title: ");
        String t = sc.nextLine();
        System.out.print("Release year: ");
        int y = Integer.parseInt(sc.nextLine());

        try (Connection conn = ConnectionOfData.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO Movies (title, release_year) VALUES (?, ?)")) {

            ps.setString(1, t);
            ps.setInt(2, y);
            ps.executeUpdate();
            System.out.println("Movie added successfully!");
        }
    }

    private static void listMovies() throws SQLException {
        try (Connection conn = ConnectionOfData.getConnection(); Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM Movies");
            while (rs.next()) {
                System.out.println(rs.getInt("mid") + ": " +
                        rs.getString("title") +
                        " (" + rs.getInt("release_year") + ")");
            }
        }
    }

    private static void moviesOfActor() throws SQLException {
        System.out.print("Enter actor ID: ");
        int aid = Integer.parseInt(sc.nextLine());

        String sql = "SELECT m.title FROM Movies m " +
                "JOIN ActorMovie am ON m.mid = am.mid WHERE am.aid = ?";

        try (Connection conn = ConnectionOfData.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, aid);
            ResultSet rs = ps.executeQuery();

            int count = 0;
            while (rs.next()) {
                System.out.println("- " + rs.getString("title"));
                count++;
            }

            System.out.println("Total movies: " + count);
        }
    }

    private static void findMovieByTitle() throws SQLException {
        System.out.print("Enter part of the title: ");
        String part = sc.nextLine();

        try (Connection conn = ConnectionOfData.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM Movies WHERE title LIKE ?")) {

            ps.setString(1, "%" + part + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Found: " + rs.getString("title"));
            }
        }
    }
}
