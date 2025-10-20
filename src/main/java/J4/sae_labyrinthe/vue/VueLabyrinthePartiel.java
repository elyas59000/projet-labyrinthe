package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.controleur.ControleurLabyrinthe;
import J4.sae_labyrinthe.modele.IModeleJeu;
import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.ModeleJeu;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class VueLabyrinthePartiel extends VueLabyrintheBase{
    private GridPane labPartiel;

    public VueLabyrinthePartiel(IModeleJeu modele) {
        super(modele);
        this.labPartiel = new GridPane();
        this.labEntier = new GridPane();
        this.setTitle("Fenetre labyrinthe partiel");
        this.setScene(createSceneContent());
        ControleurLabyrinthe c = new ControleurLabyrinthe(modele, this);
        this.show();
    }

    @Override
    protected Scene createSceneContent() {
        HBox hBox = new HBox();
        afficherLabyrinthe(labEntier);
        afficherLabyrinthePartiel(labPartiel);
        javafx.scene.control.Separator separator = new javafx.scene.control.Separator();
        separator.setOrientation(javafx.geometry.Orientation.VERTICAL);
        separator.setStyle("-fx-background-color: black; -fx-pref-width: 50;");
        hBox.getChildren().add(labEntier);
        hBox.getChildren().add(separator);
        hBox.getChildren().add(labPartiel);

        labEntier.prefWidthProperty().bind(hBox.widthProperty().multiply(0.7)); // 70% pour le labyrinthe entier
        labPartiel.prefWidthProperty().bind(hBox.widthProperty().multiply(0.3)); // 30% pour le labyrinthe partiel

        int hauteurLaby = this.modele.getLabyrinthe().getHauteur();
        int largeurLaby = this.modele.getLabyrinthe().getLargeur();
        int tailleCelluleMin = 40;

        labEntier.setMinWidth(largeurLaby * tailleCelluleMin);
        labEntier.setMinHeight(hauteurLaby * tailleCelluleMin);

        labPartiel.setMinWidth(3 * tailleCelluleMin);
        labPartiel.setMinHeight(3 * tailleCelluleMin);

        HBox.setHgrow(labEntier, Priority.ALWAYS);
        HBox.setHgrow(labPartiel, Priority.ALWAYS);

        labEntier.setMaxWidth(Double.MAX_VALUE);
        labPartiel.setMaxWidth(Double.MAX_VALUE);
        return new Scene(hBox);
    }

    @Override
    public void update() {
        afficherLabyrinthe(labEntier);
        afficherLabyrinthePartiel(labPartiel);
    }

    @Override
    public void afficherLabyrinthe(GridPane p) {
        p.getChildren().clear();
        Labyrinthe laby = this.modele.getLabyrinthe();
        int hauteur = laby.getHauteur();
        int largeur = laby.getLargeur();

        remplirGridPane(p, 0, hauteur - 1, 0, largeur - 1, 0, 0, largeur, hauteur, laby, -1, -1, false);
    }

    public void afficherLabyrinthePartiel(GridPane p) {
        p.getChildren().clear();
        Labyrinthe laby = this.modele.getLabyrinthe();
        // position du joueur
        int joueurLigne = this.getModele().getPositionJoueur().getY();
        int joueurCol = this.getModele().getPositionJoueur().getX();

        remplirGridPane(p, joueurLigne - 1, joueurLigne + 1, joueurCol - 1, joueurCol + 1, joueurLigne - 1, joueurCol - 1, 3, 3, laby, joueurLigne, joueurCol, true);
    }

    private Rectangle creerCellule(GridPane p, int nbColonnes, int nbLignes, ImagePattern contenu) {
        Rectangle cell = new Rectangle();
        cell.widthProperty().bind(p.widthProperty().divide(nbColonnes));
        cell.heightProperty().bind(p.heightProperty().divide(nbLignes));
        cell.setFill(contenu);
        return cell;
    }

    private ImagePattern getCellContent(int i, int j, int joueurLigne, int joueurCol, Labyrinthe laby, boolean partiel) {
        Image brique = getBrique();
        Image fond = getFond();
        Image fin = getFin();
        Image joueur = getJoueur();

        if (i < 0 || i >= laby.getHauteur() || j < 0 || j >= laby.getLargeur()) {
            return new ImagePattern(brique);
        }

        if (laby.getGrille()[i][j].estMur()) {
            return new ImagePattern(brique);
        }

        if (partiel && i == joueurLigne && j == joueurCol) {
            return new ImagePattern(fusionnerImages(fond, joueur));
        }

        if (laby.getGrille()[i][j].equals(laby.getSortie())) {
            return new ImagePattern(fusionnerImages(fond, fin));
        }

        return new ImagePattern(fond);
    }

    private void remplirGridPane(GridPane p, int startLigne, int endLigne, int startCol, int endCol,
                                 int offsetLigne, int offsetCol, int nbColonnes, int nbLignes,
                                 Labyrinthe laby, int joueurLigne, int joueurCol, boolean partiel) {

        for (int i = startLigne; i <= endLigne; i++) {
            for (int j = startCol; j <= endCol; j++) {
                Rectangle cell = creerCellule(p, nbColonnes, nbLignes, getCellContent(i, j, joueurLigne, joueurCol, laby, partiel));
                p.add(cell, j - offsetCol, i - offsetLigne);
            }
        }
    }

    public ModeleJeu getModele() {
        return (ModeleJeu) modele;
    }

    public GridPane getLabEntier() {
        return labEntier;
    }

    public Image getBrique() {
        return new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/Brique.png").toExternalForm());
    }

    public Image getJoueur() {
        return new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/souris.png").toExternalForm());
    }

    public Image getFond() {
        return new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/herbe.png").toExternalForm());
    }

    public Image getFin() {
        return new Image(getClass().getResource("/J4/sae_labyrinthe/assets/textures/drapeau.png").toExternalForm());

    }
}
