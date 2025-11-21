package J4.sae_labyrinthe.modele;

import J4.sae_labyrinthe.exception.ValidationException;
import J4.sae_labyrinthe.vue.Biome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class ModeleJeuTest {

    private ModeleJeu modele;

    @BeforeEach
    void setUp() {
        modele = new ModeleJeu();
        modele.nouveauListItem();
        modele.nouveauMapItem();

    }


    @Test
    void testCreerJoueur_Succes() throws ValidationException, IOException {

        Joueur j = modele.creerJoueur("nouveauJoueurTest", "mdp123");


        assertNotNull(j);
        assertEquals("nouveauJoueurTest", j.getNom());
        assertEquals(j, modele.getJoueurActuel(), "Le joueur créé doit être connecté automatiquement.");
        assertTrue(modele.getJoueurs().contains(j), "Le joueur doit être dans la liste.");
    }

    @Test
    void testCreerJoueur_Doublon_LeveException() throws ValidationException, IOException {
        modele.creerJoueur("doublon", "mdp");

        assertThrows(ValidationException.class, () -> {
            modele.creerJoueur("doublon", "autreMdp");
        }, "Créer un pseudo existant doit lever une exception.");
    }

    @Test
    void testConnexionJoueur_Succes() throws ValidationException, IOException {
        modele.creerJoueur("joueurCo", "mdp");
        modele.deconnexionJoueur(); // On le déconnecte d'abord
        assertNull(modele.getJoueurActuel());


        modele.connexionJoueur("joueurCo", "mdp");

        assertNotNull(modele.getJoueurActuel());
        assertEquals("joueurCo", modele.getJoueurActuel().getNom());
    }

    @Test
    void testConnexionJoueur_MauvaisMdp() throws ValidationException, IOException {
        modele.creerJoueur("joueurMdp", "bonMdp");

        assertThrows(ValidationException.class, () -> {
            modele.connexionJoueur("joueurMdp", "mauvaisMdp");
        });
    }

    @Test
    void testDeconnexionJoueur() throws ValidationException, IOException {
        modele.creerJoueur("joueurDeco", "mdp");
        assertNotNull(modele.getJoueurActuel());
        modele.deconnexionJoueur();
        assertNull(modele.getJoueurActuel());
    }


    @Test
    void testLancerPartieLibre() {

        modele.lancerPartieLibre(10, 10, 0, 5, Biome.DESERT, true);


        assertNotNull(modele.getLabyrinthe());
        assertTrue(modele.isPartieLibre());
        assertTrue(modele.isAvecBrouillard()); // Vérifie le getter du brouillard
        assertEquals(Biome.DESERT, modele.getBiome());
        assertNotNull(modele.getPositionJoueur(), "Le joueur doit avoir une position.");
        assertEquals(modele.getLabyrinthe().getEntree(), modele.getPositionJoueur());
    }

    @Test
    void testLancerPartieProgression() throws ValidationException, IOException {

        modele.creerJoueur("progressionMan", "mdp");
        modele.setEtapeChoisi(1);
        modele.setDefiChoisi(1); // Facile

        modele.lancerPartieProgression();


        assertNotNull(modele.getLabyrinthe());
        assertFalse(modele.isPartieLibre());
        // Vérifie que le labyrinthe a bien une étape
        assertNotNull(modele.getLabyrinthe().getEtape());
        assertEquals(1, modele.etapeChoisi());
    }

    /*@Test
    void testDeplacerJoueur_MouvementValide() {
        // Arrange : Labyrinthe vide (0% murs) pour faciliter le mouvement
        modele.lancerPartieLibre(10, 10, 0, 0, Biome.DESERT, false);
        Cellule depart = modele.getPositionJoueur();

        // Act : Déplacement à droite ("d")
        modele.deplacerJoueur("d");

        // Assert
        Cellule arrivee = modele.getPositionJoueur();

        // Si le départ n'était pas collé à un mur, la position doit changer
        if (depart.getX() < modele.getLabyrinthe().getLargeur() - 1) {
            assertNotEquals(depart, arrivee, "Le joueur aurait dû bouger.");
        }
    }*/

    @Test
    void testDeplacerJoueur_DirectionInconnue() {
        // Arrange
        modele.lancerPartieLibre(10, 10, 0, 0, Biome.DESERT, false);

        assertNotNull(modele.getPositionJoueur(), "La position de départ ne doit pas être nulle");
        Cellule depart = modele.getPositionJoueur();

        modele.deplacerJoueur("zizou");

        // Assert
        assertEquals(depart, modele.getPositionJoueur(), "Le joueur ne doit pas bouger avec une commande invalide.");
    }



    @Test
    void testGenererItems() {
        modele.lancerPartieLibre(20, 20, 0, 0, null, false);


        modele.genererItems(5);
        assertFalse(modele.getItemLabyrinthe().isEmpty(), "La map d'items ne devrait pas être vide.");
        assertEquals(5, modele.getItemLabyrinthe().size(), "Il devrait y avoir 5 items générés.");
    }

    @Test
    void testPrendreItem() {
        modele.lancerPartieLibre(10, 10, 0, 0, null, false);

        modele.genererItems(1);


        modele.prendreItem();


        assertTrue(modele.getItemLabyrinthe().isEmpty(), "L'item aurait dû être retiré du sol.");
        assertEquals(1, modele.getListItem().size(), "Le joueur aurait dû récupérer l'item.");
    }


    @Test
    void testSingleton() {
        ModeleJeu m1 = ModeleJeuSingleton.getInstance();
        ModeleJeu m2 = ModeleJeuSingleton.getInstance();
        assertNotNull(m1);
        assertSame(m1, m2, "Le Singleton doit toujours retourner la même instance.");
    }
}