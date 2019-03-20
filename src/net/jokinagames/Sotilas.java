package net.jokinagames;

import java.io.*;
import java.util.*;

public class Sotilas extends Nappula {

	public Sotilas(Vari vari) {
		super(vari);
	}

	private static final List<int[]> n = Arrays.asList( // Mahdollisten siirtojen luominen
			new int[]{1, 0},
			new int[]{1, 1},
			new int[]{1, -1});


	public List<Siirto> mahdollisetSiirrot(Koordinaatti A) {
		List<Siirto> Siirrot = new ArrayList<>(); // Alustetaan uusi palautettava lista siirroista

		int lr = A.annaRivi(); // lahtorivi
		int ls = A.annaSarake(); // lahtosarake
		int kerroin = 0;

		if (this.annaVari() == Vari.VALKOINEN){ // Apukerroin suunnan määrittämiseen, VALKOINEN 1, MUSTA -1
			kerroin = kerroin +1;
		} else {
			kerroin = kerroin -1;
		}

		for (int[] a:n) { // Käydään mahdollisten siirtojen lista läpi
			int ur = kerroin * a[0] + lr; // Muuttuja uudelle riville, väri huomioitu kertoimella
			int us = a[1] + ls; // Muuttuja uudelle sarakkeelle
			if (ur >= 0 && ur <= 7 && us >= 0 && us <= 7) {  // Tarkistetaan, että ollaanko laudalla ko. siirron tapauksessa
				Koordinaatti uk = new Koordinaatti(koordinaatit[0].charAt(ur) + "" + koordinaatit[1].charAt(us)); // Luodaan uusi koordinaatti
				Siirto uS = new Siirto(A, uk);  // Jos ollaan, generoidaan uusi siirto
				Siirrot.add(uS);                // ja lisätään siirtolistaan
			}
		}

		if(lr==1 && this.annaVari() == Vari.VALKOINEN) { // Tarkistetaan vielä oliko ensimmäine siirto vai ei
			Koordinaatti uK = new Koordinaatti(koordinaatit[0].charAt(lr + 2) + "" + koordinaatit[1].charAt(ls));
			Siirto uS1 = new Siirto(A, uK);
			Siirrot.add(uS1);
		} else if (lr==7 && this.annaVari() == Vari.MUSTA){
			Koordinaatti uK = new Koordinaatti(koordinaatit[0].charAt(lr - 2) + "" + koordinaatit[1].charAt(ls));
			Siirto uS1 = new Siirto(A, uK);
			Siirrot.add(uS1);
		}

		return Siirrot;

	}
	public String annaNappula() {
		if (this.annaVari() == Vari.VALKOINEN) {
			return "[P]";
		} else {
			return "[p]";
		}
	}
}

