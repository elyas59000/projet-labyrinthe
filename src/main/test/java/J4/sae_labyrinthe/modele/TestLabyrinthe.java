package J4.sae_labyrinthe.modele;

import J4.sae_labyrinthe.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestLabyrinthe {
    private Labyrinthe labyrinthe;
    private IModeleJeu modele;
    @BeforeEach
    void setUp() {
        modele = new ModeleJeu();
        labyrinthe = new Labyrinthe(15, 20, 30);
    }

    @Test
    void testCreationLabyrinth_Dimensions() {
        assertEquals(15, labyrinthe.getLargeur(), "La largeur devrait être de 15.");
        assertEquals(20, labyrinthe.getHauteur(), "La hauteur devrait être de 20.");

    }

    @Test
    void testCreerJoueur() throws ValidationException {
        Joueur joueur_créer = new Joueur("a","b");
        assertNotNull(joueur_créer);
        assertEquals("a",joueur_créer.getNom());
        assertEquals("b",joueur_créer.getMdp());
    }


}
