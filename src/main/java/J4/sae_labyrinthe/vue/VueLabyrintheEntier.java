package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;

public class VueLabyrintheEntier extends VueLabyrintheAbstraite {

    private final boolean aDecouvrir;
    private boolean[][] decouvert;
    private final int RAYON_DECOUVERTE=1;
    private GraphicsContext fogGC;

    public VueLabyrintheEntier(ModeleJeu modele, boolean aDecouvrir) {
        super(modele);
        this.aDecouvrir = aDecouvrir;
        int L = modele.getLabyrinthe().getLargeur();
        int H = modele.getLabyrinthe().getHauteur();
        calculTailleCasesTexturees(L, H);
        if (aDecouvrir) initFog();
        dessineeLabyrinthe();
        updateVueEnfant();
    }

    private void initFog() {
        int L = modele.getLabyrinthe().getLargeur();
        int H = modele.getLabyrinthe().getHauteur();
        decouvert = new boolean[H][L];
        Canvas fogCanvas = new Canvas(LARGEUR_ZONE, HAUTEUR_ZONE);
        fogGC = fogCanvas.getGraphicsContext2D();
        this.getChildren().add(fogCanvas);
    }

    @Override
    protected void dessineeLabyrinthe() {
        gc.clearRect(0, 0, LARGEUR_ZONE, HAUTEUR_ZONE);
        Cellule[][] g = modele.getLabyrinthe().getGrille();
        for (Cellule[] ligne : g)
            for (Cellule c : ligne) {
                if (c.estMur()){
                    System.out.println("Position par acces à cellule, mur = ("+c.getX()+","+c.getY()+")");
                    dessinerMur(c);
                }
                else if (c.equals(modele.getLabyrinthe().getSortie())) dessinerSortie(c);
                else dessinerSol(c);
            }
        dessinerJoueur();

        for(int i=0; i<g.length; i++){
            for(int j=0; j<g[i].length; j++){
                if(g[i][j].estMur()){
                    System.out.println("Position par indice, mur = ("+i+","+j+")");
                }
            }
        }

    }

    private void updateFog() {
        fogGC.clearRect(0, 0, LARGEUR_ZONE, HAUTEUR_ZONE);
        Cellule pos = modele.getPositionJoueur();
        int jx = pos.getX();
        int jy = pos.getY();
        int rayon = this.RAYON_DECOUVERTE;
        for (int y = jy - rayon; y <= jy + rayon; y++)
            for (int x = jx - rayon; x <= jx + rayon; x++)
                if (modele.getLabyrinthe().posValide(x, y))
                    decouvert[y][x] = true;
        fogGC.setFill(Color.rgb(16,16,16,1));
        Cellule[][] g = modele.getLabyrinthe().getGrille();
        for (int y = 0; y < g.length; y++)
            for (int x = 0; x < g[y].length; x++)
                if (!decouvert[y][x])
                    fogGC.fillRect(offsetX + x*tileSize, offsetY + y*tileSize, tileSize, tileSize);
    }

    @Override
    protected void updateVueEnfant() {
        dessinerJoueur();
        if (aDecouvrir) updateFog();
        /*
        Cellule modif = modele.getDerniereCelluleModifiee();
        if (modif != null) {
            if (modif.estMur()) dessinerMur(modif);
            else dessinerSol(modif);
        }
        */

    }
}
