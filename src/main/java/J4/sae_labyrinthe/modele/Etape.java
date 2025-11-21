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
            int cote1 = 8 + (int)(Math.random() * 7);
            int cote2 = 6 + (int)(Math.random() * 4);
            genererDefisDistance(cote1,cote2);
        }
        else if(NUM==5){
            int cote1 = 15 + (int)(Math.random() * 20);
            int cote2 = 15 + (int)(Math.random() * 20);
            genererDefisDistance(cote1,cote2);
        }
        else if(NUM==6){
            int cote1 = 20 + (int)(Math.random() * 25);
            int cote2 = 20 + (int)(Math.random() * 25);
            genererDefisDistance(cote1,cote2);
        }
    }

    private void genererDefis(int cote) {
        defis[0] = new Defi("Facile", cote, 20.0);
        defis[1] = new Defi("Moyen", cote, 30.0);
        defis[2] = new Defi("Difficile", cote, 50.0);
    }

    private void genererDefisDistance(int cote, int cote2) {
        defis[0] = new Defi("Facile", cote,cote2, Math.max(cote, cote2)/3);
        defis[1] = new Defi("Moyen", cote, cote2,Math.max(cote, cote2)/2);
        defis[2] = new Defi("Difficile", cote,cote2, Math.max(cote, cote2));
    }

    public int getNumero() { return NUM; }

    public Defi getDefiParDifficulte(int n) {
        return defis[n-1];
    }
}
