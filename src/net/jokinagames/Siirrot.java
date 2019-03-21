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
}

