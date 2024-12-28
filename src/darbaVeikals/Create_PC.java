package darbaVeikals;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Create_PC {

    private Create_Com createComponents = Create_Com.getInstance(); 
    private static List<String> createdPCs = new ArrayList<>();
    

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
        String motherboard = chooseComponent("Motherboard", createComponents.getMotherboards());
        if (motherboard == null) return;  

        String cpu = chooseComponent("CPU", createComponents.getCpus());
        if (cpu == null) return;

        String gpu = chooseComponent("GPU", createComponents.getGpus());
        if (gpu == null) return;

        String ram = chooseComponent("RAM", createComponents.getRams());
        if (ram == null) return;

        String storage = chooseComponent("Storage", createComponents.getStorages());
        if (storage == null) return;

        String pcName = JOptionPane.showInputDialog("Ievadiet datoru nosaukumu:");
        if (pcName == null || pcName.isEmpty()) {
            pcName = "Dators " + System.currentTimeMillis(); 
        }

        String pcDetails = "Dators: " + pcName + "\n" +
                           "Motherboard: " + motherboard + "\n" +
                           "CPU: " + cpu + "\n" +
                           "GPU: " + gpu + "\n" +
                           "RAM: " + ram + "\n" +
                           "Storage: " + storage + "\n";

        createdPCs.add(pcDetails); 
        JOptionPane.showMessageDialog(null, "Izveidotais dators: " + pcDetails);
        createPC(); 
    }

  
    void viewPCs() {
        if (createdPCs.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nav izveidoti dators.");
        } else {
            StringBuilder pcList = new StringBuilder("Izveidotie datori:\n");
            for (String pc : createdPCs) {
                pcList.append(pc).append("\n");
            }
            JOptionPane.showMessageDialog(null, pcList.toString());
        }

        Izvelne.main(new String[]{});
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
}
