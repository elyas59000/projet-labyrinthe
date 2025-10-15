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
        Joueur nouveau = new Joueur(nom);
        this.joueurs.add(nouveau);
        return nouveau;
    }

    @Override
    public void afficherProgression(IJoueur joueur) {

    }

    @Override
    public void lancerPartieLibre(int largeur, int hauteur, int tauxMur) {
        this.labyrinthe = new Labyrinthe(largeur,hauteur,tauxMur);
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
        this.observers.remove(observer);
    }

    @Override
    public void add(IObserver observer) {
        this.observers.add(observer);
    }


    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public ILabyrinthe getLabyrinthe() {
        return labyrinthe;
    }
}
