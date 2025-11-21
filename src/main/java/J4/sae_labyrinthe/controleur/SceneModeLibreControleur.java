package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.Biome;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    public ComboBox comboBiome;

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

    @FXML
    private CheckBox checkBrouillard;

    private boolean modeParfaitActif = false;

    @FXML
    private HBox boxTauxMur;

    @FXML
    private Button btnAleatoire;

    @FXML
    private Button btnParfait;




    @FXML
    void initialize() {
        champsHauteur.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, Integer.MAX_VALUE, 1));
        champsLargeur.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5,Integer.MAX_VALUE, 1));
        champsTauxMur.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));

        setNumericOnly(champsHauteur);
        setNumericOnly(champsLargeur);
        setNumericOnly(champsTauxMur);
        champsDistance.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1)
        );
        setNumericOnly(champsDistance);
        comboBiome.getItems().addAll("FORET", "DESERT", "PLAINE", "NEIGE", "GROTTE", "ENFER");
        comboBiome.getSelectionModel().select("DESERT");

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
        if (champsHauteur.getValue()>=champsLargeur.getValue()) {
            h=champsLargeur.getValue();
            l=champsHauteur.getValue();;
        }
        int d;
        int taux;
        boolean avecBrouillard = checkBrouillard.isSelected();
        if (modeParfaitActif) {
            d = champsDistance.getValue();
            taux = 0;
        } else {
            taux = champsTauxMur.getValue();
            d = 0;
        }
        String biomeNom = comboBiome.getSelectionModel().getSelectedItem().toString();
        Biome biomeChoisi ;
        switch (biomeNom) {
            case "DESERT" -> biomeChoisi = Biome.DESERT;
            case "FORET" -> biomeChoisi = Biome.FORET;
            case "PLAINE" -> biomeChoisi = Biome.PLAINE;
            case "NEIGE" -> biomeChoisi = Biome.NEIGE;
            case "GROTTE" -> biomeChoisi = Biome.GROTTE;
            case "ENFER" -> biomeChoisi = Biome.ENFER;
            default -> biomeChoisi = Biome.PLAINE;
        }    // valeur par défaut si problème
            modeleJeu.lancerPartieLibre(l, h, taux,d,biomeChoisi,avecBrouillard);
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
