package J4.sae_labyrinthe.utils;

import java.io.Serializable;

public class ScoreDuJour implements Serializable {
    private String nomJoueur;
    private long tempsSecondes;
    private static final long serialVersionUID = 1L;

    public ScoreDuJour(String nomJoueur, long tempsSecondes) {
        this.nomJoueur = nomJoueur;
        this.tempsSecondes = tempsSecondes;
    }

    public String getNom() { return nomJoueur; }
    public long getTemps() { return tempsSecondes; }

    @Override
    public String toString() {
        long minutes = tempsSecondes / 60;
        long secondes = tempsSecondes % 60;
        return nomJoueur + " - " + minutes + "m " + secondes + "s";
    }
}
