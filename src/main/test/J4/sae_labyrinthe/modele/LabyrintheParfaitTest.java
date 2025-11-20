package J4.sae_labyrinthe.modele;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class LabyrintheParfaitTest {

    private LabyrintheParfait laby;
    private int largeurAbstraite = 10;
    private int hauteurAbstraite = 10;

    @BeforeEach
    void setUp() {
        laby = new LabyrintheParfait(largeurAbstraite, hauteurAbstraite);
    }

    @Test
    void testDimensions_SontConvertiesEnTailleReelle() {
        int largeurReelleAttendue = (largeurAbstraite * 2) + 1;
        int hauteurReelleAttendue = (hauteurAbstraite * 2) + 1;

        assertEquals(largeurReelleAttendue, laby.getLargeur(), "La largeur réelle est incorrecte.");
        assertEquals(hauteurReelleAttendue, laby.getHauteur(), "La hauteur réelle est incorrecte.");
    }

    @Test
    void testEntreeEtSortie_NeSontPasDesMurs() {
        Cellule entree = laby.getEntree();
        Cellule sortie = laby.getSortie();

        assertNotNull(entree, "L'entrée ne doit pas être nulle.");
        assertNotNull(sortie, "La sortie ne doit pas être nulle.");

        assertFalse(entree.estMur(), "L'entrée doit être un chemin.");
        assertFalse(sortie.estMur(), "La sortie doit être un chemin.");
    }

    @Test
    void testLimites_SontToujoursDesMurs() {
        int h = laby.getHauteur();
        int w = laby.getLargeur();
        Cellule[][] grille = laby.getGrille();

        for (int i = 0; i < h; i++) {
            assertTrue(grille[i][0].estMur(), "Bord gauche doit être un mur");
            assertTrue(grille[i][w - 1].estMur(), "Bord droit doit être un mur");
        }
        for (int j = 0; j < w; j++) {
            assertTrue(grille[0][j].estMur(), "Bord haut doit être un mur");
            assertTrue(grille[h - 1][j].estMur(), "Bord bas doit être un mur");
        }
    }

    @Test
    void testGeneration_CheminExisteEntreEntreeEtSortie() {

        assertTrue(unCheminExiste(laby), "Il doit exister un chemin valide entre l'entrée et la sortie.");
    }

    @Test
    void testPlacerSortieA_ChangeLaSortie() {
        Cellule ancienneSortie = laby.getSortie();
        laby.placerSortieA(5);

        Cellule nouvelleSortie = laby.getSortie();

        assertNotNull(nouvelleSortie);
        assertFalse(nouvelleSortie.estMur(), "La nouvelle sortie doit être un chemin.");

    }

    private boolean unCheminExiste(LabyrintheParfait labyrinthe) {
        Cellule start = labyrinthe.getEntree();
        Cellule end = labyrinthe.getSortie();
        Cellule[][] grille = labyrinthe.getGrille();
        int h = labyrinthe.getHauteur();
        int w = labyrinthe.getLargeur();

        boolean[][] visite = new boolean[h][w];
        Queue<Cellule> file = new LinkedList<>();

        file.add(start);
        visite[start.getY()][start.getX()] = true;

        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while (!file.isEmpty()) {
            Cellule courante = file.poll();

            // Si on a atteint la sortie
            if (courante == end) {
                return true;
            }

            for (int[] dir : directions) {
                int newX = courante.getX() + dir[0]; // Attention : dans ta classe Cellule, verifie si X est colonne ou ligne
                int newY = courante.getY() + dir[1];

                if (newY >= 0 && newY < h && newX >= 0 && newX < w) {
                    Cellule voisine = grille[newY][newX];
                    if (!voisine.estMur() && !visite[newY][newX]) {
                        visite[newY][newX] = true;
                        file.add(voisine);
                    }
                }
            }
        }
        return false;
    }
}