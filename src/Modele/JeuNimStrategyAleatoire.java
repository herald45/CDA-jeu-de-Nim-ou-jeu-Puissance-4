package Modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Stratégie aléatoire pour le jeu de Nim.
 * Cette classe implémente IAStrategy pour choisir un coup de manière aléatoire
 * en respectant une limite maximale d'allumettes pouvant être retirées.
 */
public class JeuNimStrategyAleatoire implements IAStrategy {
    private final Random random = new Random();  // Générateur de nombres aléatoires pour le choix du coup
    private final int nMax; // Nombre maximal d'allumettes qui peuvent être retirées en un seul coup

    /**
     * Constructeur de la stratégie aléatoire.
     * @param nMax Le nombre maximal d'allumettes retirables en un seul coup.
     */
    public JeuNimStrategyAleatoire(int nMax) {
        this.nMax = nMax;
    }

    /**
     * Sélectionne un coup au hasard parmi tous les coups valides possibles.
     * @param partie Le modèle de jeu actuel, utilisé pour obtenir le plateau de jeu.
     * @return Un tableau de deux entiers indiquant le tas choisi et le nombre d'allumettes à retirer.
     */
    @Override
    public int[] choisirCoup(Modele partie) {
        List<List<Integer>> plateau = partie.plateauDeJeu;  // Accès au plateau de jeu
        List<int[]> coupsPossibles = new ArrayList<>();  // Liste pour stocker tous les coups possibles

        // Parcourir chaque tas pour calculer les coups possibles en tenant compte de nMax
        for (int i = 0; i < plateau.size(); i++) {
            int count = 0;
            for (int allumette : plateau.get(i)) {
                if (allumette == 1) {
                    count++;  // Compter les allumettes disponibles dans le tas
                }
            }
            int maxRetirable = Math.min(nMax, count);  // Le maximum d'allumettes qui peut être retiré de ce tas
            for (int j = 1; j <= maxRetirable; j++) {
                coupsPossibles.add(new int[]{i + 1, j}); // Ajouter le coup possible à la liste
            }
        }

        // Choisir un coup au hasard parmi les coups possibles
        if (!coupsPossibles.isEmpty()) {
            int index = random.nextInt(coupsPossibles.size());  // Sélectionner un index aléatoire
            return coupsPossibles.get(index);  // Retourner le coup choisi
        }

        // Fallback en cas d'absence de coups possibles (ce qui ne devrait normalement pas arriver)
        return new int[]{1, 1};  // Retourner un coup par défaut
    }
}
