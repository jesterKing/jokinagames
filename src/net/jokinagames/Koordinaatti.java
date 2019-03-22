package net.jokinagames;

import java.util.ArrayList;

class KoordinaattiVirhe extends Exception {
    public KoordinaattiVirhe(String viesti) { super(viesti); }
}
public class Koordinaatti {
    private int sarake = 0; // file
    private int rivi = 0; // rank
    private final String san;
    private static String sar = "abcdefgh";                //Esim. a1 asettaa koordinaatin rivin ja sarakkeen indx [0],[7].
    private static String riv = "87654321";
    /**
     * Luo Koordinaatti-oliot annetun SAN-notaation mukaan
     *
     * <code>
     *     Koordinaatti[] siirto = Koordinaatti.luoKoordinaatti("Nb3", Vari.VALKOINEN, l);
     * </code>
     * @param   san
     *          SAN-muotoinen merkkijono
     * @param   vuoro
     *          kenen vuoro on
     * @param   l
     *          Lauta, jolla konfiguraatio
     * @return  kaksipaikkainen Koordinaatti-array, 0 = lähtöruutu, 1 = kohderuutu
     *
     * @throws  KoordinaattiVirhe
     *          jos SAN-merkkijonon perusteella ei laudalta löytynyt vastaava siirtoa
     */
    public static Koordinaatti[] luoKoordinaatit(String san, Vari vuoro, Lauta l) throws KoordinaattiVirhe {
        Koordinaatti a = null;
        Koordinaatti b;

        boolean syo = san.indexOf('x')>-1;
        boolean shakki = san.indexOf('+')>-1;
        boolean matti = san.indexOf('#')>-1;
        String puhdistettuSan = san.replaceAll("[x\\+#]", "");

        Nappula n;

        if(PortableGameNotationReader.nappulat.indexOf(san.charAt(0))>-1) {
            // upseeri
            char nappulaChar = san.charAt(0);
            n = Util.luoNappula(nappulaChar);
            puhdistettuSan = puhdistettuSan.substring(1);
        } else {
            // sotilas
            n = new Sotilas(vuoro);
        }

        if(puhdistettuSan.length()==2) {
            b = new Koordinaatti(puhdistettuSan);
        } else {
            a = new Koordinaatti(puhdistettuSan.substring(0,1));
            b = new Koordinaatti(puhdistettuSan.substring(2));
        }

        if(a == null) {
            ArrayList<Siirto> loydot = new ArrayList<>();
            for (int rivi = 0; rivi < 8; rivi++) {
                for (int sarake = 0; sarake < 8; sarake++) {

                    Koordinaatti lna = new Koordinaatti(sarake, rivi);
                    Nappula ln = l.annaNappula(lna);
                    if (ln == null || ln.annaVari() != vuoro) continue;
                    if(ln.getClass() == n.getClass()) {
                        Siirrot mahdolliset = ln.mahdollisetSiirrot(lna);
                        Siirrot sallitut = l.sallitutSiirrot(mahdolliset);
                        Siirto tark = new Siirto(lna, b);
                        if(sallitut.loytyySiirto(tark)) {
                            loydot.add(tark);
                        }
                    }
                }
            }
            if(loydot.size()!=1) {
                throw new KoordinaattiVirhe("Siirto ei löytynyt");
            }
        }

        return new Koordinaatti[] {a, b};
    }

    /**
     * Luo koordinaatti 0-based indeksien avulla. a1 on 0,0
     * @param a sarake
     * @param b rivi
     */
    public Koordinaatti(int a, int b){
        this.sarake = a;
        this.rivi = b;
        san = sar.charAt(a) + "" + riv.charAt(b);
    }

    private Koordinaatti() { this("a1");}

    /**
     * Luo koordinaatti SAN-notaation mukaan
     *
     * <code>
     * Koordinaatti x = new Koordinaatti("a1");
     * </code>
     *
     * @param paikka
     */
    public Koordinaatti (String paikka){        //Pitäis luoda koordinaatti sen mukaan minkä ruudun saa syötteenä.
        san = paikka;
        this.sarake = sar.indexOf(paikka.charAt(0));
        this.rivi = riv.indexOf(paikka.charAt(1));
    }

    /**
     * Anna sarakkeen 0-indeksi
     *
     * @return
     */
    public int annaSarake(){      //Saadaan paikkatiedot laudalle.
        return sarake;
    }

    /**
     * Anna rivin 0-indeksi
     *
     * @return
     */
    public int annaRivi(){
        return rivi;
    }

    /**
     * Anna koordinaatin SAN-muotoinen merkkijono
     *
     * @return
     */
    public String annaSan() { return san; }

    @Override
    public boolean equals(Object b) {
        if(!(b instanceof Koordinaatti)) return false;
        Koordinaatti y = (Koordinaatti)b;

        return sarake==y.sarake && rivi==y.rivi;
    }

}
