package darbaVeikals;

import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;


public class Izvelne {

    public static void main(String[] args) {
     
        Order_PC orderPC = new Order_PC();
        Create_PC createPC = new Create_PC();
        Create_Com createComponents = Create_Com.getInstance();

    
        while (true) {
        
            JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

            String[] options = {
                "Pasūtīt datoru", "Izveidot datoru", "Pievienot komponentes", 
                "Apskatīt klientus", "Apskatīt datorus", "Iziet"
            };

       
            JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);

   
            for (String option : options) {
                JButton button = new JButton(option);

          
                button.addActionListener(e -> {
               
                    JOptionPane.getRootFrame().dispose();

                    switch (option) {
                        case "Pasūtīt datoru":
                            orderPC.orderPC(); 
                            break;
                        case "Izveidot datoru":
                            createPC.createPC(); 
                            break;
                        case "Pievienot komponentes":
                            createComponents.createComponent(); 
                            break;
                        case "Apskatīt klientus":
                       
                            break;
                        case "Apskatīt datorus":
                        	createPC.viewPCs(); 
                            break;
                        default:
                    
                            System.exit(0);
                            break;
                    }
                });

                panel.add(button); 
            }

         
            JDialog dialog = optionPane.createDialog(null, "Izvēlieties darbību");
            dialog.setVisible(true);

            break;
        
        }
    }
}
