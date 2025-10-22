package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.ModeleJeu;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

public interface IVueLabyrinthe {

    public void afficherLabyrinthe(GridPane p, double l,double h);


    /** Retourne l'image de la brique */
    public Image getBrique();

    /** Retourne l'image du joueur */
    public Image getJoueur();

    /** Retourne l'image du fond */
    public Image getFond();

    /** Retourne l'image de la fin */
    public Image getFin();

    public GridPane getLabEntier();
}

