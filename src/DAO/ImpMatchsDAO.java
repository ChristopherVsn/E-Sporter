package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

import modele.Arbitre;
import modele.Match;
import modele.Phase;
import modele.Tournoi;

public class ImpMatchsDAO implements MatchsDAO {
    private Connection c;

    public ImpMatchsDAO() {
        this.c = ConnexionDB.getConnexionDB().getConnexion();
    }
    
    @Override
    public List<Match> getAll() {
        List<Match> matchs = new ArrayList<>();
        ImpTournoiDAO tournoiDAO = new ImpTournoiDAO();
        try (Statement statement = c.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM Matchs");

            while (rs.next()) {
                int idTournoi = rs.getInt("IdTournoi");
                int idMatch = rs.getInt("IdMatchs");
                String nomTournoi = tournoiDAO.getById(rs.getInt("IdTournoi")).get().getNom();
                String nomEquipe = rs.getString("NomEquipe");
                int nbPoints = rs.getInt("NbPoints");

                // Recherche du match correspondant dans la liste
                Boolean exist = false;
                for (Match match : matchs) {
                    if (match.getIdMatch() == idMatch && match.getNomTournoi().equals(nomTournoi)) {
                        // Si le match existe déjà dans la liste, on ajoute l'équipe et les points
                        Map<String, Integer> equipes = match.getEquipes();
                        equipes.put(nomEquipe, nbPoints);
                        match.setEquipes(equipes);
                        exist = true;
                    }
                }

                if (!exist) {
                    // Sinon, on crée un nouveau match avec une nouvelle map d'équipes
                    Map<String, Integer> equipes = new HashMap<>();
                    equipes.put(nomEquipe, nbPoints);
                    matchs.add(new Match(idMatch, nomTournoi, equipes, idTournoi));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matchs;
    }

    @Override
    public Optional<Match> getById(Integer... id) {
        Optional<Match> match = Optional.empty();
        ImpTournoiDAO tournoiDAO = new ImpTournoiDAO();
        try (Statement statement = c.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM Matchs");

            while (rs.next()) {
                int idMatch = rs.getInt("IdMatchs");
                String nomTournoi = tournoiDAO.getById(rs.getInt("IdTournoi")).get().getNom();
                String nomEquipe = rs.getString("NomEquipe");
                int nbPoints = rs.getInt("NbPoints");
                int idTournoi = rs.getInt("IdTournoi");
                if (!match.isPresent()) {
                    match = Optional.of(new Match(idMatch, nomTournoi, new HashMap<String, Integer>(), idTournoi));
                } else {
                    Map<String, Integer> equipes = match.get().getEquipes();
                    equipes.put(nomEquipe, nbPoints);
                    match.get().setEquipes(equipes);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return match;
    }

    @Override
    public boolean add(Match value) {
        try (Statement statement = c.createStatement()) {
            for (Map.Entry<String, Integer> entry : value.getEquipes().entrySet()) {
                statement.executeUpdate("INSERT INTO Matchs VALUES (" + value.getIdMatch() + ", "
                        + value.getIdTournoi() + ", '" + entry.getKey() + "', " + entry.getValue() + ")");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Match value) {
        try (Statement statement = c.createStatement()) {
            List<String> nameEquipe = value.getEquipes().keySet().stream().collect(Collectors.toList());
            statement.executeUpdate("UPDATE Matchs "
                    + "set NbPoints = " + value.getEquipes().get(nameEquipe.get(0))
                    + " where IdTournoi = " + value.getIdTournoi() + " and IdMatchs = " + value.getIdMatch()
                    + "and NomEquipe = '" + nameEquipe.get(0) + "'");
            statement.executeUpdate("UPDATE Matchs "
                    + "set NbPoints = " + value.getEquipes().get(nameEquipe.get(1))
                    + " where IdTournoi = " + value.getIdTournoi() + " and IdMatchs = " + value.getIdMatch()
                    + "and NomEquipe = '" + nameEquipe.get(1) + "'");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Match value) {
        try (Statement statement = c.createStatement()) {
            statement.executeUpdate("DELETE FROM Matchs WHERE IdMatchs = " + value.getIdMatch());
            return true; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Match> getMatchsByTournoi(Tournoi tournoi) {
        List<Match> matchs = new ArrayList<>();
        ImpTournoiDAO tournoiDAO = new ImpTournoiDAO();
        try (Statement statement = c.createStatement()) {
            ResultSet rs = statement
                    .executeQuery("SELECT * FROM Matchs WHERE IdTournoi = " + tournoiDAO.getId(tournoi) + "ORDER BY IdMatchs");

            while (rs.next()) {
                int idTournoi = rs.getInt("IdTournoi");
                int idMatch = rs.getInt("IdMatchs");
                String nomTournoi = tournoiDAO.getById(rs.getInt("IdTournoi")).get().getNom();
                String nomEquipe = rs.getString("NomEquipe");
                int nbPoints = rs.getInt("NbPoints");

                // Recherche du match correspondant dans la liste
                Boolean exist = false;
                for (Match match : matchs) {
                    if (match.getIdMatch() == idMatch && match.getNomTournoi().equals(nomTournoi)) {
                        // Si le match existe déjà dans la liste, on ajoute l'équipe et les points
                        Map<String, Integer> equipes = match.getEquipes();
                        equipes.put(nomEquipe, nbPoints);
                        match.setEquipes(equipes);
                        exist = true;
                    }
                }

                if (!exist) {
                    // Sinon, on crée un nouveau match avec une nouvelle map d'équipes
                    Map<String, Integer> equipes = new HashMap<>();
                    equipes.put(nomEquipe, nbPoints);
                    matchs.add(new Match(idMatch, nomTournoi, equipes, idTournoi));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matchs;
    }

    public void setScoreEquipe(int idMatch, String equipe, int newScore, String nomTournoi) {
        ImpTournoiDAO tournoiDAO = new ImpTournoiDAO();
        try (Statement statement = c.createStatement()) {
            String updateQuery = "UPDATE Matchs SET NbPoints = " + newScore +
                    " WHERE IdMatchs = " + idMatch +
                    " AND NomEquipe = '" + equipe + "'" +
                    " AND IdTournoi = " + tournoiDAO.getId(tournoiDAO.getByName(nomTournoi));
            statement.executeUpdate(updateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String scoreEquipeMatchs(String nomTournoi) {
        String res = "";
        ImpTournoiDAO tournoiDAO = new ImpTournoiDAO();
        try (Statement statement = c.createStatement()) {
            String selectQuery = "SELECT * FROM Matchs WHERE IdTournoi = "
                    + tournoiDAO.getId(tournoiDAO.getByName(nomTournoi));
            ResultSet rs = statement.executeQuery(selectQuery);
            while (rs.next()) {
                res += "Equipe : " + rs.getString("NomEquipe") + " NbPts : " + rs.getInt("NbPoints") + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

	public int getNbMatchsPlayed(String nomEquipe, int idTournoi) {
		try (Statement statement = c.createStatement()) {
            String selectQuery = "SELECT count(*) as nb FROM Matchs WHERE IdTournoi = "
                    + idTournoi+ " and NomEquipe = '"+nomEquipe+"'";
            ResultSet rs = statement.executeQuery(selectQuery);
            if(rs.next()) {
            	return rs.getInt("nb");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return -1;
	}
	
	public int getNbMatchsWin(String nomEquipe, int idTournoi) {
		try (Statement statement = c.createStatement()) {
            String selectQuery = "SELECT count(*) as nb FROM Matchs WHERE IdTournoi = "
                    + idTournoi+ " and NomEquipe = '"+nomEquipe+"' and NbPoints = 3s";
            ResultSet rs = statement.executeQuery(selectQuery);
            if(rs.next()) {
            	return rs.getInt("nb");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return -1;
	}
	
    public Match getFinal(int idTournoi) {
        ImpTournoiDAO tournoiDAO = new ImpTournoiDAO();
        Tournoi t = tournoiDAO.getById(idTournoi).get();
        String nomTournoi = t.getNom();
        if (tournoiDAO.getTurnamentPhase(t) == Phase.CLOSED) {
            try (Statement statement = c.createStatement()) {
                ResultSet rs = statement.executeQuery(
                        "SELECT * FROM Matchs where IdTournoi = " + idTournoi + " order by IdMatchs DESC");
                if (rs.next()) {
                    if (rs.absolute(1)) {
                        int idMatch = rs.getInt("IdMatchs");
                        String nomEquipe = rs.getString("NomEquipe");
                        int nbPoints = rs.getInt("NbPoints");
                        // Déplacez le curseur vers la deuxième ligne (zéro index)
                        if (rs.absolute(2)) {
                            String nomEquipe2 = rs.getString("NomEquipe");
                            int nbPoints2 = rs.getInt("NbPoints");
                            Map<String, Integer> equipes = new HashMap<>();
                            equipes.put(nomEquipe, nbPoints);
                            equipes.put(nomEquipe2, nbPoints2);
                            return new Match(idMatch, nomTournoi, equipes, idTournoi);
                        }
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}