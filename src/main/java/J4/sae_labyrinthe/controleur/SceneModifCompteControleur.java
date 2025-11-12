package J4.sae_labyrinthe.controleur;

import java.io.IOException;

import J4.sae_labyrinthe.modele.Joueur;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeuSingleton;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

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
    private PasswordField champsConfirmmdp;

    @FXML
    private Text messageErreur;

    @FXML
    void initialize() {
        update();
    }

    public void validerModifPseudo(MouseEvent mouseEvent) {
        if (nomValide() && !champModifPseudo.getText().isEmpty()) {
            String ancienNom = modeleJeu.getJoueurActuel().getNom();
            modeleJeu.getJoueurActuel().setNom(champModifPseudo.getText());
            modeleJeu.sauvegarderJoueur();
            messageErreur.setText("Pseudo modifié avec succès !");
            messageErreur.setStyle("-fx-fill: green;");
        } else {
            messageErreur.setText("Erreur : Pseudo invalide ou déjà utilisé");
            messageErreur.setStyle("-fx-fill: red;");
        }
    }

    public void validerModifmdp(MouseEvent mouseEvent) {
        if (mdpValide()) {
            modeleJeu.getJoueurActuel().setMdp(champsNouveaumdp.getText());
            modeleJeu.sauvegarderJoueur();
            messageErreur.setText("Mot de passe modifié avec succès !");
            messageErreur.setStyle("-fx-fill: green;");
            // Réinitialiser les champs
            champsAncienmdp.clear();
            champsNouveaumdp.clear();
            champsConfirmmdp.clear();
        } else {
            messageErreur.setText("Erreur : Ancien mot de passe incorrect ou les nouveaux mots de passe ne correspondent pas");
            messageErreur.setStyle("-fx-fill: red;");
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

    public void suppression(MouseEvent mouseEvent) throws IOException {
        modeleJeu.joueurs.remove(modeleJeu.getJoueurActuel());
        modeleJeu.supprimerJoueur();
        modeleJeu.deconnexionJoueur();
        SceneManager.chargerScene("SceneMenuPrincipal");
    }

    @FXML
    void retourScenePrecedente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneInfosCompte");
    }

    public void update() {
        String pseudo = modeleJeu.getJoueurActuel().getNom();
        champsTitreCompte.setText(" "+pseudo);
    }
}
