package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import modele.Equipe;
import modele.Joueur;

/**
 * Implémentation de l'interface JoueurDAO fournissant des opérations de gestion
 * des joueurs dans la base de données.
 */
public class ImpJoueurDAO implements JoueurDAO {
	private Connection c;

	/**
     * Constructeur par défaut qui initialise la connexion à la base de données.
     */
	public ImpJoueurDAO() {
		this.c = ConnexionDB.getConnexionDB().getConnexion();
	}

	/**
     * Constructeur avec paramètre permettant une connexion.
     *
     * @param c Connexion à la base de données.
     */
	public ImpJoueurDAO(Connection c) {
		this.c = c;
	}

	/**
     * Récupère tous les joueurs présents dans la base de données.
     *
     * @return Une liste de tous les joueurs.
     */
	@Override
	public List<Joueur> getAll() {
		List<Joueur> joueurs = new ArrayList<>();
		try (Statement statement = c.createStatement()) {
			String query = String.format("SELECT * FROM Joueur");
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				joueurs.add(new Joueur(rs.getString("Pseudo"), rs.getString("NomEquipe")));
			}

		} catch (SQLException e) {
		}
		return joueurs;
	}

	/**
     * Récupère un joueur à partir de son identifiant.
     *
     * @param id Identifiant du joueur.
     * @return Un objet Optional contenant le joueur correspondant à l'identifiant.
     */
	@Override
	public Optional<Joueur> getById(Integer... id) {
		Joueur joueur = null;
		try (Statement statement = c.createStatement()) {
			String query = String.format("SELECT * FROM Joueur WHERE IdJoueur=" + id);
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				joueur = new Joueur(rs.getString("Pseudo"), rs.getString("NomEquipe"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(joueur);

	}
	
	/**
     * Vérifie l'existence d'un joueur dans la saison actuelle.
     *
     * @param value Le joueur à vérifier.
     * @return 0 si le joueur n'existe pas, 1 si le joueur existe mais n'est pas dans la saison actuelle,
     *         2 si le joueur existe et participe à la saison actuelle.
     */
	public int existeInSaison(Joueur value) {
		try (Statement statement = c.createStatement()) {
			ResultSet existe = statement
					.executeQuery("select count(*) as nb from Joueur where Pseudo = '" + value.getPseudo() + "'");
			existe.next();
			System.out.println(value.getPseudo()+" : "+existe.getInt("nb"));
			if (existe.getInt("nb") == 0) {
				return 0;
			} else {
				ResultSet rs = statement
						.executeQuery("select count(*) as nb " + "from Joueur as j, Equipe as e, Participer as p "
								+ "where p.Annee = '" + Calendar.getInstance().get(Calendar.YEAR) + "' and "
								+ "e.NomEquipe = p.NomEquipe and " + "j.NomEquipe = e.NomEquipe");
				rs.next();
				System.out.println("coucou"+rs.getInt("nb"));
				if (rs.getInt("nb") == 0) {
					return 1;
				}
			}
			return 2;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
     * Ajoute un joueur à la base de données.
     *
     * @param value Le joueur à ajouter.
     * @return true si l'ajout a réussi, false sinon.
     */
	@Override
	public boolean add(Joueur value) {
		try (Statement statement = c.createStatement()) {
			statement.executeUpdate("Insert into Joueur (Pseudo, NomEquipe) Values('" + value.getPseudo() + "', '"
					+ value.getnomEquipe() + "')");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	 /**
     * Met à jour les informations d'un joueur dans la base de données.
     *
     * @param value Le joueur à mettre à jour.
     * @return true si la mise à jour a réussi, false sinon.
     */
	@Override
	public boolean update(Joueur value) {
		try (Statement statement = c.createStatement()) {
			statement.executeUpdate("UPDATE Joueur " + "SET NomEquipe= " + value.getnomEquipe() + ", Pseudo = "
					+ value.getPseudo() + " WHERE Pseudo='" + value.getPseudo() + "'");
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
     * Supprime un joueur de la base de données.
     *
     * @param value Le joueur à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
	@Override
	public boolean delete(Joueur value) {
		try (Statement statement = c.createStatement()) {
			statement.executeUpdate("DELETE FROM Joueur WHERE Pseudo='" + value.getPseudo() + "'");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	 /**
     * Récupère la liste des joueurs d'une équipe.
     *
     * @param equipe L'équipe pour laquelle on veut les joueurs.
     * @return Une liste des joueurs de l'équipe.
     */
	public List<Joueur> getJoueursFromEquipe(Equipe equipe) {
		List<Joueur> lJoueurs = new ArrayList<>();
		try (Statement statement = c.createStatement()) {
			String query = String.format("SELECT * FROM Joueur WHERE nomEquipe='%s'", equipe.getNom());
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				lJoueurs.add(new Joueur(rs.getString("Pseudo"), rs.getString("NomEquipe")));
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return lJoueurs;

	}

}