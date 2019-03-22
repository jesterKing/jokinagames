package net.jokinagames;

import java.util.ArrayList;
import java.util.List;

class KoordinaattiVirhe extends Exception {
    public KoordinaattiVirhe(String viesti) { super(viesti); }
}
public class Koordinaatti {
    private int sarake; // indeksi jonoon "abcdefgh"
    private int rivi; // indeksi jonoon "12345678"
    private final String san;
    private static String sar = "abcdefgh";                //Esim. a1 asettaa koordinaatin rivin ja sarakkeen indx [0],[7].
    private static String riv = "12345678";
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
    public static Siirto luoKoordinaatit(String san, Vari vuoro, Lauta l) throws KoordinaattiVirhe {
        Koordinaatti a = null;
        Koordinaatti b;

        boolean syo = san.indexOf('x')>-1;
        boolean shakki = san.indexOf('+')>-1;
        boolean matti = san.indexOf('#')>-1;
        String puhdistettuSan = san.replaceAll("[x\\+#]", "");

        Nappula n;

        String lahtoSarake = null;
        if(san.length()>2 && PortableGameNotationReader.nappulat.indexOf(san.charAt(0))>-1) {
            // upseeri
            char nappulaChar = san.charAt(0);
            n = Util.luoNappula(nappulaChar, vuoro);
            puhdistettuSan = puhdistettuSan.substring(1);
        } else {
            // sotilas
            n = new Sotilas(vuoro);
        }

        if(n.annaVari()!=vuoro) {
            throw new KoordinaattiVirhe("Väärä vuoro");
        }

        if(puhdistettuSan.length()==2) {
            b = new Koordinaatti(puhdistettuSan);
        } else if (puhdistettuSan.length()==3) { // sisältää lähtösarakkeen
            lahtoSarake = puhdistettuSan.substring(0,1);
            b = new Koordinaatti(puhdistettuSan.substring(1));
        } else if (puhdistettuSan.length()==4) { // sisältää lähtöruudun
            a = new Koordinaatti(puhdistettuSan.substring(0,2));
            b = new Koordinaatti(puhdistettuSan.substring(2));
        } else {
            throw new KoordinaattiVirhe("SAN ei kelvollinen");
        }

        if(a == null) {
            List<Siirto> loydot = l.annaNappulatJoillaSiirtoMahdollinen(b, n);
            if(loydot.size()!=1) {
                if(lahtoSarake!=null) {
                    for(Siirto test : loydot) {
                        if(test.annaLahtoruutu().annaSan().contains(lahtoSarake)) {
                            a = test.annaLahtoruutu();
                            break;
                        }
                    }
                    if(a==null) {
                        throw new KoordinaattiVirhe("Siirto ei löytynyt, vaikka oli lähtösarake tiedossa");
                    }
                } else {
                    if(n instanceof Sotilas) {
                        if(!syo) { // löydä se suora siirto, ei vinoon kun ei syödä.
                            for(Siirto test : loydot) {
                                if(test.annaLahtoruutu().annaSarake()==b.annaSarake()) {
                                    a = test.annaLahtoruutu();
                                    break;
                                }
                            }
                        } else { // syödään, etsitään se vino siirto
                            for(Siirto test : loydot) {
                                if(test.annaLahtoruutu().annaSarake()!=b.annaSarake()) {
                                    a = test.annaLahtoruutu();
                                    break;
                                }
                            }

                        }
                        //a = loydot.get(0).annaLahtoruutu();
                    } else {
                        throw new KoordinaattiVirhe("Siirto ei löytynyt, eikä lähtösaraketta tiedossa");
                    }
                }
            } else {
                a = loydot.get(0).annaLahtoruutu();
            }
        }

        return new Siirto(a, b);
    }

    /**
     * Luo koordinaatti 0-based indeksien avulla. a1 on 0,0
     * @param   sarake
     *          sarakkeen indeksi (0-7 => a-h)
     * @param   rivi
     *          rivin indeksi (0-7 => 1-8)
     */
    public Koordinaatti(int sarake, int rivi){
        this.sarake = sarake;
        this.rivi = rivi;
        san = sar.charAt(sarake) + "" + riv.charAt(rivi);
    }

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
    public String toString() {
        return annaSan()+ " (" + annaRivi()+ ", " + annaSarake() + ")";
    }

    @Override
    public boolean equals(Object b) {
        if(!(b instanceof Koordinaatti)) return false;
        Koordinaatti y = (Koordinaatti)b;

        return sarake==y.sarake && rivi==y.rivi;
    }

}
