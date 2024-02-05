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

public class ImpJoueurDAO implements JoueurDAO {
	private Connection c;

	public ImpJoueurDAO() {
		this.c = ConnexionDB.getConnexionDB().getConnexion();
	}

	public ImpJoueurDAO(Connection c) {
		this.c = c;
	}

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
			System.out.println("Erreur : " + e.getErrorCode());
		}
		return joueurs;
	}

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
			System.out.println("Erreur : " + e.getErrorCode());
		}
		return Optional.ofNullable(joueur);

	}

	public int existeInSaison(Joueur value) {
		try (Statement statement = c.createStatement()) {
			ResultSet existe = statement
					.executeQuery("select count(*) as nb from Joueur where Pseudo = '" + value.getPseudo() + "'");
			existe.next();
			if (existe.getInt("nb") == 0) {
				return 0;
			} else {
				ResultSet rs = statement
						.executeQuery("select count(*) as nb " + "from Joueur as j, Equipe as e, Participer as p "
								+ "where p.Annee = '" + Calendar.getInstance().get(Calendar.YEAR) + "' and "
								+ "e.NomEquipe = p.NomEquipe and " + "j.NomEquipe = e.NomEquipe");
				rs.next();
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

	public List<Joueur> getJoueursFromEquipe(Equipe equipe) {
		List<Joueur> lJoueurs = new ArrayList<>();
		try (Statement statement = c.createStatement()) {
			String query = String.format("SELECT * FROM Joueur WHERE nomEquipe='%s'", equipe.getNom());
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				lJoueurs.add(new Joueur(rs.getString("Pseudo"), rs.getString("NomEquipe")));
			}
		} catch (SQLException e) {
			System.out.println("Erreur getJoueursFromEquipe: " + e.getErrorCode());
		}
		return lJoueurs;

	}

}