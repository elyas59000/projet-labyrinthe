package J4.sae_labyrinthe.vue;


import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.utils.Calcul;
import J4.sae_labyrinthe.utils.FusionImage;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

import static J4.sae_labyrinthe.utils.FusionImage.fusionnerImages;

public class VueLabyrinthePartiel implements IObserver {
    private GridPane labPartiel;

    private GridPane minimap;

    private final ModeleJeu modele;
    private final int range;

    private final double LARGEUR_DISPONIBLE_ZONE= 1280;

    private final double HAUTEUR_DISPONIBLE_ZONE=700;

    public VueLabyrinthePartiel(ModeleJeu modele, int range) {
        this.labPartiel = new GridPane();
        this.minimap = new GridPane();
        this.modele=modele;
        this.modele.add(this);
        this.range=range;
    }

    @Override
    public void update() {
        afficherMap(minimap);
        afficherLabyrinthePartiel(labPartiel,LARGEUR_DISPONIBLE_ZONE,HAUTEUR_DISPONIBLE_ZONE,range);
    }

    public void afficherMap(GridPane p) {
        p.getChildren().clear();
        Labyrinthe laby = modele.getLabyrinthe();
        int hauteur = laby.getHauteur();
        int largeur = laby.getLargeur();

        // Taille fixe plus petite que la zone du labyrinthe
        double tailleCarre = 10; // tu peux ajuster

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                Rectangle cell = new Rectangle(tailleCarre, tailleCarre);

                if (laby.getGrille()[i][j].estMur()) {
                    cell.setFill(Color.LIGHTGRAY);
                } else if (laby.getGrille()[i][j].equals(laby.getSortie())) {
                    cell.setFill(Color.RED);
                } else {
                    cell.setFill(Color.TRANSPARENT);
                }

                p.add(cell, j, i);
            }
        }
    }

    public void afficherLabyrinthePartiel(GridPane p, double largeurDispo, double hauteurDispo, int range) {
        Labyrinthe laby = this.modele.getLabyrinthe();
        int hauteur = laby.getHauteur();
        int largeur = laby.getLargeur();
        int joueurLigne = this.modele.getPositionJoueur().getY();
        int joueurCol = this.modele.getPositionJoueur().getX();

        double tailleCarre = Calcul.calculTailleCarreOptimal(largeurDispo, hauteurDispo, 2 * range + 1, 2 * range + 1);
        // Si la grille est vide, la créer
        if (p.getChildren().isEmpty()) {
            for (int i = 0; i < 2 * range + 1; i++) {
                for (int j = 0; j < 2 * range + 1; j++) {
                    Rectangle cell = new Rectangle(tailleCarre, tailleCarre);
                    p.add(cell, j, i);
                }
            }
        }
        Image brique = this.getBrique();
        Image fond = this.getFond();
        Image joueur = this.getJoueur();
        Image fin = this.getFin();
        // Mise à jour des cellules
        for (int i = 0; i < 2 * range + 1; i++) {
            for (int j = 0; j < 2 * range + 1; j++) {
                int labyI = joueurLigne - range + i;
                int labyJ = joueurCol - range + j;
                Rectangle cell = (Rectangle) p.getChildren().get(i * (2 * range + 1) + j);

                if (labyI < 0 || labyI >= hauteur || labyJ < 0 || labyJ >= largeur) {
                    cell.setFill(new ImagePattern(brique));
                } else if (laby.getGrille()[labyI][labyJ].estMur()) {
                    cell.setFill(new ImagePattern(brique));
                } else if (labyI == joueurLigne && labyJ == joueurCol) {
                    cell.setFill(new ImagePattern(FusionImage.fusionnerImages(fond, joueur)));
                } else if (laby.getGrille()[labyI][labyJ].equals(laby.getSortie())) {
                    cell.setFill(new ImagePattern(FusionImage.fusionnerImages(fond, fin)));
                } else {
                    cell.setFill(new ImagePattern(fond));
                }
            }
        }
    }


    public GridPane getLabPartiel() {
        return labPartiel;
    }

    public GridPane getMinimap(){
        return minimap;
    }

    public Image getBrique() {
        return new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/Brique.png").toExternalForm());
    }

    public Image getJoueur() {
        return new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/souris.png").toExternalForm());
    }

    public Image getFond() {
        return new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/herbe.png").toExternalForm());
    }

    public Image getFin() {
        return new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/fromage.png").toExternalForm());

    }



}


