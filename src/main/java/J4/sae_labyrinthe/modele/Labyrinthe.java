package J4.sae_labyrinthe.modele;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Implémentation d'un labyrinthe avec génération aléatoire.
 * Cette classe gère la création et la manipulation d'un labyrinthe,
 * y compris la génération des murs, la définition des entrées/sorties,
 * et la vérification des chemins possibles.
 *
 * @author Anas ACHOUCH
 * @author Nathan Philippe
 */
public class Labyrinthe implements ILabyrinthe, Serializable {
    // TODO : vérifier le Pourcentage entre 0 et 100
    private static final long serialVersionUID = 1L;
    private int largeur;
    private int hauteur;
    private double pourcentageMurs;
    private Cellule[][] grille;
    private Cellule entree;
    private Cellule sortie;
    private Etape e;

    /**
     * Constructeur simplifié du labyrinthe.
     *
     * @param largeur         Largeur du labyrinthe
     * @param hauteur         Hauteur du labyrinthe
     * @param pourcentageMurs Pourcentage de murs souhaité (entre 0 et 100)
     * @author Nathan Philippe
     */
    public Labyrinthe(int largeur, int hauteur, double pourcentageMurs) {
        this(largeur, hauteur, pourcentageMurs, null);
    }

    /**
     * @param largeur         Largeur du labyrinthe
     * @param hauteur         Hauteur du labyrinthe
     * @param pourcentageMurs Pourcentage de murs souhaité (entre 0 et 100)
     * @param e               Étape associée au labyrinthe (peut être null)
     * @author Nathan Philippe
     */
    public Labyrinthe(int largeur, int hauteur, double pourcentageMurs, Etape e) {
        this.largeur = largeur + 2;
        this.hauteur = hauteur + 2;
        this.pourcentageMurs = pourcentageMurs;
        this.e = e;
        grille = new Cellule[this.hauteur][this.largeur];
        genererLabyrinthe();
    }


    /**
     * Définit les cellules d'entrée et de sortie du labyrinthe.
     * Par défaut, l'entrée est en (0,0) et la sortie en (largeur-1, hauteur-1).
     *
     * @author Nathan Philippe
     */
    private void creerEntreeSortie() {
        Random r = new Random();
        int entreeX = r.nextInt(largeur - 2) + 1;
        int entreeY = r.nextInt(hauteur - 2) + 1;
        int sortieX, sortieY;
        do {
            sortieX = r.nextInt(largeur - 2) + 1;
            sortieY = r.nextInt(hauteur - 2) + 1;
        } while (Math.abs(sortieX - entreeX) + Math.abs(sortieY - entreeY) < 3);
        entree = grille[entreeY][entreeX];
        sortie = grille[sortieY][sortieX];
        entree.setMur(false);
        sortie.setMur(false);
    }


    /**
     * Initialise la grille du labyrinthe avec des cellules vides.
     *
     * @author Nathan Philippe
     */
    private void creerCellule() {
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {

                boolean estBordure =
                        (x == 0) || (x == largeur - 1) ||
                                (y == 0) || (y == hauteur - 1);
                grille[y][x] = new Cellule(x, y, estBordure);
            }
        }
    }

    private void placerMursAvecChemin(List<Cellule> chemin, int totalMurs) {
        List<Cellule> candidates = new ArrayList<>();

        for (int y = 1; y < hauteur - 1; y++) {
            for (int x = 1; x < largeur - 1; x++) {
                Cellule c = grille[y][x];
                if (!chemin.contains(c) && c != entree && c != sortie) {
                    candidates.add(c);
                }
            }
        }

        Collections.shuffle(candidates, new Random());

        for (int i = 0; i < Math.min(totalMurs, candidates.size()); i++) {
            candidates.get(i).setMur(true);
        }
    }


    private List<Cellule> genererChemin(Cellule start, Cellule end) {

        Queue<Cellule> q = new LinkedList<>();
        boolean[][] visite = new boolean[hauteur][largeur];
        Cellule[][] parent = new Cellule[hauteur][largeur];

        q.add(start);
        visite[start.getY()][start.getX()] = true;

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!q.isEmpty()) {
            Cellule c = q.poll();

            if (c == end) break;

            for (int[] d : dirs) {
                int nx = c.getX() + d[0];
                int ny = c.getY() + d[1];

                if (posValide(nx, ny) && !visite[ny][nx] && !grille[ny][nx].estMur()) {
                    visite[ny][nx] = true;
                    parent[ny][nx] = c;
                    q.add(grille[ny][nx]);
                }
            }
        }

        return reconstruireChemin(start, end, parent);
    }

    private List<Cellule> reconstruireChemin(Cellule start, Cellule end, Cellule[][] parent) {
        List<Cellule> chemin = new ArrayList<>();
        Cellule cur = end;

        while (cur != null && cur != start) {
            chemin.add(cur);
            cur = parent[cur.getY()][cur.getX()];
        }

        chemin.add(start);
        Collections.reverse(chemin);

        return chemin;
    }


    private void genererLabyrinthe() {
        creerCellule();
        creerEntreeSortie();
        List<Cellule> chemin = genererChemin(entree, sortie);


        int totalMurs = (int) (largeur * hauteur * pourcentageMurs / 100.0);

        placerMursAvecChemin(chemin, totalMurs);

    }


    /**
     * @return La grille du labyrinthe
     * @author Nathan Philippe
     */
    @Override
    public Cellule[][] getGrille() { return grille; }

    /**
     * @return La largeur du labyrinthe
     * @author Nathan Philippe
     */
    @Override
    public int getLargeur() { return largeur; }

    /**
     * @return La hauteur du labyrinthe
     * @author Nathan Philippe
     */
    @Override
    public int getHauteur() { return hauteur; }

    /**
     * @return La cellule d'entrée du labyrinthe
     * @author Nathan Philippe
     */
    @Override
    public Cellule getEntree() { return entree; }

    /**
     * @return La cellule de sortie du labyrinthe
     * @author Nathan Philippe
     */
    @Override
    public Cellule getSortie() { return sortie; }

    /**
     * @return Le pourcentage de murs dans le labyrinthe
     * @author Nathan Philippe
     */

    public double getPourcentageMurs() {
        return pourcentageMurs;
    }

    /**
     * Récupère l'étape associée au labyrinthe.
     * 
     * @return L'étape courante, peut être null si aucune étape n'est associée
     * @author Nathan Philippe
     */
    public Etape getEtape() {
        return e;
    }


    /**
     *
     * @return Cellule aleatoire de laquelle on peut acceder a la sortie
     */
    public Cellule positionAleaCell() {
        // Assure que la sortie existe et est libre
        if (sortie == null) {
            return null;
        }

        List<Cellule> accessibles = listAccesSortie();

        return choisiCellAlea(accessibles);
    }

    public List<Cellule> listAccesSortie(){
        boolean[][] visite = new boolean[hauteur][largeur];
        Queue<Cellule> file = new LinkedList<>();
        List<Cellule> accessibles = new ArrayList<>();

        file.add(sortie);
        visite[sortie.getY()][sortie.getX()] = true;

        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

        while (!file.isEmpty()) {
            Cellule c = file.poll();
            accessibles.add(c);

            for (int[] d : dirs) {
                int nx = c.getX() + d[0];
                int ny = c.getY() + d[1];
                if (nx >= 0 && nx < largeur && ny >= 0 && ny < hauteur) {
                    if (!visite[ny][nx] && !grille[ny][nx].estMur()) {
                        visite[ny][nx] = true;
                        file.add(grille[ny][nx]);
                    }
                }
            }
        }
        return accessibles;
    }

    public Cellule choisiCellAlea(List<Cellule> accessibles){
        // Choisir aléatoirement une cellule accessible différente de la sortie
        Random r = new Random();
        Cellule choisie;
        do {
            choisie = accessibles.get(r.nextInt(accessibles.size()));
        } while (choisie == sortie); // évite de placer l'entrée exactement sur la sortie
        return choisie;
    }


    public void sauvegarder(String fichier) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fichier))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ILabyrinthe charger(String fichier) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fichier))) {
            return (ILabyrinthe) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public boolean posValide(int x, int y) {
        return x >= 0 && x < largeur && y >= 0 && y < hauteur;
    }

    public static void main(String[] args) {
        Long temps = System.currentTimeMillis();
        ILabyrinthe test = new Labyrinthe(20,10,40);
        System.out.println("temps l=20 h=10 %=40:    " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new Labyrinthe(100,50,10);
        System.out.println("temps l=100 h=50 %=10:  " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new Labyrinthe(100,50,40);
        System.out.println("temps l=100 h=50 %=40:  " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new Labyrinthe(200,100,10);
        System.out.println("temps l=200 h=100 %=10: " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new Labyrinthe(200,100,40);
        System.out.println("temps l=200 h=100 %=40: " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new Labyrinthe(200,100,80);
        System.out.println("temps l=200 h=100 %=80:   " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new Labyrinthe(1000,500,40);
        System.out.println("temps l=1000 h=500 %=40:   " + (System.currentTimeMillis() - temps));
    }

}
