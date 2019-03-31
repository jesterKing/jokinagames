package net.jokinagames;

public class Pelaaja {

	private final Vari vari;
	private final String nimi;

	/**
	 * Uusi pelaaja annetulla nimellä ja värillä
	 * @param	nimi
	 * 			pelaajan nimi
	 * @param	vari
	 * 			pelaajan väri
	 */
	public Pelaaja(String nimi, Vari vari) {
	    this.nimi = nimi;
	    this.vari = vari;
	}

	/**
	 * Anna pelaajan nimi
	 * @return	pelaajan nimi
	 */
	public String annaNimi() {
	    return nimi;
	}

	/**
	 * Anna pelaajan väri
	 * @return	pelaajan väri
	 */
	public Vari annaVari() {
		return vari;
	}

}
