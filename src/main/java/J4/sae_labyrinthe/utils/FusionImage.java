package J4.sae_labyrinthe.utils;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 * Utilitaire pour fusionner deux images en une seule. Utilisé principalement
 * pour combiner des calques (par exemple un fond et une superposition) afin
 * d'obtenir une image résultante réutilisable dans l'interface JavaFX.
 *
 * @author Nathan Philippe
 */
public class FusionImage {

    /**
     * Fusionne l'image `dessus` par-dessus l'image `fond` et retourne une
     * nouvelle image contenant le rendu combiné.
     *
     * @param fond Image de fond (base) utilisée pour déterminer la taille
     * @param dessus Image dessinée au-dessus du fond
     * @return Image finale
     */
    public static Image fusionnerImages(Image fond, Image dessus) {
        double width = fond.getWidth();
        double height = fond.getHeight();

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.drawImage(fond, 0, 0);
        gc.drawImage(dessus, 0, 0);

        WritableImage combined = new WritableImage((int) width, (int) height);
        canvas.snapshot(null, combined);
        return combined;
    }
}
