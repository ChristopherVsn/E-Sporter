package modele;

import java.util.Map;

/**
 * Représente un match dans un tournoi avec des équipes, un tournoi associé et d'autres informations.
 */
public class Match {
    private int idMatch;
    private String nomTournoi;
    private Map<String, Integer> equipes;
	private int idTournoi;

	/**
     * Constructeur de la classe Match.
     *
     * @param idMatch    Identifiant du match
     * @param nomTournoi Nom du tournoi auquel le match appartient
     * @param equipes    Association des noms d'équipes à leurs scores
     * @param idTournoi  Identifiant du tournoi auquel le match appartient
     */
    public Match(int idMatch, String nomTournoi, Map<String, Integer> equipes, int idTournoi) {
        this.idMatch = idMatch;
        this.nomTournoi = nomTournoi;
        this.equipes = equipes;
        this.idTournoi = idTournoi;
    }
    
    /**
     * Obtient l'équipe gagnante du match.
     *
     * @return nom de l'équipe
     */
    public String getWinner() {
    	return this.equipes.entrySet().stream().max((entry1, entry2)-> Integer.compare(entry1.getValue(),entry2.getValue())).get().getKey();
    }
    
    /**
     * Obtient l'équipe perdante du match.
     *
     * @return nom de l'équipe
     */
    public String getLoser() {
    	return this.equipes.entrySet().stream().min((entry1, entry2)-> Integer.compare(entry1.getValue(),entry2.getValue())).get().getKey();
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
     * Obtient l'association des noms d'équipes à leurs scores.
     *
     * @return Association des noms d'équipes à leurs scores
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
     * Définit l'association des noms d'équipes à leurs scores.
     *
     * @param equipes Nouvelle association des noms d'équipes à leurs scores
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
    
    /**
     * Vérifie si le gagnant du match est défini (score différent de 0).
     *
     * @return true si le gagnant est défini, false sinon
     */
    public boolean isWinnerDefined() {
    	for(Integer score:this.equipes.values()) {
    		if (score != 0) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Crée une représentation textuelle du match.
     *
     * @return Représentation textuelle du match
     */
    @Override
    public String toString() {
    	return "match n°" + this.idMatch + " => " + this.getNomEquipe1() + " (" + this.equipes.get(this.getNomEquipe1()) +  ") - " +
    this.getNomEquipe2() +" (" + this.equipes.get(this.getNomEquipe2()) + ")";
    }
}