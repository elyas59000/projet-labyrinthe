package J4.sae_labyrinthe.modele.Item;

import J4.sae_labyrinthe.modele.ModeleJeu;

public class CleanseNeutral implements IItem{

    private boolean applied;
    private final ModeleJeu modele;

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
        applied = true;
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
    public String getNom() {
        return this.toString();
    }


    @Override
    public String toString(){
        return "CleanseNeutral";
    };
}
