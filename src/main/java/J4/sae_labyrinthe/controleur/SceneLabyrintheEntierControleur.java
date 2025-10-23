package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.VueLabyrintheEntier;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class SceneLabyrintheEntierControleur {

    private final ModeleJeu modele = ModeleJeuSingleton.getInstance();
    private final VueLabyrintheEntier vue = new VueLabyrintheEntier(modele);
    private final ControleurLabyrinthe control = new ControleurLabyrinthe(modele);

    @FXML
    private Button btnPause;

    @FXML
    private Text info1;

    @FXML
    private Text info2;

    @FXML
    private Text info3;

    @FXML
    private StackPane zoneLaby;

    @FXML
    private Text timerInfo;

    // Handlers stockés en champ pour pouvoir les retirer proprement
    private javafx.event.EventHandler<KeyEvent> keyPressedHandler;
    private javafx.event.EventHandler<KeyEvent> keyReleasedHandler;

    @FXML
    void initialize() {
        zoneLaby.getChildren().add(vue.getLabEntier());
        zoneLaby.setAlignment(Pos.CENTER);
        zoneLaby.setPadding(new Insets(5));
        vue.getLabEntier().setAlignment(Pos.CENTER);
        vue.update();

        zoneLaby.setFocusTraversable(true);
        zoneLaby.requestFocus();


        // Gestion du changement de scène
        zoneLaby.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (oldScene != null) {
                oldScene.removeEventHandler(KeyEvent.KEY_PRESSED, control::deplacerJoueur);
            }

            // On attache les handlers à la nouvelle scène
            if (newScene != null) {
                newScene.addEventHandler(KeyEvent.KEY_PRESSED, control::deplacerJoueur);

                // On s'assure que le focus est bien sur la zone du labyrinthe
                Platform.runLater(() -> zoneLaby.requestFocus());
            }
        });
    }

    @FXML
    void afficherMenuPause(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneModeLibre");
    }
}
