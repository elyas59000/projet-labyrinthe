package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.IModeleJeu;
import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.ModeleJeu;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;

public class VueLabyrintheEntier extends Stage implements IObserver{

    //ControllerJeu controller;
    IModeleJeu modele;
    private final GridPane P =new GridPane();


    public VueLabyrintheEntier(IModeleJeu modele){
        this.modele=  modele;
        this.setTitle("Fenetre laby");
        this.setScene(createSceneContent());
        this.show();
    }

    private Scene createSceneContent(){
        afficherLabyrinthe(P);
        return new Scene(P);
    }


    @Override
    public void update() {
        afficherLabyrinthe(P);
    }

    private void afficherLabyrinthe(GridPane p) {
        p.getChildren().clear();
        Labyrinthe laby = this.getModele().getLabyrinthe();
        int hauteur = laby.getHauteur();
        int largeur = laby.getLargeur();

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                Rectangle cell = new Rectangle();

                // affichage dynamique pour modifier la taille des cellule en meme temps que la page
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

                if (laby.getGrille()[i][j].estMur()) {
                    cell.setFill(Paint.valueOf("black"));
                } else if (laby.getGrille()[i][j].equals(this.getModele().getPositionJoueur())) {
                    cell.setFill(Paint.valueOf("blue"));
                } else {
                    cell.setFill(Paint.valueOf("white"));
                }

                p.add(cell, j, i);
            }
        }
    }



    public ModeleJeu getModele() {
        return (ModeleJeu) modele;
    }

    public GridPane getP() {
        return P;
    }
}
