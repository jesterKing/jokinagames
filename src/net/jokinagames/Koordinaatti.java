package net.jokinagames;

public class Koordinaatti {
    private int sarake = 0; // file
    private int rivi = 0; // rank
    private final String san;
    private static String sar = "abcdefgh";                //Esim. a1 asettaa koordinaatin rivin ja sarakkeen indx [0],[7].
    private static String riv = "87654321";
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
    public Koordinaatti(int a, int b){
        this.sarake = a;
        this.rivi = b;
        san = sar.charAt(a) + "" + riv.charAt(b);
    }

    private Koordinaatti() { san = "a1"; }

    public Koordinaatti (String paikka){        //Pitäis luoda koordinaatti sen mukaan minkä ruudun saa syötteenä.
        san = paikka;
        this.sarake = sar.indexOf(paikka.charAt(0));
        this.rivi = riv.indexOf(paikka.charAt(1));
    }
    public int annaSarake(){      //Saadaan paikkatiedot laudalle.
        return sarake;
    }
    public int annaRivi(){
        return rivi;
    }
    public String annaSan() { return san; }

    @Override
    public boolean equals(Object b) {
        if(!(b instanceof Koordinaatti)) return false;
        Koordinaatti y = (Koordinaatti)b;

        return sarake==y.sarake && rivi==y.rivi;
    }

}
