package net.jokinagames;

import java.util.List;

/**
 * <em>Koordinaatti</em> auttaa muuntamaan SAN-notaation <em>Lauta</em>-luokan
 * ymmärtämään muotoon.
 *
 * @author Nathan Letwory
 */
public class Koordinaatti {
    /**
     * Indeksi merkkijonoon sarakkeet
     */
    private int sarake; // indeksi jonoon "abcdefgh"
    /**
     * Indeksi merkkijonoon rivi
     */
    private int rivi; // indeksi jonoon "12345678"
    /**
     * Koordinaatin SAN-merkintä
     */
    private final String san;
    /**
     * Kaikki sarakenimet yhtenä merkkijonona.
     */
    private static String sarakkeet = "abcdefghij";                //Esim. a1 asettaa koordinaatin rivin ja sarakkeen indx [0],[7].
    /**
     * Kaikki rivinumerot yhtenä merkkijonona.
     */
    private static String rivit = "12345678";
    /**
     * Luo Koordinaatti-oliot annetun SAN-notaation mukaan.
     * <p>
     * <p>
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
     * @throws  PelkkaKohderuutuEiRiita
     *          jos annetulla SAN-notaatiolla, joka on muotoa "e4" tai "Nc3" ei yksiselitteistä siirtoa löydy
     * @throws  KohderuutuJaLahtosarakeEiRiita
     *          jos annetulla SAN-notaatiolla, joka on muotoa "Nbc3" ei yksiselitteistä siirtoa löydy
     */
    public static Siirto luoKoordinaatit(String san, Vari vuoro, Lauta l) throws KoordinaattiVirhe, PelkkaKohderuutuEiRiita, KohderuutuJaLahtosarakeEiRiita {
        Koordinaatti a = null;
        Koordinaatti b;

        // luetaan sanista erikoistilanteet
        boolean syo = san.indexOf('x')>-1;
        boolean shakki = san.indexOf('+')>-1;
        boolean matti = san.indexOf('#')>-1;
        // siivotaan erikoismerkit pois, niiden esiintyminen nyt tiedossa
        String puhdistettuSan = san.replaceAll("[x\\+#]", "");

        Nappula n;

        String lahtoSarake = null;
        if(san.length()>2 && PortableGameNotationReader.nappulat.indexOf(san.charAt(0))>-1) {
            // upseeri
            char nappulaChar = san.charAt(0);
            n = Util.luoNappula(nappulaChar, vuoro);
            // nappulatyyppi handlattu, pidetään loput sanista
            puhdistettuSan = puhdistettuSan.substring(1);
        } else {
            // sotilas
            n = new Sotilas(vuoro);
        }

        if(n.annaVari()!=vuoro) {
            throw new KoordinaattiVirhe("Väärä vuoro");
        }

        // vain kohderuutu tiedossa
        if(puhdistettuSan.length()==2) {
            b = new Koordinaatti(puhdistettuSan);
        } else if (puhdistettuSan.length()==3) {// sisältää lähtösarakkeen
            lahtoSarake = puhdistettuSan.substring(0,1);
            b = new Koordinaatti(puhdistettuSan.substring(1));
        } else if (puhdistettuSan.length()==4) { // sisältää koko lähtöruudun
            a = new Koordinaatti(puhdistettuSan.substring(0,2));
            b = new Koordinaatti(puhdistettuSan.substring(2));
        } else {
            throw new KoordinaattiVirhe("SAN ei kelvollinen");
        }

        // ei ollut lähtöruutua tiedossa, etsi oikea nappula/lähtöruutu
        if(a == null) {
            List<Siirto> loydot = l.annaNappulatJoillaSiirtoMahdollinen(b, n);
            // ilmeisesti useampi nappula pääsee samalle kohderuudulle
            if(loydot.size()>1) {
                // lähtösarake tiedossa, joten etsitään se, jolla oikea lähtösarake
                if(lahtoSarake!=null) {
                    for(Siirto test : loydot) {
                        if(test.annaLahtoruutu().annaSan().contains(lahtoSarake)) {
                            /* todo
                                mieti ja tarkista voiko olla tilanne, missä useampi
                                nappula samalta lähtösarakkeelta pääsisi samalle
                                kohderuudulle.
                                HANDLAA
                             */
                            a = test.annaLahtoruutu();
                            break;
                        }
                    }
                    if(a==null) {
                        throw new KohderuutuJaLahtosarakeEiRiita("Siirto ei löytynyt, vaikka oli lähtösarake tiedossa");
                    }
                } else {
                    // sotilaan tarkistus - pitää varmistaa syödäänkö (lähtösarake!=kohdesarake)
                    // vai ei (lähtösarake == kohdesarake)
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
                    } else {
                        throw new PelkkaKohderuutuEiRiita("Siirto ei löytynyt, eikä lähtösaraketta tiedossa");
                    }
                }
            } else {
                // kätsy, oli vain se yksi siirto
                a = loydot.get(0).annaLahtoruutu();
            }
        }

        return new Siirto(a, b);
    }

    /**
     * Luo koordinaatti 0-based indeksien avulla. a1 on 0,0
     * @param   sarake
     *          sarakkeen indeksi {@code (0-7 => a-h)}
     * @param   rivi
     *          rivin indeksi {@code (0-7 => 1-8)}
     */

    public Koordinaatti(int sarake, int rivi){
        this.sarake = sarake;
        this.rivi = rivi;
        san = sarakkeet.charAt(sarake) + "" + rivit.charAt(rivi);
    }

    /**
     * Luo koordinaatti SAN-notaation mukaan.
     * <p>
     * <p>
     * {@code
     * Koordinaatti x = new Koordinaatti("a1");
     * Koordinaatti y = new Koordinaatti("f7");
     * }
     *
     * @param   paikka
     *          Paikka merkkijonona, esimerkiksi "a1"
     */
    public Koordinaatti (String paikka){        //Pitäis luoda koordinaatti sen mukaan minkä ruudun saa syötteenä.
        san = paikka;
        this.sarake = sarakkeet.indexOf(paikka.charAt(0));
        this.rivi = rivit.indexOf(paikka.charAt(1));
    }

    /**
     * Anna sarakkeen 0-indeksi
     *
     * @return  sarakkeen 0-indeksi
     */
    public int annaSarake(){      //Saadaan paikkatiedot laudalle.
        return sarake;
    }

    /**
     * Anna rivin 0-indeksi
     *
     * @return  rivin 0-indeksi
     */
    public int annaRivi(){
        return rivi;
    }

    /**
     * Anna koordinaatin SAN-muotoinen merkkijono
     *
     * @return  SAN merkkijonona
     */
    public String annaSan() { return san; }

    /**
     * Palauttaa Koordinaatti-olion kuvauksen merkkijonona muodossa "SAN (rivi, sarake)"
     * @return Merkkijonon muodossa "SAN (rivi, sarake)"
     */
    @Override
    public String toString() {
        return annaSan()+ " (" + annaRivi()+ ", " + annaSarake() + ")";
    }

    /**
     * Vertailee oliota annettuun. Sarakkeen ja rivin on oltava samat, jotta
     * <code>equals()</code> palauttaa <code>true</code>
     * @param b
     * @return <code>true</code> jos b on tyyppiä <code>Koordinaatti</code> ja sarakkeet ja rivit ovat kummallakin samat.
     */
    @Override
    public boolean equals(Object b) {
        if(!(b instanceof Koordinaatti)) return false;
        Koordinaatti y = (Koordinaatti)b;

        return sarake==y.sarake && rivi==y.rivi;
    }

}
