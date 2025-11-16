package J4.sae_labyrinthe.modele;

import java.io.*;
import java.util.*;

public class LabyrintheParfait implements ILabyrinthe, Serializable  {
    private static final long serialVersionUID = 1L;
    private int largeur, hauteur;
    private Cellule[][] grille;
    private Cellule[][] murVerticaux, murHorizontaux;
    private Cellule entree, sortie;
    private Etape e;

    public LabyrintheParfait() {
        this(4, 5);
    }

    public LabyrintheParfait(int largeur, int hauteur) {
        this(largeur,hauteur,new Etape(0));
    }

    public LabyrintheParfait(int largeur,int hauteur,Etape e) {
        this.largeur = largeur;
        this.hauteur = hauteur;

        // Initialisation de la grille principale
        this.grille = new Cellule[hauteur][largeur];
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                grille[i][j] = new Cellule(i, j, false);
            }
        }

        // Murs verticaux et horizontaux
        this.murVerticaux = new Cellule[largeur - 1][hauteur];
        this.murHorizontaux = new Cellule[hauteur - 1][largeur];

        for (int i = 0; i < murVerticaux.length; i++) {
            for (int j = 0; j < murVerticaux[i].length; j++) {
                murVerticaux[i][j] = new Cellule(i, j, true);
            }
        }

        for (int i = 0; i < murHorizontaux.length; i++) {
            for (int j = 0; j < murHorizontaux[i].length; j++) {
                murHorizontaux[i][j] = new Cellule(i, j, true);
            }
        }

        genereLabyParfait();

        // Définir l’entrée et la sortie (par défaut : coin haut-gauche et bas-droit)
        this.entree = grille[0][0];
        this.sortie = grille[hauteur - 1][largeur - 1];

        this.e = e;
    }

    // --- Implémentation de l'interface ILabyrinthe ---
    @Override
    public Cellule[][] getGrille() {
        return grille;
    }

    @Override
    public int getLargeur() {
        return largeur;
    }

    @Override
    public int getHauteur() {
        return hauteur;
    }

    @Override
    public Cellule getEntree() {
        return entree;
    }

    @Override
    public Cellule getSortie() {
        return sortie;
    }

    @Override
    public Etape getEtape() {
        return e;
    }

    // --- Fonctions internes ---
    public boolean estMur(int ligne, int colonne, int ligne1, int colonne1) {
        if (!adjacent(ligne, colonne, ligne1, colonne1))
            throw new RuntimeException("Cellules non adjacentes");

        if (!positionCorrecte(ligne, colonne) || !positionCorrecte(ligne1, colonne1))
            return true;

        if (colonne == colonne1) {
            return murHorizontaux[Math.min(ligne, ligne1)][colonne].estMur();
        }
        if (ligne == ligne1) {
            return murVerticaux[Math.min(colonne, colonne1)][ligne].estMur();
        }
        return true;
    }

    private void enleverMur(int ligne, int colonne, int ligne1, int colonne1) {
        if (!adjacent(ligne, colonne, ligne1, colonne1))
            throw new RuntimeException("Cellules non adjacentes");

        if (colonne == colonne1) {
            murHorizontaux[Math.min(ligne, ligne1)][colonne].setMur(false);
        } else if (ligne == ligne1) {
            murVerticaux[Math.min(colonne, colonne1)][ligne].setMur(false);
        }
    }

    private boolean positionCorrecte(int ligne, int colonne) {
        return ligne >= 0 && ligne < hauteur && colonne >= 0 && colonne < largeur;
    }

    private boolean adjacent(int ligne, int colonne, int ligne1, int colonne1) {
        return (ligne == ligne1 && Math.abs(colonne - colonne1) == 1)
                || (colonne == colonne1 && Math.abs(ligne - ligne1) == 1);
    }

    public List<Cellule> voisins(int ligne, int colonne) {
        List<Cellule> v = new ArrayList<>();
        if (ligne > 0) v.add(grille[ligne - 1][colonne]);
        if (ligne < this.hauteur - 1) v.add(grille[ligne + 1][colonne]);
        if (colonne > 0) v.add(grille[ligne][colonne - 1]);
        if (colonne < this.largeur - 1) v.add(grille[ligne][colonne + 1]);
        return v;
    }

    private void genereLabyParfait() {
        boolean[][] visite = new boolean[this.hauteur][this.largeur];
        Stack<Cellule> pile = new Stack<>();
        Random alea = new Random();

        int ligne = alea.nextInt(this.hauteur);
        int colonne = alea.nextInt(this.largeur);

        pile.push(grille[ligne][colonne]);
        visite[ligne][colonne] = true;

        while (!pile.isEmpty()) {
            Cellule courante = pile.peek();
            int i = courante.getX();
            int j = courante.getY();

            List<Cellule> pasTraitees = new ArrayList<>();
            for (Cellule c : voisins(i, j)) {
                if (!visite[c.getX()][c.getY()]) {
                    pasTraitees.add(c);
                }
            }

            if (!pasTraitees.isEmpty()) {
                Cellule voisine = pasTraitees.get(alea.nextInt(pasTraitees.size()));
                enleverMur(i, j, voisine.getX(), voisine.getY());
                visite[voisine.getX()][voisine.getY()] = true;
                pile.push(voisine);
            } else {
                pile.pop();
            }
        }
    }

    public boolean peutAller(int ligne, int colonne, int ligne1, int colonne1) {
        if (!adjacent(ligne, colonne, ligne1, colonne1))
            return false;
        return !estMur(ligne, colonne, ligne1, colonne1);
    }


    public Cellule[][] getMurHorizontaux() { return murHorizontaux; }
    public Cellule[][] getMurVerticaux() { return murVerticaux; }

    public void placerSortieA(int distance) {
        boolean[][] visite = new boolean[hauteur][largeur];
        Map<Cellule, Integer> dist = new HashMap<>();
        Queue<Cellule> queue = new LinkedList<>();

        // Initialisation
        queue.add(entree);
        visite[entree.getX()][entree.getY()] = true;
        dist.put(entree, 0);

        List<Cellule> candidats = new ArrayList<>();

        while (!queue.isEmpty()) {
            Cellule c = queue.poll();
            int d = dist.get(c);

            if (d == distance) {
                candidats.add(c);
                continue; // on ne propage pas au-delà
            }

            for (Cellule v : voisins(c.getX(), c.getY())) {
                if (!visite[v.getX()][v.getY()] && peutAller(c.getX(), c.getY(), v.getX(), v.getY())) {
                    visite[v.getX()][v.getY()] = true;
                    dist.put(v, d + 1);
                    queue.add(v);
                }
            }
        }

        if (!candidats.isEmpty()) {
            // Choisir aléatoirement parmi les candidats
            sortie = candidats.get(new Random().nextInt(candidats.size()));
        } else {
            // Si aucune cellule à cette distance, on garde la sortie par défaut
            System.out.println("Impossible de placer la sortie à " + distance + " cases.");
        }
    }

    public void sauvegarder(String fichier) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fichier))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

