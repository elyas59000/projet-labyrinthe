package J4.sae_labyrinthe.modele.Item;

import J4.sae_labyrinthe.modele.ModeleJeu;

import java.util.Set;

public class CleanseMalus implements IItem {

    private final ModeleJeu modele;

    public CleanseMalus(ModeleJeu modele){
        this.modele = modele;
    }

    @Override
    public int getValue() {return 0;}

    @Override
    public void applyEffect() {
        modele.removeItems((Set<IItem>) modele.getMalus());
        removeSelf();
    }

    @Override
    public void removeSelf() {
        modele.removeItem(this);
    }

    @Override
    public void resetApplied() {};      // pas besoin

    @Override
    public int isBonus() {return 0;}
}
