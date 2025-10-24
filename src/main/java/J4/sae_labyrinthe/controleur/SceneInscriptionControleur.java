package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.exception.ValidationException;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SaisieValidation;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Contrôleur de la scène d'inscription. Gère la création d'un nouveau joueur
 * en validant les champs saisis et en affichant les erreurs éventuelles.
 *
 * @author Nathan Philippe, Clément Roty
 */
public class SceneInscriptionControleur {

    private final ModeleJeu modeleJeu= ModeleJeuSingleton.getInstance();

    @FXML
    private TextField champLogin;

    @FXML
    private PasswordField champsMDP;

    @FXML
    private PasswordField champsMDPConf;

    @FXML
    private Text messageErreur;

    /**
     * Retourne à la scène de connexion.
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void retourScenePrecedente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneConnexion");
    }

    /**
     * Valide les champs d'inscription et crée un nouveau joueur si la validation
     * réussit. Affiche un message d'erreur en cas d'échec.
     *
     * @param event clic
     * @throws ValidationException levée par la validation des champs si invalide
     */
    @FXML
    void validerInscription(MouseEvent event) throws ValidationException {
        String login = champLogin.getText();
        String mdp = champsMDP.getText();
        String mdpConf = champsMDPConf.getText();
        try{
            SaisieValidation.validationInscription(login, mdp, mdpConf);
            modeleJeu.creerJoueur(login, mdp);
            SceneManager.chargerScene("SceneMenuPrincipal");
        }catch(ValidationException e){
            messageErreur.fillProperty().setValue(Color.RED);
            messageErreur.setText(e.getMessage());
        } catch (IOException e) {
            System.out.println("Erreur de lecture");
        }

    }

}
