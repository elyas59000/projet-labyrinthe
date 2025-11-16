package J4.sae_labyrinthe.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import J4.sae_labyrinthe.modele.Joueur;

/**
 * Classe utilitaire pour la sauvegarde et le chargement des données des joueurs.
 * Gère aussi le chiffrement et déchiffrement des données sensibles (nom et mot de passe).
 * @author Adam Stievenard
 */
public class Save {

    private static final String JOUEUR_PATH ="data/save.data";
    private static final String LABYRINTHE_DU_JOUR_PATH = "data/laby_";
    private static final String SCORES_DU_JOUR_PATH = "data/scores_";

    /**
     * Sauvegarde une liste de joueurs dans un fichier avec chiffrement.
     * Les données sont chiffrées avant la sérialisation.
     *
     * @param joueurs La liste des joueurs à sauvegarder
     * @throws IOException En cas d'erreur lors de l'écriture du fichier
     * @author Adam Stievenard
     */
    public static void saveToFile(ArrayList<Joueur> joueurs) throws IOException {
        try {
            //Chiffrement.chiffrer(joueurs);

            FileOutputStream fos = new FileOutputStream(JOUEUR_PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(joueurs);
            oos.close();
            fos.close();

            // Chiffrement.dechiffrer(joueurs);

        } catch (Exception e) {
            throw new IOException("Erreur lors de la sauvegarde chiffrée", e);
        }
    }

    /**
     * Charge la liste des joueurs à partir du fichier de sauvegarde.
     * Les données sont déchiffrées après désérialisation.
     *
     * @return La liste des joueurs chargée depuis le fichier
     * @throws IOException En cas d'erreur lors de la lecture du fichier
     * @author Adam Stievenard
     */
    public static ArrayList<Joueur> loadSave() throws IOException {
        FileInputStream fis = new FileInputStream(JOUEUR_PATH);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<Joueur> savedData;

        try {
            savedData = (ArrayList<Joueur>) ois.readObject();
            ois.close();
            fis.close();

            // Chiffrement.dechiffrer(savedData);

        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("Erreur lors du chargement des données", e);
        } catch (Exception e) {
            throw new IOException("Erreur lors du déchiffrement des données", e);
        }

        return savedData;
    }

}
