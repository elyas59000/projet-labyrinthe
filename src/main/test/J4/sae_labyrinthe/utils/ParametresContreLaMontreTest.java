package J4.sae_labyrinthe.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParametresContreLaMontreTest {

    @Test
    void testSingleton() {
        ParametresContreLaMontre p1 = ParametresContreLaMontre.getInstance();
        ParametresContreLaMontre p2 = ParametresContreLaMontre.getInstance();
        assertNotNull(p1);
        assertSame(p1, p2);
    }

    @Test
    void testDefinirParametres() {
        ParametresContreLaMontre p = new ParametresContreLaMontre();
        p.definirParametres(20, 30, 15, 5, 60, true);

        assertEquals(20, p.getLargeur());
        assertEquals(30, p.getHauteur());
        assertEquals(15, p.getTauxMur());
        assertEquals(5, p.getDistanceMin());
        assertEquals(60, p.getTemps());
        assertTrue(p.isEstParfait());
    }

    @Test
    void testGenererAleatoire() {
        ParametresContreLaMontre p = new ParametresContreLaMontre();
        p.genererAleatoire();

        // On vérifie que les valeurs sont dans des bornes raisonnables
        assertTrue(p.getLargeur() >= 10);
        assertTrue(p.getHauteur() >= 10);
        assertTrue(p.getTemps() >= 30);

        if (p.isEstParfait()) {
            assertTrue(p.getDistanceMin() >= 5);
        } else {
            assertTrue(p.getTauxMur() >= 10);
        }
    }

    @Test
    void testSetLargeur() {
        ParametresContreLaMontre p = new ParametresContreLaMontre();
        p.setLargeur(100);
        assertEquals(100, p.getLargeur());
    }
}