package J4.sae_labyrinthe;

import J4.sae_labyrinthe.utils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception{
        primaryStage=stage;
        SceneManager.chargerScene("SceneMenuPrincipal");
        stage.setTitle("Jeu du Labyrinthe ©");
        stage.show();
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}