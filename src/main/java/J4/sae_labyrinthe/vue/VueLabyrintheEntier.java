package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.ILabyrinthe;
import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.LabyrintheParfait;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.utils.Calcul;
import J4.sae_labyrinthe.utils.FusionImage;
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
        int largeurAff = largeur * 2 - 1;
        int hauteurAff = hauteur * 2 - 1;

        double tailleCarre = Calcul.calculTailleCarreOptimal(
                LARGEUR_DISPONIBLE_ZONE, HAUTEUR_DISPONIBLE_ZONE, largeurAff, hauteurAff
        );

        parcoursCreationGridPane(grille, largeurAff, hauteurAff, tailleCarre);

        for (int i = 0; i < hauteurAff; i++) {
            for (int j = 0; j < largeurAff; j++) {
                Rectangle cell = (Rectangle) grille.getChildren().get(i * largeurAff + j);
                if (i % 2 == 0 && j % 2 == 0) {
                    int ci = i / 2;
                    int cj = j / 2;
                    if (ci == joueurY && cj == joueurX) {
                        cell.setFill(new ImagePattern(FusionImage.fusionnerImages(fond, joueur)));
                    } else if (laby.getGrille()[ci][cj].equals(laby.getSortie())) {
                        cell.setFill(new ImagePattern(FusionImage.fusionnerImages(fond, fin)));
                    } else {
                        cell.setFill(new ImagePattern(fond));
                    }
                }

                // CASE MUR HORIZONTAL
                else if (i % 2 == 1 && j % 2 == 0) {
                    int ci = i / 2;
                    int cj = j / 2;
                    if (laby.getMurHorizontaux()[ci][cj].estMur()) {
                        cell.setFill(new ImagePattern(brique));
                    } else {
                        cell.setFill(new ImagePattern(fond));
                    }
                }

                // CASE MUR VERTICAL
                else if (i % 2 == 0 && j % 2 == 1) {
                    int ci = i / 2;
                    int cj = j / 2;
                    if (laby.getMurVerticaux()[cj][ci].estMur()) {
                        cell.setFill(new ImagePattern(brique));
                    } else {
                        cell.setFill(new ImagePattern(fond));
                    }
                }

                // CASE INTERSECTION (toujours un mur)
                else {
                    cell.setFill(new ImagePattern(brique));
                }
            }
        }
    }


    @Override
    protected void rafraichirAffichage() {
        ILabyrinthe laby = modele.getLabyrinthe();

        // Sélection automatique selon le type réel du labyrinthe
        if (laby instanceof LabyrintheParfait) {
            rafraichirAffichageParfait();
            return;
        }
        Labyrinthe lab = (Labyrinthe) laby; // Cast sûr à ce stade
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
