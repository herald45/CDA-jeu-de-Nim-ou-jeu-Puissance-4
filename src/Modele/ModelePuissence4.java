package Modele;

import java.util.ArrayList;
import java.util.List;

/**
 * ModelePuissance4 étend la classe abstraite Modele pour implémenter les règles et comportements spécifiques du jeu Puissance 4.
 */
public class ModelePuissence4 extends Modele {


    public ModelePuissence4(String joueur1, String joueur2) {
        super(joueur1, joueur2);
    }

    /**
     * Checks if a player has won the game by forming a horizontal, vertical, or diagonal line of four tokens.
     *
     * @param joueur The player ID to check for a winning condition.
     * @return true if the player has won, false otherwise.
     */
    private boolean aGagne(int joueur) {
        // Vérification des lignes
        for (int ligne = 0; ligne < 7; ligne++) {
            int compteur = 0;
            for (int col = 0; col < 7; col++) {
                if (plateauDeJeu.get(ligne).get(col) == joueur) {
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
                if (plateauDeJeu.get(ligne).get(col) == joueur) {
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
                // Partie gauche du plateauDeJeu
                if (plateauDeJeu.get(j + incr).get(j) == joueur) {
                    comp1++;
                } else {
                    comp1 = 0;
                }
                // Partie droite du plateauDeJeu
                if (plateauDeJeu.get(j).get(j + incr) == joueur) {
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
                if (plateauDeJeu.get(j).get(t) == joueur) {
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

                if (plateauDeJeu.get(j).get(t) == joueur) {
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
     * Vérifie si le jeu est terminé.
     *
     * @param id Le numéro du joueur.
     * @return true si le jeu est terminé (le joueur spécifié a gagné ou le plateau est plein), sinon false.
     */
    public boolean finJeu(int id) {
        if (!aGagne(id)) {
            for (List<Integer> liste : this.plateauDeJeu) {
                for (int element : liste) {
                    if (element == 0) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return aGagne(id);
        }
    }

    /**
     * Régénère ou initialise le plateau de jeu avec une grille de 7x7.
     * Chaque cellule de la grille est mise à 0, indiquant une case vide où aucun joueur n'a encore placé de jeton.
     */
    @Override
    public void regenererplateau() {
        plateauDeJeu = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            List<Integer> ligne = new ArrayList<>();
            for (int j = 1; j <= 7; j++) {
                ligne.add(0);
            }
            plateauDeJeu.add(ligne);
        }
    }
    //tourner le plateau vers la gauche
    /**
     * Tourne le plateau de jeu de Puissance 4 vers la gauche.
     * Les pions sont déplacés dans le sens inverse des aiguilles d'une montre.
     */
    public void tournerAgauche() {
        // Création d'une copie de la grille
        List<List<Integer>> grilleCopie = new ArrayList<>();
        for (List<Integer> ligne : plateauDeJeu) {
            grilleCopie.add(new ArrayList<>(ligne));
        }
        // Rotation de chaque élément de la grille
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
                int res = plateauDeJeu.get(j).get(6 - i);
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
        // Met à jour la grille avec la copie modifiée
        plateauDeJeu = grilleCopie;
    }

    /**
     * Tourne le plateauDeJeu de jeu de Puissance 4 vers la droite.
     * Les pions sont déplacés dans le sens des aiguilles d'une montre.
     */
    public void tournerADroite() {
        // Création d'une copie de la grille
        List<List<Integer>> grilleCopie = new ArrayList<>();
        for (List<Integer> ligne : plateauDeJeu) {
            grilleCopie.add(new ArrayList<>(ligne));
        }
        // Rotation de chaque élément de la grille
        for (int i = 0; i < grilleCopie.size(); i++) {
            for (int j = 0; j < grilleCopie.get(i).size(); j++) {
                grilleCopie.get(i).set(j, plateauDeJeu.get(6 - j).get(i));
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
        // Met à jour la grille avec la copie modifiée
        plateauDeJeu = grilleCopie;
    }
    /**
     * Place un pion dans la colonne spécifiée pour le joueur donné.
     * Si la colonne est pleine, une exception est lancée.
     *
     * @param c La colonne où placer le pion (1-indexé).
     * @param id L'identifiant du joueur (1 pour le premier joueur, 2 pour le deuxième).
     * @throws Exception Si la colonne est pleine.
     */
    public void placer_pion(int c, int id) throws Exception {
        for (int i = 6; i >= 0; i--) {
            if (plateauDeJeu.get(i).get(c - 1) == 0) {
                plateauDeJeu.get(i).set(c - 1, id);
                return;
            }
        }
        throw new Exception("\uD83D\uDE45 plus de place dans la colonne. Veuillez essayer à nouveau:");
    }
    /**
     * Représentation graphique du plateau de jeu Puissance 4.
     *
     * @return Une chaîne de caractères représentant visuellement le plateau de jeu.
     */
    @Override
    public String toString() {
        StringBuilder grille = new StringBuilder();
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
                grille.append("|");
                if ((plateauDeJeu.get(i).get(j)) == 0) {
                    grille.append("⬛");
                }
                if ((plateauDeJeu.get(i).get(j)) == 1) {
                    grille.append("\uD83D\uDD34");
                }
                if ((plateauDeJeu.get(i).get(j)) == 2) {
                    grille.append("\uD83D\uDFE1");
                }

            }
            grille.append("|");
            grille.append("\n");
        }
        // Ajouter les numéros de colonnes de 1 à 7
        grille.append("| ");
        for (int j = 1; j <= 7; j++) {
            grille.append(String.format("%d  ", j));
        }
        grille.append("|");
        grille.append("\n");
        return grille.toString();
    }
}
