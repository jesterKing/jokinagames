package net.jokinagames;

public class PortableGameNotationReader {
    private final String gameFile;
    public PortableGameNotationReader(String gameFile) {
        this.gameFile = gameFile;
    }

    /**
     * Parsitaan PGN-tiedostosta ensimmäinen peli.
     *
     * Jos PGN-tiedostossa on useampi peli vain ensimmäinen
     * parsitaan.
     * @return Peli siirtoineen
     */
    public Peli parsePgn() {
        return parsePgn(0);
    }

    /**
     * Parsitaan PGN-tiedostosta peli annetusta indeksistä.
     *
     * @param index
     * @return
     */
    public Peli parsePgn(int index) throws IndexOutOfBoundsException {

        // TODO parsii pelaajat
        Pelaaja valkoinen = new Pelaaja("eka", Vari.VALKOINEN);
        Pelaaja musta = new Pelaaja("toka", Vari.MUSTA);

        // TODO luo alkulauta, joko FENistä, tai regular
        Lauta lauta = new Lauta();

        // Luo peli
        Peli peli = Peli.uusiPeli(valkoinen, musta, lauta);

        // TODO lue PGN:stä kaikki siirrot ja lisää peliin.

        return peli;
    }

    /**
     * Parsitaan FEN-notaation mukaisen merkkijonon, ja luodaan
     * sen perusteella Lauta.
     *
     * Esimerkki FEN:
     * rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1
     *
     * Lisätietoja Wikipediasta osoitteessa
     * https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
     *
     * @param fen FEN-notaation mukainen merkkijono
     * @return Lauta
     */
    public Lauta parseFen(String fen) {
       Lauta fenLauta = new Lauta();

       return fenLauta;
    }
}
