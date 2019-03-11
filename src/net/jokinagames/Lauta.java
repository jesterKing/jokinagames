package net.jokinagames;

public class Lauta {
    protected final char [][] palikat;

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
            System.out.println();
            for (int j = 0; j < 8; j++) {
                System.out.print(palikat[j][i]);
            }
        }
    }
    public Lauta(char[][] s) {
        palikat = s;
    }


    /**
     * Siirrä Nappula a:sta b:hen
     * @param a
     * @param b
     * @return Lauta-olio, joka kuvaa siirronjälkeisen tilanteen
     */
    public Lauta teeSiirto(Koordinaatti a, Koordinaatti b) {
        Lauta l = new Lauta(getPalikat());                              //Tehdää uus lauta vanhoilla palikoilla
        char[][] s = l.getPalikat();                                    //Otetaan palikat käsittelyyn
        char c = s[a.annaRivi()][a.annaSarake()];                       //Käpistellään
        s[a.annaRivi()][a.annaSarake()] = ' ';
        s[b.annaRivi()][b.annaSarake()] = c;
        l = new Lauta(s);                                                   //Luodaan uus lauta käpistelyn jälkeen.
        return l;
    }

    public char[][] getPalikat() {
        return palikat;
    }
}
