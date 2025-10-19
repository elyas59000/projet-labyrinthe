package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.IObserver;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class SceneInfosCompteControleur implements IObserver {

    private final ModeleJeu modeleJeu= ModeleJeuSingleton.getInstance();

    @FXML
    void initialize() {
        modeleJeu.add(this);
        update();
    }

    @FXML
    private Text champsTitreCompte;

    @FXML
    void allerSceneModifProfil(MouseEvent event) {
        modeleJeu.remove(this);
    }

    @FXML
    void deconnexion(MouseEvent event) throws IOException {
        modeleJeu.remove(this);
        modeleJeu.deconnexionJoueur();
        SceneManager.chargerScene("SceneMenuPrincipal");
    }

    @FXML
    void retourScenePrecedente(MouseEvent event) throws IOException {
        modeleJeu.remove(this);
        SceneManager.chargerScene("SceneMenuPrincipal");
    }

    @Override
    public void update() {
        String pseudo = modeleJeu.getJoueurActuel().getNom();
        champsTitreCompte.setText(" "+pseudo);
    }
}
