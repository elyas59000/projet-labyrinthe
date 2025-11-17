package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.*;
import J4.sae_labyrinthe.utils.Calcul;
import J4.sae_labyrinthe.utils.FusionImage;
import javafx.scene.control.Cell;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
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
        this.rafraichirAffichage();
    }


    @Override
    protected void rafraichirAffichage() {
        ILabyrinthe lab = modele.getLabyrinthe();
        int joueurX = modele.getPositionJoueur().getX();
        int joueurY = modele.getPositionJoueur().getY();
        int largeur = lab.getLargeur();

        for (int j = 0; j < lab.getHauteur(); j++) {
            for (int i = 0; i < largeur; i++) {
                Rectangle cell = (Rectangle) grille.getChildren().get(j * largeur + i);
                appliquerTexture(cell, lab, j, i, joueurY, joueurX);
            }
        }
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
