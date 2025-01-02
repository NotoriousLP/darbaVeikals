package darbaVeikals;

import javax.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Create_Com {

 
    private List<String> motherboards = new ArrayList<>();
    private List<String> cpus = new ArrayList<>();
    private List<String> gpus = new ArrayList<>();
    private List<String> rams = new ArrayList<>();
    private List<String> storages = new ArrayList<>();

 
    private static Create_Com instance;

    
    public static Create_Com getInstance() {
        if (instance == null) {
            instance = new Create_Com();
        }
        return instance;
    }

    public void createComponent() {
        String[] partTypes = {"Pievienot Motherboard", "Pievienot CPU", "Pievienot GPU", "Pievienot RAM", "Pievienot Storage", "Atpakaļ"};

        int choice = JOptionPane.showOptionDialog(
                null,
                "Izvēlieties, kuru komponenti pievienot:",
                "Pievienot komponenti",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                partTypes,
                partTypes[0]
        );

        switch (choice) {
            case 0:
                handleComponent("Motherboard", motherboards);
                break;
            case 1:
                handleComponent("CPU", cpus);
                break;
            case 2:
                handleComponent("GPU", gpus);
                break;
            case 3:
                handleComponent("RAM", rams);
                break;
            case 4:
                handleComponent("Storage", storages);
                break;
            case 5:
                Izvelne.main(new String[]{});
                break;
            default:
                JOptionPane.showMessageDialog(null, "Nav izvēlēts nekas.");
        }
    }

    
    private void handleComponent(String componentType, List<String> componentList) {
        String[] options = {"Pievienot " + componentType, "Apskatīt esošās " + componentType + "s", "Atpakaļ"};

        int choice = JOptionPane.showOptionDialog(
                null,
                "Izvēlieties darbību ar " + componentType + ":",
                "Darbība ar " + componentType,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (choice) {
            case 0:
                String newComponent = JOptionPane.showInputDialog("Ievadiet " + componentType + ":");
                if (newComponent != null && !newComponent.isEmpty()) {
                    //componentList.add(newComponent); 
                    saveComponentToDB(componentType, newComponent);
                    JOptionPane.showMessageDialog(null, componentType + " pievienots: " + newComponent);
                } else {
                    JOptionPane.showMessageDialog(null, "Nekas netika pievienots.");
                }
                handleComponent(componentType, componentList); 
                break;
            case 1:
                if (componentType.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nav pievienots nevienas " + componentType + "s.");
                } else {
                	getComponentsFromDBList(componentType);
                }
                handleComponent(componentType, componentList); 
                break;
            case 2:
                createComponent();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Nav izvēlēts nekas.");
                break;
        }
    }
    
    public void saveComponentToDB(String type, String name) {
        String insert = "INSERT INTO Komponente (type, name) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Savienojums ir aizvērts vai nav pieejams.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(insert)) {
                pstmt.setString(1, type);
                pstmt.setString(2, name);
                pstmt.executeUpdate();
                System.out.println("Komponente pievienota: " + name);
            }
        } catch (SQLException e) {
            System.err.println("Kļūda, pievienojot komponenti: " + e.getMessage());
        }
    }

    public List<String> getComponentsFromDB(String type) {
        List<String> components = new ArrayList<>();
        String query = "SELECT name FROM Komponente WHERE type = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, type);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                components.add(rs.getString("name"));
            }
        } catch (SQLException e) {
        	System.out.println("Kļūda, izgūstot komponentes: " + e.getMessage());
        }
        return components;
    }
    
    public List<String> getComponentsFromDBList(String type) {
        List<String> components = new ArrayList<>();
        String query = "SELECT name FROM Komponente WHERE type = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, type);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                components.add(rs.getString("name"));
            }
            if (components.isEmpty()) {
            	JOptionPane.showMessageDialog(null, "Nav pieejamu " + type + " komponentu datubāzē.");
            } else {
            	JOptionPane.showMessageDialog(null, "Atrastas " + type + " komponentes:\n" + String.join("\n", components));
            }
        } catch (SQLException e) {
        	System.out.println("Kļūda, izgūstot komponentes: " + e.getMessage());
        }
        return components;
    }
    
    public void deleteComponent() {
        String[] componentTypes = {"Motherboard", "CPU", "GPU", "RAM", "Storage"};

        // Izvēlas komponentes tipu
        String selectedType = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlieties komponentes tipu dzēšanai:",
                "Komponentes dzēšana",
                JOptionPane.QUESTION_MESSAGE,
                null,
                componentTypes,
                componentTypes[0]
        );

        if (selectedType == null)  Izvelne.main(new String[]{}); // Lietotājs atcēla izvēli

        List<String> components = getComponentsFromDBList(selectedType);
        if (components.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nav pieejamu " + selectedType + " komponentu dzēšanai!");
            return;
        }

        // Ļauj lietotājam izvēlēties komponenti dzēšanai
        String selectedComponent = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlieties komponenti dzēšanai:",
                "Komponentes dzēšana",
                JOptionPane.QUESTION_MESSAGE,
                null,
                components.toArray(),
                components.get(0)
        );

        if (selectedComponent == null) Izvelne.main(new String[]{}); // Lietotājs atcēla izvēli

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Vai tiešām vēlaties dzēst komponenti: " + selectedComponent + "?",
                "Apstiprinājums",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (deleteComponentFromDB(selectedType, selectedComponent)) {
                JOptionPane.showMessageDialog(null, "Komponente veiksmīgi dzēsta!");
                Izvelne.main(new String[]{});
            } else {
                JOptionPane.showMessageDialog(null, "Kļūda, dzēšot komponenti!");
                Izvelne.main(new String[]{});
            }
        }else {
        	Izvelne.main(new String[]{});
        }
    }

    private boolean deleteComponentFromDB(String type, String componentName) {
        String deleteComponentQuery = "DELETE FROM Komponente WHERE type = ? AND name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteComponentQuery)) {
            pstmt.setString(1, type);
            pstmt.setString(2, componentName);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Kļūda, dzēšot komponenti: " + e.getMessage());
        }
        return false;
    }

    

}
