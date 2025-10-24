package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * Contrôleur de la scène présentant les règles du jeu.
 *
 * Fournit un texte explicatif détaillant le but du jeu, les commandes et la
 * progression. Permet de revenir au menu principal.
 *
 * @author Clément Roty, Nathan Philippe
 */
public class SceneRegleDuJeuControleur {

    @FXML
    private Label texteRegles;

    @FXML
    public void initialize() {
        texteRegles.setText("""
                🌀 Le but du jeu :
                Manger le fromage le plus rapidement possible !

                🧱 Principe :
                - Chaque étape contient 3 défis : Facile, Moyen et Difficile.
                - Plus la difficulté augmente, plus il y a de murs dans le labyrinthe.
                
                - Les 2 premières étapes, varient en fonction de leur taille.
                - La 3 ème étapes inclue une nouvelle vue du labyrinthe, une nouvelle expérience avec une vue locale du labyrinthe
                
                - Tu dois te déplacer avec les touches :
                    Z ou ↑ : Haut
                    Q ou ← : Gauche
                    S ou ↓ : Bas
                    D ou → : Droite

                ⭐ Progression :
                - Chaque défi réussi te fait gagner des étoiles.
                - Si tu réussis au moins un défi d'une étape, tu débloques l'étape suivante.
                - Tu peux rejouer les défis pour améliorer ton score !

                🚪 Objectif final :
                - Terminer toutes les étapes du jeu en mangeant tous les fromages.
                - Montre que tu es la reine des souris !
                """);
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