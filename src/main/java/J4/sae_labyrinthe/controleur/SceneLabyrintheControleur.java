package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.modele.*;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.*;
import javafx.animation.AnimationTimer;
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
    private static final long MOVE_COOLDOWN = 150_000_000;

    private final ModeleJeu modele = ModeleJeuSingleton.getInstance();
    private final VueLabyrintheEntier vueEntiere = new VueLabyrintheEntier(modele,false,false);
    private final VueLabyrinthePartielle vuePartielle = new VueLabyrinthePartielle(modele, RANGE);
    private final VueLabyrintheMap vueMiniMap= new VueLabyrintheMap(modele,false,false,false,false);

    private VBox menuPause;
    private boolean pauseActive = false;
    private Timeline timeline;
    private int tempsEcoule = 0;
    private final CheckBox modeDeVue=new CheckBox("Vue locale");
    private final Spinner<Integer> rangeVue = new Spinner<>(1, 5, RANGE);
    private boolean isUp, isDown, isLeft, isRight;
    private AnimationTimer gameLoop;
    private long lastMoveTime = 0;

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
        vueMiniMap.setPrefWidth(LARGEUR_MINI_MAP);vueMiniMap.setMaxWidth(LARGEUR_MINI_MAP);vueMiniMap.setMinWidth(LARGEUR_MINI_MAP);vueMiniMap.setPrefHeight(LARGEUR_MINI_MAP);vueMiniMap.setMinHeight(LARGEUR_MINI_MAP);vueMiniMap.setMaxHeight(LARGEUR_MINI_MAP);
        ILabyrinthe laby = modele.getLabyrinthe();
        info1.setText("Hauteur du Labyrinthe : " + (laby.getHauteur()-2) + " cases");
        info2.setText("Largeur du Labyrinthe : " + (laby.getLargeur()-2) + " cases");
        if(laby instanceof Labyrinthe) {
            info3.setText("Taux de mur : " + ((Labyrinthe) laby).getPourcentageMurs() + " %");
        }
        modele.genererItems(5);
        modele.nouveauListItem();

        if(!modele.isPartieLibre()){
            switch (modele.etapeChoisi()) {
                case 1,2,4 -> setVueActive(vueEntiere,false);
                case 3 -> {
                    vuePartielle.setRange(3);
                    setVueActive(vuePartielle,true);

                }
                case 5 -> {
                    VueLabyrintheEntier vueAvecBrouillard = new VueLabyrintheEntier(modele, true, false);
                    int rayon = switch (modele.getDefiChoisi()) {
                        case 1 -> 3;
                        case 2 -> 2;
                        case 3 -> 1;
                        default -> 3;
                    };
                    vueAvecBrouillard.setRAYON_DECOUVERTE(rayon);
                    setVueActive(vueAvecBrouillard, false);
                }
                case 6 -> {
                    vueMiniMap.setPrintJoueur(true);
                    vueMiniMap.setMurADecouvrir(true);
                    vueMiniMap.setDecouvertePersistante(true);
                    int rayon = switch (modele.getDefiChoisi()) {
                        case 1 -> 3;
                        case 2 -> 2;
                        case 3 -> 1;
                        default -> 3;
                    };
                    vuePartielle.setRange(rayon);
                    setVueActive(vuePartielle,true);

                }
            }
        }else{
            afficherCheckBox();
            if(modele.getLabyrinthe().getHauteur()>60 || modele.getLabyrinthe().getLargeur()>80) {
                setVueActive(vuePartielle,true);
                modeDeVue.setSelected(true);
                rangeVue.setDisable(false);

            }else{
                setVueActive(vueEntiere,false);
            }
        }

        initialiserDeplacement();
        initTimeline();
    }

    /**
     * Centralise la gestion de l’affichage d’une vue.
     */
    private void setVueActive(VueLabyrintheAbstraite vue, boolean afficherMiniMap) {
        zoneLaby.getChildren().clear();
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
        zoneLaby.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Z, UP -> isUp = true;
                case S, DOWN -> isDown = true;
                case Q, LEFT -> isLeft = true;
                case D, RIGHT -> isRight = true;
            }
            event.consume();
        });
        zoneLaby.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case Z, UP -> isUp = false;
                case S, DOWN -> isDown = false;
                case Q, LEFT -> isLeft = false;
                case D, RIGHT -> isRight = false;
            }
            event.consume();
        });
        zoneLaby.setOnMouseClicked(e -> zoneLaby.requestFocus());
        zoneLaby.setFocusTraversable(true);
        Platform.runLater(zoneLaby::requestFocus);
        lancerBoucleDeMouvement();
    }


    private void lancerBoucleDeMouvement() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (pauseActive) return;

                if (now - lastMoveTime >= MOVE_COOLDOWN) {

                    String direction = null;

                    if (isUp) direction = "z";
                    else if (isDown) direction = "s";
                    else if (isLeft) direction = "q";
                    else if (isRight) direction = "d";

                    if (direction != null) {
                        modele.deplacerJoueur(direction);

                        if (modele.getPositionJoueur().equals(modele.getSortie())) {
                            ecranFinPartie();
                            stop();
                        } else if (modele.isCellAnItem(modele.getPositionJoueur())) {
                            modele.prendreItem();
                        }

                        lastMoveTime = now;
                    }
                }
            }
        };
        gameLoop.start();
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
        if (gameLoop != null) gameLoop.stop();
        pauseActive = true;
        arreterTimer();
        zoneLaby.setOnKeyPressed(null);
        menuPause = new VBox(40);
        menuPause.setAlignment(Pos.CENTER);
        menuPause.setId("overlayPause"); // pour le CSS
        Label titre = new Label("Jeu en pause");
        titre.getStyleClass().add("pause-titre");

        Button reprendre = new Button("Reprendre");
        ajouterPause(reprendre);
        reprendre.setOnAction(e -> reprendrePartie());

        Button quitter = new Button("Quitter");
        ajouterPause(quitter);
        quitter.setOnAction(e -> quitterPartie());

        menuPause.getChildren().addAll(titre, reprendre, quitter);
        zoneGlobale.getChildren().add(menuPause);
        StackPane.setAlignment(menuPause, Pos.CENTER);
    }

    private void reprendrePartie() {
        if (gameLoop != null) gameLoop.stop();
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
        ajouterPause(rejouer);
        rejouer.setOnAction(e -> relancerPartie());

        Button quitter = new Button("Quitter");
        ajouterPause(quitter);
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
            else if (modele.isPartieLibre()) {
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
        enteteJeu.getChildren().add(0, modeDeVue);

        // Création du spinner (désactivé par défaut)
        creerSpinner(1);
        rangeVue.setDisable(true);

        rangeVue.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (modeDeVue.isSelected()) {
                vuePartielle.setRange(newVal);
                setVueActive(vuePartielle,true);
            }
        });

        modeDeVue.setOnAction(e -> {
            boolean vueLocaleActive = modeDeVue.isSelected();
            rangeVue.setDisable(!vueLocaleActive);
            if (vueLocaleActive) {
                setVueActive(vuePartielle,true);
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
        ajouterPause(rejouer);
        rejouer.setOnAction(e -> relancerPartie());

        Button quitter = new Button("Quitter");
        ajouterPause(quitter);
        quitter.setOnAction(e -> quitterPartie());

        ecranV.getChildren().addAll(titre, rejouer, quitter);
        zoneGlobale.getChildren().add(ecranV);
        StackPane.setAlignment(ecranV, Pos.CENTER);
    }

    private void ajouterPause(Button b){
        b.getStyleClass().add("pause-bouton");
    }
}
