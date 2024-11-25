package Controleur;
import Vue.Ihm;
import Modele.*;
import Vue.IhmJeuNim;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe abstraite définissant le contrôleur pour un jeu à deux joueurs.
 */
abstract public class Controleur_Jeu_a_deux {
    protected Ihm ihm;

    /**
     * Constructeur pour initialiser le contrôleur avec une interface homme-machine.
     * @param ihm L'interface homme-machine pour interaction avec l'utilisateur.
     */
    public Controleur_Jeu_a_deux(Ihm ihm) {
        this.ihm = ihm;
    }

    /**
     * Méthode pour démarrer le jeu pour deux joueurs.
     */
    public void jouerPatron() {
        String joueur1 = this.ihm.choisirNom("1");
        String joueur2 = this.ihm.choisirNom("2");
        Modele partie = ChoisirModeleJeu(joueur1, joueur2);
        DemmandeAvantPartie(partie);
        lancerPartie(partie);
    }

    /**
     * Méthode pour démarrer le jeu contre l'IA.
     */
    public void jouerPatronIA() {
        String joueur1 = this.ihm.choisirNom("1");
        Modele partie = ChoisirModeleJeu(joueur1, "");
        partie.getLesJoueurs()[1] = new IAPlayer();
        DemmandeAvantPartie(partie);
        lancerPartie(partie);
    }

    /**
     * Lance et exécute la partie.
     * @param partie L'instance du modèle de jeu contenant la logique du jeu.
     */
    private void lancerPartie(Modele partie) {
        Joueur joueurCourant = partie.getLesJoueurs()[0];
        boolean choixRotationValide = false;
        while (!choixRotationValide) {
            try {
                if (!ihm.DemanderChoixOption()) {
                    Map resultat = PartieClassic(partie, joueurCourant);
                    partie = (Modele) resultat.get(1);
                    joueurCourant = (Joueur) resultat.get(2);
                } else {
                    Map resultat = PartieAvecOption(partie, joueurCourant);
                    partie = (Modele) resultat.get(1);
                    joueurCourant = (Joueur) resultat.get(2);
                }
                choixRotationValide = true;

            } catch (Exception e) {
                Ihm.println(e.getMessage());
            }
        }

        Ihm.println(partie.toString());
        if (partie.finJeu(joueurCourant.getId())) {
            this.ihm.afficherGagnantPartie(joueurCourant.getNom());
            joueurCourant.gagne();
        } else {
            Ihm.println("match nulle 🤝");
        }

        if (this.ihm.demanderRejouer()) {
            partie.regenererplateau();
            lancerPartie(partie);
        } else {
            comparerScores(partie);
        }
    }

    /**
     * Compare et affiche les scores des joueurs après la fin de la partie.
     * @param partie L'instance du modèle de jeu utilisé pour la partie.
     */
    private void comparerScores(Modele partie) {
        Joueur joueurCourant;
        if (partie.getLesJoueurs()[0].getPartiesGagnees() < partie.getLesJoueurs()[1].getPartiesGagnees()) {
            joueurCourant = partie.getLesJoueurs()[1];
        } else {
            joueurCourant = partie.getLesJoueurs()[0];
        }
        Ihm.println(partie.getLesJoueurs()[0].getNom() + " a " + partie.getLesJoueurs()[0].getPartiesGagnees()
                + " victoires \n"
                + partie.getLesJoueurs()[1].getNom() + " a " + partie.getLesJoueurs()[1].getPartiesGagnees()
                + " victoires");
        ihm.afficherGagnant(joueurCourant.getNom(), joueurCourant.getPartiesGagnees());
    }

    /**
     * Gère une partie classique sans options spéciales comme les rotations.
     * @param partie Modèle de jeu actuel.
     * @param joueurCourant Joueur actuel.
     * @return Map contenant le modèle de jeu et le joueur actuel mis à jour.
     */
    private Map<Integer, Objects> PartieClassic(Modele partie, Joueur joueurCourant) {
        String nomCourant;
        // Boucle de jeu continue jusqu'à la fin du jeu
        while (!partie.finJeu(joueurCourant.getId())) {
            nomCourant = joueurCourant.getNom();
            Ihm.println(nomCourant + " à vous de jouer \n"+ partie);
            boolean choixValide = false;
            while (!choixValide) {
                try {
                    FaireActionJeu(partie,joueurCourant);//methode a redefinir
                    choixValide = true;
                } catch (Exception e) {
                    Ihm.println(e.getMessage()+ "\n");
                    Ihm.println("Veuillez essayer à nouveau:");
                }
            }

            if (!partie.finJeu(joueurCourant.getId())) {
                joueurCourant = partie.joueurSuivant(joueurCourant);
            }
        }
        Map resultaPartie = new HashMap<>(2);
        resultaPartie.putIfAbsent(2, joueurCourant);
        resultaPartie.putIfAbsent(1, partie);

        return resultaPartie;
    }

    abstract void FaireActionJeu(Modele M1, Joueur J1) throws Exception;
    abstract Modele ChoisirModeleJeu(String J1, String J2);
    abstract Map<Integer, Objects> PartieAvecOption(Modele partie, Joueur joueurCourant);
    abstract void DemmandeAvantPartie(Modele P);
}

