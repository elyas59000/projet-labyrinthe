package J4.sae_labyrinthe.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import J4.sae_labyrinthe.modele.Joueur;


public class Chiffrement {
    private static final SecretKey secretKey;
    private static final int IV_SIZE = 16;

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

    /**
     * Génère un IV (vecteur d'initialisation) aléatoire.
     */
    private static byte[] generateIv() {
        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    /**
     * Chiffre un tableau de bytes et inclut l'IV dans le résultat.
     */
    private static byte[] chiffrer(byte[] input) throws Exception {
        byte[] iv = generateIv();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] encryptedData = cipher.doFinal(input);
        
        byte[] result = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(encryptedData, 0, result, iv.length, encryptedData.length);
        
        return result;
    }

    /**
     * Déchiffre un tableau de bytes qui contient l'IV au début.
     */
    private static String dechiffrer(byte[] input) throws Exception {
        if (input.length < IV_SIZE) {
            return new String(input, StandardCharsets.UTF_8);
        }
        
        byte[] iv = new byte[IV_SIZE];
        byte[] encryptedData = new byte[input.length - IV_SIZE];
        
        System.arraycopy(input, 0, iv, 0, IV_SIZE);
        System.arraycopy(input, IV_SIZE, encryptedData, 0, encryptedData.length);
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        
        return new String(cipher.doFinal(encryptedData), StandardCharsets.UTF_8);
    }


    protected static void chiffrer(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            byte[] inputBytes = new byte[(int) new File(filename).length()];
            fis.read(inputBytes);

            byte[] iv = generateIv();
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] encryptedBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(filename);
            outputStream.write(iv);
            outputStream.write(encryptedBytes);

            fis.close();
            outputStream.close();
        } catch (IOException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                 BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void dechiffrer(String filename) {
        try {
            FileInputStream inputStream = new FileInputStream(filename);
            byte[] inputBytes = new byte[(int) new File(filename).length()];
            inputStream.read(inputBytes);

            byte[] iv = new byte[IV_SIZE];
            byte[] encryptedData = new byte[inputBytes.length - IV_SIZE];
            
            System.arraycopy(inputBytes, 0, iv, 0, IV_SIZE);
            System.arraycopy(inputBytes, IV_SIZE, encryptedData, 0, encryptedData.length);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] decryptedBytes = cipher.doFinal(encryptedData);

            FileOutputStream outputStream = new FileOutputStream(filename);
            outputStream.write(decryptedBytes);

            inputStream.close();
            outputStream.close();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException | IOException | InvalidAlgorithmParameterException e) {
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
        // Décoder la Base64 pour récupérer les bytes chiffrés (qui contiennent l'IV)
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
