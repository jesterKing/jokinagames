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

	public Lauta seuraavaSiirto() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public boolean peliOhi() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public boolean onkoShakki() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
