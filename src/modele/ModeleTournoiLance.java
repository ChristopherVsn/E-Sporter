package modele;
import java.util.List;

import DAO.ImpTournoiDAO;

public class ModeleTournoiLance {
	
	private ImpTournoiDAO tournoiDAO;
	private Tournoi tournoi;
	
	 /**
     * Initialise le modele avec un tournoi t
     * @param Tounoi t
     */
	public ModeleTournoiLance(Tournoi t) {
		this.tournoiDAO = new ImpTournoiDAO();
		this.tournoi = t;
	}
	 /**
     * Permet de récupérer la liste des équipes qui participe à un tournoi
     *
     * @return List d'équipe participant au tournoi
     */
	public List<EquipeTournoi> getEquipesTournoi(){
		return this.tournoiDAO.getEquipesTournoi(this.tournoi);
	}
	
	/**
	 *  Permet de supprimer le tournoi
	 */
	public void deleteTournoi() {
		this.tournoiDAO.delete(this.tournoi);
	}
	
	/**
	 * Permet de savoir dans quel phase du tournoi on est
	 * @return la phase en cours
	 * 
	 */
	public Phase getPhase() {
		return this.tournoiDAO.getTurnamentPhase(this.tournoi);
	}
	
	/**
	 * @return String contenant le nom des équipes en final
	 */
	public String getEquipesFinale() {
		Poule p = new Poule(this.tournoi);
		Match finale = p.getFinal();
		return finale.getNomEquipe1() + " VS " + finale.getNomEquipe2();
	}
	
	/**
	 * 
	 * @return Liste des arbitres qui arbitre le tournoi en cours
	 */
	public List<Arbitre> getArbitres(){
		return this.tournoiDAO.getArbitresByTournoi(this.tournoi);
	}
}

