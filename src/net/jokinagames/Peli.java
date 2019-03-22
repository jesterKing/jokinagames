package net.jokinagames;

import java.io.*;
import java.util.*;

public class Peli {

	private Pelaaja pelaaja1;
	private Pelaaja pelaaja2;
	protected ArrayList<Lauta> siirrot;

	/*
		TODO:
		lisää seuraavat attribuutit:
		- tapahtuman nimi
		- pelin paikka
		- pelin pvm
	 */

	public static Peli uusiPeli(Pelaaja yksi, Pelaaja kaksi, Lauta alku) {
	    Peli p = new Peli(yksi, kaksi);
	    p.siirrot.add(alku);
	    return p;
	}

	private Peli(Pelaaja yksi, Pelaaja kaksi) {
		siirrot = new ArrayList<>();
	    pelaaja1 = yksi;
	    pelaaja2 = kaksi;
	}

	/**
	 * Tee uusi siirto. Siirron tuottama uusi Lauta-olio liitetään Pelin sisäiseen
	 * siirto-listaan.
	 *
	 * @param	vuoro
	 * 			Väri kenen vuoro on
	 * @param	san
	 * 			SAN-muotoinen merkkijono
	 * @return	Lauta, joka esittää siirron jälkeistä uutta tilaa
	 */
	public Lauta seuraavaSiirto(Vari vuoro, String san) throws KoordinaattiVirhe {
	    Lauta current = siirrot.get(siirrot.size()-1);
	    Koordinaatti[] kds = Koordinaatti.luoKoordinaatit(san, vuoro, current);
	    Lauta uusi = current.teeSiirto(kds[0], kds[1]);
	    siirrot.add(uusi);
	    return uusi;
	}

	public boolean peliOhi() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public boolean onkoShakki() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
