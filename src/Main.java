import Controleur.Controleur_JeuNim;
import Controleur.Controleur_Jeu_a_deux;
import Controleur.Controleur_Puissance4;
import Vue.Ihm;
import Vue.IhmJeuNim;
import Vue.IhmPuissance4;

public class Main {
    private static Controleur_Jeu_a_deux Jeu ;

    public static void main(String[] args) {
        while (true){
            try {
                int nChoixJeu = Ihm.DemanderJeu();
                if (nChoixJeu == 1) {
                    Ihm.println("Vous avez choisie Jeu de Nim");
                    IhmJeuNim ihm = new IhmJeuNim();
                    Jeu=new Controleur_JeuNim(ihm);
                    if(Ihm.DemanderIA()) {
                        Jeu.jouerPatronIA();
                    }else {
                        Jeu.jouerPatron();
                    }
                } else if (nChoixJeu == 2) {
                    Ihm.println("Vous avez choisie Le Puissance 4");
                    IhmPuissance4 ihm = new IhmPuissance4();
                    Jeu=new Controleur_Puissance4(ihm);
                    if(Ihm.DemanderIA()) {
                        Jeu.jouerPatronIA();
                    }else {
                        Jeu.jouerPatron();
                    }
                }else {
                    throw new Exception("🙅‍ Ce n'est pas un numéro valide. Veuillez essayer à nouveau:");
                }
                break;
            }catch (Exception e) {
                Ihm.println(e.getMessage());
            }
        }
    }


}