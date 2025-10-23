package J4.sae_labyrinthe.modele;

public class Defi {

    private String difficulte;

    private boolean reussi;

    private int cote;

    private double pourcentageMurs;


    public Defi(String difficulte, int cote, double pourcentageMurs) {
        this.difficulte = difficulte;
        this.reussi = false;
        this.cote = cote;
        this.pourcentageMurs = pourcentageMurs;
    }


    public boolean estReussi() {
        return this.reussi;
    }


    public String getDifficulte() {
        return this.difficulte;
    }

    public void setReussi(boolean reussi) {
        this.reussi = reussi;
    }

    public int getCote() {
        return cote;
    }

    public double getPourcentageMurs() {
        return pourcentageMurs;
    }
}
