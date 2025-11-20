package J4.sae_labyrinthe.utils;

import J4.sae_labyrinthe.modele.ILabyrinthe;
import J4.sae_labyrinthe.modele.Labyrinthe;
import J4.sae_labyrinthe.modele.LabyrintheParfait;

import java.io.File;
import java.time.LocalDate;
import java.util.Random;

public class LabyrintheDuJourManager {

    private static final String DOSSIER = "data/laby_jour/";
    private static final Random random = new Random();

    /**
     * Charge le labyrinthe du jour si déjà généré,
     * sinon en crée un nouveau, le sauvegarde et le retourne.
     */
    public static ILabyrinthe getLabyrintheDuJour() {

        String today = LocalDate.now().toString();
        String chemin = DOSSIER + "laby_" + today + ".dat";

        new File(DOSSIER).mkdirs();

        ILabyrinthe laby = Labyrinthe.charger(chemin);

        if (laby != null) {
            System.out.println("Labyrinthe du jour chargé !");
            return laby;
        }

        // 2) Sinon : on le crée

        System.out.println("Aucun labyrinthe pour aujourd'hui. Génération...");
        laby = LabyrintheDuJourManager.creerLabyrintheAleatoire();

        // 3) On sauvegarde
        laby.sauvegarder(chemin);

        System.out.println("Labyrinthe du jour généré et sauvegardé.");
        return laby;
    }

    public static ILabyrinthe creerLabyrintheAleatoire() {
        boolean choixParfait = random.nextBoolean();
        int largeur = 5 + random.nextInt(26);
        int longueur = 5 + random.nextInt(26);
        if (choixParfait) {
            int distance = (largeur + longueur)/2;
            return new LabyrintheParfait(largeur, longueur,distance);
        } else {
            double pourcentageMurs = 20.0 + random.nextDouble() * 30.0;
            int mur = (int) Math.floor(pourcentageMurs);
            return new Labyrinthe(largeur, longueur, mur);
        }
    }
}
