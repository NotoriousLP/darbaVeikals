package darbaVeikals;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Klienti {
    private String vards;
    private String uzvards;
    private String telefons;
    private String adrese;
    private String pasutitaisDators;

    public Klienti(String vards, String uzvards, String telefons, String adrese, String pasutitaisDators) {
        this.vards = vards;
        this.uzvards = uzvards;
        this.telefons = telefons;
        this.adrese = adrese;
        this.pasutitaisDators = pasutitaisDators;
    }

    public String toString() {
        return "Klients: " + vards + " " + uzvards + "\nTelefons: " + telefons +
                "\nAdrese: " + adrese + "\n" + pasutitaisDators;
    }
    
    public static void saveClientToDB(Klienti klients, int computerId) {
        String insert = "INSERT INTO Klienti (name, surname, phone, address, computer_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insert)) {
            pstmt.setString(1, klients.vards);
            pstmt.setString(2, klients.uzvards);
            pstmt.setString(3, klients.telefons);
            pstmt.setString(4, klients.adrese);
            pstmt.setInt(5, computerId);
            pstmt.executeUpdate();
            System.out.println("Klients pievienots: " + klients);
        } catch (SQLException e) {
            System.err.println("Kļūda, pievienojot klientu: " + e.getMessage());
        }
    }

    public static List<Klienti> getClientsFromDB() {
        List<Klienti> klienti = new ArrayList<>();
        String query = "SELECT k.name, k.surname, k.phone, k.address, d.name as computer_name " +
                       "FROM Klienti k JOIN Dators d ON k.computer_id = d.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Klienti klients = new Klienti(
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("computer_name")
                );
                klienti.add(klients);
            }
        } catch (SQLException e) {
            System.err.println("Kļūda, izgūstot klientus: " + e.getMessage());
        }
        return klienti;
    }

}
