package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SceneModeLibreControleur {

    @FXML
    private Spinner<?> champsHauteur;

    @FXML
    private Spinner<?> champsLargeur;

    @FXML
    private Spinner<?> champsTauxMur;

    @FXML
    void lancerPartieLibre(MouseEvent event) {

    }

    @FXML
    void retourScenePrecedente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneMenuPrincipal");
    }

}
