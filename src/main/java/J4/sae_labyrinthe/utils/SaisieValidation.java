package J4.sae_labyrinthe.utils;

import J4.sae_labyrinthe.exception.ValidationException;

public class SaisieValidation {

    public static void validationConnexion(String login,String mdp) throws ValidationException {
        if(login.isEmpty()){
            throw new ValidationException("Le champs identifiant est vide ");
        }
        if(mdp.isEmpty()){
            throw new ValidationException("Le mot de passe est vide ");
        }
    }

    public static void validationInscription(String login,String mdp,String confMdp) throws ValidationException {
        validationConnexion(login,mdp);
        if(confMdp.isEmpty()){
            throw new ValidationException("Le champs confirmation du mot de passe est vide");
        }
        if (!mdp.equals(confMdp)) {
            throw new ValidationException("Les champs mot de passe et confirmation de mot de passe ne sont pas identiques");
        }
        if (login.length()<3){
            throw new ValidationException("L'identifiant est trop court, choisir au moins un ID de 3 caractères");
        }
        /*
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:;\"'|<>,.?/]).+$";
        if (!mdp.contains(regex)) {
            throw new ValidationException("Le mdp doit contenir au moins : 1 Majuscule, 1 Chiffre et 1 Caractère spécial");
        }
        */
    }
}
