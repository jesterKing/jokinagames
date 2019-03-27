package net.jokinagames;

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

        //Uus skanneri syötteen lukemiseen.
        Scanner skanneri = new Scanner(System.in);
        System.out.println("Tervetuloa pelaamaan shakkia!");
        System.out.println("Syötä 1 jos haluat uuden pelin.");
        System.out.println("Syötä 2 jos haluat jatkaa vanhaa peliä.");
        //Valitaan aloitetaanko uusi peli vai jatketaanko vanhaa.
        int valinta = skanneri.nextInt();
        if(valinta==1) {
            System.out.println("Haluatko tavallisen vai transcendental aloituksen?");
            System.out.println("Syötä 1 tavalliseen aloitukseen.");
            System.out.println("Syötä 2 transcendental aloitukseen.");
            //Valitaan joko perinteinen vai sekoitettu aloitusasetelma.
            int peliasetelma = skanneri.nextInt();
            System.out.println("Anna pelaajan 1 nimi (Valkoiset)");
            //Pelaajille värit ja nimet.
            skanneri.nextLine();
            String nimi = skanneri.nextLine();
            Pelaaja valkoinenPelaaja = new Pelaaja(nimi, Vari.VALKOINEN);
            System.out.println("Anna pelaajan 2 nimi (Mustat)");
            nimi = skanneri.nextLine();
            Pelaaja mustaPelaaja = new Pelaaja(nimi, Vari.MUSTA);
            //Uusi aloituslauta sen mukaan mikä valinta tehty.
            Lauta alkuLauta = peliasetelma == 1 ? PortableGameNotationReader.alustaTavallinenPeli() : PortableGameNotationReader.alustaTranscendentalPeli();
            Peli uuspeli = Peli.uusiPeli(valkoinenPelaaja, mustaPelaaja, alkuLauta);
            int siirtovuoro = 0;
            while (!uuspeli.peliOhi()) {
                handlaaVuoro(siirtovuoro % 2 == 0 ? valkoinenPelaaja : mustaPelaaja, uuspeli, skanneri);
                siirtovuoro++;
            }
            System.out.println("Peli ohi! ");
        }
        if (valinta == 2) { //Täällä haetaan jostain tiedostosta keskeneräinen peli.
        }
    }
    /**
     *  @param pelaaja pelaaja jonka vuoroa pelataan.
     *
     *   @param peli sen hetkinen pelilauta.
     *
     *    @param sca skanneri syötteelle.
     */

    public static void handlaaVuoro(Pelaaja pelaaja,Peli peli, Scanner sca){
        //Tulostetaan lauta
        peli.tulostaNykyinenTila();
        //Pyydetään siirtoa pelaajalta
        System.out.println("Anna siirto muodossa *Pa3b4*, *de5* tai *Nc6* ");
        System.out.println(pelaaja.annaNimi() + " " + pelaaja.annaVari() + " siirtää:");
        //Kokeillaan onko siirto mahdollinen.
        boolean siirtoOk = false;
        while(!siirtoOk) {
            try {
                String siirt = sca.nextLine();
                peli.seuraavaSiirto(pelaaja.annaVari(), siirt);
                //Kun siirto tehty, vaihdetaan vuoroa
                siirtoOk = true;
            //Täällä napataan kiinni kaikki errorit.
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
