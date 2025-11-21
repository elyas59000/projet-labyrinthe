package J4.sae_labyrinthe.utils;

import J4.sae_labyrinthe.vue.Biome;
import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CacheMemoire {

    private static final Map<String, Image> cache=new HashMap<>();
    private static final Map<Biome, Map<String, Image>> cacheBiomes = new HashMap<>();
    private static final String path= "/J4/sae_labyrinthe/assets/textures/blocks/";

    public static void chargementTextures(){
        String[] chemin= new String[]{
                "mur_coin_exterieur_B.png",
                "mur_coin_exterieur_B_angle_G.png",
                "mur_coin_exterieur_D.png",
                "mur_coin_exterieur_D_angle_B.png",
                "mur_coin_exterieur_D_angle_H.png",
                "mur_coin_exterieur_D_angle_H_B.png",
                "mur_coin_exterieur_D_angle_G.png",
                "mur_coin_exterieur_D_angle_B_G.png",
                "mur_coin_exterieur_D_angle_H_G.png",
                "mur_coin_exterieur_D_angle_H_B_G.png",
                "mur_coin_exterieur_G.png",
                "mur_coin_exterieur_G_angle_B.png",
                "mur_coin_exterieur_H.png",
                "mur_coin_exterieur_H_angle_D.png",
                "mur_coin_exterieur_H_angle_D_G.png",
                "mur_coin_exterieur_H_angle_G.png",
                "mur_coin_exterieur_H_angle_B.png",
                "mur_coin_exterieur_H_angle_D_B.png",
                "mur_coin_exterieur_H_angle_D_B_G.png",
                "mur_coin_exterieur_H_angle_B_G.png",
                "mur_coin_interieur_B.png",
                "mur_coin_interieur_D.png",
                "mur_coin_interieur_G.png",
                "mur_coin_interieur_H.png",
                "mur_connecte_B.png",
                "mur_connecte_B_angle_B.png",
                "mur_connecte_B_angle_B_G.png",
                "mur_connecte_B_angle_G.png",
                "mur_connecte_D.png",
                "mur_connecte_D_angle_B.png",
                "mur_connecte_D_angle_D.png",
                "mur_connecte_D_angle_D_B.png",
                "mur_connecte_G.png",
                "mur_connecte_G_angle_G.png",
                "mur_connecte_G_angle_H.png",
                "mur_connecte_G_angle_H_G.png",
                "mur_connecte_H.png",
                "mur_horizontal.png",
                "mur_horizontal_angle_B.png",
                "mur_horizontal_angle_B_G.png",
                "mur_horizontal_angle_D.png",
                "mur_horizontal_angle_D_B.png",
                "mur_horizontal_angle_D_B_G.png",
                "mur_horizontal_angle_D_G.png",
                "mur_horizontal_angle_G.png",
                "mur_horizontal_angle_H.png",
                "mur_horizontal_angle_H_B.png",
                "mur_horizontal_angle_H_B_G.png",
                "mur_horizontal_angle_H_D.png",
                "mur_horizontal_angle_H_D_B.png",
                "mur_horizontal_angle_H_D_B_G.png",
                "mur_horizontal_angle_H_D_G.png",
                "mur_horizontal_angle_H_G.png",
                "mur_plein.png",
                "mur_seul.png",
                "mur_t_B.png",
                "mur_t_B_angle_B.png",
                "mur_t_B_angle_B_G.png",
                "mur_t_B_angle_G.png",
                "mur_t_D.png",
                "mur_t_D_angle_B.png",
                "mur_t_D_angle_G.png",
                "mur_t_D_angle_B_G.png",
                "mur_t_G.png",
                "mur_t_G_angle_G.png",
                "mur_t_G_angle_B.png",
                "mur_t_G_angle_B_G.png",
                "mur_t_H.png",
                "mur_t_H_angle_D.png",
                "mur_t_H_angle_H.png",
                "mur_t_H_angle_H_D.png",
                "mur_t_H_angle_B_G.png",
                "mur_t_H_angle_G.png",
                "mur_t_H_angle_B.png",
                "mur_t_H_angle_D_B_G.png",
                "mur_t_H_angle_D_B.png",
                "mur_t_H_angle_D_G.png",
                "mur_t_H_angle_H_D_B_G.png",
                "mur_t_H_angle_H_D_B.png",
                "mur_t_H_angle_H_D_G.png",
                "mur_t_H_angle_H_B_G.png",
                "mur_t_H_angle_H_B.png",
                "mur_t_H_angle_H_G.png",
                "mur_vertical.png",
                "mur_vertical_angle_B.png",
                "mur_vertical_angle_B_G.png",
                "mur_vertical_angle_G.png"
        };
        for (String key : chemin) {
            try{
                cache.put(key, new Image(Objects.requireNonNull(CacheMemoire.class.getResourceAsStream(path + key))));
            }catch(NullPointerException e){
                System.out.println("texture non trouvée " +key);
            }

        }
        chargementBiome();
        chargerAutre();
    }

    private static void chargementBiome(){
        String desert=Biome.DESERT.getPath();
        String plaine=Biome.PLAINE.getPath();
        String foret=Biome.FORET.getPath();
        String neige=Biome.NEIGE.getPath();
        String grotte=Biome.GROTTE.getPath();
        String enfer=Biome.ENFER.getPath();

        String sol="sol0.png";
        String sortie="sortie0.png";
        String[] chemin= new String[]{
                desert+sol,
                desert+sortie,
                plaine+sol,
                plaine+sortie,
                foret+sol,
                foret+sortie,
                neige+sol,
                neige+sortie,
                grotte+sol,
                grotte+sortie,
                enfer+sol,
                enfer+sortie
        };
        for (String key : chemin) {
            cache.put(key, new Image(Objects.requireNonNull(CacheMemoire.class.getResourceAsStream(key))));
        }
    }

    private static void chargerAutre(){
        cache.put("souris0.png",new Image(Objects.requireNonNull(CacheMemoire.class.getResourceAsStream("/J4/sae_labyrinthe/assets/textures/souris0.png"))));
        cache.put("souris1.png",new Image(Objects.requireNonNull(CacheMemoire.class.getResourceAsStream("/J4/sae_labyrinthe/assets/textures/souris1.png"))));
        cache.put("souris2.png",new Image(Objects.requireNonNull(CacheMemoire.class.getResourceAsStream("/J4/sae_labyrinthe/assets/textures/souris2.png"))));
        cache.put("souris3.png",new Image(Objects.requireNonNull(CacheMemoire.class.getResourceAsStream("/J4/sae_labyrinthe/assets/textures/souris3.png"))));
    }

    public static void preloadAllBiomes() {
        for (Biome biome : Biome.values()) {
            Map<String, Image> recolored = new HashMap<>();
            for (String key : cache.keySet()) {
                Image base = cache.get(key);
                Image recoloredImg = ImageOutils.recolorTexture(base, biome.getPalette());
                recolored.put(key, recoloredImg);
            }
            cacheBiomes.put(biome, recolored);
        }
    }

    public static Image getTextureForBiome(String textureKey, Biome biome) {
        return cacheBiomes.get(biome).get(textureKey);
    }

    public static Image getImage(String key) {
        return cache.get(key);
    }

}
