package J4.sae_labyrinthe.vue;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Fog {

    private final int width;
    private final int height;
    private final boolean decouvertePersistante;
    private final boolean[][] decouvert;
    private final Color fogColor;

    public Fog(int width, int height, Color fogColor, boolean decouvertePersistante) {
        this.width = width;
        this.height = height;
        this.fogColor = fogColor;
        this.decouvertePersistante = decouvertePersistante;
        this.decouvert = new boolean[height][width];
    }

    public void updateAround(int cx, int cy, int radius) {
        if (!decouvertePersistante) {
            for (int y = 0; y < height; y++)
                for (int x = 0; x < width; x++)
                    decouvert[y][x] = false;
        }

        for (int y = cy - radius; y <= cy + radius; y++)
            for (int x = cx - radius; x <= cx + radius; x++)
                if (x >= 0 && x < width && y >= 0 && y < height) decouvert[y][x] = true;
    }


    public void drawFog(GraphicsContext gc, double offsetX, double offsetY, int tileSize) {
        gc.setFill(fogColor);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!decouvert[y][x]) gc.fillRect(offsetX + x * tileSize, offsetY + y * tileSize, tileSize, tileSize);
            }
        }
    }
}
