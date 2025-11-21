package J4.sae_labyrinthe.modele.Item.Malus;

import J4.sae_labyrinthe.modele.Item.IItem;
import J4.sae_labyrinthe.modele.ModeleJeu;

/**
 * Item malus qui teleporte a l'entree
 */
public class TpDebut implements IItem {

    private final ModeleJeu modele;
    private boolean applied = false;

    public  TpDebut(ModeleJeu modele) {
        this.modele = modele;
    }

    @Override
    public int getValue() {return 0;}

    @Override
    public void applyEffect() {
        if (!applied) {
            modele.setPositionJoueur(modele.getLabyrinthe().getEntree());
        }
        applied = true;

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
        return -1;
    }

    @Override
    public String getNom() {
        return "tpdebut";
    }

    @Override
    public String toString(){
        return "TpDebut";
    };
}
