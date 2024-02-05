package modele;

import java.util.List;

import DAO.ImpMatchsDAO;
import DAO.ImpTournoiDAO;

/**
 * Modèle pour la sélection du vainqueur d'un match dans un tournoi.
 */
public class ModeleSelectionVainqueurMatch {
    private ImpMatchsDAO matchsDAO;
    private ImpTournoiDAO tournoiDAO;
    private Tournoi t;

    /**
     * Constructeur avec initialisation des objets nécessaires.
     *
     * @param t Le tournoi concerné.
     */
    public ModeleSelectionVainqueurMatch(Tournoi t) {
        this.matchsDAO = new ImpMatchsDAO();
        this.tournoiDAO = new ImpTournoiDAO();
        this.t = t;
    }

    /**
     * Récupère la liste de tous les matchs du tournoi.
     *
     * @return La liste de tous les matchs du tournoi.
     */
    public List<Match> getAll() {
        return matchsDAO.getMatchsByTournoi(this.t);
    }

    /**
     * Définit le score d'une équipe pour un match donné.
     *
     * @param equipe L'équipe concernée.
     * @param score Le score à attribuer.
     * @param idMatch L'identifiant du match.
     */
    public void setScoreEquipe(String equipe, int score, int idMatch) {
        matchsDAO.setScoreEquipe(idMatch, equipe, score, t.getNom());
    }

    /**
     * Récupère le score des équipes pour tous les matchs du tournoi.
     *
     * @return Une représentation des scores des équipes pour tous les matchs du tournoi.
     */
    public String scoreEquipeMatchs() {
        return matchsDAO.scoreEquipeMatchs(t.getNom());
    }

    /**
     * Récupère le nombre de matchs terminés dans le tournoi.
     *
     * @return Le nombre de matchs terminés dans le tournoi.
     */
    public int getNbMatchsTerminees() {
        return this.tournoiDAO.getNbMatchsOverByTournoi(this.t);
    }

    /**
     * Récupère les finalistes du tournoi.
     *
     * @return Le match opposant les finalistes du tournoi.
     */
    public Match getFinalistes() {
        Poule poule = new Poule(this.t);
        return poule.getFinal();
    }

    /**
     * Récupère la phase actuelle du tournoi.
     *
     * @return La phase actuelle du tournoi.
     */
    public Phase getPhase() {
        return this.tournoiDAO.getTurnamentPhase(this.t);
    }
}
