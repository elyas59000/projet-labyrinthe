package J4.sae_labyrinthe.utils;

import J4.sae_labyrinthe.modele.Joueur;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ChiffrementTest {

    @Test
    void testChiffrementDechiffrementJoueurs() throws Exception {

        String nomOriginal = "JoueurTest";
        String mdpOriginal = "MotDePasseSecret123!";

        Joueur joueur = new Joueur(nomOriginal, mdpOriginal);
        ArrayList<Joueur> listeJoueurs = new ArrayList<>();
        listeJoueurs.add(joueur);

        Chiffrement.chiffrer(listeJoueurs);

        assertNotEquals(nomOriginal, joueur.getNom(), "Le nom devrait être chiffré");
        assertNotEquals(mdpOriginal, joueur.getMdp(), "Le mot de passe devrait être chiffré");

        assertFalse(joueur.getNom().contains("Joueur"), "Le nom chiffré ne doit pas être lisible");

        Chiffrement.dechiffrer(listeJoueurs);

        assertEquals(nomOriginal, joueur.getNom(), "Le nom déchiffré doit correspondre à l'original");
        assertEquals(mdpOriginal, joueur.getMdp(), "Le mot de passe déchiffré doit correspondre à l'original");
    }

    @Test
    void testChiffrementDechiffrementFichier(@TempDir Path tempDir) throws IOException {
        File fichierTest = tempDir.resolve("test_secret.txt").toFile();
        String contenuOriginal = "Ceci est un contenu secret dans un fichier.";

        Files.writeString(fichierTest.toPath(), contenuOriginal);

        String cheminFichier = fichierTest.getAbsolutePath();
        Chiffrement.chiffrer(cheminFichier);

        byte[] contenuChiffreBytes = Files.readAllBytes(fichierTest.toPath());
        String contenuChiffreString = new String(contenuChiffreBytes, StandardCharsets.UTF_8);

        assertNotEquals(contenuOriginal, contenuChiffreString, "Le contenu du fichier devrait être modifié");
        assertFalse(contenuChiffreString.contains("secret"), "Le mot 'secret' ne devrait plus être lisible");

        Chiffrement.dechiffrer(cheminFichier);

        String contenuRestaure = Files.readString(fichierTest.toPath());
        assertEquals(contenuOriginal, contenuRestaure, "Le fichier déchiffré doit retrouver son contenu d'origine");
    }


    @Test
    void testByteToString() {
        String text = "Bonjour";
        byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);

        String result = Chiffrement.byteToString(textBytes);

        assertEquals(text, result);
    }

    @Test
    void testChiffrementNonDeterministe() throws Exception {
        Joueur j1 = new Joueur("MemeNom", "MemeMdp");
        Joueur j2 = new Joueur("MemeNom", "MemeMdp");

        ArrayList<Joueur> liste1 = new ArrayList<>();
        liste1.add(j1);

        ArrayList<Joueur> liste2 = new ArrayList<>();
        liste2.add(j2);

        Chiffrement.chiffrer(liste1);
        Chiffrement.chiffrer(liste2);

        assertNotEquals(j1.getNom(), j2.getNom(), "Deux chiffrements successifs du même texte devraient être différents (IV)");
        assertNotEquals(j1.getMdp(), j2.getMdp(), "Deux chiffrements successifs du même texte devraient être différents (IV)");

        Chiffrement.dechiffrer(liste1);
        Chiffrement.dechiffrer(liste2);

        assertEquals("MemeNom", j1.getNom());
        assertEquals("MemeNom", j2.getNom());
    }
}
