package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.IJoueur;
import J4.sae_labyrinthe.modele.ILabyrinthe;
import J4.sae_labyrinthe.modele.IModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.utils.SceneManager;
import J4.sae_labyrinthe.vue.IObserver;
import J4.sae_labyrinthe.vue.IVueLabyrinthe;
import J4.sae_labyrinthe.vue.VueLabyrintheEntier;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ControleurLabyrinthe {
    private IVueLabyrinthe vue;

    private IModeleJeu modele;


    public ControleurLabyrinthe(IModeleJeu modele){
        this.modele =  modele;
        this.vue = vue;
        //this.getVue().getScene().setOnKeyPressed(this::deplacerJoueur);
    }

    public void deplacerJoueur(KeyEvent event){
        String direction = correspodanceTouche(event.getCode());
        this.modele.deplacerJoueur(direction);
        System.out.println(this.getModele().getPositionJoueur().getX() + " " + this.getModele().getPositionJoueur().getY());
    }

    public ModeleJeu getModele() {
        return (ModeleJeu)  modele;
    }


    private String correspodanceTouche(KeyCode event){
        String direction = "";
        if(KeyCode.UP.equals(event)){
            direction= "z";
        } else if (KeyCode.DOWN.equals(event)) {
            direction= "s";
        }else if (KeyCode.LEFT.equals(event)) {
            direction= "q";
        }else if (KeyCode.RIGHT.equals(event)) {
            direction= "d";
        }else {
            direction = event.getChar();
        }
        return direction;
    }
}
