package darbaVeikals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:sqlite:datoraVeikals.db";
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) { // Pārbauda, vai savienojums ir slēgts
                connection = DriverManager.getConnection(DB_URL); // Mēģina atvērt jaunu savienojumu
                //System.out.println("Atvērts jauns savienojums ar datubāzi.");
            }
        } catch (SQLException e) {
            System.err.println("Kļūda, atverot savienojumu: " + e.getMessage());
        }
        return connection; // Atgriež savienojuma objektu
    }


    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Izveido tabulas, ja tās vēl neeksistē
            stmt.execute("CREATE TABLE IF NOT EXISTS Komponente (id INTEGER PRIMARY KEY AUTOINCREMENT, type TEXT, name TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS Dators (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS Klienti (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, surname TEXT, phone TEXT, address TEXT, computer_id INTEGER, FOREIGN KEY(computer_id) REFERENCES Computers(id))");

           // System.out.println("Tabulas veiksmīgi izveidotas vai jau pastāv.");
        } catch (SQLException e) {
            System.err.println("Kļūda, izveidojot tabulas: " + e.getMessage());
        }
    }
}
