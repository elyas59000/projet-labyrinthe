# Investissement dans le projet

--- 
1. Nathan : 22 %
2. Clément : 22 %
3. Anas : 22 %
4. Elyas : 14 %
5. Adam : 20 %

--- 
# Structure de données pour les labyrinthes

Nous avons utilisé des matrices de Cellule (Un objet représentant une case) pour les 2 types de labyrinthes.
Nous n'avons pas utilisé la méthode du TP pour le labyrinthe parfait car nous voulions des labyrinthes interchangeables, c'est-à-dire avoir les mêmes méthodes/utilisation pour les 2 même labyrinthes
Ces structures de données se trouvent dans le package modèle et dans les classes Labyrinthe et LabyrintheParfait.

Nous avons également ajouté la structure de Données **UnionFind** afin d'ajouter un algorithme de genération pour le Labyrinthe. 
Nous utilisons **Union-Find** pour gérer les ensembles de cellules connectées. Cela permet de vérifier rapidement si deux cellules sont déjà reliées et de les fusionner si nécessaire.

--- 
# Algorithmes de génération



## Labyrinthe Parfait

Nous implémentons deux algorithmes de géneration pour le labyrinthe parfait :

- **L'Algorithme de parcours en profondeur vue en TP**
- **L'Algorithme de Fusions Aléatoire des Chemins (Kruskal)**

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

## Labyrinthe Parfait

### Algorithme de parcours en profondeur

| Largeur | Hauteur | Distance | Temps (ms) |
|---------|----------|-----------|------------|
| 20      | 10       | 15        | 1          |
| 100     | 50       | 70        | 6          |
| 200     | 100      | 150       | 16         |
| 1000    | 500      | 700       | 147        |

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


--- 