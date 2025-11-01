package J4.sae_labyrinthe.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import J4.sae_labyrinthe.modele.Joueur;

/**
 * Classe utilitaire pour la sauvegarde et le chargement des données des joueurs.
 * @author Adam Stievenard
 */
public class Save {

    private static final String JOUEUR_PATH ="data/save.data";

    /**
     * Sauvegarde une liste de joueurs dans un fichier.
     * 
     * @param joueurs La liste des joueurs à sauvegarder
     * @throws IOException En cas d'erreur lors de l'écriture du fichier
     * @author Adam Stievenard
     */
    public static void saveToFile(ArrayList<Joueur> joueurs) throws IOException {

        FileOutputStream fos = new FileOutputStream(JOUEUR_PATH);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        try {
            oos.writeObject(joueurs);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Charge la liste des joueurs à partir du fichier de sauvegarde.
     * 
     * @return La liste des joueurs chargée depuis le fichier
     * @throws IOException En cas d'erreur lors de la lecture du fichier
     * @author Adam Stievenard
     */
    public static ArrayList<Joueur> loadSave() throws IOException {

        FileInputStream fis = new FileInputStream(JOUEUR_PATH);
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<Joueur> savedData;

        try {
            savedData = (List<Joueur>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return (ArrayList<Joueur>) savedData;
    }


}
