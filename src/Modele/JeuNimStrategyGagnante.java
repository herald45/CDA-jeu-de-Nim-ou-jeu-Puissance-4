package Modele;

import java.util.List;

/**
 * Stratégie gagnante pour le jeu de Nim.
 * Cette classe implémente IAStrategy pour choisir un coup qui maximise les chances de gagner,
 * en utilisant le principe du XOR des nombres d'allumettes dans chaque tas.
 */
public class JeuNimStrategyGagnante implements IAStrategy {

    /**
     * Sélectionne un coup basé sur la stratégie gagnante de Nim.
     * @param partie Le modèle de jeu actuel, utilisé pour obtenir le plateau de jeu.
     * @return Un tableau de deux entiers indiquant le tas choisi et le nombre d'allumettes à retirer.
     */
    @Override
    public int[] choisirCoup(Modele partie) {
        List<List<Integer>> plateau = partie.plateauDeJeu;
        int xorResult = 0;

        // Calculer le XOR du nombre d'allumettes restantes dans chaque tas
        for (List<Integer> tas : plateau) {
            int count = 0;
            for (int allumette : tas) {
                if (allumette == 1) {
                    count++;
                }
            }
            xorResult ^= count;
        }

        if (xorResult == 0) {
            // L'IA n'est pas dans une situation gagnante, choisir un coup aléatoire
            for (int i = 0; i < plateau.size(); i++) {
                for (int allumette : plateau.get(i)) {
                    if (allumette == 1) {
                        // Retirer une allumette du premier tas non vide
                        return new int[]{i + 1, 1}; // Ajouter 1 pour corriger l'indexation
                    }
                }
            }
        } else {
            // L'IA est dans une situation gagnante, choisir le coup optimal
            for (int i = 0; i < plateau.size(); i++) {
                int count = 0;
                for (int allumette : plateau.get(i)) {
                    if (allumette == 1) {
                        count++;
                    }
                }
                int nb = count ^ xorResult;
                if (nb < count) {
                    // Retirer le nombre d'allumettes nécessaire pour forcer une situation perdante pour l'adversaire
                    return new int[]{i + 1, count - nb};
                }
            }
        }

        // Par défaut, retourner un coup quelconque si aucune autre condition n'est remplie
        return new int[]{1, 1};
    }
}
