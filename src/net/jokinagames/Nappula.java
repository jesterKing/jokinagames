package net.jokinagames;

import java.io.*;
import java.util.*;

public abstract class Nappula {

	private final Vari vari;

	public Nappula() { vari = Vari.VALKOINEN; }

	public Nappula(Vari vari) {
		this.vari = vari;
	}

	public abstract List<Siirto> mahdollisetSiirrot();

	public Vari annaVari() {
		return vari;
	}

}
