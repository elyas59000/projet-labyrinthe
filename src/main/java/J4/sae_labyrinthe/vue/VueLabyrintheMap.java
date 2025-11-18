package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.modele.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VueLabyrintheMap extends VueLabyrintheAbstraite {

    private final boolean printJoueur;
    private final boolean printSortie;
    private double offsetX = 0;
    private double offsetY = 0;
    private boolean estInitialise = false;

    public VueLabyrintheMap(ModeleJeu modele, boolean printJoueur, boolean printSortie) {
        super(modele);
        this.printJoueur = printJoueur;
        this.printSortie = printSortie;
        this.getChildren().remove(joueur);
        this.setStyle("-fx-background-color: #333333; -fx-background-radius: 5;");
        this.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0 && !estInitialise) {
                this.estInitialise = true;
                this.initialiserVue();
            }
        });
        this.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (estInitialise && newVal.doubleValue() > 0 && newVal.doubleValue() != oldVal.doubleValue()) {
                this.initialiserVue();
            }
        });
    }
    private void initialiserVue() {
        double paneWidth = getWidth();
        double paneHeight = getHeight();
        Labyrinthe lab = modele.getLabyrinthe();
        int labLargeur = lab.getLargeur();
        int labHauteur = lab.getHauteur();
        if (paneHeight < 10) {
            paneHeight = paneWidth;
        }
        this.tileSize = (int) Math.max(1.0, Math.min(paneWidth / labLargeur, paneHeight / labHauteur));
        double canvasWidth = this.tileSize * labLargeur;
        double canvasHeight = this.tileSize * labHauteur;
        canvas.setWidth(canvasWidth);
        canvas.setHeight(canvasHeight);
        this.offsetX = (paneWidth - canvasWidth) / 2;
        this.offsetY = (paneHeight - canvasHeight) / 2;
        canvas.setLayoutX(this.offsetX);
        canvas.setLayoutY(this.offsetY);
        if (joueur != null) {
            this.getChildren().remove(joueur);
        }
        initPlayerImage();
        dessineeLabyrinthe();
        updatePlayerPos();
    }

    private void initPlayerImage() {
        Rectangle rect = new Rectangle(tileSize, tileSize, Color.RED);
        WritableImage img = new WritableImage(tileSize, tileSize);
        rect.snapshot(null, img);
        joueur = new ImageView(img);
        joueur.setFitWidth(tileSize);
        joueur.setFitHeight(tileSize);
        joueur.setVisible(printJoueur);
        this.getChildren().add(joueur);
    }

    @Override
    protected void dessineeLabyrinthe() {
        if (!estInitialise) return;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Cellule[][] g = modele.getLabyrinthe().getGrille();
        for (Cellule[] ligne : g)
            for (Cellule c : ligne){
                drawCellSimple(c);
            }
    }

    private void drawCellSimple(Cellule c) {
        int px = c.getX() * tileSize;
        int py = c.getY() * tileSize;
        if (c.estMur()) gc.setFill(Color.DARKSLATEGRAY);
        else if (printSortie && c.equals(modele.getLabyrinthe().getSortie())) gc.setFill(Color.GOLD);
        else gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(px, py, tileSize, tileSize);
    }

    private void updatePlayerPos() {
        if (!printJoueur || !estInitialise || joueur == null) return;
        Cellule p = modele.getPositionJoueur();
        joueur.setLayoutX(this.offsetX + p.getX() * tileSize);
        joueur.setLayoutY(this.offsetY + p.getY() * tileSize);
    }

    @Override
    protected void updateVueEnfant() {
        updatePlayerPos();
    }
}