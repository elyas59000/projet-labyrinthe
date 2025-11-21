# Suivi du projet

## Semaine 1

> **Anas** : 
> - base du modèle creation des différentes interfaces avec les méthodes ainsi que les bases (attributs et constructeur) de chaque classe utile au modèle.
> 
> **Nathan** : 
> - Création du readMe avec la description du jeu, et des classes Defi, Cellule et Joueur ainsi que leur interface.
>
> **Clement** : 
> - Création des scènes suivantes avec SceneBuilder et de leur controleur : MenuPrincipal, RègleDuJeu
> - Mise en place de l'architecture du projet
> - Création de la classe outil SceneManager
> 
> **Elyas** : 
> - Création du controleur : ControleurJeu
> 
> **Adam** : 
> - mise en place partielle de joueur  
> - création du convertisseur de données

## Semaine 2

> **Anas** : 
> - Creation de la vue Du labyrinthe entier avec le controleur associé permettant de se déplacer en utilisant les touches zsqd ou les flèches directionnelles
>
> **Nathan** : 
> - Création du labyrinthe fonctionnel avec une largeur, une hauteur, un pourcentage de murs, et un chemin entre l'entrée et la sortie.
> - Ajout de fonctionnalité dans la classe Etape.
> - Remplissage de toutes les interfaces du model.
> - Ajout de la position du joueur et du déplacement dans la classe ModeleJeu.
>
> **Clement** : 
>  - Création des différentes vues suivante : Connexion, Inscription, Score, et de leur controleur.
>  - Mise en place du systeme de connexion à travers les classes suivantes : SaisieValidation, ValidationException et modification de la classe ModeleJeu pdans les metthode creerJoueur et connexionJoueur
>  - La connexion est maintenant aussi établie dans le modele, le text d'indication de connexion se met à jour dynamiquement 
>
> **Elyas** : 
>  - mise en place des tests
> 
> **Adam** : 
> - mise en place de SceneModeLibreControleur
> - animation des fonts quand le button est hover
> - mise en place du système de saves + fix de la vérification.
> - fix les problèmes de rendu du suivi
> - ajout de la javadoc

## Semaine 3

> **Anas** : 
> - Creation de la vue permettant l'affichage local avec une carte 
> - Ajout de texture pour les différents elements du labyrinthe pour pouvoir donner un thème et rendre le jeu plus beau
> - Creation d'une méthode pour les vues permettant de fusionner deux images avec une des deux étant le fond
> - ajout du tableau des scores
> - Correction de plusieurs problèmes apparus
> 
> **Nathan** : 
> - Ajout de séparateur pour la vue partiel (plus actif)
> - Ajout élement de sortie des labyrinthes
> - Ajout des étapes et défi dans la classe Joueur
> - Création des règles du jeu, et fonctionnement du bouton Règles
> - Ajout du retour au menu quand on a atteint la sortie Mode Libre
> - Création modèle modeProgression
> - Suppression élèments inutiles
> - Création position aléatoire du joueur pour l'Etape 3
> - Création de la page Modification du Profil 
> - Ajout d'un choix pour la range du Labyrinth Partiel
> 
> **Clement** : 
> - Amélioration de l'affichage pour créer une taille dynamique pour les labyrinthes avec la methodes calculerTailleOptimale dans la classe utils Calcul
> - Optimisation du code, suppression de la latence → on rafraîchit les cases qui ont changé, et non plus tout le labyrinthe.
> - Création des overlays de pause et fin de partie
>
> **Elyas** :
> - Ajout de méthode de test
> 
> 
> **Adam** :
> - Ajout des methodes de chiffrements (non fonctionnels)
> - Ajout du timer 
> - ajouts de styles

## Semaine 4 jusqu'au rendu final

> **Anas** : 
> - Creation du labyrinthe parfait : 
>  - Algorithme de creation du TP dev efficace avec un parcours en profondeur
>  - Nouvelle algorithme de creation avec Structure UnionFind
>  - Ajout de la méthode de distance minimale pour la sortie
> - Creation de la boutique permettant D'acheter des Bonus avec des points de scores
> - Création de l'inventaire permettant l'utilsation des Bonuse en jeu par le joueur
> - Correction des inversion de coordonées entre les deux labyrinthes
> - Mise en place des test de  performance pour les deux labyrinthes
> - Correction de divers problèmes et conflits survenu 
> - Realisation du diagramme de classe UML final
> 
> 
> **Nathan** : 
> - Création vue Labyrinthe Brouillard 
> - Gestion de controle de saisie
> - Ajout des étapes 4-5-6
> - Création d'un nouveau mode de Jeu:
>   - Le labyrinthe du jour, qui est composé d'un labyrinthe qui change chaque jour, et un score qui est le temps prix pour réaliser ce labyrinthe 
>   - Le labyrinthe est généré totalement aléatoirement, il est sérialisé ainsi que le score du jour
>   
> - Mise en place de la position de la sortie aléatoire
> - Amélioration importante de l'efficacité de la génération du labyrinthe Aléatoire
> - Ajout du labyrinthe Parfait dans le mode libre
> - Gestion des redirections des boutons quitter une partie et relancer
> - Création d'un nouveau mode de Jeu:
>   - Contre la montre, c'est un mode de jeux, avec un labyrinthe générer aléatoirement ou par vos propre paramètres
>   - Il y a un système de chronomètre qui une fois atteint arrête la partie sur une défaite du joueur
>   - Vous pouvez vraiment tout choisir sur les labyrinthes, son type, largeur, hauteur, % de murs, distance et le temps
> - Création de manière aléatoire des paramètres du Mode Contre la montre
> - Ajout de la distance pour le labyrinthe parfait dans le mode Libre
> - Mise en place de la position de la sortie aléatoire dans le labyrinthe Parfait
> 
> **Clément** :
> - Refonte complète sur notre système d'affichage :
>   - On passe d'un gridPane lourd à un canvas plus léger.
>   - Le joueur est desormais visuellement une ImageView qu'on déplace à partir d'un offset et de sa position = on ne rafraîchit que le sprite du joueur.
>   - Mise en place d'un systeme de Biome permettant une diversité dans les différents niveaux de la progression ou de choix de paramétrage du mode Libre
>     - on a donc pour le moment : Désert, <>
>   - Création donc d'un texture pack, en vue top-down pour simuler un semblant de 3D.
>     - Introduction à la logique de texture connectée en elle avec des angles
>     - Création d'une fonction de bit masking pour choisir quelles textures pour quels murs.
>     - Création d'une fonction de recolorisation à partir d'une palette stockée dans le biome courant.
>   - Optimisation des appels aux ressources via la gestion d'un cache qui charge tout dès le lancement
>   - Adaptation des nouvelles vues crée par mes camarades à ce nouveau systeme
>     - On a donc desormais 3 vues (entières, partielles, minimap) qui pour les 2 dernières prennent des paramètres pour modifier leur comportement (exemple : la minicarte peut afficher ou non le joueur ou/la sortie).
> - Correction du bug du laby-parfait (inversion du i,j dans new Cellule lors de la création de la grille)
> - Optimisation de l'affichage des vues, on peut charger des labys de 200*200 en moins de 1s.
> - Creation des autres biomes : plaine ,neige, foret,grotte,enfer
> - Creation d'une classe fog pour unifier le traitement du brouillard possible dans les vues entiere et minimap
> - Revue de code dans le controleur du labyrinthe : on a maintenant une attribution correcte de vues attendues pour chaque etape de la progression + on ne fait plus jamais 2 new...vue 1 seule instance pour chacune des vues -> diminue la charge processeur.
>
>**Elyas** : 
> - création de test pour le modéle et utils
> 
> 
> **Adam**:
> - création des Items
>   - Item neutre
>     - les cleanse ("nettoient" les effets des items) 
>   - Bonus
>     - PlusVision
>     - TP (à voir si on le met en item neutre, il peut TP en arrière dans le labyrinthe)
>     - Ward (pose une balise qui donne la vision)
>   - Malus
>     - MoinsVision
>     - TpDebut
>   - non-créé
>     - SentirSortie (donne la direction vers la sortie, avec le chemin sur le labyrinthe)
>     - BriseMur
>     - MixCtrl
> - décomposition de méthodes de Labyrinthe
> - ajout de styles
> - correction des fautes dans le suivi



