package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

/**
 * Contrôleur de la scène de sélection de la difficulté (niveau) pour les défis en mode progression.
 * Chaque bouton de niveau appelle une méthode qui configure le défi choisi dans le modèle
 * et lance la partie en mode progression. Après le lancement, la scène du labyrinthe est chargée.
 *
 * @author Clément Roty
 * @author Nathan Philippe
 */
public class SceneDifficulteControleur {

    private final ModeleJeu MODELJEU = ModeleJeuSingleton.getInstance();

    /**
     * Lance une partie correspondant au niveau 1.
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void lancerPartieNiv1(MouseEvent event) throws IOException {
        MODELJEU.setDefiChoisi(1);
        MODELJEU.lancerPartieProgression();
        // Après le lancement, on redirige vers la scène du labyrinthe
        SceneManager.chargerScene("SceneLabyrinthe");
    }

    /**
     * Lance une partie correspondant au niveau 2.
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void lancerPartieNiv2(MouseEvent event) throws IOException {
        MODELJEU.setDefiChoisi(2);
        MODELJEU.lancerPartieProgression();
        SceneManager.chargerScene("SceneLabyrinthe");
    }

    /**
     * Lance une partie correspondant au niveau 3.
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void lancerPartieNiv3(MouseEvent event) throws IOException {
        MODELJEU.setDefiChoisi(3);
        MODELJEU.lancerPartieProgression();
        SceneManager.chargerScene("SceneLabyrinthe");
    }

    /**
     * Retourne à la scène de progression (sélection de défi).
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void retourScenePrecedente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneProg");
    }

}
