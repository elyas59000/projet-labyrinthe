package J4.sae_labyrinthe.modele;

public interface ILabyrinthe {

    Cellule[][] getGrille();
    int getLargeur();
    int getHauteur();
    Cellule getEntree();
    Cellule getSortie();

    Etape getEtape();
    void sauvegarder(String fichier);
    Cellule positionAleaCell();

    boolean posValide(int x, int y);
}
