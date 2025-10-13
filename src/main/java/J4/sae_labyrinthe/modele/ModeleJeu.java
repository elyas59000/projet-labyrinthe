package J4.sae_labyrinthe.modele;

import J4.sae_labyrinthe.vue.IObserver;

import java.util.ArrayList;
import java.util.List;

public class ModeleJeu implements  IModeleJeu{

    private ILabyrinthe labyrinthe;
    private List<Joueur> joueurs;
    protected List<IObserver> observers;

    public ModeleJeu(){
        this.joueurs = new ArrayList<>();
        this.observers =new ArrayList<>();
    }



    @Override
    public Joueur creerJoueur(String nom) {
        return null;
    }

    @Override
    public void afficherProgression(IJoueur joueur) {

    }

    @Override
    public void lancerPartieLibre(int largeur, int hauteur, int tauxMur) {

    }

    @Override
    public void lancerPartieProgression(IJoueur j) {

    }

    @Override
    public Joueur selectionnerJoueur() {
        return null;
    }

    @Override
    public void remove(IObserver observer) {

    }

    @Override
    public void add(IObserver observer) {

    }


    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public ILabyrinthe getLabyrinthe() {
        return labyrinthe;
    }
}
