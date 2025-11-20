package J4.sae_labyrinthe.modele;

import J4.sae_labyrinthe.modele.Item.Bonus.BriseMur;
import J4.sae_labyrinthe.modele.Item.Bonus.PlusVision;
import J4.sae_labyrinthe.modele.Item.Bonus.SentirSortie;
import J4.sae_labyrinthe.modele.Item.IItem;

public class BonusFactory {

    public static IItem creerBonus(String type) {
        return switch (type.toLowerCase()) {
            case "brisemur" -> new BriseMur();
            case "plusvision" -> new PlusVision(ModeleJeuSingleton.getInstance(),4);
            case "tp" -> new SentirSortie();
            default -> throw new IllegalArgumentException("Type inconnu : " + type);
        };
    }
}
