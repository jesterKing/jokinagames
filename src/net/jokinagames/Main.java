package net.jokinagames;

public class Main {

    public static void main(String[] args) {

        // Nathan PGN testin alku - voi kommentoida pois jos ei sitä vielä kaipaa.

        PortableGameNotationReader pgnReader = new PortableGameNotationReader("data\\test_regular_game.pgn");
        Peli peli = pgnReader.parsePgn();
        if(peli.peliOhi()) {
            // ei vielä mitään.
        }
    }
}
