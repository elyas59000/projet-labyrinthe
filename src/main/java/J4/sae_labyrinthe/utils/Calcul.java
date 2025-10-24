package J4.sae_labyrinthe.utils;

/**
 * Classe utilitaire fournissant des méthodes de calcul pour l'affichage
 * du labyrinthe (tailles optimales, conversions, etc.).
 *
 * @author Nathan Philippe
 */
public class Calcul {
    /**
     * Calcule la taille maximale d'un carré (cellule) afin que l'ensemble du
     * labyrinthe tienne dans la zone disponible.
     *
     * Le calcul prend la largeur disponible divisée par le nombre de colonnes
     * et la hauteur disponible divisée par le nombre de lignes, puis retourne
     * la plus petite valeur arrondie à l'entier inférieur.
     *
     * @param largeurZone largeur disponible en pixels
     * @param hauteurZone hauteur disponible en pixels
     * @param nbColonnes nombre de colonnes du labyrinthe
     * @param nbLignes nombre de lignes du labyrinthe
     * @return taille en pixels d'un côté du carré (arrondie vers le bas)
     */
    public static double calculTailleCarreOptimal(double largeurZone, double hauteurZone, int nbColonnes, int nbLignes) {
        double tailleSelonLargeur = largeurZone / nbColonnes;
        double tailleSelonHauteur = hauteurZone / nbLignes;
        return Math.floor(Math.min(tailleSelonLargeur, tailleSelonHauteur));
    }
}
