package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import modele.Equipe;
import modele.EquipeSaison;
import modele.EquipesSaison;

/**
 * Implémentation de l'accès aux données pour la gestion des équipes.
 */
public class ImpEquipeDAO implements EquipeDAO {
	private Connection c;

	/**
     * Constructeur de la classe ImpEquipeDAO.
     */
	public ImpEquipeDAO() {
		this.c = ConnexionDB.getConnexionDB().getConnexion();
	}

	/**
     * Obtient la liste de toutes les équipes.
     * 
     * @return liste de toutes les équipes
     */
	@Override
	public List<Equipe> getAll() {
		List<Equipe> equipes = new ArrayList<>();
		try (Statement statement = this.c.createStatement()) {
			ResultSet rs = statement.executeQuery("SELECT * FROM Equipe");
			while (rs.next()) {
				equipes.add(new Equipe(rs.getString("NomEquipe"), rs.getString("Pays")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipes;
	}

	/**
     * Obtient la liste de toutes les équipes pour une année donnée.
     * 
     * @param annee année spécifiée
     * @return liste de toutes les équipes pour l'année donnée
     */
	public List<EquipeSaison> getAllByAnnee(int annee) {
		try {
			return EquipesSaison.getInstance(Calendar.getInstance().get(Calendar.YEAR)).getEquipes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
     * Obtient une équipe par son identifiant.
     * 
     * @param id identifiant de l'équipe
     * @return équipe correspondante
     */
	@Override
	public Optional<Equipe> getById(String... id) {
		try (Statement statement = this.c.createStatement()) {
			for (int i = 0; i < id.length; i++) {
				String query = String.format("SELECT * FROM Equipe WHERE NomEquipe=" + id[i]);
				ResultSet rs = statement.executeQuery(query);
				if (rs.next()) {
					return Optional.of(new Equipe(rs.getString("NomEquipe"), rs.getString("Pays")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	/**
     * Ajoute une équipe à la base de données.
     * 
     * @param value équipe à ajouter
     * @return true si l'ajout a réussi, false sinon
     */
	@Override
	public boolean add(Equipe value) {
		int annee = Calendar.getInstance().get(Calendar.YEAR);
		String insertQuery = String.format("INSERT INTO Equipe (NomEquipe, Pays) VALUES ('%s', '%s')", value.getNom(),
				value.getPays());
		System.out.println(insertQuery);

		try (Statement statement = this.c.createStatement()) {
			statement.executeUpdate(insertQuery);
			int i = statement.executeUpdate("INSERT INTO Participer (Annee, NomEquipe) VALUES ('"
					+ Integer.toString(annee) + "', '" + value.getNom() + "' )");
			System.out.println("nb ligne "+i);
			try {
				EquipesSaison.getInstance(annee)
						.addEquipeSaison(new EquipeSaison(value.getNom(), value.getPays(), annee, 0, 1000));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
     * Obtient la liste des équipes non inscrites.
     * 
     * @return liste des équipes non inscrites
     */
	public List<Equipe> getEquipesNonInscrites() {
		List<Equipe> equipes = new ArrayList<>();
		try (Statement statement = this.c.createStatement()) {
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM Equipe WHERE NomEquipe NOT IN " + "(SELECT NomEquipe FROM Participer WHERE Annee = '"
							+ Calendar.getInstance().get(Calendar.YEAR) + "')");
			while (rs.next()) {
				equipes.add(new Equipe(rs.getString("NomEquipe"), rs.getString("Pays")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipes;
	}

	/**
     * Ajoute une équipe à la participation pour une saison donnée.
     * 
     * @param value équipe à ajouter
     * @return true si l'ajout a réussi, false sinon
     */
	public boolean addEquipeSaison(Equipe value) {
		try (Statement statement = this.c.createStatement()) {
			int annee = Calendar.getInstance().get(Calendar.YEAR);
			statement.executeUpdate(
					"INSERT INTO Participer (Annee,NomEquipe) VALUES ('" + annee + "', '" + value.getNom() + "')");
			System.out.println("coucou");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
     * Recherche une équipe dans la base de données.
     *
     * @param nomEquipe nom de l'équipe à rechercher
     * @return true si l'équipe existe, false sinon
     */
	public boolean rechercheEquipe(String nomEquipe) {
		String query = "SELECT * FROM Equipe WHERE NomEquipe = ?";
		try (PreparedStatement pstmt = this.c.prepareStatement(query)) {
			pstmt.setString(1, nomEquipe);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
     * Met à jour les informations d'une équipe dans la base de données.
     *
     * @param value nouvelle information de l'équipe
     * @return true si la mise à jour a réussi, false sinon
     */
	@Override
	public boolean update(Equipe value) {
		try (Statement statement = this.c.createStatement()) {
			statement.executeUpdate(
					String.format("UPDATE Equipe SET Pays='%s' WHERE NomEquipe='%s'", value.getPays(), value.getNom()));
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
     * Supprime une équipe (non implémenté).
     *
     * @param equipe équipe à supprimer
     * @return toujours false, car non implémenté
     */
	@Override
	public boolean delete(Equipe equipe) {
		return false;
	}

	/**
     * Supprime une équipe pour une saison donnée.
     *
     * @param equipe équipe à supprimer
     * @return true si la suppression a réussi, false sinon
     */
	public boolean deleteSaison(EquipeSaison equipe) {
		String deleteQuery = "DELETE FROM Participer WHERE NomEquipe = ?";
		try (PreparedStatement pstmt = this.c.prepareStatement(deleteQuery)) {
			pstmt.setString(1, equipe.getNom());
			pstmt.executeUpdate();
			int annee = Calendar.getInstance().get(Calendar.YEAR);
			try {
				EquipesSaison.getInstance(annee).deleteEquipeSaison(equipe);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
     * Obtient une équipe pour une saison donnée par son nom.
     *
     * @param nomEquipe nom de l'équipe à récupérer
     * @return équipe correspondante pour la saison donnée
     */
	public EquipeSaison getByName(String nomEquipe) {
		String query = "SELECT * FROM Equipe WHERE NomEquipe = ?";
		try (PreparedStatement pstmt = this.c.prepareStatement(query)) {

			pstmt.setString(1, nomEquipe);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				EquipeSaison equipe = new EquipeSaison(rs.getString("NomEquipe"), rs.getString("Pays"),
						Calendar.getInstance().get(Calendar.YEAR), 0);
				return equipe;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
     * Obtient l'identifiant d'une équipe.
     *
     * @param ancienneEquipe équipe dont on veut l'identifiant
     * @return identifiant de l'équipe
     */
	public int getId(Equipe ancienneEquipe) {
		String query = "SELECT IdEquipe FROM Equipe WHERE NomEquipe = ?";
		try (PreparedStatement pstmt = this.c.prepareStatement(query)) {
			pstmt.setString(1, ancienneEquipe.getNom());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int idE = rs.getInt("IdEquipe");
				return idE;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
     * Supprime tous les joueurs d'une équipe.
     *
     * @param equipe équipe dont on veut supprimer les joueurs
     * @return true si la suppression a réussi, false sinon
     */
	public boolean clearJoueurInEquipe(Equipe equipe) {
		String query = "select IdJoueur as id from Joueur where NomEquipe = '" + equipe.getNom() + "'";
		try {
			Statement st = this.c.createStatement();
			ResultSet rs = st.executeQuery(query);
			Statement st1 = this.c.createStatement();
			while (rs.next()) {
				st1.executeUpdate("DELETE FROM Joueur WHERE IdJoueur = " + rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}