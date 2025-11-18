package J4.sae_labyrinthe.utils;

import J4.sae_labyrinthe.modele.Cellule;
import J4.sae_labyrinthe.modele.ILabyrinthe;
import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.ModeleJeu;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe utilitaire de gestion des textures connectées
 * @author Clement Roty
 */
public class ImageOutils {

    /**
     * Fusionne l'image `dessus` par-dessus l'image `fond` et retourne une
     * nouvelle image contenant le rendu combiné.
     *
     * @param fond Image de fond (textureMur) utilisée pour déterminer la taille
     * @param dessus Image dessinée au-dessus du fond
     * @return Image finale
     */
    public static Image fusionnerImages(Image fond,Image dessus) {
        List<Image> list =new ArrayList<Image>();
        list.add(fond);
        list.add(dessus);
        return fusionnerImages(list);
    }

    public static Image fusionnerImages(List<Image> images) {
        if (images == null || images.isEmpty()) {
            return null;
        }
        Image first = images.get(0);
        double width = first.getWidth();
        double height = first.getHeight();
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (Image img : images) {
            gc.drawImage(img, 0, 0);
        }
        WritableImage combined = new WritableImage((int) width, (int) height);
        canvas.snapshot(null, combined);
        return combined;
    }


    public static Image recolorTexture(Image textureMurImage, Map<String, Color> palette) {
        int width = (int) textureMurImage.getWidth();
        int height = (int) textureMurImage.getHeight();

        WritableImage result = new WritableImage(width, height);
        PixelReader reader = textureMurImage.getPixelReader();
        PixelWriter writer = result.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color c = reader.getColor(x, y);
                double brightness = c.getBrightness(); // 0 = noir, 1 = blanc

                // correspondance ton → couleur
                Color target;
                if (brightness > 0.75) target = palette.getOrDefault("LIGHT_TOP", c);
                else if (brightness > 0.55) target = palette.getOrDefault("MID_TOP", c);
                else if (brightness > 0.35) target = palette.getOrDefault("BRICK_INNER", c);
                else target = palette.getOrDefault("SHADOW", c);

                // Conserver l’alpha (transparence)
                writer.setColor(x, y, Color.color(
                        target.getRed(), target.getGreen(), target.getBlue(), c.getOpacity()
                ));
            }
        }

        return result;
    }

    public static String choixTextureMurComplet(ModeleJeu modele, Cellule c) {
        ILabyrinthe lab=modele.getLabyrinthe();
        Cellule[][] grille = lab.getGrille();
        int x = c.getX();
        int y = c.getY();
        int largeur = grille[0].length;
        int hauteur = grille.length;

        // Connexions principales et diagonales
        boolean H = y > 0 && grille[y-1][x].estMur();
        boolean D = x < largeur-1 && grille[y][x+1].estMur();
        boolean B = y < hauteur-1 && grille[y+1][x].estMur();
        boolean G = x > 0 && grille[y][x-1].estMur();
        boolean HD = y > 0 && x < largeur-1 && grille[y-1][x+1].estMur();
        boolean HG = y > 0 && x > 0 && grille[y-1][x-1].estMur();
        boolean BG = y < hauteur-1 && x > 0 && grille[y+1][x-1].estMur();
        boolean BD = y < hauteur-1 && x < largeur-1 && grille[y+1][x+1].estMur();

        // mask cardinal (H=1, D=2, B=4, G=8)
        int mask = 0;
        if(H) mask |= 1;
        if(D) mask |= 2;
        if(B) mask |= 4;
        if(G) mask |= 8;
        String textureMur = switch (mask) {
            case 0 ->   "mur_seul.png";
            case 1 ->   "mur_connecte_H.png";
            case 2 ->   "mur_connecte_D.png";
            case 4 ->   "mur_connecte_B.png";
            case 8 ->   "mur_connecte_G.png";
            case 3 ->   "mur_coin_exterieur_G.png"; // HD
            case 6 ->   "mur_coin_exterieur_H.png"; // DB
            case 12 ->   "mur_coin_exterieur_D.png"; // BG
            case 9 ->   "mur_coin_exterieur_B.png"; // GH
            case 7 ->   "mur_t_G.png";
            case 11 ->   "mur_t_B.png";
            case 14 ->   "mur_t_H.png";
            case 13 ->   "mur_t_D.png";
            case 5 ->   "mur_vertical.png";
            case 10 ->   "mur_horizontal.png";
            case 15 ->   "mur_plein.png";
            default ->   "mur_seul.png";
        };
        if (mask == 10) {
            int full = fullMaskDiag(mask,HG,HD,BG,BD,true,true,true,true);
            return switch (full) {
                case 74  ->   "mur_horizontal_angle_B.png";
                case 106 ->   "mur_horizontal_angle_B_G.png";
                case 138 ->   "mur_horizontal_angle_D.png";
                case 202 ->   "mur_horizontal_angle_D_B.png";
                case 234 ->   "mur_horizontal_angle_D_B_G.png";
                case 170 ->   "mur_horizontal_angle_D_G.png";
                case 42  ->   "mur_horizontal_angle_G.png";
                case 26  ->   "mur_horizontal_angle_H.png";
                case 90  ->   "mur_horizontal_angle_H_B.png";
                case 122 ->   "mur_horizontal_angle_H_B_G.png";
                case 154 ->   "mur_horizontal_angle_H_D.png";
                case 218 ->   "mur_horizontal_angle_H_D_B.png";
                case 250 ->   "mur_horizontal_angle_H_D_B_G.png";
                case 186 ->   "mur_horizontal_angle_H_D_G.png";
                case 58  ->   "mur_horizontal_angle_H_G.png";
                default -> textureMur;
            };
        }
        if (mask == 5) {
            int full = fullMaskDiag(mask,HG,HD,BG,BD,false,false,true,true);
            return switch (full) {
                case 69  ->   "mur_vertical_angle_B.png";
                case 101 ->   "mur_vertical_angle_B_G.png";
                case 37  ->   "mur_vertical_angle_G.png";
                default -> textureMur;
            };
        }
        if (mask == 4) { // bas
            int full = fullMaskDiag(mask,HG,HD,BG,BD,false,false,true,true);
            return switch (full) {
                case 68  ->   "mur_connecte_B_angle_B.png";
                case 100 ->   "mur_connecte_B_angle_B_G.png";
                case 36  ->   "mur_connecte_B_angle_G.png";
                default -> textureMur;
            };
        }
        if (mask == 2) { // droite
            int full = fullMaskDiag(mask,HG,HD,BG,BD,false,true,false,true);
            return switch (full) {
                case 66  ->   "mur_connecte_D_angle_B.png";
                case 130 ->   "mur_connecte_D_angle_D.png";
                case 194 ->   "mur_connecte_D_angle_D_B.png";
                default -> textureMur;
            };
        }
        if (mask == 8) { // gauche
            int full = fullMaskDiag(mask,HG,HD,BG,BD,true,false,true,false);
            return switch (full) {
                case 40->   "mur_connecte_G_angle_G.png";
                case 24 ->   "mur_connecte_G_angle_H.png";
                case 56 ->   "mur_connecte_G_angle_H_G.png";
                default -> textureMur;
            };
        }

        if (mask == 3) { //coin gauche
            int full = fullMaskDiag(mask,HG,HD,BG,BD,false,false,false,true);
            if(full == 67){
                return "mur_coin_exterieur_G_angle_B.png";
            }
            return textureMur;

        }
        if (mask == 6) { //coin haut
            int full = fullMaskDiag(mask,HG,HD,BG,BD,false,true,true,true);
            return switch (full) {
                case 134 ->   "mur_coin_exterieur_H_angle_D.png";
                case 198 ->   "mur_coin_exterieur_H_angle_D_B.png";
                case 38  ->   "mur_coin_exterieur_H_angle_G.png";
                case 102 ->   "mur_coin_exterieur_H_angle_B_G.png";
                case 166 ->   "mur_coin_exterieur_H_angle_D_G.png";
                case 230 ->   "mur_coin_exterieur_H_angle_D_B_G.png";
                case 70  ->   "mur_coin_exterieur_H_angle_B.png";
                default  -> textureMur;
            };
        }
        if (mask == 12) { //coin droite
            int full = fullMaskDiag(mask,HG,HD,BG,BD,true,false,true,true);
            return switch (full) {
                case 76 ->   "mur_coin_exterieur_D_angle_B.png";
                case 108 ->  "mur_coin_exterieur_D_angle_B_G.png";
                case 28  ->  "mur_coin_exterieur_D_angle_H.png";
                case 60  ->  "mur_coin_exterieur_D_angle_H_G.png";
                case 92 ->   "mur_coin_exterieur_D_angle_H_B.png";
                case 124 ->  "mur_coin_exterieur_D_angle_H_B_G.png";
                case 44 ->   "mur_coin_exterieur_D_angle_G.png";
                default  -> textureMur;

            };
        }
        if (mask == 9) { //coin bas
            int full = fullMaskDiag(mask,HG,HD,BG,BD,false,false,true,false);
            if (full==41) {
                return "mur_coin_exterieur_B_angle_G.png";
            }
            return textureMur;
        }
        if (mask == 14) { //t_H
            int full = fullMaskDiag(mask,HG,HD,BG,BD,true,true,true,true);
            return switch (full) {
                case 30 ->    "mur_t_H_angle_H.png";
                case 142 ->   "mur_t_H_angle_D.png";
                case 158 ->   "mur_t_H_angle_H_D.png";
                case 110 ->   "mur_t_H_angle_B_G.png";
                case 46 ->    "mur_t_H_angle_G.png";
                case 78 ->    "mur_t_H_angle_B.png";
                case 238 ->   "mur_t_H_angle_D_B_G.png";
                case 206 ->   "mur_t_H_angle_D_B.png";
                case 174 ->   "mur_t_H_angle_D_G.png";
                case 254 ->   "mur_t_H_angle_H_D_B_G.png";
                case 222 ->   "mur_t_H_angle_H_D_B.png";
                case 190 ->   "mur_t_H_angle_H_D_G.png";
                case 126 ->   "mur_t_H_angle_H_B_G.png";
                case 94 ->    "mur_t_H_angle_H_B.png";
                case 62 ->    "mur_t_H_angle_H_G.png";
                default  -> textureMur;
            };
        }
        if (mask == 11) { //t_B
            int full = fullMaskDiag(mask,HG,HD,BG,BD,false,false,true,true);
            return switch (full) {
                case 43 ->   "mur_t_B_angle_G.png";      // HD
                case 75  ->   "mur_t_B_angle_B.png";      // BG
                case 107 ->   "mur_t_B_angle_B_G.png";    // HDBG
                default  -> textureMur;
            };
        }
        if (mask == 13) { //t_D
            int full = fullMaskDiag(mask,HG,HD,BG,BD,false,false,true,true);
            return switch (full) {
                case 77 ->  "mur_t_D_angle_B.png";
                case 109 -> "mur_t_D_angle_B_G.png";
                case 45 ->  "mur_t_D_angle_G.png";
                default -> textureMur;
            };
        }
        if (mask == 7) { //t_G
            int full = fullMaskDiag(mask,HG,HD,BG,BD,false,false,true,true);
            return switch (full) {
                case 39 ->  "mur_t_G_angle_G.png";
                case 103 -> "mur_t_G_angle_B_G.png";
                case 71 ->  "mur_t_G_angle_B.png";
                default -> textureMur;
            };
        }
        return textureMur;
    }
    public static List<String> choixTextureAngleInterne(ModeleJeu modele, Cellule c) {
        ILabyrinthe lab = modele.getLabyrinthe();
        Cellule[][] grille = lab.getGrille();
        int x = c.getX();
        int y = c.getY();
        int largeur = grille[0].length;
        int hauteur = grille.length;
        boolean H = y > 0 && grille[y - 1][x].estMur();
        boolean D = x < largeur - 1 && grille[y][x + 1].estMur();
        boolean B = y < hauteur - 1 && grille[y + 1][x].estMur();
        boolean G = x > 0 && grille[y][x - 1].estMur();
        boolean HD = y > 0 && x < largeur - 1 && grille[y - 1][x + 1].estMur();
        boolean HG = y > 0 && x > 0 && grille[y - 1][x - 1].estMur();
        boolean BG = y < hauteur - 1 && x > 0 && grille[y + 1][x - 1].estMur();
        boolean BD = y < hauteur - 1 && x < largeur - 1 && grille[y + 1][x + 1].estMur();

        List<String> textureAngle = new ArrayList<>();
        if(HG && H && G){
            textureAngle.add("mur_coin_interieur_H.png");

        }
        if(BG&& B && G){
            textureAngle.add("mur_coin_interieur_G.png");

        }
        if(BD && B && D){
            textureAngle.add("mur_coin_interieur_B.png");

        }
        if(HD && H && D){
            textureAngle.add("mur_coin_interieur_D.png");

        }
        return textureAngle;
    }

    private static int fullMaskDiag(int maskCardinaux, boolean HG, boolean HD, boolean BG, boolean BD, boolean HG_est_utile, boolean HD_est_utile, boolean BG_est_utile, boolean BD_est_utile){
        int diag=0;
        if(HD && HD_est_utile ) diag |= 128;
        if(HG && HG_est_utile) diag |= 16;
        if(BG && BG_est_utile) diag |= 32;
        if(BD && BD_est_utile) diag |= 64;
        return maskCardinaux |diag;
    }

}
