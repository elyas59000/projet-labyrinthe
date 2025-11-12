package J4.sae_labyrinthe.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import J4.sae_labyrinthe.modele.Joueur;


public class Chiffrement {
    private static final SecretKey secretKey;

    static {
        try {
            secretKey = getSecretKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static SecretKey getSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);

        return keyGenerator.generateKey();
    }

    private static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance("AES");
    }

    /**
     * Chiffre un tableau de bytes et le convertit en String Base64.
     */
    private static byte[] chiffrer(byte[] input) throws Exception {
        Cipher cipher = getCipher();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(input);
    }

    /**
     * Déchiffre un tableau de bytes et le retourne en String.
     */
    private static String dechiffrer(byte[] input) throws Exception {
        Cipher cipher = getCipher();
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(input), StandardCharsets.UTF_8);
    }


    /* marche pas */

    protected static void chiffrer(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            byte[] inputBytes = new byte[(int) new File(filename).length()];
            fis.read(inputBytes);

            Cipher cipher = getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(filename);
            outputStream.write(encryptedBytes);

            fis.close();
            outputStream.close();
        } catch (IOException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                 BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void dechiffrer(String filename) {
        try {
            Cipher cipher = getCipher();
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            FileInputStream inputStream = new FileInputStream(filename);
            byte[] inputBytes = new byte[(int) new File(filename).length()];
            inputStream.read(inputBytes);

            byte[] decryptedBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(filename);
            outputStream.write(decryptedBytes);

            inputStream.close();
            outputStream.close();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Chiffre les données sensibles d'un joueur (nom et mot de passe).
     */
    private static void chiffrer(Joueur joueur) throws Exception {
        byte[] mdpBytes = joueur.getMdp().getBytes(StandardCharsets.UTF_8);
        byte[] nomBytes = joueur.getNom().getBytes(StandardCharsets.UTF_8);

        byte[] mdpChiffre = chiffrer(mdpBytes);
        byte[] nomChiffre = chiffrer(nomBytes);

        // Encoder les bytes chiffrés en Base64 pour les stocker en String
        joueur.setMdp(Base64.getEncoder().encodeToString(mdpChiffre));
        joueur.setNom(Base64.getEncoder().encodeToString(nomChiffre));
    }

    /**
     * Chiffre les données de tous les joueurs d'une liste.
     */
    protected static void chiffrer(ArrayList<Joueur> joueurs) throws Exception {
        for (Joueur joueur : joueurs) {
            chiffrer(joueur);
        }
    }


    /**
     * Déchiffre les données sensibles d'un joueur (nom et mot de passe).
     */
    private static void dechiffrer(Joueur joueur) throws Exception {
        // Décoder la Base64 pour récupérer les bytes chiffrés
        byte[] mdpChiffre = Base64.getDecoder().decode(joueur.getMdp());
        byte[] nomChiffre = Base64.getDecoder().decode(joueur.getNom());

        // Déchiffrer les données
        String mdp = dechiffrer(mdpChiffre);
        String nom = dechiffrer(nomChiffre);

        // Mise à jour des données déchiffrées
        joueur.setMdp(mdp);
        joueur.setNom(nom);
    }

    /**
     * Déchiffre les données de tous les joueurs d'une liste.
     */
    protected static void dechiffrer(ArrayList<Joueur> joueurs) throws Exception {
        for (Joueur joueur : joueurs) {
            dechiffrer(joueur);
        }
    }

    /**
     * Convertit un tableau de bytes en String UTF-8.
     */
    public static String byteToString(byte[] input) {
        return new String(input, StandardCharsets.UTF_8);
    }
}
