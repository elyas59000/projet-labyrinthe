package J4.sae_labyrinthe.modele.Item.Bonus;

import J4.sae_labyrinthe.modele.Item.IItem;
import J4.sae_labyrinthe.modele.ModeleJeu;

public class Ward implements IItem {

    private int value;      // range de la ward
    private final ModeleJeu modele;
    private boolean applied;

    public Ward(ModeleJeu modele, int value) {
        this.value=value;
        this.modele=modele;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void applyEffect() {
        // no effect, la presence meme de la ward est suffisante
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
    public String getNom() {
        return "vision";
    }

    @Override
    public String toString(){
        return "Ward";
    };
}
