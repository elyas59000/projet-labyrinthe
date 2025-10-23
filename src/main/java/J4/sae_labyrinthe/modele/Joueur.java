package J4.sae_labyrinthe.modele;

import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Joueur implements IJoueur, Serializable {
    private String nom;
    private String mdp;
    private int etapeMax;
    private int etapeActuelle;
    private int score;//le nombre d'étoiles gagnées
    private double tempsMoyenDansLabyrinthe;
    private Image imagePP;

    private List<Etape> etapes;

    public Joueur(String nom,String mdp){
        this.nom = nom;
        this.mdp = mdp;
        this.etapeMax = 1;
        this.etapeActuelle = 1;
        this.score = 0;
        this.tempsMoyenDansLabyrinthe = 0.0;
        //this.imagePP = new Image("@../assets/icons/rules.png");
        this.etapes = new ArrayList<>();
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

    public String getMdp() {return this.mdp;}

    public void setTempsMoyenDansLabyrinthe(double tempsMoyenDansLabyrinthe) {
        this.tempsMoyenDansLabyrinthe = tempsMoyenDansLabyrinthe;
    }
    public double getTempsMoyenDansLabyrinthe() {return this.tempsMoyenDansLabyrinthe;}

    /*public void setImagePP(Image imagePP) {
        this.imagePP = imagePP;
    }
    public Image getImagePP() {
        return this.imagePP;
    }
*/
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

    public void ajouterEtape(Etape e) {
        if (!etapes.contains(e))
            etapes.add(e);
    }

    public void marquerDefiReussi(Etape etape, int difficulte) {
        ajouterEtape(etape);
        Defi d = etape.getDefiParDifficulte(difficulte);
        if (d != null) {
            d.setReussi(true);
        }
    }

    public boolean estDefiReussi(Etape etape, int difficulte) {
        Defi d = etape.getDefiParDifficulte(difficulte);
        return d != null && d.estReussi();
    }
}
