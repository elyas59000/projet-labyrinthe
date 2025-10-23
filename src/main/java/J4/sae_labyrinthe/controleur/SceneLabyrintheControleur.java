package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.VueLabyrintheEntier;
import J4.sae_labyrinthe.vue.VueLabyrinthePartiel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class SceneLabyrintheControleur {

    private static final int RANGE = 1;

    private final ModeleJeu modele = ModeleJeuSingleton.getInstance();
    private final VueLabyrintheEntier vueEntiere = new VueLabyrintheEntier(modele);
    private final VueLabyrinthePartiel vuePartielle = new VueLabyrinthePartiel(modele, RANGE);

    @FXML
    private Button btnPause;

    @FXML
    private Text info1, info2, info3, timerInfo;

    @FXML
    private StackPane zoneLaby;

    @FXML
    void initialize() {
        // Choisis l’affichage voulu ici :
        affichageEntier();
        // affichageLocal();

        initialiserDeplacement();
    }

    /**
     * Affiche le labyrinthe complet.
     */
    private void affichageEntier() {
        zoneLaby.getChildren().add(vueEntiere.getLabEntier());
        zoneLaby.setAlignment(Pos.CENTER);
        zoneLaby.setPadding(new Insets(5));
        vueEntiere.getLabEntier().setAlignment(Pos.CENTER);

        zoneLaby.setFocusTraversable(true);
        Platform.runLater(zoneLaby::requestFocus);
        vueEntiere.update();
    }

    /**
     * Affiche une vue partielle autour du joueur.
     */
    private void affichageLocal() {
        zoneLaby.getChildren().add(vuePartielle.getLabPartiel());
        zoneLaby.setAlignment(Pos.CENTER);
        zoneLaby.setPadding(new Insets(5));
        vuePartielle.getLabPartiel().setAlignment(Pos.CENTER);

        zoneLaby.setFocusTraversable(true);
        Platform.runLater(zoneLaby::requestFocus);
        vuePartielle.update();
    }

    /**
     * Initialise la gestion du clavier pour les déplacements.
     */
    private void initialiserDeplacement() {
        zoneLaby.setOnKeyPressed(this::gererDeplacement);
        zoneLaby.setFocusTraversable(true);
        Platform.runLater(zoneLaby::requestFocus);
    }

    /**
     * Déplace le joueur selon la touche pressée.
     */
    private void gererDeplacement(KeyEvent event) {
        String direction = correspondanceTouche(event.getCode());
        if (direction == null) {
            return;
        }

        modele.deplacerJoueur(direction);

        vueEntiere.update();

        if (modele.getPositionJoueur().equals(modele.getSortie())) {
            try {
                SceneManager.chargerScene("SceneMenuPrincipal");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Platform.runLater(zoneLaby::requestFocus);
    }

    /**
     * Associe les touches clavier à une direction.
     */
    private String correspondanceTouche(KeyCode code) {
        if (code == KeyCode.UP || code == KeyCode.Z) return "z";
        if (code == KeyCode.DOWN || code == KeyCode.S) return "s";
        if (code == KeyCode.LEFT || code == KeyCode.Q) return "q";
        if (code == KeyCode.RIGHT || code == KeyCode.D) return "d";
        return null;
    }

    /**
     * Affiche le menu de pause.
     */
    @FXML
    void afficherMenuPause(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneModeLibre");
    }
}
