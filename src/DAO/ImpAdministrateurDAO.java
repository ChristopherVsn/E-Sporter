package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import Lancement.DataBase;
import modele.Administrateur;

/**
 * Implémentation de l'accès aux données pour la gestion des administrateurs.
 */
public class ImpAdministrateurDAO implements AdministrateurDAO {

	private Connection c;

	/**
     * Constructeur de la classe ImpAdministrateurDAO.
     */
	public ImpAdministrateurDAO() {
		this.c = ConnexionDB.getConnexionDB().getConnexion();
	}

	/**
     * Obtient un administrateur par son identifiant.
     *
     * @param id identifiant de l'administrateur
     * @return administrateur correspondant à l'identifiant
     */
	@Override
	public Optional<Administrateur> getById(Integer... id) {
		try (Statement statement = this.c.createStatement()) {
			ResultSet rs = statement.executeQuery("select * from Administrateur where IdAdministrateur = " + id);
			rs.next();
			return Optional.ofNullable(new Administrateur(rs.getString("Login"), rs.getString("MotDePasse")));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
     * Ajoute un administrateur à la base de données.
     *
     * @param value administrateur à ajouter
     * @return true si l'ajout a réussi, false sinon
     */
	@Override
	public boolean add(Administrateur value) {
		try (Statement statement = this.c.createStatement()) {
			statement.executeUpdate(String.format("INSERT INTO Administrateur (Login,MotDePasse) VALUES ('%s', '%s')",
					value.getLogin(), value.getPassword()));
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
     * Met à jour les informations d'un administrateur dans la base de données.
     *
     * @param value nouvelle information de l'administrateur
     * @return true si la mise à jour a réussi, false sinon
     */
	@Override
	public boolean update(Administrateur value) {
		try (Statement statement = this.c.createStatement()) {
			ResultSet rs = statement
					.executeQuery(String.format("select IdAdministrateur from administrateur where Login='%s'",
							((Administrateur) value).getLogin()));
			statement.executeUpdate(String.format(
					"set Login = '%s', MotDePasse  = '%s 'FROM Administrateur WHERE IdAdministrateur='%s'",
					value.getLogin(), value.getPassword(), rs.getInt(1)));
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
     * Supprime un administrateur de la base de données.
     *
     * @param value administrateur à supprimer
     * @return true si la suppression a réussi, false sinon
     */
	@Override
	public boolean delete(Administrateur value) {
		try (Statement statement = this.c.createStatement()) {
			statement.executeUpdate(
					String.format("DELETE FROM Administrateur WHERE Login='%s'", value.getLogin()));
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
     * Obtient la liste de tous les administrateurs.
     *
     * @return liste de tous les administrateurs
     */
	@Override
	public List<Administrateur> getAll() {
		List<Administrateur> admins = new LinkedList<>();
		try (Statement statement = this.c.createStatement()) {
			ResultSet rs = statement.executeQuery("select * from Administrateur");
			if (rs.next()) {
				admins.add(new Administrateur(rs.getString("Login"), rs.getString("MotDePasse")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return admins;
	}
}