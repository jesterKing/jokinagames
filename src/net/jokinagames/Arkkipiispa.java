package net.jokinagames;

public class Arkkipiispa extends Nappula {
    public Arkkipiispa(Vari vari, int sarakeMax, int riviMax) {
        super(vari, sarakeMax, riviMax);
    }

    @Override
    public Siirrot mahdollisetSiirrot(Koordinaatti mista) {
        Siirrot ratsu = (new Ratsu(annaVari(),sarakeMax, riviMax)).mahdollisetSiirrot(mista);
        Siirrot lahetti = (new Lahetti(annaVari(), sarakeMax, riviMax)).mahdollisetSiirrot(mista);
        Siirrot mahdolliset = new Siirrot();
        for(int i = 0; i<8; i++) {
            mahdolliset.annaSuunta(i).addAll(ratsu.annaSuunta(i));
            mahdolliset.annaSuunta(i).addAll(lahetti.annaSuunta(i));
        }
        return mahdolliset;
    }

    @Override
    public String annaNappula() {
        return annaVari()==Vari.MUSTA ? "[a]" : "[A]";
    }
}
