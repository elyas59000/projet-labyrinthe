package J4.sae_labyrinthe.modele;

import java.util.Arrays;

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

    public void genererGrilleVide(){
        for(int i = 0; i< this.hauteur;i++){
            for(int j =0;j<this.largeur;j++){
                this.grille[i][j] = new Cellule(i,j);
            }
        }
    }

    public void setEntree(Cellule entree) {
        this.entree = entree;
    }

    public void setSortie(Cellule sortie) {
        this.sortie = sortie;
    }

    @Override
    public String toString() {
        return this.grille();
    }

    public String grille(){
        String grille = "";
        for(int i = 0; i< this.hauteur;i++){
            for(int j =0;j<this.largeur;j++){
                grille = grille + this.grille[i][j];
            }
            grille = grille + "\n";
        }
        return  grille;
    }

    public static void main(String[] args) {
        Labyrinthe l = new Labyrinthe(10,10);
        l.genererGrilleVide();
        System.out.println(l);
    }
}
