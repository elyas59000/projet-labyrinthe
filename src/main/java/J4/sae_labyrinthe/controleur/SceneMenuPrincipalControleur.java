package J4.sae_labyrinthe.controleur;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class SceneMenuPrincipalControleur {

    @FXML
    private Text texteIndicationDeConnection;

    @FXML
    void ouvrirSceneConnexion(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneConnexion");
    }

    @FXML
    void ouvrirSceneLibre(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneModeLibre");
    }

    @FXML
    void ouvrirSceneProgression(MouseEvent event) {

    }

    @FXML
    void ouvrirSceneRegleDuJeu(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneRegleDuJeu");
    }

    @FXML
    void ouvrirSceneScores(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneScore");
    }

    @FXML
    void quitterApp(MouseEvent event) {
        System.out.println("Fermeture de l'application en cours");
        Platform.exit();
        System.exit(0);
    }

}