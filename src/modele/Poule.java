package modele;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import DAO.ImpMatchsDAO;
import DAO.ImpTournoiDAO;

public class Poule {

	private List<Match> matchs;
	private int idFinal;
	private String nomTournoi;
	private ImpMatchsDAO impMatchs;
	private ImpTournoiDAO impTournoi;
	private Tournoi tournoi;

	public Poule(Tournoi tournoi) {
		this.impMatchs = new ImpMatchsDAO();
		this.impTournoi = new ImpTournoiDAO();
		this.nomTournoi = tournoi.getNom();
		this.tournoi = tournoi;
		this.matchs = new ArrayList<>();
		this.matchs = this.impMatchs.getMatchsByTournoi(tournoi);
		this.impTournoi = new ImpTournoiDAO();
	}

	private Map<String, Integer> scoreByEquipe() {
		this.matchs.stream().forEach(m -> m.getEquipes());
		Map<String, Integer> scoreByEquipe = matchs.stream().flatMap(m -> m.getEquipes().entrySet().stream())
				.collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)));
		return scoreByEquipe;
	}

	/**
	 * renvoie le match final du tournoi
	 * 
	 * @return Matchs
	 */
	public Match getFinal() {

		this.matchs = this.impMatchs.getMatchsByTournoi(tournoi);
		int nbEquipes = this.impTournoi.getNbEquipesByTournoi(this.tournoi);
		this.idFinal = ((nbEquipes * (nbEquipes - 1))/2);
		for(Match match:this.matchs) {
			if(match.getIdMatch() == idFinal) {
				return match;
			}
		}
		Match finale = new Match(this.idFinal, this.nomTournoi, this.getEquipeForFinal(), this.tournoi.getIdTournoi());
		this.impMatchs.add(finale);
		return finale;
	}

	/**
	 * Sélectionne les 2 équipes qui passeront à la finale
	 * 
	 * @return une liste composait de 2 noms déquipes sélectionner pour la finale
	 */
	public Map<String, Integer> getEquipeForFinal() {
		Map<String, Integer> scoreByEquipe = scoreByEquipe();
		Optional<Map.Entry<String, Integer>> maxEntry = getMaxEntry(scoreByEquipe);
		Optional<Map.Entry<String, Integer>> secondMaxEntry = getSecondMaxEntry(scoreByEquipe, maxEntry);

		List<String> equipe1 = maxEntry.map(entry -> getEquipesWithScore(scoreByEquipe, entry.getValue()))
				.orElse(Collections.emptyList());

		List<String> equipe2 = secondMaxEntry.map(entry -> getEquipesWithScore(scoreByEquipe, entry.getValue()))
				.orElse(Collections.emptyList());

		Map<String, Integer> teamsSelected = new HashMap<>();
		List<String> teamsReceved = selectEquipes(equipe1, equipe2);
		teamsSelected.put(teamsReceved.get(0), 0);
		teamsSelected.put(teamsReceved.get(1), 0);
		return teamsSelected;
	}

	/**
	 * @param un map contenant toutes les équipes associer à leur score
	 * @return le ou les équipes qui ont le plus haut score
	 */
	private Optional<Map.Entry<String, Integer>> getMaxEntry(Map<String, Integer> scoreByEquipe) {
		return scoreByEquipe.entrySet().stream().max(Map.Entry.<String, Integer>comparingByValue());
	}

	/**
	 * @param un map contenant toutes les équipes associer à leur score
	 * @param le map d'où des équipes qui ont le plus haut score
	 * @return le ou les équipes qui ont le second plus haut score
	 */
	private Optional<Map.Entry<String, Integer>> getSecondMaxEntry(Map<String, Integer> scoreByEquipe,
			Optional<Map.Entry<String, Integer>> maxEntry) {
		return maxEntry.flatMap(max -> scoreByEquipe.entrySet().stream().filter(entry -> !entry.equals(max))
				.max(Map.Entry.<String, Integer>comparingByValue()));
	}


	/**
	 * @param scorebyequipe map des équipes associés à leur score
	 * @param score
	 * @return renvoie une liste composer de nom des équipes associer au score
	 */
	private List<String> getEquipesWithScore(Map<String, Integer> scoreByEquipe, int score) {
		return scoreByEquipe.entrySet().stream().filter(entry -> entry.getValue() == score).map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}

	/**
	 * @param liste des équipes qui ont le plus haut score
	 * @param liste des équipes qui ont le second plus haut score
	 * @return une liste composait de 2 équipes
	 */
	private List<String> selectEquipes(List<String> equipe1, List<String> equipe2) {
		List<String> equipeSelected = new ArrayList<>();
		if (equipe1.size() == 1) {
			equipeSelected.add(equipe1.get(0));
			if (equipe2.size() == 1) {
				equipeSelected.add(equipe2.get(0));
			} else {
				equipeSelected.add(getRandomString(equipe2, 1).get(0));
			}
		} else if (equipe1.size() == 2) {
			return equipe1;
		} else {
			equipeSelected.addAll(getRandomString(equipe1, 2));
		}
		return equipeSelected;
	}

	/**
	 * quand égalité choix d'une équipe par le world rank sinon aléatoire
	 * 
	 * @param liste de noms d'équipes
	 * @return renvoie un nom d'équipe
	 */
	public static List<String> getRandomString(List<String> list, int nb) {

	    EquipesSaison equipes = EquipesSaison.getInstance(Calendar.getInstance().get(Calendar.YEAR));
	    List<String> teamsSelected = new ArrayList<>();
	    // Obtenir le WR le plus élevé parmi toutes les équipes de la liste
	    list.stream()
	    .sorted((x,y)->{if(equipes.getWrByName(x) > equipes.getWrByName(y)) {
	    					return 1;
	    } else if(equipes.getWrByName(x) == equipes.getWrByName(y)) {
	    	return 0;
	    } else {
	    	return -1;
	    }})
	    .map(x -> equipes.getWrByName(x));
//	    .forEach(System.out::println);
	    
	    return list;
	}

    public void supprimerTournoi(Tournoi tournoi2) {
    }

}
