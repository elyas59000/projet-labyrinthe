package J4.sae_labyrinthe.modele;

import J4.sae_labyrinthe.exception.ValidationException;
import J4.sae_labyrinthe.vue.IObserver;

import java.io.IOException;

public interface IModeleJeu {
    /**
     * Creer un nouveau joueur à partir de son nom
     * @param nom,mdp
     * @return
     */
    Joueur creerJoueur(String nom, String mdp) throws ValidationException, IOException;


    void connexionJoueur(String nom, String mdp) throws ValidationException;


    /**
     * Genere ou modifie un labyrinthe  grâce donnés passé en paramètre
     * @param largeur
     * @param hauteur
     * @param tauxMur
     */
    void lancerPartieLibre(int largeur,int hauteur,int tauxMur);

    /**
     *lance la partie en mode progression avec la progression du joueur passé en paramètre
     */
    void lancerPartieProgression();

    /**
     *
     * @return
     */
    Joueur selectionnerJoueur();

    /**
     *
     * @param observer
     */
    void remove(IObserver observer);

    /**
     *
     * @param observer
     */
    void add(IObserver observer);

    Labyrinthe getLabyrinthe();

    void deplacerJoueur(String direction) throws IOException;
}
