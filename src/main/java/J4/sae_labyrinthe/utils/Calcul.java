package J4.sae_labyrinthe.utils;

public class Calcul {
    public static double calculTailleCarreOptimal(double largeurZone, double hauteurZone, int nbColonnes, int nbLignes) {
        double tailleSelonLargeur = largeurZone / nbColonnes;
        double tailleSelonHauteur = hauteurZone / nbLignes;
        return Math.floor(Math.min(tailleSelonLargeur, tailleSelonHauteur));
    }
}
