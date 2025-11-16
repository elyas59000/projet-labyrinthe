package J4.sae_labyrinthe.controleur;

import java.io.IOException;
import java.time.LocalDate;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.utils.SaveScoreDuJour;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.utils.ScoreDuJour;
import J4.sae_labyrinthe.vue.IObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class SceneLabyrintheDuJourControleur implements IObserver {

    private final ModeleJeu modele = ModeleJeuSingleton.getInstance();

    @FXML
    private Button btnJouer, btnScore;
    @FXML
    private Text txtDescription;

    @FXML
    void initialize() {
        txtDescription.setText("Labyrinthe du jour - " + LocalDate.now());
    }

    @FXML
    void jouerLabyrintheDuJour() {
        modele.lancerLabyrintheDuJour();
        try {
            modele.remove(this);
            SceneManager.chargerScene("SceneLabyrinthe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void terminerLabyrintheDuJour() {
        long temps = modele.terminerLabyrintheDuJour();
        String nom = modele.getJoueurActuel().getNom();
        SaveScoreDuJour.sauvegarderScore(new ScoreDuJour(nom, temps));
    }

    @FXML
    void ouvrirScoresDuJour() {
        try {
            modele.remove(this);
            SceneManager.chargerScene("SceneScoreDuJour");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void revenirMenu() {
        try {
            modele.remove(this);
            SceneManager.chargerScene("SceneModeSupplementaire");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

    }
}
