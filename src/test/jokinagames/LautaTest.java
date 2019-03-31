package test.jokinagames;

import net.jokinagames.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LautaTest {

    @Test
    public void annaNappulatJoillaSiirtoMahdollinen() {
        Lauta l = PortableGameNotationReader.parseFen("8/8/8/2pppp2/2PPPP2/8/8/8");
        try {
            Siirto s = Koordinaatti.luoKoordinaatit("xde4", Vari.MUSTA, l);
            List<Siirto> siirrot = l.annaNappulatJoillaSiirtoMahdollinen(s.annaKohderuutu(), new Sotilas(Vari.MUSTA, 8, 8));
            Assert.assertThat("kaksi voi syödä", siirrot.size(), is(2));
            Nappula n = l.annaNappula(s.annaLahtoruutu());
            Assert.assertThat("Nappula on sotilas", n instanceof Sotilas, is(true));
            Siirrot mahdolliset = n.mahdollisetSiirrot(s.annaLahtoruutu());
            Assert.assertThat("Musta sotilas voisi teoriassa kolmeen suuntaan mennä", mahdolliset.siirrotYhteensa(), is(3));
            Siirrot sallitut = l.sallitutSiirrot(mahdolliset);
            Assert.assertThat("Asetelmassa musta voi siirtyä vain kahteen suuntaan - syödä valkoiset", sallitut.siirrotYhteensa(), is(2));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertThat("Odottamaton virhe", false, is(true));
        }
    }

    @Test
    public void sotilasEiVoiLiikkuaSuoraanVastustajanPaalle()
    {
        Lauta l = PortableGameNotationReader.parseFen("8/8/8/3p4/3P4/8/8/8");
        try {
            Siirto s = new Siirto(new Koordinaatti("d5"), new Koordinaatti("d4"));
            Nappula n = l.annaNappula(s.annaLahtoruutu());
            Siirrot mahdolliset = n.mahdollisetSiirrot(s.annaLahtoruutu());
            Assert.assertThat("Musta sotilas voisi teoriassa kolmeen suuntaan mennä", mahdolliset.siirrotYhteensa(), is(3));
            Siirrot sallitut = l.sallitutSiirrot(mahdolliset);
            Assert.assertThat("Asetelmassa musta ei voi siirtyä", sallitut.siirrotYhteensa(), is(0));
            s = new Siirto(new Koordinaatti("d4"), new Koordinaatti("d5"));
            n = l.annaNappula(s.annaLahtoruutu());
            mahdolliset = n.mahdollisetSiirrot(s.annaLahtoruutu());
            Assert.assertThat("Valkoinen sotilas voisi teoriassa kolmeen suuntaan mennä", mahdolliset.siirrotYhteensa(), is(3));
            sallitut = l.sallitutSiirrot(mahdolliset);
            Assert.assertThat("Asetelmassa valkoinen ei voi siirtyä", sallitut.siirrotYhteensa(), is(0));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertThat("Odottamaton virhe", false, is(true));
        }

    }

    @Test
    public void annaKaikkiKoordinaatit() {
    }

    @Test
    public void etsiKuningas() {
    }

    @Test
    public void annaNappula() {
    }

    @Test
    public void annaFen() {
        Lauta l = PortableGameNotationReader.alustaTavallinenPeli();
        String fen = l.annaFen();
        Assert.assertThat("FEN-esitykset sama", fen, is(PortableGameNotationReader.perusFen.split(" ")[0]));
        l = PortableGameNotationReader.alustaGrandChessPeli();
        fen = l.annaFen();
        Assert.assertThat("FEN-esitykset sama", fen, is(PortableGameNotationReader.grandChessFen.split(" ")[0]));
    }
}