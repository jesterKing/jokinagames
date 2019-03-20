package net.jokinagames;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Lauta {
    protected final Nappula[][] palikat;

    /**
     * Luo Lauta-olion antaman FEN-kuvauksen mukaan
     *
     * @param fen asettelukuvaus (FEN)
     */
    public Lauta(String fen) {
        throw new UnsupportedOperationException("Ei toteutettu");
    }

    /**
     * Luo perusshakki -Lauta-olion
     */
    public Lauta() {
        palikat = new Nappula[8][8];
    }


    public Lauta(Nappula[][] s) {
        palikat = s;
    }


    /**
     * Siirrä Nappula a:sta b:hen
     *
     * @param a
     * @param b
     * @return Lauta-olio, joka kuvaa siirronjälkeisen tilanteen
     */
    public Lauta teeSiirto(Koordinaatti a, Koordinaatti b) {                        //Siirto pelkällä koordinaateilla
        Nappula[][] s = new Nappula[8][8];
        Nappula[][] alkup = getPalikat();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                s[i][j] = alkup[i][j];
            }
        }
        Nappula c = s[a.annaRivi()][a.annaSarake()];                                //Tänne jonnekki tulee oman värin tarkastusta jne.
        s[a.annaSarake()][a.annaRivi()] = null;
        s[b.annaSarake()][b.annaRivi()] = c;
        Lauta l = new Lauta(s);
        return l;
    }

    public Lauta teeSiirto(Nappula n, Koordinaatti a, Koordinaatti b) {              //Ylikuormitettu versio siirrosta nappulaoliolla.
        Nappula[][] s = new Nappula[8][8];
        Nappula[][] alkup = getPalikat();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                s[i][j] = alkup[i][j];
            }
        }
        if(a.equals(b)) {
            System.out.println("Siirto tehty");
            s[a.annaSarake()][a.annaRivi()] = n;
            Lauta l = new Lauta(s);
            return l;
        }
        int[] suunta = annaSuunta(a, b);                                                //Mihin suuntaan lähdetään liikkumaan.
        List<Siirto> siirt = sallitutSiirrot(n.mahdollisetSiirrot(a));                  //Kaikki sallitut siirrot
        Koordinaatti k = new Koordinaatti(a.annaSarake()+suunta[0],a.annaRivi()+suunta[1]);     //Seuraava määränpääkoordinaatti.
        for(Siirto si:siirt){
            if(si.getB().equals(k)){                                            //Onko määränpää sallittujen listalla.
                if(alkup[si.getA().annaSarake()][si.getA().annaRivi()].annaVari()!=alkup[si.getB().annaSarake()][si.getB().annaRivi()].annaVari()&&alkup[si.getB().annaSarake()][si.getB().annaRivi()].annaVari()!=null){     //Tarkistetaan tapahtuuko syönti siirrettäessä.
                    teeSiirto(n,b,b);
                }
                teeSiirto(n,k,b);
            }
            else {
                System.out.println("Ei mahdollinen siirto");
                break;
            }
        }
        return null;
    }


    private Nappula[][] getPalikat() {
        return palikat;
    }

    public void tulostaLauta(Lauta l) {                                //(Pistetään jos koetaan tarpeeliseksi)
        Nappula[][] indx = l.getPalikat();                              // Tulostaa laudan senhetkisen tilan tavallisilla ASCII merkeillä
        for (int i = 0; i < 8; i++) {
            if(i==0){
                System.out.println(" [a][b][c][d][e][f][g][h]");
            }
            System.out.print(8 - i);
            for (int j = 0; j < 8; j++){
                Nappula n = indx[i][j];
                if(n!=null){
                    System.out.print(n.annaNappula());
                }
                else{System.out.print("[ ]");}
            }
            System.out.println();
        }
    }


    public void asetaNappula(Nappula n, Koordinaatti x) {
        palikat[x.annaRivi()][x.annaSarake()] = n;                      //Käpistellään ilman getteriä, liekö väliä.
    }

    public List<Siirto> sallitutSiirrot(List<Siirto> e) {
        List<Siirto> siirrot = new ArrayList<>();                       // uusi sallittujen siirtojen lista.
        for (Siirto s:e){
            Koordinaatti k = s.getB();                                  //Haetaan määränpääkoordinaatti.
            if(palikat[k.annaSarake()][k.annaRivi()]!=null){            //Mikäli koordinaatti ei vapaa, tarkistetaan kenen on.
                Koordinaatti o = s.getA();                                  // Apunappulat värin tarkistuksen
                Nappula n2 = palikat[o.annaSarake()][o.annaRivi()];
                Nappula n = palikat[k.annaSarake()][k.annaRivi()];
                if(n2.annaVari()!=n.annaVari()){
                    siirrot.add(s);                                     //Jos vastustajan nappula, siirto mahdollinen(tähän joku logiikka syömiselle)
                }
                continue;                                           //Jos oma, ei lisätä siirtoa listaan, koska ei sallittu.
            }
            siirrot.add(s);
        }
        return siirrot;
    }



    public int[] annaSuunta(Koordinaatti a, Koordinaatti b){
        int yy = a.annaSarake();                                        //Tässä pitäs olla hellpo seurata ajatuksen kulkua.
        int kaa = a.annaRivi();
        int koo = b.annaSarake();
        int nee = b.annaRivi();
        int sarake = yy - koo;
        int rivi = kaa - nee;
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
        int[] suunta = {sarake,rivi};                                   //Tarkastamme siis mihin suuntaan lähdetään.
        return suunta;
    }
}

