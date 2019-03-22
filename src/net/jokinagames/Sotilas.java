package net.jokinagames;

import java.io.*;
import java.util.*;

public class Sotilas extends Nappula {

	public Sotilas(Vari vari) {
		super(vari);
	}

	private static final List<int[]> n = Arrays.asList( // Mahdollisten siirtojen luominen
			new int[]{1, 0}, // N
			new int[]{1, 1}, // NE
			new int[]{1, -1}); // NW


	public Siirrot mahdollisetSiirrot(Koordinaatti A) {
		Siirrot siirrot = new Siirrot(); // Alustetaan uusi palautettava lista siirroista

		int lr = A.annaRivi(); // lahtorivi
		int ls = A.annaSarake(); // lahtosarake
		int kerroin = 0;

		if (this.annaVari() == Vari.VALKOINEN){ // Apukerroin suunnan määrittämiseen, VALKOINEN 1, MUSTA -1
			kerroin = kerroin +1;
		} else {
			kerroin = kerroin -1; // S
		}
		String apu = "";
		for (int[] a:n) { // Käydään mahdollisten siirtojen lista läpi
			int ur = kerroin * a[0] + lr; // Muuttuja uudelle riville, väri huomioitu kertoimella
			int us = a[1] + ls; // Muuttuja uudelle sarakkeelle


			if (ur >= 0 && ur <= 7 && us >= 0 && us <= 7) {  // Tarkistetaan, että ollaanko laudalla ko. siirron tapauksessa


				Koordinaatti uk = new Koordinaatti(koordinaatit[0].charAt(us) + "" + koordinaatit[1].charAt(ur)); // Luodaan uusi koordinaatti
				Siirto uS = new Siirto(A, uk);  // Jos ollaan, generoidaan uusi siirto
				if (n.indexOf(a) == 0 && kerroin == 1){
					siirrot.N.add(uS);                // ja lisätään siirtolistaan
				} else if (n.indexOf(a) == 1 && kerroin == 1){
					siirrot.NE.add(uS);
				} else if (n.indexOf(a) == 2 && kerroin == 1){
					siirrot.NW.add(uS);
				} else if (n.indexOf(a) == 0 && kerroin == -1){
					siirrot.S.add(uS);
				} else if (n.indexOf(a) == 1  && kerroin == -1){
					siirrot.SE.add(uS);
				} else if (n.indexOf(a) == 2 && kerroin == -1){
					siirrot.SW.add(uS);
				}

			}
		}

		if(lr==1 && this.annaVari() == Vari.VALKOINEN) { // Tarkistetaan vielä oliko ensimmäine siirto vai ei
			Koordinaatti uK = new Koordinaatti(koordinaatit[0].charAt(ls) + "" + koordinaatit[1].charAt(lr+2));
			Siirto uS1 = new Siirto(A, uK);
			siirrot.N.add(uS1);
		} else if (lr==6 && this.annaVari() == Vari.MUSTA){
			Koordinaatti uK = new Koordinaatti(koordinaatit[0].charAt(ls) + "" + koordinaatit[1].charAt(lr-2));
			Siirto uS1 = new Siirto(A, uK);
			siirrot.S.add(uS1);
		}

		return siirrot;

	}
	public String annaNappula() {
		if (this.annaVari() == Vari.VALKOINEN) {
			return "[P]";
		} else {
			return "[p]";
		}
	}
}

