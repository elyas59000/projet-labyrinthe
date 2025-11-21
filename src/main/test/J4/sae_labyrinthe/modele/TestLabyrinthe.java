package J4.sae_labyrinthe.modele;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class LabyrintheTest {

    private Labyrinthe laby;
    private int largeur = 20;
    private int hauteur = 10;
    private double tauxMur = 30.0;

    @BeforeEach
    void setUp() {
        laby = new Labyrinthe(largeur, hauteur, tauxMur);
    }

    @Test
    void testDimensions() {
        // Largeur réelle = largeur + 2
        assertEquals(largeur + 2, laby.getLargeur());
        assertEquals(hauteur + 2, laby.getHauteur());
        assertEquals(tauxMur, laby.getPourcentageMurs());
    }

    @Test
    void testEntreeSortie_SontValides() {
        Cellule e = laby.getEntree();
        Cellule s = laby.getSortie();

        assertNotNull(e);
        assertNotNull(s);
        assertFalse(e.estMur(), "Entrée ne doit pas être un mur.");
        assertFalse(s.estMur(), "Sortie ne doit pas être un mur.");
        assertNotEquals(e, s, "Entrée et sortie doivent être différentes.");

        int dist = Math.abs(s.getX() - e.getX()) + Math.abs(s.getY() - e.getY());
        assertTrue(dist >= 3, "La distance Entrée-Sortie doit être >= 3.");
    }

    @Test
    void testPositionAleaCell_RetourneCelluleValide() {
        Cellule c = laby.positionAleaCell();

        assertNotNull(c);
        assertFalse(c.estMur(), "La cellule aléatoire doit être accessible (pas un mur).");

        assertNotEquals(laby.getSortie(), c);
    }

    @Test
    void testPosValide() {
        assertTrue(laby.posValide(0, 0));
        assertTrue(laby.posValide(largeur+1, hauteur+1));

        assertFalse(laby.posValide(-1, 0));
        assertFalse(laby.posValide(largeur+10, 0));
    }

    @Test
    void testSauvegarderEtCharger(@TempDir Path tempDir) {
        File fichier = tempDir.resolve("laby_standard.ser").toFile();
        String path = fichier.getAbsolutePath();

        laby.sauvegarder(path);
        assertTrue(fichier.exists());

        ILabyrinthe labyCharge = Labyrinthe.charger(path);
        assertNotNull(labyCharge);
        assertEquals(laby.getLargeur(), labyCharge.getLargeur());
        assertEquals(laby.getHauteur(), labyCharge.getHauteur());
    }
}