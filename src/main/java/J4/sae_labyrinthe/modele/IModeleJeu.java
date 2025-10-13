package J4.sae_labyrinthe.modele;

import J4.sae_labyrinthe.vue.IObserver;

public interface IModeleJeu {
    /**
     * Creer un nouveau joueur à partir de son nom
     * @param nom
     * @return
     */
    Joueur creerJoueur(String nom);

    /**
     * affiche l'étape maximale ainsique les defis remporter par le joueur passé en paramèter
     * @param joueur
     */

    void afficherProgression(IJoueur joueur);

    /**
     * Genere ou modifie un labyrinthe  grâce donnés passé en paramètre
     * @param largeur
     * @param hauteur
     * @param tauxMur
     */
    void lancerPartieLibre(int largeur,int hauteur,int tauxMur);

    /**
     *lance la partie en mode progression avec la progression du joueur passé en paramètre
     * @param j
     */
    void lancerPartieProgression(IJoueur j);

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

}
