package J4.sae_labyrinthe.modele.Item.Bonus;

import J4.sae_labyrinthe.modele.Item.IItem;

public class SentirSortie implements IItem {
    // TODO : implementation

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public void applyEffect() {

    }

    @Override
    public void removeSelf() {

    }

    @Override
    public void resetApplied() {

    }

    @Override
    public int isBonus() {
        return 0;
    }

    @Override
    public String getNom() {
        return "sentir";
    }

    @Override
    public String toString(){
        return "SentirSortie";
    };
}
