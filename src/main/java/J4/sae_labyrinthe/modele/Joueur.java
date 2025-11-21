package J4.sae_labyrinthe.modele;

import J4.sae_labyrinthe.modele.Item.IItem;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe représentant un joueur dans le jeu du labyrinthe.
 * Gère les informations du joueur comme son nom, son score, son niveau actuel
 * et sa progression dans le jeu.
 * 
 * @author Nathan Philippe
 * @author Clément Roty
 * @author Anas ACHOUCH
 */
public class Joueur implements IJoueur, Serializable {
    private static final long serialVersionUID = -6956888135510794480L;
    private String nom;
    private String mdp;
    private int etapeMax;
    private int etapeActuelle;
    private int etapeFini;
    private int score;
    private double tempsMoyenDansLabyrinthe;
    private Map<IItem, Integer> inventaires;


    /**
     * @param nom Nom du joueur
     * @param mdp Mot de passe du joueur
     * @author Nathan Philippe
     * @author Clément Roty
     */
    public Joueur(String nom, String mdp) {
        this.nom = nom;
        this.mdp = mdp;
        this.etapeMax = 1;
        this.etapeActuelle = 1;
        this.etapeFini = 0;
        this.score = 0;
        this.tempsMoyenDansLabyrinthe = 0.0;
        this.inventaires = new HashMap<>();
    }

    /**
     * @return Le nom du joueur
     * @author Nathan Philippe
     */
    @Override
    public String getNom() {
        return this.nom;
    }

    /**
     * @return L'étape actuelle du joueur
     * @author Nathan Philippe
     */
    @Override
    public int getEtapeActuelle() {
        return this.etapeActuelle;
    }

    /**
     * @return L'étape maximale débloquée par le joueur
     * @author Nathan Philippe
     */
    @Override
    public int getEtapeMax() {
        return this.etapeMax;
    }

    /**
     * @return Le score total du joueur (nombre d'étoiles)
     * @author Nathan Philippe
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * @return Le mot de passe du joueur
     * @author Clément Roty
     */
    public String getMdp() {
        return this.mdp;
    }

    /**
     * Définit le temps moyen passé dans les labyrinthes.
     * 
     * @param tempsMoyenDansLabyrinthe Le nouveau temps moyen en secondes
     * @author Clément Roty
     */
    public void setTempsMoyenDansLabyrinthe(double tempsMoyenDansLabyrinthe) {
        this.tempsMoyenDansLabyrinthe = tempsMoyenDansLabyrinthe;
    }

    /**
     * @return Le temps moyen passé dans les labyrinthes en secondes
     * @author Clément Roty
     */
    public double getTempsMoyenDansLabyrinthe() {
        return this.tempsMoyenDansLabyrinthe;
    }

    /**
     * Ajoute des points au score du joueur.
     * 
     * @param ajout Nombre de points à ajouter
     * @author Nathan Philippe
     */
    @Override
    public void ajouterScore(int ajout) {
        this.score += ajout;
    }

    /**
     * Fait progresser le joueur à l'étape suivante.
     * Met à jour l'étape maximale si nécessaire.
     * 
     * @author Nathan Philippe
     */
    @Override
    public void avancerEtape() {
        ++this.etapeActuelle;
        ++this.etapeFini;
        if (this.etapeActuelle > this.etapeMax) {
            this.etapeMax = this.etapeActuelle;
        }
    }

    /**
     * Fait reculer le joueur à l'étape précédente.
     * Ne peut pas descendre en dessous de l'étape 1.
     * 
     * @author Nathan Philippe
     */
    @Override
    public void reculerEtape() {
        if (this.etapeActuelle > 1) {
            --this.etapeActuelle;
        }
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public int getEtapeFini() {
        return etapeFini;
    }

    public void ajouterItem(IItem bonus){
        if(this.inventaires.putIfAbsent(bonus, 1) != null)  this.inventaires.put(bonus,this.inventaires.get(bonus) + 1);
    }

    public void retirerItem(IItem bonus){
        if(this.inventaires.get(bonus) != null && this.inventaires.get(bonus) != 0){
            this.inventaires.put(bonus,this.inventaires.get(bonus)-1);
        }
    }

    public String getQuantiteItem(String nomItem) {
        if (nomItem == null && this.inventaires.isEmpty()) return "0";

        for (Map.Entry<IItem, Integer> entry : inventaires.entrySet()) {
            IItem item = entry.getKey();

            if (item.getNom().equalsIgnoreCase(nomItem)) {
                return entry.getValue() + "";
            }
        }

        return "0"; // si on ne trouve pas l'item
    }

}
