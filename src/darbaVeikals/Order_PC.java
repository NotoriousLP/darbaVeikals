package darbaVeikals;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Order_PC {

    private static List<Klienti> klientuPasutijumi = new ArrayList<>();

    public void orderPC() {
        if (Create_PC.getCreatedPCs().isEmpty()) { // Pārbauda, vai datoru saraksts ir tukšs
            JOptionPane.showMessageDialog(null, "Nav pieejamu datoru pasūtīšanai!");
            Izvelne.main(new String[]{});
            return;
        }

        // Ievada klienta informāciju
        String vards = JOptionPane.showInputDialog("Ievadiet savu vārdu:");
        if (vards == null || vards.isEmpty()) return;

        String uzvards = JOptionPane.showInputDialog("Ievadiet savu uzvārdu:");
        if (uzvards == null || uzvards.isEmpty()) return;

        String telefons = JOptionPane.showInputDialog("Ievadiet savu telefona numuru:");
        if (telefons == null || telefons.isEmpty()) return;

        String adrese = JOptionPane.showInputDialog("Ievadiet savu mājas adresi:");
        if (adrese == null || adrese.isEmpty()) return;

        // Izvēlas datoru no izveidotajiem datoriem
        String selectedPC = choosePC();
        if (selectedPC == null) return;

        // Izveido jaunu klienta ierakstu
        Klienti klients = new Klienti(vards, uzvards, telefons, adrese, selectedPC);
        klientuPasutijumi.add(klients);

        JOptionPane.showMessageDialog(null, "Paldies! Jūsu pasūtījums ir pieņemts:\n" + klients);
        Izvelne.main(new String[]{});
    }

    public void viewClients() {
        if (klientuPasutijumi.isEmpty()) { // Pārbauda, vai ir reģistrēti pasūtījumi
            JOptionPane.showMessageDialog(null, "Nav reģistrētu klientu!");
        } else {
            // Izvada visus klientus
            StringBuilder klientuSaraksts = new StringBuilder("Klientu pasūtījumi:\n");
            for (Klienti klients : klientuPasutijumi) {
                klientuSaraksts.append(klients).append("\n\n");
            }
            JOptionPane.showMessageDialog(null, klientuSaraksts.toString());
        }
        Izvelne.main(new String[]{});
    }

    private String choosePC() {
        List<String> createdPCs = Create_PC.getCreatedPCs(); // Iegūst datoru sarakstu
        String[] pcArray = createdPCs.toArray(new String[0]);

        // Izvēlas datoru no saraksta
        String selectedPC = (String) JOptionPane.showInputDialog(
                null,
                "Izvēlieties datoru pasūtīšanai:",
                "Datoru izvēle",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pcArray,
                pcArray.length > 0 ? pcArray[0] : null
        );

        return selectedPC;
    }
}
