package J4.sae_labyrinthe;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class App extends Application {

    private final KeyCode fullscreen=KeyCode.F11;

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception{
        primaryStage=stage;
        primaryStage.setTitle("Jeu du Labyrinthe ©");

        SceneManager.chargerScene("SceneMenuPrincipal");
        primaryStage.setResizable(true);
        primaryStage.setFullScreen(true);
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == fullscreen) {
                primaryStage.setFullScreen(!primaryStage.isFullScreen());
            }
        });

        primaryStage.show();
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}