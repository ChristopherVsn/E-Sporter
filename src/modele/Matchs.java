package modele;

import java.util.Map;
/**
 * Représente un match dans un tournoi avec des équipes et d'autres informations associées.
 */
public class Matchs {
    private int idMatch;
    private String nomTournoi;
    private Map<String, Integer> equipes;
	private int idTournoi;

	/**
     * Constructeur de la classe Matchs.
     *
     * @param idMatch    Identifiant du match
     * @param nomTournoi Nom du tournoi auquel le match appartient
     * @param equipes    Association des noms d'équipes à leurs identifiants
     * @param idTournoi  Identifiant du tournoi auquel le match appartient
     */
    public Matchs(int idMatch, String nomTournoi, Map<String, Integer> equipes, int idTournoi) {
        this.idMatch = idMatch;
        this.nomTournoi = nomTournoi;
        this.equipes = equipes;
        this.idTournoi = idTournoi;
    }
    
    /**
     * Obtient l'identifiant du tournoi auquel le match appartient.
     *
     * @return Identifiant du tournoi
     */
    public int getIdTournoi() {
    	return this.idTournoi;
    }

    /**
     * Obtient l'identifiant du match.
     *
     * @return Identifiant du match
     */
    public int getIdMatch() {
        return idMatch;
    }

    /**
     * Obtient le nom du tournoi auquel le match appartient.
     *
     * @return Nom du tournoi
     */
    public String getNomTournoi() {
        return nomTournoi;
    }

    /**
     * Obtient l'association des noms d'équipes à leurs identifiants.
     *
     * @return Association des noms d'équipes à leurs identifiants
     */
    public Map<String, Integer> getEquipes() {
        return equipes;
    }

    /**
     * Définit l'identifiant du match.
     *
     * @param idMatch Nouvel identifiant du match
     */
    public void setIdMatch(int idMatch) {
        this.idMatch = idMatch;
    }

    /**
     * Définit le nom du tournoi auquel le match appartient.
     *
     * @param nomTournoi Nouveau nom du tournoi
     */
    public void setNomTournoi(String nomTournoi) {
        this.nomTournoi = nomTournoi;
    }

    /**
     * Définit l'association des noms d'équipes à leurs identifiants.
     *
     * @param equipes Nouvelle association des noms d'équipes à leurs identifiants
     */
    public void setEquipes(Map<String, Integer> equipes) {
        this.equipes = equipes;
    }

    /**
     * Obtient le nom de la première équipe du match.
     *
     * @return Nom de la première équipe
     */
    public String getNomEquipe1() {
        return this.equipes.keySet().toArray()[0].toString();
    }

    /**
     * Obtient le nom de la deuxième équipe du match.
     *
     * @return Nom de la deuxième équipe
     */
    public String getNomEquipe2() {
        return this.equipes.keySet().toArray()[1].toString();
    }
}