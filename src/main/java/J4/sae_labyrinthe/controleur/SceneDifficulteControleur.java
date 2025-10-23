package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SceneDifficulteControleur {
    private final ModeleJeu MODELJEU = ModeleJeuSingleton.getInstance();
    @FXML
    void lancerPartieNiv1(MouseEvent event) throws IOException {
        MODELJEU.setDefiChoisi(1);
        MODELJEU.lancerPartieProgression();
        if(MODELJEU.etapeChoisi() == 3) {
            SceneManager.chargerScene("SceneLabyrintheEntier");
        }else {
            SceneManager.chargerScene("SceneLabyrintheEntier");
        }
    }

    @FXML
    void lancerPartieNiv2(MouseEvent event) throws IOException {
        MODELJEU.setDefiChoisi(2);
        MODELJEU.lancerPartieProgression();
        if(MODELJEU.etapeChoisi() == 3) {
            SceneManager.chargerScene("SceneLabyrintheEntier");
        }else {
            SceneManager.chargerScene("SceneLabyrintheEntier");
        }
    }

    @FXML
    void lancerPartieNiv3(MouseEvent event) throws IOException {
        MODELJEU.setDefiChoisi(3);
        MODELJEU.lancerPartieProgression();
        if(MODELJEU.etapeChoisi() == 3) {
            SceneManager.chargerScene("SceneLabyrintheEntier");
        }else {
            SceneManager.chargerScene("SceneLabyrintheEntier");
        }
    }

    @FXML
    void retourScenePrecedente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneMenuPrincpal");
    }

}
