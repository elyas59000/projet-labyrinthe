package J4.sae_labyrinthe.controleur;

import java.io.IOException;
import J4.sae_labyrinthe.utils.ParametresContreLaMontre;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class SceneParametresContreLaMontreControleur {

    @FXML private Slider sliderLargeur;
    @FXML private Slider sliderHauteur;
    @FXML private Slider sliderTemps;
    @FXML private CheckBox checkParfait;

    @FXML private VBox boxTauxMur;       // VBox contenant le slider Taux Mur
    @FXML private Slider sliderTauxMur;

    @FXML private VBox boxDistanceMini;  // VBox contenant le slider Distance
    @FXML private Slider sliderDistanceMin;

    @FXML
    void initialize() {
        boxDistanceMini.visibleProperty().bind(checkParfait.selectedProperty());
        boxDistanceMini.managedProperty().bind(checkParfait.selectedProperty());

        boxTauxMur.visibleProperty().bind(checkParfait.selectedProperty().not());
        boxTauxMur.managedProperty().bind(checkParfait.selectedProperty().not());

        ParametresContreLaMontre params = ParametresContreLaMontre.getInstance();
        if (params.getLargeur() > 0) {
            sliderLargeur.setValue(params.getLargeur());
            sliderHauteur.setValue(params.getHauteur());
            sliderTemps.setValue(params.getTemps());
            checkParfait.setSelected(params.isEstParfait());

            if(params.isEstParfait())
                sliderDistanceMin.setValue(params.getDistanceMin());
            else
                sliderTauxMur.setValue(params.getTauxMur());
        }
        miseAJourAffichage(params);
    }

    @FXML
    void validerParametres() {
        ParametresContreLaMontre.getInstance().definirParametres(
                (int) sliderLargeur.getValue(),
                (int) sliderHauteur.getValue(),
                (int) sliderTauxMur.getValue(),
                (int) sliderDistanceMin.getValue(),
                (int) sliderTemps.getValue(),
                checkParfait.isSelected()
        );
        revenirModeCM();
    }

    @FXML
    void revenirModeCM() {
        try {
            SceneManager.chargerScene("SceneContreLaMontre");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void resetParametres() {
        ParametresContreLaMontre params = ParametresContreLaMontre.getInstance();

        // Génère de nouvelles valeurs dans le singleton
        params.genererAleatoire();

        // Met à jour l'interface graphique immédiatement
        miseAJourAffichage(params);
    }

    private void miseAJourAffichage(ParametresContreLaMontre params) {
        sliderLargeur.setValue(params.getLargeur());
        sliderHauteur.setValue(params.getHauteur());
        sliderTemps.setValue(params.getTemps());
        checkParfait.setSelected(params.isEstParfait());

        if (params.isEstParfait()) {
            sliderDistanceMin.setValue(params.getDistanceMin());
        } else {
            sliderTauxMur.setValue(params.getTauxMur());
        }
    }

}