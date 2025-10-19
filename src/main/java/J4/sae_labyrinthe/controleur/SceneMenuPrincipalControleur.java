package J4.sae_labyrinthe.controleur;
import J4.sae_labyrinthe.modele.Joueur;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.IObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;

public class SceneMenuPrincipalControleur implements IObserver {

    private final ModeleJeu modeleJeu=ModeleJeuSingleton.getInstance();

   // private final Image imagePPDefault = new Image("@../assets/icons/rules.png");


    @FXML
    private void initialize() {
        modeleJeu.add(this);
        update();
    }
    @FXML
    private ImageView iconPP;

    @FXML
    private Button buttonModeProgression;

    @FXML
    private Text textHintConnexion;

    @FXML
    void ouvrirSceneConnexion(MouseEvent event) throws IOException {
        modeleJeu.remove(this);
        if(modeleJeu.getJoueurActuel()!=null){
            SceneManager.chargerScene("SceneInfosCompte");
        }else {
            SceneManager.chargerScene("SceneConnexion");
        }
    }

    @FXML
    void ouvrirSceneLibre(MouseEvent event) throws IOException {
        modeleJeu.remove(this);
        SceneManager.chargerScene("SceneModeLibre");
    }

    @FXML
    void ouvrirSceneProgression(MouseEvent event) {
        modeleJeu.remove(this);
    }

    @FXML
    void ouvrirSceneRegleDuJeu(MouseEvent event) throws IOException {
        modeleJeu.remove(this);
        SceneManager.chargerScene("SceneRegleDuJeu");
    }

    @FXML
    void ouvrirSceneScores(MouseEvent event) throws IOException {
        modeleJeu.remove(this);
        SceneManager.chargerScene("SceneScore");
    }

    @FXML
    void quitterApp(MouseEvent event) {
        System.out.println("Fermeture de l'application en cours");
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void update() {
        Joueur joueur = modeleJeu.getJoueurActuel();
        Platform.runLater(() -> {
            if (joueur == null) {
                textHintConnexion.setText("Déconnecté");
                textHintConnexion.setFill(Color.RED);
                buttonModeProgression.setDisable(true);
                //conPP.setImage(imagePPDefault);

            } else {
                textHintConnexion.setText("Connecté – " + joueur.getNom());
                textHintConnexion.setFill(Color.GREEN);
                buttonModeProgression.setDisable(false);
                //iconPP.setImage(modeleJeu.getJoueurActuel().getImagePP());

            }
        });
    }
}