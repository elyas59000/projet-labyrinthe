package J4.sae_labyrinthe.controleur;

import java.io.IOException;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.ParametresContreLaMontre;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;

public class SceneContreLaMontreControleur {

    @FXML
    void jouerContreLaMontre() {
        ParametresContreLaMontre params = ParametresContreLaMontre.getInstance();

        if (params.getLargeur() == 0) {
            params.genererAleatoire();
        }

        ModeleJeuSingleton.getInstance().lancerContreLaMontre(params);

        try {
            SceneManager.chargerScene("SceneLabyrinthe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ouvrirParametres() {
        try {
            SceneManager.chargerScene("SceneParametresContreLaMontre");
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