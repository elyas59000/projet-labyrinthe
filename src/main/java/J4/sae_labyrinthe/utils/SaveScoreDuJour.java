package J4.sae_labyrinthe.utils;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SaveScoreDuJour {
    private static final String DOSSIER = "data/score_jour/";

    /**
     * Sauvegarde le score du joueur pour aujourd'hui.
     * Si le joueur a déjà un score, on garde uniquement le meilleur (temps le plus faible).
     */
    public static void sauvegarderScore(ScoreDuJour nouveauScore) {
        try {
            new File(DOSSIER).mkdirs();
            String chemin = DOSSIER + "score_" + LocalDate.now() + ".dat";

            List<ScoreDuJour> scores = chargerScores();
            // On supprime les éventuels nulls
            scores.removeIf(Objects::isNull);

            boolean scoreMisAJour = false;

            for (int i = 0; i < scores.size(); i++) {
                ScoreDuJour s = scores.get(i);
                if (s.getNom().equals(nouveauScore.getNom())) {
                    // On garde le meilleur score (plus petit)
                    if (nouveauScore.getTemps() < s.getTemps()) {
                        scores.set(i, nouveauScore);
                    }
                    scoreMisAJour = true;
                    break;
                }
            }

            if (!scoreMisAJour) {
                scores.add(nouveauScore);
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chemin))) {
                oos.writeObject(scores);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Charge tous les scores du jour
     */
    public static ArrayList<ScoreDuJour> chargerScores() {
        try {
            String chemin = DOSSIER + "score_" + LocalDate.now() + ".dat";
            File f = new File(chemin);
            if (!f.exists()) return new ArrayList<>();

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                return (ArrayList<ScoreDuJour>) ois.readObject();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
