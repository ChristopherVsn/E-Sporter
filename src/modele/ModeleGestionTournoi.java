package modele;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import DAO.ImpArbitreDAO;
import DAO.ImpMatchsDAO;
import DAO.ImpTournoiDAO;

public class ModeleGestionTournoi {
	private Tournoi tournoi;
	private List<EquipeSaison> allEquipes;
	private List<EquipeSaison> equipesDisponibles;
	private List<EquipeSaison> equipesParticipantes;

	private ImpArbitreDAO arbitreDAO;
	private ImpTournoiDAO tournoiDAO;
	private ImpMatchsDAO impMatchs;

	private List<Arbitre> allArbitres;
	private List<Arbitre> arbitresDisponibles;
	private List<Arbitre> arbitresSelectionnes;

	/**
	 * Initialise le modèle en récupérant à partir de l'année en cours toutes les
	 * équipes de la saison
	 * 
	 * @param t Tournoi, permet de sauvegarder les données dans le tournoi donné
	 */
	public ModeleGestionTournoi(Tournoi t) {
		EquipesSaison equipesSaison;
		try {
			equipesSaison = EquipesSaison.getInstance(Calendar.getInstance().get(Calendar.YEAR));
			this.allEquipes = equipesSaison.getEquipes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.equipesDisponibles = new ArrayList<EquipeSaison>();
		for(EquipeSaison e : this.allEquipes) {
			this.equipesDisponibles.add(e);
		}
		this.equipesParticipantes = new ArrayList<EquipeSaison>();
		
		this.arbitreDAO = new ImpArbitreDAO();
		this.tournoiDAO = new ImpTournoiDAO();
		this.impMatchs = new ImpMatchsDAO();
		
		this.tournoi = t;

		this.allArbitres = this.arbitreDAO.getAll();
		this.arbitresDisponibles = this.arbitreDAO.getAll();
		this.arbitresSelectionnes = new ArrayList<>();
	}

	/**
	 * @return les équipes sélectionnables, donc celles inscrites à la saison en
	 *         cours
	 */
	public List<String> getEquipesDispos() {
		List<String> equipes = new ArrayList<>();
		for(EquipeSaison e : this.equipesDisponibles) {
			equipes.add(e.getNom());
		}
		return equipes;
	}

	/**
	 * qui va sélectionner une équipe
	 * @param EquipeSaison e pour la retirer des équipes sélectionnables et
	 *                     l'ajouter aux équipes sélectionnées
	 */
	public void selectEquipe(EquipeSaison equipe) {
		this.equipesDisponibles.remove(equipe);
		this.equipesParticipantes.add(equipe);
	}
	
	public void selectArbitree(Arbitre arbitre) {
		this.arbitresDisponibles.remove(arbitre);
		this.arbitresSelectionnes.add(arbitre);
	}
	
	/**
	 * @return les équipes sélectionnées
	 */
	public List<String> getEquipesSelectionnees() {
		List<String> equipes = new ArrayList<>();
		for(EquipeSaison e : this.equipesParticipantes) {
			equipes.add(e.getNom());
		}
		return equipes;
	}
	
	/**
	 * Enlève l'équipe e de la sélection d'équipes
	 * @param e EquipeSaison
	 */
	public void unselectEquipe(EquipeSaison e) {
		this.equipesDisponibles.add(e);
		this.equipesParticipantes.remove(e);
	}

	/**
	 * @param nomEquipe
	 * @return l'équipe de nom nomEquipe
	 */
	public EquipeSaison getEquipeByName(String nomEquipe) {
		try {
			for (EquipeSaison equipe : allEquipes) {
				if (equipe.getNom().equals(nomEquipe)) {
					return equipe;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return le nombre d'équipes participantes
	 */
	public int getNbEquipesParticipantes() {
		return this.equipesParticipantes.size();
	}

	
	/**
	 * @return vrai si il y a au moins 3 arbitres inscrits et s'il y a entre 4 et 8 équipes inscrites au tournoi
	 */
	public boolean checkContraintes() {
		return (getArbitresSelectionnes().size() >= 3)
				&&(getEquipesSelectionnees().size() > 3) 
				&&(getEquipesDispos().size() < 9);
	}
	
	public String getMdp() {
		return this.tournoi.getMotDePasse();
	}

	public String getLog() {
		return this.tournoi.getLogin();
	}

	public String getNomTournoi() {
		return this.tournoi.getNom();
	}
	
	public int getIdTournoi() {
		return this.tournoi.getIdTournoi();
	}
	
	public String getNPArbitres() {
		String arb = "";
		int cpt = 0;
		for(int i = 0; i<this.arbitresSelectionnes.size() - 1; i++) {
			cpt++;
			arb += this.arbitresSelectionnes.get(i).getNom() + " " + this.arbitresSelectionnes.get(i).getPrenom() + ", ";
		}
		arb += this.arbitresSelectionnes.get(cpt).getNom() + " " + this.arbitresSelectionnes.get(cpt).getPrenom();
		return arb;
	}

	/**
	 * @param prend le nom et le prénom d'un arbitre, et return cet arbitre
	 * @return l'arbitre trouvé
	 * @throws Exception
	 */
	public Arbitre getArbitre(String nom, String prenom) {
		Arbitre a = null;
		for(Arbitre arb : this.allArbitres) {
			if(arb.getNom().equals(nom) && arb.getPrenom().equals(prenom)) {
				a = arb;
				return a;
			}
		}
		return a;
	}

	/**
	 * @return les arbitres sélectionnables
	 */
	public List<String> getArbitresDispos() {
		List<String> arbitres = new ArrayList<>();
		for(Arbitre a : this.arbitresDisponibles) {
			arbitres.add(a.getNom() + " " + a.getPrenom());
		}
		return arbitres;
	}

	/**
	 * @return les arbitres sélectionnés
	 */
	public List<String> getArbitresSelectionnes() {
		List<String> arbitres = new ArrayList<>();
		for(Arbitre a : this.arbitresSelectionnes) {
			arbitres.add(a.getNom() + " " + a.getPrenom());
		}
		return arbitres;
	}

	/**
	 * @param ajoute un arbitre "arbitre" à la liste d'arbitres sélectionnés, en le
	 *               supprimant de tous les arbitres sélectionnables
	 */
	public void selectArbitre(Arbitre arbitre) {
		this.arbitresDisponibles.remove(arbitre);
		this.arbitresSelectionnes.add(arbitre);
	}

	/**
	 * @param enlève un arbitre des sélectionnés, pour le remettre dans les arbitres
	 *               sélectionnables
	 */
	public void unselectArbitre(Arbitre arbitre) {
		this.arbitresSelectionnes.remove(arbitre);
		this.arbitresDisponibles.add(arbitre);
	}

	public List<Arbitre> getAllArbitres() {
		return this.allArbitres;
	}

	public void generateMatch() {
		List<EquipeSaison> equipes = this.equipesParticipantes;
		int n = 0;
		for(int i = 0; i <  equipes.size(); i++) {
			for(int j = i+1; j < equipes.size(); j++ ) {
				HashMap<String,Integer> map = new HashMap<>();
				map.put(equipes.get(i).getNom(),0);
				map.put(equipes.get(j).getNom(),0);
				this.impMatchs.add(new Match(n, this.tournoi.getNom(), map, this.tournoi.getIdTournoi()));
				n++;
			}
		}		
	}
	
	public Tournoi getTournoi() {
		return this.tournoi;
	}
	
	public void addArbitres() {
		for(Arbitre arbitre:this.arbitresSelectionnes) {
			this.tournoiDAO.addArbitre(this.tournoi, arbitre);
		}
	}
}
