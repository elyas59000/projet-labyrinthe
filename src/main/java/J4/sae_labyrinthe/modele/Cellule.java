package J4.sae_labyrinthe.modele;

public class Cellule {
    private int x;
    private int y;
    private boolean mur;

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

    @Override
    public String toString() {
        if(mur){
            return "#";
        }
        else{
            return ".";
        }
    }
}
