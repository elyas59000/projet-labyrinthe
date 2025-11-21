package J4.sae_labyrinthe.modele;

import J4.sae_labyrinthe.modele.Item.Bonus.BriseMur;
import J4.sae_labyrinthe.modele.Item.IItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoutiqueTest {

    private Boutique boutique;
    private Joueur joueur;

    @BeforeEach
    void setUp() {
        boutique = Boutique.getInstance();
        joueur = new Joueur("Acheteur", "mdp");
    }

    @Test
    void testSingleton() {
        Boutique b1 = Boutique.getInstance();
        Boutique b2 = Boutique.getInstance();
        assertSame(b1, b2, "La boutique doit être un Singleton.");
    }

    @Test
    void testAjouterEtAcheterItem_Succes() {
        IItem itemTest = new BriseMur(); // On crée un item spécifique pour le test
        int prix = 50;
        boutique.ajouterItem(itemTest, prix);

        joueur.ajouterScore(100); // Le joueur a assez d'argent (100 > 50)

        boolean achatReussi = boutique.acheterItem(itemTest, joueur);

        assertTrue(achatReussi, "L'achat devrait réussir.");
        assertEquals(50, joueur.getScore(), "Le prix aurait dû être débité (100 - 50).");

        //boutique.retirerItem(itemTest);
    }

    @Test
    void testAcheterItem_PasAssezDArgent() {
        IItem itemChere = new BriseMur();
        boutique.ajouterItem(itemChere, 1000);

        joueur.ajouterScore(10); // Le joueur est pauvre (10 < 1000)

        boolean achatReussi = boutique.acheterItem(itemChere, joueur);

        assertFalse(achatReussi, "L'achat devrait échouer par manque de fonds.");
        assertEquals(10, joueur.getScore(), "Le score ne doit pas changer.");

        //boutique.retirerItem(itemChere);
    }

    @Test
    void testAcheterItem_NonDisponible() {
        IItem itemInexistant = new BriseMur();
        // On ne l'ajoute PAS à la boutique

        boolean achatReussi = boutique.acheterItem(itemInexistant, joueur);

        assertFalse(achatReussi, "On ne peut pas acheter un item qui n'est pas dans la boutique.");
    }
}