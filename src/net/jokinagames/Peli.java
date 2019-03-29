package net.jokinagames;

import java.text.SimpleDateFormat;
import java.util.*;

public class Peli {

	private Pelaaja valkoinenPelaaja;
	private Pelaaja mustaPelaaja;
	private Tulos tulos;
	protected ArrayList<Lauta> siirrot;
	protected ArrayList<String> sansiirrot;

	private String pvm;
	private String aika;

	private int kokoVuoro;
	private int puoliVuoro;
	private Vari pelaajanVuoro;

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
		sansiirrot = new ArrayList<>();
		valkoinenPelaaja = valkoinen;
		mustaPelaaja = musta;
		tulos = Tulos.KESKEN;
		kokoVuoro = 1;
		puoliVuoro = 1;
		pelaajanVuoro = Vari.VALKOINEN;
		pvm = new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime());
		aika = new SimpleDateFormat("HH-mm-ss").format(Calendar.getInstance().getTime());
	}

	/**
	 * Tee uusi siirto. Siirron tuottama uusi Lauta-olio liitetään Pelin sisäiseen
	 * siirto-listaan.
	 *
	 * @param	vuoro
	 * 			Väri kenen kokoVuoro on
	 * @param	san
	 * 			SAN-muotoinen merkkijono
	 * @return	Lauta, joka esittää siirron jälkeistä uutta tilaa
	 * @throws	VuoroVirhe
	 * @throws	KoordinaattiVirhe
	 * @throws	PelkkaKohderuutuEiRiita
	 * @throws	KohderuutuJaLahtosarakeEiRiita
	 */
	public Lauta seuraavaSiirto(Vari vuoro, String san) throws VuoroVirhe, KoordinaattiVirhe, PelkkaKohderuutuEiRiita, KohderuutuJaLahtosarakeEiRiita {
		if(vuoro!=this.pelaajanVuoro) {
			throw new VuoroVirhe(vuoro + " ei ole vuorossa");
		}
		Lauta current = nykyinenTilanne();
		Siirto siirto = Koordinaatti.luoKoordinaatit(san, vuoro, current);
		Lauta uusi = current.teeSiirto(siirto.annaLahtoruutu(), siirto.annaKohderuutu());
		siirrot.add(uusi);
		sansiirrot.add(san);
		puoliVuoro++;
		if(pelaajanVuoro==Vari.MUSTA) {
			this.kokoVuoro++;
		}
		pelaajanVuoro = pelaajanVuoro==Vari.MUSTA ? Vari.VALKOINEN : Vari.MUSTA;
		return uusi;
	}

	/**
	 * Tulosta ASCII-muotoinen esitys nykyisestä pelitilanteesta viimeisimmän siirron jälkeen.
	 * @author	Nathan Letwory
	 */
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

	public boolean peliOhi() {
		return annaTulos()!=Tulos.KESKEN;
	}

	/**
	 *
	 * @author Kimmo Hildén
	 * @param v
	 * @return
	 */
	public boolean onkoShakki(Vari v) {
		Lauta L = this.nykyinenTilanne();

		if (L.annaKaikkiKoordinaatit(L, v).contains(L.etsiKuningas(L,v))){
			return true;
		} else {
			return false;
		}
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

	/**
	 * Anna pelin aloituspäivämäärä muodossa <code>"2019.03.29"</code>.
	 * @return	pelin aloituspäivämäärä
	 * @author	Nathan Letwory
	 */
	public String annaPaivamaara() {
		return pvm;
	}

	/**
	 * Anna pelin aloitusaika muodossa <code>"08-24-12"</code>.
	 * @return	pelin aloitusaika
	 * @author	Nathan Letwory
	 */
	public String annaAika() {
		return aika;
	}

	/**
	 * Antaa alkuasetelman FEN-muodossa
	 * @return	alkuasetelma FEN-muodossa
	 * @author	Nathan Letwory
	 */
	public String annaAloitusFen() {
		return siirrot.get(0).annaFen();
	}

	/**
	 * Antaa pelaajan värin, jonka vuoro nyt on.
	 * @return	Vari
	 * @author	Nathan Letwory
	 */
	public Vari annaPelaajanVuoro() {
		return pelaajanVuoro;
	}

	public Pelaaja annaVuorossoOlevaPelaaja() {
		return annaPelaajanVuoro()==Vari.MUSTA ? annaMustaPelaaja() : annaValkoinenPelaaja();
	}

	/**
	 * Antaa pelin vuoron numeron.
	 * @return	käynnissä olevan vuoron numero.
	 */
	public int annaKokoVuoro() {
		return kokoVuoro;
	}

	/**
	 * Antaa pelin puolivuoron;
	 * @return	käynnissä olevan puolivuoron numero.
	 */
	public int annaPuoliVuoro() {
		return puoliVuoro;
	}


	/**
	 * Tuloksen ilmaisua varten
	 *
	 * @author Kimmo Hildén
 	 */
	public enum Tulos{
		KESKEN,
		TASAPELI,
		MUSTA_VOITTI,
		VALKOINEN_VOITTI
	}

	/**
	 * Aseta tulos
	 * @param t
	 * @author Kimmo Hildén
	 */
	public void asetaTulos(Tulos t){
		this.tulos = t;

	}

	/**
	 * Anna pelin tulos
	 * @return Tulos
	 * @author Kimmo Hildén
	 */
	public Tulos annaTulos(){
		return this.tulos;
	}

	/**
	 * Tuloksen perusteella oikea merkkijono.
	 * {@code
	 * TASAPELI = "1/2-1/2";
	 * MUSTA_VOITTI = "0-1";
	 * VALKOINEN_VOITTI = "1-0";
	 * KESKEN = "*";
	 * }
	 * @return merkkijono
	 * @author Nathan Letwory
	 */
	public String annaTulosToString() {
	    String t;
		switch(annaTulos()) {
			case TASAPELI:
				t = "1/2-1/2";
				break;
			case MUSTA_VOITTI:
				t = "0-1";
				break;
			case VALKOINEN_VOITTI:
				t = "1-0";
				break;
			default:
				t = "*";
				break;
		}
		return t;
	}
}
