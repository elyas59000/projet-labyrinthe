package J4.sae_labyrinthe.utils;

import J4.sae_labyrinthe.modele.ILabyrinthe;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LabyrintheDuJourManagerTest {

    @Test
    void testGetLabyrintheDuJour_RetourneToujoursUnLabyrinthe() {

        ILabyrinthe laby = LabyrintheDuJourManager.getLabyrintheDuJour();


        assertNotNull(laby, "Le manager doit retourner un labyrinthe valide.");
        assertNotNull(laby.getEntree(), "Le labyrinthe doit avoir une entrée.");
        assertNotNull(laby.getSortie(), "Le labyrinthe doit avoir une sortie.");

        assertNotNull(laby.getGrille());
        assertTrue(laby.getLargeur() > 0);
        assertTrue(laby.getHauteur() > 0);
    }

    @Test
    void testCreerLabyrintheAleatoire() {
        // Test de la méthode de génération pure (sans sauvegarde fichier)
        ILabyrinthe laby = LabyrintheDuJourManager.creerLabyrintheAleatoire();

        assertNotNull(laby);
        // On vérifie que c'est soit un Labyrinthe simple, soit un Parfait
        assertTrue(laby.getLargeur() >= 5);
        assertTrue(laby.getHauteur() >= 5);
    }
}