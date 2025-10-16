package J4.sae_labyrinthe;

import J4.sae_labyrinthe.controleur.ControleurJeu;
import J4.sae_labyrinthe.modele.IModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.IObserver;
import J4.sae_labyrinthe.vue.VueLabyrintheEntier;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception{
        primaryStage=stage;
        SceneManager.chargerScene("SceneMenuPrincipal");
        stage.setTitle("Jeu du Labyrinthe ©");
        IModeleJeu modele = new ModeleJeu();
        modele.lancerPartieLibre(10,10,70);
        IObserver vue1 = new VueLabyrintheEntier(modele);
        ControleurJeu controller = new ControleurJeu(modele,vue1);
        modele.add(vue1);
        stage.show();
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}