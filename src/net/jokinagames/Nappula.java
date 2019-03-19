package net.jokinagames;

import java.io.*;
import java.util.*;

public abstract class Nappula {

	private final Vari vari;
	static final String[] koordinaatit = {"abcdefgh", "12345678"};
	public Nappula() { vari = Vari.VALKOINEN; }

	public Nappula(Vari vari) {
		this.vari = vari;
	}

	public abstract List<Siirto> mahdollisetSiirrot(Koordinaatti A);

	public Vari annaVari() {
		return vari;
	}

	public static String annaNappula(Nappula n) {
		if (n != null) {
			if (n instanceof Sotilas) {
				if (n.annaVari() == Vari.VALKOINEN) {
					return("[P]");
				} else {
					return("[p]");
				}
			}
			if (n instanceof Ratsu) {
				if (n.annaVari() == Vari.VALKOINEN) {
					return("[N]");
				} else {
					return("[n]");
				}
			}
			if (n instanceof Torni) {
				if (n.annaVari() == Vari.VALKOINEN) {
					return("[R]");
				} else {
					return("[r]");
				}
			}
			if (n instanceof Ratsu) {
				if (n.annaVari() == Vari.VALKOINEN) {
					return("[N]");
				} else {
					return("[n]");
				}
			}
			if (n instanceof Lahetti) {
				if (n.annaVari() == Vari.VALKOINEN) {
					return("[B]");
				} else {
					return("[b]");
				}
			}
			if (n instanceof Kuningas) {
				if (n.annaVari() == Vari.VALKOINEN) {
					return("[K]");
				} else {
					return("[k]");
				}
			}
			if (n instanceof Kuningatar) {
				if (n.annaVari() == Vari.VALKOINEN) {
					return("[Q]");
				} else {
					return("[q]");
				}
			}
		}
		return("[ ]");
	}

}
