package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.modele.Joueur;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

/**
 * Contrôleur de la scène des scores.
 *
 * Affiche un tableau des joueurs avec des colonnes pour le nom, le nombre
 * d'étoiles, le nombre d'étapes et le temps moyen. Fournit également un
 * bouton pour revenir au menu principal.
 *
 * @author Clément Roty, Nathan Philippe
 */
public class SceneScoreControleur {

    @FXML
    private TableColumn<?, ?> avgTimePlayer;

    @FXML
    private TableColumn<?, ?> namePlayer;

    @FXML
    private TableColumn<?, ?> nbStarPlayer;

    @FXML
    private TableColumn<?, ?> nbStepPlayer;

    @FXML
    private TableView<Joueur> tabScores;

    /**
     * Retourne au menu principal.
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void retourScenePrecedente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneMenuPrincipal");
    }

}
