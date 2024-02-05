package modele;
import java.util.List;

import DAO.ImpTournoiDAO;

public class ModeleTournoiLance {
	
	private ImpTournoiDAO tournoiDAO;
	private Tournoi tournoi;

	public ModeleTournoiLance(Tournoi t) {
		this.tournoiDAO = new ImpTournoiDAO();
		this.tournoi = t;
	}
	
	public List<EquipeTournoi> getEquipesTournoi(){
		return this.tournoiDAO.getEquipesTournoi(this.tournoi);
	}
	
	public void deleteTournoi() {
		this.tournoiDAO.delete(this.tournoi);
	}
	
	public Phase getPhase() {
		return this.tournoiDAO.getTurnamentPhase(this.tournoi);
	}
	
	public String getEquipesFinale() {
		Poule p = new Poule(this.tournoi);
		Match finale = p.getFinal();
		return finale.getNomEquipe1() + " VS " + finale.getNomEquipe2();
	}
	
	public List<Arbitre> getArbitres(){
		return this.tournoiDAO.getArbitresByTournoi(this.tournoi);
	}
}

