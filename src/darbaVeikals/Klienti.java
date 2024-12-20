package darbaVeikals;

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
                "\nAdrese: " + adrese + "\nPasūtītais dators: " + pasutitaisDators;
    }
}
