package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modele.ModeleListeTournois;
import modele.Tournoi;
import ressources.Pages;
import vue.PopupSuppressionTournoi;
import vue.VueListeTournois;
import vue.VueMain;
import vue.VueTournoiLance;

public class ControleurPopupSuppressionTournoi implements ActionListener {
    private ModeleListeTournois modele;
    private PopupSuppressionTournoi popup;
    private VueMain vue;
	private Tournoi tournoi;

    public ControleurPopupSuppressionTournoi(PopupSuppressionTournoi popup, VueMain vue, Tournoi tournoi) {
        this.popup = popup;
        this.tournoi = tournoi;
        this.modele = new ModeleListeTournois();
        this.vue = vue;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Supprimer":
                this.modele.deleteTournoi(this.tournoi);
                if(this.vue instanceof VueListeTournois) {
                	((VueListeTournois)this.vue).remplirTable(this.modele.getAll());
                }
                if(this.vue instanceof VueTournoiLance) {
                	this.vue.changePage(Pages.ACCUEIL);
                }
                this.popup.dispose();
                break;
            case "Annuler":
                this.popup.dispose();
                break;
        }
    }

}
