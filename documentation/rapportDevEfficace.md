# Investissement dans le projet

--- 
1. Nathan : 24 %
2. Clément : 24 %
3. Anas : 24 %
4. Elyas : 11 %
5. Adam : 17 %

--- 
# Structure de données pour les labyrinthes

Nous avons utilisé des matrices de Cellule (Un objet représentant une case) pour les 2 types de labyrinthes.
Nous n'avons pas utilisé la méthode du TP pour le labyrinthe parfait car nous voulions des labyrinthes interchangeables, c'est-à-dire avoir les mêmes méthodes/utilisation pour les 2 même labyrinthes
Ces structures de données se trouvent dans le package modèle et dans les classes Labyrinthe et LabyrintheParfait.

Nous avons également ajouté la structure de Données **UnionFind** afin d'ajouter un algorithme de genération pour le Labyrinthe. 
Nous utilisons **Union-Find** pour gérer les ensembles de cellules connectées. Cela permet de vérifier rapidement si deux cellules sont déjà reliées et de les fusionner si nécessaire.

--- 
# Algorithmes de génération

## Labyrinthe Aléatoire
### Localisation de l'algorithme
1. Classe : Labyrinthe
2. Méthode principale : genererLabyrinthe()
3. Méthodes auxiliaires :
- creerCellule()
- creerEntreeSortie()
- genererChemin()
- placerMursAvecChemin()


### Structure de données utilisées
1. File (Queue via LinkedList)
   - Utilisation : Dans la méthode 'genererChemin'. 
   - Justification : Indispensable pour effectuer un parcours en largeur (BFS).
   - Elle stocke les cellules à visiter selon le principe FIFO (First In, First
     Out), ce qui garantit l'exploration des voisins immédiats avant de
     s'éloigner. Cela assure de trouver un chemin valide sur la grille vide.

2. Liste dynamique (ArrayList)
   - Utilisation : Dans 'placerMursAvecChemin' pour stocker les murs candidats.
   - Justification : Nécessaire pour stocker un nombre variable de cellules
   éligibles (cellules qui ne sont ni entrée, ni sortie, ni sur le chemin).
   - Elle permet surtout l'utilisation de 'Collections.shuffle()' pour mélanger
   aléatoirement les candidats et garantir une distribution imprévisible
   des murs.


### Pseudo code


    1. INITIALISATION
    Créer une matrice "grille" de (hauteur x largeur)
    Initialiser toutes les cellules internes comme vides (non-mur)
    Marquer les cellules de bordure (périphérie) comme murs

    2. CHOIX DES POINTS
    Choisir aléatoirement une cellule "Entrée" (x, y)
    Choisir aléatoirement une cellule "Sortie" (x, y)
    Tant que la distance de Manhattan entre Entrée et Sortie est trop faible :
    Rechoisir Sortie

    3. RÉSERVATION DU CHEMIN (BFS)
    Créer une File "F" vide
    Créer un tableau "Visite" de booléens (faux partout)
    Créer un tableau "Parent" pour reconstruire le trajet

    Enfiler Entrée dans F
    Marquer Entrée dans Visite

    Tant que F n'est pas vide :
    Cellule C = défiler de F
    Si C est Sortie : Stopper la recherche

       Pour chaque Voisin V de C :
           Si V est dans la grille, non mur et non visité dans Visite :
               Marquer V dans Visite
               Parent[V] = C  // On mémorise d'où on vient
               Enfiler V dans F

    Reconstruire la liste "CheminGaranti" en remontant de Sortie vers Entrée via le tableau Parent

    4. PLACEMENT DES MURS ALÉATOIRES
    Créer une liste "Candidats" vide

    Pour chaque cellule C de la grille (hors bordures) :
    Si C n'est PAS dans "CheminGaranti" ET C ≠ Entrée ET C ≠ Sortie :
    Ajouter C à "Candidats"

    Mélanger aléatoirement la liste "Candidats" (Shuffle)
    Calculer NombreMurs = (largeur * hauteur * pourcentageMurs / 100)

    Pour i allant de 0 à NombreMurs (ou taille max de Candidats) :
    Cellule MurAlea = Candidats[i]
    Transformer MurAlea en MUR dans la grille

    Retourner grille



## Labyrinthe Parfait

Nous implémentons deux algorithmes de géneration pour le labyrinthe parfait :

- **L'Algorithme de parcours en profondeur vue en TP**
- **L'Algorithme de Fusions Aléatoire des Chemins (Kruskal)**


# Structures de données dans LabyrintheParfait

## Localisation de l'algorithme
- **Classe** : `LabyrintheParfait`
- **Méthode principale** : `generationFusionAleatoire()`,`generationLaby()`
- **Méthodes auxiliaires** :
    - `creuser(int cx, int cy)`
    - `creuserMur(int x1, int y1, int x2, int y2)`
    - `positionAleaCell()`
    - `listAccesSortie()`
    - `choisiCellAlea(List<Cellule> accessibles)`

---

## Structures de données utilisées

### 3. Pile (`Stack<int[]>`)
- **Utilisation** : Dans `generationLaby()` pour effectuer un parcours en profondeur (DFS) afin de générer le labyrinthe.
- **Justification** : Permet de revenir sur les cellules précédentes lorsqu’un chemin n’a plus de voisins non visités, ce qui est essentiel pour un DFS itératif.

### 4. Liste dynamique (`ArrayList`)
- **Utilisation** :
    - Stockage des voisins non visités dans `generationLaby()`.
    - Stockage des murs candidats dans `generationFusionAleatoire()`.
    - Stockage des cellules accessibles dans `listAccesSortie()`.
    - Stockage des cellules candidates pour la sortie dans `placerSortieA()`.
- **Justification** : Permet d’avoir un conteneur dynamique dont la taille varie selon le contexte. Permet aussi de mélanger aléatoirement les éléments via `Collections.shuffle()` pour générer des labyrinthes imprévisibles.

### 5. Union-Find (`UnionFind`)
- **Utilisation** : Dans `generationFusionAleatoire()` pour gérer la fusion des cellules lors de la suppression aléatoire des murs.
- **Justification** : Permet de vérifier rapidement si deux cellules appartiennent au même ensemble et d’unir des ensembles, garantissant que le labyrinthe reste parfait (connecté et sans cycles).

### Pseudo code de l'Algorithme de parcours en profondeur
     
     Visite = tableau à deux dimensions de booléens de même taille que le labyrinthe. Ce tableau
     permet savoir si une cellule a déjà été visitée ou non.
     Pile = une Pile, vide au départ, qui stocke la cellule atteinte par cet algorithme
     On choisit aléatoirement une cellule de départ, sous forme (ligne, colonne)
     On la met dans Pile, et on indique dans le tableau visite que cette cellule a été traitée.
     tant que Pile non vide
     soit (i,j) le premier élément de Pile
     on recherche les voisins non encore traités de (i,j)
     si il y en a
     soit (k,l) = un de ces voisins, choisi au hasard
     on détruit le mur entre (k,l) et la cellule (i,j)
     on indique dans Visite que (k,l) a été traitée
     on ajoute (k,l) à la Pile
     fin si
     sinon
     on retire le premier élément de Pile
     fin sinon
     fin tant que
     retourner grille


### Pseudo code de l'Algorithme de Fusions aléatoires des chemins

    Créer une matrice "grille" avec toutes les cellules initialisées comme murs
    Créer un tableau "cellules" de toutes les cellules jouables
    Marquer chaque cellule comme ouverte
    Créer une structure Union-Find "UF" pour toutes les cellules
    Lister tous les murs possibles entre deux cellules adjacentes dans une liste "murs"
    Mélanger aléatoirement la liste "murs"
    Pour chaque mur dans "murs" :
        Soit cellule1 et cellule2 séparées par ce mur
        Si UF.find(cellule1) ≠ UF.find(cellule2) :
            UF.union(cellule1, cellule2)
            Creuser le mur entre cellule1 et cellule2


--- 
# Efficacité
## Labyrinthe Aléatoire

| Largeur  | Hauteur    | Pourcentage de Murs | Temps (ms)  |
|----------|------------|---------------------|-------------|
| 20       | 10         | 40                  | 0           |
| 100      | 50         | 10                  | 4           |
| 100      | 50         | 40                  | 0           |
| 200      | 100        | 10                  | 12          |
| 200      | 100        | 40                  | 6           |
| 200      | 100        | 10                  | 8           |
| 1000     | 500        | 40                  | 70          |


### Voici le tableau de temps avec mon algorithme de la V1
Cette algorithme tester creer les murs puis a chaque creation de mur faisais un parcour du labyrinthe 
pour savoir si un chemin était possible.

| Largeur  | Hauteur    | Pourcentage de Murs | Temps (ms)  |
|----------|------------|---------------------|-------------|
| 20       | 10         | 40                  | 5           |
| 100      | 50         | 10                  | 76          |
| 100      | 50         | 40                  | 125         |
| 200      | 100        | 10                  | 639         |
| 200      | 100        | 40                  | 1588        |
| 200      | 100        | 10                  | 3015        |
| 1000     | 500        | 40                  | 1274247     |

On peut voir un changement énorme, il fallait absolument changé cet algorithme de génération.
Car il était vraiment très très long.(Surtout pour le 1000 * 500 comme on peut le voir)
## Labyrinthe Parfait

### Algorithme de parcours en profondeur

| Largeur | Hauteur | Distance | Temps (ms) |
|---------|---------|----------|------------|
| 20      | 10      | 15       | 1          |
| 100     | 50      | 70       | 6          |
| 200     | 100     | 150      | 16         |
| 1000    | 500     | 700      | 147        |

### Algorithme de Fusions aléatoires des chemins 

| Largeur | Hauteur | Distance | Temps (ms) |
|---------|---------|----------|------------|
| 20      | 10      | 15       | 1          |
| 100     | 50      | 70       | 6          |
| 200     | 100     | 150      | 19         |
| 1000    | 500     | 700      | 336        |



--- 
# Complément et Bilan

--- 
## Complément

--- 
### Effort sur l'éfficacité

- Amélioration IMPORTANTE de l'éfficacité de la génération du labyrinthe aléatoire (Classe Labyrinthe)
- Bonne gestion de l'initialisation des vues pour avoir une génération rapide en jeu (Classe des vues et SceneLabyrintheControleur)
- Gestion de la vue Partielle si le labyrinthe est trop grand (Classe SceneLabyrintheControleur)
- Utilisation d'un cache Memoire pour stocker les images (Classe CacheMemoire)

## Bilan 

### Difficulté rencontré

- Génération du labyrinthe aléatoire très longue pour la V1
- Beaucoup de très gros problèmes de merge
- Gerer les vues pour que le labyrinthe ne soit pas long a lancé en jeu

### Améliorations envisageables

- Decomposition des méthodes
- Des commentaires pour expliquer qu'est ce que font certaines méthodes
- Un meilleur rangement des méthodes dans les classes

--- 