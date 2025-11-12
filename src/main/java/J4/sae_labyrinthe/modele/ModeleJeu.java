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
    public List<Joueur> joueurs;
    protected List<IObserver> observers;
    private Joueur joueurActuel;
    private Cellule positionJoueur;
    private Etape etapeChoisi;
    private int defiChoisi;
    private boolean partieLibre;
    private boolean typeDeVue; /* On part du principe que true -> globale et false -> local*/

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
        partieLibre =true;
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
        partieLibre =false;
        Defi d = etapeChoisi.getDefiParDifficulte(defiChoisi);
        if(etapeChoisi.getNumero() > 3){
            this.labyrinthe = new LabyrintheParfait(20,20,etapeChoisi);
            ((LabyrintheParfait) this.labyrinthe).placerSortieA(d.getDistance());
        }else {
            this.labyrinthe = new Labyrinthe(d.getCote(),d.getCote(),d.getPourcentageMurs(),etapeChoisi);
        }
        this.positionJoueur = this.labyrinthe.getEntree();
    }

    /**
     * Retourne le joueur actuellement sélectionné (connecté).
     *
     * @return Le joueur actif ou null s'il n'y en a pas
     * @author Nathan Philippe
     */
    @Override
    public Joueur selectionnerJoueur() {
        return joueurActuel;
    }

    /**
     * Retire un observateur de la liste des observers (utilisé lors de la
     * navigation entre scènes pour éviter des notifications indésirables).
     *
     * @param observer Observateur à retirer
     */
    @Override
    public void remove(IObserver observer) {
        this.observers.remove(observer);
    }

    /**
     * Ajoute un observateur qui sera notifié lors des changements du modèle.
     *
     * @param observer Observateur à ajouter
     */
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
    public ILabyrinthe getLabyrinthe() {

        return this.labyrinthe;
    }


    /**
     * Notifie tous les observateur
     */
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
    public void deplacerJoueur(String direction) {
        if (labyrinthe == null || positionJoueur == null) return;

        int x = positionJoueur.getX();
        int y = positionJoueur.getY();
        int nx = x;
        int ny = y;

        switch (direction.toLowerCase().charAt(0)) {
            case 'z' -> ny--;
            case 's' -> ny++;
            case 'q' -> nx--;
            case 'd' -> nx++;
            default -> {
                System.out.println("mauvaise direction");
                return;
            }
        }

        // Délégation selon le type concret du labyrinthe
        if (labyrinthe instanceof LabyrintheParfait) {
            deplacerDansLabyrintheParfait((LabyrintheParfait) labyrinthe, nx, ny);
        } else if (labyrinthe instanceof Labyrinthe) {
            deplacerDansLabyrintheClassique((Labyrinthe) labyrinthe, nx, ny);
        } else {
            System.out.println("Type de labyrinthe non reconnu.");
        }
    }

    private void deplacerDansLabyrintheClassique(Labyrinthe laby, int nx, int ny) {
        if (nx < 0 || nx >= laby.getLargeur() || ny < 0 || ny >= laby.getHauteur()) return;

        Cellule next = laby.getGrille()[ny][nx];
        if (!next.estMur()) {
            positionJoueur = next;
            verifierSortie(laby, next);
            notifierObservateurs();
        } else {
            System.out.println("C'est un mur");
        }
    }

    private void deplacerDansLabyrintheParfait(LabyrintheParfait laby, int nx, int ny) {
        int x = positionJoueur.getX();
        int y = positionJoueur.getY();

        // Toujours une case adjacente dans la logique du labyrinthe
        if (!laby.peutAller(y, x, ny, nx)) {
            System.out.println("C'est un mur");
            return;
        }
        positionJoueur = laby.getGrille()[nx][ny];
        verifierSortie(laby, laby.getGrille()[nx][ny]);
        notifierObservateurs();
    }


    private void verifierSortie(ILabyrinthe laby, Cellule next) {
        if (next == laby.getSortie()) {
            if (!etapeChoisi.getDefiParDifficulte(defiChoisi).estReussi()) {
                etapeChoisi.getDefiParDifficulte(defiChoisi).setReussi(true);
                joueurActuel.ajouterScore(1);
            }

            if (laby.getEtape() != null && etapeChoisi.getNumero() == joueurActuel.getEtapeMax()) {
                joueurActuel.avancerEtape();
                try {
                    Save.saveToFile((ArrayList<Joueur>) joueurs);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }


    /**
     * Retourne le nombre d'étapes débloquées pour le joueur actuel.
     *
     * @return Étape maximale atteinte par le joueur
     */
    public int etapeJoueur(){
        return joueurActuel.getEtapeMax();
    }

    /**
     * Retourne la cellule de sortie du labyrinthe courant.
     *
     * @return Cellule de sortie
     */
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

    public boolean getPartieLibre() {
        return partieLibre;
    }

    /**
     * Met à jour le joueur actuel dans la liste des joueurs et sauvegarde les modifications.
     * Cette méthode trouve l'ancien joueur dans la liste et le remplace par le joueur actuel mis à jour.
     *
     * @throws IOException En cas d'erreur lors de la sauvegarde
     */
    public void sauvegarderJoueur() {
        if (joueurActuel != null) {
            // Cherche l'index du joueur actuel dans la liste
            for (int i = 0; i < joueurs.size(); i++) {
                if (joueurs.get(i).getNom().equals(joueurActuel.getNom())) {
                    // Remplace l'ancien joueur par le joueur actuel mis à jour
                    joueurs.set(i, joueurActuel);
                    break;
                }
            }
            try {
                // Sauvegarde les modifications dans le fichier
                Save.saveToFile((ArrayList<Joueur>) joueurs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void supprimerJoueur(){
        if (joueurActuel != null) {
            for (int i = 0; i < joueurs.size(); i++) {
                if (joueurs.get(i).getNom().equals(joueurActuel.getNom())) {
                    joueurs.remove(joueurActuel);
                    break;
                }
            }

            try {
                // Sauvegarde les modifications dans le fichier
                Save.saveToFile((ArrayList<Joueur>) joueurs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeAll() {
        observers.clear();
    }

}
