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

### Pseudo code de l'Algorithme de Fusions aléatoires des chemins


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