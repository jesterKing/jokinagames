package net.jokinagames;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Ratsu extends Nappula {

    public Ratsu(Vari vari)
    {
        super(vari);
    }

    public List<Siirto> mahdollisetSiirrot(Koordinaatti A) {
        List<Siirto> Siirrot = new ArrayList<>(); // Alustetaan uusi palautettava lista siirroista
        String[] koordinaatit = {"abcdefgh", "12345678"}; // Apuna käytettävät Stringit
        List<Integer> testi = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7)); // Lista laudallaolotarkastusta varten
        int lr = A.annaRivi(); // lahtorivi
        int ls = A.annaSarake(); // lahtosarake
        List<int[]> n = Arrays.asList( // Mahdollisten siirtojen luominen
                new int[]{2, 1},
                new int[]{2, -1},
                new int[]{1, 2},
                new int[]{1, -2},
                new int[]{-2, 1},
                new int[]{-2, -1},
                new int[]{-1, 2},
                new int[]{-1, -2});

        for (int[] a:n){ // Käydään mahdollisten siirtojen lista läpi
            if (testi.contains(a[0] + lr) && testi.contains(a[1] + ls)){// Tarkistetaan, että ollaanko laudalla ko.
                                                                        // siirron tapauksessa
                Siirrot.add(new Siirto(A, new Koordinaatti(koordinaatit[0].charAt(a[0]+lr)
                        +""+koordinaatit[1].charAt(a[1]+ls)))); // Jos ollaan, generoidaan uusi siirto
                                                                // ja lisätään siirtolistaan
            }
        }

        return Siirrot; // Palautetaan siirtolista

    }
}
