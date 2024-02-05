package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import modele.ModelAccueil;
import modele.ModeleListeTournois;
import modele.ModeleSuppressionEquipe;
import ressources.Pages;
import vue.VueAccueil;
import vue.VuePalmares;
import vue.VueSuppressionEquipe;
import vue.VueTournoiLance;

public class ControleurAccueil implements ActionListener {
	private VueAccueil vue;
	private ModelAccueil modele;
	private ModeleListeTournois modeleTransitifTournoi;
	private ModeleSuppressionEquipe modeleTransitifEquipes;

	public ControleurAccueil(VueAccueil vue) {
		this.vue = vue;
		this.modele = new ModelAccueil();
		this.modeleTransitifTournoi = new ModeleListeTournois();
		this.modeleTransitifEquipes = new ModeleSuppressionEquipe();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton j = (JButton) e.getSource();
		switch (j.getActionCommand()) {
			case "equipe":
				new VueSuppressionEquipe().remplirTable(this.modeleTransitifEquipes.getAll());
				this.vue.changePage(new VueSuppressionEquipe());
				break;
			case "palmares":
				this.vue.changePage(new VuePalmares());
				break;
			case "historique":
				// this.vue.fermerVue(this.vue, new VueHistorique());
				break;

			case "tournois":
				if (this.modele.turnamentInProcess()) {
					// Optimisation possible : créer une méthode update tournoi dans la vue
					// TournoiLance
					this.vue.changePage(new VueTournoiLance(this.modele.getTurnamentInProcess()));
				} else {
					Pages.LISTE_TOURNOIS.remplirTable(this.modeleTransitifTournoi.getAll());
					this.vue.changePage(Pages.LISTE_TOURNOIS);
				}
				break;
			case "arbitres":
				this.vue.changePage(Pages.GESTION_ARBITRES);
				break;
		}
	}

}
