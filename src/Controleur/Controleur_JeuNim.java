package Controleur;

import Modele.*;
import Vue.Ihm;
import Vue.IhmJeuNim;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Controleur_JeuNim extends Controleur_Jeu_a_deux{
    /**
     * Constructeur du contrôleur pour le jeu de Nim.
     * @param ihm Interface homme-machine spécifique au jeu de Nim.
     */
    public Controleur_JeuNim(Ihm ihm) {
        super(ihm);
    }

    /**
     * Implémentation de l'action de jeu pour le jeu de Nim.
     * Distingue entre les joueurs IA et humains et effectue les actions correspondantes.
     * @param partie Modèle du jeu actuel sur lequel les actions sont effectuées.
     * @param J Joueur actuel qui doit jouer.
     * @throws Exception Lève une exception si le coup n'est pas valide ou en cas d'erreur de configuration.
     */
    @Override
    void FaireActionJeu(Modele partie, Joueur J) throws Exception {
        if (partie instanceof ModeleJeuNim && ihm instanceof IhmJeuNim) {
            if (J instanceof IAPlayer) {
                Ihm.println("L'IA joue :");
                IAStrategy strategy = new JeuNimStrategyGagnante();
                int[] choix = ((IAPlayer) J).jouerCoup(strategy, partie);
                Ihm.println(choix[0] + " " + choix[1]);
                ((ModeleJeuNim) partie).enlever(choix[0], choix[1]);
            } else {
                int[] choix = ihm.demanderChoix();
                ((ModeleJeuNim) partie).enlever(choix[0], choix[1]);
            }
        } else {
            throw new Exception("Erreur: configuration incorrecte.");
        }
    }

    /**
     * Sélectionne le modèle de jeu approprié pour une partie de Nim.
     * @param J1 Nom du premier joueur.
     * @param J2 Nom du deuxième joueur.
     * @return Retourne une nouvelle instance de ModeleJeuNim.
     */
    @Override
    Modele ChoisirModeleJeu(String J1, String J2) {
        return new ModeleJeuNim(J1, J2);
    }

    /**
     * Gère une partie de jeu de Nim avec des options supplémentaires spécifiques au jeu.
     * @param partie Modèle de jeu de Nim sur lequel la partie est jouée.
     * @param joueurCourant Joueur qui est actuellement en train de jouer.
     * @return Map contenant les informations de la partie en cours, incluant le joueur actuel et le modèle de jeu.
     */
    @Override
    Map<Integer, Objects> PartieAvecOption(Modele partie, Joueur joueurCourant) {
        String nomCourant;
        if (partie instanceof ModeleJeuNim && ihm instanceof IhmJeuNim ) {
            // Boucle de jeu continue jusqu'à la fin du jeu
            int nMax=((IhmJeuNim) ihm).DemmanderMax();
            while (!partie.finJeu(joueurCourant.getId())) {
                nomCourant = joueurCourant.getNom();
                Ihm.println(nomCourant + " à vous de jouer \n" + partie);
                boolean choixValide = false;
                while (!choixValide) {
                    try {
                        if (joueurCourant instanceof IAPlayer){
                            Ihm.println("L'IA joue :");
                            IAStrategy strategy = new JeuNimStrategyAleatoire(nMax);
                            int[] choix = ((IAPlayer) joueurCourant).jouerCoup(strategy,partie);
                            Ihm.println(choix[0] +" "+ choix[1]);
                            ((ModeleJeuNim) partie).enlever(choix[0],choix[1]);
                        }else{
                            int[] nchoix = ihm.demanderChoix();
                            if (nchoix[1]> nMax){
                                throw new  Exception("🙅‍ Ce n'est pas un numéro valide pour le nombre d'allumette. Veuillez essayer à nouveau:");
                            }
                            ((ModeleJeuNim) partie).enlever(nchoix[0], nchoix[1]);
                        }

                        choixValide = true;
                    } catch (Exception e) {
                        Ihm.println(e.getMessage() + "\n");
                        Ihm.println("Veuillez essayer à nouveau:");
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
     * Prépare et initialise la partie de Nim avant de commencer à jouer.
     * @param partie Modèle de jeu de Nim qui doit être préparé.
     */
    @Override
    void DemmandeAvantPartie(Modele partie) {
        if (partie instanceof ModeleJeuNim && ihm instanceof IhmJeuNim ) {
            ((ModeleJeuNim) partie).setnTas(((IhmJeuNim) ihm).DemanderTas());
            partie.regenererplateau();
        }
    }
}
