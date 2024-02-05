package modele;

import DAO.ImpAdministrateurDAO;
import DAO.ImpTournoiDAO;
import ressources.Pages;
import vue.VueMain;
import vue.VueMatchsPouleArbitre;

public class ModeleIdentification {
	
	private ImpTournoiDAO tournoiDAO;
	private ImpAdministrateurDAO adminDAO;
	
	public ModeleIdentification() {
		this.tournoiDAO = new ImpTournoiDAO();
		this.adminDAO = new ImpAdministrateurDAO();
	}
	
	public Tournoi getTournoi(Administrateur user) {
		return this.tournoiDAO.getTournoiByLogin(user.getLogin(), user.getPassword());
	}
	
	public Phase getPhase(Tournoi t) {
		return this.tournoiDAO.getTurnamentPhase(t);
	}
	
	public VueMain getView(Tournoi t) {
		return new VueMatchsPouleArbitre(t);
	}
	
	public boolean isUserAdmin(Administrateur user) {
		Administrateur admin = this.adminDAO.getAll().get(0);
		return user.getLogin().equals(admin.getLogin()) && user.getPassword().equals(admin.getPassword());
	}
	
	public boolean isUserEmpty(Administrateur user) {
		return !user.getLogin().isEmpty() && !user.getPassword().isEmpty();
	}
}
