package net.jokinagames;

abstract class Nappula {

	private final Vari vari;
	static final String[] koordinaatit = {"abcdefgh", "12345678"};

	/**
	 * Uusi nappula annetulla värillä
	 * @param	vari
	 * 			Nappulan väri
	 */
	public Nappula(Vari vari) {
		this.vari = vari;
	}

	/**
	 * Anna nappulan mahdolliset siirrot lähtöruudusta tyhjää lautaa ajatellen
	 * @param	mista
	 * 			Lähtöruudun koordinaatti
	 * @return	Siirrot-olio, johon tallennettu kaikki mahdolliset siirrot tyhjällä laudalla
	 */
	public abstract Siirrot mahdollisetSiirrot(Koordinaatti mista);

	/**
	 * Anna nappulan väri
	 * @return	Nappulan väri
	 */
	public Vari annaVari() {
		return vari;
	}

	/**
	 * Anna nappulan graafisen esityksen merkkijono
	 * @return	Nappulan graafisen esityksen merkkijono
	 */
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