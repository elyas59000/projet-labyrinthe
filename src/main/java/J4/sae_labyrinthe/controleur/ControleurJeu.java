package J4.sae_labyrinthe.controleur;

import J4.sae_labyrinthe.modele.IJoueur;
import J4.sae_labyrinthe.modele.ILabyrinthe;
import J4.sae_labyrinthe.modele.IModeleJeu;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.vue.IObserver;

public class ControleurJeu{
    private ILabyrinthe modele;
    private IObserver vue;

    private IModeleJeu modeleJeu;


    public ControleurJeu(ILabyrinthe modele, IObserver vue){
        this.modele = modele;
        this.vue = vue;
        this.modeleJeu = new ModeleJeu();
    }

    public void deplacerJoueur(String touche){

    }

    public void demarrerProgression(){

        IJoueur joueurChoisi = modeleJeu.selectionnerJoueur();
        if(joueurChoisi != null){
            modeleJeu.lancerPartieProgression(joueurChoisi);
            //vue.afficher("lancement mode progressin ...)

        }
    }












}
