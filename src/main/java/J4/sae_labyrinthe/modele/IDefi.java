package J4.sae_labyrinthe.modele;

public interface IDefi {
    /**
     *
     */
    void jouer();

    /**
     *
     * @return
     */
    boolean estReussi();

    /**
     *
     * @return
     */
    String getDifficulte();

    /**
     *
     * @return
     */
    Labyrinthe getLabyrinthe();

}
