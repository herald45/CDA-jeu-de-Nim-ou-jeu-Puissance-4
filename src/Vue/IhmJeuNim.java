package Vue;

import java.util.Scanner;

/**
 * Interface homme-machine pour le jeu de Nim.
 * Fournit des méthodes pour interagir avec l'utilisateur afin de configurer et jouer une partie de Nim.
 */
public class IhmJeuNim extends Ihm {

    /**
     * Demande à l'utilisateur de spécifier le nombre de tas pour le jeu de Nim.
     *
     * @return Le nombre de tas choisi par l'utilisateur.
     */
    public int DemanderTas() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("🙋‍️ Combien de tas voulez-vous ?");
            if (sc.hasNextInt()) {
                int nb = sc.nextInt();
                if (nb>0) {
                    return nb; // Retourne le nombre si c'est un entier
                }
                else {
                    System.out.println("🙅‍️ Veuillez saisir un chiffre superieur a zero.");
                }
            } else {
                System.out.println("🙅‍️ Veuillez saisir un entier.");
                sc.next(); // Importante pour éviter une boucle infinie
            }
        }
    }
    /**
     * Invite l'utilisateur à jouer un coup en spécifiant le tas et le nombre d'allumettes à retirer.
     *
     * @return Un tableau d'entiers où le premier élément est le numéro du tas et le second le nombre d'allumettes à retirer.
     */
    public int[] demanderChoix() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Jouer un coup sous la forme 'm n' où m est le tas choisi et n le nombre d'allumettes à retirer dans ce tas");

        while (true) {
            if (!scanner.hasNextInt()) {
                System.out.println("🙅‍ Ce n'est pas un numéro valide pour le numero du tas. Veuillez essayer à nouveau:");
                scanner.next();
            }else {
                int nTas = scanner.nextInt();
                if (!scanner.hasNextInt()) {
                    System.out.println("🙅‍ Ce n'est pas un numéro valide pour le nombre d'allumette. Veuillez essayer à nouveau:");
                    scanner.next();
                }else {
                    int nbAllumettes = scanner.nextInt();
                    return new int[]{nTas, nbAllumettes};
                }
            }
        }
    }
    /**
     * Demande à l'utilisateur s'il souhaite intégrer une contrainte sur le nombre maximum d'allumettes par coup.
     *
     * @return true si l'utilisateur choisit d'appliquer une contrainte, false sinon.
     */
    public boolean DemanderChoixOption() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("saisir un entier positif correspondant à ce nombre maximum ou 0 si les joueurs ne\n" +
                    "souhaitent pas jouer avec une contrainte sur le nombre maximum d'allumettes.");
            while (!sc.hasNextInt()) {
                System.out.println("🙅‍ Ce n'est pas un numéro valide pour le nombre d'allumettes. Veuillez essayer à nouveau:");
                sc.next();
            }
            int nMax = sc.nextInt();
            if (nMax== 0) {
                return false;
            } else if (nMax > 0) {
                return true;
            } else {
                System.out.println("🙅‍ Ce n'est pas un numéro valide pour le nombre d'allumettes. Veuillez essayer à nouveau:");
            }
        }
    }
    /**
     * Demande à l'utilisateur de saisir le nombre maximum d'allumettes que l'on peut retirer par coup.
     *
     * @return Le nombre maximum d'allumettes que l'on peut retirer, spécifié par l'utilisateur.
     */
    public int DemmanderMax() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Confirmer le max");

        while (true) {

            while (!sc.hasNextInt()) {
                System.out.println("🙅‍ Ce n'est pas un numéro valide pour le nombre d'allumettes. Veuillez essayer à nouveau:");
                sc.next();
            }
            int nMax = sc.nextInt();
            if (nMax<1){
                System.out.println("🙅‍ Ce n'est pas un numéro valide pour le nombre d'allumettes. Veuillez essayer à nouveau:");
                sc.next();
            }else {
                return nMax;
            }

        }
    }


}


