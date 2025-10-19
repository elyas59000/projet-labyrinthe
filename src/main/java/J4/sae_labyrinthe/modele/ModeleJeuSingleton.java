package J4.sae_labyrinthe.modele;

public class ModeleJeuSingleton {

    private static ModeleJeu INSTANCE;
    private ModeleJeuSingleton() {}

    public static ModeleJeu getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModeleJeu();
        }
            return INSTANCE;
    }
}
