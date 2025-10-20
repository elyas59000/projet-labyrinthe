package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.controleur.ControleurLabyrinthe;
import J4.sae_labyrinthe.modele.IModeleJeu;
import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.ModeleJeu;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class VueLabyrintheEntier extends VueLabyrintheBase {

    public VueLabyrintheEntier(IModeleJeu modele) {
        super(modele);
    }

    private Scene createSceneContent() {
        afficherLabyrinthe(P);
        return new Scene(P);
    }

    @Override
    public void update() {
        afficherLabyrinthe(P);
    }

    public void afficherLabyrinthe(GridPane p) {
        p.getChildren().clear();

        Image brique = this.getBrique();
        Image joueur = this.getJoueur();
        Image fond = this.getFond();
        Image fin = this.getFin();
        Labyrinthe laby = this.modele.getLabyrinthe();
        int hauteur = laby.getHauteur();
        int largeur = laby.getLargeur();

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                Rectangle cell = new Rectangle();

                // Taille dynamique
                cell.widthProperty().bind(
                        Bindings.min(
                                p.widthProperty().divide(largeur),
                                p.heightProperty().divide(hauteur)
                        )
                );
                cell.heightProperty().bind(
                        Bindings.min(
                                p.widthProperty().divide(largeur),
                                p.heightProperty().divide(hauteur)
                        )
                );

                // --- Choix du contenu ---
                if (laby.getGrille()[i][j].estMur()) {
                    cell.setFill(new ImagePattern(brique));
                } else {
                    cell.setFill(new ImagePattern(fond));

                    if (laby.getGrille()[i][j].equals(this.getModele().getPositionJoueur())) {
                        cell.setFill(new ImagePattern(createOverlayedImage(fond, joueur)));
                    } else if (laby.getGrille()[i][j].equals(laby.getSortie())) {
                        cell.setFill(new ImagePattern(createOverlayedImage(fond, fin)));
                    }
                }

                p.add(cell, j, i);
            }
        }
    }


    private Image createOverlayedImage(Image fond, Image dessus) {
        double width = fond.getWidth();
        double height = fond.getHeight();

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.drawImage(fond, 0, 0);
        gc.drawImage(dessus, 0, 0);

        WritableImage combined = new WritableImage((int) width, (int) height);
        canvas.snapshot(null, combined);
        return combined;
    }


    public ModeleJeu getModele() {
        return (ModeleJeu) modele;
    }

    public GridPane getP() {
        return P;
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
        return new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/drapeau.png").toExternalForm());

    }
}
