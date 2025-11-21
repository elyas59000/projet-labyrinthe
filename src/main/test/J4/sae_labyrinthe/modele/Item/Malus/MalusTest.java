package J4.sae_labyrinthe.modele.Item.Malus;

import J4.sae_labyrinthe.modele.ModeleJeu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MalusTest {

    private ModeleJeu modele;

    @BeforeEach
    void setUp() {
        modele = new ModeleJeu();
        // (Largeur, Hauteur, TauxMur, Distance, Biome, Brouillard)
        modele.lancerPartieLibre(10, 10, 0, 0, null, false);
        // Initialisation de la liste d'items pour éviter les crashs lors des removeSelf()
        modele.nouveauListItem();
    }

    // --- Tests pour MoinsVision ---

    @Test
    void testMoinsVision_ReduitLaVision() {
        // Arrange
        modele.setVisionRange(5);
        MoinsVision malus = new MoinsVision(modele, 2);

        malus.applyEffect();
        assertEquals(3, modele.getVisionRange(), "5 - 2 devrait donner 3 de vision.");

        assertEquals("moinsvision", malus.getNom());
        assertEquals("MoinsVision", malus.toString());

        assertEquals(1, malus.isBonus());
    }

    @Test
    void testMoinsVision_LimiteMinimumUn() {
        modele.setVisionRange(2);
        MoinsVision grosMalus = new MoinsVision(modele, 10); // Tente d'enlever 10

        grosMalus.applyEffect();

        assertEquals(1, modele.getVisionRange(), "La vision ne doit pas descendre en dessous de 1.");
    }

    @Test
    void testMoinsVision_RemoveSelfRestaurerVision() {
        modele.setVisionRange(5);
        MoinsVision malus = new MoinsVision(modele, 2);
        malus.applyEffect();
        assertEquals(3, modele.getVisionRange()); // Vérif intermédiaire

        malus.removeSelf();
        assertEquals(5, modele.getVisionRange(), "Retirer le malus doit restaurer la vision initiale.");
    }

    // --- Tests pour TpDebut ---

    @Test
    void testTpDebut_TeleporteAuDepart() {

        modele.setPositionJoueur(modele.getLabyrinthe().getSortie());
        assertNotEquals(modele.getLabyrinthe().getEntree(), modele.getPositionJoueur());

        TpDebut malus = new TpDebut(modele);
        malus.applyEffect();

        assertEquals(modele.getLabyrinthe().getEntree(), modele.getPositionJoueur(), "Le joueur doit être revenu à l'entrée.");
        assertEquals("tpdebut", malus.getNom());
        assertEquals(-1, malus.isBonus());
    }

    // --- Tests pour MixCtrl ---

    @Test
    void testMixCtrl_ComportementParDefaut() {
        MixCtrl mix = new MixCtrl();

        assertEquals(0, mix.getValue());
        assertEquals(0, mix.isBonus());
        assertEquals("mixctrl", mix.getNom());
        assertEquals("MixCtrl", mix.toString());

        assertDoesNotThrow(mix::applyEffect);
        assertDoesNotThrow(mix::removeSelf);
        assertDoesNotThrow(mix::resetApplied);
    }
}