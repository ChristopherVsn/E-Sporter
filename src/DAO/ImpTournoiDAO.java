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

/**
 * Classe d'implémentation de l'interface TournoiDAO.
 * Gère l'accès aux données des tournois dans la base de données.
 */
public class ImpTournoiDAO implements TournoiDAO {
	private Connection c;

	/**
     * Constructeur par défaut, utilise la connexion à la base de données par défaut.
     */
	public ImpTournoiDAO() {
		this.c = ConnexionDB.getConnexionDB().getConnexion();
	}

	/**
     * Constructeur utilisé pour les tests, prend une connexion en paramètre.
     *
     * @param test Connexion à la base de données.
     */
	public ImpTournoiDAO(Connection test) {
		this.c = test;
	}

	/**
     * Récupère tous les tournois de la base de données.
     *
     * @return Liste des tournois.
     */
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

	/**
     * Récupère un tournoi par son identifiant.
     *
     * @param id Identifiant du tournoi.
     * @return Tournoi correspondant à l'identifiant, s'il existe.
     */
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

	/**
     * Ajoute un tournoi à la base de données.
     *
     * @param value Tournoi à ajouter.
     * @return true si l'ajout a réussi, false sinon.
     */
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

	/**
     * Met à jour les informations d'un tournoi dans la base de données.
     *
     * @param value Tournoi à mettre à jour.
     * @return true si la mise à jour a réussi, false sinon.
     */
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

	/**
     * Supprime un tournoi de la base de données.
     *
     * @param value Tournoi à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
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

	/**
     * Obtient l'identifiant d'un tournoi à partir de son objet Tournoi.
     *
     * @param value Tournoi dont on veut l'identifiant.
     * @return Identifiant du tournoi.
     */
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

	/**
     * Obtient un tournoi à partir de son nom.
     *
     * @param nom Nom du tournoi.
     * @return Tournoi correspondant au nom, s'il existe.
     */
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

	/**
	 * Obtient le nombre de matchs associés à un tournoi.
	 *
	 * @param t Tournoi pour lequel on veut compter les matchs.
	 * @return Le nombre de matchs du tournoi.
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

	/**
	 * Obtient la phase actuelle d'un tournoi en fonction du nombre de matchs joués.
	 *
	 * @param tournoi Tournoi pour lequel on veut déterminer la phase.
	 * @return La phase actuelle du tournoi (NOT_STARTED, POULE, FINALE, CLOSED).
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

	/**
	 * Obtient le nombre d'équipes participant à un tournoi.
	 *
	 * @param tournoi Tournoi pour lequel on veut connaître le nombre d'équipes.
	 * @return Le nombre d'équipes participant au tournoi.
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

	/**
	 * Obtient le nombre de matchs joués dans un tournoi.
	 *
	 * @param tournoi Tournoi pour lequel on veut connaître le nombre de matchs joués.
	 * @return Le nombre de matchs joués au tournoi.
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

	/**
	 * Génère des matchs pour un tournoi à partir de la liste des équipes.
	 *
	 * @param nomTournoi Nom du tournoi.
	 * @param idTournoi  Identifiant du tournoi.
	 * @param equipes    Liste des équipes participantes.
	 */
	public void generateMatch(String nomTournoi, int idTournoi, List<EquipeSaison> equipes) {
		ImpMatchsDAO impMatch = new ImpMatchsDAO();
		int n = 0;
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

	/**
	 * Obtient la liste des arbitres associés à un tournoi.
	 *
	 * @param t Tournoi pour lequel on veut récupérer les arbitres.
	 * @return Liste des arbitres du tournoi.
	 */
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

	/**
	 * Obtient la liste des équipes participantes à un tournoi avec des statistiques.
	 *
	 * @param t Tournoi pour lequel on veut récupérer les équipes et leurs statistiques.
	 * @return Liste des équipes du tournoi avec leurs statistiques.
	 */
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
			String equipeGagne = this.getNomEquipeGagnanteFinale(t);
			while (rs.next()) {
				count++;
				int nbVictoires = rs.getInt("nb_matchs_gagnes");
				if(count<3) {
					if(rs.getString("NomEquipe").equals(equipeGagne)) {
						classement = 1;
					}else {
						classement = 2;
					}
				}else {
					if (lastScore != nbVictoires) {
						classement = count;
					}					
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
	
	/**
	 * Obtient le classement d'une équipe dans un tournoi.
	 *
	 * @param t      Tournoi dans lequel se trouve l'équipe.
	 * @param equipe Équipe pour laquelle on veut obtenir le classement.
	 * @return Le classement de l'équipe dans le tournoi.
	 */
	public int getRankEquipe(Tournoi t, Equipe equipe) {
		List<EquipeTournoi> tournoi = this.getEquipesTournoi(t);
		int rank = -1;
		for(EquipeTournoi e : tournoi) {
			if(e.getEquipe().equals(equipe.getNom())) {
				rank = e.getRank();
			}
		}
		return rank;
	}

	/**
	 * Obtient le nom de l'équipe gagnante de la finale d'un tournoi.
	 *
	 * @param t Tournoi pour lequel on veut connaître l'équipe gagnante de la finale.
	 * @return Le nom de l'équipe gagnante de la finale.
	 */
	public String getNomEquipeGagnanteFinale(Tournoi t) {
		int idTournoi = getId(t);
	    try (Statement statement = this.c.createStatement()) {
	        String finaleQuery = "SELECT NomEquipe, IdMatchs, IdTournoi FROM Matchs WHERE IdMatchs = (SELECT MAX(IdMatchs) FROM Matchs WHERE IdTournoi = " + idTournoi + ") AND IdTournoi = " + idTournoi + " AND NbPoints = 3 ";
	        ResultSet rs = statement.executeQuery(finaleQuery);
	        if (rs.next()) {
	            return rs.getString("NomEquipe");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	
	/**
	 * Ajoute un arbitre à un tournoi.
	 *
	 * @param t Tournoi auquel on veut ajouter un arbitre.
	 * @param a Arbitre à ajouter.
	 * @return true si l'ajout a réussi, false sinon.
	 */
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

	/**
	 * Obtient la liste des tournois auxquels une équipe a participé.
	 *
	 * @param nomEquipe Nom de l'équipe.
	 * @return Liste des tournois auxquels l'équipe a participé.
	 */
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


	/**
	 * Obtient un tournoi en utilisant le login et le mot de passe des arbitres.
	 *
	 * @param login    Login associé au tournoi.
	 * @param password Mot de passe associé au tournoi.
	 * @return Tournoi correspondant aux identifiants, s'il existe.
	 */
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
