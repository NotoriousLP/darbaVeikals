package darbaVeikals;

import javax.swing.*;
import java.util.List;

public class Order_PC {

    public void orderPC() {
        List<String> createdPCs = Create_PC.getCreatedPCsFromDB(); // Iegūst datoru sarakstu no datubāzes
        if (createdPCs.isEmpty()) { // Pārbauda, vai datoru saraksts ir tukšs
            JOptionPane.showMessageDialog(null, "Nav pieejamu datoru pasūtīšanai!");
            Izvelne.main(new String[]{});
        }

        // Ievada klienta informāciju
        String vards = JOptionPane.showInputDialog("Ievadiet savu vārdu:");
        if (vards == null || vards.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vārds nevar būt tukšs!");
            Izvelne.main(new String[]{});  // Ja "Cancel" vai tukšs, tiek pārtraukta funkcija
        }

        String uzvards = JOptionPane.showInputDialog("Ievadiet savu uzvārdu:");
        if (uzvards == null || uzvards.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Uzvārds nevar būt tukšs!");
            Izvelne.main(new String[]{});  // Ja "Cancel" vai tukšs, tiek pārtraukta funkcija
        }

        String telefons = JOptionPane.showInputDialog("Ievadiet savu telefona numuru:");
        if (telefons == null || telefons.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Telefona numurs nevar būt tukšs!");
            Izvelne.main(new String[]{});  // Ja "Cancel" vai tukšs, tiek pārtraukta funkcija
        }

        String adrese = JOptionPane.showInputDialog("Ievadiet savu mājas adresi:");
        if (adrese == null || adrese.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Adrese nevar būt tukša!");
            Izvelne.main(new String[]{});  // Ja "Cancel" vai tukšs, tiek pārtraukta funkcija
        }

        // Izvēlas datoru no pieejamajiem datoriem
        String selectedPC = choosePC(createdPCs);
        if (selectedPC == null) return;

        // Iegūst datora ID no datubāzes
        int computerId = Create_PC.getComputerIdByName(selectedPC);
        if (computerId == -1) {
            JOptionPane.showMessageDialog(null, "Kļūda: Izvēlētais dators netika atrasts datubāzē!");
            return;
        }

        // Izveido jaunu klienta ierakstu un saglabā datubāzē
        Klienti klients = new Klienti(vards, uzvards, telefons, adrese, selectedPC);
        Klienti.saveClientToDB(klients, computerId);

        JOptionPane.showMessageDialog(null, "Paldies! Jūsu pasūtījums ir pieņemts:\n" + klients);
        Izvelne.main(new String[]{});
    }

    public void viewClients() {
        List<Klienti> klienti = Klienti.getClientsFromDB(); // Iegūst klientus no datubāzes
        if (klienti.isEmpty()) { // Pārbauda, vai ir reģistrēti pasūtījumi
            JOptionPane.showMessageDialog(null, "Nav reģistrētu klientu!");
        } else {
            // Izvada visus klientus
            StringBuilder klientuSaraksts = new StringBuilder("Klientu pasūtījumi:\n");
            for (Klienti klients : klienti) {
                klientuSaraksts.append(klients).append("\n\n");
            }
            JOptionPane.showMessageDialog(null, klientuSaraksts.toString());
        }
        Izvelne.main(new String[]{});
    }

    private String choosePC(List<String> pcs) {
        String[] pcArray = pcs.toArray(new String[0]);

        // Izvēlas datoru no saraksta
        return (String) JOptionPane.showInputDialog(
                null,
                "Izvēlieties datoru pasūtīšanai:",
                "Datoru izvēle",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pcArray,
                pcArray.length > 0 ? pcArray[0] : null
        );
    }
}
