package J4.sae_labyrinthe.modele;

import java.util.ArrayList;
import java.util.List;

public class Etape implements IEtape{
    private final int NUM;
    private List<Defi> defis;

    public Etape(int num){
        this.NUM = num;
        this.defis = new ArrayList<>();
    }
}
