package darbaVeikals;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Create_PC {

    private Create_Com createComponents = Create_Com.getInstance(); 

    

    public void createPC() {

        String[] mainMenuOptions = {"Izveidot datoru", "Atpakaļ"};

        int choice = JOptionPane.showOptionDialog(
                null,
                "Izvēlieties darbību:",
                "Izvēlieties darbību",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                mainMenuOptions,
                mainMenuOptions[0]
        );

        switch (choice) {
            case 0:
                buildPC();
                break;
            case 1:
                Izvelne.main(new String[]{});
                break;
            default:
                JOptionPane.showMessageDialog(null, "Nav izvēlēts nekas.");
                break;
        }
    }

  
    private void buildPC() {
        String motherboard = chooseComponent("Motherboard", createComponents.getComponentsFromDB("Motherboard"));
        if (motherboard == null) return;  

        String cpu = chooseComponent("CPU", createComponents.getComponentsFromDB("CPU"));
        if (cpu == null) return;

        String gpu = chooseComponent("GPU", createComponents.getComponentsFromDB("GPU"));
        if (gpu == null) return;

        String ram = chooseComponent("RAM", createComponents.getComponentsFromDB("RAM"));
        if (ram == null) return;

        String storage = chooseComponent("Storage", createComponents.getComponentsFromDB("Storage"));
        if (storage == null) return;

        String pcName = JOptionPane.showInputDialog("Ievadiet datoru nosaukumu:");
        if (pcName == null || pcName.isEmpty()) {
            pcName = "Dators " + System.currentTimeMillis(); 
        }

        String pcDetails = "Dators: " + pcName + " \n" +
                           "Motherboard: " + motherboard + " \n" +
                           "CPU: " + cpu + " \n" +
                           "GPU: " + gpu + " \n" +
                           "RAM: " + ram + " \n" +
                           "Storage: " + storage + " \n";

        //createdPCs.add(pcDetails); 
        savePCToDB(pcDetails);
        JOptionPane.showMessageDialog(null, "Izveidotais dators: " + pcDetails);
     
        createPC(); 
    }

  
    void viewPCs() {
        	getPCsFromDB();
    }

   
    private String chooseComponent(String componentType, List<String> components) {
        String[] componentArray = components.toArray(new String[0]);

        String selected = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlieties " + componentType + ":",
                componentType,
                JOptionPane.QUESTION_MESSAGE,
                null,
                componentArray,
                componentArray.length > 0 ? componentArray[0] : null
        );

        if (selected == null) {
         
        	createPC();  
           
            return null;  
        }

        return selected; 
    }
   
    public int savePCToDB(String name) {
        String insert = "INSERT INTO Dators (name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Atgriež izveidotā datora ID
            }
        } catch (SQLException e) {
            System.err.println("Kļūda, saglabājot datoru: " + e.getMessage());
        }
        return -1;
    }
    
    public static List<String> getPCsFromDB() {
        List<String> pcs = new ArrayList<>();
        String query = "SELECT name FROM Dators";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                pcs.add(rs.getString("name"));
            }

            if (pcs.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nav pieejamu datoru datubāzē.");
                Izvelne.main(new String[]{});
            } else {
                JOptionPane.showMessageDialog(null, "Atrasti datori:\n" + String.join("\n", pcs));
                Izvelne.main(new String[]{});
            }
        } catch (SQLException e) {
            System.err.println("Kļūda, izgūstot datorus: " + e.getMessage());
        }

        return pcs;
    }

    public static List<String> getCreatedPCsFromDB() {
        List<String> pcs = new ArrayList<>();
        String query = "SELECT name FROM Dators";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                pcs.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Kļūda, izgūstot datorus: " + e.getMessage());
        }
        return pcs;
    }

    public static int getComputerIdByName(String name) {
        String query = "SELECT id FROM Dators WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Kļūda, izgūstot datora ID: " + e.getMessage());
        }
        return -1;
    }
    
    public void deletePC() {
        List<String> pcs = getCreatedPCsFromDB(); // Iegūst datorus no datubāzes
        if (pcs.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nav pieejamu datoru dzēšanai!");
            Izvelne.main(new String[]{});
        }

        // Ļauj lietotājam izvēlēties datoru dzēšanai
        String selectedPC = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlieties datoru dzēšanai:",
                "Datoru dzēšana",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pcs.toArray(),
                pcs.get(0)
        );

        if (selectedPC == null) Izvelne.main(new String[]{}); // Lietotājs atcēla izvēli

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Vai tiešām vēlaties dzēst datoru: " + selectedPC + "?",
                "Apstiprinājums",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (deletePCFromDB(selectedPC)) {
                JOptionPane.showMessageDialog(null, "Dators veiksmīgi dzēsts!");
                Izvelne.main(new String[]{});
            } else {
                JOptionPane.showMessageDialog(null, "Kļūda, dzēšot datoru!");
                Izvelne.main(new String[]{});
            }
        }else {
        	Izvelne.main(new String[]{});
        }
    }

    private boolean deletePCFromDB(String pcName) {
        String deletePCQuery = "DELETE FROM Dators WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deletePCQuery)) {
            pstmt.setString(1, pcName);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Kļūda, dzēšot datoru: " + e.getMessage());
        }
        return false;
    }

}
