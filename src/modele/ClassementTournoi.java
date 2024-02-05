package modele;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import DAO.ImpMatchsDAO;

public class ClassementTournoi {

	private List<Match> listeMatchs;
	private ImpMatchsDAO impMatchs;
	private int annee;
	private Map<String, Integer> WrEquipeOfSaison;

	/**
     * Constructeur de la classe ClassementTournoi.
     *
     * @param t Le tournoi pour lequel le classement est calculé.
     */
	public ClassementTournoi(Tournoi t) {
		this.impMatchs = new ImpMatchsDAO();
		this.listeMatchs = this.impMatchs.getMatchsByTournoi(t);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate debut = LocalDate.parse(t.getDateDebut(), formatter);
		this.annee = debut.getYear();
		this.WrEquipeOfSaison = EquipesSaison.getInstance(this.annee).getEquipes().stream()
				.collect(Collectors.toMap(EquipeSaison::getNom, EquipeSaison::getWr));
	}

	/**
     * Calcule les points des équipes en fonction des matchs joués.
     *
     * @return Une map associant le nom de l'équipe à son score.
     */
	public Map<String, Integer> getPointEquipes() {
		Map<String, Integer> pointEquipes = new HashMap<>();
		for (Match match : this.listeMatchs) {
			for (Entry<String, Integer> equipe : match.getEquipes().entrySet()) {
				if (pointEquipes.containsKey(equipe.getKey())) {
					pointEquipes.replace(equipe.getKey(), pointEquipes.get(equipe.getKey()) + equipe.getValue());
				} else {
					pointEquipes.put(equipe.getKey(), equipe.getValue());
				}
			}
		}
		return pointEquipes;
	}

	/**
     * Génère le classement des équipes en fonction des scores et du Wr (Winning rate).
     *
     * @param equipes Une map associant le nom de l'équipe à son score.
     * @return Une liste d'objets EquipeSaison représentant le classement des équipes.
     */
	public List<EquipeSaison> getClassement(Map<String, Integer> equipes) {
		List<EquipeSaison> listEquipe = new ArrayList<>();
		for (String nom : equipes.keySet()) {
			Integer score = equipes.get(nom);
			// Vérifie si l'équipe existe dans listEquipe
			EquipeSaison existingEquipe = listEquipe.stream()
					.filter(equipe -> equipe.getNom().equals(nom))
					.findFirst()
					.orElse(null);
			if (existingEquipe != null) {
				// Supprime l'équipe existante dans listEquipe
				listEquipe.remove(existingEquipe);
			}
			// Ajoute la nouvelle équipe
			listEquipe.add(new EquipeSaison(nom, "", score, this.WrEquipeOfSaison.get(nom)));
		}
		if (equipes.entrySet().stream().max((e1, e2) -> e1.getValue().compareTo(e2.getValue())).get().getValue() == 0) {
			listEquipe = listEquipe.stream().sorted((e1, e2) -> e1.getNom().compareTo(e2.getNom()))
					.collect(Collectors.toList());
		} else {
			listEquipe.stream().sorted((e1, e2) -> {
				int compare = Integer.compare(e1.getScore(), e2.getScore());
				if (compare == 0) {
					compare = e1.getNom().compareTo(e2.getNom());
				}
				return compare;
			}).collect(Collectors.collectingAndThen(Collectors.toList(), result -> {
				Collections.reverse(result);
				return result;
			}));
		}
		return listEquipe;
	}
}
