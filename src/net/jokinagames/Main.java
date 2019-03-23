package net.jokinagames;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;

public class Main {

    public static void main(String[] args) {
        // haetaan current working dir.
        // data oletetaan olevan siinä alla, eli $CWD$/data, jne.
        final String rootFolder = FileSystems.getDefault().getPath(".").normalize().toAbsolutePath().toString();
        String dataFolder = rootFolder + File.separator + "data" + File.separator;

        // Nathan PGN testin alku - voi kommentoida pois jos ei sitä vielä kaipaa.
        try {
            PortableGameNotationReader pgnReader = new PortableGameNotationReader(dataFolder + "test_regular_game.pgn");
            Peli peli = pgnReader.parsePgn();
            if (peli.peliOhi()) {
                // ei vielä mitään.
            }
        } catch (IOException fnfe) {
            System.out.println(fnfe.getMessage());
        }
    }
}
