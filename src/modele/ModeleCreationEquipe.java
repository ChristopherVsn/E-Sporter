package modele;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.derby.tools.sysinfo;

import DAO.ImpEquipeDAO;
import DAO.ImpJoueurDAO;
/**
 * Modèle facilitant la création d'une équipe.
 */
public class ModeleCreationEquipe {

	private List<Equipe> equipesNonInscrites;
	private ImpEquipeDAO impEquipeDAO;
	private ImpJoueurDAO impJoueurDAO;

	/**
     * Constructeur du modèle permettant la création d'une équipe.
     */
	public ModeleCreationEquipe() {
		this.impEquipeDAO = new ImpEquipeDAO();
		this.impJoueurDAO = new ImpJoueurDAO();
		this.equipesNonInscrites = this.impEquipeDAO.getEquipesNonInscrites();
	}

	/**
     * Ajoute une équipe à la base de données.
     * 
     * @param equipe à ajouter à la base de données
     * @return 0 si l'ajout de l'équipe a bien été enregistré dans la base de données,
     *         1 en cas d'échec d'ajout, 2 si l'équipe appartient à une saison
     *         existante, 3 si l'équipe existe déjà et ses joueurs ont été mis à jour.
     */
	public int addEquipe(EquipeSaison equipe) {
		boolean existe = false;
		boolean presentSaison = false;
		for (Equipe e : this.impEquipeDAO.getAll()) {
			if (e.getNom().equals(equipe.getNom())) {
				existe = true;
			}
		}
		for (EquipeSaison e : this.impEquipeDAO.getAllByAnnee(Calendar.getInstance().get(Calendar.YEAR))) {
			if (e.getNom().equals(equipe.getNom())) {
				presentSaison = true;
			}
		}
		if (!presentSaison) {
			if (!existe) {
				if (this.impEquipeDAO.add(equipe)) {
					System.out.println("ok");
					return 0;
				} else {
					return 1;
				}
			} else {
				if (this.impEquipeDAO.addEquipeSaison(equipe) && this.impEquipeDAO.clearJoueurInEquipe(equipe)) {
					this.equipesNonInscrites.remove(equipe);
					return 0;
				} else {
					return 3;
				}
			}
		}
		return 2;
	}

	/**
     * Vérifie s'il n'y a pas de doublons dans la liste de joueurs et que chaque
     * pseudo joueur ne dépasse pas 20 caractères.
     * 
     * @param joueur liste de joueurs
     * @return 0 si tous les joueurs respectent les conditions, 1 s'il y a un doublon,
     *         2 si un joueur a plus de 20 caractères.
     */
	public int testJoueurSaisie(List<Joueur> joueur) {
		int listeJoueursValide = 0;
		for (int i = 0; i < joueur.size(); i++) {
			for (int j = i + 1; j < joueur.size(); j++) {
				if (joueur.get(i).getPseudo().equals(joueur.get(j).getPseudo())) {
					listeJoueursValide = 1;
				}
				if (joueur.get(i).getPseudo().length() > 20) {
					listeJoueursValide = 2;
				}
			}
		}
		return listeJoueursValide;
	}

	/**
     * Transforme une liste de pseudos de joueurs en liste de Joueurs.
     * 
     * @param joueur     liste de pseudos de joueurs
     * @param nomEquipe  nom de l'équipe des joueurs
     * @return une liste de Joueurs
     */
	public List<Joueur> stringToJoueur(List<String> joueur, String nomEquipe) {
		List<Joueur> joueurs = new ArrayList<>();
		for (int i = 0; i < joueur.size(); i++) {
			joueurs.add(new Joueur(joueur.get(i), nomEquipe));
		}
		return joueurs;
	}

	/**
     * Renvoie la liste des équipes non inscrites à la saison.
     * 
     * @return List<Equipe>
     */
	public List<Equipe> getEquipesNonInscrites() {
		return this.impEquipeDAO.getEquipesNonInscrites();
	}

	/**
     * Retourne la liste de toutes les équipes inscrites à la base de données.
     * 
     * @return List<Equipe>
     */
	public List<Equipe> getAllEquipes() {
		return this.impEquipeDAO.getAll();
	}

	/**
	 * Vérifie la validité des joueurs avant leur ajout à la base de données.
	 * 
	 * @param joueurs liste des joueurs à vérifier
	 * @return "true" si tous les joueurs sont valides, sinon le pseudo du premier joueur
	 *         invalide rencontré.
	 */
	public String joueurValide(List<Joueur> joueurs) {
		boolean ok = true;
		String error = "true";
		int i = 0;
		int j;
		while (i < joueurs.size() && ok) {
			j = this.impJoueurDAO.existeInSaison(joueurs.get(i));
			System.out.println(j);
			if (j != 0 && j != 1) {
				error = joueurs.get(i).getPseudo();
				ok = false;
			}
			i++;
		}
		return error;
	}

	/**
     * Ajoute à la base de données les joueurs d'une équipe.
     * 
     * @param joueurs liste des joueurs à ajouter
     * @return true si tous les joueurs ont pu être ajoutés à la base de données,
     *         false sinon.
     */
	public boolean addJoueurs(List<Joueur> joueurs) {
		for (Joueur joueur : joueurs) {
			if (!this.addJoueur(joueur)) {
				return false;
			}
		}
		return true;
	}

	/**
     * Ajoute le joueur à la base de données.
     * 
     * @param joueur joueur à ajouter
     * @return true si le joueur a bien été ajouté à la base de données, false sinon.
     */
	private boolean addJoueur(Joueur joueur) {
		try {
			int i = this.impJoueurDAO.existeInSaison(joueur);
			if (i == 0) {
				return this.impJoueurDAO.add(joueur);
			} else if (i == 1) {
				return this.impJoueurDAO.update(joueur);
			} else if (i == 2) {
				return false;
			} else {
				// error
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
     * Récupère l'équipe par son nom.
     * 
     * @param nomEquipe nom de l'équipe à retourner
     * @return l'équipe correspondante, sinon null.
     */
	public Equipe getEquipeByName(String nomEquipe) {
		for (Equipe e : this.impEquipeDAO.getAll()) {
			if (e.getNom().equals(nomEquipe)) {
				return e;
			}
		}
		return null;
	}

	/**
     * Récupère le World Rank de l'année passée de l'équipe.
     * 
     * @param nomEquipe nom de l'équipe
     * @return le World Rank de l'année passée de l'équipe, sinon "1000".
     */
	public String getWorldRank(String nomEquipe) {

		List<EquipeSaison> equipes;
		try {
			equipes = EquipesSaison.getInstance(Calendar.getInstance().get(Calendar.YEAR) - 1).getEquipes();
			for (EquipeSaison e : equipes) {
				if (e.getNom().equals(nomEquipe)) {
					return String.valueOf(e.getWr());
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return "1000";
	}

}