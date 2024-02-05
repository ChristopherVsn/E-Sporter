package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;

import modele.EquipeTournoi;
import modele.ModeleTournoiLance;
import modele.Phase;
import ressources.Pages;
import vue.PopupInfoTournoi;
import vue.PopupSuppressionTournoi;
import vue.VueTournoiLance;

public class ControleurTournoiLance implements ActionListener{
	
	private VueTournoiLance vue;
	private ModeleTournoiLance model;
	
	public ControleurTournoiLance(VueTournoiLance vue) {
		this.vue = vue;
		this.model = new ModeleTournoiLance(this.vue.getTournoi());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton)e.getSource();
		switch(b.getActionCommand()) {
			case "accueil":
				this.vue.changePage(Pages.ACCUEIL);
				break;
			case "information":
                PopupInfoTournoi popup = new PopupInfoTournoi(this.vue.getTournoi(), this.model.getArbitres());
                popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                popup.setVisible(true);
                break;
			case "supprimer":
				PopupSuppressionTournoi popupSup = new PopupSuppressionTournoi(this.vue.getTournoi(), this.vue);
				popupSup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				popupSup.setVisible(true);
				break;
		}
	}
	
	/**
	 * @return la liste des équipes EquipeTournoi du tournoi en cours
	 */
	public List<EquipeTournoi> getEquipesTournoi(){
		return this.model.getEquipesTournoi();
	}
	
	/**
	 * @return la Phase du tournoi en cours
	 */
	public Phase getPhaseEnCours() {
		return this.model.getPhase();
	}

	/**
	 * @return le String de la finale du tournoi
	 */
	public String getFinale() {
		return this.model.getEquipesFinale();
	}
}
