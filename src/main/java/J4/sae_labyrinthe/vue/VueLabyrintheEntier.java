package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.utils.Calcul;
import J4.sae_labyrinthe.utils.FusionImage;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class VueLabyrintheEntier implements IObserver, IVueLabyrinthe {

    private final GridPane labEntier;
    private final ModeleJeu modele;

    private final double LARGEUR_DISPONIBLE_ZONE= 1280;

    private final double HAUTEUR_DISPONIBLE_ZONE=700;

    public VueLabyrintheEntier(ModeleJeu modele) {
        this.labEntier = new GridPane();
        this.modele = modele;
        this.modele.add(this);
    }

    /**
     * Affiche le labyrinthe avec une taille adaptée à la zone visible
     */
    public void afficherLabyrinthe(GridPane p, double largeurDispo, double hauteurDispo) {
        p.getChildren().clear();

        Labyrinthe laby = modele.getLabyrinthe();
        int lignes = laby.getHauteur();
        int colonnes = laby.getLargeur();

        double tailleCarre = Calcul.calculTailleCarreOptimal(largeurDispo, hauteurDispo, colonnes, lignes);

        Image brique = getBrique();
        Image joueur = getJoueur();
        Image fond = getFond();
        Image fin = getFin();

        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                Rectangle cell = new Rectangle(tailleCarre, tailleCarre);

                if (laby.getGrille()[i][j].estMur()) {
                    cell.setFill(new ImagePattern(brique, 0, 0, 1, 1, true));
                } else {
                    cell.setFill(new ImagePattern(fond, 0, 0, 1, 1, true));

                    if (laby.getGrille()[i][j].equals(modele.getPositionJoueur())) {
                        cell.setFill(new ImagePattern(FusionImage.fusionnerImages(fond, joueur), 0, 0, 1, 1, true));
                    } else if (laby.getGrille()[i][j].equals(laby.getSortie())) {
                        cell.setFill(new ImagePattern(FusionImage.fusionnerImages(fond, fin), 0, 0, 1, 1, true));
                    }
                }
                p.add(cell, j, i);
            }
        }
    }

    /**
     * Calcule la taille optimale d’une cellule pour que le labyrinthe
     * tienne dans la zone visible, sans déborder ni déformer.
     */

    @Override
    public void update() {
        afficherLabyrinthe(labEntier,LARGEUR_DISPONIBLE_ZONE,HAUTEUR_DISPONIBLE_ZONE);
    }

    public GridPane getLabEntier() {
        return labEntier;
    }

    public ModeleJeu getModele() {
        return modele;
    }

    @Override
    public Image getBrique() {
        return new Image(Objects.requireNonNull(getClass().getResource("/J4/sae_labyrinthe/assets/textures/Brique.png")).toExternalForm());
    }

    @Override
    public Image getJoueur() {
        return new Image(Objects.requireNonNull(getClass().getResource("/J4/sae_labyrinthe/assets/textures/souris.png")).toExternalForm());
    }

    @Override
    public Image getFond() {
        return new Image(Objects.requireNonNull(getClass().getResource("/J4/sae_labyrinthe/assets/textures/herbe.png")).toExternalForm());
    }

    @Override
    public Image getFin() {
        return new Image(Objects.requireNonNull(getClass().getResource("/J4/sae_labyrinthe/assets/textures/fromage.png")).toExternalForm());
    }
}
