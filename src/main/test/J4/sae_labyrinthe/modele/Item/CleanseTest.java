package J4.sae_labyrinthe.modele.Item;

import J4.sae_labyrinthe.modele.Item.Bonus.PlusVision;
import J4.sae_labyrinthe.modele.Item.Malus.TpDebut;
import J4.sae_labyrinthe.modele.ModeleJeu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CleanseTest {

    private ModeleJeu modele;

    @BeforeEach
    void setUp() {
        modele = new ModeleJeu();
        modele.lancerPartieLibre(10, 10, 0, 0, null, false);
        modele.nouveauListItem();
    }

    @Test
    void testCleanse_SupprimeTout() {
        IItem bonus = new PlusVision(modele, 1);
        IItem malus = new TpDebut(modele);

        modele.addItem(bonus);
        modele.addItem(malus);

        assertEquals(2, modele.getListItem().size(), "L'inventaire doit contenir 2 items.");

        Cleanse cleanse = new Cleanse(modele);
        cleanse.applyEffect();

        assertTrue(modele.getListItem().isEmpty(), "Cleanse doit vider entièrement la liste.");
    }

    @Test
    void testCleanseBonus_EnleveLesMalus() {

        IItem bonus = new PlusVision(modele, 1); // Bonus (+1)
        IItem malus = new TpDebut(modele);       // Malus (-1)

        modele.addItem(bonus);
        modele.addItem(malus);

        CleanseBonus cb = new CleanseBonus(modele);
        cb.applyEffect();

        assertEquals(1, modele.getListItem().size(), "Il ne doit rester qu'un item.");
        assertTrue(modele.getListItem().contains(bonus), "Le bonus doit être conservé.");
        assertFalse(modele.getListItem().contains(malus), "Le malus doit être supprimé par CleanseBonus.");
    }

    @Test
    void testCleanseMalus_EnleveLesMalus() {

        IItem bonus = new PlusVision(modele, 1);
        IItem malus = new TpDebut(modele);

        modele.addItem(bonus);
        modele.addItem(malus);

        CleanseMalus cm = new CleanseMalus(modele);
        cm.applyEffect();

        assertEquals(1, modele.getListItem().size());
        assertTrue(modele.getListItem().contains(bonus), "Le bonus doit rester.");
        assertFalse(modele.getListItem().contains(malus), "Le malus doit être supprimé.");
    }

    @Test
    void testCleanseNeutral_EnleveLesNeutres() {
        // Cleanse est un item neutre (isBonus() == 0)
        IItem itemNeutre = new Cleanse(modele);
        IItem itemBonus = new PlusVision(modele, 1); // Bonus (+1)

        modele.addItem(itemNeutre);
        modele.addItem(itemBonus);

        CleanseNeutral cn = new CleanseNeutral(modele);
        cn.applyEffect();

        assertEquals(1, modele.getListItem().size());
        assertTrue(modele.getListItem().contains(itemBonus), "Le bonus doit rester.");
        assertFalse(modele.getListItem().contains(itemNeutre), "L'item neutre doit être supprimé.");
    }

    @Test
    void testGetNom_Et_ToString() {
        Cleanse c = new Cleanse(modele);
        assertEquals("cleanse", c.getNom());
        assertEquals("Cleanse", c.toString());

        CleanseBonus cb = new CleanseBonus(modele);
        assertEquals("cleanbonus", cb.getNom());
        assertEquals("CleanseBonus", cb.toString());

        CleanseMalus cm = new CleanseMalus(modele);
        assertEquals("CleanseMalus", cm.getNom());
        assertEquals("CleanseMalus", cm.toString());

        CleanseNeutral cn = new CleanseNeutral(modele);
        assertEquals("CleanseNeutral", cn.getNom());
        assertEquals("CleanseNeutral", cn.toString());
    }

    @Test
    void testIsBonus() {
        // Vérifie que tous ces items sont considérés comme neutres (0)
        assertEquals(0, new Cleanse(modele).isBonus());
        assertEquals(0, new CleanseBonus(modele).isBonus());
        assertEquals(0, new CleanseMalus(modele).isBonus());
        assertEquals(0, new CleanseNeutral(modele).isBonus());
    }
}