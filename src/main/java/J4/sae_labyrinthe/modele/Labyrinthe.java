package J4.sae_labyrinthe.modele;

public class Labyrinthe implements ILabyrinthe{
    private int largeur;
    private int hauteur;
    private double pourcentageMur;
    private Cellule[][] grille;
    private Cellule entree;
    private Cellule sortie;

    public Labyrinthe(int largeur,int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.grille = new Cellule[hauteur][largeur];
        this.entree = new Cellule(0,0,false);
        this.sortie = new Cellule(0,0,false);
    }

    public void setEntree(Cellule entree) {
        this.entree = entree;
    }

    public void setSortie(Cellule sortie) {
        this.sortie = sortie;
    }

}
