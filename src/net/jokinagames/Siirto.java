package net.jokinagames;

class Siirto {

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

	@Override
	public boolean equals(Object s) {
		if(!(s instanceof Siirto)) return false;
		Siirto ss = (Siirto)s;
		return mista.equals(ss.mista) && minne.equals(ss.minne);
	}
}
