package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.IObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SceneProgrControleur implements IObserver {

    private final ModeleJeu MODELJEU = ModeleJeuSingleton.getInstance();
    @FXML
    private Button etape1;

    @FXML
    private Button etape2;

    @FXML
    private Button etape3;

    @FXML
    private void initialize() {
        MODELJEU.add(this);
        update();
    }


    @FXML
    void ouvrirEtape1(MouseEvent event) throws IOException {
        MODELJEU.setEtapeChoisi(1);
        SceneManager.chargerScene("SceneDifficulte");
    }

    @FXML
    void ouvrirEtape2(MouseEvent event) throws IOException {
        MODELJEU.setEtapeChoisi(2);
        SceneManager.chargerScene("SceneDifficulte");
    }

    @FXML
    void ouvrirEtape3(MouseEvent event) throws IOException {
        MODELJEU.setEtapeChoisi(3);
        SceneManager.chargerScene("SceneDifficulte");
    }

    @FXML
    void retourScenePrecendente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneMenuPrincipal");
    }

    @Override
    public void update() {
        if(MODELJEU.etapeJoueur() >= 2 ) this.etape2.setDisable(false);
        if(MODELJEU.etapeJoueur() >= 3 ) this.etape3.setDisable(false);
    }
}
