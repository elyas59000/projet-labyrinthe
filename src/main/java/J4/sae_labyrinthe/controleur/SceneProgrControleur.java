package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.Biome;
import J4.sae_labyrinthe.vue.IObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * Contrôleur de la scène de progression (sélection d'étapes de défi).
 *
 * L'utilisateur peut sélectionner l'étape (1,2,3). Les étapes supérieures
 * sont activées en fonction de la progression du joueur. Le contrôleur s'abonne
 * au modèle pour être notifié des changements.
 *
 * @author Clément Roty, Nathan Philippe
 */
public class SceneProgrControleur implements IObserver {

    private final ModeleJeu MODELJEU = ModeleJeuSingleton.getInstance();

    @FXML
    private Button etape1;

    @FXML
    private Button etape2;

    @FXML
    private Button etape3;

    @FXML
    private Button etape4;

    @FXML
    private Button etape5;

    @FXML
    private Button etape6;

    @FXML
    private void initialize() {
        MODELJEU.add(this);
        update();
    }

    /**
     * Ouvre la sélection de difficulté pour l'étape 1.
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void ouvrirEtape1(MouseEvent event) throws IOException {
        MODELJEU.setEtapeChoisi(1);
        MODELJEU.setBiome(Biome.DESERT);
        SceneManager.chargerScene("SceneDifficulte");
    }

    /**
     * Ouvre la sélection de difficulté pour l'étape 2.
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void ouvrirEtape2(MouseEvent event) throws IOException {
        MODELJEU.setEtapeChoisi(2);
        MODELJEU.setBiome(Biome.PLAINE);
        SceneManager.chargerScene("SceneDifficulte");
    }

    /**
     * Ouvre la sélection de difficulté pour l'étape 3.
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void ouvrirEtape3(MouseEvent event) throws IOException {
        MODELJEU.setEtapeChoisi(3);
        MODELJEU.setBiome(Biome.FORET);
        SceneManager.chargerScene("SceneDifficulte");
    }

    @FXML
    void ouvrirEtape4(MouseEvent event) throws IOException {
        MODELJEU.setEtapeChoisi(4);
        MODELJEU.setBiome(Biome.NEIGE);
        SceneManager.chargerScene("SceneDifficulte");
    }

    @FXML
    void ouvrirEtape5(MouseEvent event) throws IOException {
        MODELJEU.setEtapeChoisi(5);
        MODELJEU.setBiome(Biome.GROTTE);
        SceneManager.chargerScene("SceneDifficulte");
    }

    @FXML
    void ouvrirEtape6(MouseEvent event) throws IOException {
        MODELJEU.setEtapeChoisi(6);
        MODELJEU.setBiome(Biome.ENFER);
        SceneManager.chargerScene("SceneDifficulte");
    }


    /**
     * Retourne au menu principal.
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void retourScenePrecendente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneMenuPrincipal");
    }

    /**
     * Mise à jour de l'interface : active les boutons des étapes accessibles
     * en fonction de la progression du joueur.
     */
    @Override
    public void update() {
        if(MODELJEU.etapeJoueur() >= 2 ) this.etape2.setDisable(false);
        if(MODELJEU.etapeJoueur() >= 3 ) this.etape3.setDisable(false);
        if(MODELJEU.etapeJoueur() >= 4 ) this.etape4.setDisable(false);
        if(MODELJEU.etapeJoueur() >= 5 ) this.etape5.setDisable(false);
        if(MODELJEU.etapeJoueur() >= 6 ) this.etape6.setDisable(false);
    }
}
