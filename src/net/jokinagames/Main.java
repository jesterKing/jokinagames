package net.jokinagames;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*
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
        */
        Scanner s = new Scanner(System.in);             //Uus skanneri syötteen lukemiseen.
        System.out.println("Syötä 1 jos haluat uuden pelin.");
        System.out.println("Syötä 2 jos haluat jatkaa vanhaa peliä.");
        int valinta = s.nextInt();
        if(valinta==1){
            System.out.println("Haluatko tavallisen vai transcendental aloituksen?");
            System.out.println("Syötä 1 tavalliseen aloitukseen.");
            System.out.println("Syötä 2 transcendental aloitukseen.");
            int peliasetelma = s.nextInt();
            if(peliasetelma==1) {
                System.out.println("Anna pelaajan 1 nimi(Valkoiset).");                 //Nämä kaks tulee jostain syystä samaan aikaan.
                String nimi1 = s.nextLine();
                Pelaaja yy = new Pelaaja(nimi1, Vari.VALKOINEN);
                System.out.println("Anna pelaajan 2 nimi(Mustat)");                     // -||-
                String nimi2 = s.nextLine();
                Pelaaja kaa = new Pelaaja(nimi2, Vari.MUSTA);
                Lauta uus = PortableGameNotationReader.alustaTavallinenPeli();
                Peli uuspeli = Peli.uusiPeli(yy, kaa, uus);
                while(!uuspeli.peliOhi()){
                    uuspeli.tulostaNykyinenTila();
                    System.out.println("Valkoinen pelaaja siirtää");
                    System.out.println("Anna siirto muodossa ????");
                    String siirt = s.nextLine();

                }

            }
            if(peliasetelma==2) {
                System.out.println("Anna pelaajan 1 nimi(Valkoiset).");
                String nimi1 = s.nextLine();
                Pelaaja yy = new Pelaaja(nimi1, Vari.VALKOINEN);
                System.out.println("Anna pelaajan 2 nimi(Mustat)");
                String nimi2 = s.nextLine();
                Pelaaja kaa = new Pelaaja(nimi2, Vari.MUSTA);
                Lauta uus = PortableGameNotationReader.alustaTranscendentalPeli();
                Peli uuspeli = Peli.uusiPeli(yy, kaa, uus);
                while (!uuspeli.peliOhi()) {
                    uuspeli.tulostaNykyinenTila();
                    System.out.println("Valkoinen pelaaja siirtää");
                    System.out.println("Anna siirto muodossa ????");
                    String siirt = s.nextLine();
                }
            }
        if(valinta==2){ //Täällä haetaan jostain tiedostosta keskeneräinen peli.

        }

        }
    }
}
