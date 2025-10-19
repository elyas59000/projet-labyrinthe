package J4.sae_labyrinthe.modele;

import J4.sae_labyrinthe.modele.IModeleJeu;
import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.ModeleJeu;
import org.junit.jupiter.api.AfterEach;
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
    void testCreationLabyrinthe_Dimensions() {
        // Assertions logiques et correctes
        assertEquals(15, labyrinthe.getLargeur(), "La largeur devrait être de 15.");
        assertEquals(20, labyrinthe.getHauteur(), "La hauteur devrait être de 20.");
    }


}
