package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import modele.EquipeSaison;
import modele.Match;
import modele.Phase;
import modele.Poule;
import modele.Tournoi;

/**
 * Implémentation de l'accès aux données pour la gestion des palmarès.
 */
public class ImpPalmares {
	private Connection c;
	private ImpTournoiDAO impTournoiDAO;
	
	/**
     * Constructeur de la classe ImpPalmares.
     */
	public ImpPalmares() {
		this.c = ConnexionDB.getConnexionDB().getConnexion();
		this.impTournoiDAO = new ImpTournoiDAO();
	}

	/**
     * Obtient la liste des équipes pour une année donnée.
     * 
     * @param annee année spécifiée
     * @return liste des équipes avec leur score
     */
	public List<EquipeSaison> getByAnnee(Integer annee) {
		List<EquipeSaison> listeEquipes = new ArrayList<>();
		List<String> nom = new ArrayList<>();
		ResultSet rs, rst;
		Tournoi tournoi = null;
		int idTournoi = 0;
		double ligue = 0d;
		Poule poule = null;
		try {
			Statement st = this.c.createStatement();
			// récupération des équipes
			rs = st.executeQuery("select distinct t.IdTournoi as IdTournoi " + "from Tournoi as t "
					+ "where t.Annee = '" + annee + "'");
			// pour chaque équipe
			while (rs.next()) {
				idTournoi = rs.getInt("IdTournoi");
				tournoi = this.impTournoiDAO.getById(idTournoi).get();
				ligue = tournoi.getLigue().getValeur();
				if (this.impTournoiDAO.getTurnamentPhase(tournoi) == Phase.CLOSED) {
					String query = "SELECT NomEquipe, SUM(CASE WHEN NbPoints = 3 THEN 25 WHEN NbPoints = 1 THEN 15 ELSE 0 END) AS TotalPoints "
							+ "FROM Matchs WHERE IdTournoi = " + idTournoi
							+ " GROUP BY NomEquipe order by TotalPoints desc";
					Statement st1 = c.createStatement();
					rst = st1.executeQuery(query);
					Map<String, Double> pointsMap = new HashMap<>();
					while (rst.next()) {
						String nomEquipe = rst.getString("NomEquipe");
						Double totalPoints = rst.getDouble("TotalPoints");
						pointsMap.put(nomEquipe, totalPoints);
					}

					poule = new Poule(tournoi);
					Match finale = poule.getFinal();
					pointsMap.replace(finale.getWinner(), pointsMap.get(finale.getWinner()) + 200);
					pointsMap.replace(finale.getLoser(), pointsMap.get(finale.getLoser()) + 100);

					List<Entry<String, Double>> sortedTeams = pointsMap.entrySet().stream()
							.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).collect(Collectors.toList());
					Double thirdScore = sortedTeams.stream().skip(2)
							.max((equipe1, equipe2) -> Double.compare(equipe1.getValue(), equipe2.getValue()))
							.map(e -> e.getValue()).orElse(-1d);
					Double fourScore = sortedTeams.stream().skip(2).filter(equipe -> equipe.getValue() != thirdScore)
							.max((equipe1, equipe2) -> Double.compare(equipe1.getValue(), equipe2.getValue()))
							.map(e -> e.getValue()).orElse(-1d);
					
					for (int i = 0; i < sortedTeams.size(); i++) {
						Entry<String, Double> team = sortedTeams.get(i);
						int additionalPoints = 0;
						if (team.getValue() == thirdScore) {
							additionalPoints = 50;
						} else if (team.getValue() == fourScore) {
							additionalPoints = 15;
						}
						pointsMap.put(team.getKey(), (pointsMap.get(team.getKey()) + additionalPoints) * ligue);
					}
					for (Map.Entry<String, Double> entry : pointsMap.entrySet()) {
						if (!nom.contains(entry.getKey())) {
							listeEquipes.add(new EquipeSaison(entry.getKey(), getPays(entry.getKey()), annee,
									entry.getValue().intValue()));
							nom.add(entry.getKey());
						} else {
							for (EquipeSaison e : listeEquipes) {
								if (e.getNom().equals(entry.getKey())) {
									e.setScore(e.getScore() + entry.getValue().intValue());
								}
							}
						}
					}
				}
			}
			rst = st.executeQuery("select e.NomEquipe as NomEquipe " + "from Participer as p, Equipe as e "
					+ "where p.NomEquipe = e.NomEquipe and " + "p.Annee = '" + annee + "'");
			while (rst.next()) {
				if (!nom.contains(rst.getString("NomEquipe"))) {
					listeEquipes.add(new EquipeSaison(rst.getString("NomEquipe"), getPays(rst.getString("NomEquipe")),
							annee, 0));
					nom.add(rst.getString("NomEquipe"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		listeEquipes = listeEquipes.stream().sorted((e1,e2) -> -1*Integer.compare(e1.getScore(), e2.getScore())).collect(Collectors.toList());
		return listeEquipes;
	}

	/**
     * Obtient le pays d'une équipe à partir de son nom.
     * 
     * @param nom nom de l'équipe
     * @return nom du pays
     */
	public String getPays(String nom) {
		ResultSet rs;
		String s = "";
		try {
			Statement st = this.c.createStatement();
			rs = st.executeQuery("select Pays from Equipe as e where NomEquipe = '" + nom + "'");
			while (rs.next()) {
				s = rs.getString("Pays");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
     * Obtient la liste des équipes de l'année précédente.
     * 
     * @param annee année spécifiée
     * @return liste des équipes de l'année précédente avec leur score
     */
	public List<EquipeSaison> getAnneePrecedente(int annee) {
		return this.getByAnnee(annee - 1);
	}

}