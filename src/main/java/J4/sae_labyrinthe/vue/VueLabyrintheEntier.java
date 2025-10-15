package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.IModeleJeu;
import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.ModeleJeu;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class VueLabyrintheEntier extends Stage implements IObserver{

    //ControllerJeu controller;
    IModeleJeu modele;

    public VueLabyrintheEntier(IModeleJeu modele){
        this.modele= modele;
        this.setTitle("Fenetre laby");
        this.setScene(createSceneContent());
        this.show();
    }

    private Scene createSceneContent(){
        GridPane p=new GridPane();
        afficherLabyrinthe(p);
        return new Scene(p);
    }


    @Override
    public void update(Labyrinthe l) {

    }

    private  void afficherLabyrinthe(GridPane p){
        Rectangle cell;
        for(int i=0;i<40;i++){
            for(int j=0;j<40;j++){
                cell = new Rectangle(25,25);
                if (this.modele.getLabyrinthe().getGrille()[i][j].estMur()){
                    cell.setFill(Paint.valueOf("Black"));
                }else {
                    cell.setFill(Paint.valueOf("blue"));
                }

                p.add(cell,i,j);
            }
        }
    }
}
