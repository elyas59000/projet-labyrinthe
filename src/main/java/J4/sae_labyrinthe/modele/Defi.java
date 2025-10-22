package J4.sae_labyrinthe.modele;

public class Defi implements IDefi{

    private String difficulte;
    private Labyrinthe labyrinthe;
    private boolean reussi;

    public Defi(String difficulte, int largeur, int hauteur, double pourcentageMurs) {
        this.difficulte = difficulte;
        this.reussi = false;
        labyrinthe = new Labyrinthe(largeur, hauteur, pourcentageMurs);

    }

    @Override
    public boolean estReussi() {
        return this.reussi;
    }

    @Override
    public String getDifficulte() {
        return this.difficulte;
    }

    @Override
    public Labyrinthe getLabyrinthe() {
        return this.labyrinthe;
    }

    public void setReussi(boolean reussi) {
        this.reussi = reussi;
    }
}
