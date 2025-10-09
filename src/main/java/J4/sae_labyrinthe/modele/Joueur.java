package J4.sae_labyrinthe.modele;

public class Joueur implements IJoueur{
    private String nom;
    private int etapeMax;

    private int etapeActuelle;
    private int score;

    public Joueur(String nom){
        this.nom = nom;
        this.etapeMax = 1;
        this.etapeActuelle = 1;
        this.score = 0;
    }

    public String getNom() {
        return this.nom;
    }

    public int getEtapeActuelle() {
        return this.etapeActuelle;
    }

    public int getEtapeMax() {
        return this.etapeMax;
    }

    public int getScore() {
        return this.score;
    }

    public void ajouterScore(int ajout) {
        this.score += ajout;
    }

    public void avancerEtape() {
        ++this.etapeActuelle;
        if (this.etapeActuelle > this.etapeMax) {
            this.etapeMax = this.etapeActuelle;
        }
    }

    public void reculerEtape() {
        if (this.etapeActuelle > 1) {
            --this.etapeActuelle;
        }
    }
}
