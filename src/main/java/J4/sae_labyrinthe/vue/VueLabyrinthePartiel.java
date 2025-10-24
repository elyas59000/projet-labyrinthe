package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.utils.Calcul;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class VueLabyrinthePartiel extends VueLabyrintheAbstraite {

    private final int range;
    private final GridPane minimap;

    public VueLabyrinthePartiel(ModeleJeu modele, int range) {
        super(modele);
        this.range = range;
        this.minimap = new GridPane();
    }

    @Override
    public void initialiserAffichage() {
        int taille = 2 * range + 1;
        double tailleCarre = Calcul.calculTailleCarreOptimal(
                LARGEUR_DISPONIBLE_ZONE, HAUTEUR_DISPONIBLE_ZONE, taille, taille
        );
        parcoursCreationGridPane(grille,taille,taille, tailleCarre);
        this.rafraichirAffichage();
        afficherMap(minimap);
    }

    @Override
    protected void rafraichirAffichage() {
        Labyrinthe laby = modele.getLabyrinthe();
        int joueurX = modele.getPositionJoueur().getX();
        int joueurY = modele.getPositionJoueur().getY();
        int taille = 2 * range + 1;

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                int labI = joueurY - range + i;
                int labJ = joueurX - range + j;
                Rectangle cell = (Rectangle) grille.getChildren().get(i * taille + j);
                appliquerTexture(cell, laby, labI, labJ, joueurX, joueurY);
            }
        }
    }

    private void afficherMap(GridPane p) {
        Labyrinthe laby = modele.getLabyrinthe();
        p.getChildren().clear();
        double tailleCarreMini = 10;

        for (int i = 0; i < laby.getHauteur(); i++) {
            for (int j = 0; j < laby.getLargeur(); j++) {
                Rectangle cell = new Rectangle(tailleCarreMini, tailleCarreMini);
                appliquerTexture(cell, laby, i, j, -1, -1);
                p.add(cell, j, i);
            }
        }
    }

    public GridPane getMinimap() {
        return minimap;
    }
}
