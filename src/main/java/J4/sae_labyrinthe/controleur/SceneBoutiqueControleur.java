package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.*;
import J4.sae_labyrinthe.modele.Item.IItem;
import J4.sae_labyrinthe.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class SceneBoutiqueControleur {

    // Prix
    public Label prixVision;
    public Label prixSentir;
    public Label prixBriseMur;
    public Label prixTeleport;

    // Inventaire
    public Label invBriseMur;
    public Label invTeleport;
    public Label invSentir;
    public Label invVision;
    @FXML private Label msgBriseMur;
    @FXML private Label msgTeleport;
    @FXML private Label msgSentir;
    @FXML private Label msgVision;



    // Argent du joueur
    @FXML
    private Text txtArgent;

    private ModeleJeu modele = ModeleJeuSingleton.getInstance();
    private Joueur joueur = modele.getJoueurActuel();


    public void initialize() {
        // Argent
        txtArgent.setText(joueur.getScore() + "");

        // Prix
        prixBriseMur.setText(Boutique.getInstance().getPrixItemByNom("brisemur") + "");
        prixTeleport.setText(Boutique.getInstance().getPrixItemByNom("teleportation") + "");
        prixSentir.setText(Boutique.getInstance().getPrixItemByNom("sentir") + "");
        prixVision.setText(Boutique.getInstance().getPrixItemByNom("vision") + "");

        // Inventaire
        invBriseMur.setText(joueur.getQuantiteItem("brisemur"));
        invTeleport.setText(joueur.getQuantiteItem("tp"));
        invSentir.setText(joueur.getQuantiteItem("sentirsortie"));
        invVision.setText(joueur.getQuantiteItem("plusvision"));

        // On efface les messages d’erreur
        clearAllMessages();
    }



    private boolean acheterItemEtMettreAJour(String nomItem, Label labelInventaire, Label labelMessage) {
        IItem item = Boutique.getInstance().getItemByNom(nomItem);
        boolean succes = Boutique.getInstance().acheterItem(item, joueur);

        if (succes) {
            txtArgent.setText(joueur.getScore() + "");
            labelInventaire.setText(joueur.getQuantiteItem(nomItem));
            labelMessage.setText(""); // effacer message d’erreur
            return true;
        } else {
            labelMessage.setText("Pas assez d’argent !");
            return false;
        }
    }

    private void clearAllMessages() {
        msgBriseMur.setText("");
        msgTeleport.setText("");
        msgSentir.setText("");
        msgVision.setText("");
    }



    @FXML
    public void acheterBriseMur(MouseEvent event) {
        acheterItemEtMettreAJour("brisemur", invBriseMur, msgBriseMur);
    }

    @FXML
    public void acheterTeleportation(MouseEvent event) {
        acheterItemEtMettreAJour("teleportation", invTeleport, msgTeleport);
    }

    @FXML
    public void acheterSentirSortie(MouseEvent event) {
        acheterItemEtMettreAJour("sentir", invSentir, msgSentir);
    }

    @FXML
    public void acheterBaliseVision(MouseEvent event) {
        acheterItemEtMettreAJour("vision", invVision, msgVision);
    }
    @FXML
    void retourScenePrecendente(MouseEvent event) throws IOException {
        SceneManager.chargerScene("SceneMenuPrincipal");
    }
}
