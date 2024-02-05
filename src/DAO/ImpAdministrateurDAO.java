package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import modele.Administrateur;
import modele.DataBase;

public class ImpAdministrateurDAO implements AdministrateurDAO {
	
	private Connection c;
	
	public ImpAdministrateurDAO() {
		this.c = ConnexionDB.getConnexionDB().getConnexion();
	}

	@Override
	public Optional<Administrateur> getById(Integer... id) {
		try (Statement statement = this.c.createStatement())  {
			ResultSet rs = statement.executeQuery("select * from Administrateur where IdAdministrateur = " + id);
			rs.next();
			return Optional.ofNullable(new Administrateur(rs.getString("Login"), rs.getString("MotDePasse")));
		} catch (SQLException e) {
			System.out.println("Erreur : " + e.getErrorCode());
			return null;
		}
	}

	@Override
	public boolean add(Administrateur value) {
		try (Statement statement = this.c.createStatement())  {
			statement.executeUpdate(String.format("INSERT INTO Administrateur (Login,MotDePasse) VALUES ('%s', '%s')",
					value.getLogin(), value.getPassword()));
			return true;
		} catch (SQLException e) {
			System.out.println("Erreur : " + e.getErrorCode());
			return false;
		}
	}

	@Override
	public boolean update(Administrateur value) {
		try (Statement statement = this.c.createStatement())  {
			ResultSet rs = statement
					.executeQuery(String.format("select IdAdministrateur from administrateur where Login='%s'",
							((Administrateur) value).getLogin()));
			statement.executeUpdate(String.format(
					"set Login = '%s', MotDePasse  = '%s 'FROM Administrateur WHERE IdAdministrateur='%s'",
					value.getLogin(), value.getPassword(), rs.getInt(1)));
			return true;
		} catch (SQLException e) {
			System.out.println("Erreur : " + e.getErrorCode());
			return false;
		}
	}

	@Override
	public boolean delete(Administrateur value) {
		try (Statement statement = this.c.createStatement())  {
			statement.executeUpdate(
					String.format("DELETE FROM Administrateur WHERE Login='%s'", value.getLogin()));
			return true;
		} catch (SQLException e) {
			System.out.println("Erreur : " + e.getErrorCode());
			return false;
		}
	}

	@Override
	public List<Administrateur> getAll() {
		List<Administrateur> admins = new LinkedList<>();
		try (Statement statement = this.c.createStatement())  {
			ResultSet rs = statement.executeQuery("select * from Administrateur");
			if (rs.next()) {
				admins.add(new Administrateur(rs.getString("Login"), rs.getString("MotDePasse")));
			}
		} catch (SQLException e) {
			System.out.println("Erreur : " + e.getErrorCode());
			return null;
		}
		return admins;
	}
}