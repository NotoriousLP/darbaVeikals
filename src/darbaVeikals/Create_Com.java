package darbaVeikals;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Create_Com {

 
    private List<String> motherboards = new ArrayList<>();
    private List<String> cpus = new ArrayList<>();
    private List<String> gpus = new ArrayList<>();
    private List<String> rams = new ArrayList<>();
    private List<String> storages = new ArrayList<>();

 
    private static Create_Com instance;


    private Create_Com() {
      
        motherboards.add("Asus Z490");
        motherboards.add("Gigabyte B450");

        cpus.add("Intel i5-11600K");
        cpus.add("AMD Ryzen 5 5600X");

        gpus.add("NVIDIA RTX 3080");
        gpus.add("AMD Radeon RX 6800");

        rams.add("Corsair Vengeance 16GB");
        rams.add("G.Skill Ripjaws 16GB");

        storages.add("Samsung 970 Evo 1TB");
        storages.add("Crucial P3 500GB");
    }

    
    public static Create_Com getInstance() {
        if (instance == null) {
            instance = new Create_Com();
        }
        return instance;
    }

    public List<String> getMotherboards() {
        return motherboards;
    }

    public List<String> getCpus() {
        return cpus;
    }

    public List<String> getGpus() {
        return gpus;
    }

    public List<String> getRams() {
        return rams;
    }

    public List<String> getStorages() {
        return storages;
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
                    componentList.add(newComponent); 
                    JOptionPane.showMessageDialog(null, componentType + " pievienots: " + newComponent);
                } else {
                    JOptionPane.showMessageDialog(null, "Nekas netika pievienots.");
                }
                handleComponent(componentType, componentList); 
                break;
            case 1:
                if (componentList.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nav pievienots nevienas " + componentType + "s.");
                } else {
                    StringBuilder listStr = new StringBuilder("Esošie " + componentType + "s:\n");
                    for (String comp : componentList) {
                        listStr.append(comp).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, listStr.toString());
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
}
