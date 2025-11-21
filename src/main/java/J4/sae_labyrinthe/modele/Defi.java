package J4.sae_labyrinthe.modele;

public class Defi {

    private String difficulte;

    private boolean reussi;

    private int cote;
    private int cote2;

    private double pourcentageMurs;
    private int distance;



    public Defi(String difficulte, int cote, double pourcentageMurs) {
        this(difficulte, cote, pourcentageMurs, 0, false);
    }
    public Defi(String difficulte, int cote, int distance) {
        this(difficulte, cote, cote, distance, false);
    }
    // Constructeur complet avec tous les champs
    public Defi(String difficulte, int cote, double pourcentageMurs, int distance, boolean reussi) {
        this(difficulte,cote,cote,pourcentageMurs,distance,reussi);
    }

    public Defi(String difficulte, int cote, int cote2, int distance) {
        this(difficulte,cote,cote2,0,distance,false);
    }

    public Defi(String difficulte, int cote, int cote2 ,double pourcentageMurs, int distance, boolean reussi) {
        this.difficulte = difficulte;
        this.cote = cote;
        this.cote2 = cote2;
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

    public int getCote2() {
        return cote2;
    }

    public double getPourcentageMurs() {
        return pourcentageMurs;
    }

    public int getDistance() {
        return distance;
    }
}
