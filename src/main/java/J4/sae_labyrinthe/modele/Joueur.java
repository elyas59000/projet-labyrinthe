package J4.sae_labyrinthe.modele;

import java.io.Serializable;

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
    private String nom;
    private String mdp;
    private int etapeMax;
    private int etapeActuelle;
    private int score;
    private double tempsMoyenDansLabyrinthe;


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
        this.score = 0;
        this.tempsMoyenDansLabyrinthe = 0.0;
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
}
