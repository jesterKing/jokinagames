package net.jokinagames;

import javax.sound.sampled.Port;
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
     * <p>&nbsp;</p>
     * <p>&nbsp;</p>
     * <code>"abcdefghij"</code>
     * <p>&nbsp;</p>
     * Merkkijonosta löytyvät myös "i" ja "j" jos halutaan
     * laajentaa laudan kokoa 10x8 (Capablanca Chess, Capablanca Random Chess)
     */
    private static String sarakkeet = "abcdefghijklmnopqrstuvwxyz";                //Esim. a1 asettaa koordinaatin rivin ja sarakkeen indx [0],[7].
    /**
     * Luo Koordinaatti-oliot annetun SAN-notaation mukaan.
     * <p>&nbsp;</p>
     * <p>&nbsp;</p>
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
     * @throws  VuoroVirhe
     *          jos pelaaja yrittää siirtää vastustajan nappulaa
     */
    public static Siirto luoKoordinaatit(String san, Vari vuoro, Lauta l) throws VuoroVirhe, KoordinaattiVirhe, PelkkaKohderuutuEiRiita, KohderuutuJaLahtosarakeEiRiita {
        Koordinaatti a = null;
        Koordinaatti b;

        // luetaan sanista erikoistilanteet
        boolean syo = san.indexOf('x')>-1;
        boolean shakki = san.indexOf('+')>-1;
        boolean matti = san.indexOf('#')>-1;
        // siivotaan erikoismerkit pois, niiden esiintyminen nyt tiedossa
        String puhdistettuSan = san.replaceAll("[x\\+#]", "");

        Nappula n;

        String nappulat = l.annaSarakkeetMax()>=10 ? PortableGameNotationReader.nappulatSatu : PortableGameNotationReader.nappulat;

        String lahtoSarake = null;
        if(san.length()>2 && puhdistettuSan.matches("[a-zA-Z]{2}.*") && nappulat.indexOf(san.charAt(0))>-1) {
            // upseeri
            char nappulaChar = puhdistettuSan.charAt(0);
            n = Util.luoNappula(nappulaChar, vuoro, l.annaSarakkeetMax(), l.annaRivitMax());
            // nappulatyyppi handlattu, pidetään loput sanista
            puhdistettuSan = puhdistettuSan.substring(1);
        } else {
            // sotilas
            n = new Sotilas(vuoro, l.annaSarakkeetMax(), l.annaRivitMax());
        }

        if(n.annaVari()!=vuoro) {
            throw new VuoroVirhe("Vääränvärinen nappula, yritit " + n.annaVari().toString());
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
            // varmista että lähtöruudussa on annettua nappulaa
            Nappula lahtoNappula = l.annaNappula(a);
            if(lahtoNappula==null || !lahtoNappula.equals(n)) {
                throw new KoordinaattiVirhe("Annetulla lähtöruudulla ei ole (oikeaa) nappulaa.");
            }
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
            } else if(loydot.size()==1) {
                // kätsy, oli vain se yksi siirto
                a = loydot.get(0).annaLahtoruutu();
            } else {
                throw new KoordinaattiVirhe("Annettu siirto ei löytynyt");
            }
        }

        return new Siirto(a, b);
    }

    /**
     * Luo koordinaatti 0-based indeksien avulla. a1 on 0,0
     * @param   sarake
     *          sarakkeen indeksi
     * @param   rivi
     *          rivin indeksi
     */

    public Koordinaatti(int sarake, int rivi){
        this.sarake = sarake;
        this.rivi = rivi;
        san = sarakkeet.charAt(sarake) + "" + (rivi+1);
    }

    /**
     * Luo koordinaatti SAN-notaation mukaan.
     * <p>&nbsp;</p>
     * <p>&nbsp;</p>
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
        this.rivi = Integer.parseInt(paikka.substring(1))-1;
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
     * @param   b
     *          objekti johon verrataan.
     * @return <code>true</code> jos b on tyyppiä <code>Koordinaatti</code> ja sarakkeet ja rivit ovat kummallakin samat.
     */
    @Override
    public boolean equals(Object b) {
        if(!(b instanceof Koordinaatti)) return false;
        Koordinaatti y = (Koordinaatti)b;

        return sarake==y.sarake && rivi==y.rivi;
    }

}
