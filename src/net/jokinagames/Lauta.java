package net.jokinagames;

import java.util.List;

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
        s[a.annaRivi()][a.annaSarake()] = null;
        s[b.annaRivi()][b.annaSarake()] = c;
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
        if (n instanceof Ratsu) {
            Siirto si = n.mahdollisetSiirrot(a).get(0);                 //Apurivi jotta saadaan aloituskoordinaatti nappulalta
            Koordinaatti k = si.getA();                                 //Sarake josta lähdetään.
            s[k.annaRivi()][k.annaSarake()] = null;
            s[b.annaRivi()][b.annaSarake()] = n;
        }                                                               //Täällä jossain sitten tarkastellaan luokan mukaiset reitit.
        Lauta l = new Lauta(s);
        return l;
    }

    public Nappula[][] getPalikat() {
        return palikat;
    }

    public void tulostaLauta(Lauta l) {                                //HOXHOX KESKEN! (Pistetään jos koetaan tarpeeliseksi)
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
}