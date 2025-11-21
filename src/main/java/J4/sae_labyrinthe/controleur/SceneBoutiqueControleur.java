package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SceneBoutiqueControleur {
    public ImageView iconCoin;

    @FXML
    void retourScenePrecendente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneMenuPrincipal");
    }
}
