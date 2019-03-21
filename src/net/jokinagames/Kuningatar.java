package net.jokinagames;

import java.io.*;
import java.util.*;

public class Kuningatar extends Nappula {

	private static final List<int[]> n = Arrays.asList( // Mahdollisten siirtojen luominen
			new int[]{1, 0},
			new int[]{1, 1},
			new int[]{0, 1},
			new int[]{-1, 1},
			new int[]{-1, 0},
			new int[]{-1, -1},
			new int[]{0, -1},
			new int[]{1, -1});

	public Kuningatar(Vari vari) {
		super(vari);
	}

	public Siirrot mahdollisetSiirrot(Koordinaatti A) {
		Siirrot siirrot = new Siirrot(); // Alustetaan uusi palautettava lista siirroista

		int lr = A.annaRivi(); // lahtorivi
		int ls = A.annaSarake(); // lahtosarake
		int i = 0; // laskuri siirrot luokan siirtoja varten
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
			i+=1;
		}
		return siirrot;
	}

	public String annaNappula() {
		if (this.annaVari() == Vari.VALKOINEN) {
			return "[Q]";
		} else {
			return "[q]";
		}
	}

}
