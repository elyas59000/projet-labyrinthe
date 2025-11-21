package J4.sae_labyrinthe.modele;

import J4.sae_labyrinthe.modele.Item.Bonus.*;
import J4.sae_labyrinthe.modele.Item.IItem;

public class BonusFactory {

    public static IItem creerBonus(String type) {
        return switch (type.toLowerCase()) {
            case "brisemur" -> new BriseMur();
            case "plusvision" -> new PlusVision(ModeleJeuSingleton.getInstance(),4);
            case "tp" -> new TP(ModeleJeuSingleton.getInstance());
            case "ward" -> new Ward(ModeleJeuSingleton.getInstance(),3);
            case "sentirsortie" -> new SentirSortie();
            default -> throw new IllegalArgumentException("Type inconnu : " + type);
        };
    }
}
