package net.jokinagames;

import java.util.*;

class Kuningatar extends Nappula {

	private static final List<int[]> n = Arrays.asList( // Mahdollisten siirtojen luominen
			new int[]{1, 0},
			new int[]{1, 1},
			new int[]{0, 1},
			new int[]{-1, 1},
			new int[]{-1, 0},
			new int[]{-1, -1},
			new int[]{0, -1},
			new int[]{1, -1});

	/**
	 * Uusi nappula annetulla värillä
	 * @param	vari
	 * 			Nappulan väri
	 * @param 	sarakeMax
	 * 			sarakkeiden määrä laudalla
	 * @param 	riviMax
	 * 			rivien määrä laudalla
	 */
	public Kuningatar(Vari vari, int sarakeMax, int riviMax) {
		super(vari, sarakeMax, riviMax);
	}

	public Siirrot mahdollisetSiirrot(Koordinaatti A) {
		Siirrot siirrot = new Siirrot(); // Alustetaan uusi palautettava lista siirroista

		int lr = A.annaRivi(); // lahtorivi
		int ls = A.annaSarake(); // lahtosarake

		for (int[] a:n){
			int i = n.indexOf(a); // apumuuttuja siirrot luokan ilmansuuntien hakemista varten
			int ur = a[0] + lr; // Muuttuja uudelle riville
			int us = a[1] + ls; // Muuttuja uudelle sarakkeelle
			while (ur>=0 && ur<riviMax && us>=0 && us<sarakeMax){
				Koordinaatti uk = new Koordinaatti(koordinaatit.charAt(us) +""+(ur+1)); // Luodaan uusi koordinaatti
				Siirto uS = new Siirto(A, uk);  // Jos ollaan, generoidaan uusi siirto
				siirrot.annaSuunta(i).add(uS); // ja lisätään siirtolistaan
				ur = a[0] + ur;
				us = a[1] + us;

			}
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
