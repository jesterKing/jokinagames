package net.jokinagames;

import static java.lang.Math.abs;

public class Siirto {

	private Koordinaatti mista;
	private Koordinaatti minne;

	/**
	 * Siirto lähtöruudusta mista kohderuutuun minne
	 * @param	mista
	 * 			lähtöruutu
	 * @param	minne
	 * 			kohderuutu
	 */
	public Siirto(Koordinaatti mista, Koordinaatti minne){
		this.mista = mista;
		this.minne = minne;
	}

	/**
	 * Anna lähtöruudun koordinaatti
	 * @return	lähtöruudun koordinaatti
	 */
	public Koordinaatti annaLahtoruutu() {
		return mista;
	}

	/**
	 * Anna kohderuudun koordinaatti
	 * @return	kohderuudun koordinaatti
	 */
	public Koordinaatti annaKohderuutu() {
		return minne;
	}

	/**
	 * Kertoo edustaako Siirto ratsuhyppyä.
	 * @return	true jos ratsuhyppy
	 */
	public boolean onkoRatsuHyppy() {
		int sardelta = abs(minne.annaSarake()-mista.annaSarake());
		int rivdelta = abs(minne.annaRivi()-mista.annaRivi());

		return sardelta==2 && rivdelta==1 || sardelta==1 && rivdelta==2;
	}

	/**
	 * Kertoo edustaako Siirto yhtä ruutua vinoon.
	 * @return	true jos siirto on yksi ruutu vinoon
	 */
	public boolean onkoYksiViistoon() {
		int sardelta = abs(minne.annaSarake()-mista.annaSarake());
		int rivdelta = abs(minne.annaRivi()-mista.annaRivi());

		return sardelta==1 && rivdelta==1;
	}

	@Override
	public boolean equals(Object s) {
		if(!(s instanceof Siirto)) return false;
		Siirto ss = (Siirto)s;
		return mista.equals(ss.mista) && minne.equals(ss.minne);
	}

	@Override
	public String toString() {
		return "Siirto " + annaLahtoruutu().toString() + " " + annaKohderuutu().toString() + " onkoYksiViistoon: "+onkoYksiViistoon()+ " onkoRatsuHyppy: " + onkoRatsuHyppy();
	}
}
