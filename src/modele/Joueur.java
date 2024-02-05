package modele;
/**
 * Classe représentant un joueur avec un pseudo et le nom de son équipe.
 */
public class Joueur {
    private String pseudo;
    private String nomEquipe;

    /**
     * Constructeur de la classe Joueur.
     *
     * @param pseudo    Pseudo du joueur
     * @param nomEquipe Nom de l'équipe du joueur
     */
    public Joueur(String pseudo, String nomEquipe) {
        this.pseudo = pseudo;
        this.nomEquipe = nomEquipe;
    }

    /**
     * Obtient le pseudo du joueur.
     *
     * @return Pseudo du joueur
     */
    public String getPseudo() {
        return this.pseudo;
    }

    /**
     * Obtient le nom de l'équipe du joueur.
     *
     * @return Nom de l'équipe du joueur
     */
    public String getnomEquipe() {
        return this.nomEquipe;
    }

    /**
     * Modifie le pseudo du joueur.
     *
     * @param pseudo Nouveau pseudo du joueur
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Modifie le nom de l'équipe du joueur.
     *
     * @param nomEquipe Nouveau nom de l'équipe du joueur
     */
    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

}