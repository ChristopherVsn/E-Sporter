package modele;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.derby.tools.sysinfo;

import DAO.ImpEquipeDAO;
import DAO.ImpJoueurDAO;

public class ModeleCreationEquipe {

	private List<Equipe> equipesNonInscrites;
	private ImpEquipeDAO impEquipeDAO;
	private ImpJoueurDAO impJoueurDAO;

	/**
	 * Modele permettant la création d'une équipe
	 */
	public ModeleCreationEquipe() {
		this.impEquipeDAO = new ImpEquipeDAO();
		this.impJoueurDAO = new ImpJoueurDAO();
		this.equipesNonInscrites = this.impEquipeDAO.getEquipesNonInscrites();
	}

	/**
	 * ajoute une équipe à la base de données
	 * @param equipe à ajouter à la base de données
	 * @return true si ajout de l'équipe a bien était enregistrer dans la base de données
	 */
	public int addEquipe(EquipeSaison equipe)  {
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
					return 0;
				} else {
					return 1;
				}
			} else {				
				if( this.impEquipeDAO.addEquipeSaison(equipe) && this.impEquipeDAO.clearJoueurInEquipe(equipe)) {
					this.equipesNonInscrites.remove(equipe);
					System.out.println("ok");
					return 0;
				}else {
					System.out.println("wtf");
					return 3;
				}
			}
		}
		return 2;
	}
	
	/**
	 * vérifie s'il n'y a pas la présence de doublons dans la liste de joueurs et que chaque pseudo joueur ne dépasse pas 20 caractères
	 * @param liste de joueurs
	 * @return 0 si toute joueurs respectent les conditions, 1 si doublon et 2 si un joueur à plus de 20 caractères
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
	 * transforme une liste de pseudo de joueurs en liste de Joueurs
	 * @param liste de pseudo de jouer
	 * @param nom de l'équipe des joueurs
	 * @return une liste de Joueurs
	 */
	public List<Joueur> stringToJoueur(List<String> joueur, String nomEquipe){
		List<Joueur> joueurs = new ArrayList<>();
		for (int i = 0; i < joueur.size(); i++) {
			joueurs.add(new Joueur(joueur.get(i),nomEquipe));
		}
		return joueurs;
	}

	/**
	 * @return liste des équipes non inscrites a la saison en cours
	 */
	public List<Equipe> getEquipesNonInscrites() {
		return this.equipesNonInscrites;
	}

	/**
	 * @return la liste de tous les équipes inscrites à la base de données
	 */
	public List<Equipe> getAllEquipes() {
		return this.impEquipeDAO.getAll();
	}

	/**
	 * ajoute à la base de données les joueurs d'une équipe
	 * @param listent des joueurs à jouter
	 * @return un String "enregistrait" si tous les joueurs on pue être ajouté à la base donnée sinon le nom du joueur qui n'a pas pu être ajouté
	 */
	public String addJoueurs(List<Joueur> joueurs) {
		boolean ok = true;
		String error = "";
		int i = 0;
		int j;
		while (i < joueurs.size() && ok) {
			j = this.impJoueurDAO.existeInSaison(joueurs.get(i));
			if (j != 0 && j != 1) {
				error = joueurs.get(i).getPseudo();
				ok = false;
			}
			i++;
		}
		if(ok) {
			for (Joueur joueur : joueurs) {
				this.addJoueur(joueur);
			}
			error = "enregistrer";
		}
		return error;
	}

	/**
	 * ajout du joueur à la base donnée
	 * @param joueur
	 * @return true si le joueur a bien peu être ajouté à la base donnée
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
	 * @param nom de l'équipe à retourner
	 * @return l'équipe sinon null
	 */
	public Equipe getEquipeByName(String nomEquipe)  {
		for (Equipe e : this.impEquipeDAO.getAll()) {
			if (e.getNom().equals(nomEquipe)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * @param nom de l'équipe
	 * @return le world rank de l'année passé de l'équipe mise en paramètre sinon 1000
	 */
	public String getWorldRank(String nomEquipe) {

		List<EquipeSaison> equipes;
		try {
			equipes = EquipesSaison.getInstance(Calendar.getInstance().get(Calendar.YEAR) - 1)
					.getEquipes();
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