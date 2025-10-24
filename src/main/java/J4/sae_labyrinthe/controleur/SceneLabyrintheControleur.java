package J4.sae_labyrinthe.controleur;

import java.io.IOException;

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
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Contrôleur de la scène principale du jeu de labyrinthe.
 * Gère l'affichage du labyrinthe, les déplacements du joueur et le menu pause.
 * Supporte deux modes d'affichage : vue complète et vue partielle avec minimap.
 * 
 * @author Clément Roty
 * @author Nathan Philippe
 */
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
        if(modele.getLabyrinthe().getLargeur()>60 || modele.getLabyrinthe().getHauteur()>45){
            affichageLocal();
        }else{
            affichageEntier();
        }
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
        vueEntiere.update();
    }

    /**
     * Affiche une vue partielle autour du joueur.
     */
    private void affichageLocal() {
        HBox hb=new HBox(vuePartielle.getLabPartiel(),vuePartielle.getMinimap());
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(40);
        zoneLaby.setAlignment(Pos.CENTER);
        zoneLaby.setPadding(new Insets(5));
        vuePartielle.getLabPartiel().setAlignment(Pos.CENTER_LEFT);
        vuePartielle.getMinimap().setAlignment(Pos.CENTER_RIGHT);
        zoneLaby.setFocusTraversable(true);
        zoneLaby.getChildren().add(hb);
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



    private boolean pauseVisible = false;

    private VBox menuPause;

    /**
     * Affiche ou masque un menu de pause intégré à la zone du labyrinthe.
     * Gère la création et l'affichage d'un menu de pause semi-transparent
     * permettant de reprendre la partie ou de quitter vers le menu principal.
     * 
     * @param event clic
     * @author Clément Roty
     */
    @FXML
    void afficherMenuPause(MouseEvent event) {
        if (pauseVisible) {
            // Si le menu est déjà affiché, on le retire
            zoneLaby.getChildren().remove(menuPause);
            zoneLaby.setOpacity(1);
            pauseVisible = false;
            return;
        }

        // Crée le menu de pause
        Label titre = new Label("Jeu en pause");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button btnReprendre = new Button("Reprendre");
        Button btnQuitter = new Button("Quitter");

        btnReprendre.setOnAction(e -> {
            zoneLaby.getChildren().remove(menuPause);
            zoneLaby.setOpacity(1);
            pauseVisible = false;
        });

        btnQuitter.setOnAction(e -> {
            try {
                nettoyerJeu();
                SceneManager.chargerScene("SceneMenuPrincipal");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        menuPause = new VBox(15, titre, btnReprendre, btnQuitter);
        menuPause.setAlignment(Pos.CENTER);
        menuPause.setPadding(new Insets(20));
        menuPause.setStyle(
                "-fx-background-color: rgba(0,0,0,0.8); " +
                        "-fx-background-radius: 15;"
        );
        menuPause.setPrefSize(300, 250);

        // Ajoute la couche semi-transparente sur le labyrinthe
        zoneLaby.setOpacity(0.5);
        zoneLaby.getChildren().add(menuPause);
        StackPane.setAlignment(menuPause, Pos.CENTER);

        pauseVisible = true;
    }

    /**
     * Nettoie les éléments de l'interface et supprime les écouteurs d'événements
     * avant de quitter la scène.
     * @author Clément Roty
     */
    private void nettoyerJeu() {
        // Supprime le menu s’il est visible
        if (pauseVisible && menuPause != null) {
            zoneLaby.getChildren().remove(menuPause);
            pauseVisible = false;
        }

        // Supprime les listeners pour éviter les doublons
        zoneLaby.setOnKeyPressed(null);
        zoneLaby.setOnKeyReleased(null);

        // Supprime le contenu du StackPane
        zoneLaby.getChildren().clear();
    }

}
