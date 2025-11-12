package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.ILabyrinthe;
import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.VueLabyrintheAbstraite;
import J4.sae_labyrinthe.vue.VueLabyrintheEntier;
import J4.sae_labyrinthe.vue.VueLabyrinthePartiel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.IOException;

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
    private VBox menuPause;
    private boolean pauseActive = false;

    private CheckBox modeDeVue;

    private Spinner<Integer> rangeVue = new Spinner<>(1, 5, RANGE);

    @FXML
    private HBox enteteJeu;
    @FXML
    private Text info1, info2, info3, timerInfo;
    @FXML
    private StackPane zoneLaby;
    @FXML
    StackPane zoneGlobale;

    @FXML
    void initialize() {
        ILabyrinthe laby = modele.getLabyrinthe();
        info1.setText("Hauteur du Labyrinthe : " + laby.getHauteur() + " cases");
        info2.setText("Largeur du Labyrinthe : " + laby.getLargeur() + " cases");
        if(laby instanceof Labyrinthe){info3.setText("Taux de mur : " + ((Labyrinthe) laby).getPourcentageMurs() + " %");}

        if (!modele.getPartieLibre()) {
            if (modele.etapeChoisi() == 3) {
                afficherSpinnerEtape3();
                setVueActive(vuePartielle);
            } else {
                setVueActive(vueEntiere);
            }
        } else {
            afficherCheckBox();
            setVueActive(vueEntiere);
        }

        initialiserDeplacement();
    }

    /**
     * Centralise la gestion de l’affichage d’une vue.
     */
    private void setVueActive(VueLabyrintheAbstraite vue) {
        // Nettoyer la zone d’affichage actuelle
        zoneLaby.getChildren().clear();
        modele.removeAll();

        // Initialiser et enregistrer la nouvelle vue
        vue.initialiserAffichage();
        modele.add(vue);

        // Ajouter la vue dans la zone d’affichage
        if (vue instanceof VueLabyrinthePartiel vuePartielleActive) {
            HBox hb = new HBox(vuePartielleActive.getGrille(), vuePartielleActive.getMinimap());
            hb.setAlignment(Pos.CENTER);
            hb.setSpacing(40);
            hb.maxHeight(1);
            zoneLaby.getChildren().add(hb);
        } else if (vue instanceof VueLabyrintheEntier vueEntiereActive) {
            vueEntiereActive.getGrille().setAlignment(Pos.CENTER);
            zoneLaby.getChildren().add(vueEntiereActive.getGrille());
        }

        zoneLaby.setAlignment(Pos.CENTER);
        zoneLaby.setPadding(new Insets(5));
        zoneLaby.setFocusTraversable(true);
        Platform.runLater(zoneLaby::requestFocus);
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
        if (direction == null) return;
        modele.deplacerJoueur(direction);
        if (modele.getPositionJoueur().equals(modele.getSortie())) {
            ecranFinPartie();
        }
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

    @FXML
    void afficherMenuPause(MouseEvent event) {
        if (pauseActive) {
            reprendrePartie();
        } else {
            mettreEnPause();
        }
    }

    private void mettreEnPause() {
        pauseActive = true;
        zoneLaby.setOnKeyPressed(null);
        menuPause = new VBox(40);
        menuPause.setAlignment(Pos.CENTER);
        menuPause.setId("overlayPause"); // pour le CSS
        Label titre = new Label("Jeu en pause");
        titre.getStyleClass().add("pause-titre");

        Button reprendre = new Button("Reprendre");
        reprendre.getStyleClass().add("pause-bouton");
        reprendre.setOnAction(e -> reprendrePartie());

        Button quitter = new Button("Quitter");
        quitter.getStyleClass().add("pause-bouton");
        quitter.setOnAction(e -> quitterPartie());

        menuPause.getChildren().addAll(titre, reprendre, quitter);
        zoneGlobale.getChildren().add(menuPause);
        StackPane.setAlignment(menuPause, Pos.CENTER);
    }

    private void reprendrePartie() {
        pauseActive = false;
        zoneGlobale.getChildren().remove(menuPause);
        menuPause = null;
        initialiserDeplacement();
        Platform.runLater(zoneLaby::requestFocus);
    }

    private void quitterPartie() {
        try {
            nettoyerJeu();
            if (modele.getPartieLibre()) {
                SceneManager.chargerScene("SceneModeLibre");
            } else {
                SceneManager.chargerScene("SceneProg");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void nettoyerJeu() {
        zoneLaby.setOnKeyPressed(null);
        zoneLaby.setOnKeyReleased(null);
        zoneLaby.getChildren().clear();
    }

    private void ecranFinPartie() {
        String text = "Vous avez atteint la sortie !";
        zoneLaby.setOnKeyPressed(null);
        VBox ecranV = new VBox(20);
        ecranV.setAlignment(Pos.CENTER);
        ecranV.setId("overlayPause");

        Label titre = new Label(text);
        titre.getStyleClass().add("pause-titre");

        Button rejouer = new Button("Rejouer");
        rejouer.getStyleClass().add("pause-bouton");
        rejouer.setOnAction(e -> relancerPartie());

        Button quitter = new Button("Quitter");
        quitter.getStyleClass().add("pause-bouton");
        quitter.setOnAction(e -> quitterPartie());

        ecranV.getChildren().addAll(titre, rejouer, quitter);
        zoneGlobale.getChildren().add(ecranV);
        StackPane.setAlignment(ecranV, Pos.CENTER);
    }

    private void relancerPartie() {
        try {
            if (modele.getPartieLibre()) {
                modele.lancerPartieLibre(
                        modele.getLabyrinthe().getLargeur(),
                        modele.getLabyrinthe().getHauteur(),
                        (int) ((Labyrinthe) modele.getLabyrinthe()).getPourcentageMurs());
                SceneManager.chargerScene("SceneLabyrinthe");
            } else {
                modele.lancerPartieProgression();
                SceneManager.chargerScene("SceneLabyrinthe");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Affiche la checkbox et le spinner pour la vue locale (mode libre uniquement).
     */
    private void afficherCheckBox() {
        modeDeVue = new CheckBox("Vue locale");
        enteteJeu.getChildren().add(0, modeDeVue);

        // Création du spinner (désactivé par défaut)
        creerSpinner(1);
        rangeVue.setDisable(true);

        rangeVue.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (modeDeVue.isSelected()) {
                VueLabyrinthePartiel nouvelleVue = new VueLabyrinthePartiel(modele, newVal);
                setVueActive(nouvelleVue);
            }
        });

        modeDeVue.setOnAction(e -> {
            boolean vueLocaleActive = modeDeVue.isSelected();
            rangeVue.setDisable(!vueLocaleActive);
            if (vueLocaleActive) {
                setVueActive(new VueLabyrinthePartiel(modele, rangeVue.getValue()));
            } else {
                setVueActive(vueEntiere);
            }
        });
    }

    /**
     * Affiche un spinner pour régler la portée de vue (étape 3 en mode progression).
     */
    private void afficherSpinnerEtape3() {
        creerSpinner(0);
        rangeVue.valueProperty().addListener((obs, oldVal, newVal) -> {
                setVueActive(new VueLabyrinthePartiel(modele, newVal));
        });


    }

    private void creerSpinner(int index){
        rangeVue.setPrefWidth(70);
        rangeVue.setEditable(false);
        Label labelRange = new Label("Portée :");
        labelRange.setPadding(new Insets(0, 5, 0, 15));

        HBox hBoxRange = new HBox(labelRange, rangeVue);
        hBoxRange.setAlignment(Pos.CENTER_LEFT);
        hBoxRange.setSpacing(5);
        enteteJeu.getChildren().add(index, hBoxRange);
    }

}
