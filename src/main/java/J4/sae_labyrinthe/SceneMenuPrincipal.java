package J4.sae_labyrinthe;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneMenuPrincipal extends Stage {

    public SceneMenuPrincipal() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("SceneMenuPrincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        this.setTitle("Hello!");
        this.setScene(scene);
        this.show();
    }
}
