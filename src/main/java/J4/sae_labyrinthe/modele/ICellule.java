package J4.sae_labyrinthe.modele;

public interface ICellule {

    /**
     *
     * @return
     */
    int getX();

    /**
     *
     * @return
     */
    int getY();

    /**
     *
     * @return
     */
    boolean estMur();

    /**
     *
     * @param mur
     */
    void setMur(boolean mur);
}
