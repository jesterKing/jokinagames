package net.jokinagames;

import java.io.*;
import java.util.*;

public class Siirto {

	private Koordinaatti A;
	private Koordinaatti B;
	public Siirto(Koordinaatti A, Koordinaatti B){
		this.A = A;
		this.B = B;
	}

	public Koordinaatti getA() {
		return A;
	}

	public Koordinaatti getB() {
		return B;
	}

	@Override
	public boolean equals(Object s) {
		if(!(s instanceof Siirto)) return false;
		Siirto ss = (Siirto)s;
		return A.equals(ss.A) && B.equals(ss.B);
	}
}
