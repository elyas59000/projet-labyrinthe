package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.*;
import J4.sae_labyrinthe.utils.ImageOutils;
import J4.sae_labyrinthe.utils.CacheMemoire;
import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import java.util.*;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public abstract class VueLabyrintheAbstraite extends Pane implements IObserver {

    protected final ModeleJeu modele;
    protected Canvas canvas;
    protected GraphicsContext gc;
    protected Image sortie;
    protected ImageView joueur;
    protected final int LARGEUR_ZONE = 1500;
    protected final int HAUTEUR_ZONE = 750;
    protected final int TAILLE_TEXTURE = 128;
    protected int tileSize;
    protected int offsetX;
    protected int offsetY;
    protected TranslateTransition animationJoueur;


    protected VueLabyrintheAbstraite(ModeleJeu modele) {
        this.modele = modele;
        this.modele.add(this);
        this.sortie = modele.getBiome().getSortie();
        this.joueur = new ImageView(CacheMemoire.getImage("souris0.png"));
        this.joueur.setPreserveRatio(true);
        this.canvas = new Canvas(LARGEUR_ZONE, HAUTEUR_ZONE);
        this.gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        this.getChildren().add(joueur);
        this.animationJoueur = new TranslateTransition(Duration.millis(200), joueur);
        this.animationJoueur.setInterpolator(Interpolator.LINEAR); // Mouvement constant
    }

    protected void calculTailleCasesTexturees(int largeur, int hauteur) {
        tileSize = Math.min(Math.min((LARGEUR_ZONE / largeur), (HAUTEUR_ZONE / hauteur)), TAILLE_TEXTURE);
        offsetX = (LARGEUR_ZONE - tileSize * largeur) / 2;
        offsetY = (HAUTEUR_ZONE - tileSize * hauteur) / 2;
    }
    protected void dessinerMur(Cellule c,int px,int py) {
        Image texture = choixTexturesMur(modele, c);
        gc.drawImage(texture, px, py, tileSize, tileSize);
    }
    protected void dessinerAngleInterieur(Cellule c,int px,int py) {
        List<Image> listeCoin = choixTextureAngleInterne(modele,c);
        for(Image i : listeCoin){
            gc.drawImage(i, px, py, tileSize, tileSize);
        }
    }
    protected void dessinerSol(Cellule c,int px,int py) {
        gc.drawImage(modele.getBiome().getSol(), px, py, tileSize, tileSize);
        dessinerAngleInterieur(c,px,py);
    }
    protected void dessinerSortie(Cellule c,int px,int py) {
        dessinerSol(c,px,py);
        gc.drawImage(sortie, px, py, tileSize, tileSize);
    }

    protected void dessinerJoueur() {
        Cellule pos = modele.getPositionJoueur();

        double targetX = offsetX + pos.getX() * tileSize;
        double targetY = offsetY + pos.getY() * tileSize;

        double currentX = joueur.getTranslateX();
        double currentY = joueur.getTranslateY();

        double dist = Math.abs(targetX - currentX) + Math.abs(targetY - currentY);

        joueur.setFitWidth(tileSize);
        joueur.setFitHeight(tileSize);

        joueur.toFront();

        if (dist > tileSize * 1.5) {
            animationJoueur.stop();
            joueur.setTranslateX(targetX);
            joueur.setTranslateY(targetY);
        }
        else {
            if (targetY < currentY - 0.1) {
                joueur.setImage(CacheMemoire.getImage("souris2.png")); // Dos
            }
            else if (targetY > currentY + 0.1) {
                joueur.setImage(CacheMemoire.getImage("souris3.png")); // Face
            }
            else if (targetX < currentX - 0.1) {
                joueur.setImage(CacheMemoire.getImage("souris1.png")); // Gauche
            }
            else if (targetX > currentX + 0.1) {
                joueur.setImage(CacheMemoire.getImage("souris0.png")); // Droite
            }

            animationJoueur.stop();
            animationJoueur.setToX(targetX);
            animationJoueur.setToY(targetY);
            animationJoueur.play();
        }
    }


    protected Image choixTexturesMur(ModeleJeu modele, Cellule cellule) {
        String chemin = ImageOutils.choixTextureMurComplet(modele, cellule);
        return CacheMemoire.getTextureForBiome(chemin,modele.getBiome());
    }
    protected  List<Image> choixTextureAngleInterne(ModeleJeu modele, Cellule cellule) {
        List<String> listeNomAngles=ImageOutils.choixTextureAngleInterne(modele,cellule);
        List<Image> listeImageAngles=new ArrayList<>();
        for(String nomAngle:listeNomAngles){
            listeImageAngles.add(CacheMemoire.getTextureForBiome(nomAngle,modele.getBiome()));
        }
        return  listeImageAngles;
    }

    @Override
    public final void update() {
        updateVueEnfant();
    }

    protected abstract void updateVueEnfant();
    protected abstract void dessineeLabyrinthe();
}
