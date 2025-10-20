package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.controleur.ControleurJeu;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class VueLabyrintheEntier extends Stage implements IObserver {

    private final IModeleJeu modele;
    private final GridPane P = new GridPane();

    public VueLabyrintheEntier(IModeleJeu modele) {
        this.modele = modele;
        this.setTitle("Fenetre laby");
        this.setScene(createSceneContent());
        ControleurJeu c = new ControleurJeu(modele, this);
        this.show();
    }

    private Scene createSceneContent() {
        afficherLabyrinthe(P);
        return new Scene(P);
    }

    @Override
    public void update() {
        afficherLabyrinthe(P);
    }

    private void afficherLabyrinthe(GridPane p) {
        p.getChildren().clear();

        Image brique = new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/Brique.png").toExternalForm());
        Image joueur = new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/souris.png").toExternalForm());
        Image fond = new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/herbe.png").toExternalForm());
        Image fin = new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/drapeau.png").toExternalForm());
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
                }
                else {
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
}
