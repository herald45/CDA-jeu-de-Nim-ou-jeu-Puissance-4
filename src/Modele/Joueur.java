package Modele;

/**
 * La classe Joueur représente un joueur dans un jeu.
 * Chaque joueur possède un identifiant unique, un nom et un nombre de parties gagnées.
 */
public class Joueur {
    static private int incr;
    protected int id;
    private final String nom;
    private int partiesGagnees;
    private int caracteristiqueJoueur;

    /**
     * Constructeur de la classe Joueur.
     *
     * @param nom Le nom du joueur.
     */
    public Joueur(String nom) {
        this.nom = nom;
        id = ++incr;
        partiesGagnees = 0;
    }

    /**
     * Obtient le nom du joueur.
     *
     * @return Le nom du joueur.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Obtient l'identifiant du joueur.
     *
     * @return L'identifiant du joueur.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtient le nombre de parties gagnées par le joueur.
     *
     * @return Le nombre de parties gagnées par le joueur.
     */
    public int getPartiesGagnees() {
        return partiesGagnees;
    }

    /**
     * Incrémente le nombre de parties gagnées par le joueur.
     */
    public void gagne() {
        this.partiesGagnees++;
    }

    /**
     * Renvoie la caractéristique du joueur.
     *
     * @return La caractéristique du joueur.
     */
    public int getCaracteristiqueJoueur() {
        return caracteristiqueJoueur;
    }

    /**
     * Incrémente la caractéristique du joueur.
     */
    public void adjcaracteristiqueJoueur() {
        this.caracteristiqueJoueur++;
    }


}
