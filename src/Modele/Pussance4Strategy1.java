package Modele;

import Vue.Ihm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Stratégie de jeu pour Puissance 4 qui évalue les coups possibles basée sur des scores de potentiel de victoire.
 */
public class Pussance4Strategy1 implements IAStrategy {
    private final Random random = new Random();

    /**
     * Sélectionne le coup le plus stratégique en évaluant chaque emplacement vide sur le plateau.
     *
     * @param partie Le modèle de jeu actuel.
     * @return Un tableau d'entiers indiquant la colonne du coup choisi et si une rotation est nécessaire (toujours 0 ici).
     */
    @Override
    public int[] choisirCoup(Modele partie) {
        List<List<Integer>> plateau = partie.plateauDeJeu;
        int meilleurScore = 0;
        List<Integer> meilleursCoups = new ArrayList<>();

        // Évaluer chaque coup de jeton possible
        for (int c = 0; c < 7; c++) {
            for (int r = 6; r >= 0; r--) {
                if (plateau.get(r).get(c) == 0) {  // Trouver la première ligne libre
                    int scoreJaune = evaluerCoup(r, c, plateau, 2);
                    int scoreRouge = evaluerCoup(r, c, plateau, 1);


                    // Calculer le score final en tenant compte des possibilités d'alignement futur
                    int score = determineScore(scoreJaune, scoreRouge, r, c, plateau);

                    if (score > meilleurScore) {
                        meilleurScore = score;
                        meilleursCoups.clear();
                    }
                    if (score == meilleurScore) {
                        meilleursCoups.add(c + 1);
                    }
                    break;
                }
            }
        }

        // Choisir aléatoirement parmi les meilleurs coups
        return new int[]{meilleursCoups.get(random.nextInt(meilleursCoups.size())), 0};  // Pas de rotation
    }
    /**
     * Évalue et détermine le score d'un coup donné, basé sur le potentiel d'aligner quatre pions et de bloquer l'adversaire.
     *
     * @param scoreJaune Le score potentiel pour les jetons jaunes.
     * @param scoreRouge Le score potentiel pour les jetons rouges.
     * @param r La ligne du coup évalué.
     * @param c La colonne du coup évalué.
     * @param plateau L'état actuel du plateau de jeu.
     * @return Un score indiquant la valeur stratégique du coup.
     */
    private int determineScore(int scoreJaune, int scoreRouge, int r, int c, List<List<Integer>> plateau) {
        // Prioriser les coups selon le potentiel de victoire ou de blocage
        Ihm.println(canExtendToFour(r,c,plateau,1)+"rougeeee");
        Ihm.println(canExtendToFour(r,c,plateau,2)+ "jauneeeeee");
        if (scoreJaune >= 4) return 7;
        if (scoreRouge >= 4) return 6;
        if (scoreRouge == 3) return 4;
        if (scoreJaune == 3 ) return 5;
        if (scoreRouge == 2 ) return 2;
        if (scoreJaune == 2 ) return 3;
        return 1;  // Aucun alignement significatif avec potentiel de victoire
    }
    /**
     * Vérifie si un coup peut potentiellement être étendu pour former une ligne de quatre jetons.
     *
     * @param r La ligne à vérifier.
     * @param c La colonne à vérifier.
     * @param plateau Le plateau de jeu.
     * @param playerId L'identifiant du joueur.
     * @return true si il est possible d'étendre à quatre jetons, false sinon.
     */
    private boolean canExtendToFour(int r, int c, List<List<Integer>> plateau, int playerId) {
        // Directions de vérification : droite, bas, diagonale montante, diagonale descendante
        int[] rowDirections = {0, 1, 1, 1};
        int[] colDirections = {1, 0, 1, -1};
        int rows = plateau.size();
        int cols = plateau.get(0).size();

        // Parcourir les quatre directions principales pour vérifier les alignements
        for (int dir = 0; dir < rowDirections.length; dir++) {
            int count = 1; // Compte le pion actuellement placé

            // Vérifier dans la direction principale
            count += countInDirection(r, c, rowDirections[dir], colDirections[dir], plateau, playerId);

            // Vérifier dans la direction opposée
            count += countInDirection(r, c, -rowDirections[dir], -colDirections[dir], plateau, playerId);

            // Vérifier s'il y a la possibilité d'étendre jusqu'à quatre jetons
            if (count >= 4) {
                return true; // Trouvé une possibilité d'étendre à quatre
            }
        }

        return false;
    }

    // Méthode auxiliaire pour compter les pions dans une direction spécifique
    private int countInDirection(int r, int c, int rowDir, int colDir, List<List<Integer>> plateau, int playerId) {
        int count = 0;
        int rows = plateau.size();
        int cols = plateau.get(0).size();

        while (true) {
            r += rowDir;
            c += colDir;
            if (r < 0 || r >= rows || c < 0 || c >= cols || plateau.get(r).get(c) != playerId) {
                break; // Sortir si hors limites ou si la cellule n'est pas du joueur
            }
            count++;
        }

        return count;
    }



    /**
     * Évalue un coup potentiel en plaçant temporairement un jeton et en calculant l'alignement maximal possible.
     *
     * @param ligne L'indice de la ligne où le jeton serait placé.
     * @param colonne L'indice de la colonne où le jeton serait placé.
     * @param plateau Le plateau de jeu.
     * @param joueurId L'identifiant du joueur pour lequel évaluer le coup.
     * @return Le nombre maximal de jetons alignés que ce coup pourrait produire.
     */
    private int evaluerCoup(int ligne, int colonne, List<List<Integer>> plateau, int joueurId) {
        plateau.get(ligne).set(colonne, joueurId);
        int maxAlign = maxAlign(ligne, colonne, plateau, joueurId);
        plateau.get(ligne).set(colonne, 0);
        return maxAlign;
    }
    /**
     * Calcule le nombre maximal de jetons alignés qu'un coup peut produire, en vérifiant toutes les directions possibles.
     *
     * @param ligne La ligne du jeton placé.
     * @param colonne La colonne du jeton placé.
     * @param plateau Le plateau de jeu.
     * @param joueurId L'identifiant du joueur.
     * @return Le nombre maximal de jetons alignés.
     */
    private int maxAlign(int ligne, int colonne, List<List<Integer>> plateau, int joueurId) {
        int maxAlign = 0;

        // Calculer l'alignement maximal possible dans les lignes
        int count = 1;  // Compter le jeton actuellement placé
        // À gauche
        for (int i = colonne - 1; i >= 0 && plateau.get(ligne).get(i) == joueurId; i--) {
            count++;
        }
        // À droite
        for (int i = colonne + 1; i < 7 && plateau.get(ligne).get(i) == joueurId; i++) {
            count++;
        }
        maxAlign = Math.max(maxAlign, count);

        // Calculer l'alignement maximal possible dans les colonnes
        count = 1;  // Réinitialiser le compteur pour la colonne
        // Vers le bas (on ne peut pas aller vers le haut puisque le jeton tombe toujours au plus bas possible)
        for (int i = ligne + 1; i < 7 && plateau.get(i).get(colonne) == joueurId; i++) {
            count++;
        }
        maxAlign = Math.max(maxAlign, count);

        // Calculer l'alignement maximal pour les diagonales (descendant droite et gauche)
        // Diagonale droite (descendante)
        count = 1;
        // Haut gauche
        for (int i = 1; ligne - i >= 0 && colonne - i >= 0 && plateau.get(ligne - i).get(colonne - i) == joueurId; i++) {
            count++;
        }
        // Bas droite
        for (int i = 1; ligne + i < 7 && colonne + i < 7 && plateau.get(ligne + i).get(colonne + i) == joueurId; i++) {
            count++;
        }
        maxAlign = Math.max(maxAlign, count);

        // Diagonale gauche (descendante)
        count = 1;
        // Haut droite
        for (int i = 1; ligne - i >= 0 && colonne + i < 7 && plateau.get(ligne - i).get(colonne + i) == joueurId; i++) {
            count++;
        }
        // Bas gauche
        for (int i = 1; ligne + i < 7 && colonne - i >= 0 && plateau.get(ligne + i).get(colonne - i) == joueurId; i++) {
            count++;
        }
        maxAlign = Math.max(maxAlign, count);

        return maxAlign;
    }

}