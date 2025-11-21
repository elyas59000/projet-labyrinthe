package J4.sae_labyrinthe.modele.Item;

import J4.sae_labyrinthe.modele.ModeleJeu;

public class Cleanse implements IItem {

    private final ModeleJeu modele;

    public Cleanse(ModeleJeu modele){
        this.modele = modele;
    }

    @Override
    public int getValue() {return 0;}

    @Override
    public void applyEffect() {
        modele.cleanse();
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
        return "cleanse";
    }

    @Override
    public String toString(){
        return "Cleanse";
    };
}
