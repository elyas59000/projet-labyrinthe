package J4.sae_labyrinthe.modele;

public interface IModeleJeu {
    /**
     *
     * @param nom
     * @return
     */
    Joueur creerJoueur(String nom);

    /**
     *
     * @param joueur
     */

    void afficherProgression(IJoueur joueur);

    /**
     *
     * @param largeur
     * @param hauteur
     * @param tauxMur
     */
    void lancerPartieLibre(int largeur,int hauteur,int tauxMur);

    /**
     *
     * @param j
     */
    void lancerPartieProgresion(IJoueur j);

    /**
     *
     * @return
     */
    Joueur selectionnerJoureur();

}
