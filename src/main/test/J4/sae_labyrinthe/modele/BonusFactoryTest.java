package J4.sae_labyrinthe.modele;

import J4.sae_labyrinthe.modele.Item.Bonus.*;
import J4.sae_labyrinthe.modele.Item.IItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BonusFactoryTest {



    @Test
    void testCreerBonus_InsensibleAlaCasse() {
        // "WaRd" doit marcher comme "ward"
        assertTrue(BonusFactory.creerBonus("WaRd") instanceof Ward);
        assertTrue(BonusFactory.creerBonus("TP") instanceof TP);
    }

    @Test
    void testCreerBonus_TypeInconnu() {
        // Vérifie que l'exception est bien levée pour un nom bidon
        assertThrows(IllegalArgumentException.class, () -> {
            BonusFactory.creerBonus("SuperBonusQuiNExistePas");
        });
    }
}