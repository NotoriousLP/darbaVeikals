package darbaVeikals;

public class Komponente {
     String veids;
     String nosaukums;

    public Komponente(String veids, String nosaukums) {
        this.veids = veids;
        this.nosaukums = nosaukums;
    }

    public String getVeids() {
        return veids;
    }

    public String getNosaukums() {
        return nosaukums;
    }


    public String toString() {
        return veids + ": " + nosaukums;
    }
}
