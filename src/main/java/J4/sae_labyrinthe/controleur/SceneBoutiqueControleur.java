package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.IModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class SceneBoutiqueControleur {
    public Label prixVision;
    public Label prixSentir;
    public Label prixBriseMur;
    public Label prixTeleport;
    public Label invBriseMur;
    public Label invTeleport;
    public Label invSentir;
    public Label invVision;
    @FXML
    private Text txtArgent;

    private ModeleJeu  modele = ModeleJeuSingleton.getInstance();

    public void initialize(){
        txtArgent.setText(modele.getJoueurActuel().getScore() + "");
    }
    @FXML
    void retourScenePrecendente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneMenuPrincipal");
    }

    public void acheterBriseMur(MouseEvent mouseEvent) {
    }

    public void acheterTeleportation(MouseEvent mouseEvent) {
    }

    public void acheterSentirSortie(MouseEvent mouseEvent) {
    }

    public void acheterBaliseVision(MouseEvent mouseEvent) {
    }
}
