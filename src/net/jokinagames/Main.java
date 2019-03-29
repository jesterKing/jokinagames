package net.jokinagames;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Scanner skanneri = new Scanner(System.in);             //Uus skanneri syötteen lukemiseen.
        System.out.println("Tervetuloa pelaamaan shakkia!");
        System.out.println("Syötä 1 jos haluat uuden pelin.");
        System.out.println("Syötä 2 jos haluat jatkaa vanhaa peliä.");
        int valinta = skanneri.nextInt();
        if(valinta==1) {
            System.out.println("Haluatko tavallisen vai transcendental aloituksen?");
            System.out.println("Syötä 1 tavalliseen aloitukseen.");
            System.out.println("Syötä 2 transcendental aloitukseen.");
            int peliasetelma = skanneri.nextInt();

            System.out.println("Anna pelaajan 1 nimi (Valkoiset).");
            skanneri.nextLine();
            String nimi = skanneri.nextLine();
            Pelaaja valkoinenPelaaja = new Pelaaja(nimi, Vari.VALKOINEN);
            System.out.println("Anna pelaajan 2 nimi (Mustat)");
            nimi = skanneri.nextLine();
            Pelaaja mustaPelaaja = new Pelaaja(nimi, Vari.MUSTA);

            Lauta alkuLauta = peliasetelma == 1
                    ?PortableGameNotationReader.alustaTavallinenPeli()
                    :PortableGameNotationReader.alustaTranscendentalPeli();

            Peli uuspeli = Peli.uusiPeli(valkoinenPelaaja, mustaPelaaja, alkuLauta);
            while (!uuspeli.peliOhi()) {
                handlaaVuoro(uuspeli.annaVuorossoOlevaPelaaja(),uuspeli, skanneri);
            }

            PortableGameNotationReader.tallennaPeli(peli);
            System.out.println("Peli ohi! ");
        }

        if (valinta == 2) { //Täällä haetaan jostain tiedostosta keskeneräinen peli.
            final String rootFolder = FileSystems.getDefault().getPath(".").normalize().toAbsolutePath().toString();
            String dataFolder = rootFolder + File.separator + "data" + File.separator;

            // luetellaan kaikki meidän omat tiedostomme.
            List<File> filesInFolder = null;
            try (Stream<Path> paths = Files.walk(Paths.get(dataFolder))) {
                filesInFolder = paths
                        .filter(Files::isRegularFile)
                        .filter((p) -> p.toString().endsWith(".pgn"))
                        .filter((p) -> p.getFileName().toString().matches("\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}-\\d{2}.pgn"))
                        .map(Path::toFile)
                        .sorted((f1, f2) -> Long.valueOf(f1.lastModified()).compareTo(f2.lastModified()))
                        .collect(Collectors.toList());
            } catch (IOException ignored) {

            }

            if(filesInFolder!=null && filesInFolder.size()>0) {
                int fileIdx = 0;
                for (File f : filesInFolder) {
                    fileIdx++;
                    System.out.println(fileIdx + ": " + f);
                }
                System.out.println("Valitse tiedosto [1-" + fileIdx+"]:");
                int valittuFileIdx = skanneri.nextInt();
                if(valittuFileIdx<1 || valittuFileIdx>fileIdx ) {
                    System.out.println("Tiedosto " + valittuFileIdx + " ei ole olemassa");
                } else {
                    try {
                        PortableGameNotationReader pgnReader = new PortableGameNotationReader(filesInFolder.get(valittuFileIdx - 1).getAbsolutePath());
                        Peli peli = pgnReader.parsePgn();
                        while (!peli.peliOhi()) {
                            handlaaVuoro(peli.annaVuorossoOlevaPelaaja(),peli, skanneri);
                        }
                        PortableGameNotationReader.tallennaPeli(peli);
                        System.out.println("Peli ohi!");
                    } catch (IOException ignored) {

                    }
                }
            } else {
                System.out.println("Ei ole vanhoja pelejä");
            }

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

        }
    }

    public static void handlaaVuoro(Pelaaja pelaaja, Peli peli, Scanner sca){
        peli.tulostaNykyinenTila();
        System.out.println("Anna siirto muodossa *Pa3b4*, *de5* tai *Nc6* ");
        System.out.println("Vuoro " + peli.annaKokoVuoro());
        System.out.println(pelaaja.annaNimi() + " (" + pelaaja.annaVari() + ") siirtää:");
        boolean siirtoOk = false;
        while(!siirtoOk) {
            try {
                String siirt = sca.nextLine();
                if(siirt.equals("tallenna")) {
                    PortableGameNotationReader.tallennaPeli(peli);
                    System.out.println("... tallennettu, voit jatkaa peliä nyt antamalla siirtosi");
                    continue;
                }
                else if(siirt.equals("luovutan")) {
                    System.out.println("Pelaaja " + pelaaja.annaNimi() + " (" + pelaaja.annaVari() + ") luovuttaa!");
                    peli.asetaTulos(pelaaja.annaVari()==Vari.MUSTA ? Peli.Tulos.VALKOINEN_VOITTI : Peli.Tulos.MUSTA_VOITTI);
                    siirtoOk = true;
                    continue;
                }
                else if(siirt.equals("tasapeli")) {
                    System.out.println("tasapeli");
                    peli.asetaTulos(Peli.Tulos.TASAPELI);
                }
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
            } catch (VuoroVirhe vv) {
                System.out.println(vv.getMessage());
                System.out.println("Siirrä omaa nappulaasi, yritä vielä");
            }
        }
        if (peli.onkoShakki(pelaaja.annaVari())) {
            System.out.println("Shakki");
        }
    }
}

