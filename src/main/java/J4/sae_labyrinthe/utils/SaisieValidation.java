package J4.sae_labyrinthe.utils;

import J4.sae_labyrinthe.exception.ValidationException;

/**
 * Utilitaire de validation des saisies utilisateur (connexion / inscription).
 *
 * Contient des méthodes statiques qui lèvent {@link ValidationException}
 * en cas d'erreur de saisie afin que les contrôleurs puissent afficher des
 * messages d'erreur adaptés.
 *
 * @author Nathan Philippe, Clément Roty
 */
public class SaisieValidation {

    /**
     * Valide les champs nécessaires à une connexion.
     *
     * @param login identifiant saisi
     * @param mdp mot de passe saisi
     * @throws ValidationException si un champ requis est vide
     */
    public static void validationConnexion(String login,String mdp) throws ValidationException {
        if(login == null || login.isEmpty()){
            throw new ValidationException("Le champs identifiant est vide ");
        }
        if(mdp == null || mdp.isEmpty()){
            throw new ValidationException("Le mot de passe est vide ");
        }
    }

    /**
     * Valide les champs nécessaires à une inscription.
     *
     * @param login identifiant souhaité
     * @param mdp mot de passe choisi
     * @param confMdp confirmation du mot de passe
     * @throws ValidationException si une règle n'est pas respectée
     */
    public static void validationInscription(String login,String mdp,String confMdp) throws ValidationException {
        validationConnexion(login,mdp);
        if(confMdp == null || confMdp.isEmpty()){
            throw new ValidationException("Le champs confirmation du mot de passe est vide");
        }
        if (!mdp.equals(confMdp)) {
            throw new ValidationException("Les champs mot de passe et confirmation de mot de passe ne sont pas identiques");
        }
        if (login.length()<3){
            throw new ValidationException("L'identifiant est trop court, choisir au moins un ID de 3 caractères");
        }

    }
}
