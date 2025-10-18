package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SceneInscriptionControleur {

    @FXML
    private TextField champLogin;

    @FXML
    private PasswordField champsMDP;

    @FXML
    private PasswordField champsMDPConf;

    @FXML
    void retourScenePrecedente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneConnexion");
    }

    @FXML
    void validerInscription(MouseEvent event) {

    }

}
