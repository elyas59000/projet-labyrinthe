package J4.sae_labyrinthe.modele;

import J4.sae_labyrinthe.modele.Item.IItem;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

public class Boutique {

    // Instance unique du singleton
    private static final Boutique instance = new Boutique();

    // La boutique : clé = item, valeur = prix
    private Map<IItem, Integer> items;

    // Constructeur privé pour empêcher l’instanciation
    private Boutique() {
        items = new HashMap<>();
        items.put(BonusFactory.creerBonus("brisemur"),10);
        items.put(BonusFactory.creerBonus("tp"),5);
        items.put(BonusFactory.creerBonus("Ward"),7);
        items.put(BonusFactory.creerBonus("sentirsortie"),15);
        items.put(BonusFactory.creerBonus("plusvision"),12);
    }

    // Méthode d'accès à l'instance unique
    public static Boutique getInstance() {
        return instance;
    }

    /**
     * Ajoute un item avec son prix
     */
    public void ajouterItem(IItem item, int prix) {
        if (item != null && prix >= 0) {
            items.put(item, prix);
        }
    }

    /**
     * Retire un item de la boutique
     */
    public void retirerItem(IItem item) {
        items.remove(item);
    }

    /**
     * Achète un item si disponible
     */
    public boolean acheterItem(IItem item, Joueur joueur) {
        if (!items.containsKey(item)) { // verif item dispo
            return false;
        }

        int prix = items.get(item);
        if (joueur.getScore() >= prix) {
            joueur.ajouterItem(item);
            joueur.ajouterScore(-prix);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retourne la HashMap complète (encapsulation possible)
     */
    public Map<IItem, Integer> getItems() {
        return new HashMap<>(items);
    }
}



