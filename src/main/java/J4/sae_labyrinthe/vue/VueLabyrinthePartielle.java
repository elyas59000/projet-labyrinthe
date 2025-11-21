package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.*;
import J4.sae_labyrinthe.utils.ImageOutils;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import java.util.List;

public class VueLabyrinthePartielle extends VueLabyrintheAbstraite {

    private int range;
    private int side;

    public VueLabyrinthePartielle(ModeleJeu modele, int range) {
        super(modele);
        this.range = range;
        this.side = 2 * range + 1;
        this.tileSize = Math.min(LARGEUR_ZONE / side, HAUTEUR_ZONE / side);
        dessineeLabyrinthe();
    }

    @Override
    protected void dessineeLabyrinthe() {
        gc.clearRect(0, 0, LARGEUR_ZONE, HAUTEUR_ZONE);
        ILabyrinthe lab = modele.getLabyrinthe();
        Cellule pos = modele.getPositionJoueur();
        int joueurX = pos.getX();
        int joueurY = pos.getY();
        int largeur = lab.getLargeur();
        int hauteur = lab.getHauteur();
        int canvasTiles = 2 * range + 1;
        int gridWidth = canvasTiles * tileSize;
        int gridHeight = canvasTiles * tileSize;
        int frameX = (LARGEUR_ZONE - gridWidth) / 2;
        int frameY = (HAUTEUR_ZONE - gridHeight) / 2;
        for (int dy = 0; dy < canvasTiles; dy++) {
            for (int dx = 0; dx < canvasTiles; dx++) {
                int mapX = joueurX + (dx - range);
                int mapY = joueurY + (dy - range);
                int px = frameX + dx * tileSize;
                int py = frameY + dy * tileSize;
                if (mapX < 0 || mapX >= largeur || mapY < 0 || mapY >= hauteur) {
                    gc.setFill(Color.valueOf(modele.getBiome().getBackground()));
                    gc.fillRect(px, py, tileSize, tileSize);
                } else {
                    Cellule c = lab.getGrille()[mapY][mapX];
                    if (c.estMur()) {
                        dessinerSol(c, px, py);
                        dessinerMur(c, px, py);
                    }
                    else if (c.equals(lab.getSortie())) dessinerSortie(c, px, py);
                    else dessinerSol(c, px, py);
                }
            }
        }
        int joueurScreenX = frameX + range * tileSize;
        int joueurScreenY = frameY + range * tileSize;
        joueur.setLayoutX(joueurScreenX);
        joueur.setLayoutY(joueurScreenY);
        joueur.setFitWidth(tileSize);
        joueur.setFitHeight(tileSize);
    }

    @Override
    protected void updateVueEnfant() {
        dessineeLabyrinthe();
    }

    public void setRange(int range) {
        this.range = range;
        this.side = 2 * range + 1;
        this.tileSize = Math.min(LARGEUR_ZONE / side, HAUTEUR_ZONE / side);
        dessineeLabyrinthe();
    }
}