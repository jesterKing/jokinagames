package net.jokinagames;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        // Nathan PGN testin alku - voi kommentoida pois jos ei sitä vielä kaipaa.

        try {
            PortableGameNotationReader pgnReader = new PortableGameNotationReader("data\\test_regular_game.pgn");
            Peli peli = pgnReader.parsePgn();
            if (peli.peliOhi()) {
                // ei vielä mitään.
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
