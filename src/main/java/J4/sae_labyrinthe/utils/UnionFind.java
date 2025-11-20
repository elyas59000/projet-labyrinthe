package J4.sae_labyrinthe.utils;

public class UnionFind {

    private int[] parent;
    private int[] rank;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;   // Chaque élément est sa propre racine
            rank[i] = 0;
        }
    }

    // Trouve la racine avec compression de chemin
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // Compression
        }
        return parent[x];
    }

    // Fusionne deux ensembles (retourne true si fusion, false si déjà connectés)
    public boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) return false;  // déjà connectés

        // Union by rank pour maintenir l'arbre petit
        if (rank[rootA] < rank[rootB]) {
            parent[rootA] = rootB;
        } else if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
        } else {
            parent[rootB] = rootA;
            rank[rootA]++;
        }

        return true;
    }
}
