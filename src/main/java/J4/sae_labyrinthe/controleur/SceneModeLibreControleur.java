package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.VueLabyrintheEntier;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SceneModeLibreControleur {

    private final ModeleJeu modeleJeu= ModeleJeuSingleton.getInstance();

    @FXML
    private Spinner<Integer> champsHauteur;

    @FXML
    private Spinner<Integer> champsLargeur;

    @FXML
    private Spinner<Integer> champsTauxMur;


    @FXML
    void initialize() {
        champsHauteur.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
        champsLargeur.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
        champsTauxMur.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
    }

    @FXML
    void lancerPartieLibre(MouseEvent event) throws IOException {
        modeleJeu.lancerPartieLibre(champsLargeur.getValue(), champsHauteur.getValue(), champsTauxMur.getValue());
        SceneManager.chargerScene("SceneLabyrinthe");
    }

    @FXML
    void retourScenePrecedente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneMenuPrincipal");
    }

}
