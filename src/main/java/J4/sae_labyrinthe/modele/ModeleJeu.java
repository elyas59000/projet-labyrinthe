package J4.sae_labyrinthe.modele;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import J4.sae_labyrinthe.exception.ValidationException;
import J4.sae_labyrinthe.modele.Item.Bonus.PlusVision;
import J4.sae_labyrinthe.modele.Item.Cleanse;
import J4.sae_labyrinthe.modele.Item.IItem;
import J4.sae_labyrinthe.modele.Item.Malus.MoinsVision;
import J4.sae_labyrinthe.utils.LabyrintheDuJourManager;
import J4.sae_labyrinthe.utils.Save;
import J4.sae_labyrinthe.utils.SaveScoreDuJour;
import J4.sae_labyrinthe.utils.ScoreDuJour;
import J4.sae_labyrinthe.vue.Biome;
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
    private boolean partieDuJourEnCours = false;
    private int tempsDuJour = 0;
    private ScoreDuJour scoresDuJour;
    private LocalDateTime debutLabyrintheDuJour;
    // items
    private HashSet<IItem> setItem;
    private int visionRange;
    private HashMap<Cellule, Integer> ward;
    private Biome biome;


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
    public void lancerPartieLibre(int largeur, int hauteur, int tauxMur,Biome biome) {
        partieLibre =true;
        this.labyrinthe = new Labyrinthe(largeur,hauteur,tauxMur);
        this.positionJoueur = this.labyrinthe.getEntree();
        this.biome = biome;
    }

    /**
     * Lance le labyrinthe du jour : un labyrinthe unique et fixé pour une date donnée.
     */
    public void lancerLabyrintheDuJour() {
        ILabyrinthe laby = LabyrintheDuJourManager.getLabyrintheDuJour();
        this.labyrinthe = laby;
        this.positionJoueur = laby.getEntree();
        this.debutLabyrintheDuJour = LocalDateTime.now();
        this.partieDuJourEnCours = true;
        this.biome=Biome.values()[(int)(Math.random() * Biome.values().length)];
    }

    public long terminerLabyrintheDuJour() {
        if (debutLabyrintheDuJour == null) return 0;
        Duration duree = Duration.between(debutLabyrintheDuJour, LocalDateTime.now());
        return duree.getSeconds(); // temps en secondes
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
            this.labyrinthe = new LabyrintheParfait(5,5,etapeChoisi);
            ((LabyrintheParfait) this.labyrinthe).placerSortieA(5);
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

    @Override
    public Biome getBiome() {
        return this.biome;
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
        int ny,nx;
        int x = positionJoueur.getX();
        int y = positionJoueur.getY();
        nx = x;
        ny = y;
        if(labyrinthe instanceof Labyrinthe){
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
        } else if (labyrinthe instanceof  LabyrintheParfait) {
            switch (direction.toLowerCase().charAt(0)) {
                case 'z' -> nx--;
                case 's' -> nx++;
                case 'q' -> ny--;
                case 'd' -> ny++;
                default -> {
                    System.out.println("mauvaise direction");
                    return;
                }

            }
        }


        if(this.labyrinthe instanceof  LabyrintheParfait){
            deplacerDansLabyrintheParfait((LabyrintheParfait) labyrinthe,nx,ny,direction);
        }else if(this.labyrinthe instanceof Labyrinthe) {
            deplacerDansLabyrintheClassique((Labyrinthe) labyrinthe, nx, ny,direction);
        }
    }

    private void deplacerDansLabyrintheClassique(Labyrinthe laby, int nx, int ny,String direction) {
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

    private void deplacerDansLabyrintheParfait(LabyrintheParfait laby, int nx, int ny,String direction) {

        int x = positionJoueur.getX();
        int y = positionJoueur.getY();

        if (nx < 0 || nx >= laby.getHauteur() || ny < 0 || ny >= laby.getLargeur()) {
            System.out.println("Hors de la grille !");
            return;
        }

        if (!laby.peutAller(x, y, nx, ny)) {
            System.out.println("C'est un mur");
            return;
        }

        positionJoueur = laby.getGrille()[nx][ny];


        if (positionJoueur.getX() == laby.getSortie().getX() &&
                positionJoueur.getY() == laby.getSortie().getY()) {
            System.out.println("Bravo ! Vous avez atteint la sortie !");
        }

        notifierObservateurs();
    }



    private void verifierSortie(ILabyrinthe laby, Cellule next) {
        if (next == laby.getSortie()) {
            if (partieDuJourEnCours) {
                tempsDuJour = (int) terminerLabyrintheDuJour();
                scoresDuJour = new ScoreDuJour(joueurActuel.getNom(), tempsDuJour);
                SaveScoreDuJour.sauvegarderScore(scoresDuJour);

                return; // IMPORTANT : empêche le mode progression d'agir
            }


            if (etapeChoisi == null) {
                System.out.println("Sortie atteinte (mode libre)");
                return;
            }

            joueurActuel.ajouterScore(defiChoisi);

            // Marquer le défi comme réussi
            if (!etapeChoisi.getDefiParDifficulte(defiChoisi).estReussi()) {
                etapeChoisi.getDefiParDifficulte(defiChoisi).setReussi(true);
            }

            // Faire avancer l'étape si besoin
            if (laby.getEtape() != null && etapeChoisi.getNumero() == joueurActuel.getEtapeMax()) {
                joueurActuel.avancerEtape();
            }

            // Sauvegarde classique des joueurs
            try {
                Save.saveToFile((ArrayList<Joueur>) joueurs);
            } catch (IOException e) {
                System.out.println(e.getMessage());
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
        if (this.etapeChoisi == null) {
            return -1;
        }
        return this.etapeChoisi.getNumero();
    }

    public boolean getPartieLibre() {
        return partieLibre;
    }

    public String getPartie(){
        if(partieLibre){
            return "SceneModeLibre";
        }
        else if(partieDuJourEnCours){
            partieDuJourEnCours = false;
            return "SceneLabyrintheDuJour";
        }
        else{
            return "SceneProg";
        }
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


    /* Items */

    public void setPositionJoueur(Cellule pos) {
        this.positionJoueur = pos;
    }

    public void setVisionRange(int range){
        this.visionRange = range;
    }
    public int getVisionRange(){
        return this.visionRange;
    }

    public HashMap<Cellule, Integer> getWard() {
        return ward;
    }
    public void setWard(HashMap<Cellule, Integer> ward) {
        this.ward = ward;
    }
    public void setWard(Cellule cell, int range) {
        setWard((HashMap<Cellule, Integer>) Map.of(cell, range));
    }


    public void itemApplyEffect(){
        if (containCleanse()){
            cleanse();
        }

        for (IItem item : setItem){
            item.applyEffect();
        }
    }

    public void cleanse(){
        setItem.clear();
    }

    public void removeItem(IItem item){
        setItem.remove(item);
    }
    public void removeItems(Set<IItem> items){
        setItem.removeAll(items);
    }

    public void addItem(IItem item){
        setItem.add(item);
    }

    public Set<IItem> getSetItem(){
        return this.setItem;
    }

    public boolean containPlusVision(){
        for (IItem item : setItem){
            if (item.getClass().equals(PlusVision.class) ) {
                return true;
            }
        }
        return false;
    }

    public boolean containCleanse(){
        for (IItem item : setItem){
            if (item.getClass().equals(Cleanse.class) ) {
                return true;
            }
        }
        return false;
    }

    public boolean rangeZero(){
        return visionRange == 0;
    }

    public Set<IItem> getVisionItems(){
        HashSet<IItem> temp = new HashSet<>();
        for (IItem item : setItem){
            if (item.getClass().equals(PlusVision.class) ||
                    item.getClass().equals(MoinsVision.class)) {
                temp.add(item);
            }
        }
        return temp;
    }

    public void removeWard(){
        ward.clear();
    }

    public Set<IItem> getMalus(){
        HashSet<IItem> temp = new HashSet<>();
        for (IItem item : setItem){
            if (item.isBonus() == -1){
                temp.add(item);
            }
        }
        return temp;
    }
    public Set<IItem> getBonus(){
        HashSet<IItem> temp = new HashSet<>();
        for (IItem item : setItem){
            if (item.isBonus() == 1){
                temp.add(item);
            }
        }
        return temp;
    }
    public Set<IItem> getNeutral(){
        HashSet<IItem> temp = new HashSet<>();
        for (IItem item : setItem){
            if (item.isBonus() == 0){
                temp.add(item);
            }
        }
        return temp;
    }

}
