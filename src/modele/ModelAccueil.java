package modele;

import java.util.List;
import java.util.stream.Collectors;

import DAO.ImpTournoiDAO;

public class ModelAccueil {
	private ImpTournoiDAO tournoiDAO;
	
	
	public ModelAccueil() {
		this.tournoiDAO = new ImpTournoiDAO();
	}
	
	public boolean turnamentInProcess() {
		for (Tournoi t:this.tournoiDAO.getAll()) {
			Phase phaseTournoi = this.tournoiDAO.getTurnamentPhase(t);
			if(phaseTournoi != Phase.NOT_STARTED && phaseTournoi != Phase.CLOSED) {
				return true;
			}
		}
		return false;
	}
	
	public Tournoi getTurnamentInProcess() {
		for (Tournoi t:this.tournoiDAO.getAll()) {
			Phase phaseTournoi = this.tournoiDAO.getTurnamentPhase(t);
			if(phaseTournoi != Phase.NOT_STARTED && phaseTournoi != Phase.CLOSED) {
				return t;
			}
		}
		return null;
	}
	
	public List<String> getArbitresForTurnamentInProcess(){
		List<Arbitre> arbitres = this.tournoiDAO.getArbitresByTournoi(getTurnamentInProcess());
		return arbitres.stream().map(entry -> entry.getPrenom()).collect(Collectors.toList());
	}
}
