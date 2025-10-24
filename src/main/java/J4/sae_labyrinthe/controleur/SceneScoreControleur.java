package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.modele.Joueur;
import J4.sae_labyrinthe.utils.Save;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableColumn<Joueur, String> namePlayer;

    @FXML
    private TableColumn<Joueur, Integer> nbStarPlayer;

    @FXML
    private TableColumn<Joueur, Integer> nbStepPlayer;

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
    @FXML
    void initialize() throws IOException {
        tabScores.getItems().clear();
        affficherNom();
        afficherEtape();
        afficherDefi();
    }

    void affficherNom() throws IOException {
        namePlayer.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ObservableList<Joueur> list = FXCollections.observableArrayList(Save.loadSave());
        tabScores.setItems(list);
    }

    void afficherEtape() throws IOException {
        nbStepPlayer.setCellValueFactory(new PropertyValueFactory<>("etapeFini"));
        ObservableList<Joueur> list = FXCollections.observableArrayList(Save.loadSave());
        tabScores.setItems(list);
    }

    void afficherDefi() throws IOException {
        nbStarPlayer.setCellValueFactory(new PropertyValueFactory<>("score"));
        ObservableList<Joueur> list = FXCollections.observableArrayList(Save.loadSave());
        tabScores.setItems(list);
    }
}
