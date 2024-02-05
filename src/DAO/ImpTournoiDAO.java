package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import modele.Arbitre;
import modele.Equipe;
import modele.EquipeSaison;
import modele.EquipeTournoi;
import modele.Match;
import modele.Phase;
import modele.Tournoi;

public class ImpTournoiDAO implements TournoiDAO {
	private Connection c;

	public ImpTournoiDAO() {
		this.c = ConnexionDB.getConnexionDB().getConnexion();
	}

	// pour les tests
	public ImpTournoiDAO(Connection coco) {
		this.c = coco;
	}

	@Override
	public List<Tournoi> getAll() {
		List<Tournoi> tournois = new ArrayList<>();
		try (Statement statement = this.c.createStatement()) {
			ResultSet rs = statement.executeQuery("SELECT * FROM Tournoi");
			while (rs.next()) {
				tournois.add(new Tournoi(rs.getInt("idTournoi"), rs.getString("Nom"), rs.getString("Ligue"),
						rs.getString("DateDebut"), rs.getString("DateFin"), rs.getString("Login"),
						rs.getString("MotDePasse")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tournois;
	}

	@Override
	public Optional<Tournoi> getById(Integer... id) {
		try (Statement statement = this.c.createStatement()) {
			for (int i = 0; i < id.length; i++) {
				String query = String.format("SELECT * FROM Tournoi WHERE IdTournoi=" + id[i]);
				ResultSet rs = statement.executeQuery(query);
				if (rs.next()) {
					return Optional.of(new Tournoi(rs.getInt("idTournoi"), rs.getString("Nom"), rs.getString("Ligue"),
							rs.getString("DateDebut"), rs.getString("DateFin"), rs.getString("Login"),
							rs.getString("MotDePasse")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public boolean add(Tournoi value) {
		if (this.getByName(value.getNom()) != null) {
			return false;
		}
		try (Statement statement = this.c.createStatement()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate debut = LocalDate.parse(value.getDateDebut(), formatter);

			String query = String.format(
					"INSERT INTO Tournoi (Nom, Ligue, DateDebut, DateFin, Login, MotDePasse, Annee) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
					value.getNom(), value.getLigue().toString(), value.getDateDebut(), value.getDateFin(),
					value.getLogin(), value.getMotDePasse(), debut.getYear());
			statement.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Tournoi value) {
		try (Statement statement = this.c.createStatement()) {
			String query = String.format(
					"UPDATE Tournoi SET Nom='%s', Ligue='%s', DateDebut='%s', DateFin='%s', Login='%s', MotDePasse='%s' WHERE IdTournoi='%d'",
					value.getNom(), value.getLigue().toString(), value.getDateDebut(), value.getDateFin(),
					value.getLogin(), value.getMotDePasse(), this.getId(value));
			statement.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Tournoi value) {
		int idTournoi = this.getId(value);
		try (Statement statement = this.c.createStatement()) {
			String queryArbitrer = String.format("DELETE FROM Arbitrer WHERE IdTournoi=" + idTournoi);
			statement.executeUpdate(queryArbitrer);
			String queryMatchs = String.format("DELETE FROM Matchs WHERE IdTournoi=" + idTournoi);
			statement.executeUpdate(queryMatchs);
			String queryTournoi = String.format("DELETE FROM Tournoi WHERE IdTournoi=" + idTournoi);
			statement.executeUpdate(queryTournoi);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public int getId(Tournoi value) {
		try (Statement statement = this.c.createStatement()) {
			String query = String.format("SELECT IdTournoi FROM Tournoi WHERE Nom='%s'", value.getNom());
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				return rs.getInt("IdTournoi");
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public Tournoi getByName(String nom) {
		try (Statement statement = this.c.createStatement()) {
			String query = String.format("SELECT * FROM Tournoi WHERE Nom='%s'", nom);
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				Tournoi tournoi = new Tournoi(rs.getInt("idTournoi"), rs.getString("Nom"), rs.getString("Ligue"),
						rs.getString("DateDebut"), rs.getString("DateFin"), rs.getString("Login"),
						rs.getString("MotDePasse"));
				tournoi.setIdTournoi(rs.getInt("IdTournoi"));
				return tournoi;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Prennds en paramètre un tournoi et renvoie le nombre de matchs au tournoi
	 */
	public int getNbMatchsByTournoi(Tournoi t) {
		try (Statement statement = this.c.createStatement()) {
			String query = String
					.format("SELECT COUNT(DISTINCT IdMatchs) FROM Matchs WHERE IdTournoi=" + this.getId(t));
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				return rs.getInt(1);
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/*
	 * Prends en paramètre un tournoi est renvoie true si la finale est terminée
	 */
	public Phase getTurnamentPhase(Tournoi tournoi) {
		int nbEquipes = this.getNbEquipesByTournoi(tournoi);
		int nbMatchsPoule = (nbEquipes * (nbEquipes - 1)) / 2;
		int nbMatchsJoues = this.getNbMatchsOverByTournoi(tournoi);
		int nbMatchsEnCours = this.getNbMatchsByTournoi(tournoi);
		if (nbMatchsEnCours == 0) {
			return Phase.NOT_STARTED;
		} else if (nbMatchsJoues < nbMatchsPoule) {
			return Phase.POULE;
		} else if (nbMatchsJoues == nbMatchsPoule) {
			return Phase.FINALE;
		} else {
			return Phase.CLOSED;
		}
	}

	/*
	 * Prends en paramètre un tournoi est renvoie le nombre d'équipe jouant au
	 * tournoi
	 */
	public int getNbEquipesByTournoi(Tournoi tournoi) {
		try (Statement statement = this.c.createStatement()) {
			String query = String
					.format("SELECT COUNT(DISTINCT NomEquipe) FROM Matchs WHERE IdTournoi=" + this.getId(tournoi));
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				return rs.getInt(1);
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/*
	 * Prends en paramètre un tournoi est renvoie le nombre de matchs joués au
	 * tournoi
	 */
	public int getNbMatchsOverByTournoi(Tournoi tournoi) {
		try (Statement statement = this.c.createStatement()) {
			String query = String.format("SELECT COUNT(DISTINCT IdMatchs) FROM Matchs WHERE IdTournoi="
					+ this.getId(tournoi) + "AND NbPoints != 0");
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				return rs.getInt(1);
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public void generateMatch(String nomTournoi, int idTournoi, List<EquipeSaison> equipes) {
		ImpMatchsDAO impMatch = new ImpMatchsDAO();
		int n = 1;
		for (int i = 0; i < equipes.size(); i++) {
			for (int j = i + 1; j < equipes.size(); j++) {
				HashMap<String, Integer> map = new HashMap<>();
				map.put(equipes.get(i).getNom(), 0);
				map.put(equipes.get(j).getNom(), 0);
				impMatch.add(new Match(n, nomTournoi, map, idTournoi));
				n++;
			}
		}
	}

	public List<Arbitre> getArbitresByTournoi(Tournoi t) {
		List<Arbitre> arbitres = new LinkedList<>();
		try (Statement statement = this.c.createStatement()) {
			String query = String.format(
					"SELECT * FROM Arbitres, Arbitrer WHERE Arbitrer.IdTournoi = %d AND Arbitrer.IdArbitres = Arbitres.IdArbitres",
					t.getIdTournoi());
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				Arbitre arbitre = new Arbitre(rs.getInt("IdArbitres"), rs.getString("Nom"), rs.getString("Prenom"));
				arbitres.add(arbitre);
			}
			return arbitres;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<EquipeTournoi> getEquipesTournoi(Tournoi t) {
		List<EquipeTournoi> equipes = new LinkedList<>();
		try (Statement statement = this.c.createStatement()) {
			String query = String.format("SELECT Matchs.NomEquipe,"
					+ "COUNT(CASE WHEN Matchs.NbPoints <> 0 THEN 1 END) AS nb_matchs_joues, "
					+ "COUNT(CASE WHEN Matchs.NbPoints = 3 THEN 1 END) AS nb_matchs_gagnes "
					+ "FROM Matchs "
					+ "WHERE Matchs.IdTournoi = " + t.getIdTournoi() + " "
					+ "GROUP BY Matchs.NomEquipe "
					+ "ORDER BY 3 DESC, 2 DESC, 1 ASC ");
			ResultSet rs = statement.executeQuery(query);
			int classement = 0;
			int count = 0;
			int lastScore = -1;
			while (rs.next()) {
				count++;
				int nbVictoires = rs.getInt("nb_matchs_gagnes");
				if (lastScore != nbVictoires) {
					classement = count;
				}
				lastScore = rs.getInt("nb_matchs_gagnes");
				equipes.add(new EquipeTournoi(rs.getString("NomEquipe"), classement, rs.getInt("nb_matchs_joues"),
						rs.getInt("nb_matchs_gagnes")));
			}
			return equipes;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean addArbitre(Tournoi t, Arbitre a) {
		try (Statement statement = this.c.createStatement()) {
			String query = String.format(
					"INSERT INTO Arbitrer (IdTournoi, IdArbitres) VALUES (%d, %d)",
					t.getIdTournoi(), a.getIdArbitre());
			statement.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Tournoi> getTournoisFromEquipe(String nomEquipe) {
		List<Tournoi> tournois = new ArrayList<>();
		try (Statement statement = this.c.createStatement()) {
			String query = String.format(
					"SELECT Distinct Tournoi.* FROM Tournoi, Matchs WHERE Tournoi.IdTournoi = Matchs.IdTournoi AND Matchs.NomEquipe = '%s' ORDER BY Tournoi.Annee DESC",
					nomEquipe);
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				Tournoi tournoi = new Tournoi(rs.getString("Nom"), rs.getString("Ligue"),
						rs.getString("DateDebut"), rs.getString("DateFin"),
						rs.getString("Login"), rs.getString("MotDePasse"), rs.getString("Annee"));
				tournoi.setIdTournoi(rs.getInt("IdTournoi"));
				tournois.add(tournoi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tournois;
	}

	public int getRankEquipe(Tournoi t, Equipe equipe) {
		try (Statement statement = this.c.createStatement()) {
			String query = String.format(
					"SELECT Matchs.NomEquipe," + "COUNT(CASE WHEN Matchs.NbPoints <> 0 THEN 1 END) AS nb_matchs_joues, "
							+ "COUNT(CASE WHEN Matchs.NbPoints = 3 THEN 1 END) AS nb_matchs_gagnes " + "FROM Matchs "
							+ "WHERE Matchs.IdTournoi = " + t.getIdTournoi() + " " + "GROUP BY Matchs.NomEquipe "
							+ "ORDER BY 3 DESC, 2 DESC ");
			ResultSet rs = statement.executeQuery(query);
			int classement = 0;
			int count = 0;
			int lastScore = 0;
			while (rs.next()) {
				count++;
				int nbVictoires = rs.getInt("nb_matchs_gagnes");
				if (lastScore != nbVictoires) {
					classement = count;
				}
				lastScore = rs.getInt("nb_matchs_gagnes");
				if (rs.getString("NomEquipe").equals(equipe.getNom())) {
					return classement;
				}
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public Tournoi getTournoiByLogin(String login, String password) {
		Tournoi tournoi = null;
		try (Statement statement = this.c.createStatement()) {
			String query = String.format(
					"SELECT Distinct * FROM Tournoi WHERE Tournoi.Login = '%s' AND Tournoi.MotDePasse = '%s' ORDER BY Tournoi.DateDebut DESC",
					login, password);
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				tournoi = new Tournoi(rs.getString("Nom"), rs.getString("Ligue"),
						rs.getString("DateDebut"), rs.getString("DateFin"),
						rs.getString("Login"), rs.getString("MotDePasse"), rs.getString("Annee"));
				tournoi.setIdTournoi(rs.getInt("IdTournoi"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tournoi;
	}
}
