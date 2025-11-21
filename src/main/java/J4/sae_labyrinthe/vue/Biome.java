package J4.sae_labyrinthe.vue;

import J4.sae_labyrinthe.utils.CacheMemoire;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Map;

public enum Biome {
    PLAINE("/J4/sae_labyrinthe/assets/textures/biome/plaine/",
            Map.of(
                    "LIGHT_TOP", Color.web("#E8A37A"),
                    "MID_TOP", Color.web("#C77652"),
                    "BRICK_INNER", Color.web("#8F4A2D"),
                    "SHADOW", Color.web("#3E1E12")
            ),"#8ec941"),
    DESERT("/J4/sae_labyrinthe/assets/textures/biome/desert/",
            Map.of(
                    "LIGHT_TOP", Color.web("#F0C88B"),
                    "MID_TOP", Color.web("#C9925B"),
                    "BRICK_INNER", Color.web("#B7874E"),
                    "SHADOW", Color.web("#512702")
            ),"#FFF895"),
    FORET("/J4/sae_labyrinthe/assets/textures/biome/foret/",
            Map.of(
                    "LIGHT_TOP", Color.web("#D5D8D2"),
                    "MID_TOP", Color.web("#A5AAA3"),
                    "BRICK_INNER", Color.web("#6F736E"),
                    "SHADOW", Color.web("#2F312E")
            ),"#494f2c"),
    NEIGE("/J4/sae_labyrinthe/assets/textures/biome/neige/",
            Map.of(
                    "LIGHT_TOP", Color.web("#FFFFFF"),
                    "MID_TOP", Color.web("#abdcff"),
                    "BRICK_INNER", Color.web("#B4C8D8"),
                    "SHADOW", Color.web("#4A6372")
            ),"#affff7"),
    GROTTE("/J4/sae_labyrinthe/assets/textures/biome/grotte/",
            Map.of(
                    "LIGHT_TOP", Color.web("#C8B8A4"),
                    "MID_TOP", Color.web("#A8967F"),
                    "BRICK_INNER", Color.web("#746552"),
                    "SHADOW", Color.web("#362F26")
            ),"#74482f"),
    ENFER("/J4/sae_labyrinthe/assets/textures/biome/enfer/",
            Map.of(
                    "LIGHT_TOP", Color.web("#5A4A42"),
                    "MID_TOP", Color.web("#3B2E28"),
                    "BRICK_INNER", Color.web("#241814"),
                    "SHADOW", Color.web("#0C0605")
            ),"#771110");


    private final String path;
    private final Map<String, Color> palette;
    private final String background;

    Biome(String path, Map<String, Color> palette,String background) {
        this.path = path;
        this.palette = palette;
        this.background = background;

    }
    public String getBackground() {
        return this.background;
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
    public String getPath() {
        return path;
    }
}
