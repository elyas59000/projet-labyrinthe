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

    public void déplacé_joueur(String touche){

    }

    public void démarréProgression(){

        IJoueur joueur_chosi = modeleJeu.selectionnerJoureur();
        if(joueur_chosi != null){
            modeleJeu.lancerPartieProgresion(joueur_chosi);
            //vue.afficher("lancement mode progressin ...)

        }
    }












}
