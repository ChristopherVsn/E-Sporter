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

public class ImpEquipeDAO implements EquipeDAO {
	private Connection c;

	public ImpEquipeDAO() {
		this.c = ConnexionDB.getConnexionDB().getConnexion();
	}

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

	public List<EquipeSaison> getAllByAnnee(int annee) {
		try {
			return EquipesSaison.getInstance(Calendar.getInstance().get(Calendar.YEAR)).getEquipes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

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

	@Override
	public boolean add(Equipe value) {
		int annee = Calendar.getInstance().get(Calendar.YEAR);
		String insertQuery = String.format("INSERT INTO Equipe (NomEquipe, Pays) VALUES ('%s', '%s')", value.getNom(),
				value.getPays());

		try (Statement statement = this.c.createStatement()) {
			statement.executeUpdate(insertQuery);
			int i = statement.executeUpdate("INSERT INTO Participer (Annee, NomEquipe) VALUES ('"
					+ Integer.toString(annee) + "', '" + value.getNom() + "' )");
			try {
				EquipesSaison.getInstance(annee)
						.addEquipeSaison(new EquipeSaison(value.getNom(), value.getPays(), annee, 0, 1000));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;

		} catch (SQLException e) {
			System.out.println("Erreur lors de l'insertion");
			e.printStackTrace();
			return false;
		}
	}

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

	public boolean addEquipeSaison(Equipe value) {
		try (Statement statement = this.c.createStatement()) {
			int annee = Calendar.getInstance().get(Calendar.YEAR);
			statement.executeUpdate(
					"INSERT INTO Participer (Annee,NomEquipe) VALUES ('" + annee + "', '" + value.getNom() + "')");
			try {
				EquipesSaison.getInstance(annee)
						.addEquipeSaison(new EquipeSaison(value.getNom(), value.getPays(), annee, 0));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

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

	@Override
	public boolean delete(Equipe equipe) {
		return false;
	}

	public boolean deleteSaison(EquipeSaison equipe) {
		String deleteQuery = "DELETE FROM Participer WHERE NomEquipe = ?";
		try (PreparedStatement pstmt = this.c.prepareStatement(deleteQuery)) {
			pstmt.setString(1, equipe.getNom());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

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
	
	public boolean clearJoueurInEquipe(Equipe equipe) {
		System.out.println(equipe.getNom());
		String query = "select IdJoueur as id from Joueur where NomEquipe = '"+equipe.getNom()+"'";
		try{
			Statement st = this.c.createStatement();
			ResultSet rs = st.executeQuery(query);
			Statement st1 = this.c.createStatement();
			while(rs.next()) {
				st1.executeUpdate("DELETE FROM Joueur WHERE IdJoueur = "+rs.getInt("id"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}