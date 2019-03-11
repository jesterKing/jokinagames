package net.jokinagames;

public class Lauta {
    protected char [][] palikat;

    /**
     * Luo Lauta-olion antaman FEN-kuvauksen mukaan
     * @param fen asettelukuvaus (FEN)
     */
    public Lauta(String fen) {
        throw new UnsupportedOperationException("Ei toteutettu");
    }

    /**
     * Luo perusshakki -Lauta-olion
     */
    public Lauta(){}

    public Lauta(char[][] s){                                           //täällä periaatteesa luodaan se itse lauta.
        palikat = s;
    }


    /**
     * Siirrä Nappula a:sta b:hen
     * @param a
     * @param b
     * @return Lauta-olio, joka kuvaa siirronjälkeisen tilanteen
     */
    public Lauta teeSiirto(Koordinaatti a, Koordinaatti b) {        //Koordinaatista a kordinaattiin b tehty "nappulan" siirto.
        char s = palikat [a.annaRivi()][a.annaSarake()];            //pitäisi palauttaa uusi lauta näillä siirroilla muutettuna.
        palikat [a.annaRivi()][a.annaSarake()] = ' ';
        palikat[b.annaRivi()][b.annaSarake()] = s;
        return new Lauta(palikat);
    }

        }
