package Modele;

import Vue.Ihm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Stratégie de jeu pour Puissance 4 qui évalue des coups potentiels en tenant compte des rotations
 * et de leur impact sur l'issue du jeu.
 */
public class Puissance4Strategy2 implements IAStrategy {
    private final Random random = new Random();

    /**
     * Détermine le meilleur coup à jouer en évaluant chaque possibilité selon des critères stratégiques.
     *
     * @param partie Le modèle de jeu actuel sur lequel évaluer les coups.
     * @return Un tableau d'entiers où la première valeur est l'index de la colonne (1-indexé) et la seconde indique une rotation.
     */
    @Override
    public int[] choisirCoup(Modele partie) {
        List<List<Integer>> plateau = partie.plateauDeJeu;


        // Simuler et vérifier si une rotation à droite ou à gauche est gagnante
        if (peutGagnerParRotation(partie, true)) {
            return new int[]{0, 1};  // Rotation à droite gagnante
        }
        if (peutGagnerParRotation(partie, false)) {
            return new int[]{0, 2};  // Rotation à gauche gagnante
        }


        List<Integer> meilleursCoups = new ArrayList<>();
        int meilleurScore = 0;
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
                        meilleursCoups.add(c+1);
                    }
                    break;
                }
            }
        }

        // Choisir aléatoirement parmi les meilleurs coups
        return new int[]{meilleursCoups.get(random.nextInt(meilleursCoups.size())), 0};
    }
    /**
     * Évalue si une rotation peut mener directement à une victoire.
     *
     * @param partie L'état actuel du jeu.
     * @param isRight Booléen indiquant si la rotation est à droite (true) ou à gauche (false).
     * @return true si la rotation peut mener à une victoire, false sinon.
     */
    private boolean peutGagnerParRotation(Modele partie, boolean isRight) {
        // Simuler une rotation, vérifier si cela mène à une victoire
        List<List<Integer>> originalPlateau = new ArrayList<>(partie.plateauDeJeu);
        if (isRight) {
            partie.plateauDeJeu = TounerAdroite(partie.plateauDeJeu);
        } else {
            partie.plateauDeJeu = TournerAgauche(partie.plateauDeJeu);
        }

        boolean gagne = aGagne(partie);  // Vérifier si la rotation donne une victoire pour l'IA
        partie.plateauDeJeu = originalPlateau;  // Restaurer l'état original du plateau
        return gagne;
    }
    /**
     * Effectue une rotation à droite du plateau de jeu et ajuste les jetons en conséquence.
     *
     * @param plateau Le plateau de jeu actuel.
     * @return Le nouveau plateau après rotation.
     */
    private List<List<Integer>> TounerAdroite(List<List<Integer>> plateau) {
        // Ajouter la logique pour effectuer une rotation à droite du plateau
        // Création d'une copie de la grille
        List<List<Integer>> grilleCopie = new ArrayList<>();
        for (List<Integer> ligne : plateau) {
            grilleCopie.add(new ArrayList<>(ligne));
        }
        // Rotation de chaque élément de la grille
        for (int i = 0; i < grilleCopie.size(); i++) {
            for (int j = 0; j < grilleCopie.get(i).size(); j++) {
                grilleCopie.get(i).set(j, plateau.get(6 - j).get(i));
            }
        }
        // Faire tomber les pions vers le bas
        for (int j = 0; j < grilleCopie.get(0).size(); j++) {
            int bottomIndex = 6;
            for (int i = 6; i >= 0; i--) {
                if (grilleCopie.get(i).get(j) != 0) {
                    grilleCopie.get(bottomIndex--).set(j, grilleCopie.get(i).get(j));
                }
            }
            // Remplir les cases vides en haut avec des zéros
            for (int i = bottomIndex; i >= 0; i--) {
                grilleCopie.get(i).set(j, 0);
            }
        }
        return grilleCopie;


    }
    /**
     * Effectue une rotation à gauche du plateau de jeu et ajuste les jetons en conséquence.
     *
     * @param plateau Le plateau de jeu actuel.
     * @return Le nouveau plateau après rotation.
     */
    private List<List<Integer>> TournerAgauche(List<List<Integer>> plateau) {
        // Ajouter la logique pour effectuer une rotation à gauche du plateau
        // Création d'une copie de la grille
        List<List<Integer>> grilleCopie = new ArrayList<>();
        for (List<Integer> ligne : plateau) {
            grilleCopie.add(new ArrayList<>(ligne));
        }
        // Rotation de chaque élément de la grille
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
                int res = plateau.get(j).get(6 - i);
                grilleCopie.get(i).set(j, res);
            }
        }
        // Faire tomber les pions vers le bas
        for (int j = 0; j < grilleCopie.get(0).size(); j++) {
            int bottomIndex = 6;
            for (int i = 6; i >= 0; i--) {
                if (grilleCopie.get(i).get(j) != 0) {
                    grilleCopie.get(bottomIndex--).set(j, grilleCopie.get(i).get(j));
                }
            }
            // Remplir les cases vides en haut avec des zéros
            for (int i = bottomIndex; i >= 0; i--) {
                grilleCopie.get(i).set(j, 0);
            }
        }
        return grilleCopie;


    }
    /**
     * Vérifie si le joueur a gagné après un coup ou une rotation.
     *
     * @param partie Le modèle de jeu à vérifier.
     * @return true si le joueur a aligné quatre jetons, false sinon.
     */
    private boolean aGagne(Modele partie) {
        // Utiliser la logique existante pour déterminer si le joueur a gagné
        // Vérification des lignes
        for (int ligne = 0; ligne < 7; ligne++) {
            int compteur = 0;
            for (int col = 0; col < 7; col++) {
                if (partie.plateauDeJeu.get(ligne).get(col) == 2) {
                    compteur++;
                } else {
                    compteur = 0;
                }
                if (compteur == 4) {
                    return true;
                }
            }
        }

        // Vérification des colonnes
        for (int col = 0; col < 7; col++) {
            int compteur = 0;
            for (int ligne = 0; ligne < 7; ligne++) {
                if (partie.plateauDeJeu.get(ligne).get(col) == 2) {
                    compteur++;
                } else {
                    compteur = 0;
                }
                if (compteur == 4) {
                    return true;
                }
            }
        }

        // Vérification des diagonales vers le bas à droite
        int incr = 0;
        for (int i = 6; i > 2; i--) {
            int comp1 = 0;
            int comp2 = 0;
            for (int j = 0; j <= i; j++) {
                // Partie gauche du partie.plateauDeJeu
                if (partie.plateauDeJeu.get(j + incr).get(j) == 2) {
                    comp1++;
                } else {
                    comp1 = 0;
                }
                // Partie droite du partie.plateauDeJeu
                if (partie.plateauDeJeu.get(j).get(j + incr) == 2) {
                    comp2++;
                } else {
                    comp2 = 0;
                }
                if (comp1 == 4 || comp2 == 4) {
                    return true;
                }
            }
            incr++;
        }

        // Vérification des diagonales vers le bas à gauche

        // Partie gauche
        int incr2 = 3;
        for (int i = 0; i <= 3; i++) {
            int comp = 0;
            for (int j = 0; j <= 3 + i; j++) {
                int t = incr2 - j;
                if (partie.plateauDeJeu.get(j).get(t) == 2) {
                    comp++;
                } else {
                    comp = 0;
                }
                if (comp == 4) {
                    return true;
                }
            }
            incr2++;
        }

        // Partie droite
        for (int i = 0; i <= 2; i++) {
            int comp = 0;
            for (int j = 3 - i; j <= 6; j++) {
                int t = 9 - j - i;

                if (partie.plateauDeJeu.get(j).get(t) == 2) {
                    comp++;
                } else {
                    comp = 0;
                }
                if (comp == 4) {
                    return true;
                }
            }
        }
        return false;
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
     * Détermine le score d'un coup en fonction des possibilités d'aligner quatre jetons.
     *
     * @param scoreJaune Score calculé pour le joueur avec les jetons jaunes.
     * @param scoreRouge Score calculé pour le joueur avec les jetons rouges.
     * @param r Indice de la ligne du coup évalué.
     * @param c Indice de la colonne du coup évalué.
     * @param plateau Le plateau de jeu.
     * @return Un score évaluant l'intérêt stratégique du coup.
     */
    private int determineScore(int scoreJaune, int scoreRouge, int r, int c, List<List<Integer>> plateau) {
        // Prioriser les coups selon le potentiel de victoire ou de blocage
        if (scoreJaune >= 4) return 7;
        if (scoreRouge >= 4) return 6;
        if (scoreJaune == 3 ) return 5;
        if (scoreRouge == 3 ) return 4;
        if (scoreJaune == 2 ) return 3;
        if (scoreRouge == 2 ) return 2;
        return 1;  // Aucun alignement significatif avec potentiel de victoire
    }
    /**
     * Vérifie si il est possible d'étendre un alignement de trois jetons à quatre.
     *
     * @param r L'indice de la ligne où vérifier.
     * @param c L'indice de la colonne où vérifier.
     * @param plateau Le plateau de jeu.
     * @param playerId L'identifiant du joueur pour lequel vérifier.
     * @return true si l'alignement peut être étendu à quatre jetons, false sinon.
     */
    private boolean canExtendToFour(int r, int c, List<List<Integer>> plateau, int playerId) {
        // Directions de vérification : droite, bas, gauche, haut, et les quatre diagonales
        int[] rowDirections = {0, 1, 0, -1, 1, 1, -1, -1};
        int[] colDirections = {1, 0, -1, 0, 1, -1, 1, -1};
        int rows = plateau.size();
        int cols = plateau.get(0).size();

        // Parcourir toutes les directions pour vérifier les alignements
        for (int dir = 0; dir < rowDirections.length; dir++) {
            int count = 1; // Comptez le pion actuellement placé
            int row = r;
            int col = c;

            // Vérifier dans une direction spécifique
            while (true) {
                row += rowDirections[dir];
                col += colDirections[dir];
                if (row < 0 || row >= rows || col < 0 || col >= cols || plateau.get(row).get(col) != playerId) {
                    break;  // Sortir si hors limites ou si la cellule n'est pas du joueur
                }
                count++;
            }

            // Vérifier dans la direction opposée
            row = r;
            col = c;
            while (true) {
                row -= rowDirections[dir];
                col -= colDirections[dir];
                if (row < 0 || row >= rows || col < 0 || col >= cols || plateau.get(row).get(col) != playerId) {
                    break;  // Sortir si hors limites ou si la cellule n'est pas du joueur
                }
                count++;
            }

            // Vérifier s'il y a la possibilité d'étendre jusqu'à quatre jetons
            if (count >= 4) {
                return true; // Trouvé une possibilité d'étendre à quatre
            }
        }

        return false;
    }
    /**
     * Calcule l'alignement maximal possible autour d'un coup spécifié.
     * Cette méthode prend en compte toutes les directions possibles pour évaluer l'alignement maximal.
     *
     * @param ligne L'indice de la ligne où le jeton est placé.
     * @param colonne L'indice de la colonne où le jeton est placé.
     * @param plateau Le plateau actuel de jeu.
     * @param joueurId L'identifiant du joueur effectuant le coup.
     * @return Le nombre maximum de jetons consécutifs pouvant être formés à partir de ce coup.
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