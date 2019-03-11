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
    public Lauta() {
        palikat = new char[8][8];
        String[] s = {"rnbqkbnr", "pppppppp", " ", "PPPPPPPP", "RNBQKBNR" };        //Pitäis olla nappulat paikallaan
        for (int i = 0; i < 8; i++) {
            palikat[i][0] = s[0].charAt(i);
            palikat[i][1] = s[1].charAt(i);
            for (int j = 2; j < 6; j++) {
                palikat[i][j] = s[2].charAt(0);
            }
            palikat[i][6] = s[3].charAt(i);
            palikat[i][7] = s[4].charAt(i);
        }
        for (int i = 0; i < 8; i++) {                                   //Tulostetaan luotu lauta näkyville.
            System.out.println("");
            for (int j = 0; j < 8; j++) {
                System.out.print(palikat[j][i]);
            }
        }
    }


    public Lauta(char[][] s){                    //täällä luodaan itse lauta joka siirron jälkeen uudestaan.
        palikat = s;                             //ja tulostetaan nähtäville
        for(int n=0;n<8;n++){
            System.out.println("");
            for(int j=0;j<8;j++){
                System.out.print(palikat[j][n]);
            }
        }
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
