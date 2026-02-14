import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionOfData {
    private static final String url = "jdbc:postgresql://localhost:5432/endterm_oop";
    private static final String user = "postgres";
    private static final String password = "13062013";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}