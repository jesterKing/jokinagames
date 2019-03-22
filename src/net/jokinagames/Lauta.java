package net.jokinagames;

import java.util.ArrayList;
import java.util.List;

public class Lauta {
    private final Nappula[][] palikat;

    /**
     * Luo perusshakki -Lauta-olion
     */
    public Lauta() {
        palikat = new Nappula[8][8];
    }


    public Lauta(Nappula[][] s) {
        palikat = s;
    }

    public List<Siirto> annaNappulatJoillaSiirtoMahdollinen(Koordinaatti kohde, Nappula nappula) {
        Vari vuoro = nappula.annaVari() ;
        ArrayList<Siirto> loydot = new ArrayList<>();
        for (int rivi = 0; rivi < 8; rivi++) {
            for (int sarake = 0; sarake < 8; sarake++) {

                Koordinaatti lna = new Koordinaatti(sarake, rivi);
                Nappula ln = this.annaNappula(lna);
                if (ln == null || ln.annaVari() != vuoro) continue;
                if(ln.getClass() == nappula.getClass()) {
                    Siirrot mahdolliset = ln.mahdollisetSiirrot(lna);
                    Siirrot sallitut = this.sallitutSiirrot(mahdolliset);
                    Siirto tark = new Siirto(lna, kohde);
                    if(sallitut.loytyySiirto(tark)) {
                        loydot.add(tark);
                    }
                }
            }
        }
        return loydot;
    }


    /**
     * Siirrä Nappula a:sta b:hen
     *
     * @param a
     * @param b
     * @return Lauta-olio, joka kuvaa siirronjälkeisen tilanteen
     */
    public Lauta teeSiirto(Koordinaatti a, Koordinaatti b) {                        //Siirto pelkällä koordinaateilla
        Nappula[][] s = luoNappulaMatriisiKopio();
        Nappula c = s[a.annaRivi()][a.annaSarake()];                                //Tänne jonnekki tulee oman värin tarkastusta jne.
        s[a.annaRivi()][a.annaSarake()] = null;
        s[b.annaRivi()][b.annaSarake()] = c;
        Lauta l = new Lauta(s);
        return l;
    }

    public Lauta teeSiirto(Nappula n, Koordinaatti kohde)
    {
        Nappula[][] s = luoNappulaMatriisiKopio();
        List<Siirto> siirtoNappulat = annaNappulatJoillaSiirtoMahdollinen(kohde, n);
        if(siirtoNappulat.size()==1) {
            return teeSiirto(n, siirtoNappulat.get(0).annaLahtoruutu(), kohde);
        }

        return null;
    }

    public Lauta teeSiirto(Nappula n, Koordinaatti a, Koordinaatti b) {              //Ylikuormitettu versio siirrosta nappulaoliolla.
        Nappula[][] s = luoNappulaMatriisiKopio();
        Siirrot siirt = sallitutSiirrot(n.mahdollisetSiirrot(a));
        boolean found = false;
        for (int i = 0; i < 8; i++) {
            if (found) {                                           //Mikäli löyty, lopetetaan läpikäynti.
                break;
            }
            for (Siirto si : siirt.annaSuunta(i)) {             //Käydään kaikki mahdolliset siirrot ja ilmansuunnat läpi.
                if (si.annaKohderuutu().equals(b)) {                      //Jos siirto löytyy sallituista, tehdään siirto.
                    found = true;
                    break;
                }
            }
        }
        if (found) {
            s[a.annaRivi()][a.annaSarake()] = null;
            s[b.annaRivi()][b.annaSarake()] = n;
            Util.println("Siirto tehty");
            return new Lauta(s);
        } else {
            Util.println("Siirto mahdoton");
            return null;
        }
    }

    private Nappula[][] luoNappulaMatriisiKopio() {
        Nappula[][] s = new Nappula[8][8];
        Nappula[][] alkup = getPalikat();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                s[i][j] = alkup[i][j];
            }
        }
        return s;
    }

    public void tulostaLauta() {                                //(Pistetään jos koetaan tarpeeliseksi)
        System.out.println("--------------------------");
        System.out.println();
        for (int rivi = 7; rivi >= 0; rivi--) {
            System.out.print(rivi+1 + " ");
            for (int sarake = 0; sarake < 8; sarake++) {
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
                System.out.println();
                System.out.println("  [a][b][c][d][e][f][g][h]");
                System.out.println("--------------------------");
            }
        }
    }

    private Nappula[][] getPalikat() {                                  //Palauttaa nappulamuotoisen laudan.
        return palikat;
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

    public Siirrot sallitutSiirrot(Siirrot mahdolliset) {
        Siirrot sallitut = new Siirrot();
        for (int i = 0; i < 8; i++) {
            for (Siirto mahd : mahdolliset.annaSuunta(i)) {
                Nappula n1 = annaNappula(mahd.annaLahtoruutu());                         //Lähtö
                Nappula n2 = annaNappula(mahd.annaKohderuutu());                      //Määränpää
                if (n2 == null) {
                    sallitut.annaSuunta(i).add(mahd);                          //Jos tyhjä, saa liikkua.
                } else {
                    if (n1.annaVari() == n2.annaVari()) {                       //Jos oma, matka tyssää sinne suuntaan siihen.
                        if(n1 instanceof Ratsu) continue;
                        else break;
                    } else {
                        sallitut.annaSuunta(i).add(mahd);                               //Jos vihulainen, sallittu ja viimeinen.
                        if(n1 instanceof Ratsu) continue;
                        else break;
                    }
                }
            }
        }
        return sallitut;
    }
}

//HYLÄTYN KOODIN HAUTAUSMAA; Rest in pieces.
/*
    public List<Siirto> sallitutSiirrot(List<Siirto> e) {
        List<Siirto> siirrot = new ArrayList<>();                       // uusi sallittujen siirtojen lista.
        for (Siirto s : e) {
            Koordinaatti k = s.annaKohderuutu();                                  //Haetaan määränpääkoordinaatti.
            if (palikat[k.annaRivi()][k.annaSarake()] != null) {            //Mikäli koordinaatti ei vapaa, tarkistetaan kenen on.
                Koordinaatti o = s.annaLahtoruutu();                                  // Apunappulat värin tarkistuksen
                Nappula n2 = palikat[o.annaRivi()][o.annaSarake()];
                Nappula n = palikat[k.annaRivi()][k.annaSarake()];
                if (n2.annaVari() != n.annaVari()) {
                    siirrot.add(s);                                     //Jos vastustajan nappula, siirto mahdollinen(tähän joku logiikka syömiselle)
                }
                continue;                                           //Jos oma, ei lisätä siirtoa listaan, koska ei sallittu.
            }
            siirrot.add(s);
        }
        return siirrot;
    }
}

    public int[] annaSuunta(Koordinaatti a, Koordinaatti b){
        int yy = a.annaSarake();                                        //Tässä pitäs olla hellpo seurata ajatuksen kulkua.
        int kaa = a.annaRivi();
        int koo = b.annaSarake();
        int nee = b.annaRivi();
        int sarake = koo - yy;
        int rivi = kaa - nee ;
        if(sarake>0){
            sarake = 1;
        }
        if(sarake<0) {
            sarake = -1;
        }
        if(rivi>0){
            rivi = 1;
        }
        if(rivi<0) {
            rivi = -1;
        }
        int[] suunta = {rivi, sarake};                                   //Tarkastamme siis mihin suuntaan lähdetään.
        return suunta;
    }

        if(a.equals(b)) {
            System.out.println("Siirto tehty");
            s[a.annaRivi()][a.annaSarake()] = n;
            Lauta l = new Lauta(s);
            return l;
        }
        int[] suunta = annaSuunta(a, b);                                                //Mihin suuntaan lähdetään liikkumaan.
        List<Siirto> siirt = sallitutSiirrot(n.mahdollisetSiirrot(a));                  //Kaikki sallitut siirrot
        Koordinaatti k = new Koordinaatti(a.annaSarake()+suunta[0],a.annaRivi()+suunta[1]);     //Seuraava määränpääkoordinaatti.
        for(Siirto si:siirt){
            if(si.annaKohderuutu().equals(k)){                                            //Onko määränpää sallittujen listalla.
                if(alkup[si.annaLahtoruutu().annaRivi()][si.annaLahtoruutu().annaSarake()].annaVari()!=alkup[si.annaKohderuutu().annaRivi()][si.annaKohderuutu().annaSarake()].annaVari()
                   && alkup[si.annaKohderuutu().annaRivi()][si.annaKohderuutu().annaSarake()].annaVari()!=null){     //Tarkistetaan tapahtuuko syönti siirrettäessä.
                    teeSiirto(n,b,b);
                }
                s[si.annaLahtoruutu().annaRivi()][si.annaLahtoruutu().annaSarake()]=null;
                s[si.annaKohderuutu().annaRivi()][si.annaKohderuutu().annaSarake()]=n;
                Lauta l = new Lauta(s);
                tulostaLauta(l);
                teeSiirto(n,k,b);
            }
            else {
                System.out.println("Ei mahdollinen siirto");
                break;
            }
        }*/

