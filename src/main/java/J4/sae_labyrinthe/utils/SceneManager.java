package J4.sae_labyrinthe.utils;

import java.io.IOException;

import J4.sae_labyrinthe.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



/**
 * Petit utilitaire centralisant le chargement des différentes scènes
 * FXML de l'application. Utilise la classe `App` pour récupérer la
 * fenêtre principale et remplace la racine de la scène courante.
 *
 * Les méthodes exposées permettent de charger une scène par son nom de
 * fichier FXML (sans l'extension ni le préfixe de dossier).
 *
 * @author Nathan Philippe
 */
public class SceneManager {

    /**
     * Charge un fichier FXML et remplace la racine de la scène du stage
     * fourni. Si le stage n'a pas encore de scène, en crée une nouvelle.
     *
     * @param fichierFxml nom du fichier FXML (sans préfixe 'fxml/' et sans '.fxml')
     * @param stage fenêtre JavaFX cible
     * @throws IOException en cas d'erreur de chargement
     */
    private static void chargerUneScene(String fichierFxml, Stage stage) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader((App.class.getResource("fxml/" + fichierFxml +".fxml" )));
            Parent root = loader.load();

            if(stage.getScene()==null){
                stage.setScene(new Scene(root));
            }else{
                stage.getScene().setRoot(root);
            }

        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void chargerScene(String fichierFxml) throws IOException {
        chargerUneScene(fichierFxml, App.getPrimaryStage());
    }

}
