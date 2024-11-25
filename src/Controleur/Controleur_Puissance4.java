package Controleur;

import Modele.*;
import Vue.Ihm;
import Vue.IhmPuissance4;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe de contrôle pour le jeu de Puissance 4.
 */
public class Controleur_Puissance4 extends Controleur_Jeu_a_deux {

    /**
     * Constructeur pour le contrôleur du jeu Puissance 4.
     * @param ihm Interface utilisateur pour interagir avec le joueur.
     */
    public Controleur_Puissance4(Ihm ihm) {
        super(ihm);
    }

    /**
     * Implémente l'action de jeu spécifique pour Puissance 4, incluant la gestion des coups de l'IA.
     * @param partie Modèle de jeu actuel.
     * @param J Joueur actuel.
     * @throws Exception Si une erreur survient lors du jeu (p.ex. coup invalide).
     */
    @Override
    void FaireActionJeu(Modele partie, Joueur J) throws Exception {
        if (partie instanceof ModelePuissence4 && ihm instanceof IhmPuissance4) {
            if (J instanceof IAPlayer) {
                Ihm.println("L'IA joue :");
                IAStrategy strategy = new Pussance4Strategy1();
                int choix = ((IAPlayer) J).jouerCoup(strategy, partie)[0];
                Ihm.println(choix);
                ((ModelePuissence4) partie).placer_pion(choix, J.getId());
            } else {
                int choix = ihm.demanderChoix()[0];
                if (choix < 0 || choix > 7) {
                    throw new Exception("🙅‍ Le numéro doit être compris entre 0 et 7. Veuillez essayer à nouveau:");
                }
                ((ModelePuissence4) partie).placer_pion(choix, J.getId());
            }
        } else {
            throw new Exception("Erreur de configuration");
        }
    }

    /**
     * Crée le modèle spécifique pour le jeu de Puissance 4.
     * @param J1 Nom du joueur 1.
     * @param J2 Nom du joueur 2.
     * @return Une nouvelle instance de ModelePuissance4.
     */
    @Override
    Modele ChoisirModeleJeu(String J1, String J2) {return new ModelePuissence4(J1, J2);}

    /**
     * Gère les options spéciales pour une partie de Puissance 4, incluant les rotations et les choix des joueurs.
     * @param partie Modèle de jeu courant.
     * @param joueurCourant Joueur en train de jouer.
     * @return Map contenant le modèle de jeu mis à jour et le joueur suivant.
     */
    @Override
    Map<Integer, Objects> PartieAvecOption(Modele partie, Joueur joueurCourant) {
        String nomCourant;
        if (partie instanceof ModelePuissence4 && ihm instanceof IhmPuissance4) {

            // Boucle de jeu continue jusqu'à la fin du jeu.
            while (!partie.finJeu(joueurCourant.getId())) {
                nomCourant = joueurCourant.getNom();
                Ihm.println(nomCourant + " à vous de jouer \n"+ partie);
                Ihm.println("Il vous reste " + (4 - joueurCourant.getCaracteristiqueJoueur()) + " rotations.");
                boolean choixValide = false;
                while (!choixValide) {
                    try {
                        if (joueurCourant.getCaracteristiqueJoueur() < 4) {
                            if (joueurCourant instanceof IAPlayer) {
                                Ihm.println("L'IA joue :");
                                IAStrategy strategy = new Puissance4Strategy2();
                                int[] nChoixIA = ((IAPlayer) joueurCourant).jouerCoup(strategy, partie);
                                if (nChoixIA[1] == 0) {
                                    Ihm.println("Pas de rotations :");
                                    Ihm.println(nChoixIA[0]);
                                    ((ModelePuissence4) partie).placer_pion(nChoixIA[0], joueurCourant.getId());
                                } else if (nChoixIA[1] == 1) {
                                    Ihm.println("Rotations a Droite ");
                                    ((ModelePuissence4) partie).tournerADroite();
                                    joueurCourant.adjcaracteristiqueJoueur();
                                } else if (nChoixIA[1] == 2) {
                                    Ihm.println("Rotations a Gauche ");
                                    ((ModelePuissence4) partie).tournerAgauche();
                                    joueurCourant.adjcaracteristiqueJoueur();
                                }
                            } else {
                                if (((IhmPuissance4) ihm).demander_choix_rotation()) {
                                    int nChoixRotation = ((IhmPuissance4) ihm).DemanderTypeRotations();
                                    if (nChoixRotation == 1) {
                                        ((ModelePuissence4) partie).tournerADroite();
                                    } else if (nChoixRotation == 2) {
                                        ((ModelePuissence4) partie).tournerAgauche();
                                    } else {
                                        throw new Exception("🙅‍ Ce n'est pas un numéro valide. Veuillez essayer à nouveau:");
                                    }
                                    joueurCourant.adjcaracteristiqueJoueur();
                                    if (joueurCourant.getCaracteristiqueJoueur() == 4) {
                                        Ihm.println("vous ne pouvez plus faire de rotations");
                                    }

                                } else {
                                    int nChoixCol = ihm.demanderChoix()[0];
                                    ((ModelePuissence4) partie).placer_pion(nChoixCol, joueurCourant.getId());
                                }
                            }
                        }else {
                            if (joueurCourant instanceof IAPlayer) {
                                Ihm.println("L'IA joue :");
                                IAStrategy strategy = new Pussance4Strategy1();
                                int choix = ((IAPlayer) joueurCourant).jouerCoup(strategy, partie)[0];
                                Ihm.println(choix);
                                ((ModelePuissence4) partie).placer_pion(choix, joueurCourant.getId());
                            }else {
                                int nChoixCol = ihm.demanderChoix()[0];
                                ((ModelePuissence4) partie).placer_pion(nChoixCol, joueurCourant.getId());
                            }
                            }
                        choixValide = true;
                    } catch (Exception e) {
                        Ihm.println(e.getMessage() + "Veuillez essayer à nouveau:");
                    }
                }
                if (!partie.finJeu(joueurCourant.getId())) {
                    joueurCourant = partie.joueurSuivant(joueurCourant);
                }
            }

        }
        Map resultaPartie = new HashMap<>(2);
        resultaPartie.putIfAbsent(2, joueurCourant);
        resultaPartie.putIfAbsent(1, partie);
        return resultaPartie;
    }
    /**
     * Initialise la partie en configurant les paramètres spécifiques avant le début du jeu.
     * @param P Modèle de jeu qui doit être configuré avant le début de la partie.
     */
    @Override
    void DemmandeAvantPartie(Modele P) {

    }

}
