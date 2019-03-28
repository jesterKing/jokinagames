package test.jokinagames;

import net.jokinagames.*;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

/**
 * Peli-luokan testejä.
 *
 * Shakki ja mattitesteissä lähteenä käytetty mm.http://www.chessgames.com/perl/chesscollection?cid=1022048
 */
public class PeliTest {

    private Pelaaja valkoinen;
    private Pelaaja musta;
    private Peli peli;

    public void setUp() {
        Pelaaja valkoinen = new Pelaaja("valkoinen", Vari.VALKOINEN);
        Pelaaja musta = new Pelaaja("musta", Vari.MUSTA);

    }

    @Test
    public void onkoShakkiAlkuasetelma() {
        Peli peli = Peli.uusiPeli(valkoinen, musta, PortableGameNotationReader.alustaTavallinenPeli());

        boolean shakki = peli.onkoShakki(Vari.VALKOINEN);

        Assert.assertThat("Alkuasetelmassa ei ole shakkia", shakki, is(false));
    }

    @Test
    public void onkoBackRankShakki() {
        // back rank mate
        Lauta fenLauta = PortableGameNotationReader.parseFen("R5k1/5ppp/8/8/8/8/8/4K3");
        Peli peli = Peli.uusiPeli(valkoinen, musta, fenLauta);

        boolean shakki = peli.onkoShakki(Vari.VALKOINEN);
        Assert.assertThat("takarivin shakki (ja matti), kun musta kunkku motissa", shakki, is(true));
    }

    @Test
    public void onkoAnastasiasMateShakki() {
        // Anastasia's Mate
        Lauta fenLauta = PortableGameNotationReader.parseFen("8/4Nppk/8/7R/8/8/8/6K1");
        Peli peli = Peli.uusiPeli(valkoinen, musta, fenLauta);

        boolean shakki = peli.onkoShakki(Vari.VALKOINEN);
        Assert.assertThat("Anastasian matti", shakki, is(true));
    }

    @Test
    public void onkoAnastasiasMateMatti() {
        // melkein Anastasia's Mate. Huomaa että kunkku voi vielä siirtyä pois shakista.
        Lauta fenLauta = PortableGameNotationReader.parseFen("8/4Np1k/8/7R/8/8/8/6K1");
        Peli peli = Peli.uusiPeli(valkoinen, musta, fenLauta);

        boolean shakki = peli.onkoShakki(Vari.VALKOINEN);
        Assert.assertThat("Anastasian matti", shakki, is(true));

        // TODO: testaa onko matti
        //  boolean shakki = peli.onkoShakki(Vari.VALKOINEN);
        //  Assert.assertThat("Anastasian matti", shakki, is(true));
    }
}