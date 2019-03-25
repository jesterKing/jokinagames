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
        System.out.println("Tervetuloa pelaamaan shakkia!");
        System.out.println("Syötä 1 jos haluat uuden pelin.");
        System.out.println("Syötä 2 jos haluat jatkaa vanhaa peliä.");
        int valinta = s.nextInt();
        if(valinta==1) {
            System.out.println("Haluatko tavallisen vai transcendental aloituksen?");
            System.out.println("Syötä 1 tavalliseen aloitukseen.");
            System.out.println("Syötä 2 transcendental aloitukseen.");
            int peliasetelma = s.nextInt();
            if (peliasetelma == 1) {
                System.out.println("Anna pelaajan 1 nimi (Valkoiset).");
                s.nextLine();
                String nimi = s.nextLine();
                Pelaaja yy = new Pelaaja(nimi, Vari.VALKOINEN);
                System.out.println("Anna pelaajan 2 nimi (Mustat)");
                nimi = s.nextLine();
                Pelaaja kaa = new Pelaaja(nimi, Vari.MUSTA);
                Lauta uus = PortableGameNotationReader.alustaTavallinenPeli();
                Peli uuspeli = Peli.uusiPeli(yy, kaa, uus);
                int siirtovuoro = 0;
                while (!uuspeli.peliOhi()) {
                    handlaaVuoro(siirtovuoro%2==0?yy:kaa,uuspeli, s);
                    siirtovuoro++;
                }
                System.out.println("Peli ohi! ");
            }
            if (peliasetelma == 2) {
                System.out.println("Anna pelaajan 1 nimi (Valkoiset).");
                s.nextLine();
                String nimi1 = s.nextLine();
                Pelaaja yy = new Pelaaja(nimi1, Vari.VALKOINEN);
                System.out.println("Anna pelaajan 2 nimi (Mustat)");
                String nimi2 = s.nextLine();
                Pelaaja kaa = new Pelaaja(nimi2, Vari.MUSTA);
                Lauta uus = PortableGameNotationReader.alustaTranscendentalPeli();
                Peli uuspeli = Peli.uusiPeli(yy, kaa, uus);
                int siirtovuoro = 0;
                while (!uuspeli.peliOhi()) {
                    handlaaVuoro(siirtovuoro%2==0?yy:kaa,uuspeli, s);
                    siirtovuoro++;
                }
                System.out.println("Peli ohi! ");
            }
            if (valinta == 2) { //Täällä haetaan jostain tiedostosta keskeneräinen peli.

            }
        }
    }
    public static void handlaaVuoro(Pelaaja pelaaja,Peli peli, Scanner sca){
        peli.tulostaNykyinenTila();
        System.out.println("Anna siirto muodossa *Pa3b4*, *de5* tai *Nc6* ");
        System.out.println(pelaaja.annaNimi() + " " + pelaaja.annaVari() + " siirtää:");
        boolean siirtoOk = false;
        while(!siirtoOk) {
            try {
                String siirt = sca.nextLine();
                peli.seuraavaSiirto(pelaaja.annaVari(), siirt);
                siirtoOk = true;
            } catch (KoordinaattiVirhe virhe) {
                System.out.println(virhe.getMessage());
            } catch (PelkkaKohderuutuEiRiita pelkka) {
                System.out.println(pelkka.getMessage());
                System.out.println("Yritä vielä lisäämällä lähtösarake");
            } catch (KohderuutuJaLahtosarakeEiRiita lahto) {
                System.out.println(lahto.getMessage());
                System.out.println("Yritä vielä lisäämällä koko lähtöruudun nimi");
            }
        }
        if (peli.onkoShakki()) {
            //Tarkasta shakki täällä
        }
    }
}
