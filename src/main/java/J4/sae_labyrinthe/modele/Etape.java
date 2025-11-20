package J4.sae_labyrinthe.modele;


public class Etape{
    private final int NUM;
    private Defi[] defis;

    public Etape(int num){
        this.NUM = num;
        defis = new Defi[3];
        if(NUM<3){
            genererDefis((NUM+4)*(2+NUM));
        }
        else if(NUM==3){
            genererDefis((NUM+3)*(1+NUM));
        }else if(NUM==4){
            genererDefisDistance(10,7);
        }
    }

    private void genererDefis(int cote) {
        defis[0] = new Defi("Facile", cote, 20.0);
        defis[1] = new Defi("Moyen", cote, 30.0);
        defis[2] = new Defi("Difficile", cote, 50.0);
    }

    private void genererDefisDistance(int cote, int cote2) {
        defis[0] = new Defi("Facile", cote,cote2, 5);
        defis[1] = new Defi("Moyen", cote, cote2,12);
        defis[2] = new Defi("Difficile", cote,cote2, 20);
    }

    public int getNumero() { return NUM; }

    public Defi getDefiParDifficulte(int n) {
        return defis[n-1];
    }
}
