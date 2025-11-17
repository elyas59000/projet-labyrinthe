package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

/**
 * Contrôleur de la scène des modes supplémentaires.
 * Permet d'accéder aux deux nouveaux modes :
 *  - Labyrinthe du jour
 *  - Contre-la-montre
 *
 * Fonctionne avec le même pattern que le menu principal.
 *
 * @author Nathan
 */
public class SceneModeSupplementaireControleur {

    /**
     * Retour au menu principal.
     */
    @FXML
    void revenirMenu(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneMenuPrincipal");
    }

    /**
     * Ouvre la scène du "Labyrinthe du jour".
     *
     * @param event clic
     * @throws IOException si chargement échoue
     */
    @FXML
    void ouvrirLabyrintheDuJour(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneLabyrintheDuJour");
    }

    /**
     * Ouvre la scène du mode "Contre la montre".
     *
     * @param event clic
     * @throws IOException si chargement échoue
     */
    @FXML
    void ouvrirContreLaMontre(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneContreLaMontre");
    }
}
