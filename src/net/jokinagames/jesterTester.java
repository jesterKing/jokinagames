package net.jokinagames;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.Random;

public class jesterTester {
    public static void main(String[] args) {
        Random rand = new Random(); //13);
        Util.alusta();
        // haetaan current working dir.
        // data oletetaan olevan siinä alla, eli $CWD$/data, jne.
        final String rootFolder = FileSystems.getDefault().getPath(".").normalize().toAbsolutePath().toString();
        String dataFolder = rootFolder + File.separator + "data" + File.separator;
        String[] pgnFiles = null;

        File data = new File(dataFolder);
        if(data.isDirectory()) {
            FilenameFilter pgnsOnly = (dir, name) -> name.toLowerCase().endsWith(".pgn");
            pgnFiles = data.list(pgnsOnly);

        }

        for(int i = (int)('\u2654'); i<(int)('\u2654')+12; i++) {
            Util.print("" + (char)(i));
        }
        Util.println("");

        int totalgamesparsed = 0;
        // Nathan PGN testin alku - voi kommentoida pois jos ei sitä vielä kaipaa.
        try {
            String pgnFile = dataFolder + pgnFiles[rand.nextInt(pgnFiles.length)];
            PortableGameNotationReader pgnReader = new PortableGameNotationReader(pgnFile); //dataFolder + "test_regular_game.pgn");
            //pgnReader.parseFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1");
            //pgnReader.parseFen("r1bq1rk1/4bppp/p1n2n2/1pppp3/4P3/2PP1N2/PPB2PPP/R1BQRNK1 w - - 0 1");
            Lauta l = pgnReader.parseFen("n6n/8/8/8/8/8/8/N6N w - - 0 1");
            l.tulostaLauta(l);
            Ratsu r = new Ratsu(Vari.VALKOINEN);
            Koordinaatti y = new Koordinaatti("b3");
            Koordinaatti x = new Koordinaatti("a1");
            List<Siirto> mahdollisetSiirrot = r.mahdollisetSiirrot(x);
            for(Siirto s:mahdollisetSiirrot) {
                Util.println(s.getA().annaSan() + " - " + s.getB().annaSan());
            }
            mahdollisetSiirrot = r.mahdollisetSiirrot(y);
            for(Siirto s:mahdollisetSiirrot) {
                Util.println(s.getA().annaSan() + " - " + s.getB().annaSan());
            }
            Lauta siirronjalkeen = l.teeSiirto(x, y);
            siirronjalkeen.tulostaLauta(siirronjalkeen);
            siirronjalkeen = siirronjalkeen.teeSiirto(y, x);
            siirronjalkeen.tulostaLauta(siirronjalkeen);
            siirronjalkeen = siirronjalkeen.teeSiirto(r, x, y);
            siirronjalkeen.tulostaLauta(siirronjalkeen);
            //Peli peli = pgnReader.parsePgn();
            //Util.println(peli.toString());


            /*
            for(String name : pgnFiles) {
                String pgnFile = dataFolder + name;
                PortableGameNotationReader pgnReader = new PortableGameNotationReader(pgnFile); //dataFolder + "test_regular_game_many.pgn");
                int pelienMaara = pgnReader.laskePelit();
                Util.println("Tiedostossa on " + pelienMaara + " peliä");
                for(int i=0; i< pelienMaara; i++) {
                    Peli peli = pgnReader.parsePgn(i);
                    Util.println(peli.toString());
                    totalgamesparsed++;
                }
            }
            */

        } catch (FileNotFoundException fnfe) {
            Util.println(fnfe.getMessage());
        } catch (IOException ioe) {
            Util.println(ioe.getMessage());
        }
        Util.print("Käsitelty " + totalgamesparsed + " peliä\n", Util.Color.BLUE_BOLD_BRIGHT, Util.Color.WHITE_BACKGROUND);
        Util.println(PortableGameNotationReader.sekoitettuTakarivi(Vari.MUSTA));
        Util.println(PortableGameNotationReader.sekoitettuTakarivi(Vari.VALKOINEN));
    }
}
