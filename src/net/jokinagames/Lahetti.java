package net.jokinagames;

import java.io.*;
import java.util.*;

public class Lahetti extends Nappula {

	public Lahetti(Vari vari) {
			super(vari);
		}

		private static final List<int[]> n = Arrays.asList( // Mahdollisten siirtojen luominen
				new int[]{1, 1},
				new int[]{-1, 1},
				new int[]{-1, -1},
				new int[]{1, -1});


		public Siirrot mahdollisetSiirrot(Koordinaatti A) {
			Siirrot siirrot = new Siirrot(); // Alustetaan uusi palautettava lista siirroista

			int lr = A.annaRivi(); // lahtorivi
			int ls = A.annaSarake(); // lahtosarake
			int i = 1; // apumuuttuja oikean listan löytämiseen siirrot oliossa, ensimmäinen suunta NE, joten alkaa 1
			for (int[] a:n){
				int ur = a[0] + lr; // Muuttuja uudelle riville
				int us = a[1] + ls; // Muuttuja uudelle sarakkeelle
				while (ur>=0 && ur<=7 && us>=0 && us<=7){
					Koordinaatti uk = new Koordinaatti(koordinaatit[0].charAt(us) +""+koordinaatit[1].charAt(ur)); // Luodaan uusi koordinaatti
					Siirto uS = new Siirto(A, uk);  // Jos ollaan, generoidaan uusi siirto
					siirrot.annaSuunta(i).add(uS);
					ur = a[0] + ur;
					us = a[1] + us;
				}
				i=i+2; // lähetin suunnat NE, SE, SW, NW, joten lisätään iihin aina kaksi jotta ollaan oikean suunnan listassa
			}
			return siirrot;
		}

		public String annaNappula() {
		if (this.annaVari() == Vari.VALKOINEN) {
			return "[B]";
		} else {
			return "[b]";
		}
	}

}
