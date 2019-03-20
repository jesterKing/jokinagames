package net.jokinagames;

import java.io.*;
import java.util.*;

public class Torni extends Nappula {

	public Torni(Vari vari) {

		super(vari);
	}

	private static final List<int[]> n = Arrays.asList( // Mahdollisten siirtojen luominen
			new int[]{1, 0},
			new int[]{-1, 0},
			new int[]{0, 1},
			new int[]{0, -1});

	public List<Siirto> mahdollisetSiirrot(Koordinaatti A) {
		List<Siirto> Siirrot = new ArrayList<>(); // Alustetaan uusi palautettava lista siirroista

		int lr = A.annaRivi(); // lahtorivi
		int ls = A.annaSarake(); // lahtosarake

		for (int[] a:n){
			int ur = a[0] + lr; // Muuttuja uudelle riville
			int us = a[1] + ls; // Muuttuja uudelle sarakkeelle
			while (ur>=0 && ur<=7 && us>=0 && us<=7){
				Koordinaatti uk = new Koordinaatti(koordinaatit[0].charAt(ur) +""+koordinaatit[1].charAt(us)); // Luodaan uusi koordinaatti
				Siirto uS = new Siirto(A, uk);  // Jos ollaan, generoidaan uusi siirto
				Siirrot.add(uS);
				ur = a[0] + ur;
				us = a[1] + us;
			}
		}
		return Siirrot;
	}
    public String annaNappula() {
        if (this.annaVari() == Vari.VALKOINEN) {
            return "[R]";
        } else {
            return "[r]";
        }
    }
}