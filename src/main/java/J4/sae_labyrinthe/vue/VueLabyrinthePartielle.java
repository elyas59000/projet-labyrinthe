package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.*;
import J4.sae_labyrinthe.utils.CacheMemoire; // N'oublie pas cet import
import javafx.scene.paint.Color;

public class VueLabyrinthePartielle extends VueLabyrintheAbstraite {

    private int range;
    private int side;

    // On ajoute ces variables pour se souvenir d'où on vient
    private int lastX = -1;
    private int lastY = -1;

    public VueLabyrinthePartielle(ModeleJeu modele, int range) {
        super(modele);
        this.range = range;
        this.side = 2 * range + 1;
        this.tileSize = Math.min(LARGEUR_ZONE / side, HAUTEUR_ZONE / side);

        // On initialise la position de départ
        Cellule pos = modele.getPositionJoueur();
        this.lastX = pos.getX();
        this.lastY = pos.getY();

        dessineeLabyrinthe();
    }

    @Override
    protected void dessineeLabyrinthe() {
        gc.clearRect(0, 0, LARGEUR_ZONE, HAUTEUR_ZONE);
        ILabyrinthe lab = modele.getLabyrinthe();
        Cellule pos = modele.getPositionJoueur();
        int joueurX = pos.getX();
        int joueurY = pos.getY();

        // --- GESTION DES IMAGES (DIRECTION) ---
        // On compare la position actuelle avec l'ancienne position mémorisée
        if (joueurY < lastY) {
            joueur.setImage(CacheMemoire.getImage("souris2.png")); // Dos (Haut)
        }
        else if (joueurY > lastY) {
            joueur.setImage(CacheMemoire.getImage("souris3.png")); // Face (Bas)
        }
        else if (joueurX < lastX) {
            joueur.setImage(CacheMemoire.getImage("souris1.png")); // Gauche
        }
        else if (joueurX > lastX) {
            joueur.setImage(CacheMemoire.getImage("souris0.png")); // Droite
        }

        // On met à jour la mémoire pour le prochain tour
        lastX = joueurX;
        lastY = joueurY;
        // --------------------------------------

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

        // Placement du joueur au centre de l'écran
        int joueurScreenX = frameX + range * tileSize;
        int joueurScreenY = frameY + range * tileSize;

        joueur.setLayoutX(joueurScreenX);
        joueur.setLayoutY(joueurScreenY);
        joueur.setFitWidth(tileSize);
        joueur.setFitHeight(tileSize);

        // On s'assure que le joueur est visible au premier plan
        joueur.toFront();
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