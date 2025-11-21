package J4.sae_labyrinthe.modele;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JoueurTest {

    @Test
    void testConstructeur_EtatInitial() {
        Joueur j = new Joueur("Pseudo", "Secret");

        assertEquals("Pseudo", j.getNom());
        assertEquals("Secret", j.getMdp());
        assertEquals(1, j.getEtapeActuelle());
        assertEquals(1, j.getEtapeMax());
        assertEquals(0, j.getScore());
        assertEquals(0.0, j.getTempsMoyenDansLabyrinthe());
        assertEquals(0, j.getEtapeFini());
    }

    @Test
    void testSetters_NomEtMdp() {
        Joueur j = new Joueur("Avant", "123");
        j.setNom("Apres");
        j.setMdp("456");

        assertEquals("Apres", j.getNom());
        assertEquals("456", j.getMdp());
    }

    @Test
    void testTempsMoyen() {
        Joueur j = new Joueur("Test", "mdp");
        j.setTempsMoyenDansLabyrinthe(120.5);
        assertEquals(120.5, j.getTempsMoyenDansLabyrinthe());
    }

    @Test
    void testAvancerEtape_MetAJourMax_SiNouvelleEtape() {
        Joueur j = new Joueur("Test", "mdp");
        // Etat: Actuelle=1, Max=1

        j.avancerEtape();
        assertEquals(2, j.getEtapeActuelle());
        assertEquals(2, j.getEtapeMax(), "L'étape max doit suivre la progression.");
        assertEquals(1, j.getEtapeFini(), "Le nombre d'étapes finies doit s'incrémenter.");
    }

    @Test
    void testReculerEtAvancer_EtapeMaxNeBougePas() {
        Joueur j = new Joueur("Test", "mdp");
        j.avancerEtape(); // Actuelle=2, Max=2

        j.reculerEtape(); // Actuelle=1, Max=2
        assertEquals(1, j.getEtapeActuelle());
        assertEquals(2, j.getEtapeMax(), "L'étape max ne doit pas diminuer quand on recule.");

        j.avancerEtape(); // Actuelle=2, Max=2
        assertEquals(2, j.getEtapeMax(), "L'étape max ne doit pas augmenter si on refait une étape connue.");
    }
}