package net.jokinagames;

import java.util.List;
import java.util.ArrayList;

public class Siirrot {
    public List<Siirto> N; // ylös, eli rivi-indeksit 0-7 kasvava ... +1,0
    public List<Siirto> NE; // ylös oikealle, indeksit kasvava ... +1,+1
    public List<Siirto> E; // oikealle, eli sarake-indeksit 0-7 kasvava ... 0,+1
    public List<Siirto> SE; // alas oikealle, eli rivit laskee, mutta sarakkeet kasvaa ... -1,+1
    public List<Siirto> S; // alas, eli rivi-indeksit 7-0 laskeva ... -1,0
    public List<Siirto> SW; // alas vasemmalle, eli rivit laskee ja sarakkeet laskee ... -1,-1
    public List<Siirto> W; // vasemmalle, eli sarake-indeksit 7-0 laskeva ... 0,-1
    public List<Siirto> NW; // ylös vasemmalle ylös, rivit nouseei, sarakkeet laskee ... +1,-1

    public Siirrot() {
        N = new ArrayList<>();
        NE = new ArrayList<>();
        E = new ArrayList<>();
        SE = new ArrayList<>();
        S = new ArrayList<>();
        SW = new ArrayList<>();
        W = new ArrayList<>();
        NW = new ArrayList<>();
    }

    public boolean loytyySiirto(Siirto s) {
        for(int suunta=0; suunta<8; suunta++) {
            for (Siirto siirrot : annaSuunta(suunta)) {
                if (siirrot.equals(s)) return true;
            }
        }
        return false;
    }

    public List<Siirto> annaSuunta(int i) {
        if (i == 0) {
            return N;
        }
        if (i == 1) {
            return NE;
        }
        if (i == 2) {
            return E;
        }
        if (i == 3) {
            return SE;
        }
        if (i == 4) {
            return S;
        }
        if (i == 5) {
            return SW;
        }
        if (i == 6) {
            return W;
        }
        if (i == 7) {
            return NW;
        }
        return null;
    }
}


