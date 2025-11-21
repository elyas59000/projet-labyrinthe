package J4.sae_labyrinthe.modele.Item.Malus;

import J4.sae_labyrinthe.modele.Item.Bonus.PlusVision;
import J4.sae_labyrinthe.modele.Item.IItem;
import J4.sae_labyrinthe.modele.ModeleJeu;

public class MoinsVision implements IItem {

    private int value;      // range à retirer
    private final ModeleJeu modele;
    private boolean applied;

    public MoinsVision(ModeleJeu modele, int value) {
        this.value=value;
        this.modele=modele;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void applyEffect() {
        if (!applied) {
            // retire la vision avec un minimum de 1
            modele.setVisionRange(Math.max(1, modele.getVisionRange()-value));

            // pas mettre comme ça car on modifie la liste pd l'iteration
            // si on enleve toute la vision, on enleve les items plus et moins vision (ils servent plus à rien)
            // if (modele.rangeZero()){
            //    removeSelf();
            // }
            applied=true;
        }
    }

    @Override
    public void removeSelf() {
        // on enleve le malus
        modele.setVisionRange(modele.getVisionRange() + value);
        // si on remove moins vision
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
        return "moinsvision";
    }

    @Override
    public String toString() {
        return  "MoinsVision";
    }
}
