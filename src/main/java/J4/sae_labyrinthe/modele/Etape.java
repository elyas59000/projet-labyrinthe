package J4.sae_labyrinthe.modele;


public class Etape{
    private final int NUM;
    private Defi[] defis;

    public Etape(int num){
        this.NUM = num;
        defis = new Defi[3];
        if(NUM!=3){
            genererDefis((NUM+4)*(2+NUM));
        }
        else{
            genererDefis((NUM+3)*(1+NUM));
        }
    }

    private void genererDefis(int cote) {
        defis[0] = new Defi("Facile", cote, 20);
        defis[1] = new Defi("Moyen", cote, 30);
        defis[2] = new Defi("Difficile", cote, 50);
    }

    public boolean aReussiUnDefi() {
        for (Defi d : defis) {
            if (d.estReussi()) return true;
        }
        return false;
    }

    public int getNumero() { return NUM; }
    public Defi[] getDefis() { return defis; }

    public Defi getDefiParDifficulte(int n) {
        return defis[n-1];
    }
}
