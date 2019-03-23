package net.jokinagames;

import java.util.*;

class Peli {

	private Pelaaja valkoinenPelaaja;
	private Pelaaja mustaPelaaja;
	protected ArrayList<Lauta> siirrot;

	/*
		TODO:
		lisää seuraavat attribuutit:
		- tapahtuman nimi
		- pelin paikka
		- pelin pvm
	 */

	/**
	 * Luo uusi peli, johon osallistuvat pelaajat yksi ja kaksi
	 * @param	valkoinen
	 * 			valkoinen pelaaja
	 * @param	musta
	 * 			musta pelaaja
	 * @param	alku
	 * 			shakkipelin aloitusasetelma
	 * @return	uusi Peli olio
	 */
	public static Peli uusiPeli(Pelaaja valkoinen, Pelaaja musta, Lauta alku) {
		Peli p = new Peli(valkoinen, musta);
		p.siirrot.add(alku);
		return p;
	}

	/**
	 * Piilotettu konstruktori, jotta sitä ei vahingossa käytetä. Tällä pyritään
	 * varmistamaan, että Peli-olio on oikeanmuotoinen
	 * @param	valkoinen
	 * 			Valkoinen pelaaja
	 * @param	musta
	 * 			Musta pelaaja
	 */
	private Peli(Pelaaja valkoinen, Pelaaja musta) {
		siirrot = new ArrayList<>();
		valkoinenPelaaja = valkoinen;
		mustaPelaaja = musta;
	}

	/**
	 * Tee uusi siirto. Siirron tuottama uusi Lauta-olio liitetään Pelin sisäiseen
	 * siirto-listaan.
	 *
	 * @param	vuoro
	 * 			Väri kenen vuoro on
	 * @param	san
	 * 			SAN-muotoinen merkkijono
	 * @return	Lauta, joka esittää siirron jälkeistä uutta tilaa
	 */
	public Lauta seuraavaSiirto(Vari vuoro, String san) throws KoordinaattiVirhe {
		Lauta current = nykyinenTilanne();
		Siirto siirto = Koordinaatti.luoKoordinaatit(san, vuoro, current);
		Lauta uusi = current.teeSiirto(siirto.annaLahtoruutu(), siirto.annaKohderuutu());
		siirrot.add(uusi);
		return uusi;
	}

	public void tulostaNykyinenTila()
	{
		nykyinenTilanne().tulostaLauta();
	}

	/**
	 * Antaa pelin nykyisen tilan
	 * @return	Lauta, joka esittää nykyistä tilaa
	 */
	public Lauta nykyinenTilanne() {
		return siirrot.get(siirrot.size()-1);
	}

	public boolean peliOhi() { throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public boolean onkoShakki() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/**
	 * Anna valkoinen pelaaja
	 * @return	valkoinen pelaaja
	 */
	public Pelaaja annaValkoinenPelaaja()
	{
		return valkoinenPelaaja;
	}

	/**
	 * Anna musta pelaaja
	 * @return	musta pelaaja
	 */
	public Pelaaja annaMustaPelaaja()
	{
		return mustaPelaaja;
	}

}
