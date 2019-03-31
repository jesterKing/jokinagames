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
            Siirto s = Koordinaatti.luoKoordinaatit("dxe4", Vari.MUSTA, l);
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
    public void varmistaSotilaanAloitussiirrot()
    {
        Lauta l = PortableGameNotationReader.alustaTavallinenPeli();
        try {
            Siirto s = Koordinaatti.luoKoordinaatit("e4", Vari.VALKOINEN, l);
            Assert.assertThat("Aloitusruutu on e2", s.annaLahtoruutu(), is(new Koordinaatti("e2")));
            Assert.assertThat("Kohderuutu on e4", s.annaKohderuutu(), is(new Koordinaatti("e4")));
            s = Koordinaatti.luoKoordinaatit("a5", Vari.MUSTA, l);
            Assert.assertThat("Aloitusruutu on a7", s.annaLahtoruutu(), is(new Koordinaatti("a7")));
            Assert.assertThat("Kohderuutu on a5", s.annaKohderuutu(), is(new Koordinaatti("a5")));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertThat("Odottamaton virhe", false, is(true));
        }

        l = PortableGameNotationReader.alustaGrandChessPeli();
        try {
            Siirto s = Koordinaatti.luoKoordinaatit("i5", Vari.VALKOINEN, l);
            Assert.assertThat("Aloitusruutu on i3", s.annaLahtoruutu(), is(new Koordinaatti("i3")));
            Assert.assertThat("Kohderuutu on i5", s.annaKohderuutu(), is(new Koordinaatti("i5")));
            s = Koordinaatti.luoKoordinaatit("j6", Vari.MUSTA, l);
            Assert.assertThat("Aloitusruutu on j8", s.annaLahtoruutu(), is(new Koordinaatti("j8")));
            Assert.assertThat("Kohderuutu on j6", s.annaKohderuutu(), is(new Koordinaatti("j6")));
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
    public void annaFen() {
        Lauta l = PortableGameNotationReader.alustaTavallinenPeli();
        String fen = l.annaFen();
        Assert.assertThat("FEN-esitykset sama", fen, is(PortableGameNotationReader.perusFen.split(" ")[0]));
        l = PortableGameNotationReader.alustaGrandChessPeli();
        fen = l.annaFen();
        Assert.assertThat("FEN-esitykset sama", fen, is(PortableGameNotationReader.grandChessFen.split(" ")[0]));
    }
}