package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.controleur.ControleurLabyrinthe;
import J4.sae_labyrinthe.modele.IModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeu;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public abstract class VueLabyrintheBase implements IVueLabyrinthe {
    protected  IModeleJeu modele;
    protected  GridPane labEntier;
    ControleurLabyrinthe c;

    public VueLabyrintheBase(IModeleJeu modele) {
        this.modele = modele;
    }


    protected abstract Scene createSceneContent() ;

    @Override
    public abstract void update();

    public abstract void afficherLabyrinthe(GridPane p);


    protected Image fusionnerImages(Image fond, Image dessus) {
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

    public GridPane getLabEntier() {
        return labEntier;
    }

    /** Retourne l'image de la brique */
    public abstract Image getBrique();

    /** Retourne l'image du joueur */
    public abstract Image getJoueur();

    /** Retourne l'image du fond */
    public abstract Image getFond();

    /** Retourne l'image de la fin */
    public abstract Image getFin();

    public abstract Parent getRoot();

    public ControleurLabyrinthe getControleur(){
        return c;
    }
}
