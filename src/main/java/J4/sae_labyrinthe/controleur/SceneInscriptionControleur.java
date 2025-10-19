package J4.sae_labyrinthe.controleur;

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

import java.io.IOException;

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

    @FXML
    void retourScenePrecedente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneConnexion");
    }

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
