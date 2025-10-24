package J4.sae_labyrinthe.modele;

import J4.sae_labyrinthe.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import J4.sae_labyrinthe.utils.Save;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestLabyrinthe {
    private Labyrinthe labyrinthe;
    private IModeleJeu modele;
    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueur3;
    @BeforeEach
    void setUp() {
        modele = new ModeleJeu();
        labyrinthe = new Labyrinthe(15, 20, 0);
        joueur1 = new Joueur("j1","mdp1");
        joueur2 = new Joueur("j2","mdp2");
        joueur3 = new Joueur("j3","mdp3");
    }

    @Test
    void testCreationLabyrinth_Dimensions() {
        assertEquals(15, labyrinthe.getLargeur(), "La largeur devrait être de 15.");
        assertEquals(20, labyrinthe.getHauteur(), "La hauteur devrait être de 20.");

    }

    @Test
    void testCreerJoueur() throws ValidationException {
        Joueur joueur_creer = new Joueur("a","b");
        assertNotNull(joueur_creer);
        assertEquals("a",joueur_creer.getNom());
        assertEquals("b",joueur_creer.getMdp());
    }


    @Test
    void testDeplacementJoueur() throws IOException {
        modele.lancerPartieLibre(15,20,0);
        assertEquals(0,((ModeleJeu)modele).getPositionJoueur().getX());
        assertEquals(0,((ModeleJeu)modele).getPositionJoueur().getY());

        modele.deplacerJoueur("d");
        Cellule nouvellePosition = ((ModeleJeu) modele).getPositionJoueur();

        assertEquals(1,nouvellePosition.getX(),"la position X devrait avoir augmenter de 1");
        assertEquals(0,nouvellePosition.getY(),"la position Y ne devrait pas augmenter");
    }

    @Test
    void testDeconnexionJoueur() throws ValidationException, IOException {
        modele.creerJoueur("a","b");
        modele.connexionJoueur("a","b");
        assertNotNull(((ModeleJeu)modele).getJoueurActuel());
        ((ModeleJeu) modele).deconnexionJoueur();
        assertNull(((ModeleJeu)modele).getJoueurActuel());
    }

    @Test
    void testScore() throws ValidationException {
        IJoueur joueur = new Joueur("nom","mpd");
        assertEquals(0,joueur.getScore());
        joueur.ajouterScore(15);
        assertEquals(15,joueur.getScore());

    }

    @Test
    void testSauvegardeEtChargement() throws IOException {
        joueur2.ajouterScore(10);
        ArrayList<Joueur> liste = new ArrayList<>();
        liste.add(joueur1);
        liste.add(joueur2);
        liste.add(joueur3);
        Save.saveToFile(liste);
        ArrayList<Joueur> joueur_lue = Save.loadSave();
        assertNotNull(joueur_lue);
        assertEquals(liste.size(),joueur_lue.size());
        assertEquals(liste.get(0).getNom(),joueur_lue.get(0).getNom());
        assertEquals(liste.get(1).getScore(),joueur_lue.get(1).getScore());
    }

    @Test
    void testGenerationLabyrinth(){
        Labyrinthe laby2 = new Labyrinthe(10,10,0);
        boolean conditon = (laby2.getEntree().getX() == 0 & laby2.getEntree().getY() == 0);
        assertTrue(conditon);

    }

    @Test
    void testDeplacementJoueurCollison() throws IOException {

        modele.lancerPartieLibre(15, 20, 100);
        Cellule positionInitiale = ((ModeleJeu) modele).getPositionJoueur();

        assertEquals(0, positionInitiale.getX());
        assertEquals(0, positionInitiale.getY());
        modele.deplacerJoueur("d");


        Cellule positionFinale = ((ModeleJeu) modele).getPositionJoueur();

        assertEquals(0, positionFinale.getX(), "Position X n'aurait pas dû changer (collision mur).");
        assertEquals(0, positionFinale.getY(), "Position Y n'aurait pas dû changer (collision mur).");
    }




}
