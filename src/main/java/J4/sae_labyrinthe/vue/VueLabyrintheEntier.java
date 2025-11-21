package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;

public class VueLabyrintheEntier extends VueLabyrintheAbstraite {

    private boolean aDecouvrir;
    private Fog fog;
    private GraphicsContext fogGC;
    private int RAYON_DECOUVERTE = 1;
    private boolean decouvertePersistante;

    public VueLabyrintheEntier(ModeleJeu modele, boolean aDecouvrir,boolean decouvertePersistante) {
        super(modele);
        this.aDecouvrir = aDecouvrir;
        this.decouvertePersistante = decouvertePersistante;
        int L = modele.getLabyrinthe().getLargeur();
        int H = modele.getLabyrinthe().getHauteur();
        calculTailleCasesTexturees(L, H);
        if (aDecouvrir) initFog(L,H);
        dessineeLabyrinthe();
        if (aDecouvrir) {
            updateFog();
        }
        updateVueEnfant();
    }


    @Override
    protected void dessineeLabyrinthe() {
        gc.clearRect(0, 0, LARGEUR_ZONE, HAUTEUR_ZONE);
        Cellule[][] g = modele.getLabyrinthe().getGrille();
        for (Cellule[] ligne : g)
            for (Cellule c : ligne) {
                int px = offsetX + c.getX() * tileSize;
                int py = offsetY + c.getY() * tileSize;
                if (c.estMur()) dessinerMur(c,px,py);
                else if (c.equals(modele.getLabyrinthe().getSortie())) dessinerSortie(c,px,py);
                else dessinerSol(c,px,py);
            }
        dessinerJoueur();


    }
    private void initFog(int L, int H) {
        fog = new Fog(L, H, Color.rgb(16, 16, 16, 1), decouvertePersistante);
        Canvas fogCanvas = new Canvas(LARGEUR_ZONE, HAUTEUR_ZONE);
        fogGC = fogCanvas.getGraphicsContext2D();
        fogCanvas.setMouseTransparent(true);

        fogGC.setFill(Color.rgb(16, 16, 16, 1));
        fogGC.fillRect(0, 0, LARGEUR_ZONE, HAUTEUR_ZONE);
        this.getChildren().add(fogCanvas);
    }

    private void updateFog() {
        if (fogGC == null) return;
        fogGC.clearRect(0, 0, LARGEUR_ZONE, HAUTEUR_ZONE);
        Cellule pos = modele.getPositionJoueur();
        if (pos != null) {
            fog.updateAround(pos.getX(), pos.getY(), RAYON_DECOUVERTE);
            fog.drawFog(fogGC, offsetX, offsetY, tileSize);
        }
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

    public void setDecouvertePersistante(boolean decouvertePersistante) {
        this.decouvertePersistante = decouvertePersistante;
    }
    public void setaDecouvrir(boolean aDecouvrir) {
        this.aDecouvrir = aDecouvrir;

        if (aDecouvrir && fogGC == null) {
            int L = modele.getLabyrinthe().getLargeur();
            int H = modele.getLabyrinthe().getHauteur();
            initFog(L, H);
            updateFog();
        }
    }

    public void setRAYON_DECOUVERTE(int RAYON_DECOUVERTE) {
        this.RAYON_DECOUVERTE = RAYON_DECOUVERTE;
        if(aDecouvrir) updateFog();
    }
}
