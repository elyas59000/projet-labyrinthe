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
        else if(NUM==3){
            genererDefis((NUM+3)*(1+NUM));
        }else if(NUM==4){
            genererDefisDistance(10);
        }
    }

    private void genererDefis(int cote) {
        defis[0] = new Defi("Facile", cote, 20.0);
        defis[1] = new Defi("Moyen", cote, 30.0);
        defis[2] = new Defi("Difficile", cote, 50.0);
    }

    private void genererDefisDistance(int cote) {
        defis[0] = new Defi("Facile", cote, 1);
        defis[1] = new Defi("Moyen", cote, 2);
        defis[2] = new Defi("Difficile", cote, 4);
    }

    public int getNumero() { return NUM; }

    public Defi getDefiParDifficulte(int n) {
        return defis[n-1];
    }
}
