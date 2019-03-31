package net.jokinagames;

/**
 * Nappula, joka liikkuu kuin ratsu tai torni.
 */
public class Kansleri extends Nappula {
    /**
     * Uusi nappula annetulla värillä
     * @param	vari
     * 			Nappulan väri
     * @param 	sarakeMax
     * 			sarakkeiden määrä laudalla
     * @param 	riviMax
     * 			rivien määrä laudalla
     */
    public Kansleri(Vari vari, int sarakeMax, int riviMax) {
        super(vari, sarakeMax, riviMax);
    }

    @Override
    public Siirrot mahdollisetSiirrot(Koordinaatti mista) {
        // ratsu + torni
        Siirrot ratsu = (new Ratsu(annaVari(), sarakeMax, riviMax)).mahdollisetSiirrot(mista);
        Siirrot torni = (new Torni(annaVari(), sarakeMax, riviMax)).mahdollisetSiirrot(mista);
        Siirrot mahdolliset = new Siirrot();
        for(int i = 0; i<8; i++) {
            mahdolliset.annaSuunta(i).addAll(ratsu.annaSuunta(i));
            mahdolliset.annaSuunta(i).addAll(torni.annaSuunta(i));
        }
        return mahdolliset;
    }

    @Override
    public String annaNappula() {
        return annaVari()==Vari.MUSTA ? "[c]" : "[C]";
    }
}
