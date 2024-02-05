package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import DAO.ConnexionDB;
import DAO.ImpArbitreDAO;

public class ModeleArbitre {
	private ImpArbitreDAO arbitreDAO;

	/**
	 * initialise le modèle, en créant une liste avec tous les arbitres et une autre
	 * vide d'arbitres sélectionnés
	 */
	public ModeleArbitre() {
		this.arbitreDAO = new ImpArbitreDAO();
	}

	/**
	 * @param prend le nom et le prénom d'un arbitre, et return cet arbitre
	 * @return l'arbitre trouvé
	 * @throws Exception
	 */
	public Arbitre getArbitre(String nom, String prenom) {
		return this.arbitreDAO.getByNP(nom, prenom);
	}

	/**
	 * @param ajoute un arbitre "arbitre" à la BD s'il n'existe pas déjà
	 * @throws Exception 
	 */
	public boolean ajouterArbitreBD(Arbitre arbitre) {
		return this.arbitreDAO.add(arbitre);
	}
	/**
	 * Supprime un arbitre de la base de données.
	 *
	 * @param arbitre L'arbitre à supprimer.
	 * @return true si l'arbitre a été supprimé avec succès, sinon false.
	 */
	public boolean deleteArbitreBD(Arbitre arbitre) {
		try {
			return this.arbitreDAO.delete(arbitre);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * Obtient la liste de tous les arbitres enregistrés dans la base de données.
	 *
	 * @return La liste de tous les arbitres.
	 */
	public List<Arbitre> getAllArbitres() {
		return this.arbitreDAO.getAll();
	} 

}
