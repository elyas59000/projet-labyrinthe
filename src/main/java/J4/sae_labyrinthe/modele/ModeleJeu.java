package J4.sae_labyrinthe.modele;

import J4.sae_labyrinthe.exception.ValidationException;
import J4.sae_labyrinthe.vue.IObserver;

import java.util.ArrayList;
import java.util.List;

public class ModeleJeu implements  IModeleJeu{

    private ILabyrinthe labyrinthe;
    private List<Joueur> joueurs;
    protected List<IObserver> observers;
    private Joueur joueurActuel;
    private Cellule positionJoueur;

    public ModeleJeu(){
        this.joueurs = new ArrayList<>();
        this.observers =new ArrayList<>();
    }

    @Override
    public Joueur creerJoueur(String nom, String mdp) throws ValidationException {
        boolean pseudoDejaExistant=false;
        for(Joueur joueur : this.joueurs){
            if(joueur.getNom().equals(nom)){
                pseudoDejaExistant=true;
            }
        }
        if(!pseudoDejaExistant){
            Joueur nouveau = new Joueur(nom,mdp);
            this.joueurs.add(nouveau);
            this.joueurActuel = nouveau;
            notifierObservateurs();
            return nouveau;
        }
        else{
            throw new ValidationException("L'identifant est déjà utilisé");
        }

    }

    public void connexionJoueur(String nom,String mdp) throws ValidationException {
        if(this.joueurs.isEmpty()){throw new ValidationException("Aucun joueur n'existe pour l'instant");}
        for(Joueur joueur : this.joueurs){
            if(joueur.getNom().equals(nom)){
                if(joueur.getMdp().equals(mdp)){
                    joueurActuel = joueur;
                    notifierObservateurs();
                }else {
                    throw new ValidationException("Mot de passe incorrect");
                }
            }else {
                throw new ValidationException("Le joueur n'existe pas");
            }
        }

    }

    public void deconnexionJoueur() {
        this.joueurActuel=null;
    }

    @Override
    public void afficherProgression(IJoueur joueur) {

    }

    @Override
    public void lancerPartieLibre(int largeur, int hauteur, int tauxMur) {
        this.labyrinthe = new Labyrinthe(largeur,hauteur,tauxMur);
        this.positionJoueur = this.labyrinthe.getEntree();
    }

    @Override
    public void lancerPartieProgression(IJoueur j) {
    }

    @Override
    public Joueur selectionnerJoueur() {
        return joueurActuel;
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

    public Cellule getPositionJoueur() {
        return positionJoueur;
    }

    public Joueur getJoueurActuel() {
        return joueurActuel;
    }

    public Labyrinthe getLabyrinthe() {
        return (Labyrinthe) this.labyrinthe;
    }


    public void notifierObservateurs() {
        for (IObserver obs : observers) {
            obs.update();
        }
    }


    public void deplacerJoueur(String direction) {
        if (labyrinthe == null || positionJoueur == null) return;

        int x = positionJoueur.getX();
        int y = positionJoueur.getY();

        int nx = x, ny = y;
        if(direction.toLowerCase().startsWith("z")){
            ny--;
        }
        else if(direction.toLowerCase().startsWith("s")){
            ny++;
        }
        else if(direction.toLowerCase().startsWith("q")){
            nx--;
        }
        else if(direction.toLowerCase().startsWith("d")){
            nx++;
        }
        else{
            System.out.println("mauvaise direction");
            return;
        }
        this.notifierObservateurs();

        if (nx >= 0 && nx < labyrinthe.getLargeur() && ny >= 0 && ny < labyrinthe.getHauteur()) {
            Cellule next = ((Labyrinthe) labyrinthe).getGrille()[ny][nx];
            if (!next.estMur()) {
                positionJoueur = next;
                if (next == ((Labyrinthe) labyrinthe).getSortie()) {
                    System.out.println("Bravo ! Vous avez atteint la sortie !");
                }
                notifierObservateurs();
            }
            else{
                System.out.println("C'est un mur");
            }
        }
    }
}
