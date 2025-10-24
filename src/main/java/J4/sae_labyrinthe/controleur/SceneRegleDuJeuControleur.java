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
                Trouve la sortie du labyrinthe le plus rapidement possible !

                🧱 Principe :
                - Chaque étape contient 3 défis : Facile, Moyen et Difficile.
                - Plus la difficulté augmente, plus il y a de murs dans le labyrinthe.
                - Tu dois te déplacer avec les touches :
                    Z → Haut
                    Q → Gauche
                    S → Bas
                    D → Droite

                ⭐ Progression :
                - Chaque défi réussi te fait gagner des étoiles.
                - Si tu réussis au moins un défi d'une étape, tu débloques l'étape suivante.
                - Tu peux rejouer les défis pour améliorer ton score !

                🚪 Objectif final :
                - Terminer toutes les étapes du jeu en atteignant la sortie de chaque labyrinthe.
                - Montre que tu es le maître du labyrinthe !
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