package J4.sae_labyrinthe;

//import J4.sae_labyrinthe.controleur.ControleurJeu;
import J4.sae_labyrinthe.modele.IModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.IObserver;
import J4.sae_labyrinthe.vue.VueLabyrintheEntier;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.Objects;


public class App extends Application {

    private final KeyCode fullscreen=KeyCode.F11;

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception{
        primaryStage=stage;
        primaryStage.setTitle("Jeu du Labyrinthe ©");
        ModeleJeu modele = ModeleJeuSingleton.getInstance();
        /*
        IObserver vue1 = new VueLabyrintheEntier(modele);
        ControleurJeu controller = new ControleurJeu(modele,vue1);
        modele.add(vue1);
        modele.lancerPartieLibre(25,25,45);
        */

        SceneManager.chargerScene("SceneMenuPrincipal");
        primaryStage.setResizable(false);
        primaryStage.getScene().setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()) == fullscreen) {
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