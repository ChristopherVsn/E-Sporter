package modele;

import java.util.List;

import DAO.ImpMatchsDAO;
import DAO.ImpTournoiDAO;

public class ModeleSelectionVainqueurMatch {
	private ImpMatchsDAO matchsDAO;
	private ImpTournoiDAO tournoiDAO;
	private Tournoi t;

	public ModeleSelectionVainqueurMatch(Tournoi t) {
		this.matchsDAO = new ImpMatchsDAO();
		this.tournoiDAO = new ImpTournoiDAO();
		this.t = t;
	}

	/**
	 * 
	 * @return tous les matchs du tournoi
	 */
	public List<Match> getAll() {
		return matchsDAO.getMatchsByTournoi(this.t);
	}

	public void setScoreEquipe(String equipe, int score, int idMatch) {
		matchsDAO.setScoreEquipe(idMatch, equipe, score, t.getNom());
	}

	public String scoreEquipeMatchs() {
		return matchsDAO.scoreEquipeMatchs(t.getNom());
	}
	

	public int getNbMatchsTerminees() {
		return this.tournoiDAO.getNbMatchsOverByTournoi(this.t);
	}

	public Match getFinalistes() {
		Poule poule = new Poule(this.t);
		return poule.getFinal();
	}

	public Phase getPhase() {
		int nbEquipe = this.tournoiDAO.getNbEquipesByTournoi(this.t);
		int nbMatchPoule = (nbEquipe * (nbEquipe -1))/2;
		int nbMatchOver = this.tournoiDAO.getNbMatchsOverByTournoi(t);
		if(nbMatchOver < nbMatchPoule) {
			return Phase.POULE;
		} else if(nbMatchOver == nbMatchPoule) {
			return Phase.FINALE;
		}
		return Phase.CLOSED;
	}
}
