package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.VueLabyrintheEntier;
import J4.sae_labyrinthe.vue.VueLabyrinthePartiel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class SceneLabyrintheControleur {

    private final int RANGE =1;
    private final ModeleJeu modele = ModeleJeuSingleton.getInstance();
    private final VueLabyrintheEntier vue = new VueLabyrintheEntier(modele);

    private final VueLabyrinthePartiel vuePartiel=new VueLabyrinthePartiel(modele, RANGE);

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

    @FXML
    void initialize() {
        //affichageLocal();
        affichageEntier();
        deplacement();

    }

    @FXML
    void afficherMenuPause(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneModeLibre");
    }

    private void affichageEntier(){
        zoneLaby.getChildren().add(vue.getLabEntier());
        zoneLaby.setAlignment(Pos.CENTER);
        zoneLaby.setPadding(new Insets(5));
        vue.getLabEntier().setAlignment(Pos.CENTER);

        zoneLaby.setFocusTraversable(true);
        zoneLaby.requestFocus();
        vue.update();
    }
    private void affichageLocal() {
        zoneLaby.getChildren().add(vuePartiel.getLabPartiel());
        zoneLaby.setAlignment(Pos.CENTER);
        zoneLaby.setPadding(new Insets(5));
        vuePartiel.getLabPartiel().setAlignment(Pos.CENTER);
        zoneLaby.setFocusTraversable(true);
        zoneLaby.requestFocus();
    }

    private void deplacement(){

        zoneLaby.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (oldScene != null) {
                oldScene.removeEventHandler(KeyEvent.KEY_PRESSED, this::deplacerJoueur);
            }

            // On attache les handlers à la nouvelle scène
            if (newScene != null) {
                newScene.addEventHandler(KeyEvent.KEY_PRESSED, this::deplacerJoueur);

                // On s'assure que le focus est bien sur la zone du labyrinthe
                Platform.runLater(() -> zoneLaby.requestFocus());
            }
        });

    }

    private void deplacerJoueur(KeyEvent event){
        String direction = correspodanceTouche(event.getCode());
        this.modele.deplacerJoueur(direction);
        System.out.println(this.modele.getPositionJoueur().getX() + " " + this.modele.getPositionJoueur().getY());
        if((this.modele.getPositionJoueur().getX() == this.modele.getSortie().getX())  && (this.modele.getPositionJoueur().getY()) == this.modele.getSortie().getY()){
            try {
                SceneManager.chargerScene("SceneMenuPrincipal");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String correspodanceTouche(KeyCode event){
        String direction = "";
        if(KeyCode.UP.equals(event)){
            direction= "z";
        } else if (KeyCode.DOWN.equals(event)) {
            direction= "s";
        }else if (KeyCode.LEFT.equals(event)) {
            direction= "q";
        }else if (KeyCode.RIGHT.equals(event)) {
            direction= "d";
        }else {
            direction = event.getChar();
        }
        return direction;
    }
}
