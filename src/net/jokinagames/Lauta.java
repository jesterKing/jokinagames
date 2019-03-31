package net.jokinagames;

import java.util.ArrayList;
import java.util.List;

public class Lauta {
    private final Nappula[][] palikat;

    private final int sarakkeetMax;
    private final int rivitMax;


    /**
     * Luo perusshakki -Lauta-olion.
     *
     * @param   sarakkeet
     *          Sarakkeiden määrä laudalla
     * @param   rivit
     *          Rivien määrä laudalla.
     */
    public Lauta(int sarakkeet, int rivit) {
        sarakkeetMax = sarakkeet;
        rivitMax = rivit;
        palikat = new Nappula[rivitMax][sarakkeetMax];
    }


    /**
     * Piilotettu konstruktori. Käytetään sisäisesti.
     *
     * @param   s
     *          Nappulamatriisi, jolla alustaa.
     */
    private Lauta(Nappula[][] s) {
        sarakkeetMax = s[0].length;
        rivitMax = s.length;
        palikat = s;
    }

    /**
     * Anna nappula-tyypin perusteella kaikki siirrot, joita on mahdollista tehdä
     * kohderuutuun. Eli jos kaksi tornia pääsevät samalle ruudulle niin kummankin
     * siirto tallennetaan palautettavaan listaan.
     *
     * @param   kohde
     *          Kohderuudun koordinaatti
     * @param   nappula
     *          Nappula, jonka tyypin mukaan tarkistetaan
     * @return  Lista siirtoja, joilla pääsee kohderuutuun kyseisellä nappulatyypillä.
     */
    public List<Siirto> annaNappulatJoillaSiirtoMahdollinen(Koordinaatti kohde, Nappula nappula) {
        Vari vuoro = nappula.annaVari() ;
        ArrayList<Siirto> loydot = new ArrayList<>();

        // käydään koko lautaa läpi, etsitään samaa tyyppiä kuin nappula
        for (int rivi = 0; rivi < rivitMax; rivi++) {
            for (int sarake = 0; sarake < sarakkeetMax; sarake++) {
                // katso mitä nappulaa on koordinaatilla
                Koordinaatti lna = new Koordinaatti(sarake, rivi);
                Nappula ln = this.annaNappula(lna);
                // tyhjää tai väärä väri
                if (ln == null || ln.annaVari() != vuoro) continue;
                // löytyy oikean luokan nappula
                if(ln.getClass() == nappula.getClass()) {
                    // anna sen mahdolliset siirrot
                    Siirrot mahdolliset = ln.mahdollisetSiirrot(lna);
                    // karsii pois ei-sallitut
                    Siirrot sallitut = this.sallitutSiirrot(mahdolliset);
                    // katso onko löydetyn nappulan ja kohteen välillä
                    // mahdollista edes siirtyä
                    Siirto tark = new Siirto(lna, kohde);
                    if(sallitut.loytyySiirto(tark)) {
                        loydot.add(tark); // on, lisätään
                    }
                }
            }
        }
        return loydot;
    }


    /**
     * Siirrä Nappula lähtöruudusta mista kohderuutuun minne
     *
     * @param   mista
     *          Lähtöruudun koordinaatti
     * @param   minne
     *          Kohderuudun koordinaatti
     * @return  Lauta-olio, joka kuvaa siirronjälkeisen tilanteen
     */
    public Lauta teeSiirto(Koordinaatti mista, Koordinaatti minne) {                        //Siirto pelkällä koordinaateilla
        Nappula[][] s = luoNappulaMatriisiKopio();
        Nappula n = s[mista.annaRivi()][mista.annaSarake()];
        Siirrot siirt = sallitutSiirrot(n.mahdollisetSiirrot(mista));
        boolean found = false;
        for (int i = 0; i < 8; i++) {
            if (found) {                                           //Mikäli löyty, lopetetaan läpikäynti.
                break;
            }
            for (Siirto si : siirt.annaSuunta(i)) {             //Käydään kaikki mahdolliset siirrot ja ilmansuunnat läpi.
                if (si.annaKohderuutu().equals(minne)) {                      //Jos siirto löytyy sallituista, tehdään siirto.
                    found = true;
                    break;
                }
            }
        }
        if (found) {
            s[mista.annaRivi()][mista.annaSarake()] = null;
            s[minne.annaRivi()][minne.annaSarake()] = n;
            Util.println("Siirto tehty");
            return new Lauta(s);
        } else {
            Util.println("Siirto mahdoton, mieti tarkemmin seuraavalla kerralla, vuoro meni ;)");                   //Tähän joku exceptioni minkä heitttää ja palauttaa vuoron mainissa alkuun
            return new Lauta(s);
        }
    }


    /**
     * Siirrä Nappula n kohderuutuun
     * @param   n
     *          Siirrettävä nappula
     * @param   kohde
     *          Kohderuudun koordinaatti
     * @return  Lauta-olio, joka kuvaa siirronjälkeisen tilanteen
     */
    public Lauta teeSiirto(Nappula n, Koordinaatti kohde)
    {
        Nappula[][] s = luoNappulaMatriisiKopio();
        List<Siirto> siirtoNappulat = annaNappulatJoillaSiirtoMahdollinen(kohde, n);
        if(siirtoNappulat.size()==1) {
            return teeSiirto(n, siirtoNappulat.get(0).annaLahtoruutu(), kohde);
        }

        return null;
    }

    /**
     * Siirrä Nappula lähtöruudusta mista kohderuutuun minne
     *
     * @param   n
     *          Siirrettävä nappula
     * @param   mista
     *          Lähtöruudun koordinaatti
     * @param   minne
     *          Kohderuudun koordinaatti
     * @return  Lauta-olio, joka kuvaa siirronjälkeisen tilanteen
     */
    public Lauta teeSiirto(Nappula n, Koordinaatti mista, Koordinaatti minne) {              //Ylikuormitettu versio siirrosta nappulaoliolla.
        Nappula[][] s = luoNappulaMatriisiKopio();
        Siirrot siirt = sallitutSiirrot(n.mahdollisetSiirrot(mista));
        boolean found = false;
        for (int i = 0; i < 8; i++) {
            if (found) {                                           //Mikäli löyty, lopetetaan läpikäynti.
                break;
            }
            for (Siirto si : siirt.annaSuunta(i)) {             //Käydään kaikki mahdolliset siirrot ja ilmansuunnat läpi.
                if (si.annaKohderuutu().equals(minne)) {                      //Jos siirto löytyy sallituista, tehdään siirto.
                    found = true;
                    break;
                }
            }
        }
        if (found) {
            s[mista.annaRivi()][mista.annaSarake()] = null;
            s[minne.annaRivi()][minne.annaSarake()] = n;
            Util.println("Siirto tehty");
            return new Lauta(s);
        } else {
            Util.println("Siirto mahdoton");
            return null;
        }
    }

    private Nappula[][] luoNappulaMatriisiKopio() {
        Nappula[][] s = new Nappula[rivitMax][sarakkeetMax];
        Nappula[][] alkup = getPalikat();
        for (int i = 0; i < rivitMax; i++) {
            for (int j = 0; j < sarakkeetMax; j++) {
                s[i][j] = alkup[i][j];
            }
        }
        return s;
    }

    /**
     * Tulostaa Lauta näytölle.
     */
    public void tulostaLauta() {                                //(Pistetään jos koetaan tarpeeliseksi)
        System.out.println("--------------------------");
        System.out.println();
        for (int rivi = rivitMax-1; rivi >= 0; rivi--) {
            System.out.print(rivi+1);
            if(rivi+1<10) System.out.print("  ");
            else System.out.print(" ");
            for (int sarake = 0; sarake < sarakkeetMax; sarake++) {
                Koordinaatti x = new Koordinaatti(sarake, rivi);
                Nappula n = annaNappula(x);
                if (n != null) {
                    System.out.print(n.annaNappula());
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println();
            if (rivi == 0) {
                System.out.print("   ");
                StringBuilder line = new StringBuilder("___");
                for(int sarakeIdx = 0; sarakeIdx< sarakkeetMax; sarakeIdx++) {
                    System.out.print("[");
                    System.out.print(PortableGameNotationReader.sarakkeet.charAt(sarakeIdx));
                    System.out.print("]");
                    line.append("___");
                }
                System.out.println();
                System.out.println(line.toString());
            }
        }
    }

    private Nappula[][] getPalikat() {                                  //Palauttaa nappulamuotoisen laudan.
        return palikat;
    }


    /**
     * Palauttaa pelaajan kaikkien nappuloiden mahdolliset loppukoordinaatit listana
     *
     * @param l nykyinen lauta
     * @param v pelaajan väri
     * @return koordinaattilista mahdollisista pelaajan siirroista
     */
    public List<Koordinaatti> annaKaikkiKoordinaatit(Lauta l, Vari v) {
        ArrayList<Koordinaatti> loppukoordinaatit = new ArrayList<>(); // Alustetaan palautettava koordinaattilista
        for (int r = 0; r < rivitMax; r++) { // Käydään lauta läpi
            for (int sa = 0; sa < sarakkeetMax; sa++) {
                Koordinaatti y = new Koordinaatti(sa, r); // Tehdään koordinaatti ruudusta missä ollaan
                if (l.annaNappula(y)!=null && l.annaNappula(y).annaVari() == v){ // Tarkistetaan onko koordinaatissa nappula ja onko se oikean värinen
                    Siirrot valisiirrot =  sallitutSiirrot(l.annaNappula(y).mahdollisetSiirrot(y)); // Haetaan nappulan mahdolliset  siirrot
                    for(int suunta=0; suunta<8; suunta++) { // Käytään Siirrot olio läpi
                        for (Siirto siirto : valisiirrot.annaSuunta(suunta)) {
                            loppukoordinaatit.add(siirto.annaKohderuutu()); // Lisätään siirron kohderuutu listaan
                        }
                    }
                }
            }
        }
        return loppukoordinaatit;
    }

    /**
     * Etsitään kuninkaan koordinaatti
     * @param l nykyinen lauta
     * @param v pelaajan väri
     * @return Kuninkaan koordinaatti
     */
    public Koordinaatti etsiKuningas(Lauta l, Vari v) {
        Koordinaatti kuningas = new Koordinaatti("a1"); // Alustetaan palautettava koordinaatti
        for (int r = 0; r < rivitMax; r++) { // Käydään lauta läpi
            for (int sa = 0; sa < sarakkeetMax; sa++) {
                Koordinaatti y = new Koordinaatti(sa, r);  // Tehdään koordinaatti ruudusta missä ollaan
                if (l.annaNappula(y)!=null && l.annaNappula(y) instanceof Kuningas && l.annaNappula(y).annaVari() != v) { // Tarkistetaan onko nappula kuningas
                    kuningas = y; // Jos on, tallennetaan koordinaatti
                }
            }
        }
        return kuningas; // Palautetaan koordinaatti
    }

    /**
     * Laita Nappula-olio laudan ruudulle x
     * @param   n
     *          Nappula, jota halutaan asettaa. Saa olla null
     * @param   x
     *          ruudun koordinaatti
     */
    public void asetaNappula(Nappula n, Koordinaatti x) {
        palikat[x.annaRivi()][x.annaSarake()] = n;                      //Käpistellään ilman getteriä, liekö väliä.
    }

    /**
     * Anna Nappula ruudulla k
     * @param   k
     *          ruutu jolla nappula
     * @return  Nappula-olio jos ruudulla k on joku, null jos tyhjä
     */
    public Nappula annaNappula(Koordinaatti k) {                        //Mikä nappula sijaitsee koordinaatissa.
        return palikat[k.annaRivi()][k.annaSarake()];
    }

    /**
     * Anna sallitut siirrot mahdollisten siirtojen perusteella.
     * @param   mahdolliset
     *          Siirrot-olio, johon on tallennettu mahdolliset siirrot
     * @return  Siirrot-olio, johon on tallennettu sallitut siirrot
     */
    public Siirrot sallitutSiirrot(Siirrot mahdolliset) {
        Siirrot sallitut = new Siirrot();
        for (int i = 0; i < 8; i++) {
            for (Siirto mahd : mahdolliset.annaSuunta(i)) {
                Nappula n1 = annaNappula(mahd.annaLahtoruutu());                         //Lähtö
                Nappula n2 = annaNappula(mahd.annaKohderuutu());                      //Määränpää
                if (n2 == null) {
                    if(n1 instanceof Sotilas && mahd.onkoYksiViistoon()) continue;
                    sallitut.annaSuunta(i).add(mahd);                          //Jos tyhjä, saa liikkua.
                } else {
                    if (n1.annaVari() == n2.annaVari()) {                       //Jos oma, matka tyssää sinne suuntaan siihen.
                        if(n1 instanceof Ratsu) continue;
                        if(n1 instanceof Kansleri) continue;
                        if(n1 instanceof Arkkipiispa) continue;
                        else break;
                    } else {
                        if(n1 instanceof Sotilas && !mahd.onkoYksiViistoon()) continue; // ei voi mennä suoraan vihun päälle
                        sallitut.annaSuunta(i).add(mahd);                               //Jos vihulainen, sallittu ja viimeinen.
                        if(n1 instanceof Ratsu) continue;
                        else if(n1 instanceof Kansleri) continue;
                        else if(n1 instanceof Arkkipiispa) continue;
                        else break;
                    }
                }
            }
        }
        return sallitut;
    }

    public String annaFen() {
       StringBuilder fenBuilder = new StringBuilder();
       for(int rivi=rivitMax-1; rivi>=0; rivi--) {
           int emptycnt = 0;
           for(int sarake=0; sarake<sarakkeetMax; sarake++) {
               Koordinaatti k = new Koordinaatti(sarake, rivi);
               Nappula n = annaNappula(k);
               if(n==null) {
                   emptycnt++;
               } else {
                   if(emptycnt>0) {
                       fenBuilder.append(emptycnt);
                       emptycnt=0;
                   }
                   char nappulaMerkki = n.annaNappula().charAt(1);
                   if(n.annaVari()==Vari.MUSTA) nappulaMerkki = Character.toLowerCase(nappulaMerkki);
                   else nappulaMerkki = Character.toUpperCase(nappulaMerkki);
                   fenBuilder.append(nappulaMerkki);
               }
               if(sarake==sarakkeetMax-1 && emptycnt>0) {
                   fenBuilder.append(emptycnt);
               }
           }
           if(rivi>0) fenBuilder.append('/');
       }
       return fenBuilder.toString();
    }

    public int annaSarakkeetMax() { return sarakkeetMax; }
    public int annaRivitMax() { return rivitMax; }

}
