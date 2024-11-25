package Vue;


import java.util.Scanner;

public class IhmPuissance4 extends Ihm {


    /**
     * Demande à l'utilisateur de choisir une colonne.
     *
     * @return Le numéro de colonne choisi par l'utilisateur.
     */
    public int[] demanderChoix() {
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("\uD83D\uDE4B\u200D  Saisir la colonne ou vous voulez placer le pion ");
            if (!scanner.hasNextInt()) {
                System.out.println("🙅‍ Ce n'est pas un numéro valide. Veuillez essayer à nouveau:");
                scanner.next();
            }else {
                int nCol = scanner.nextInt();
                return new int[]{nCol, 0};
            }

        }
    }



    /**
     * Demande à l'utilisateur s'il souhaite intégrer la rotation dans la partie en cours.
     * L'utilisateur doit saisir 'O' (pour oui) ou 'N' (pour non).
     *
     * @return true si l'utilisateur souhaite intégrer la rotation, false sinon.
     */
    public boolean DemanderChoixOption() {
        String rep;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\uD83D\uDE4B\u200D  Souhaitez-vous intégrer la rotation dans cette partie? (O/N)");
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
     * Demande à l'utilisateur de choisir le type de rotation qu'il souhaite effectuer.
     * L'utilisateur doit saisir 1 pour une rotation à droite et 2 pour une rotation à gauche.
     *
     * @return Le choix de rotation de l'utilisateur (1 pour droite, 2 pour gauche).
     */
    public int DemanderTypeRotations(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\uD83D\uDE4B\u200D A quelle rotation voulez vous faire ?");
            System.out.println("- 1 Droite");
            System.out.println("- 2 Gauche");

            if (!scanner.hasNextInt()) {
                System.out.println("🙅‍ Saisie invalide. Veuillez essayer à nouveau: ");
                scanner.next();
            }else {
                int nChoix = scanner.nextInt();

                if (nChoix == 1) {
                    System.out.println("Vous avez choisie de faire une rotation a droite");
                } else if (nChoix == 2) {
                    System.out.println("Vous avez choisie de faire une rotations a gauche");
                }
                return nChoix;
            }
        }
    }
    /**
     * Demande à l'utilisateur s'il souhaite effectuer une rotation lors de ce tour.
     *
     * @return true si l'utilisateur choisit de faire une rotation, false sinon.
     */
    public boolean demander_choix_rotation() {
        String rep;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\uD83D\uDE4B\u200D  Souhaitez-vous faire une rotation dans ce tour ? (O/N)");
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

}
