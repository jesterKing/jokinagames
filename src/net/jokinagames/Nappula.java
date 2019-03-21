package net.jokinagames;

import java.io.*;
import java.util.*;

public abstract class Nappula {

	private final Vari vari;
	static final String[] koordinaatit = {"abcdefgh", "87654321"};
	public Nappula() { vari = Vari.VALKOINEN; }

	public Nappula(Vari vari) {
		this.vari = vari;
	}

	public abstract Siirrot mahdollisetSiirrot(Koordinaatti A);

	public Vari annaVari() {
		return vari;
	}

	public String annaNappula() { return "[ ]";}
}