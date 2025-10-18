package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SceneConnexionControleur {

    @FXML
    private TextField champLogin;

    @FXML
    private TextField champsMDP;

    @FXML
    void ouvrirSceneInscriptions(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneInscription");
    }

    @FXML
    void retourScenePrecedente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneMenuPrincipal");
    }

    @FXML
    void validerEntreesConnexion(MouseEvent event) {

    }

}

