package darbaVeikals;

public class Dators {
     String nosaukums;
     Komponente[] komponentes;
     int komponentuSkaits;

    public Dators(String nosaukums, int maxKomponentes) {
        this.nosaukums = nosaukums;
        this.komponentes = new Komponente[maxKomponentes];
        this.komponentuSkaits = 0;
    }

    public String getNosaukums() {
        return nosaukums;
    }

    public boolean pievienotKomponenti(Komponente komponente) {
        if (komponentuSkaits < komponentes.length) {
            komponentes[komponentuSkaits++] = komponente;
            return true;
        }
        return false;
    }

    public Komponente[] getKomponentes() {
        return komponentes;
    }
    
    
    public String toString() {
        String rezultats = "Dators: " + nosaukums + "\nKomponentes:\n";
        for (int i = 0; i < komponentuSkaits; i++) {
            rezultats += "- " + komponentes[i] + "\n";
        }
        return rezultats;
    }
}
