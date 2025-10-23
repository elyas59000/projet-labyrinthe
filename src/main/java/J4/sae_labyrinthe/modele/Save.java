package J4.sae_labyrinthe.modele;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Save {
    private static final String JOUEUR_PATH = Objects.requireNonNull(Labyrinthe.class.getResource("/J4/sae_labyrinthe/txt/joueurs.txt")).toExternalForm();

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
