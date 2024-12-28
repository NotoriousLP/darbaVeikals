package darbaVeikals;

import javax.swing.*;

public class Order_PC {
    public void orderPC() {
      
        JTextArea textArea = new JTextArea(10, 30); 
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

       
        JScrollPane scrollPane = new JScrollPane(textArea);

     
        int result = JOptionPane.showConfirmDialog(
            null, 
            scrollPane, 
            "Ievadiet tekstu", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE
        );

   
        if (result == JOptionPane.OK_OPTION) {
            String inputText = textArea.getText();
            JOptionPane.showMessageDialog(null, "Ievadītais teksts: " + inputText);
        } else {
            JOptionPane.showMessageDialog(null, "Nav ievadīts teksts.");
        }
    }
}