package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.utils.Calcul;
import javafx.scene.shape.Rectangle;

public class VueLabyrintheEntier extends VueLabyrintheAbstraite {

    public VueLabyrintheEntier(ModeleJeu modele) {
        super(modele);
    }

    @Override
    public void initialiserAffichage() {
        Labyrinthe laby = modele.getLabyrinthe();
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
        Labyrinthe laby = modele.getLabyrinthe();
        int joueurX = modele.getPositionJoueur().getX();
        int joueurY = modele.getPositionJoueur().getY();
        int largeur = laby.getLargeur();

        for (int i = 0; i < laby.getHauteur(); i++) {
            for (int j = 0; j < largeur; j++) {
                Rectangle cell = (Rectangle) grille.getChildren().get(i * largeur + j);
                appliquerTexture(cell, laby, i, j, joueurX, joueurY);
            }
        }
    }
}
