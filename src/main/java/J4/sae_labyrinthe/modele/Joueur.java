package J4.sae_labyrinthe.modele;

import javafx.scene.image.Image;

import java.io.*;

public class Joueur implements IJoueur, Serializable {
    private String nom;
    private String mdp;
    private int etapeMax;
    private int etapeActuelle;
    private int score;//le nombre d'étoiles gagnées
    private double tempsMoyenDansLabyrinthe;


    public Joueur(String nom,String mdp){
        this.nom = nom;
        this.mdp = mdp;
        this.etapeMax = 1;
        this.etapeActuelle = 1;
        this.score = 0;
        this.tempsMoyenDansLabyrinthe = 0.0;
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
}
