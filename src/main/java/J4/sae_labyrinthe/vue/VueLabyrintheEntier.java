package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.*;
import J4.sae_labyrinthe.utils.Calcul;
import J4.sae_labyrinthe.utils.FusionImage;
import javafx.scene.control.Cell;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class VueLabyrintheEntier extends VueLabyrintheAbstraite {

    public VueLabyrintheEntier(ModeleJeu modele) {
        super(modele);
    }

    @Override
    public void initialiserAffichage() {
        ILabyrinthe laby = modele.getLabyrinthe();
        int hauteur = laby.getHauteur();
        int largeur = laby.getLargeur();

        double tailleCarre = Calcul.calculTailleCarreOptimal(
                LARGEUR_DISPONIBLE_ZONE, HAUTEUR_DISPONIBLE_ZONE, largeur, hauteur
        );
        parcoursCreationGridPane(grille,largeur,hauteur, tailleCarre);
        if(laby instanceof Labyrinthe) this.rafraichirAffichage();
        if(laby instanceof LabyrintheParfait) this.rafraichirAffichageParfait();
    }

    private void rafraichirAffichageParfait() {
        LabyrintheParfait laby = (LabyrintheParfait) modele.getLabyrinthe();
        int joueurX = modele.getPositionJoueur().getX();
        int joueurY = modele.getPositionJoueur().getY();

        int largeur = laby.getLargeur();
        int hauteur = laby.getHauteur();

        double tailleCarre = Calcul.calculTailleCarreOptimal(
                LARGEUR_DISPONIBLE_ZONE, HAUTEUR_DISPONIBLE_ZONE, hauteur, largeur        );
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                Rectangle cell = (Rectangle) grille.getChildren().get(i * largeur + j);
                appliquerTexture(cell, laby, i, j, joueurX, joueurY);
            }
        }
    }


    @Override
    protected void rafraichirAffichage() {
        ILabyrinthe laby = modele.getLabyrinthe();
        if (laby instanceof LabyrintheParfait) {
            rafraichirAffichageParfait();
        }else{
            rafraichirAleatoire();
        }
        return;
    }

    protected void rafraichirAleatoire(){
        ILabyrinthe lab =  modele.getLabyrinthe();
        int joueurX = modele.getPositionJoueur().getX();
        int joueurY = modele.getPositionJoueur().getY();
        int largeur = lab.getLargeur();

        for (int i = 0; i < lab.getHauteur(); i++) {
            for (int j = 0; j < largeur; j++) {
                Rectangle cell = (Rectangle) grille.getChildren().get(i * largeur + j);
                appliquerTexture(cell, lab, i, j, joueurX, joueurY);
            }
        }
    }

}
