package J4.sae_labyrinthe.modele.Item.Bonus;

import J4.sae_labyrinthe.modele.Item.IItem;
import J4.sae_labyrinthe.modele.ModeleJeu;

public class PlusVision implements IItem {

    private final ModeleJeu modele;
    private int value;      // range de la vision ajouté
    private boolean applied;

    public  PlusVision(ModeleJeu modele,  int value) {
        this.modele = modele;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void applyEffect() {
        if (!applied) {
            modele.setVisionRange(modele.getVisionRange() + value);
            applied = true;
        }
    }

    @Override
    public void removeSelf() {
        modele.removeItem(this);
    }

    @Override
    public void resetApplied() {
        applied = false;
    }

    @Override
    public int isBonus() {
        return 1;
    }

    @Override
    public String toString(){
        return "PlusVision";
    };
}
