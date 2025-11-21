package J4.sae_labyrinthe.modele;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import J4.sae_labyrinthe.utils.UnionFind;

public class LabyrintheParfait implements ILabyrinthe, Serializable {
    private static final long serialVersionUID = 1L;

    private int largeur, hauteur; // nombre de cellules “jouables”
    private int gridLargeur, gridHauteur;
    private Cellule[][] grille;
    private Cellule entree, sortie;
    private Etape e;
    int distance;

    public LabyrintheParfait() {
        this(5, 5,5); // labyrinthe 5x5 jouable
    }

    public LabyrintheParfait(int largeur, int hauteur,int distance) {
        this(largeur, hauteur, new Etape(0),distance);
    }

    public LabyrintheParfait(int largeur, int hauteur, Etape e,int distance) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.e = e;
        this.distance = distance;
        gridLargeur = largeur * 2 + 1;
        gridHauteur = hauteur * 2 + 1;

        // initialisation de la grille entière remplie de murs
        initGrid();
        // génération du labyrinthe
        generationFusionAleatoire();

        creerEntree();
        sortie = grille[gridHauteur / 2 - 1][gridLargeur / 2- 1];
        placerSortieA(distance);
    }

    private void creerEntree() {
        Random r = new Random();
        int entreeX = r.nextInt(largeur);
        int entreeY = r.nextInt(hauteur);
        entree = grille[entreeY* 2 + 1][entreeX* 2 + 1];
    }

    private void initGrid() {
        grille = new Cellule[gridHauteur][gridLargeur];
        for (int i = 0; i < gridHauteur; i++) {
            for (int j = 0; j < gridLargeur; j++) {
                grille[i][j] = new Cellule(j, i, true);
            }
        }
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
        Queue<int[]> file = new LinkedList<>();
        List<Cellule> accessibles = new ArrayList<>();

        // Convertir la sortie (coordonnées grille) en coordonnées jouables
        int startX = (sortie.getX() - 1) / 2;
        int startY = (sortie.getY() - 1) / 2;
        visite[startY][startX] = true;
        file.add(new int[]{startX, startY});

        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

        while (!file.isEmpty()) {
            int[] c = file.poll();
            int cx = c[0], cy = c[1];

            // Ajouter la cellule correspondante dans la grille (conversion en coords grille)
            accessibles.add(grille[cy * 2 + 1][cx * 2 + 1]);

            for (int[] d : dirs) {
                int nx = cx + d[0];
                int ny = cy + d[1];
                if (nx >= 0 && nx < largeur && ny >= 0 && ny < hauteur) {
                    // vérifier le mur entre (cx,cy) et (nx,ny) en coordonnées grille
                    int mx = cx * 2 + 1 + d[0];
                    int my = cy * 2 + 1 + d[1];
                    if (!visite[ny][nx] && !grille[my][mx].estMur()) {
                        visite[ny][nx] = true;
                        file.add(new int[]{nx, ny});
                    }
                }
            }
        }
        return accessibles;
    }

    public Cellule choisiCellAlea(List<Cellule> accessibles){
        // Choisir aléatoirement une cellule accessible différente de la sortie
        Random r = new Random();
        if (accessibles == null || accessibles.isEmpty()) return null;
        if (accessibles.size() == 1) return accessibles.get(0);

        Cellule choisie;
        do {
            choisie = accessibles.get(r.nextInt(accessibles.size()));
        } while (choisie == sortie && accessibles.size() > 1);
        return choisie;
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
    private void generationFusionAleatoire() {
        // Chaque cellule jouable devient un "noeud" dans l'UnionFind
        UnionFind uf = new UnionFind(hauteur * largeur);

        // Liste de tous les murs séparant deux cellules adjacentes
        List<int[][]> murs = new ArrayList<>();

        for (int x = 0; x < hauteur; x++) {
            for (int y = 0; y < largeur; y++) {
                // Mur vertical (avec cellule à droite)
                if (y < largeur - 1) {
                    murs.add(new int[][]{{x, y}, {x, y + 1}});
                }
                // Mur horizontal (avec cellule en bas)
                if (x < hauteur - 1) {
                    murs.add(new int[][]{{x, y}, {x + 1, y}});
                }
            }
        }

        // Mélange aléatoire des murs
        Collections.shuffle(murs, new Random());

        for (int[][] mur : murs) {
            int x1 = mur[0][0], y1 = mur[0][1];
            int x2 = mur[1][0], y2 = mur[1][1];

            int id1 = x1 * largeur + y1;
            int id2 = x2 * largeur + y2;

            // Si les deux cellules ne sont pas encore connectées, supprimer le mur
            if (uf.find(id1) != uf.find(id2)) {
                uf.union(id1, id2);

                // Creuser les cellules et le mur entre elles
                grille[x1 * 2 + 1][y1 * 2 + 1].setMur(false);
                grille[x2 * 2 + 1][y2 * 2 + 1].setMur(false);

                int mx = (x1 * 2 + 1 + x2 * 2 + 1) / 2;
                int my = (y1 * 2 + 1 + y2 * 2 + 1) / 2;
                grille[mx][my].setMur(false);
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

    public int getDistance() {
        return distance;
    }

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
    public void placerSortieA(int distanceVoulu) {

        int[][] dist = new int[hauteur][largeur];
        for (int i = 0; i < hauteur; i++) {
            Arrays.fill(dist[i], -1);
        }

        Queue<int[]> file = new LinkedList<>();
        int startX = (this.entree.getX()-1)/2;
        int startY = (this.entree.getY()-1)/2;
        dist[startY][startX] = 0;
        file.add(new int[]{startX, startY});

        List<int[]> candidats = new ArrayList<>();

        int[] maxDistancePoint = new int[]{startX, startY};
        int maxDist = 0;

        // Directions cardinales
        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

        while (!file.isEmpty()) {
            int[] c = file.poll();
            int cx = c[0], cy = c[1];

            if (dist[cy][cx] > maxDist) {
                maxDist = dist[cy][cx];
                maxDistancePoint = c;
            }

            for (int[] dir : dirs) {
                int nx = cx + dir[0];
                int ny = cy + dir[1];

                if (nx < 0 || nx >= largeur || ny < 0 || ny >= hauteur) continue;



                // Coordonnées du mur dans la grande grille
                int mx = (cx * 2 + 1) + dir[0];
                int my = (cy * 2 + 1) + dir[1];

                // Vérification : le mur doit être dans la grille complète
                if (mx < 0 || mx >= gridHauteur || my < 0 || my >= gridLargeur) continue;

                // Si mur → pas de passage
                if (grille[mx][my].estMur()) continue;

                if (dist[ny][nx] == -1) {
                    dist[ny][nx] = dist[cy][cx] + 1;
                    file.add(new int[]{nx, ny});

                    if (dist[ny][nx] >= distanceVoulu) {
                        candidats.add(new int[]{nx, ny});
                    }
                }
            }
        }

        int[] choix;

        if (!candidats.isEmpty()) {
            Random r = new Random();
            choix = candidats.get(r.nextInt(candidats.size()));
        } else {
            System.out.println("Aucune cellule trouvée à cette distance exacte.");
            choix = maxDistancePoint;
        }

        sortie = grille[choix[1] * 2 + 1][choix[0] * 2 + 1];
        sortie.setMur(false);
    }

    public boolean posValide(int x, int y) {
        return x >= 0 && x < largeur && y >= 0 && y < hauteur;
    }

    public static void main(String[] args) {
        Long temps = System.currentTimeMillis();
        ILabyrinthe test = new LabyrintheParfait(10,20,15);
        System.out.println("temps l=20 h=10 d=15:    " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new LabyrintheParfait(50,100,70);
        System.out.println("temps l=100 h=50 d=70:  " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new LabyrintheParfait(50,100,70);
        System.out.println("temps l=100 h=50 d=70:  " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new LabyrintheParfait(100,200,150);
        System.out.println("temps l=200 h=100 d=150: " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new LabyrintheParfait(100,200,150);
        System.out.println("temps l=200 h=100 d=150: " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new LabyrintheParfait(100,200,150);
        System.out.println("temps l=200 h=100 d=150:   " + (System.currentTimeMillis() - temps));
        temps = System.currentTimeMillis();
        test = new LabyrintheParfait(500,1000,700);
        System.out.println("temps l=1000 h=500 d=700:   " + (System.currentTimeMillis() - temps));
    }

}
