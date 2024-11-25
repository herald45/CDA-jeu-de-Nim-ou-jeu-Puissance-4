package Vue;


import java.util.Scanner;

public abstract class Ihm  {



    /**
     * Affiche un message.
     * @param s Le message à afficher.
     */
    public static void println(Object s) {
        System.out.println(s);
    }



    /**
     * Demande à l'utilisateur quel jeu il souhaite jouer et renvoie son choix.
     * L'utilisateur doit saisir 1 pour le Jeu de Nim et 2 pour Puissance 4.
     *
     * @return Le choix de jeu de l'utilisateur (1 pour Jeu de Nim, 2 pour Puissance 4).
     */
    public static int DemanderJeu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\uD83D\uDE4B\u200D A quelle jeu vouler vous jouer ?");
            System.out.println("- 1 Jeu de Nim ||");
            System.out.println("- 2 Puissance 4 \uD83D\uDD34 \uD83D\uDFE1");

            if (!scanner.hasNextInt()) {
                System.out.println("🙅‍ Saisie invalide. Veuillez essayer à nouveau: ");
                scanner.next();
            }else {
                return scanner.nextInt();
            }


        }
    }

    public static boolean DemanderIA() {
        String rep;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\uD83D\uDE4B\u200D  Souhaitez-vous jouer contre l’IA ? (O/N)");
            rep = sc.next();
            if (rep.matches("[Oo]")) {
                return true;
            } else if (rep.matches("[Nn]")) {
                return false;
            } else {
                System.out.println("🙅‍ Saisir soit O ou N (O/N)");
            }
        }
    }


    /**
     * Demande à l'utilisateur d'entrer le nom d'un joueur.
     *
     * @param num Le numéro du joueur.
     * @return Le nom du joueur entré par l'utilisateur.
     */
    public String choisirNom(String num) {
        String nom;
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Joueur "+ num );
            System.out.println("\uD83D\uDE4B\u200D️ Entrez votre nom");//smyley leve la main
            nom = sc.next();
            if (nom.matches("[a-zA-Z]{1,20}")) {
                System.out.println("Joueur crée!");
                return nom;
            } else {
                System.out.println("Nom incorrect");
            }
        }
    }
    /**
     * Affiche le nom du gagnant de la partie.
     *
     * @param nom Le nom du joueur gagnant.
     */
    public void afficherGagnantPartie(String nom) {
        System.out.println(
                "\uD83C\uDF89" + " " + nom + " a gagner !!"
        );
    }

    /**
     * Demande à l'utilisateur s'il souhaite rejouer.
     * L'utilisateur doit saisir 'O' (pour oui) ou 'N' (pour non).
     *
     * @return true si l'utilisateur souhaite rejouer, false sinon.
     */
    public boolean demanderRejouer() {
        String rep;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\uD83D\uDE4B\u200D  Souhaitez-vous rejouer? (O/N)");
            rep = sc.next();
            if (rep.matches("[Oo]")) {
                return true;
            } else if (rep.matches("[Nn]")) {
                return false;
            } else {
                System.out.println("🙅‍ Saisir soit O ou N (O/N)");
            }
        }
    }

    /**
     * Affiche le gagnant de toutes les parties jouées.
     * @param nom Le nom du gagnant.
     * @param partiesGagnees Le nombre de parties gagnées par le joueur.
     */
    public void afficherGagnant(String nom, int partiesGagnees) {
        String s = partiesGagnees > 1 ? "s" : "";
        System.out.println(
                "\uD83C\uDF89" + " " + nom + " gagne avec " + partiesGagnees + " partie"+s+" gagnée"+s+ " " + "\uD83D\uDC4F"
        );
    }

    public abstract int[] demanderChoix();

    public abstract boolean DemanderChoixOption() ;


}
