package J4.sae_labyrinthe.modele;

import java.io.*;
import java.util.*;

public class LabyrintheParfait implements ILabyrinthe, Serializable {
    private static final long serialVersionUID = 1L;

    private int largeur, hauteur; // nombre de cellules “jouables”
    private int gridLargeur, gridHauteur; // taille réelle de la grille avec murs
    private Cellule[][] grille;
    private Cellule entree, sortie;
    private Etape e;

    public LabyrintheParfait() {
        this(5, 5); // labyrinthe 5x5 jouable
    }

    public LabyrintheParfait(int largeur, int hauteur) {
        this(largeur, hauteur, new Etape(0));
    }

    public LabyrintheParfait(int largeur, int hauteur, Etape e) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.e = e;

        // taille réelle de la grille (ajout des murs)
        gridLargeur = largeur * 2 + 1;
        gridHauteur = hauteur * 2 + 1;

        // initialisation de la grille entière remplie de murs
        grille = new Cellule[gridHauteur][gridLargeur];
        for (int i = 0; i < gridHauteur; i++) {
            for (int j = 0; j < gridLargeur; j++) {
                grille[i][j] = new Cellule(j, i, true);
            }
        }

        // génération du labyrinthe
        generationLaby();

        entree = grille[1][1]; // coin haut-gauche (chemin)
        sortie = grille[gridHauteur - 2][gridLargeur - 2]; // coin bas-droit
    }

    private void generationLaby() {
        boolean[][] visite = new boolean[hauteur][largeur]; // cellules jouables
        Stack<int[]> pile = new Stack<>();
        Random r = new Random();

        int startX = 0, startY = 0;
        pile.push(new int[]{startX, startY});
        visite[startX][startY] = true;

        // Creuse la cellule de départ
        creuser(startX, startY);

        while (!pile.isEmpty()) {
            int[] c = pile.peek();
            int cx = c[0], cy = c[1];

            // voisins non visités
            List<int[]> voisins = new ArrayList<>();
            if (cx > 0 && !visite[cx - 1][cy]) voisins.add(new int[]{cx - 1, cy});
            if (cx < hauteur - 1 && !visite[cx + 1][cy]) voisins.add(new int[]{cx + 1, cy});
            if (cy > 0 && !visite[cx][cy - 1]) voisins.add(new int[]{cx, cy - 1});
            if (cy < largeur - 1 && !visite[cx][cy + 1]) voisins.add(new int[]{cx, cy + 1});

            if (voisins.isEmpty()) {
                pile.pop();
            } else {
                int[] v = voisins.get(r.nextInt(voisins.size()));
                int vx = v[0], vy = v[1];

                // Creuse le mur entre c et v
                creuserMur(cx, cy, vx, vy);

                visite[vx][vy] = true;
                pile.push(v);
            }
        }
    }


    private void creuser(int cx, int cy) {
        grille[cx * 2 + 1][cy * 2 + 1].setMur(false);
    }

    // -------------------------------------------------------------------------
    // Creuser le mur entre deux cellules jouables
    // -------------------------------------------------------------------------
    private void creuserMur(int x1, int y1, int x2, int y2) {
        int gx1 = x1 * 2 + 1, gy1 = y1 * 2 + 1;
        int gx2 = x2 * 2 + 1, gy2 = y2 * 2 + 1;

        // Creuser la cellule cible
        grille[gx2][gy2].setMur(false);

        // Creuser le mur entre les deux
        int mx = (gx1 + gx2) / 2;
        int my = (gy1 + gy2) / 2;
        grille[mx][my].setMur(false);
    }

    // -------------------------------------------------------------------------
    // Accès à la grille
    // -------------------------------------------------------------------------
    @Override
    public Cellule[][] getGrille() { return grille; }

    @Override
    public int getLargeur() { return gridLargeur; }

    @Override
    public int getHauteur() { return gridHauteur; }

    @Override
    public Cellule getEntree() { return entree; }

    @Override
    public Cellule getSortie() { return sortie; }

    @Override
    public Etape getEtape() { return e; }


    // -------------------------------------------------------------------------
    // Sauvegarde
    // -------------------------------------------------------------------------
    public void sauvegarder(String fichier) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fichier))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Place la sortie à une distance précise de l'entrée.
     * La distance est mesurée en nombre de pas (cellules adjacentes).
     * Choisit aléatoirement parmi les cellules à cette distance.
     */
    public void placerSortieA(int distance) {
        boolean[][] visite = new boolean[hauteur][largeur]; // cellules jouables
        Map<int[], Integer> distMap = new HashMap<>();
        Queue<int[]> file = new LinkedList<>();

        int startX = 0, startY = 0; // cellule de départ jouable
        file.add(new int[]{startX, startY});
        visite[startX][startY] = true;
        distMap.put(new int[]{startX, startY}, 0);

        List<int[]> candidats = new ArrayList<>();

        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

        while (!file.isEmpty()) {
            int[] c = file.poll();
            int cx = c[0], cy = c[1];
            int d = Math.abs(cx - startX) + Math.abs(cy - startY); // distance en pas
            if (d == distance) {
                candidats.add(c);
                continue;
            }

            for (int[] dir : dirs) {
                int nx = cx + dir[0];
                int ny = cy + dir[1];
                if (nx >= 0 && nx < hauteur && ny >= 0 && ny < largeur &&
                        !visite[nx][ny] && !grille[nx*2+1][ny*2+1].estMur()) {
                    visite[nx][ny] = true;
                    file.add(new int[]{nx, ny});
                }
            }
        }

        if (!candidats.isEmpty()) {
            Random r = new Random();
            int[] choix = candidats.get(r.nextInt(candidats.size()));
            sortie = grille[choix[0]*2+1][choix[1]*2+1]; // conversion en coordonnées de grille
            sortie.setMur(false);
        } else {
            System.out.println("Aucune cellule à cette distance.");
        }
    }

    @Override
    public Cellule positionAleaCell() {
        // TODO : cf Labyrinthe simple
        return null;
    }
    public boolean posValide(int x, int y) {
        return x >= 0 && x < largeur && y >= 0 && y < hauteur;
    }

    public static void main(String[] args) {
        Long temps = System.currentTimeMillis();
        ILabyrinthe test = new LabyrintheParfait(10,20);
        System.out.println("temps l=20 h=10:    " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new LabyrintheParfait(50,100);
        System.out.println("temps l=100 h=50:  " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new LabyrintheParfait(50,100);
        System.out.println("temps l=100 h=50:  " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new LabyrintheParfait(100,200);
        System.out.println("temps l=200 h=100: " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new LabyrintheParfait(100,200);
        System.out.println("temps l=200 h=100: " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new LabyrintheParfait(100,200);
        System.out.println("temps l=200 h=100:   " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new LabyrintheParfait(500,1000);
        System.out.println("temps l=1000 h=500:   " + (System.currentTimeMillis() - temps));
    }

}
