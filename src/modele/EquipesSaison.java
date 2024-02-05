package modele;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import DAO.ImpEquipeDAO;
import DAO.ImpPalmares;
import DAO.ImpTournoiDAO;

/**
 * EquipesSaison est un multiton composé de listes EquipeSaison d'une année
 */
public class EquipesSaison implements Comparator<EquipeSaison> {
	private List<EquipeSaison> listEquipe;
	private static Map<Integer, EquipesSaison> instances = new HashMap<>();
	private ImpPalmares impPalmares;
	private int saison;

	/**
	 * constructeur du multiton
	 * 
	 * @param saison
	 */
	private EquipesSaison(int saison) {
		this.impPalmares = new ImpPalmares();
		this.listEquipe = this.impPalmares.getByAnnee(saison);
		this.saison = saison;
		this.listEquipe = this.listEquipe.stream().sorted((e1, e2) -> compare(e1, e2)).collect(Collectors.toList());
	}
	
	/**
     * Obtient le World Rank d'une équipe par son nom.
     * 
     * @param nom nom de l'équipe
     * @return le World Rank de l'équipe, sinon 1000.
     */
	public int getWrByName(String nom) {
		return this.listEquipe.stream().filter(e -> e.getNom().equals(nom)).mapToInt(EquipeSaison::getWr).findFirst()
				.orElse(1000);
	}

	/**
     * Obtient une instance d'EquipesSaison pour une année donnée.
     * 
     * @param saison année de la saison
     * @return l'instance d'EquipesSaison correspondante.
     */
	public static synchronized EquipesSaison getInstance(int saison) {
		EquipesSaison instance = EquipesSaison.instances.get(saison);
		if (instance == null) {
			synchronized (EquipesSaison.class) {
				if (instance == null) {
					instance = new EquipesSaison(saison);
					EquipesSaison.instances.put(saison, instance);
				}
			}
		}
		return instance;
	}

	/**
     * Obtient la liste des EquipeSaison.
     * 
     * @return liste d'EquipeSaison
     */
	public List<EquipeSaison> getEquipes() {
		this.majListEquipesSaison();
		this.setWr();
		this.listEquipe = this.listEquipe.stream().sorted((e1, e2) -> this.compare(e1, e2))
				.collect(Collectors.toList());
		return this.listEquipe;
	}

	/**
     * Met à jour la liste des EquipeSaison.
     */
	private void majListEquipesSaison() {
		this.listEquipe = this.impPalmares.getByAnnee(this.saison);
	}

	/**
     * Ajoute une EquipeSaison à la liste.
     * 
     * @param equipe EquipeSaison à ajouter
     */
	public void addEquipeSaison(EquipeSaison equipe) {
		this.listEquipe.add(equipe);
		this.listEquipe = this.listEquipe.stream().sorted((e1, e2) -> compare(e1, e2)).collect(Collectors.toList());
	}

	/**
     * Supprime une EquipeSaison de la liste.
     * 
     * @param equipe EquipeSaison à supprimer
     */
	public void deleteEquipeSaison(EquipeSaison equipe) {
		for (Iterator<EquipeSaison> iterator = this.listEquipe.iterator(); iterator.hasNext();) {
			EquipeSaison e = iterator.next();
			if (e.getNom().equals(equipe.getNom())) {
				iterator.remove();
			}
		}
	}

	/**
     * Trie la liste des EquipeSaison par leur nom.
     * 
     * @return la liste triée
     */
	public List<EquipeSaison> getEquipesByName() {
		return this.listEquipe.stream().sorted((e1, e2) -> e1.getNom().compareTo(e2.getNom()))
				.collect(Collectors.toList());
	}

	/**
     * Calcule le World Rank de chaque Equipe de la liste.
     */
	private void setWr() {
		int maxScore = this.listEquipe.stream().map(EquipeSaison::getScore).max(Comparator.naturalOrder()).orElse(-1);
		if (maxScore != 0 && maxScore != -1) {
			this.listEquipe.forEach(e -> e.setWr(getWRSaison(this.listEquipe, e.getNom())));
		} else if (maxScore != -1 && maxScore == 0) {
			getWrSaisonInitial();
		}
	}

	/**
     * Calcule le World Rank de la liste quand chaque Equipe a un score de 0.
     */
	private void getWrSaisonInitial() {
		try {
			List<EquipeSaison> equipesSaisonPreviousYear = this.impPalmares.getAnneePrecedente(this.saison);
			if ((equipesSaisonPreviousYear.size() != 0 && equipesSaisonPreviousYear.stream()
					.max((e1, e2) -> Integer.compare(e1.getScore(), e2.getScore())).get().getScore() == 0)
					|| equipesSaisonPreviousYear.size() == 0) {
				this.listEquipe.forEach(equipe -> equipe.setWr(1000));
			} else {
				this.listEquipe.forEach(equipe -> {
					String nomEquipe = equipe.getNom();
					Integer wrAnneePassee = getWRSaison(equipesSaisonPreviousYear, nomEquipe);
					if (wrAnneePassee != null && wrAnneePassee != -1) {
						equipe.setWr(wrAnneePassee);
					} else {
						equipe.setWr(1000);
					}
				});
				this.listEquipe = this.listEquipe.stream().sorted((e1, e2) -> this.compare(e1, e2))
						.collect(Collectors.toList());
				int currentRank = 0;
				int currentWR = -1;
				int doublon = 0;
				for (int i = 0; i < this.listEquipe.size(); i++) {
					int wr = listEquipe.get(i).getWr();
					if (wr != currentWR && wr != 1000) {
						currentWR = wr;
						currentRank += 1 + doublon;
						doublon = 0;
						this.listEquipe.get(i).setWr(currentRank);
					} else if (wr == 1000) {
						this.listEquipe.get(i).setWr(1000);
					} else {
						doublon++;
						this.listEquipe.get(i).setWr(currentRank);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
     * Calcule le World Rank d'une équipe lorsque au moins une équipe a un score
     * différent de 0.
     * 
     * @param listeEquipes liste d'EquipeSaison
     * @param nom nom d'une Equipe de la liste
     * @return le World Rank
     */
	private int getWRSaison(List<EquipeSaison> listeEquipes, String nom) {
		int rank = 0;
		int occurence = 1;
		int score = 0;
		int i = 0;
		while (listeEquipes.size() > i && !listeEquipes.get(i).getNom().equals(nom)) {
			if (score == listeEquipes.get(i).getScore()) {
				occurence++;
			} else {
				score = listeEquipes.get(i).getScore();
				rank += occurence;
				occurence = 1;
			}

			i++;
		}
		if (listeEquipes.size() > i) {
			if (score != listeEquipes.get(i).getScore()) {
				rank += occurence;
			}
			if (rank == 0) {
				rank++;
			}

		} else {
			rank = -1;
		}
		return rank;
	}

	/**
     * Redéfinition de la méthode compare pour comparer deux EquipeSaison par
     * rapport à leur World Rank puis leur nom.
     */
	@Override
	public int compare(EquipeSaison e1, EquipeSaison e2) {
		int max = this.listEquipe.stream().max(Comparator.comparingInt(EquipeSaison::getScore)).get().getScore();
		if (max == 0) {
			if (e1.getWr() > e2.getWr()) {
				return 1;
			} else if (e1.getWr() == e2.getWr()) {
				return e1.getNom().compareTo(e2.getNom());
			} else {
				return -1;
			}
		} else {
			if (e1.getScore() > e2.getScore()) {
				return -1;
			} else if (e1.getScore() == e2.getScore()) {
				return e1.getNom().compareTo(e2.getNom());
			} else {
				return 1;
			}
		}
	}

	/**
     * Obtient une Equipe par son nom.
     * 
     * @param nom nom de l'équipe
     * @return l'Equipe correspondante, sinon null.
     */
	public Equipe getEquipesByName(String string) {
		ImpEquipeDAO impEquipeDAO = new ImpEquipeDAO();
		return impEquipeDAO.getByName(string);

	}

}