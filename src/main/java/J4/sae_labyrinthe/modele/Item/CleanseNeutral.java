package J4.sae_labyrinthe.modele.Item;

import J4.sae_labyrinthe.modele.ModeleJeu;

public class CleanseNeutral implements IItem{

    private final ModeleJeu modele;
    private boolean applied;

    public CleanseNeutral(ModeleJeu modele){
        this.modele = modele;
    }

    @Override
    public int getValue() {return 0;}

    @Override
    public void applyEffect() {
        if (!applied) {
            modele.removeItems(modele.getNeutral());
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
        return "CleanseNeutral";
    };
}
