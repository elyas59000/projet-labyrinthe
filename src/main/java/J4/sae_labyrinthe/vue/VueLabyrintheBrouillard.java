package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.ILabyrinthe;
import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.ModeleJeu;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.shape.Rectangle;

public class VueLabyrintheBrouillard extends VueLabyrintheEntier {

    private boolean[][] visible;
    // Rayon de vision du joueur (modifiable)
    private final int RAYON_VISION = 3;

    public VueLabyrintheBrouillard(ModeleJeu modele) {
        super(modele);
    }

    @Override
    public void initialiserAffichage() {
        ILabyrinthe laby = modele.getLabyrinthe();
        visible = new boolean[laby.getHauteur()][laby.getLargeur()];
        super.initialiserAffichage();
    }

    @Override
    protected void rafraichirAffichage() {
        Labyrinthe laby = (Labyrinthe) modele.getLabyrinthe();
        int joueurX = modele.getPositionJoueur().getX();
        int joueurY = modele.getPositionJoueur().getY();

        majVisibilite(laby, joueurX, joueurY);

        int largeur = laby.getLargeur();
        for (int i = 0; i < laby.getHauteur(); i++) {
            for (int j = 0; j < largeur; j++) {
                Rectangle cell = (Rectangle) grille.getChildren().get(i * largeur + j);

                if (visible[i][j]) {
                    appliquerTexture(cell, laby, i, j, joueurX, joueurY);
                    cell.setEffect(null);
                } else {
                    appliquerTexture(cell, laby, i, j, joueurX, joueurY);

                    GaussianBlur flou = new GaussianBlur(15);
                    ColorAdjust assombri = new ColorAdjust();
                    assombri.setBrightness(-5);

                    // Combine les effets
                    assombri.setInput(flou);
                    cell.setEffect(assombri);
                }
            }
        }
    }

    /**
     * Met à jour la visibilité autour du joueur en fonction du rayon et des murs
     */
    private void majVisibilite(Labyrinthe laby, int x, int y) {
        visible[y][x] = true;

        for (int dy = -RAYON_VISION; dy <= RAYON_VISION; dy++) {
            for (int dx = -RAYON_VISION; dx <= RAYON_VISION; dx++) {
                int nx = x + dx;
                int ny = y + dy;

                if (!laby.estDansLabyrinthe(ny, nx)) continue;

                if (ligneDeVue(laby, x, y, nx, ny)) {
                    visible[ny][nx] = true;
                }
            }
        }
    }

    /**
     * Détermine si la case (nx, ny) est visible depuis (x, y)
     * (ici version simple : bloque la vision dès qu’un mur est rencontré)
     */
    private boolean ligneDeVue(Labyrinthe laby, int x1, int y1, int x2, int y2) {
        int dx = Integer.signum(x2 - x1);
        int dy = Integer.signum(y2 - y1);
        int cx = x1;
        int cy = y1;

        while (cx != x2 || cy != y2) {
            cx += dx;
            cy += dy;

            if (!laby.estDansLabyrinthe(cy, cx)) return false;

            if (dx != 0 && dy != 0) {
                if (laby.getGrille()[cy - dy][cx].estMur() && laby.getGrille()[cy][cx - dx].estMur()) {
                    return false;
                }
            }

            if (cx == x2 && cy == y2) {
                return true;
            }

            if (laby.getGrille()[cy][cx].estMur()) {
                return false;
            }
        }

        return true;
    }

}
