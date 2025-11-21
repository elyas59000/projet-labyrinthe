package J4.sae_labyrinthe.modele;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import J4.sae_labyrinthe.exception.ValidationException;
import J4.sae_labyrinthe.modele.Item.*;
import J4.sae_labyrinthe.modele.Item.Bonus.*;
import J4.sae_labyrinthe.modele.Item.Malus.MoinsVision;
import J4.sae_labyrinthe.modele.Item.Malus.TpDebut;
import J4.sae_labyrinthe.utils.*;
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
    private boolean brouillard = false;
    private boolean typeDeVue; /* On part du principe que true -> globale et false -> local*/
    private boolean partieDuJourEnCours = false;
    private boolean partieContreLaMontre =false;
    private int tempsDuJour = 0;
    private ScoreDuJour scoresDuJour;
    private LocalDateTime debutLabyrintheDuJour;
    private int tempsLimite = 0;
    // items
    private ArrayList<IItem> listItem;     // items recupéré par le joueur
    private HashMap<Cellule, IItem> items;  // items dans le labyrinthe
    private int visionRange;

    private final int NBBONUS = 3;
    private final int NBMALUS = 2;
    private final int NBNEUTRE = 4;

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
     * @param distance distance minimal entre entré et sortie
     * @param biome texture de la map
     * @author Nathan Philippe
     */
    @Override
    public void lancerPartieLibre(int largeur, int hauteur, int tauxMur, int distance, Biome biome, boolean brouillard) {
        partieLibre =true;
        this.brouillard = brouillard;
        if(tauxMur == 0){
            this.labyrinthe = new LabyrintheParfait(largeur/2,hauteur/2,distance);

        }
        else{
            this.labyrinthe = new Labyrinthe(largeur,hauteur,tauxMur);
        }
        this.positionJoueur = this.labyrinthe.getEntree();
        //genererItems(5);
        //nouveauListItem();
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
        this.biome=Biome.DESERT;
        this.brouillard = false;
        //this.biome=Biome.values()[(int)(Math.random() * Biome.values().length)];
    }

    public long terminerLabyrintheDuJour() {
        if (debutLabyrintheDuJour == null) return 0;
        Duration duree = Duration.between(debutLabyrintheDuJour, LocalDateTime.now());
        return duree.getSeconds(); // temps en secondes
    }

    public boolean getPartieDuJourEnCours() {
        return this.partieDuJourEnCours;
    }

    /**
     * Lance une partie en mode progression
     *
     * @author Nathan Philippe
     */
    @Override
    public void lancerPartieProgression() {
        partieLibre =false;
        this.brouillard = false;
        Defi d = etapeChoisi.getDefiParDifficulte(defiChoisi);
        if(etapeChoisi.getNumero() > 3){
            this.labyrinthe = new LabyrintheParfait(d.getCote()/2,d.getCote2()/2,etapeChoisi,d.getDistance());
        }else {
            this.labyrinthe = new Labyrinthe(d.getCote(),d.getCote2(),d.getPourcentageMurs(),etapeChoisi);
        }
        this.positionJoueur = this.labyrinthe.getEntree();
        //genererItems(5);
        //nouveauListItem();
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
    public void setBiome(Biome biome) {
        this.biome = biome;
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
        deplacerDansLabyrinthe(labyrinthe,nx,ny);

    }
    private void deplacerDansLabyrinthe(ILabyrinthe laby, int nx, int ny) {
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
    private void verifierSortie(ILabyrinthe laby, Cellule next) {
        if (next == laby.getSortie()) {
            if (partieDuJourEnCours) {
                tempsDuJour = (int) terminerLabyrintheDuJour();
                scoresDuJour = new ScoreDuJour(joueurActuel.getNom(), tempsDuJour);
                SaveScoreDuJour.sauvegarderScore(scoresDuJour);
                return;
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

    public boolean isAvecBrouillard() {
        return this.brouillard;
    }

    public int getDefiChoisi(){
        return defiChoisi;
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

    public boolean isPartieLibre() {
        return partieLibre;
    }


    public String getPartie(){
        if(partieLibre){
            partieLibre = false;
            return "SceneModeLibre";
        }
        else if(partieDuJourEnCours){
            partieDuJourEnCours = false;
            return "SceneLabyrintheDuJour";
        }
        else if (partieContreLaMontre) {
            partieContreLaMontre = false;
            return "SceneModeContreLaMontre";
        } else{
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

    public HashMap<Cellule, IItem> getWards() {
        HashMap<Cellule, IItem> mapWard = new HashMap<>();
        for (Map.Entry<Cellule, IItem> item : items.entrySet()) {
            if (item instanceof Ward){
                mapWard.put(item.getKey(), item.getValue());
            }
        }
        return mapWard;
    }


    public void itemApplyEffect(){
        if (containCleanse()){
            cleanse();
        }
        else if (containCleanseMalus()){
            getCleanseMalus().applyEffect();
        }
        else if (containCleanseNeutral()){
            getCleanseNeutral().applyEffect();
        }
        else if (containCleanse()){
            getCleanseBonus().applyEffect();
        } else {
            for (IItem item : listItem){
                item.applyEffect();
            }
        }
    }


    public IItem getCleanseBonus(){
        for (IItem item : listItem){
            if (item instanceof CleanseBonus){
                return (CleanseBonus) item;
            }
        }
        return null;
    }
    public IItem getCleanseNeutral(){
        for (IItem item : listItem){
            if (item instanceof CleanseNeutral){
                return (CleanseNeutral) item;
            }
        }
        return null;
    }
    public IItem getCleanseMalus(){
        for (IItem item : listItem){
            if (item instanceof CleanseNeutral){
                return (CleanseNeutral) item;
            }
        }
        return null;
    }

    public void cleanse(){
        listItem.clear();
    }

    public void removeItem(IItem item){
        listItem.remove(item);
    }
    public void removeItems(ArrayList<IItem> items){
        listItem.removeAll(items);
    }

    public void addItem(IItem item){listItem.add(item);}

    public ArrayList<IItem> getListItem(){
        return this.listItem;
    }

    public boolean containPlusVision(){
        for (IItem item : listItem){
            if (item.getClass().equals(PlusVision.class) ) {
                return true;
            }
        }
        return false;
    }

    public boolean containCleanse(){
        for (IItem item : listItem){
            if (item.getClass().equals(Cleanse.class) ) {
                return true;
            }
        }
        return false;
    }

    public boolean containCleanseBonus(){
        for (IItem item : listItem){
            if (item.getClass().equals(CleanseBonus.class) ) {
                return true;
            }
        }
        return false;
    }

    public boolean containCleanseMalus(){
        for (IItem item : listItem){
            if (item.getClass().equals(CleanseMalus.class) ) {
                return true;
            }
        }
        return false;
    }

    public boolean containCleanseNeutral(){
        for (IItem item : listItem){
            if (item.getClass().equals(CleanseNeutral.class) ) {
                return true;
            }
        }
        return false;
    }


    public boolean rangeZero(){
        return visionRange == 0;
    }

    public ArrayList<IItem> getVisionItems(){
        ArrayList<IItem> temp = new ArrayList<>();
        for (IItem item : listItem){
            if (item.getClass().equals(PlusVision.class) ||
                    item.getClass().equals(MoinsVision.class)) {
                temp.add(item);
            }
        }
        return temp;
    }

    public ArrayList<IItem> getMalus(){
        ArrayList<IItem> temp = new ArrayList<>();
        for (IItem item : listItem){
            if (item.isBonus() == -1){
                temp.add(item);
            }
        }
        return temp;
    }
    public ArrayList<IItem> getBonus(){
        ArrayList<IItem> temp = new ArrayList<>();
        for (IItem item : listItem){
            if (item.isBonus() == 1){
                temp.add(item);
            }
        }
        return temp;
    }
    public ArrayList<IItem> getNeutral(){
        ArrayList<IItem> temp = new ArrayList<>();
        for (IItem item : listItem){
            if (item.isBonus() == 0){
                temp.add(item);
            }
        }
        return temp;
    }

    public HashMap<Cellule, IItem> getItemLabyrinthe(){
        return this.items;
    }

    public void genererItems(int nbItems){
        HashMap<Cellule, IItem> temp = new HashMap<Cellule, IItem>();
        Cellule cell;

        for (int i = 0; i < nbItems; i++) {
            cell = labyrinthe.positionAleaCell();
            IItem item = getRandomItem();
            temp.put(cell, item);
            System.out.println(item + " : " + cell.toStringV());
        }
        System.out.println("Items génèrés");
        items = temp;
    }

    public IItem getRandomItem(){
        Random rand = new Random();

        int neutre = 1;     // ici on admet que tout les cleanse forment un seul item
        int rnd = rand.nextInt(0, NBBONUS + NBMALUS + neutre);

        // bonus
        if (rnd > (neutre + NBMALUS)) {
            return getRandomBonus();
        }
        // neutre
        if (rnd > (NBMALUS)) {
            return getRandomNeutre();

        }
        // malus
        return getRandomMalus();
    }

    public IItem getRandomBonus(){
        Random rand = new Random();

        int rnd = rand.nextInt(0, NBBONUS);

        switch (rnd) {
            case 0:
                // System.out.println("genere plus vision");
                return new PlusVision(this, rand.nextInt(1,4));     // donne un bonus de vision entre 1 et 4 cases
            case 1:
                // System.out.println("genere TP");

                return new TP(this);
            case 2:
                // System.out.println("genere Ward");

                return new Ward(this, rand.nextInt(1,4));           // place une ward de range entre 1 et 4 cases

            // pas encore implémenté
            case 3:
                System.out.println("getRandomBonus() : erreur, OOB du nextInt");
                // return new BriseMur();
            case 4:
                System.out.println("getRandomBonus() : erreur, OOB du nextInt");
                // return new SentirSortie();
        }

        System.out.println("getRandomBonus() : erreur de génération d'un item, return null");
        return null;
    }

    public IItem getRandomNeutre(){
        Random rand = new Random();

        int rnd = rand.nextInt(0, NBNEUTRE);

        switch (rnd) {
            case 0:
                // System.out.println("genere cleanse malus");

                return new CleanseMalus(this);

            case 1:
                // System.out.println("genere cleanse neutral");

                return new CleanseNeutral(this);

            case 2:
                // System.out.println("genere cleanse bonus");

                return new CleanseBonus(this);

            case 3:
                // System.out.println("genere cleanse");

                return new Cleanse(this);

        }

        System.out.println("getRandomNeutre() : erreur de génération d'un item, return null");
        return null;
    }

    public IItem getRandomMalus(){
        Random rand = new Random();

        int rnd = rand.nextInt(0, NBMALUS);

        switch (rnd) {
            case 0:
                // System.out.println("genere moins vision");

                return new MoinsVision(this, rand.nextInt(1,4));
            case 1:
                // System.out.println("genere tpdebut");

                return new TpDebut(this);
            case 2:

                System.out.println("getRandomMalus() : erreur, OOB du nextInt");
                // return new MixCtrl(this);

        }

        System.out.println("getRandomBonus() : erreur de génération d'un item, return null");
        return null;
    }

    public boolean isCellAnItem(Cellule cellule){
        return items.containsKey(cellule);
    }

    public void prendreItem(){
        if (items != null && !items.isEmpty()){
            Random rand = new Random();
            int rnd = rand.nextInt(0, items.size());
            IItem item = getItem(rnd);
            Cellule cell = getKey(item);
            printItem(cell);

            addItem(item);
            items.remove(cell);
            itemApplyEffect();
        } else {
            System.out.println("prendreItem() : essaie de prendre un item alors qu'il n'y en as plus dans le labyrinthe");
        }
    }

    public IItem getItem(int idx){
        if (idx > items.size()){
            System.out.println("getItem() : idx > size()");
            return null;
        }

        Collection<IItem> list = items.values();

        int cpt = 0;
        for (IItem item : list){
            if (cpt == idx){
                return item;
            }
            cpt ++;
        }
        System.out.println("getItem() : erreur");
        return null;

    }

    public Cellule getKey(IItem item){
        for (Map.Entry<Cellule, IItem> entry : items.entrySet()){
            if (entry.getValue().equals(item)){
                return entry.getKey();
            }
        }
        System.out.println("getKey(item) : item absent de items");
        return null;
    }

    public void nouveauListItem(){
        listItem = new ArrayList<IItem>();
    }

    public void printItem(Cellule cell){
        System.out.println(getItem(cell).toString() + " : " + cell.toStringV());
    }

    public IItem getItem(Cellule cell){
        for (Map.Entry<Cellule, IItem> entry : items.entrySet()){
            if (entry.getKey().equals(cell)){
                return entry.getValue();
            }
        }
        System.out.println("getItem(item) : item absent de items");
        return null;
    }

    public void lancerContreLaMontre(ParametresContreLaMontre params) {
        this.partieContreLaMontre = true;
        this.partieLibre = false;
        this.partieDuJourEnCours = false;
        this.brouillard = false;

        this.tempsLimite = params.getTemps();

        if (params.isEstParfait()) {
            this.labyrinthe = new LabyrintheParfait(
                    params.getLargeur()/2,
                    params.getHauteur()/2,
                    new Etape(0),
                    params.getDistanceMin()
            );
        } else {
            this.labyrinthe = new Labyrinthe(
                    params.getLargeur(),
                    params.getHauteur(),
                    (double) params.getTauxMur()
            );
        }

        this.positionJoueur = this.labyrinthe.getEntree();
        this.biome = Biome.DESERT;
        notifierObservateurs();
    }

    public boolean isPartieContreLaMontre() {
        return partieContreLaMontre;
    }
    public int getTempsLimite() { return tempsLimite; }

    public void utiliserItem(IItem bonus){
        bonus.applyEffect();
        joueurActuel.retirerItem(bonus);
    }

    public void nouveauMapItem(){
        items = new HashMap<Cellule, IItem>();
    }

    public void setPartieContreLaMontre(boolean partieContreLaMontre) {
        this.partieContreLaMontre = partieContreLaMontre;
    }
}
