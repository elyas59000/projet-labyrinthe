package J4.sae_labyrinthe.controleur;
import java.io.IOException;

import J4.sae_labyrinthe.modele.Joueur;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.IObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Contrôleur du menu principal de l'application.
 *
 * Gère la navigation vers les différentes scènes (connexion, mode libre,
 * progression, règles, scores) et met à jour l'interface selon l'état de
 * connexion du joueur courant. Implémente `IObserver` pour recevoir les
 * notifications du modèle lorsque l'état du joueur change.
 *
 * @author Clément Roty, Nathan Philippe
 */
public class SceneMenuPrincipalControleur implements IObserver {

    private final ModeleJeu modeleJeu=ModeleJeuSingleton.getInstance();



    @FXML
    private void initialize() {
        modeleJeu.add(this);
        update();
    }
    @FXML
    public Button buttonBoutique;
    @FXML
    private ImageView iconPP;

    @FXML
    private Button buttonModeProgression;

    @FXML
    private Button buttonModeSupplementaire;

    @FXML
    private Text textHintConnexion;

    /**
     * Ouvre la scène de connexion ou, si un joueur est connecté, la page
     * d'information du compte.
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void ouvrirSceneConnexion(MouseEvent event) throws IOException {
        modeleJeu.remove(this);
        if(modeleJeu.getJoueurActuel()!=null){
            SceneManager.chargerScene("SceneInfosCompte");
        }else {
            SceneManager.chargerScene("SceneConnexion");
        }
    }

    /**
     * Ouvre la scène du mode libre.
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void ouvrirSceneLibre(MouseEvent event) throws IOException {
        modeleJeu.remove(this);
        SceneManager.chargerScene("SceneModeLibre");
    }

    /**
     * Ouvre la scène de progression (sélection des défis).
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void ouvrirSceneProgression(MouseEvent event) throws IOException {
        modeleJeu.remove(this);
        SceneManager.chargerScene("SceneProg");
    }

    /**
     * Ouvre la scène affichant les règles du jeu.
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void ouvrirSceneRegleDuJeu(MouseEvent event) throws IOException {
        modeleJeu.remove(this);
        SceneManager.chargerScene("SceneRegleDuJeu");
    }

    /**
     * Ouvre la scène des scores.
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void ouvrirSceneScores(MouseEvent event) throws IOException {
        modeleJeu.remove(this);
        SceneManager.chargerScene("SceneScore");
    }

    /**
     * Quitte l'application proprement.
     *
     * @param event clic
     */
    @FXML
    void quitterApp(MouseEvent event) {
        System.out.println("Fermeture de l'application en cours");
        Platform.exit();
        System.exit(0);
    }

    /**
     * Ouvre la scène des modes supplémentaires.
     *
     * @param event clic
     * @throws IOException si le chargement échoue
     */
    @FXML
    void ouvrirSceneModeSupplementaire(MouseEvent event) throws IOException {
        modeleJeu.remove(this);
        SceneManager.chargerScene("SceneModeSupplementaire");
    }


    /**
     * Mise à jour demandée par le modèle : met à jour l'affichage selon le joueur courant.
     * Cette méthode s'exécute sur le thread JavaFX via Platform.runLater.
     */
    @Override
    public void update() {
        Joueur joueur = modeleJeu.getJoueurActuel();
            if (joueur == null) {
                textHintConnexion.setText("Déconnecté");
                textHintConnexion.setFill(Color.RED);
                buttonModeProgression.setDisable(true);
                //conPP.setImage(imagePPDefault);
                buttonModeSupplementaire.setDisable(true);
                buttonBoutique.setDisable(true);

            } else {
                textHintConnexion.setText("Connecté – " + joueur.getNom());
                textHintConnexion.setFill(Color.GREEN);
                buttonModeProgression.setDisable(false);
                //iconPP.setImage(modeleJeu.getJoueurActuel().getImagePP());
                buttonModeSupplementaire.setDisable(false);
                buttonBoutique.setDisable(false);
            }

    }

    public void ouvrirSceneBoutique(MouseEvent mouseEvent) throws IOException {
        modeleJeu.remove(this);
        SceneManager.chargerScene("SceneBoutique");
    }
}