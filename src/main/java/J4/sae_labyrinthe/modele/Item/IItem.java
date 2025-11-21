package J4.sae_labyrinthe.modele.Item;

import J4.sae_labyrinthe.modele.ModeleJeu;

public interface IItem {
    /**
     * Retourne la valeur utile à l'item
     * la valeur peut être le nb cellules pour le bonus range, le nombre de "tours" dans mixCtrl, …
     */
    public int getValue();
    public void applyEffect();
    public void removeSelf();
    public void resetApplied();
    public String toString();


    /**
     * @return 1 si bonus, 0 si neutral, -1 si malus
     */
    public int isBonus();

    String getNom();
}
