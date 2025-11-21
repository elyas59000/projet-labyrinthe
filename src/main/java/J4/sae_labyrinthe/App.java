package J4.sae_labyrinthe;

import J4.sae_labyrinthe.utils.CacheMemoire;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Classe principale de l'application du labyrinthe.
 * Gère le lancement de l'application et la fenêtre principale.
 * 
 * @author Clément Roty
 * @author Nathan Philippe
 */
public class App extends Application {

    private final KeyCode fullscreen=KeyCode.F11;
    private static Stage primaryStage;

    /**
     * Méthode de démarrage de l'application JavaFX.
     * Configure la fenêtre principale et charge la scène initiale.
     * 
     * @param stage La fenêtre principale de l'application
     * @throws Exception En cas d'erreur lors du chargement de la scène
     * @author Clément Roty
     */
    @Override
    public void start(Stage stage) throws Exception{
        primaryStage=stage;
        primaryStage.setTitle("Jeu du Labyrinthe ©");
        CacheMemoire.chargementTextures();
        CacheMemoire.preloadAllBiomes();
        Font.loadFont(getClass().getResourceAsStream("/J4/sae_labyrinthe/fonts/PixelGameFont.ttf"), 18);
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

    /**
     * Récupère la fenêtre principale de l'application.
     * 
     * @return La fenêtre principale
     * @author Clément Roty
     */
    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    /**
     * Point d'entrée principal de l'application.
     * 
     * @author Clément Roty
     */
    public static void main(String[] args) {
        launch();
    }
}