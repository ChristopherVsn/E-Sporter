package modele;

import java.util.List;
import java.util.stream.Collectors;

import DAO.ImpTournoiDAO;
/**
 * Modèle pour la page d'accueil permettant de vérifier si au moins un tournoi est en cours.
 */
public class ModelAccueil {
	private ImpTournoiDAO tournoiDAO;
	
	/**
     * Initialise un nouveau modèle d'accueil.
     */
	public ModelAccueil() {
		this.tournoiDAO = new ImpTournoiDAO();
	}
	 /**
     * Vérifie si au moins un tournoi est en cours.
     *
     * @return true si au moins un tournoi est en cours, sinon false.
     */
	public boolean turnamentInProcess() {
		for (Tournoi t:this.tournoiDAO.getAll()) {
			Phase phaseTournoi = this.tournoiDAO.getTurnamentPhase(t);
			if(phaseTournoi != Phase.NOT_STARTED && phaseTournoi != Phase.CLOSED) {
				return true;
			}
		}
		return false;
	}
	/**
     * Obtient le tournoi en cours s'il y en a un.
     *
     * @return Le tournoi en cours, ou null s'il n'y en a pas.
     */
	public Tournoi getTurnamentInProcess() {
		for (Tournoi t:this.tournoiDAO.getAll()) {
			Phase phaseTournoi = this.tournoiDAO.getTurnamentPhase(t);
			if(phaseTournoi != Phase.NOT_STARTED && phaseTournoi != Phase.CLOSED) {
				return t;
			}
		}
		return null;
	}
	/**
     * Obtient la liste des prénoms des arbitres pour le tournoi en cours.
     *
     * @return Liste des prénoms des arbitres pour le tournoi en cours.
     */
	public List<String> getArbitresForTurnamentInProcess(){
		List<Arbitre> arbitres = this.tournoiDAO.getArbitresByTournoi(getTurnamentInProcess());
		return arbitres.stream().map(entry -> entry.getPrenom()).collect(Collectors.toList());
	}
}
