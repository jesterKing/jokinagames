package net.jokinagames;

import java.io.*;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * Luokka, jolla parsitaan PGN-muodon tiedostoja. Luokka osaa
 * antaa Peli-olion jokaista tiedostossa esiintyvää peliä kohden.
 * <p>
 * PGN-tiedosto voi sisältää FEN-notaatiolla alkuasetelmia perusnappuloilla
 * ja peruslaudalle (8x8).
 * <p>
 * PGN-spesifikaatio löytyy osoitteesta
 * http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm
 *
 * @author  Nathan Letwory
 */
public class PortableGameNotationReader {
    private final String gameFile;
    final private int firstPiece = (int)('\u2654');
    static final public String nappulat = "KQRBNPkqrbnp";
    static final public String nappulatSatu = "KQACRBNPkqacrbnp";
    static final public String sarakkeet = "abcdefghijklmnopqrstuvwxyz";
    static final public HashMap<Character, Character> nappulaMerkit = new HashMap<>();
    static private boolean nappulatAlustettu = false;

    static final public String perusUpseeriAsetelma = "RNBQKBNR";
    static final public String capablancaUpseeriAsetelma = "RNABQKBCNR";
    static final public String perusFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1";
    static final public String grandChessFen = "r8r/1nbqkcabn1/pppppppppp/10/10/10/10/PPPPPPPPPP/1NBQKCABN1/R8R w - - 0 1";
    static final public String cpablancaFen = capablancaUpseeriAsetelma.toLowerCase()+"pppppppppp/10/10/10/10/PPPPPPPPPP/"+capablancaUpseeriAsetelma;

    /**
     * Konstruktori, joka ottaa polun PGN-tiedostoon.
     *
     * @param   gameFile
     *          polku PGN-tiedostoon.
     * @throws  IOException
     *          jos tiedostonkäsittelyssä ilmenee ongelmia.
     * @author  Nathan Letwory
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
     * @return  Peli siirtoineen
     * @author  Nathan Letwory
     */
    public Peli parsePgn() {
        return parsePgn(0);
    }

    private int gameCount = 0;
    private long fileSize = 0;

    /**
     * Laskee pelien määrä annetussa PGN-tiedostossa
     * @return  pelien määrä
     * @author  Nathan Letwory
     */
    public int laskePelit() {
        File f = new File(gameFile);

        if (gameCount == 0 || fileSize != f.length()) {
            int s = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(gameFile));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("[Event ")) s++;
                }
            } catch (IOException ignored) {

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

        // siivotaan ylimääräiset whitespacet
        cleaned = cleaned.replaceAll("\\s{2,}", " ");

        peliPgn.set(peliPgn.size()-1, cleaned);
    }

    /**
     * Lue PGN-tiedostosta peli annetussa indeksissa
     * <p>
     * Jos PGN-pelissa MOVETEXT ja lopputulos ovat eri riveillä
     * nämä yhdistetään yhdeksi riviksi, välilyönnillä erotettuna.
     * </p>
     * Tyhjat rivit jätetään välistä.
     * @param   index
     *          Luettavan pelin indeksi.
     * @return  ArrayList&lt;String&gt; jossa pelin tagit ja movetext
     * @author  Nathan Letwory
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
     * @param   index
     *          [0, pelien määrä)
     * @return  Peli-olio valitusta pelistä
     * @author  Nathan Letwory
     */
    public Peli parsePgn(int index) throws IndexOutOfBoundsException {
        int pelienMaara = laskePelit();
        if (index < 0 || index >= pelienMaara) {
            throw new IndexOutOfBoundsException("Virheellinen indeksi PGN-tiedostoon.");
        }

        ArrayList<String> peliRivit = luePeli(index);
        String ekanimi = null;
        String tokanimi = null;
        String fen = null;
        String pvm = null;
        String klo = null;
        String event = null;
        String round = null;
        String site = null;
        String result = null;
        for(String tag : peliRivit) {
            if(tag.startsWith("[Event ")) {
                event = tagArvo(tag, "Event");
                tulostaTagArvo("Tapahtuman nimi", event);
            }
            else if(tag.startsWith("[Site ")) {
                site = tagArvo(tag, "Site");
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
                pvm = tagArvo(tag, "Date");
                tulostaTagArvo("Pelin päivämäärä", pvm);
            }
            else if(tag.startsWith("[Time ")) {
                klo = tagArvo(tag, "Time");
                tulostaTagArvo("Pelin aloitusaika", klo);
            }
            else if(tag.startsWith("[Round ")) {
                round = tagArvo(tag, "Round");
                tulostaTagArvo("Tapahtuman kierros", round);
            }
            else if(tag.startsWith("[Result ")) {
                result = tagArvo(tag, "Result");
                tulostaTagArvo("Pelin tulos", result);
            }
            else if(tag.startsWith("[FEN ")) {
                fen = tagArvo(tag, "FEN");
                tulostaTagArvo("FEN", fen);
                //parseFen(fen);
            }
        }

        String movetext = peliRivit.get(peliRivit.size()-1);
        try {

            tulostaTagArvo("movetext", movetext);
        } catch (ArrayIndexOutOfBoundsException a) {
            Util.println("hmm");
        }

        Pelaaja valkoinen = new Pelaaja(ekanimi, Vari.VALKOINEN);
        Pelaaja musta = new Pelaaja(tokanimi, Vari.MUSTA);

        Lauta lauta;
        if(fen==null) {
            lauta = alustaTavallinenPeli();
        } else {
            // luodaan FENistä
            lauta = parseFen(fen);
            lauta.tulostaLauta();
        }

        // Luo peli
        Peli peli = Peli.uusiPeli(valkoinen, musta, lauta);
        peli.tulostaNykyinenTila();
        if(pvm!=null) {
            peli.asetaPaivamaara(pvm);
        }
        if(klo!=null) {
            peli.asetaAika(klo);
        }

        Pattern p = Pattern.compile("\\d+\\.+ \\S+( \\S+)?");
        Matcher m = p.matcher(movetext);
        while(m.find()) {
            String completeTurn = m.group();
            String[] parts = completeTurn.split(" ");
            Util.print("Siirto " + parts[0],Util.Color.GREEN_BOLD, Util.Color.BLACK_BACKGROUND);
            Util.print("\n", Util.Color.RESET);
            try {
                Util.println(Vari.VALKOINEN.name() + " " + parts[1]);
                peli.seuraavaSiirto(Vari.VALKOINEN, parts[1]);
                peli.tulostaNykyinenTila();
                if(parts.length==3) {
                    Util.println(Vari.MUSTA.name() + " " + parts[2]);
                    peli.seuraavaSiirto(Vari.MUSTA, parts[2]);
                    peli.tulostaNykyinenTila();
                }
            } catch (KoordinaattiVirhe kv) {
                Util.print(kv.getMessage(), Util.Color.RED, Util.Color.BLACK_BACKGROUND);
                Util.print("\n", Util.Color.RESET);
            } catch (PelkkaKohderuutuEiRiita pker ) {
                Util.print(pker.getMessage(), Util.Color.RED, Util.Color.BLACK_BACKGROUND);
                Util.print("\n", Util.Color.RESET);
            } catch (KohderuutuJaLahtosarakeEiRiita kjler) {
                Util.print(kjler.getMessage(), Util.Color.RED, Util.Color.BLACK_BACKGROUND);
                Util.print("\n", Util.Color.RESET);
            } catch (VuoroVirhe vv) {
                Util.print(vv.getMessage(), Util.Color.RED, Util.Color.BLACK_BACKGROUND);
                Util.print("\n", Util.Color.RESET);
            }


        }

        return peli;
    }

    /**
     * Tallenna annettu peli tiedostoon PGN-muotoisena. Tiedoston nimi
     * on mallia <code>"2019-03-29_07-58-00.pgn"</code>.
     * <p>
     * {@code
     * String siivottuPvm = peli.annaPaivamaara().replaceAll("\\.", "-");
     * String pgnTiedostonNimi = siivottuPvm + "_" + peli.annaAika() + ".pgn";
     * }
     * <p>
     * @param   peli
     *          tallennettava peli
     * @author  Nathan Letwory
     */
    public static void tallennaPeli(Peli peli)
    {
        final String rootFolder = FileSystems.getDefault().getPath(".").normalize().toAbsolutePath().toString();
        String dataFolder = rootFolder + File.separator + "data" + File.separator;
        String timeStamp = peli.annaPaivamaara() + "_" + peli.annaAika();
        timeStamp = timeStamp.replaceAll("[\\.:]", "-");

        String pgnFile = dataFolder + timeStamp + ".pgn";
        File f = new File(pgnFile);

        try {
            f.createNewFile();

            try (BufferedWriter pgnWriter = new BufferedWriter(new FileWriter(pgnFile))) {
                pgnWriter.write("[Event \"?\"]\n");
                pgnWriter.write("[Site \"?\"]\n");
                pgnWriter.write("[Round \"?\"]\n");
                pgnWriter.write("[White \"" + peli.annaValkoinenPelaaja().annaNimi() + "\"]\n");
                pgnWriter.write("[Black \"" + peli.annaMustaPelaaja().annaNimi() + "\"]\n");
                pgnWriter.write("[Date \"" + peli.annaPaivamaara() + "\"]\n");
                pgnWriter.write("[Time \"" + peli.annaAika() + "\"]\n");
                pgnWriter.write("[Result \"" + peli.annaTulosToString() + "\"]\n");
                if (!peli.annaAloitusFen().equals(PortableGameNotationReader.perusFen)) {
                    pgnWriter.write("[FEN \"" + peli.annaAloitusFen() + "\"]\n");
                }
                pgnWriter.write("\n");
                int puoliVuoro = 0;
                for (String san : peli.sansiirrot) {
                    if (puoliVuoro % 2 == 0) {
                        pgnWriter.write((puoliVuoro / 2 + 1) + ". ");
                    }
                    pgnWriter.write(san + " ");
                    puoliVuoro++;
                }
                pgnWriter.write(peli.annaTulosToString());
                pgnWriter.flush();
            } catch (IOException ignored) {

            }
        } catch (IOException ignored) {

        }
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
     * @param   fen
     *          FEN-notaation mukainen merkkijono
     * @return  Lauta
     * @author  Nathan Letwory
     */
    public static Lauta parseFen(String fen) {
        String[] splitOnWhitespace = fen.split(" ");
        String lautaString = splitOnWhitespace[0];
        String[] rivit = lautaString.split("/");

        Pattern p = Pattern.compile("(\\D{1})|(\\d{1,2})");
        Matcher m = p.matcher(rivit[0]);
        int sarakkeetMax = 0;
        while(m.find()) {
            String nappulaTaiTyhja = m.group();
            int tyhja;
            try {
                tyhja = Integer.parseInt(nappulaTaiTyhja);
                sarakkeetMax+=tyhja;
            } catch (NumberFormatException ignored) {
                sarakkeetMax++;
            }
        }

        int rivitMax = rivit.length;
        Lauta fenLauta = new Lauta(sarakkeetMax, rivitMax);

        for(int riviIndeksi=0; riviIndeksi<rivitMax; riviIndeksi++) {
            String rivi = rivit[riviIndeksi];
            int sarakeIndeksi = 0;

            m = p.matcher(rivi);
            while(m.find()) {
                String nappulaTaiTyhja = m.group();

                int tyhja = 0;
                char nappulaChar;
                try {
                    tyhja = Integer.parseInt(nappulaTaiTyhja);
                    sarakeIndeksi+=tyhja;
                } catch (NumberFormatException ignored) {
                    nappulaChar = nappulaTaiTyhja.charAt(0);
                    String paikka = sarakkeet.charAt(sarakeIndeksi) + "" + (rivitMax-riviIndeksi);
                    Koordinaatti x = new Koordinaatti(paikka);
                    Nappula n = Util.luoNappula(nappulaChar, Vari.KATSOKIRJAIMESTA, sarakkeetMax, rivitMax);
                    fenLauta.asetaNappula(n, x);
                    sarakeIndeksi++;
                }
            }

            /*
            for(int nappulaIndeksi = 0; nappulaIndeksi < rivi.length(); nappulaIndeksi++) {
                int emptcnt = "0123456789".indexOf(rivi.charAt(nappulaIndeksi));
                if(emptcnt>0) {
                    for(int j=0; j< emptcnt;j++) {
                        sarakeIndeksi++;
                    }
                } else {
                    String paikka = sarakkeet.charAt(sarakeIndeksi) + "" + (rivitMax-riviIndeksi);
                    Koordinaatti x = new Koordinaatti(paikka);

                    char nappulaChar = rivi.charAt(nappulaIndeksi);
                    Nappula n = Util.luoNappula(nappulaChar, Vari.KATSOKIRJAIMESTA);
                    fenLauta.asetaNappula(n, x);
                    sarakeIndeksi++;
                }
            }*/
        }

        return fenLauta;
    }

    /**
     * Alustaa Lauta-olion perusshakki-asettelulla.
     * @return  perusshakin asettelulla alustettu Lauta-olio
     * @author  Nathan Letwory
     */
    public static Lauta alustaTavallinenPeli() {
        return parseFen(perusFen); //"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1");
    }

    /**
     * Alustaa Lauta-olion transcendental shakkia varten. Transcendental shakissa
     * takarivit ovat sekoitettuja, eikä pelaajien rivit välttämättä ole peilikuvia
     * kuten tavallisessa shakissa.
     * @return  Transcendentalshakkia varten alustettu Lauta-olio
     * @author  Nathan Letwory
     */
    public static Lauta alustaTranscendentalPeli() {
        return parseFen(sekoitettuTakarivi(Vari.MUSTA) + "/pppppppp/8/8/8/8/PPPPPPPP/" + sekoitettuTakarivi(Vari.VALKOINEN) + " w - - 0 1");
    }

    /**
     * Alustaa Lauta-olion Grand Chessia varten.
     * <p>
     * Lauta on 10x10, ja lisäksi on satunappuloita arkkipiispa (A)
     * ja kansleri (C)
     * @return  10x10 Grand Chess lauta aloitusasetelmineen
     */
    public static Lauta alustaGrandChessPeli() {
        return parseFen(grandChessFen);
    }

    /**
     * Alustaa Lauta-olion Capablancaa varten.
     * <p>
     * Lauta on 10x8
     * @return  10x8 Capablancaa varten alustettu Lauta
     */
    public static Lauta alustaCapablancaPeli() {
        return parseFen(cpablancaFen);
    }

    /**
     * Antaa takarivin, joka on sekoitettu versio perustakarivistä.
     *
     * @param   vari
     *          Pelaajan väri, jonka takarivia sekoitetaan
     * @return  Sekoitettu merkkijono värin mukaan. Pienet kirjaimet
     *          mustia nappuloita ja isot kirjaimet valkoisia nappuloita
     *          varten
     * @author  Nathan Letwory
     */
    public static String sekoitettuTakarivi(Vari vari) {
        List<Character> l = perusUpseeriAsetelma.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.shuffle(l);
        StringBuilder sb = new StringBuilder();
        l.forEach(c -> sb.append(c));
        if(vari == Vari.MUSTA) { return sb.toString().toLowerCase();}
        else { return sb.toString(); }
    }
}
