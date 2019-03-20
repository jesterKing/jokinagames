package net.jokinagames;

public class Koordinaatti {
    private int sarake = 0; // file
    private int rivi = 0; // rank
    private final String san;
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


    private Koordinaatti() { san = "a1"; }

    public Koordinaatti (String paikka){        //Pitäis luoda koordinaatti sen mukaan minkä ruudun saa syötteenä.
        String sar = "abcdefgh";                //Esim. c3 asettaa koordinaatin rivin ja sarakkeen indx 2,2.
        String riv = "87654321";
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

}
