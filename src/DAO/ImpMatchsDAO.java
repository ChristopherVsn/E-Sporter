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

/**
 * Implémentation de l'interface MatchsDAO fournissant des opérations de gestion
 * des matchs dans la base de données.
 */
public class ImpMatchsDAO implements MatchsDAO {
    private Connection c;

    /**
     * Constructeur par défaut qui initialise la connexion à la base de données.
     */
    public ImpMatchsDAO() {
        this.c = ConnexionDB.getConnexionDB().getConnexion();
    }
    
    /**
     * Récupère tous les matchs présents dans la base de données.
     *
     * @return Une liste de tous les matchs.
     */
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

    /**
     * Récupère un match à partir de son identifiant.
     *
     * @param id Identifiant du match.
     * @return Un objet Optional contenant le match correspondant à l'identifiant.
     */
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

    /**
     * Ajoute un match à la base de données.
     *
     * @param value Le match à ajouter.
     * @return true si l'ajout a réussi, false sinon.
     */
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

    /**
     * Met à jour les informations d'un match dans la base de données.
     *
     * @param value Le match à mettre à jour.
     * @return true si la mise à jour a réussi, false sinon.
     */
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

    /**
     * Supprime un match de la base de données.
     *
     * @param value Le match à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
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

    /**
     * Récupère tous les matchs associés à un tournoi.
     *
     * @param tournoi Le tournoi pour lequel on veut les matchs.
     * @return Une liste des matchs du tournoi.
     */
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

    /**
     * Modifie le score d'une équipe pour un match donné.
     *
     * @param idMatch   Identifiant du match.
     * @param equipe    Nom de l'équipe.
     * @param newScore  Nouveau score de l'équipe.
     * @param nomTournoi Nom du tournoi.
     */	
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
    
    /**
     * Récupère les scores des équipes pour tous les matchs d'un tournoi.
     *
     * @param nomTournoi Nom du tournoi.
     * @return Une chaîne de caractères représentant les scores des équipes pour tous les matchs du tournoi.
     */
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

    /**
     * Récupère le nombre de matchs joués par une équipe dans un tournoi.
     *
     * @param nomEquipe Nom de l'équipe.
     * @param idTournoi Identifiant du tournoi.
     * @return Le nombre de matchs joués par l'équipe dans le tournoi.
     */
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
	
	/**
     * Récupère le nombre de matchs remportés par une équipe dans un tournoi.
     *
     * @param nomEquipe Nom de l'équipe.
     * @param idTournoi Identifiant du tournoi.
     * @return Le nombre de matchs remportés par l'équipe dans le tournoi.
     */
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
	
	/**
     * Récupère le match de la finale d'un tournoi.
     *
     * @param idTournoi Identifiant du tournoi.
     * @return Le match de la finale du tournoi, ou null s'il n'y a pas encore de finale.
     */
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