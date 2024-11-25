package Modele;

import Vue.Ihm;

public class IAPlayer extends Joueur{


    /**
     * Constructeur de la classe Joueur.
     *
     */
    public IAPlayer() {
        super("IA");
        this.id =2;
    }
    public int[] jouerCoup(IAStrategy strategy,Modele P) {
        return strategy.choisirCoup(P);
    }
}
