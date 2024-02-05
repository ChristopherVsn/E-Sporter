package modele;

import java.util.Calendar;
import java.util.List;

import DAO.ImpJoueurDAO;
import DAO.ImpPalmares;
import DAO.ImpTournoiDAO;

public class ModeleDetailEquipe {
    private ImpJoueurDAO joueurDAO;
    private ImpTournoiDAO tournoiDAO;
    private Equipe equipe;

    /**
     * Initialise le modèle avec une équipe donnée.
     *
     * @param equipe L'équipe pour laquelle les détails seront gérés.
     */
    public ModeleDetailEquipe(Equipe equipe) {
        this.joueurDAO = new ImpJoueurDAO();
        this.tournoiDAO = new ImpTournoiDAO();
        this.equipe = equipe;
    }

    /**
     * Obtient la liste des joueurs de l'équipe.
     *
     * @return La liste des joueurs de l'équipe.
     */
    public List<Joueur> getJoueurs() {
        return this.joueurDAO.getJoueursFromEquipe(this.equipe);
    }

    /**
     * Obtient la liste des tournois auxquels l'équipe a participé.
     *
     * @return La liste des tournois de l'équipe.
     */
    public List<Tournoi> getTournois() {
        return this.tournoiDAO.getTournoisFromEquipe(this.equipe.getNom());
    }

    /**
     * Obtient le classement de l'équipe dans un tournoi donné.
     *
     * @param t Le tournoi pour lequel obtenir le classement de l'équipe.
     * @return Le classement de l'équipe dans le tournoi.
     */
    public int getRank(Tournoi t) {
        Tournoi to = tournoiDAO.getByName(t.getNom());
        return this.tournoiDAO.getRankEquipe(to, this.equipe);
    }

    /**
     * Vérifie si un tournoi est terminé.
     *
     * @param t Le tournoi à vérifier.
     * @return true si le tournoi est terminé, sinon false.
     */
    public boolean tournoiFini(Tournoi t) {
        Phase tournamentPhase = this.tournoiDAO.getTurnamentPhase(t);
        if (tournamentPhase != Phase.CLOSED) {
            return false;
        }
        return true;
    }

    /**
     * Obtient le score de l'équipe dans la saison actuelle.
     *
     * @param e L'équipe pour laquelle obtenir le score.
     * @return Le score de l'équipe dans la saison actuelle.
     */
    public String getScore(Equipe e) {
        ImpPalmares palmares = new ImpPalmares();
        List<EquipeSaison> equipes = palmares.getByAnnee(Calendar.getInstance().get(Calendar.YEAR));
        for (EquipeSaison equipeSaison : equipes) {
            if (equipeSaison.getNom().equals(e.getNom())) {
                return String.valueOf(equipeSaison.getScore());
            }
        }
        return "-1";
    }

    /**
     * Obtient le WR (Winning Rate) de l'équipe dans la saison actuelle.
     *
     * @param e L'équipe pour laquelle obtenir le WR.
     * @return Le WR de l'équipe dans la saison actuelle.
     */
    public String getWr(Equipe e) {
        List<EquipeSaison> equipes = EquipesSaison.getInstance(Calendar.getInstance().get(Calendar.YEAR)).getEquipes();
        for (EquipeSaison equipeSaison : equipes) {
            if (equipeSaison.getNom().equals(e.getNom())) {
                return String.valueOf(equipeSaison.getWr());
            }
        }
        return "-1";
    }
}
