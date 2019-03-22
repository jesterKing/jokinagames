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

	public abstract Siirrot mahdollisetSiirrot(Koordinaatti A);

	public Vari annaVari() {
		return vari;
	}

	public String annaNappula() { return "[ ]";}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " " + annaNappula();
	}

	@Override
	public boolean equals(Object rhs) {
		if(!(rhs instanceof Nappula)) return false;

		Nappula n = (Nappula)rhs;

		return n.annaNappula().equals(this.annaNappula()) && n.annaVari().equals(this.annaVari());
	}
}