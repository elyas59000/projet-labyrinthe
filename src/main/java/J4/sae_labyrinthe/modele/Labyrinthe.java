package J4.sae_labyrinthe.modele;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

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
     * @param largeur Largeur du labyrinthe
     * @param hauteur Hauteur du labyrinthe
     * @param pourcentageMurs Pourcentage de murs souhaité (entre 0 et 100)
     * @author Nathan Philippe
     */
    public Labyrinthe(int largeur, int hauteur, double pourcentageMurs) {
        this(largeur, hauteur, pourcentageMurs, null);
    }

    /**
     * @param largeur Largeur du labyrinthe
     * @param hauteur Hauteur du labyrinthe
     * @param pourcentageMurs Pourcentage de murs souhaité (entre 0 et 100)
     * @param e Étape associée au labyrinthe (peut être null)
     * @author Nathan Philippe
     */
    public Labyrinthe(int largeur, int hauteur, double pourcentageMurs, Etape e) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.pourcentageMurs = pourcentageMurs;
        this.e = e;
        grille = new Cellule[hauteur][largeur];
        genererLabyrinthe();
    }

    /**
     * Initialise la grille du labyrinthe avec des cellules vides.
     * @author Nathan Philippe
     */
    private void creerCellule() {
        // Création de toutes les cellules sans mur pour l'instant
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                grille[y][x] = new Cellule(x, y, false);
            }
        }
    }

    /**
     * Définit les cellules d'entrée et de sortie du labyrinthe.
     * Par défaut, l'entrée est en (0,0) et la sortie en (largeur-1, hauteur-1).
     * @author Nathan Philippe
     */
    private void creerEntreeSortie() {
        entree = grille[0][0];
        sortie = grille[hauteur - 1][largeur - 1];
        entree.setMur(false);
        sortie.setMur(false);
    }

    /**
     * Calcule toutes les positions possibles pour placer des murs.
     * Exclut l'entrée et la sortie du labyrinthe.
     * 
     * @return Liste des coordonnées [x,y] où des murs peuvent être placés
     * @author Nathan Philippe
     */
    private List<int[]> positionPossibles() {
        List<int[]> positions = new ArrayList<>();
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                if ((x != 0 || y != 0) && (x != largeur - 1 || y != hauteur - 1)) {
                    positions.add(new int[]{x, y});
                }
            }
        }
        return positions;
    }

    private void placerMurs(List<int[]> positions, int totalMurs){
        Random r = new Random();
        Collections.shuffle(positions, r);
        int mursPlace = 0;
        for (int[] pos : positions) {
            if (mursPlace >= totalMurs) break;
            grille[pos[1]][pos[0]].setMur(true);
            if (cheminExistant()) {
                mursPlace++;
            } else {
                // Si plus de chemin, retire le mur
                grille[pos[1]][pos[0]].setMur(false);
            }
        }
    }

    private void genererLabyrinthe() {
        creerCellule();
        creerEntreeSortie();

        int totalMurs = (int) (largeur * hauteur * pourcentageMurs / 100.0);
        placerMurs(positionPossibles(), totalMurs);

        nettoyerZones();
        positionSortieAlea();
        if (e != null && e.getNumero() == 3) {
            positionAlea();
        }

    }

    private void nettoyerZones() {
        boolean[][] visite = new boolean[hauteur][largeur];
        Queue<Cellule> file = new LinkedList<>();

        // On part de la sortie, qu'on sait accessible
        file.add(sortie);
        visite[sortie.getY()][sortie.getX()] = true;

        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

        // Marque toutes les cellules accessibles depuis la sortie
        while (!file.isEmpty()) {
            Cellule c = file.poll();
            c.setAccessible(true); // celles atteignables depuis la sortie sont accessibles
            for (int[] d : dirs) {
                int nx = c.getX() + d[0];
                int ny = c.getY() + d[1];
                if (nx >= 0 && nx < largeur && ny >= 0 && ny < hauteur) {
                    Cellule voisin = grille[ny][nx];
                    if (!visite[ny][nx] && !voisin.estMur()) {
                        visite[ny][nx] = true;
                        file.add(voisin);
                    }
                }
            }
        }

        // Les cellules non visitées (et non murées) deviennent inaccessibles
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                if (!grille[y][x].estMur() && !visite[y][x]) {
                    grille[y][x].setAccessible(false);
                }
            }
        }
    }
    private void regardeDirection(int x, int y, int[] dx, int[] dy, boolean[][] visite, Stack<int []> stack){
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx >= 0 && nx < largeur && ny >= 0 && ny < hauteur
                    && !grille[ny][nx].estMur() && !visite[ny][nx]) {
                stack.push(new int[]{nx, ny});
                visite[ny][nx] = true;
            }
        }
    }

    private boolean cheminExistant() {
        boolean[][] visite = new boolean[hauteur][largeur];
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{0, 0});
        visite[0][0] = true;

        // directions possibles
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        while (!stack.isEmpty()) {
            int[] p = stack.pop();
            int x = p[0], y = p[1];
            if (x == largeur - 1 && y == hauteur - 1) return true;

            regardeDirection(x,y,dx,dy,visite,stack);
        }
        return false;
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
     * Place l'entrée du labyrinthe à une position aléatoire accessible depuis la sortie.
     * Cette méthode garantit qu'il existe un chemin entre la nouvelle entrée et la sortie.
     * 
     * @author Nathan Philippe
     */
    public void positionAlea() {
        // Assure que la sortie existe et est libre
        if (sortie == null) {
            sortie = grille[hauteur - 1][largeur - 1];
            sortie.setMur(false);
        } else {
            sortie.setMur(false);
        }

        List<Cellule> accessibles = listAccesSortie();

        // Si seules la sortie (ou aucune cellule) est accessible, fallback sur (0,0)
        if (accessibles.size() <= 1) {
            this.entree = grille[0][0];
            this.entree.setMur(false);
            return;
        }

        this.entree = choisiCellAlea(accessibles);
        this.entree.setMur(false);
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


    public boolean estDansLabyrinthe(int i, int j) {
        return i >= 0 && i < getHauteur() && j >= 0 && j < getLargeur();
    }

    public void setSortie(Cellule sortie) {
        this.sortie = sortie;
    }

    public void setEntree(Cellule entree) {
        this.entree = entree;
    }

    public void setGrille(Cellule[][] grilleSimple) {
        this.grille = grilleSimple;
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


    /**
     * Place la sortie dans une position aléatoire accessible depuis l'entrée.
     * Ne doit jamais être placée sur la même cellule que l'entrée.
     *
     */
    public void positionSortieAlea() {

        // On fait un BFS simple depuis l'entrée
        boolean[][] visite = new boolean[hauteur][largeur];
        Queue<Cellule> file = new LinkedList<>();
        List<Cellule> accessibles = new ArrayList<>();

        file.add(entree);
        visite[entree.getY()][entree.getX()] = true;

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

        // si entrée seule accessible → sortie classique
        if (accessibles.size() <= 1) {
            sortie = grille[hauteur - 1][largeur - 1];
            sortie.setMur(false);
            System.out.println("Sortie placée par défaut en : (" + sortie.getX() + ", " + sortie.getY() + ")");
            return;
        }

        // choix aléatoire ≠ entrée
        Random r = new Random();
        Cellule choisie;
        do {
            choisie = accessibles.get(r.nextInt(accessibles.size()));
        } while (choisie == entree);

        sortie = choisie;
        sortie.setMur(false);
        System.out.println("DEBUG: Mur avant nettoyage = " + sortie.estMur());
        System.out.println("Sortie placée aléatoirement en : (" + sortie.getX() + ", " + sortie.getY() + ")");
    }



}
