package J4.sae_labyrinthe.utils;

import J4.sae_labyrinthe.exception.ValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestSaisieValidation {

    // --- Tests pour validationConnexion ---

    @Test
    void testValidationConnexion_CasSucces() {
        // Arrange : Données valides
        String login = "utilisateur";
        String mdp = "motdepasse";
        assertDoesNotThrow(() -> {
            SaisieValidation.validationConnexion(login, mdp);
        });
    }

    @Test
    void testValidationConnexion_QuandLoginVide_LeveException() {
        String login = "";
        String mdp = "motdepasse";

        ValidationException e = assertThrows(ValidationException.class, () -> {
            SaisieValidation.validationConnexion(login, mdp);
        });

    }

    @Test
    void testValidationConnexion_QuandMdpVide_LeveException() {
        String login = "utilisateur";
        String mdp = "";

        ValidationException e = assertThrows(ValidationException.class, () -> {
            SaisieValidation.validationConnexion(login, mdp);
        });

        assertEquals("Le mot de passe est vide ", e.getMessage());
    }


    @Test
    void testValidationInscription_CasSucces() {
        String login = "valideLogin";
        String mdp = "valideMdp123";
        String confMdp = "valideMdp123";

        assertDoesNotThrow(() -> {
            SaisieValidation.validationInscription(login, mdp, confMdp);
        });
    }

    @Test
    void testValidationInscription_QuandConfirmationVide_LeveException() {
        String login = "utilisateur";
        String mdp = "motdepasse";
        String confMdp = "";

        ValidationException e = assertThrows(ValidationException.class, () -> {
            SaisieValidation.validationInscription(login, mdp, confMdp);
        });

        assertEquals("Le champs confirmation du mot de passe est vide", e.getMessage());
    }

    @Test
    void testValidationInscription_QuandMdpDifferent_LeveException() {
        String login = "utilisateur";
        String mdp = "motdepasse1";
        String confMdp = "motdepasse2";

        ValidationException e = assertThrows(ValidationException.class, () -> {
            SaisieValidation.validationInscription(login, mdp, confMdp);
        });

        assertEquals("Les champs mot de passe et confirmation de mot de passe ne sont pas identiques", e.getMessage());
    }

    @Test
    void testValidationInscription_QuandLoginTropCourt_LeveException() {
        String login = "id";
        String mdp = "motdepasse";
        String confMdp = "motdepasse";

        ValidationException e = assertThrows(ValidationException.class, () -> {
            SaisieValidation.validationInscription(login, mdp, confMdp);
        });

        assertEquals("L'identifiant est trop court, choisir au moins un ID de 3 caractères", e.getMessage());
    }


}