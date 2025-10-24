package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.ModeleJeu;
import J4.sae_labyrinthe.utils.FusionImage;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public abstract class VueLabyrintheAbstraite implements IObserver {

    protected final ModeleJeu modele;
    protected final GridPane grille;

    protected final Image brique;
    protected final Image fond;
    protected final Image joueur;
    protected final Image fin;

    protected final double LARGEUR_DISPONIBLE_ZONE = 1280;
    protected final double HAUTEUR_DISPONIBLE_ZONE = 700;

    protected VueLabyrintheAbstraite(ModeleJeu modele) {
        this.modele = modele;
        this.grille = new GridPane();
        this.brique = getBrique();
        this.fond = getFond();
        this.joueur = getJoueur();
        this.fin = getFin();
    }

    /**
     * Construction initiale de la grille (appelée 1 seule fois par le contrôleur)
     */
    public abstract void initialiserAffichage();

    /**
     * Met à jour uniquement les textures existantes (appelée à chaque update du modèle)
     */
    protected abstract void rafraichirAffichage();

    /**
     * Appelée automatiquement lorsque le modèle notifie un changement.
     * Ne redessine PAS la grille complète.
     */
    @Override
    public void update() {
        rafraichirAffichage();
    }

    protected void appliquerTexture(Rectangle cell, Labyrinthe laby, int i, int j, int joueurX, int joueurY) {
        if (i < 0 || i >= laby.getHauteur() || j < 0 || j >= laby.getLargeur()) {
            cell.setFill(new ImagePattern(brique));
            return;
        }
        if (laby.getGrille()[i][j].estMur()) {
            cell.setFill(new ImagePattern(brique));
        } else if (i == joueurY && j == joueurX) {
            cell.setFill(new ImagePattern(FusionImage.fusionnerImages(fond, joueur)));
        } else if (laby.getGrille()[i][j].equals(laby.getSortie())) {
            cell.setFill(new ImagePattern(FusionImage.fusionnerImages(fond, fin)));
        } else {
            cell.setFill(new ImagePattern(fond));
        }
    }

    protected void parcoursCreationGridPane(GridPane grid, int largeur, int hauteur,double tailleCarre) {
        grid.getChildren().clear();
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                Rectangle cell = new Rectangle(tailleCarre, tailleCarre);
                grille.add(cell, j, i);
            }
        }
    }

    public GridPane getGrille() {
        return grille;
    }

    public Image getBrique() {
        return new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/Brique.png").toExternalForm());
    }

    public Image getJoueur() {
        return new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/souris.png").toExternalForm());
    }

    public Image getFond()   {
        return new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/herbe.png").toExternalForm());
    }

    public Image getFin()    {
        return new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/fromage.png").toExternalForm());
    }
}
