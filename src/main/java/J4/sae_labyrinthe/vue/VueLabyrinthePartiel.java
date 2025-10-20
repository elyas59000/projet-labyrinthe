package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.controleur.ControleurLabyrinthe;
import J4.sae_labyrinthe.modele.IModeleJeu;
import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.ModeleJeu;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class VueLabyrinthePartiel extends VueLabyrintheBase{
    private GridPane labPartiel;

    public VueLabyrinthePartiel(IModeleJeu modele) {
        super(modele);
        this.labPartiel = new GridPane();
        this.labEntier = new GridPane();
        this.setTitle("Fenetre labyrinthe partiel");
        this.setScene(createSceneContent());
        ControleurLabyrinthe c = new ControleurLabyrinthe(modele, this);
        this.show();
    }

    @Override
    protected Scene createSceneContent() {
        HBox hBox = new HBox();
        afficherLabyrinthe(labEntier);
        afficherLabyrinthePartiel(labPartiel);
        javafx.scene.control.Separator separator = new javafx.scene.control.Separator();
        separator.setOrientation(javafx.geometry.Orientation.VERTICAL);
        separator.setStyle("-fx-background-color: black; -fx-pref-width: 50;");
        hBox.getChildren().add(labEntier);
        hBox.getChildren().add(separator);
        hBox.getChildren().add(labPartiel);

        labEntier.prefWidthProperty().bind(hBox.widthProperty().multiply(0.7)); // 70% pour le labyrinthe entier
        labPartiel.prefWidthProperty().bind(hBox.widthProperty().multiply(0.3)); // 30% pour le labyrinthe partiel

        int hauteurLaby = this.modele.getLabyrinthe().getHauteur();
        int largeurLaby = this.modele.getLabyrinthe().getLargeur();
        int tailleCelluleMin = 40;

        labEntier.setMinWidth(largeurLaby * tailleCelluleMin);
        labEntier.setMinHeight(hauteurLaby * tailleCelluleMin);

        labPartiel.setMinWidth(3 * tailleCelluleMin);
        labPartiel.setMinHeight(3 * tailleCelluleMin);


        HBox.setHgrow(labEntier, Priority.ALWAYS);
        HBox.setHgrow(labPartiel, Priority.ALWAYS);

        labEntier.setMaxWidth(Double.MAX_VALUE);
        labPartiel.setMaxWidth(Double.MAX_VALUE);
        return new Scene(hBox);
    }

    @Override
    public void update() {
        afficherLabyrinthe(labEntier);
        afficherLabyrinthePartiel(labPartiel);
    }

    @Override
    public void afficherLabyrinthe(GridPane p) {
        p.getChildren().clear();

        Image brique = this.getBrique();
        Image fond = this.getFond();
        Image fin = this.getFin();
        Labyrinthe laby = this.modele.getLabyrinthe();
        int hauteur = laby.getHauteur();
        int largeur = laby.getLargeur();

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                Rectangle cell = new Rectangle();

                // Taille dynamique
                cell.widthProperty().bind(p.widthProperty().divide(largeur));
                cell.heightProperty().bind(p.heightProperty().divide(hauteur));

                // --- Choix du contenu ---
                if (laby.getGrille()[i][j].estMur()) {
                    cell.setFill(new ImagePattern(brique));
                } else {
                    cell.setFill(new ImagePattern(fond));
                     if (laby.getGrille()[i][j].equals(laby.getSortie())) {
                        cell.setFill(new ImagePattern(fusionnerImages(fond, fin)));
                    }
                }

                p.add(cell, j, i);
            }
        }
    }

    public void afficherLabyrinthePartiel(GridPane p) {
        p.getChildren().clear();

        Image brique = this.getBrique();
        Image fond = this.getFond();
        Image joueur = this.getJoueur();
        Image fin = this.getFin();
        Labyrinthe laby = this.modele.getLabyrinthe();
        int hauteur = laby.getHauteur();
        int largeur = laby.getLargeur();

        // position du joueur
        int joueurLigne = this.getModele().getPositionJoueur().getY();
        int joueurCol = this.getModele().getPositionJoueur().getX();

        // afficher les cases autour du joueur (3x3)
        for (int i = joueurLigne - 1; i <= joueurLigne + 1; i++) {
            for (int j = joueurCol - 1; j <= joueurCol + 1; j++) {

                Rectangle cell = new Rectangle();

                // Taille dynamique
                cell.widthProperty().bind(p.widthProperty().divide(3));
                cell.heightProperty().bind(p.heightProperty().divide(3));

                // --- Choix du contenu ---
                if (i < 0 || i >= hauteur || j < 0 || j >= largeur) {
                    // hors limites : remplir en mur
                    cell.setFill(new  ImagePattern(brique));
                } else {
                    if (laby.getGrille()[i][j].estMur()) {
                        cell.setFill(new ImagePattern(brique));
                    } else if (i == joueurLigne && j == joueurCol) {
                        cell.setFill(new ImagePattern(fusionnerImages(fond, joueur)));
                    } else if (laby.getGrille()[i][j].equals(laby.getSortie())) {
                        cell.setFill(new ImagePattern(fusionnerImages(fond, fin)));
                    } else {
                        cell.setFill(new ImagePattern(fond));
                    }
                }

                // ajouter la cellule dans le GridPane partiel
                p.add(cell, j - (joueurCol - 1), i - (joueurLigne - 1));
            }
        }
    }

    public ModeleJeu getModele() {
        return (ModeleJeu) modele;
    }

    public GridPane getLabEntier() {
        return labEntier;
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
