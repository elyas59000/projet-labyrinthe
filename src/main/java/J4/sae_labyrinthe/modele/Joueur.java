package J4.sae_labyrinthe.modele;

import java.io.*;
import java.util.Map;

public class Joueur implements IJoueur, Serializable {
    private String nom;
    private int etapeMax;

    private int etapeActuelle;
    private int score;

    public Joueur(String nom){
        this.nom = nom;
        this.etapeMax = 1;
        this.etapeActuelle = 1;
        this.score = 0;
    }

    public String getNom() {
        return this.nom;
    }

    public int getEtapeActuelle() {
        return this.etapeActuelle;
    }

    public int getEtapeMax() {
        return this.etapeMax;
    }

    public int getScore() {
        return this.score;
    }

    public void ajouterScore(int ajout) {
        this.score += ajout;
    }

    public void avancerEtape() {
        ++this.etapeActuelle;
        if (this.etapeActuelle > this.etapeMax) {
            this.etapeMax = this.etapeActuelle;
        }
    }

    public void reculerEtape() {
        if (this.etapeActuelle > 1) {
            --this.etapeActuelle;
        }
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
            System.out.println("Compte sauvegardé dans " + filename);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde du Compte : " + e.getMessage());
        }
    }
    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = ois.readObject();
            if (obj instanceof Joueur) {
                this.nom = ((Joueur) obj).nom;
                this.etapeActuelle = ((Joueur) obj).etapeActuelle;
                this.score = ((Joueur) obj).score;
                this.etapeMax = ((Joueur) obj).etapeMax;
                System.out.println("Compte chargé depuis " + filename);
            } else {
                System.err.println("Le fichier ne contient pas un Compte valide.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement de l'historique : " + e.getMessage());
        }
    }


}
