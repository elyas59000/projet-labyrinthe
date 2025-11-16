package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.IObserver;
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
public class SceneModeSupplementaireControleur implements IObserver {

    private final ModeleJeu modeleJeu = ModeleJeuSingleton.getInstance();

    @FXML
    private void initialize() {
        modeleJeu.add(this);
        update();
    }

    /**
     * Retour au menu principal.
     */
    @FXML
    void revenirMenu(MouseEvent event) throws IOException {
        modeleJeu.remove(this);
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
        modeleJeu.remove(this);
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
        modeleJeu.remove(this);
        SceneManager.chargerScene("SceneContreLaMontre");
    }

    /**
     * Mise à jour du contrôleur (ici pas grand-chose).
     * Peut servir si certains modes devaient être verrouillés.
     */
    @Override
    public void update() {
        // Pour le moment, rien à mettre à jour
    }
}
