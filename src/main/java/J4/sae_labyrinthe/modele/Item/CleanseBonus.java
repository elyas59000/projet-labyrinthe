package J4.sae_labyrinthe.modele.Item;

import J4.sae_labyrinthe.modele.ModeleJeu;

public class CleanseBonus implements IItem{

    private final ModeleJeu modele;
    private boolean applied;

    public CleanseBonus(ModeleJeu modele){
        this.modele = modele;
    }

    @Override
    public int getValue() {return 0;}

    @Override
    public void applyEffect() {
        if (!applied) {
            modele.removeItems(modele.getBonus());
        }
    }

    @Override
    public void removeSelf() {
        modele.removeItem(this);
    }

    @Override
    public void resetApplied() {};      // pas besoin

    @Override
    public int isBonus() {return 0;}

    @Override
    public String toString(){
        return "CleanseBonus";
    };
}
