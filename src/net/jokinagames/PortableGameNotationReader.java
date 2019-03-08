package net.jokinagames;

import java.io.*;
import java.lang.reflect.Array;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.MatchResult;
import java.util.ArrayList;
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
    static final public String nappulat = "KQRBNPkqrbnp";
    static final public HashMap<Character, Character> nappulaMerkit = new HashMap<>();
    static private boolean nappulatAlustettu = false;

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

        if(!nappulatAlustettu) {
            // alustetaan nappulamerkki hashmappi
            for (int i = 0; i < nappulat.length(); i++) {
                if (osname.startsWith("Windows")) {
                    nappulaMerkit.put(nappulat.charAt(i), nappulat.charAt(i));
                } else {
                    nappulaMerkit.put(nappulat.charAt(i), Util.charFromInt(firstPiece + i));
                }
            }
            nappulatAlustettu = true;
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

    /**
     * Laskee pelien määrä annetussa PGN-tiedostossa
     * @return pelien määrä
     */
    public int laskePelit() {
        File f = new File(gameFile);

        if(gameCount==0 || fileSize!=f.length()) {
            int s = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(gameFile));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("[Event ")) s++;
                }
            } catch (IOException ioe) {

            }
            gameCount = s;
            fileSize = f.length();
        }
        return gameCount;
    }

    private void siistiMovetext(ArrayList<String> peliPgn ) {
        String movetext = peliPgn.get(peliPgn.size()-1);
        String curlyBraces = "\\{[^\\{\\}]*?\\}";
        String parentheses= "\\([^\\(\\)]*?\\)";
        String cleaned = movetext.replaceAll(curlyBraces, "");

        int cleancount = 0;
        while((cleaned.indexOf('{')>-1 || cleaned.indexOf('}')>-1) && cleancount < 5) {
            cleaned = cleaned.replaceAll(curlyBraces, "");
            cleancount++;
        }

        // esiintyy sisäkkäisiä kommenteja, joten käytetään mahdollisimman
        // tiukkaa regexiä ja ajetaan se kunnes ei ole enää sulkuja.
        cleancount = 0;
        while((cleaned.indexOf('(') > -1 || cleaned.indexOf(')') > -1) && cleancount < 5) {
            cleaned = cleaned.replaceAll(parentheses, "");
            cleancount++;
        }

        // siivotaan NAGit pois
        cleaned = cleaned.replaceAll("\\$\\d+", "");

        // siivotaan siirtojatkot
        cleaned = cleaned.replaceAll("\\s+\\d+\\.{2,}\\s+", " ");

        peliPgn.set(peliPgn.size()-1, cleaned);
    }

    /**
     * Lue PGN-tiedostosta peli annetussa indeksissa
     *
     * Jos PGN-pelissa MOVETEXT ja lopputulos ovat eri riveillä
     * nämä yhdistetään yhdeksi riviksi, välilyönnillä erotettuna.
     *
     * Tyhjat rivit jätetään välistä.
     * @param index
     * @return ArrayList<String> jossa pelin tagit ja movetext
     */
    private ArrayList<String> luePeli(int index) {
        ArrayList<String> peliPgn = new ArrayList<>(15);
        int pos = -1;
        try {
            BufferedReader br = new BufferedReader(new FileReader(gameFile));
            String line;
            String movetext = "";
            int movetextidx = -1;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("[Event ")) pos++;
                {
                    if(pos==index) {
                        peliPgn.add(line);
                        while(true) {
                            line = br.readLine();
                            if(line == null || line.startsWith("[Event ")) {
                                siistiMovetext(peliPgn);
                                return peliPgn;
                            }
                            line = line.trim();
                            if(line.isEmpty()) continue;
                            if(!line.startsWith("[") && movetextidx<0) {
                                movetext = line;
                                movetextidx = peliPgn.size();
                                peliPgn.add(line);
                            } else if (!line.startsWith("[") && movetext.length()>0 && movetextidx>=0) {
                                movetext = movetext + " " + line;
                                peliPgn.set(movetextidx, movetext);
                            } else {
                                peliPgn.add(line);
                            }

                        }
                    }
                }
            }
        } catch (IOException ioe) {
            Util.println("something happen");
        }

        return peliPgn;
    }

    private String tagArvo(String syote, String tagName) {
        String pattern = "\\[" + tagName + " \"?(.*?)\"??\\]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(syote);
        if(m.find()) {
            return m.group(1);
        }
        return "<?>";
    }

    private void tulostaTagArvo(String prompt, String arvo) {
        Util.print(prompt + ": " + arvo , Util.Color.YELLOW_BOLD, Util.Color.BLACK_BACKGROUND);
        Util.ln();
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
            - lue PGN-pelin yhteen stringiin
            - pätkistä tageihin ja siirtomerkkijonoon
            - parsii pelaajat
            - parsii muut tagit ja aseta Peliin (tapahtuma, pvm, kierros, jne)
         */

        ArrayList<String> peliRivit = luePeli(index);
        String ekanimi = null;
        String tokanimi = null;
        for(String tag : peliRivit) {
            if(tag.startsWith("[Event ")) {
                String event = tagArvo(tag, "Event");
                tulostaTagArvo("Tapahtuman nimi", event);
            }
            else if(tag.startsWith("[Site ")) {
                String site = tagArvo(tag, "Site");
                tulostaTagArvo("Tapaptuman paikka", site);
            }
            else if(tag.startsWith("[White ")) {
                ekanimi = tagArvo(tag, "White");
                tulostaTagArvo("Valkoinen pelaaja", ekanimi);
            }
            else if(tag.startsWith("[Black ")) {
                tokanimi = tagArvo(tag, "Black");
                tulostaTagArvo("Musta pelaaja", tokanimi);
            }
            else if(tag.startsWith("[Date ")) {
                String date = tagArvo(tag, "Date");
                tulostaTagArvo("Pelin päivämäärä", date);
            }
            else if(tag.startsWith("[Round ")) {
                String round = tagArvo(tag, "Round");
                tulostaTagArvo("Tapahtuman kierros", round);
            }
            else if(tag.startsWith("[Result ")) {
                String result = tagArvo(tag, "Result");
                tulostaTagArvo("Pelin tulos", result);
            }
            else if(tag.startsWith("[FEN ")) {
                String fen = tagArvo(tag, "FEN");
                tulostaTagArvo("FEN", fen);
                parseFen(fen);
            }
        }

        try {
            tulostaTagArvo("movetext", peliRivit.get(peliRivit.size() - 1));
        } catch (ArrayIndexOutOfBoundsException a) {
            Util.println("hmm");
        }

        // TODO parsii pelaajat
        Pelaaja valkoinen = new Pelaaja(ekanimi, Vari.VALKOINEN);
        Pelaaja musta = new Pelaaja(tokanimi, Vari.MUSTA);

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
                    Util.Color col = Character.isLowerCase(rivi.charAt(i)) ? Util.Color.BLACK : Util.Color.WHITE;
                    Util.print(" " + nappulaMerkit.get(rivi.charAt(i)) + "  ", col);
                }
            }
            Util.ln();
        }

        return fenLauta;
    }
}
