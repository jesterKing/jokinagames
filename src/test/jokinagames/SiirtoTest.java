package test.jokinagames;

import net.jokinagames.*;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SiirtoTest {

    @Test
    public void onkoRatsuHyppy() {
        Lauta l = PortableGameNotationReader.alustaTavallinenPeli();
        try {
            Siirto siirto = Koordinaatti.luoKoordinaatit("Nc3", Vari.VALKOINEN, l);
            Assert.assertThat("Nb3 on ratsuhyppy", siirto.onkoRatsuHyppy(), is(true));

            siirto = Koordinaatti.luoKoordinaatit("e4", Vari.VALKOINEN, l);
            Assert.assertThat("e4 ei ole ratsuhyppy", siirto.onkoRatsuHyppy(), is(false));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertThat("Odottamaton virhe", false, is(true));
        }
    }

    @Test
    public void onkoYksiViistoon() {
        Lauta l = PortableGameNotationReader.parseFen("8/8/8/2pppp2/2PPPP2/8/8/8");
        try {
            Siirto siirto = Koordinaatti.luoKoordinaatit("c5d4", Vari.MUSTA, l);
            Assert.assertThat("c5d4", siirto.onkoYksiViistoon(), is(true));
            siirto = Koordinaatti.luoKoordinaatit("c5d5", Vari.MUSTA, l);
            Assert.assertThat("c5d5", siirto.onkoYksiViistoon(), is(false));

        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertThat("Odottamaton virhe", false, is(true));
        }
    }
}