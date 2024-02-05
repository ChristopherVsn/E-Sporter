package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import modele.Equipe;
import modele.Joueur;
import modele.ModeleDetailEquipe;
import modele.Tournoi;
import vue.VueDetailEquipe;
import vue.VueMain;

public class ControleurDetailEquipe implements ActionListener {
    private VueDetailEquipe vue;
    private ModeleDetailEquipe modele;
    private VueMain vuePrecedente;

    public ControleurDetailEquipe(VueDetailEquipe vue, VueMain vuePrecedente) {
        this.vuePrecedente = vuePrecedente;
        this.vue = vue;
        this.vue.getEquipe();
        this.modele = new ModeleDetailEquipe(this.vue.getEquipe());

    }

    public List<Tournoi> getTournois() {
        return this.modele.getTournois();
    }

    public int getRank(Tournoi t) {
        return this.modele.getRank(t);
    }

    public boolean tournoiFini(Tournoi t) {
        return this.modele.tournoiFini(t);
    }

    public List<Joueur> getJoueurs() {
        return this.modele.getJoueurs();
    }

    public String getScore(Equipe e) {
        return this.modele.getScore(e);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "retour":
                this.vue.changePage(vuePrecedente);
                break;
            default:
                break;
        }
    }

    public String getWr(Equipe e) {
        return this.modele.getWr(e);
    }

}
