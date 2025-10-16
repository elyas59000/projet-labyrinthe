package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.IJoueur;
import J4.sae_labyrinthe.modele.ILabyrinthe;
import J4.sae_labyrinthe.modele.IModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.vue.IObserver;
import J4.sae_labyrinthe.vue.VueLabyrintheEntier;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class ControleurJeu{
    private IObserver vue;

    private IModeleJeu modele;


    public ControleurJeu(IModeleJeu modele, IObserver vue){
        this.modele =  modele;
        this.vue = vue;
        this.getVue().getScene().setOnKeyPressed(this::deplacerJoueur);
    }

    public void deplacerJoueur(KeyEvent event){
        String direction = event.getCode().getChar();
        this.modele.deplacerJoueur(direction);
        System.out.println(this.getModele().getPositionJoueur().getX() + " " + this.getModele().getPositionJoueur().getY());
    }

    public void demarrerProgression(){

        IJoueur joueurChoisi = modele.selectionnerJoueur();
        if(joueurChoisi != null){
            modele.lancerPartieProgression(joueurChoisi);
            //vue.afficher("lancement mode progressin ...)

        }
    }

    public ModeleJeu getModele() {
        return (ModeleJeu)  modele;
    }

    public VueLabyrintheEntier getVue() {
        return (VueLabyrintheEntier) vue;
    }
}
