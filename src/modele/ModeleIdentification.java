package modele;

import DAO.ImpAdministrateurDAO;
import DAO.ImpTournoiDAO;
import ressources.Pages;
import vue.VueMain;
import vue.VueMatchsPouleArbitre;

/**
 * Modèle pour l'identification et l'accès aux fonctionnalités du système.
 */
public class ModeleIdentification {
	
	private ImpTournoiDAO tournoiDAO;
	private ImpAdministrateurDAO adminDAO;
	
	/**
	 * Constructeur par défaut.
	 * Initialise les objets ImpTournoiDAO et ImpAdministrateurDAO utilisés pour l'accès aux données.
	 */
	public ModeleIdentification() {
		this.tournoiDAO = new ImpTournoiDAO();
		this.adminDAO = new ImpAdministrateurDAO();
	}
	
	/**
	 * Récupère le tournoi associé à un administrateur.
	 *
	 * @param user L'administrateur dont on souhaite récupérer le tournoi.
	 * @return Le tournoi associé à l'administrateur.
	 */
	public Tournoi getTournoi(Administrateur user) {
		return this.tournoiDAO.getTournoiByLogin(user.getLogin(), user.getPassword());
	}
	
	/**
	 * Récupère la phase du tournoi.
	 *
	 * @param t Le tournoi concerné.
	 * @return La phase actuelle du tournoi.
	 */
	public Phase getPhase(Tournoi t) {
		return this.tournoiDAO.getTurnamentPhase(t);
	}
	
	/**
	 * Récupère la vue principale en fonction du tournoi.
	 *
	 * @param t Le tournoi concerné.
	 * @return La vue principale associée au tournoi.
	 */
	public VueMain getView(Tournoi t) {
		return new VueMatchsPouleArbitre(t);
	}
	
	/**
	 * Vérifie si l'utilisateur est un administrateur.
	 *
	 * @param user L'utilisateur à vérifier.
	 * @return true si l'utilisateur est un administrateur, sinon false.
	 */
	public boolean isUserAdmin(Administrateur user) {
		Administrateur admin = this.adminDAO.getAll().get(0);
		return user.getLogin().equals(admin.getLogin()) && user.getPassword().equals(admin.getPassword());
	}
	
	/**
	 * Vérifie si les informations de l'utilisateur ne sont pas vides.
	 *
	 * @param user L'utilisateur à vérifier.
	 * @return true si les informations ne sont pas vides, sinon false.
	 */
	public boolean isUserEmpty(Administrateur user) {
		return !user.getLogin().isEmpty() && !user.getPassword().isEmpty();
	}
}
