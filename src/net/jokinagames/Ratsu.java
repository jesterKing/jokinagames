package net.jokinagames;

import java.util.List;
import java.util.Arrays;

class Ratsu extends Nappula {

    public Ratsu(Vari vari, int sarakeMax, int riviMax)
    {
        super(vari, sarakeMax, riviMax);
    }

    private static final List<int[]> n = Arrays.asList( // Mahdollisten siirtojen luominen
            new int[]{2, 1},
            new int[]{1, 2},
            new int[]{-1, 2},
            new int[]{-2, 1},
            new int[]{-2, -1},
            new int[]{-1, -2},
            new int[]{1, -2},
            new int[]{2, -1}
            );


    public Siirrot mahdollisetSiirrot(Koordinaatti A) {
        Siirrot siirrot = new Siirrot(); // Alustetaan uusi palautettava lista siirroista

        int lr = A.annaRivi(); // lahtorivi
        int ls = A.annaSarake(); // lahtosarake
        int[] apuja = {1,1,3,3,5,5,7,7}; // tähän hätään en keksinyt muutakaan apua

        for (int[] a:n){ // Käydään mahdollisten siirtojen lista läpi
            int i = apuja[n.indexOf(a)];
            int ur = a[0] + lr; // Muuttuja uudelle riville
            int us = a[1] + ls; // Muuttuja uudelle sarakkeelle
            if (ur>=0 && ur<riviMax && us>=0 && us<sarakeMax){  // Tarkistetaan, että ollaanko laudalla ko. siirron tapauksessa
                Koordinaatti uk = new Koordinaatti(koordinaatit.charAt(us) +""+(ur+1)); // Luodaan uusi koordinaatti
                Siirto uS = new Siirto(A, uk);  // Jos ollaan, generoidaan uusi siirto
                siirrot.annaSuunta(i).add(uS);  // ja lisätään siirtolistaan
            }
        }

        return siirrot; // Palautetaan siirtolista

    }
    public String annaNappula() {
        if (this.annaVari() == Vari.VALKOINEN) {
            return "[N]";
        } else {
            return "[n]";
        }
    }

}
