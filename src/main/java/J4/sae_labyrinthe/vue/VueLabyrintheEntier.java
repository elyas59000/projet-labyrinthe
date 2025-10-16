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
    private final GridPane P =new GridPane();


    public VueLabyrintheEntier(IModeleJeu modele){
        this.modele=  modele;
        this.setTitle("Fenetre laby");
        this.setScene(createSceneContent());
        this.show();
    }

    private Scene createSceneContent(){
        afficherLabyrinthe(P);
        return new Scene(P);
    }


    @Override
    public void update() {
        afficherLabyrinthe(P);
    }

    private  void afficherLabyrinthe(GridPane p){
        p.getChildren().clear();
        Rectangle cell;
        for(int i=0;i<this.getModele().getLabyrinthe().getHauteur();i++){
            for(int j=0;j<this.getModele().getLabyrinthe().getLargeur();j++){
                cell = new Rectangle(10,10);
                if (this.getModele().getLabyrinthe().getGrille()[i][j].estMur()){
                    cell.setFill(Paint.valueOf("Black"));
                }else if(this.getModele().getLabyrinthe().getGrille()[i][j].equals(this.getModele().getPositionJoueur())){
                    cell.setFill(Paint.valueOf("blue"));
                }else {
                    cell.setFill(Paint.valueOf("White"));
                }

                p.add(cell,i,j);
            }
        }
    }

    public ModeleJeu getModele() {
        return (ModeleJeu) modele;
    }

    public GridPane getP() {
        return P;
    }
}
