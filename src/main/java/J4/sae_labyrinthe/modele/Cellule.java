package J4.sae_labyrinthe.modele;

public class Cellule {
    private int x;
    private int y;
    private boolean mur;
    private boolean accessible;

    public Cellule(int x, int y, boolean mur) {
        this.x = x;
        this.y = y;
        this.mur = mur;
        this.accessible = true;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean estMur() { return mur; }
    public boolean estAccessible() { return accessible; }

    public void setMur(boolean mur) {
        this.mur = mur;
    }
    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
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
