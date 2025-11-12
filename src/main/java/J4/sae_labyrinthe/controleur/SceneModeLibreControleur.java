package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;

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
    void initialize() {
        champsHauteur.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3, Integer.MAX_VALUE, 1));
        champsLargeur.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3, Integer.MAX_VALUE, 1));
        champsTauxMur.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));

        setNumericOnly(champsHauteur);
        setNumericOnly(champsLargeur);
        setNumericOnly(champsTauxMur);
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
    void lancerPartieLibre(MouseEvent event) throws IOException {;
        modeleJeu.lancerPartieLibre(champsLargeur.getValue(), champsHauteur.getValue(), champsTauxMur.getValue());
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
