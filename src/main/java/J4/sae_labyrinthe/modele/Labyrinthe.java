package J4.sae_labyrinthe.modele;

import java.util.*;

public class Labyrinthe implements ILabyrinthe{
    private int largeur;
    private int hauteur;
    private double pourcentageMurs;
    private Cellule[][] grille;
    private Cellule entree;
    private Cellule sortie;

    private Etape e;

    public Labyrinthe(int largeur, int hauteur, double pourcentageMurs) {
        this(largeur, hauteur, pourcentageMurs, null);
    }

    public Labyrinthe(int largeur, int hauteur, double pourcentageMurs,Etape e) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.pourcentageMurs = pourcentageMurs;
        this.e = e;
        grille = new Cellule[hauteur][largeur];
        genererLabyrinthe();
    }

    private void creerCellule(){
        // Création de toutes les cellules sans mur pour l'instant
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                grille[y][x] = new Cellule(x, y, false);
            }
        }
    }

    private void creerEntreeSortie(){
        entree = grille[0][0];
        sortie = grille[hauteur - 1][largeur - 1];
        entree.setMur(false);
        sortie.setMur(false);
    }

    private List <int[]> positionPossibles(){
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

        nettoyerZonesInaccessibles();

        if (e != null && e.getNumero() == 3) {
            positionAlea();
        }
    }

    private void nettoyerZonesInaccessibles() {
        boolean[][] visite = new boolean[hauteur][largeur];
        Queue<Cellule> file = new LinkedList<>();

        file.add(sortie);
        visite[sortie.getY()][sortie.getX()] = true;

        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

        while (!file.isEmpty()) {
            Cellule c = file.poll();
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

        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                if (!visite[y][x]) {
                    grille[y][x].setMur(true);
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

    public Cellule[][] getGrille() { return grille; }
    public int getLargeur() { return largeur; }
    public int getHauteur() { return hauteur; }
    public Cellule getEntree() { return entree; }
    public Cellule getSortie() { return sortie; }

    public double getPourcentageMurs() {
        return pourcentageMurs;
    }

    public Etape getEtape() {
        return e;
    }

    public void positionAlea() {
        // Assure que la sortie existe et est libre
        if (sortie == null) {
            sortie = grille[hauteur - 1][largeur - 1];
            sortie.setMur(false);
        } else {
            sortie.setMur(false);
        }

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

        // Si seules la sortie (ou aucune cellule) est accessible, fallback sur (0,0)
        if (accessibles.size() <= 1) {
            this.entree = grille[0][0];
            this.entree.setMur(false);
            return;
        }

        // Choisir aléatoirement une cellule accessible différente de la sortie
        Random r = new Random();
        Cellule choisie;
        do {
            choisie = accessibles.get(r.nextInt(accessibles.size()));
        } while (choisie == sortie); // évite de placer l'entrée exactement sur la sortie

        this.entree = choisie;
        this.entree.setMur(false);
    }


}
