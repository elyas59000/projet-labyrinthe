package J4.sae_labyrinthe.utils;

import java.util.Random;

public class ParametresContreLaMontre {

    private static ParametresContreLaMontre instance;

    private int largeur;
    private int hauteur;
    private int tauxMur;
    private int distanceMin;
    private int temps;
    private boolean estParfait;

    // Constructeur privé
    public ParametresContreLaMontre() {}

    public static ParametresContreLaMontre getInstance() {
        if (instance == null) {
            instance = new ParametresContreLaMontre();
        }
        return instance;
    }

    public void definirParametres(int l, int h, int t, int dist, int temps, boolean parfait) {
        this.largeur = l;
        this.hauteur = h;
        this.tauxMur = t;
        this.distanceMin = dist;
        this.temps = temps;
        this.estParfait = parfait;
    }

    public void genererAleatoire() {
        Random rand = new Random();
        this.estParfait = rand.nextBoolean();
        this.largeur = 10 + rand.nextInt(40);
        this.hauteur = 10 + rand.nextInt(40);
        this.temps = 30 + rand.nextInt(120);

        if (this.estParfait) {
            this.distanceMin = 5 + rand.nextInt(Math.min(this.largeur, this.hauteur));
        } else {
            this.tauxMur = 10 + rand.nextInt(45);
        }
    }

    public boolean isEstParfait() { return estParfait; }
    public int getLargeur() { return largeur; }

    public int getDistanceMin() {
        return distanceMin;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getTauxMur() {
        return tauxMur;
    }

    public int getTemps() {
        return temps;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }
}