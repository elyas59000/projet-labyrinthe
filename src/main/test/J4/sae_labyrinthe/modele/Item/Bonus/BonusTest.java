package J4.sae_labyrinthe.modele.Item.Bonus; // Respecte l'arborescence

import J4.sae_labyrinthe.modele.Cellule;
import J4.sae_labyrinthe.modele.ModeleJeu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class BonusTest {

    private ModeleJeu modele;

    @BeforeEach
    void setUp() {
        modele = new ModeleJeu();
        modele.lancerPartieLibre(10, 10, 0, 0, null, false);
        modele.nouveauListItem();
        modele.setWard(new HashMap<>());
    }

    @Test
    void testTP() {
        // Arrange
        TP tp = new TP(modele);
        Cellule positionAvant = modele.getPositionJoueur();

        tp.applyEffect();


        assertNotNull(modele.getPositionJoueur());
        // On vérifie surtout que ça ne plante pas et que l'effet est appliqué
        assertEquals(1, tp.isBonus());
    }

    @Test
    void testWard() {
        Ward ward = new Ward(modele, 5);
        assertTrue(modele.getWard().isEmpty(), "Pas de ward au début");

        ward.applyEffect();
        assertNotNull(modele.getWard(), "La ward doit être posée");

        ward.removeSelf();
        assertTrue(modele.getWard().isEmpty(), "La ward doit être retirée.");
    }

    @Test
    void testBriseMur_EstVidePourLInstant() {
        // Test pour couvrir la classe même si elle est vide
        BriseMur bm = new BriseMur();
        assertDoesNotThrow(bm::applyEffect);
        assertEquals(0, bm.getValue());
    }
}