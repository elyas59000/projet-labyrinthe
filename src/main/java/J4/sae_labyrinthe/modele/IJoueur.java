package J4.sae_labyrinthe.modele;

public interface IJoueur {
    String getNom();
    int getEtapeMax();
    int getScore();
    int getEtapeActuelle();
    void ajouterScore(int ajout);
    void avancerEtape();
    void reculerEtape();

}
