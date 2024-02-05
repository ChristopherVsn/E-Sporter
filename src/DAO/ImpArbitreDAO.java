package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modele.Arbitre;

public class ImpArbitreDAO implements ArbitreDAO {

	private Connection c;

	public ImpArbitreDAO() {
		this.c = ConnexionDB.getConnexionDB().getConnexion();
	}

	public ImpArbitreDAO(Connection co) {
		this.c = co;
	}

	@Override
	public List<Arbitre> getAll() {
		List<Arbitre> arbitres = new ArrayList<>();
		String req = "SELECT * FROM Arbitres";
		try (Statement statement = this.c.createStatement()) {
			ResultSet rs = statement.executeQuery(req);
			while (rs.next()) {
				arbitres.add(new Arbitre(rs.getInt("IdArbitres"),rs.getString("Nom"), rs.getString("Prenom")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arbitres;
	}

	@Override
	public Optional<Arbitre> getById(Integer... id) {
		String req = "SELECT * FROM Arbitres WHERE IdArbitres = ?";
		try (PreparedStatement statement = this.c.prepareStatement(req)) {
			int i = 0;
			while (i < id.length) {
				statement.setInt(1, id[i]);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					Arbitre arbitre = new Arbitre(rs.getInt("IdArbitres"), rs.getString("Nom"), rs.getString("Prenom"));
					return Optional.of(arbitre);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public Arbitre getByNP(String nom, String prenom) {
		String req = "SELECT * FROM Arbitres WHERE Nom = ? AND Prenom = ?";
		Arbitre arb = null;
		try (PreparedStatement statement = this.c.prepareStatement(req)) {
			statement.setString(1, nom);
			statement.setString(2, prenom);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				arb = new Arbitre(rs.getInt("IdArbitres"), rs.getString("Nom"), rs.getString("Prenom"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arb;
	}

	@Override
	public boolean add(Arbitre value) {
		boolean b = false;
		String query = String.format("SELECT * FROM Arbitres WHERE Nom = '%s' AND Prenom = '%s'", value.getNom(),
				value.getPrenom());
		try (Statement statement = c.createStatement()) {
			ResultSet r = statement.executeQuery(query);
			if (!r.next()) {
				String insertQuery = String.format("INSERT INTO Arbitres (Nom, Prenom) VALUES ('%s', '%s')",
						value.getNom(), value.getPrenom());
				statement.executeUpdate(insertQuery);
				b = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public boolean delete(Arbitre value) {
		String query = String.format("SELECT * FROM Arbitres WHERE Nom = '%s' AND Prenom = '%s'", value.getNom(),
				value.getPrenom());
		try (Statement statement = c.createStatement()) {
			ResultSet r = statement.executeQuery(query);
			if (r.next()) {
				String deleteQuery = String.format("DELETE FROM Arbitres WHERE Nom = '%s' AND Prenom = '%s'",
						value.getNom(), value.getPrenom());
				statement.executeUpdate(deleteQuery);
				return true;
			} else {
				System.out.println("Arbitre non présent dans la base de données");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Arbitre value) {
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}
}
