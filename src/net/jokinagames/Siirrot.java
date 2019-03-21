package net.jokinagames;

import java.util.List;
import java.util.ArrayList;

public class Siirrot {
    private List<Siirto> N; // ylös, eli rivi-indeksit 0-7 kasvava ... +1,0
    private List<Siirto> NE; // ylös oikealle, indeksit kasvava ... +1,+1
    private List<Siirto> E; // oikealle, eli sarake-indeksit 0-7 kasvava ... 0,+1
    private List<Siirto> SE; // alas oikealle, eli rivit laskee, mutta sarakkeet kasvaa ... -1,+1
    private List<Siirto> S; // alas, eli rivi-indeksit 7-0 laskeva ... -1,0
    private List<Siirto> SW; // alas vasemmalle, eli rivit laskee ja sarakkeet laskee ... -1,-1
    private List<Siirto> W; // vasemmalle, eli sarake-indeksit 7-0 laskeva ... 0,-1
    private List<Siirto> NW; // ylös vasemmalle ylös, rivit nouseei, sarakkeet laskee ... +1,-1

    public Siirrot(){
        N = new ArrayList<Siirto>();
        NE = new ArrayList<Siirto>();
        E =  new ArrayList<Siirto>();
        SE =  new ArrayList<Siirto>();
        S =  new ArrayList<Siirto>();
        SW =  new ArrayList<Siirto>();
        W =  new ArrayList<Siirto>();
        NW =  new ArrayList<Siirto>();
    }
}
