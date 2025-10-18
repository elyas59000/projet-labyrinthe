package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.Joueur;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class SceneScoreControleur {

    @FXML
    private TableColumn<?, ?> avgTimePlayer;

    @FXML
    private TableColumn<?, ?> namePlayer;

    @FXML
    private TableColumn<?, ?> nbStarPlayer;

    @FXML
    private TableColumn<?, ?> nbStepPlayer;

    @FXML
    private TableView<Joueur> tabScores;

    @FXML
    void retourScenePrecedente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneMenuPrincipal");
    }

}
