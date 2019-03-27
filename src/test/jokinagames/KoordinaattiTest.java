package test.jokinagames;

import net.jokinagames.*;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class KoordinaattiTest {

    @Test
    public void annaSarake() {
        Koordinaatti a1 = new Koordinaatti("a1");
        Assert.assertThat(a1.annaSarake(), is(0));
        Koordinaatti h1 = new Koordinaatti("h1");
        Assert.assertThat(h1.annaSarake(), is(7));
    }

    @Test
    public void annaRivi() {
        Koordinaatti a1 = new Koordinaatti("a1");
        Assert.assertThat(a1.annaRivi(), is(0));
        Koordinaatti h1 = new Koordinaatti("h1");
        Assert.assertThat(h1.annaRivi(), is(0));
    }

    @Test
    public void annaSan() {
        Koordinaatti a1 = new Koordinaatti("a1");
        Assert.assertThat(a1.annaSan(), is("a1"));
        Koordinaatti h1 = new Koordinaatti("h1");
        Assert.assertThat(h1.annaSan(), is("h1"));
    }

    @Test
    public void yritaVirheellinenSiirto() {
        Lauta l = PortableGameNotationReader.alustaTavallinenPeli();
        try {
            Koordinaatti.luoKoordinaatit("Pa5", Vari.VALKOINEN, l);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof KoordinaattiVirhe);
        }
    }

    @Test
    public void yritaTyhjanRuudunSiirto() {
        Lauta l = PortableGameNotationReader.alustaTavallinenPeli();
        try {
            Koordinaatti.luoKoordinaatit("e3d6", Vari.VALKOINEN, l);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof KoordinaattiVirhe);
        }

    }
}