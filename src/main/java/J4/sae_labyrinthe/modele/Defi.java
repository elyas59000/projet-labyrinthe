package J4.sae_labyrinthe.modele;

public class Defi {

    private String difficulte;

    private boolean reussi;

    private int cote;

    private double pourcentageMurs;
    private int distance;



    public Defi(String difficulte, int cote, double pourcentageMurs) {
        this(difficulte, cote, pourcentageMurs, 0, false);
    }
    public Defi(String difficulte, int cote, int distance) {
        this(difficulte, cote, 0, distance, false);
    }
    // Constructeur complet avec tous les champs
    public Defi(String difficulte, int cote, double pourcentageMurs, int distance, boolean reussi) {
        this.difficulte = difficulte;
        this.cote = cote;
        this.pourcentageMurs = pourcentageMurs;
        this.distance = distance;
        this.reussi = reussi;
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

    public int getDistance() {
        return distance;
    }
}
