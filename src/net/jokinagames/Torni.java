package net.jokinagames;

import java.util.*;

class Torni extends Nappula {

	/**
	 * Uusi nappula annetulla värillä
	 * @param	vari
	 * 			Nappulan väri
	 * @param 	sarakeMax
	 * 			sarakkeiden määrä laudalla
	 * @param 	riviMax
	 * 			rivien määrä laudalla
	 */
	public Torni(Vari vari, int sarakeMax, int riviMax) {
		super(vari, sarakeMax, riviMax);
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
			while (ur>=0 && ur<riviMax && us>=0 && us<sarakeMax){
				Koordinaatti uk = new Koordinaatti(koordinaatit.charAt(us) +""+(ur+1)); // Luodaan uusi koordinaatti
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