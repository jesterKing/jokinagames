package net.jokinagames;

import java.util.*;

class Kuningas extends Nappula {

	public Kuningas(Vari vari, int sarakeMax, int riviMax) {
		super(vari, sarakeMax, riviMax);
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

	@Override
	public Siirrot mahdollisetSiirrot(Koordinaatti A) {
		Siirrot siirrot = new Siirrot(); // Alustetaan uusi palautettava lista siirroista

		int lr = A.annaRivi(); // lahtorivi
		int ls = A.annaSarake(); // lahtosarake

		for (int[] a:n) { // Käydään mahdollisten siirtojen lista läpi
			int i = n.indexOf(a); // apumuuttuja siirrot luokan ilmansuuntien hakemista varten
			int ur = a[0] + lr; // Muuttuja uudelle riville
			int us = a[1] + ls; // Muuttuja uudelle sarakkeelle
			if (ur>=0 && ur<riviMax  && us>=0 && us<sarakeMax) {  // Tarkistetaan, että ollaanko laudalla ko. siirron tapauksessa
				Koordinaatti uk = new Koordinaatti(koordinaatit.charAt(us) + "" + (ur+1)); // Luodaan uusi koordinaatti
				Siirto uS = new Siirto(A, uk);  // Jos ollaan, generoidaan uusi siirto
				siirrot.annaSuunta(i).add(uS);                // ja lisätään siirtolistaan
			}
		}

		return siirrot;

	}
	@Override
	public String annaNappula() {
		if (this.annaVari() == Vari.VALKOINEN) {
			return "[K]";
		} else {
			return "[k]";
		}
	}
}