package net.jokinagames;

import java.io.*;
import java.util.*;

public class Torni extends Nappula {

	public Torni(Vari vari) {

		super(vari);
	}

	private static final List<int[]> n = Arrays.asList( // Mahdollisten siirtojen luominen
			new int[]{1, 0},
			new int[]{0, 1},
			new int[]{-1, 0},
			new int[]{0, -1});

	public Siirrot mahdollisetSiirrot(Koordinaatti A) {
		Siirrot siirrot = new Siirrot(); // Alustetaan uusi palautettava lista siirroista

		int lr = A.annaRivi(); // lahtorivi
		int ls = A.annaSarake(); // lahtosarake
		int i = 0; // apumuuttuja oikean listan löytämiseen siirrot oliossa, ensimmäinen suunta N, joten alkaa nollasta
		for (int[] a:n){

			int ur = a[0] + lr; // Muuttuja uudelle riville
			int us = a[1] + ls; // Muuttuja uudelle sarakkeelle
			while (ur>=0 && ur<=7 && us>=0 && us<=7){
				Koordinaatti uk = new Koordinaatti(koordinaatit[0].charAt(ur) +""+koordinaatit[1].charAt(us)); // Luodaan uusi koordinaatti
				Siirto uS = new Siirto(A, uk);  // Jos ollaan, generoidaan uusi siirto
				siirrot.annaSuunta(i).add(uS);
				ur = a[0] + ur;
				us = a[1] + us;
			}
			i=i+2; // tornin suunnat N, E, S, W, joten lisätään iihin aina kaksi jotta ollaan oikean suunnan listassa
		}
		return siirrot;
	}
    public String annaNappula() {
        if (this.annaVari() == Vari.VALKOINEN) {
            return "[R]";
        } else {
            return "[r]";
        }
    }
}