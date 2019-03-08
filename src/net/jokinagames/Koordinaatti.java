package net.jokinagames;

public class Koordinaatti {
    /**
     * Luo Koordinaatti-oliot annetun SAN-notaation mukaan
     * @param san
     * @return kaksipaikkainen Koordinaatti-array
     */
    public static Koordinaatti[] luoKoordinaatit(String san) {
        Koordinaatti a = new Koordinaatti();
        Koordinaatti b = new Koordinaatti();

        boolean syo = san.indexOf('x')>-1;

        if(PortableGameNotationReader.nappulat.indexOf(san.charAt(0))>-1) {
            // upseeri
        } else {
            // sotilas
        }

        return new Koordinaatti[] {a, b};
    }

    private int sarake = 0; // file
    private int rivi = 0; // rank
    private Koordinaatti() {

    }
}
