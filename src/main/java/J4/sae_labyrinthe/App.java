package J4.sae_labyrinthe;

import J4.sae_labyrinthe.vue.VueLabyrintheEntier;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        //FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("SceneMenuPrincipal.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        //stage.setTitle("Hello!");
        //tage.setScene(scene);
        //stage.show();
        new SceneMenuPrincipal();
    }

    public static void main(String[] args) {
        launch();
    }
}