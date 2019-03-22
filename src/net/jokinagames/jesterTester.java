package net.jokinagames;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
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

            /*String movet = "1. Nf3 d5 2. d4 Nf6 3. c4 e6 4. Nc3 Be7 5. Bf4 O-O 6. e3 c5 7. dxc5 Bxc5 8. cxd5 Nxd5 9. Nxd5 exd5 10. a3 Nc6 11. Bd3 Bb6 12. O-O Bg4 13. h3 Bh5 14. b4 Re8 15. Ra2 Ne5 16. Be2 Nc4 17. Qb3 Bc7 18. Rd1 Rc8 19. Bxc7 Rxc7 20. Qa4 Qb8 21. Qb5 f6 22. Bxc4 Rxc4 23. Qxd5+ Bf7 24. Qb5 a6 25. Qa5 Rc7 26. Raa1 Bc4 27. Rac1 b6 28. Qf5 Be6 29. Qd3 a5 30. Qb5 Rec8 31. Rxc7 Qxc7 32. Nd4 Bd7 33. Qd5+ Kh8 34. bxa5 bxa5 35. Rb1 Be8 36. Rb7 Qc1+ 37. Kh2 Qxa3 38. Nf5 Bc6 39. Qxc6";
            Pattern p = Pattern.compile("\\d+\\.+ \\S+( \\S+)?");
            Matcher m = p.matcher(movet);
            while(m.find()) {
                Util.println(m.group());
            }
            String[] bits = movet.split("\\d+\\.+");
            for(String bit : bits) {
                Util.println(bit);
            }*/



            String pgnFile = dataFolder + pgnFiles[rand.nextInt(pgnFiles.length)];
            PortableGameNotationReader pgnReader = new PortableGameNotationReader(pgnFile); //dataFolder + "test_regular_game.pgn");
            //Peli peli = pgnReader.parsePgn();
            //System.out.println(peli.siirrot);

            /*Koordinaatti koordinaattiSanista = new Koordinaatti("a1");
            Koordinaatti koordinaattiIndekseista = new Koordinaatti(0, 0);
            Util.println(koordinaattiSanista.toString());
            Util.println(koordinaattiIndekseista.toString());
            assert koordinaattiIndekseista.equals(koordinaattiSanista);
            assert koordinaattiSanista.annaRivi()==0;
            assert koordinaattiSanista.annaSarake()==0;
            assert koordinaattiIndekseista.annaRivi()==0;
            assert koordinaattiIndekseista.annaSarake()==0;*/

            Lauta koordtest = pgnReader.parseFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1");
            koordtest.tulostaLauta();
            Siirto koord = Koordinaatti.luoKoordinaatit("Nc3", Vari.VALKOINEN, koordtest);
            Util.println(koord.annaLahtoruutu().toString() + " - " + koord.annaKohderuutu().toString());
            //pgnReader.parseFen("r1bq1rk1/4bppp/p1n2n2/1pppp3/4P3/2PP1N2/PPB2PPP/R1BQRNK1 w - - 0 1");
            //Lauta l = pgnReader.parseFen("rn4nr/8/8/8/8/8/8/RN4NR w - - 0 1");
            /*Lauta l = pgnReader.alustaTavallinenPeli();
            l.tulostaLauta(l);
            Ratsu r = new Ratsu(Vari.VALKOINEN);
            Koordinaatti y = new Koordinaatti("c3");
            Koordinaatti x = new Koordinaatti("b1");*/
            /*List<Siirto> mahdollisetSiirrot = r.mahdollisetSiirrot(x);
            for(Siirto s:mahdollisetSiirrot) {
                Util.println(s.annaLahtoruutu().annaSan() + " - " + s.annaKohderuutu().annaSan());
            }
            mahdollisetSiirrot = r.mahdollisetSiirrot(y);
            for(Siirto s:mahdollisetSiirrot) {
                Util.println(s.annaLahtoruutu().annaSan() + " - " + s.annaKohderuutu().annaSan());
            }*/
            /*Lauta siirronjalkeen = l.teeSiirto(x, y);
            siirronjalkeen.tulostaLauta(siirronjalkeen);
            siirronjalkeen = siirronjalkeen.teeSiirto(y, x);
            siirronjalkeen.tulostaLauta(siirronjalkeen);
            siirronjalkeen = siirronjalkeen.teeSiirto(r, x, y);
            siirronjalkeen.tulostaLauta(siirronjalkeen);*/
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
        } catch (KoordinaattiVirhe kv) {
            Util.println(kv.getMessage());
        }
        Util.print("Käsitelty " + totalgamesparsed + " peliä\n", Util.Color.BLUE_BOLD_BRIGHT, Util.Color.WHITE_BACKGROUND);
        Util.println(PortableGameNotationReader.sekoitettuTakarivi(Vari.MUSTA));
        Util.println(PortableGameNotationReader.sekoitettuTakarivi(Vari.VALKOINEN));
    }
}
