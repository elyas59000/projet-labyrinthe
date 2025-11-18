package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.utils.CacheMemoire;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Map;

public enum Biome {
    /*
    PLAINE("/J4/sae_labyrinthe/assets/textures/biome/plaine/",
            Map.of(
                    "LIGHT_TOP", Color.web("#F0C88B"),
                    "MID_TOP", Color.web("#C9925B"),
                    "BRICK_INNER", Color.web("#B7874E"),
                    "SHADOW", Color.web("#512702")
            )),
     */
    DESERT("/J4/sae_labyrinthe/assets/textures/biome/desert/",
            Map.of(
                    "LIGHT_TOP", Color.web("#F0C88B"),
                    "MID_TOP", Color.web("#C9925B"),
                    "BRICK_INNER", Color.web("#B7874E"),
                    "SHADOW", Color.web("#512702")
            ));
    private final String path;
    private final Map<String, Color> palette;

    private

    Biome(String path, Map<String, Color> palette) {
        this.path = path;
        this.palette = palette;
    }
    public String getBackground() {
        return "#FFF895";
    }
    public Image getSol() {
        return CacheMemoire.getImage(path+"sol0.png");
    }
    public Image getSortie() {
        return CacheMemoire.getImage(path+"sortie0.png");
    }

    public Map<String, Color> getPalette() {
        return palette;
    }
    public  String getPath() {
        return path;
    }
}
