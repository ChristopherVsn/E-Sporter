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

    public ModeleDetailEquipe(Equipe equipe) {
        this.joueurDAO = new ImpJoueurDAO();
        this.tournoiDAO = new ImpTournoiDAO();
        this.equipe = equipe;
    }

    public List<Joueur> getJoueurs() {
        return this.joueurDAO.getJoueursFromEquipe(this.equipe);
    }

    public List<Tournoi> getTournois() {
        return this.tournoiDAO.getTournoisFromEquipe(this.equipe.getNom());
    }

    public int getRank(Tournoi t) {
        Tournoi to = tournoiDAO.getByName(t.getNom());
        return this.tournoiDAO.getRankEquipe(to, this.equipe);
    }

    public boolean tournoiFini(Tournoi t) {
        Phase tournamentPhase = this.tournoiDAO.getTurnamentPhase(t);
        if (tournamentPhase != Phase.CLOSED) {
            return false;
        }
        return true;
    }

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
