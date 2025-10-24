package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.Joueur;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class SceneModifCompteControleur{

    private final ModeleJeu modeleJeu= ModeleJeuSingleton.getInstance();

    @FXML
    private Text champsTitreCompte;

    @FXML
    private TextField champsNomCompte;
    @FXML
    private TextField champModifPseudo;
    @FXML
    private TextField champsAncienmdp;
    @FXML
    private TextField champsNouveaumdp;
    @FXML
    private TextField champsConfirmmdp;

    public void validerModifPseudo(MouseEvent mouseEvent) {
        if (nomValide()){
            modeleJeu.getJoueurActuel().setMdp(champsNouveaumdp.getText());
        }else {
            System.out.println("Erreur mpd");
            System.out.println(champModifPseudo.getText());
            System.out.println(modeleJeu.getJoueurActuel().getNom());
        }
    }

    public void validerModifmdp(MouseEvent mouseEvent) {
        if (mdpValide()){
            modeleJeu.getJoueurActuel().setMdp(champsNouveaumdp.getText());
        }else {
            System.out.println("Erreur mpd");
            System.out.println(champsNouveaumdp.getText());
            System.out.println(modeleJeu.getJoueurActuel().getMdp());
        }
    }

    public boolean mdpValide() {
        return champsAncienmdp.getText().equals(modeleJeu.getJoueurActuel().getMdp()) && champsNouveaumdp.getText().equals(champsConfirmmdp.getText());
    }

    public boolean nomValide() {
        for (Joueur j : modeleJeu.joueurs) {
            if (j.getNom().equals(champModifPseudo.getText())) {
                return false;
            }
        }
        return true;
    }

    public void suppression(MouseEvent mouseEvent) {
        modeleJeu.joueurs.remove(modeleJeu.getJoueurActuel());
        modeleJeu.deconnexionJoueur();
    }

    @FXML
    void retourScenePrecedente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneInfosCompte");
    }
}
