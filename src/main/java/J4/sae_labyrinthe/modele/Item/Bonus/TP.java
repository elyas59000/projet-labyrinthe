package J4.sae_labyrinthe.modele.Item.Bonus;

import J4.sae_labyrinthe.modele.Cellule;
import J4.sae_labyrinthe.modele.Item.IItem;
import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.ModeleJeu;

public class TP implements IItem {

    private boolean applied = false;
    private final ModeleJeu modele;

    public TP(ModeleJeu modele) {
        this.modele = modele;
    }

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public void applyEffect() {
        if (!applied) {
            Cellule pos = modele.getLabyrinthe().positionAleaCell();
            if (pos != null) {
                modele.setPositionJoueur(pos);
            }
            applied = true;
        }
    }

    @Override
    public void removeSelf() {
        modele.removeItem(this);
    }

    @Override
    public void resetApplied() {};

    @Override
    public int isBonus() {
        // TODO : peut etre mettre neutral car il peut ramener en arriere
        return 1;
    }

    @Override
    public String getNom() {
        return "teleportation";
    }

    @Override
    public String toString(){
        return "TP";
    };
}
