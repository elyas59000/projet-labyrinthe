package J4.sae_labyrinthe.modele;

import java.io.Serializable;

public class Cellule implements Serializable {
    private int x;
    private int y;
    private boolean mur;
    private static final long serialVersionUID = 1L;

    public Cellule(int x, int y, boolean mur) {
        this.x = x;
        this.y = y;
        this.mur = mur;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean estMur() { return mur; }

    public void setMur(boolean mur) {
        this.mur = mur;
    }
}
