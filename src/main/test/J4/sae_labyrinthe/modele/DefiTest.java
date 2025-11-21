package J4.sae_labyrinthe.modele;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DefiTest {

    @Test
    void testConstructeurComplet() {
        // Test du constructeur maître avec tous les arguments
        Defi defi = new Defi("Difficile", 20, 30, 50.5, 10, true);

        assertEquals("Difficile", defi.getDifficulte());
        assertEquals(20, defi.getCote());
        assertEquals(30, defi.getCote2());
        assertEquals(50.5, defi.getPourcentageMurs());
        assertEquals(10, defi.getDistance());
        assertTrue(defi.estReussi());
    }

    @Test
    void testConstructeurSimplifie_CarreAvecPourcentage() {
        // Constructeur : Defi(String difficulte, int cote, double pourcentageMurs)
        Defi defi = new Defi("Moyen", 15, 20.0);

        assertEquals(15, defi.getCote());
        assertEquals(15, defi.getCote2(), "Si cote2 n'est pas précisé, il doit être égal à cote.");
        assertEquals(20.0, defi.getPourcentageMurs());
        assertFalse(defi.estReussi(), "Par défaut, le défi ne doit pas être réussi.");
        assertEquals(0, defi.getDistance(), "Par défaut, la distance doit être 0.");
    }

    @Test
    void testSetReussi() {
        Defi defi = new Defi("Facile", 10, 10);
        assertFalse(defi.estReussi());

        defi.setReussi(true);
        assertTrue(defi.estReussi());
    }
}