package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SaveScoreDuJour;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.utils.ScoreDuJour;
import javafx.fxml.FXML;

public class SceneLabyrintheDuJourControleur  {

    private final ModeleJeu modele = ModeleJeuSingleton.getInstance();


    @FXML
    void jouerLabyrintheDuJour() {
        modele.lancerLabyrintheDuJour();
        try {
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
            SceneManager.chargerScene("SceneScoreDuJour");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void revenirMenu() {
        try {
            SceneManager.chargerScene("SceneModeSupplementaire");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
