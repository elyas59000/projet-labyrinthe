package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.vue.Biome;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import J4.sae_labyrinthe.utils.SceneManager;

public class SceneContreLaMontreControleur {

    private final ModeleJeu modele = ModeleJeuSingleton.getInstance();

    @FXML
    private Text txtDescription;

    @FXML
    void initialize() {
        txtDescription.setText("Complète le labyrinthe avant la fin du chrono !");
    }

    @FXML
    void jouerContreLaMontre() {
        // Génère un labyrinthe aléatoire pour le contre-la-montre (largeur/hauteur 10, 30% murs)
        modele.lancerPartieLibre(10, 10, 30, Biome.values()[(int)(Math.random() * Biome.values().length)]);
        // Ici on pourrait ajouter un timer dans SceneLabyrintheControleur pour le chrono
        try {
            SceneManager.chargerScene("SceneLabyrinthe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void revenirMenu() {
        try {
            SceneManager.chargerScene("SceneModeSupplementaire");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
