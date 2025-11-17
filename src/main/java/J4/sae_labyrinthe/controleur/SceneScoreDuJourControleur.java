package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.Joueur;
import J4.sae_labyrinthe.utils.SaveScoreDuJour;
import J4.sae_labyrinthe.utils.ScoreDuJour;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import J4.sae_labyrinthe.utils.SceneManager;

public class SceneScoreDuJourControleur {

    @FXML
    private TableView<ScoreDuJour> tableScores;

    @FXML
    private TableColumn<Joueur, String> colJoueur;

    @FXML
    private TableColumn<Joueur, Integer> colScore;

    @FXML
    public void initialize() {

        ArrayList<ScoreDuJour> scores;
        try {
            scores = SaveScoreDuJour.chargerScores();
        } catch (Exception e) {
            scores = new ArrayList<>();
        }

        colJoueur.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colScore.setCellValueFactory(new PropertyValueFactory<>("temps"));

        List<ScoreDuJour> scoresTries = scores.stream()
                .sorted(Comparator.comparingLong(ScoreDuJour::getTemps))
                .toList();

        tableScores.getItems().setAll(scoresTries);
    }


    @FXML
    public void revenirMenu() {
        try {
            SceneManager.chargerScene("SceneLabyrintheDuJour");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
