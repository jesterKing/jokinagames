package net.jokinagames;

import java.io.*;
import java.util.HashMap;

/**
 * Luokka, jolla parsitaan PGN-muodon tiedostoja. Luokka osaa
 * antaa Peli-olion jokaista tiedostossa esiintyvää peliä kohden.
 * <p>
 * PGN-tiedosto voi sisältää FEN-notaatiolla alkuasetelmia perusnappuloilla
 * ja peruslaudalle (8x8).
 * <p>
 * PGN-spesifikaatio löytyy osoitteesta
 * http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm
 */
public class PortableGameNotationReader {
    private final String gameFile;

    final private int firstPiece = (int)('\u2654');
    final private char[] pieces = new char[] {'K', 'Q', 'R', 'B', 'N', 'P', 'k', 'q', 'r', 'b', 'n', 'p'};
    final private HashMap<Character, Character> nappulaMerkit = new HashMap<>();

    /**
     * Konstruktori, joka ottaa polun PGN-tiedostoon.
     *
     * @param gameFile polku PGN-tiedostoon.
     */
    public PortableGameNotationReader(String gameFile) throws IOException {
        this.gameFile = gameFile;


        File f = new File(gameFile);
        if (!f.isFile()) {
            throw new FileNotFoundException("Tiedosto ei ole olemassa: " + gameFile + ".");
        }

        if(laskePelit()==0) {
            throw new IOException("Tiedostossa ei ole pelejä.");
        }
        Util.println("Käytetään " + this.gameFile);

        String osname = System.getProperty("os.name");

        // alustetaan nappulamerkki hashmappi
        for(int i = 0; i < pieces.length; i++) {
            if(osname.startsWith("Windows")) {
                nappulaMerkit.put(pieces[i], pieces[i]);
            } else {
                nappulaMerkit.put(pieces[i], Util.charFromInt(firstPiece + i));
            }
        }
    }

    /**
     * Parsitaan PGN-tiedostosta ensimmäinen peli.
     * <p>
     * Jos PGN-tiedostossa on useampi peli vain ensimmäinen
     * parsitaan.
     *
     * @return Peli siirtoineen
     */
    public Peli parsePgn() {
        return parsePgn(0);
    }

    private int gameCount = 0;
    private long fileSize = 0;
    private int laskePelit() {
        File f = new File(gameFile);

        if(gameCount==0 || fileSize!=f.length()) {
            int s = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(gameFile));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("[Event")) s++;
                }
            } catch (IOException ioe) {

            }
            gameCount = s;
            fileSize = f.length();
        }
        return gameCount;
    }

    /**
     * Parsitaan PGN-tiedostosta peli annetusta indeksistä.
     *
     * @param index [0, pelien määrä)
     * @return Peli-olio valitusta pelistä
     */
    public Peli parsePgn(int index) throws IndexOutOfBoundsException {
        int pelienMaara = laskePelit();
        if (index < 0 || index >= pelienMaara) {
            throw new IndexOutOfBoundsException("Virheellinen indeksi PGN-tiedostoon.");
        }
        /*
            TODO
            - avaa tiedosto
            - laske pelien määrä
            - varmista että index on [0, pelien määrä)
            - lue PGN-pelin yhteen stringiin
            - pätkistä tageihin ja siirtomerkkijonoon
            - parsii pelaajat
            - parsii muut tagit ja aseta Peliin (tapahtuma, pvm, kierros, jne)
         */

        // TODO parsii pelaajat
        Pelaaja valkoinen = new Pelaaja("eka", Vari.VALKOINEN);
        Pelaaja musta = new Pelaaja("toka", Vari.MUSTA);

        // TODO luo alkulauta, joko FENistä, tai regular
        Lauta lauta = new Lauta();

        // Luo peli
        Peli peli = Peli.uusiPeli(valkoinen, musta, lauta);

        // TODO lue PGN:stä kaikki siirrot ja lisää peliin.

        //

        return peli;
    }

    /**
     * Parsitaan FEN-notaation mukaisen merkkijonon, ja luodaan
     * sen perusteella Lauta.
     * <p>
     * Tämä implementaatio käyttää vain alkuasettelumäärittely, eli
     * kaikki ensimmäiseen välilyöntiin
     * <p>
     * Esimerkki FEN:
     * rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1
     * <p>
     * Lisätietoja Wikipediasta osoitteessa
     * https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
     *
     * @param fen FEN-notaation mukainen merkkijono
     * @return Lauta
     */
    public Lauta parseFen(String fen) {
        Lauta fenLauta = new Lauta();

        String[] splitOnWhitespace = fen.split(" ");
        String lautaString = splitOnWhitespace[0];
        String[] rivit = lautaString.split("/");

        for(String rivi : rivit) {
            //Util.println(rivi);
            for(int i = 0; i < rivi.length(); i++) {
                int emptcnt = "0123456789".indexOf(rivi.charAt(i));
                if(emptcnt>0) {
                    for(int j=0; j< emptcnt;j++) {
                        Util.print("    ");
                    }
                } else {
                    Util.print(" " + nappulaMerkit.get(rivi.charAt(i)) + "  ");
                }
            }
            Util.ln();
        }

        return fenLauta;
    }
}
