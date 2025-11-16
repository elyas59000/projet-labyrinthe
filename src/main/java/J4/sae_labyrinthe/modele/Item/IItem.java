package J4.sae_labyrinthe.modele.Item;

public interface IItem {
    /**
     * Retourne la valeur utile à l'item
     * la valeur peut être le nb cellules pour le bonus range, le nombre de "tours" dans mixCtrl, …
     */
    public int getValue();
    public void applyEffect();
}
