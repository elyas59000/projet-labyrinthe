package J4.sae_labyrinthe.utils;

import J4.sae_labyrinthe.modele.Joueur;

import javax.crypto.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


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

    private static byte[] chiffrer(byte[] input) throws Exception {
        Cipher cipher = getCipher();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return cipher.doFinal(input);
    }

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


    /// Marche pas ///

    private static void chiffrer(Joueur joueur) throws Exception {
        byte[] mdp = joueur.getMdp().getBytes(StandardCharsets.UTF_8);
        byte[] nom = joueur.getNom().getBytes(StandardCharsets.UTF_8);

        joueur.setMdp(byteToString(chiffrer(mdp)));
        joueur.setNom(byteToString(chiffrer(nom)));
    }

    protected static void chiffrer(ArrayList<Joueur> joueurs) throws Exception {
        for (Joueur joueur : joueurs) {
            chiffrer(joueur);
        }
    }


    private static void dechiffrer(Joueur joueur) throws Exception {
        byte[] mdp = joueur.getMdp().getBytes(StandardCharsets.UTF_8);
        byte[] nom = joueur.getNom().getBytes(StandardCharsets.UTF_8);

        joueur.setMdp(dechiffrer(mdp));
        joueur.setNom(dechiffrer(nom));
    }

    protected static void dechiffrer(ArrayList<Joueur> joueurs) throws Exception {
        for (Joueur joueur : joueurs) {
            dechiffrer(joueur);
        }
    }

    public static String byteToString(byte[] input) {
        return new String(input, StandardCharsets.UTF_8);
    }




}
