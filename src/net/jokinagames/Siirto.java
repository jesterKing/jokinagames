package net.jokinagames;

import java.io.*;
import java.util.*;

public class Siirto {

	private Koordinaatti A;
	private Koordinaatti B;

	/**
	 * Siirto lähtöruudusta A kohderuutuun B
	 * @param	A
	 * 			lähtöruutu
	 * @param	B
	 * 			kohderuutu
	 */
	public Siirto(Koordinaatti A, Koordinaatti B){
		this.A = A;
		this.B = B;
	}

	/**
	 * Anna lähtöruudun koordinaatti
	 * @return	lähtöruudun koordinaatti
	 */
	public Koordinaatti annaLahtoruutu() {
		return A;
	}

	/**
	 * Anna kohderuudun koordinaatti
	 * @return	kohderuudun koordinaatti
	 */
	public Koordinaatti annaKohderuutu() {
		return B;
	}

	@Override
	public boolean equals(Object s) {
		if(!(s instanceof Siirto)) return false;
		Siirto ss = (Siirto)s;
		return A.equals(ss.A) && B.equals(ss.B);
	}
}
