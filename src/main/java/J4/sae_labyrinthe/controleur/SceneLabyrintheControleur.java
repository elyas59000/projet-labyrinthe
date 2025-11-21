package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.modele.*;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.VueLabyrintheAbstraite;
import J4.sae_labyrinthe.vue.VueLabyrintheEntier;
import J4.sae_labyrinthe.vue.VueLabyrintheMap;
import J4.sae_labyrinthe.vue.VueLabyrinthePartielle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import J4.sae_labyrinthe.utils.ParametresContreLaMontre;
import javafx.scene.paint.Color;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

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
    private static final double LARGEUR_MINI_MAP = 340;
    private final ModeleJeu modele = ModeleJeuSingleton.getInstance();
    private final VueLabyrintheEntier vueEntiere = new VueLabyrintheEntier(modele,false);
    private final VueLabyrinthePartielle vuePartielle = new VueLabyrinthePartielle(modele, RANGE);
    private final VueLabyrintheMap vueMiniMap= new VueLabyrintheMap(modele,true,true);
    private VBox menuPause;
    private boolean pauseActive = false;
    private Timeline timeline;
    private int tempsEcoule = 0;
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
        zoneLaby.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        ILabyrinthe laby = modele.getLabyrinthe();
        info1.setText("Hauteur du Labyrinthe : " + laby.getHauteur() + " cases");
        info2.setText("Largeur du Labyrinthe : " + laby.getLargeur() + " cases");
        vueMiniMap.setPrefWidth(LARGEUR_MINI_MAP);
        vueMiniMap.setMaxWidth(LARGEUR_MINI_MAP);
        vueMiniMap.setMinWidth(LARGEUR_MINI_MAP);
        vueMiniMap.setPrefHeight(LARGEUR_MINI_MAP);
        vueMiniMap.setMinHeight(LARGEUR_MINI_MAP);
        vueMiniMap.setMaxHeight(LARGEUR_MINI_MAP);
        if(laby instanceof Labyrinthe){info3.setText("Taux de mur : " + ((Labyrinthe) laby).getPourcentageMurs() + " %");}
        modele.genererItems(5);
        modele.nouveauListItem();

        if (!modele.getPartieLibre() && modele.etapeChoisi() == -1) {
            setVueActive(vueEntiere,false);
        }

        if (!modele.getPartieLibre()) {
            if (modele.etapeChoisi() == 3) {
                afficherSpinnerEtape3();
                setVueActive(vuePartielle,true);
            } else {
                setVueActive(vueEntiere,false);
            }
        } else {
            afficherCheckBox();
            setVueActive(vueEntiere,false);
        }

        initialiserDeplacement();
        initTimeline();
    }

    /**
     * Centralise la gestion de l’affichage d’une vue.
     */
    private void setVueActive(VueLabyrintheAbstraite vue, boolean afficherMiniMap) {
        zoneLaby.getChildren().clear();
        modele.removeAll();
        modele.add(vue);
        vue.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);

        if (vue instanceof VueLabyrintheEntier vueEntiereActive) {
            zoneGlobale.setStyle("-fx-background-color: " + modele.getBiome().getBackground());
            StackPane.setAlignment(vueEntiereActive, Pos.CENTER);
            zoneLaby.getChildren().add(vueEntiereActive);

        } else {
            zoneGlobale.setStyle("-fx-background-color: BLACK");
            StackPane.setAlignment(vue, Pos.CENTER);
            zoneLaby.getChildren().add(vue);
            if (afficherMiniMap) {
                modele.add(vueMiniMap);
                StackPane.setAlignment(vueMiniMap, Pos.CENTER_LEFT);
                StackPane.setMargin(vueMiniMap, new Insets(20, 0, 0, 20));
                zoneLaby.getChildren().add(vueMiniMap);
            }
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
        zoneLaby.setOnMouseClicked(e -> zoneLaby.requestFocus());
        zoneLaby.setFocusTraversable(true);
        Platform.runLater(zoneLaby::requestFocus);
    }

    /**
     * Déplace le joueur selon la touche pressée.
     */
    private void gererDeplacement(KeyEvent event) {
        String direction = correspondanceTouche(event.getCode());
        if (direction == null) return;
        event.consume();
        modele.deplacerJoueur(direction);
        if (modele.getPositionJoueur().equals(modele.getSortie())) {
            ecranFinPartie();
        } else {
            if (modele.isCellAnItem(modele.getPositionJoueur())) {
                modele.prendreItem();
            }
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
        arreterTimer();
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
        timeline.play();
        Platform.runLater(zoneLaby::requestFocus);
    }

    private void quitterPartie() {
        try {
            nettoyerJeu();

            String sceneCible;
            if (modele.isPartieContreLaMontre()) {
                sceneCible = "SceneContreLaMontre";
            } else {
                sceneCible = modele.getPartie();
            }

            SceneManager.chargerScene(sceneCible);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void nettoyerJeu() {
        arreterTimer();
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
            if (modele.isPartieContreLaMontre()) {
                modele.lancerContreLaMontre(ParametresContreLaMontre.getInstance());

            }
            else if (modele.getPartieLibre()) {
                int t;
                int l = modele.getLabyrinthe().getLargeur();
                int h = modele.getLabyrinthe().getHauteur();
                int d;
                if(modele.getLabyrinthe() instanceof LabyrintheParfait) {
                    t = 0;
                    d = (int) ((LabyrintheParfait)modele.getLabyrinthe()).getDistance();
                }
                else{
                    t = (int) ((Labyrinthe) modele.getLabyrinthe()).getPourcentageMurs();
                    l -= 2;
                    h -= 2;
                    d = 0;
                }
                modele.lancerPartieLibre(
                        l,
                        h,
                        t,
                        d,
                        modele.getBiome());
            } else if (modele.getPartieDuJourEnCours()) {
                modele.lancerLabyrintheDuJour();
            } else {
                modele.lancerPartieProgression();
            }
            SceneManager.chargerScene("SceneLabyrinthe");
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
                VueLabyrinthePartielle nouvelleVue = new VueLabyrinthePartielle(modele, newVal);
                setVueActive(nouvelleVue,true);
            }
        });

        modeDeVue.setOnAction(e -> {
            boolean vueLocaleActive = modeDeVue.isSelected();
            rangeVue.setDisable(!vueLocaleActive);
            if (vueLocaleActive) {
                setVueActive(new VueLabyrinthePartielle(modele, rangeVue.getValue()),true);
            } else {
                setVueActive(vueEntiere,false);
            }
        });
    }

    /**
     * Affiche un spinner pour régler la portée de vue (étape 3 en mode progression).
     */
    private void afficherSpinnerEtape3() {
        creerSpinner(0);
        rangeVue.valueProperty().addListener((obs, oldVal, newVal) -> {
                setVueActive(new VueLabyrinthePartielle(modele, newVal),true);
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

    /**
     * Initialise et démarre le timer qui s'incrémente chaque seconde.
     */
    private void initTimeline() {
        timerInfo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: black;");
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (modele.isPartieContreLaMontre()) {
                // --- LOGIQUE CONTRE LA MONTRE ---
                tempsEcoule--;
                timerInfo.setText("Temps restant: " + tempsEcoule + "s");
                if (tempsEcoule <= 10) timerInfo.setFill(Color.RED);
                else timerInfo.setFill(Color.BLACK);

                if (tempsEcoule <= 0) {
                    arreterTimer();
                    ecranDefaite();
                }
            } else {
                // --- LOGIQUE CLASSIQUE (Ne change pas) ---
                tempsEcoule++;
                timerInfo.setText("Temps: " + tempsEcoule + "s");
            }
        }));

        if (modele.isPartieContreLaMontre()) {
            tempsEcoule = modele.getTempsLimite();
            timerInfo.setText("Temps restant: " + tempsEcoule + "s");
        } else {
            tempsEcoule = 0;
            timerInfo.setText("Temps: 0s");
        }

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    /**
     * Arrête le timer.
     */
    private void arreterTimer() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    private void ecranDefaite() {
        zoneLaby.setOnKeyPressed(null);

        VBox ecranV = new VBox(20);
        ecranV.setAlignment(Pos.CENTER);
        ecranV.setId("overlayPause");

        Label titre = new Label("Temps écoulé ! Vous avez perdu.");
        titre.getStyleClass().add("pause-titre");
        titre.setStyle("-fx-text-fill: #ff5555;");

        Button rejouer = new Button("Réessayer");
        rejouer.getStyleClass().add("pause-bouton");
        rejouer.setOnAction(e -> relancerPartie());

        Button quitter = new Button("Quitter");
        quitter.getStyleClass().add("pause-bouton");
        quitter.setOnAction(e -> quitterPartie());

        ecranV.getChildren().addAll(titre, rejouer, quitter);
        zoneGlobale.getChildren().add(ecranV);
        StackPane.setAlignment(ecranV, Pos.CENTER);
    }


}
