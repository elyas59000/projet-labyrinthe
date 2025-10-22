package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.IVueLabyrinthe;
import J4.sae_labyrinthe.vue.VueLabyrintheBase;
import J4.sae_labyrinthe.vue.VueLabyrintheEntier;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SceneModeLibreControleur {

    SpinnerValueFactory<Integer> svfHauteur = new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 100, 20);
    SpinnerValueFactory<Integer> svfLargeur = new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 100, 20);
    SpinnerValueFactory<Integer> svfMur = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 50, 10);

    @FXML
    private Spinner<Integer> champsHauteur;

    @FXML
    private Spinner<Integer> champsLargeur;

    @FXML
    private Spinner<Integer> champsTauxMur;

    private final ModeleJeu modeleJeu= ModeleJeuSingleton.getInstance();

    @FXML
    void initialize() {
        champsHauteur.setValueFactory(svfHauteur);
        champsLargeur.setValueFactory(svfLargeur);
        champsTauxMur.setValueFactory(svfMur);
    }

    @FXML
    void lancerPartieLibre(MouseEvent event) throws IOException {
        modeleJeu.lancerPartieLibre(champsHauteur.getValue(), champsLargeur.getValue(), champsTauxMur.getValue());
        VueLabyrintheBase entier = new VueLabyrintheEntier(modeleJeu);
        modeleJeu.add(entier);
        ControleurLabyrinthe controleur = new ControleurLabyrinthe(modeleJeu, entier);
        SceneManager.chargerScene(entier);      // TODO : mettre le labyrinthe jeu libre
    }



    @FXML
    void retourScenePrecedente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneMenuPrincipal");
    }


}
