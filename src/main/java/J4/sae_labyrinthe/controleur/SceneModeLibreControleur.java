package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.Biome;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Contrôleur de la scène "Mode Libre" permettant de lancer une partie personnalisée.
 * Les utilisateurs peuvent définir la largeur, la hauteur et le taux de murs du labyrinthe.
 *
 * @author Clément Roty, Nathan Philippe, Adam Stievenard
 */
public class SceneModeLibreControleur {

    private final ModeleJeu modeleJeu= ModeleJeuSingleton.getInstance();

    @FXML
    private Spinner<Integer> champsHauteur;

    @FXML
    private Spinner<Integer> champsLargeur;

    @FXML
    private Spinner<Integer> champsTauxMur;

    @FXML
    private HBox boxDistance;

    @FXML
    private Spinner<Integer> champsDistance;

    private boolean modeParfaitActif = false;

    @FXML
    private HBox boxTauxMur;

    @FXML
    private Button btnAleatoire;

    @FXML
    private Button btnParfait;




    @FXML
    void initialize() {
        champsHauteur.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3, Integer.MAX_VALUE, 1));
        champsLargeur.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3, Integer.MAX_VALUE, 1));
        champsTauxMur.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));

        setNumericOnly(champsHauteur);
        setNumericOnly(champsLargeur);
        setNumericOnly(champsTauxMur);
        champsDistance.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1)
        );
        setNumericOnly(champsDistance);
    }

    @FXML
    void modeAleatoire(MouseEvent event) {
        modeParfaitActif = false;

        btnParfait.getStyleClass().remove("selectedMode");

        if (!btnAleatoire.getStyleClass().contains("selectedMode")) {
            btnAleatoire.getStyleClass().add("selectedMode");
        }

        boxDistance.setVisible(false);
        boxDistance.setManaged(false);

        boxTauxMur.setVisible(true);
        boxTauxMur.setManaged(true);
    }


    @FXML
    void modeParfait(MouseEvent event) {
        modeParfaitActif = true;

        btnAleatoire.getStyleClass().remove("selectedMode");

        if (!btnParfait.getStyleClass().contains("selectedMode")) {
            btnParfait.getStyleClass().add("selectedMode");
        }

        boxDistance.setVisible(true);
        boxDistance.setManaged(true);

        boxTauxMur.setVisible(false);
        boxTauxMur.setManaged(false);
    }


    private void setNumericOnly(Spinner<Integer> spinner) {
        spinner.getEditor().setTextFormatter(new TextFormatter<Integer>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // uniquement chiffres
                return change;
            }
            return null; // rejet
        }));
    }

    /**
     * Lance une partie en mode libre avec les paramètres saisis.
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void lancerPartieLibre(MouseEvent event) throws IOException {
        int l = champsLargeur.getValue();
        int h = champsHauteur.getValue();
        int taux = champsTauxMur.getValue();
        int d = 0;
        if (modeParfaitActif) {
            d = champsDistance.getValue();
            taux = 0;
        }

        modeleJeu.lancerPartieLibre(l, h, taux, Biome.DESERT,d);
        SceneManager.chargerScene("SceneLabyrinthe");
    }

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
