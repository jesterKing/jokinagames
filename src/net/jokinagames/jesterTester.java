package net.jokinagames;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;

public class jesterTester {
    public static void main(String[] args) {
        Util.alusta();
        // haetaan current working dir.
        // data oletetaan olevan siinä alla, eli $CWD$/data, jne.
        final String rootFolder = FileSystems.getDefault().getPath(".").normalize().toAbsolutePath().toString();
        String dataFolder = rootFolder + File.separator + "data" + File.separator;

        for(int i = (int)('\u2654'); i<(int)('\u2654')+12; i++) {
            Util.print("" + (char)(i));
        }
        Util.println("");

        // Nathan PGN testin alku - voi kommentoida pois jos ei sitä vielä kaipaa.
        try {
            PortableGameNotationReader pgnReader = new PortableGameNotationReader(dataFolder + "test_regular_game.pgn");
            Peli peli = pgnReader.parsePgn();
            pgnReader.parseFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1");
            Util.println(peli.toString());
        } catch (FileNotFoundException fnfe) {
            Util.println(fnfe.getMessage());
        } catch (IOException ioe) {
            Util.println(ioe.getMessage());
        }

    }
}
