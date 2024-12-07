package darbaVeikals;

import javax.swing.JOptionPane;

public class veikals {

	public static void main(String[] args) {
		  while (true) {
	            String izvele = JOptionPane.showInputDialog(
	                    "Izvēlieties darbību:\n" +
	                            "1. Klients pasūta datorus\n" +
	                            "2. Darbinieks uztaisa jaunus datorus\n" +
	                            "3. Izveido jaunas komponentes\n" +
	                            "4. Izvada ārā klientus\n" +
	                            "5. Izvada ārā datorus\n" +
	                            "6. Iziet no programmas");
	            if (izvele == null || izvele.equals("6")) {
	                JOptionPane.showMessageDialog(null, "Programma tiek izslēgta. Uz redzēšanos!");
	                break;
	            }
	            switch (izvele) {
	                case "1":
	                    //klientaPasutijums();
	                    break;
	                case "2":
	                    //izveidotJaunuDatoru();
	                    break;
	                case "3":
	                    //izveidotJaunuKomponenti();
	                    break;
	                case "4":
	                    //izvaditKlientus();
	                    break;
	                case "5":
	                    //izvaditDatorus();
	                    break;
	                default:
	                    JOptionPane.showMessageDialog(null, "Nepareiza izvēle.");
	            }
		  }

	}

}
