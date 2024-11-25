package Modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant le modèle de base pour des jeux à deux joueurs.
 */
abstract public class Modele {
    public final Joueur[] lesJoueurs = new Joueur[2];  // Tableau stockant les deux joueurs

    protected List<List<Integer>> plateauDeJeu;  // Plateau de jeu représenté par une liste de listes d'entiers

    /**
     * Constructeur pour initialiser le modèle avec deux joueurs.
     * @param joueur1 Le nom du premier joueur.
     * @param joueur2 Le nom du deuxième joueur.
     */
    public Modele(String joueur1, String joueur2) {
        this.lesJoueurs[0] = new Joueur(joueur1);
        this.lesJoueurs[1] = new Joueur(joueur2);
        this.plateauDeJeu = new ArrayList<>();
        regenererplateau();  // Initialiser ou réinitialiser le plateau de jeu
    }

    /**
     * Obtient le tableau de joueurs.
     * @return Un tableau contenant les deux joueurs.
     */
    public Joueur[] getLesJoueurs() {
        return lesJoueurs;
    }

    /**
     * Renvoie le joueur suivant selon le joueur actuel.
     * @param j Le joueur actuel.
     * @return Le joueur suivant.
     */
    public Joueur joueurSuivant(Joueur j) {
        return j.getId() == lesJoueurs[0].getId() ? lesJoueurs[1] : lesJoueurs[0];
    }

    /**
     * Méthode abstraite pour déterminer si la partie est terminée.
     * @param joueur Identifiant du joueur pour lequel vérifier la fin de partie.
     * @return true si le jeu est fini pour le joueur spécifié, false sinon.
     */
    public abstract boolean finJeu(int joueur);

    /**
     * Méthode abstraite pour régénérer le plateau de jeu.
     * Cette méthode doit être implémentée pour initialiser ou réinitialiser le plateau selon les règles du jeu spécifique.
     */
    abstract public void regenererplateau();

    /**
     * Méthode abstraite pour obtenir une représentation sous forme de chaîne du modèle.
     * @return Une chaîne de caractères représentant l'état actuel du modèle.
     */
    @Override
    abstract public String toString();
}
