package J4.sae_labyrinthe.modele.Item.Bonus; // Respecte l'arborescence

import J4.sae_labyrinthe.modele.Cellule;
import J4.sae_labyrinthe.modele.ModeleJeu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class BonusTest {

    private ModeleJeu modele;

    @BeforeEach
    void setUp() {
        modele = new ModeleJeu();
        // 1. Lancer une partie pour avoir un labyrinthe et un joueur
        modele.lancerPartieLibre(10, 10, 0, 0, null, false);

        // 2. Initialiser les listes pour éviter les NullPointerException
        modele.nouveauListItem();  // Initialise la liste du joueur
        modele.nouveauMapItem();   // Initialise la map des items du labyrinthe
    }

    @Test
    void testTP() {
        TP tp = new TP(modele);
        assertDoesNotThrow(() -> tp.applyEffect());
        assertEquals(1, tp.isBonus());
        assertEquals("teleportation", tp.getNom());
    }

    @Test
    void testWard() {
        Ward ward = new Ward(modele, 5);

        assertDoesNotThrow(() -> ward.applyEffect());

        assertEquals(1, ward.isBonus());
        assertEquals(5, ward.getValue());
        assertEquals("vision", ward.getNom());

        assertDoesNotThrow(() -> ward.removeSelf());
    }

    @Test
    void testBriseMur() {
        BriseMur bm = new BriseMur();
        assertDoesNotThrow(() -> bm.applyEffect());
        assertEquals(0, bm.getValue());
        assertEquals("brisemur", bm.getNom());
    }

    @Test
    void testPlusVision() {
        PlusVision bonus = new PlusVision(modele, 3);
        // On vérifie que l'effet s'applique sans erreur
        assertDoesNotThrow(() -> bonus.applyEffect());
        assertEquals("vision", bonus.getNom());
    }

    @Test
    void testSentirSortie() {
        SentirSortie s = new SentirSortie();
        assertDoesNotThrow(() -> s.applyEffect());
        assertEquals("sentir", s.getNom());
    }
}
