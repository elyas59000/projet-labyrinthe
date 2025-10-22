package J4.sae_labyrinthe.utils;

import J4.sae_labyrinthe.App;
import J4.sae_labyrinthe.modele.IModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeu;
//import J4.sae_labyrinthe.vue.VueLabyrintheBase;
import J4.sae_labyrinthe.vue.IVueLabyrinthe;
import J4.sae_labyrinthe.vue.VueLabyrintheEntier;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static void chargerUneScene(String fichierFxml, Stage stage) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader((App.class.getResource("fxml/" + fichierFxml +".fxml" )));
            Parent root = loader.load();

            if(stage.getScene()==null){
                stage.setScene(new Scene(root));
            }else{
                stage.getScene().setRoot(root);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
/*
    public static void chargerUneScene(VueLabyrintheEntier base, Stage stage) throws IOException {
        try{
            if(stage.getScene()==null) {
                stage.setScene(new Scene(base.getRoot()));
            }else {
                stage.getScene().setRoot(base.getRoot());
            }
            base.getControleur().attacherEvents(stage.getScene());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

   */
    public static void chargerScene(String fichierFxml) throws IOException {
        chargerUneScene(fichierFxml, App.getPrimaryStage());
    }


}
