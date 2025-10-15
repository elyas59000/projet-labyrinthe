package J4.sae_labyrinthe.modele;


public class Etape implements IEtape{
    private final int NUM;
    private Defi[] defis;

    public Etape(int num,int largeur, int hauteur){
        this.NUM = num;
        defis = new Defi[3];
        genererDefis(largeur, hauteur);
    }

    private void genererDefis(int largeur, int hauteur) {
        defis[0] = new Defi("Facile", largeur, hauteur, 20);
        defis[1] = new Defi("Moyen", largeur, hauteur, 30);
        defis[2] = new Defi("Difficile", largeur, hauteur, 50);
    }

    public boolean aReussiUnDefi() {
        for (Defi d : defis) {
            if (d.estReussi()) return true;
        }
        return false;
    }

    public int getNumero() { return NUM; }
    public Defi[] getDefis() { return defis; }
}
