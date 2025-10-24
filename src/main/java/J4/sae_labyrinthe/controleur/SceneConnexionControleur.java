package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.exception.ValidationException;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SaisieValidation;
import J4.sae_labyrinthe.utils.Save;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Contrôleur de la scène de connexion permettant aux utilisateurs de se connecter
 * avec leurs identifiants existants ou d'accéder à la page d'inscription.
 * Gère la validation des identifiants et l'affichage des erreurs.
 * 
 * @author Nathan Philippe
 * @author Clément Roty
 */
public class SceneConnexionControleur {

    private final ModeleJeu modeleJeu= ModeleJeuSingleton.getInstance();

    @FXML
    private Text messageErreur;

    @FXML
    private TextField champLogin;

    @FXML
    private TextField champsMDP;

    /**
     * Redirige vers la scène d'inscription après avoir chargé la liste des joueurs.
     * 
     * @param event clic
     * @throws IOException En cas d'erreur lors du chargement de la scène
     * @author Nathan Philippe
     */
    @FXML
    void ouvrirSceneInscriptions(MouseEvent event) throws IOException {
        try {
            modeleJeu.joueurs = Save.loadSave();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        SceneManager.chargerScene("SceneInscription");
    }

    /**
     * Retourne au menu principal.
     * 
     * @param event clic
     * @throws IOException En cas d'erreur lors du chargement de la scène
     * @author Clément Roty
     */
    @FXML
    void retourScenePrecedente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneMenuPrincipal");
    }

    /**
     * Vérifie les identifiants saisis et connecte l'utilisateur si valides.
     * En cas d'erreur, affiche un message approprié.
     * 
     * @param event Événement de clic déclencheur
     * @throws IOException En cas d'erreur lors de la lecture des données ou du chargement de scène
     * @author Nathan Philippe
     */
    @FXML
    void validerEntreesConnexion(MouseEvent event) throws IOException {
        String login = champLogin.getText();
        String mdp = champsMDP.getText();
        modeleJeu.joueurs = Save.loadSave();
        try{
            SaisieValidation.validationConnexion(login,mdp);
            modeleJeu.connexionJoueur(login,mdp);
            SceneManager.chargerScene("SceneMenuPrincipal");

        } catch (ValidationException e) {
            messageErreur.fillProperty().setValue(Color.RED);
            messageErreur.setText(e.getMessage());
        } catch (IOException e) {
            System.out.println("Erreur de lecture");
        }
    }

}

