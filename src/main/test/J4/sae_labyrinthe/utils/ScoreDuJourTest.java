package J4.sae_labyrinthe.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScoreDuJourTest {

    @Test
    void testConstructeur_EtGetters() {
        String nomAttendu = "Champion";
        long tempsAttendu = 125; // 125 secondes

        ScoreDuJour score = new ScoreDuJour(nomAttendu, tempsAttendu);


        assertEquals(nomAttendu, score.getNom(), "Le nom n'est pas correctement stocké.");
        assertEquals(tempsAttendu, score.getTemps(), "Le temps n'est pas correctement stocké.");
    }

    @Test
    void testToString_FormatCorrect() {

        ScoreDuJour score = new ScoreDuJour("Testeur", 125);
        String affichage = score.toString();

        String attendu = "Testeur - 2m 5s";
        assertEquals(attendu, affichage, "La méthode toString ne formate pas bien les minutes/secondes.");
    }

    @Test
    void testToString_MoinsDuneMinute() {
        ScoreDuJour score = new ScoreDuJour("Rapide", 45);
        // 0m 45s
        assertEquals("Rapide - 0m 45s", score.toString());
    }
}