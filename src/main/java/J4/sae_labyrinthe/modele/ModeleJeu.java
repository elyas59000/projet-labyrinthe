package J4.sae_labyrinthe.modele;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import J4.sae_labyrinthe.exception.ValidationException;
import J4.sae_labyrinthe.utils.Save;
import J4.sae_labyrinthe.vue.IObserver;

/**
 * Classe principale gérant la logique du jeu de labyrinthe.
 * Gère les joueurs, le labyrinthe actuel, la progression et les déplacements.
 * Implémente le pattern Observer pour notifier les vues des changements.
 *
 * @author Nathan Philippe
 * @author Anas ACHOUCH
 * @author Clément Roty
 * @author Elyas Rabhiu
 */
public class ModeleJeu implements IModeleJeu {

    private ILabyrinthe labyrinthe;
    public List<Joueur> joueurs;    // créer setJoueurs() et remettre en private
    protected List<IObserver> observers;
    private Joueur joueurActuel;
    private Cellule positionJoueur;
    private Etape etapeChoisi;
    private int defiChoisi;

    /**
     * Constructeur par défaut.
     * Initialise les listes de joueurs et d'observateurs.
     * 
     * @author Nathan Philippe
     */
    public ModeleJeu() {
        this.joueurs = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    /**
     * Crée un nouveau joueur et l'ajoute à la liste des joueurs.
     * 
     * @param nom Nom du nouveau joueur
     * @param mdp Mot de passe du nouveau joueur
     * @return Le joueur créé
     * @throws ValidationException Si le nom est déjà utilisé
     * @throws IOException En cas d'erreur lors de la sauvegarde
     * @author Nathan Philippe
     */
    @Override
    public Joueur creerJoueur(String nom, String mdp) throws ValidationException, IOException {
        boolean pseudoDejaExistant=false;
        for(Joueur joueur : this.joueurs){
            if(joueur.getNom().equals(nom)){
                pseudoDejaExistant=true;
                break;
            }
        }
        if(!pseudoDejaExistant){
            Joueur nouveau = new Joueur(nom,mdp);
            this.joueurs.add(nouveau);
            this.joueurActuel = nouveau;
            Save.saveToFile((ArrayList<Joueur>) joueurs);
            notifierObservateurs();
            return nouveau;
        }
        else{
            throw new ValidationException("L'identifant est déjà utilisé");
        }

    }

    /**
     * Connecte un joueur existant.
     * 
     * @param nom Nom du joueur
     * @param mdp Mot de passe du joueur
     * @throws ValidationException Si les identifiants sont incorrects ou si aucun joueur n'existe
     * @author Nathan Philippe
     */
    @Override
    public void connexionJoueur(String nom, String mdp) throws ValidationException {
        if(this.joueurs.isEmpty()){throw new ValidationException("Aucun joueur n'existe pour l'instant");}

        boolean joueurTrouve = false;
        for(Joueur joueur : this.joueurs){
            if(joueur.getNom().equals(nom)){
                if(joueur.getMdp().equals(mdp)){
                    joueurActuel = joueur;
                    joueurTrouve = true;
                    notifierObservateurs();

                }else {
                    throw new ValidationException("Mot de passe incorrect");
                }
                break;
            }
        }
        if (!joueurTrouve){
            throw new ValidationException("Le joueur n'existe pas");
        }

    }

    /**
     * Déconnecte le joueur actuel.
     * 
     * @author Nathan Philippe
     */
    @Override
    public void deconnexionJoueur() {
        this.joueurActuel = null;
    }

    /**
     * Lance une partie en mode libre avec un labyrinthe personnalisé.
     * 
     * @param largeur Largeur du labyrinthe
     * @param hauteur Hauteur du labyrinthe
     * @param tauxMur Pourcentage de murs dans le labyrinthe
     * @author Nathan Philippe
     */
    @Override
    public void lancerPartieLibre(int largeur, int hauteur, int tauxMur) {
        this.labyrinthe = new Labyrinthe(largeur,hauteur,tauxMur);
        this.positionJoueur = this.labyrinthe.getEntree();
    }

    /**
     * Lance une partie en mode progression
     * 
     * @author Nathan Philippe
     */
    @Override
    public void lancerPartieProgression() {
        Defi d = etapeChoisi.getDefiParDifficulte(defiChoisi);
        this.labyrinthe = new Labyrinthe(d.getCote(),d.getCote(),d.getPourcentageMurs(),etapeChoisi);
        this.positionJoueur = this.labyrinthe.getEntree();
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


    /**
     * @return La liste de tous les joueurs enregistrés
     * @author Nathan Philippe
     */
    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    /**
     * @return La position actuelle du joueur dans le labyrinthe
     * @author Nathan Philippe
     */
    public Cellule getPositionJoueur() {
        return positionJoueur;
    }

    /**
     * @return Le joueur actuellement connecté
     * @author Nathan Philippe
     */
    public Joueur getJoueurActuel() {
        return joueurActuel;
    }

    /**
     * @return Le labyrinthe actuel de la partie
     * @author Nathan Philippe
     */
    @Override
    public Labyrinthe getLabyrinthe() {
        return (Labyrinthe) this.labyrinthe;
    }


    public void notifierObservateurs() {
        for (IObserver obs : observers) {
            obs.update();
        }
    }


    /**
     * Déplace le joueur dans le labyrinthe selon une direction donnée.
     * Vérifie si le déplacement est possible et met à jour la position.
     * Notifie les observateurs après chaque déplacement.
     * 
     * @param direction Direction du déplacement ("z" pour haut, "s" pour bas, "q" pour gauche, "d" pour droite)
     * @author Nathan Philippe
     */
    @Override
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
                    if(((Labyrinthe) labyrinthe).getEtape() != null){
                        joueurActuel.avancerEtape();
                        try{
                            Save.saveToFile((ArrayList<Joueur>) joueurs);
                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                }
                notifierObservateurs();
            }
            else{
                System.out.println("C'est un mur");
            }
        }
    }

    public int etapeJoueur(){
        return joueurActuel.getEtapeMax();
    }

    public Cellule getSortie(){
        return labyrinthe.getSortie();
    }

    /**
     * Définit l'étape choisie pour le mode progression.
     * 
     * @param etapeChoisi Numéro de l'étape à définir
     * @author Nathan Philippe
     */
    public void setEtapeChoisi(int etapeChoisi) {
        this.etapeChoisi = new Etape(etapeChoisi);
    }

    /**
     * Définit le niveau de défi choisi pour l'étape courante.
     * 
     * @param defiChoisi Niveau de difficulté du défi
     * @author Nathan Philippe
     */
    public void setDefiChoisi(int defiChoisi) {
        this.defiChoisi = defiChoisi;
    }

    /**
     * @return Le numéro de l'étape actuellement choisie
     * @author Nathan Philippe
     */
    public int etapeChoisi() {
        return etapeChoisi.getNumero();
    }

}
