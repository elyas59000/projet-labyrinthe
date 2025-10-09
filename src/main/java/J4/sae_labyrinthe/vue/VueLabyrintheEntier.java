package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.Labyrinthe;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class VueLabyrintheEntier extends Stage implements IObserver{

    //ControllerJeu controller;

    public VueLabyrintheEntier(){
        this.setTitle("Fenetre laby");
        this.setScene(createSceneContent());
        this.show();
    }

    private Scene createSceneContent(){
        Pane p=new Pane();

        return new Scene(p);
    }


    @Override
    public void update(Labyrinthe l) {

    }
}
