package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.IObserver;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * Contrôleur de la scène d'informations du compte utilisateur.
 *
 * Affiche le pseudo du joueur courant et propose des actions : modifier le
 * profil, se déconnecter ou revenir au menu principal. S'abonne au modèle
 * pour recevoir les mises à jour via l'interface `IObserver`.
 *
 * @author Clément Roty, Nathan Philippe
 */
public class SceneInfosCompteControleur implements IObserver {

    private final ModeleJeu modeleJeu= ModeleJeuSingleton.getInstance();


    @FXML
    void initialize() {
        modeleJeu.add(this);
        update();
    }

    @FXML
    private Text titreCompte;

    /**
     * Redirige vers la scène de modification du profil.
     * Cette méthode retire également le contrôleur de la liste d'observateurs
     * afin d'éviter les mises à jour après navigation.
     *
     * @param event clic
     */
    @FXML
    void allerSceneModifProfil(MouseEvent event) throws IOException {
        modeleJeu.remove(this);
        SceneManager.chargerScene("SceneModifCompte");
    }

    /**
     * Déconnecte le joueur courant et retourne au menu principal.
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void deconnexion(MouseEvent event) throws IOException {
        modeleJeu.remove(this);
        modeleJeu.deconnexionJoueur();
        SceneManager.chargerScene("SceneMenuPrincipal");
    }

    /**
     * Retourne au menu principal.
     *
     * @param event clic
     * @throws IOException si le chargement de la scène échoue
     */
    @FXML
    void retourScenePrecedente(MouseEvent event) throws IOException {
        modeleJeu.remove(this);
        SceneManager.chargerScene("SceneMenuPrincipal");
    }

    /**
     * Mise à jour appelée lorsque le modèle notifie ses observateurs.
     * Met à jour le champ affichant le pseudo du joueur courant.
     */
    @Override
    public void update() {
        String pseudo = modeleJeu.getJoueurActuel().getNom();
        titreCompte.setText(" "+pseudo);
    }
}
