package net.jokinagames;

import java.io.*;
import java.util.*;

public class Kuningas extends Nappula {

	public Kuningas(Vari vari) {
		super(vari);
	}

	private static final List<int[]> n = Arrays.asList( // Mahdollisten siirtojen luominen
			new int[]{1, 0},
			new int[]{1, 1},
			new int[]{0, 1},
			new int[]{-1, 1},
			new int[]{-1, 0},
			new int[]{-1, -1},
			new int[]{0, -1},
			new int[]{1, -1});

	public Siirrot mahdollisetSiirrot(Koordinaatti A) {
		Siirrot siirrot = new Siirrot(); // Alustetaan uusi palautettava lista siirroista

		int lr = A.annaRivi(); // lahtorivi
		int ls = A.annaSarake(); // lahtosarake

		for (int[] a:n) { // Käydään mahdollisten siirtojen lista läpi
			int i = n.indexOf(a); // apumuuttuja siirrot luokan ilmansuuntien hakemista varten
			int ur = a[0] + lr; // Muuttuja uudelle riville
			int us = a[1] + ls; // Muuttuja uudelle sarakkeelle
			if (ur >= 0 && ur <= 7 && us >= 0 && us <= 7) {  // Tarkistetaan, että ollaanko laudalla ko. siirron tapauksessa
				Koordinaatti uk = new Koordinaatti(koordinaatit[0].charAt(us) + "" + koordinaatit[1].charAt(ur)); // Luodaan uusi koordinaatti
				Siirto uS = new Siirto(A, uk);  // Jos ollaan, generoidaan uusi siirto
				siirrot.annaSuunta(i).add(uS);                // ja lisätään siirtolistaan
			}
		}

		return siirrot;

	}
	public String annaNappula() {
		if (this.annaVari() == Vari.VALKOINEN) {
			return "[K]";
		} else {
			return "[k]";
		}
	}
}