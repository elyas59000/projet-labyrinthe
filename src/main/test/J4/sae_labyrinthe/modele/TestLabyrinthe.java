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
        labyrinthe = new Labyrinthe(15, 20, 0);
    }

    @Test
    void testCreationLabyrinth_Dimensions() {
        assertEquals(15, labyrinthe.getLargeur(), "La largeur devrait être de 15.");
        assertEquals(20, labyrinthe.getHauteur(), "La hauteur devrait être de 20.");

    }

    @Test
    void testCreerJoueur() throws ValidationException {
        Joueur joueur_creer = new Joueur("a","b");
        assertNotNull(joueur_creer);
        assertEquals("a",joueur_creer.getNom());
        assertEquals("b",joueur_creer.getMdp());
    }

    @Test
    void testDeplacementJoueur(){
        modele.lancerPartieLibre(15,20,0);
        assertEquals(0,((ModeleJeu)modele).getPositionJoueur().getX());
        assertEquals(0,((ModeleJeu)modele).getPositionJoueur().getY());

        modele.deplacerJoueur("d");
        Cellule nouvellePosition = ((ModeleJeu) modele).getPositionJoueur();

        assertEquals(1,nouvellePosition.getX(),"la position X devrait avoir augmenter de 1");
        assertEquals(0,nouvellePosition.getY(),"la position Y ne devrait pas augmenter");
    }

    @Test
    void testDeconnexionJoueur() throws ValidationException {
        modele.creerJoueur("a","b");
        modele.connexionJoueur("a","b");
        assertNotNull(((ModeleJeu)modele).getJoueurActuel());
        ((ModeleJeu) modele).deconnexionJoueur();
        assertNull(((ModeleJeu)modele).getJoueurActuel());
    }


}
