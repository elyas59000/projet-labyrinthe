package J4.sae_labyrinthe.utils;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SaveScoreDuJourTest {

    @Test
    void testSauvegardeEtChargement_ScenarioComplet() {

        String pseudoTest = "TesteurAuto_" + System.currentTimeMillis();

        // Un score "moyen" (100 secondes)
        ScoreDuJour scoreMoyen = new ScoreDuJour(pseudoTest, 100);

        // Un score "meilleur" (50 secondes)
        ScoreDuJour scoreMeilleur = new ScoreDuJour(pseudoTest, 50);

        // Un score "pire" (200 secondes)
        ScoreDuJour scorePire = new ScoreDuJour(pseudoTest, 200);

        // - Étape 1 : Premier enregistrement
        SaveScoreDuJour.sauvegarderScore(scoreMoyen);

        // Vérification
        List<ScoreDuJour> scores1 = SaveScoreDuJour.chargerScores();
        ScoreDuJour s1 = trouverScoreParNom(scores1, pseudoTest);
        assertNotNull(s1, "Le score devrait être sauvegardé.");
        assertEquals(100, s1.getTemps(), "Le temps devrait être 100s.");

        // t - Étape 2 : On enregistre un MEILLEUR score
        SaveScoreDuJour.sauvegarderScore(scoreMeilleur);

        // Vérification : Le score doit être mis à jour
        List<ScoreDuJour> scores2 = SaveScoreDuJour.chargerScores();
        ScoreDuJour s2 = trouverScoreParNom(scores2, pseudoTest);
        assertEquals(50, s2.getTemps(), "Le score aurait dû être mis à jour (50 < 100).");

        //  Étape 3 : On enregistre un PIRE score
        SaveScoreDuJour.sauvegarderScore(scorePire);

        // Vérification : Le score NE DOIT PAS changer
        List<ScoreDuJour> scores3 = SaveScoreDuJour.chargerScores();
        ScoreDuJour s3 = trouverScoreParNom(scores3, pseudoTest);
        assertEquals(50, s3.getTemps(), "Le score ne doit pas changer car 200 > 50.");
    }

    // Petite méthode utilitaire pour retrouver notre joueur test dans la liste
    private ScoreDuJour trouverScoreParNom(List<ScoreDuJour> scores, String nom) {
        for (ScoreDuJour s : scores) {
            if (s.getNom().equals(nom)) {
                return s;
            }
        }
        return null;
    }
}