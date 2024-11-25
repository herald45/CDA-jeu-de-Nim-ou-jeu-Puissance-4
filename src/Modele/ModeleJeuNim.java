package Modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Modèle spécifique pour le jeu de Nim.
 * Gère les tas d'allumettes et permet de réaliser des actions telles que retirer des allumettes.
 */
public class ModeleJeuNim extends Modele {
    private int nTas;  // Nombre de tas dans le jeu
    private int nMax;

    /**
     * Constructeur pour créer un modèle de jeu de Nim.
     * Initialise le jeu avec deux joueurs spécifiés.
     * @param joueur1 Nom du premier joueur.
     * @param joueur2 Nom du deuxième joueur.
     */
    public ModeleJeuNim(String joueur1, String joueur2) {
        super(joueur1, joueur2);
    }

    public int getnMax() {
        return nMax;
    }

    public void setnMax(int nMax) throws Exception {
        if (nMax < 0) {
            throw new Exception("🙅‍ Ce n'est pas un numéro valide pour le nombre d'allumettes. Veuillez essayer à nouveau:");
        }else {
            this.nMax = nMax;
        }

    }

    /**
     * Définit le nombre de tas pour le jeu de Nim.
     * @param nTas Nombre de tas à utiliser dans le jeu.
     */
    public void setnTas(int nTas) {
        this.nTas = nTas;
    }

    /**
     * Vérifie si la partie est terminée, c'est-à-dire si tous les tas sont vides.
     * @param joueur Identifiant du joueur pour lequel vérifier la fin de partie.
     * @return true si tous les tas sont vides, sinon false.
     */
    @Override
    public boolean finJeu(int joueur) {
        for (List<Integer> tas : plateauDeJeu) {
            for (int allumette : tas) {
                if (allumette == 1) {
                    return false;  // Il reste des allumettes, donc le jeu continue
                }
            }
        }
        return true;  // Tous les tas sont vides
    }

    /**
     * Régénère le plateau de jeu en créant le nombre spécifié de tas, chacun avec un nombre croissant d'allumettes.
     */
    @Override
    public void regenererplateau() {
        this.plateauDeJeu = new ArrayList<>();
        for (int i = 1; i <= nTas; i++) {
            List<Integer> ligne = new ArrayList<>();
            for (int j = 0; j < (2 * i - 1); j++) {
                ligne.add(1);  // Chaque tas commence avec un nombre croissant d'allumettes
            }
            plateauDeJeu.add(ligne);
        }
    }
    /**
     * Vérifie si un mouvement est valide en fonction du tas et du nombre d'allumettes spécifiés.
     *
     * @param l  Le numéro du tas.
     * @param nb Le nombre d'allumettes à retirer du tas.
     * @return true si le mouvement est valide, sinon false.
     */
    private boolean valide(int l,int nb){

        if (0<l && plateauDeJeu.size()>l-1 && nb > 0) {
            int compteur = 0;
            l = l - 1;
            for (int element : plateauDeJeu.get(l)) {
                if (element == 1) {
                    compteur++;
                }
            }
            return (compteur >= nb);
        }
        else return (false);
    }
    /**
     * Retire un nombre spécifié d'allumettes du tas spécifié.
     * Lève une exception si le mouvement est invalide.
     * @param l  L'index du tas (1-indexé).
     * @param nb Le nombre d'allumettes à retirer.
     * @throws Exception Si le mouvement est invalide.
     */
    public void enlever(int l, int nb) throws Exception {
        if (valide(l, nb)) {
            int nombreRemplace = 0;
            for (int i = 0; i < plateauDeJeu.get(l - 1).size(); i++) {
                if (plateauDeJeu.get(l - 1).get(i) == 1 && nombreRemplace < nb) {
                    plateauDeJeu.get(l - 1).set(i, 0);  // Remplacer par 0 pour indiquer que l'allumette a été retirée
                    nombreRemplace++;
                }
            }
        } else {
            throw new Exception("🙅‍ Mouvement invalide, veuillez réessayer.");
        }
    }

    /**
     * Génère une représentation textuelle du plateau de jeu.
     * @return Une chaîne de caractères représentant l'état actuel du plateau.
     */
    @Override
    public String toString() {
        int longueurMaximale = plateauDeJeu.get(plateauDeJeu.size() - 1).size();
        StringBuilder Plateau = new StringBuilder();
        for (List<Integer> ligne : plateauDeJeu) {
            int espaces = (longueurMaximale - ligne.size()) / 2;
            StringBuilder batonnet = new StringBuilder();
            batonnet.append(" ".repeat(Math.max(0, espaces)));
            for (Integer valeur : ligne) {
                batonnet.append(valeur == 1 ? "|" : " ");
            }
            Plateau.append(batonnet).append("\n");
        }
        return Plateau.toString();
    }
}
