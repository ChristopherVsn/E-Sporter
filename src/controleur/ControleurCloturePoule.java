package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import modele.Match;
import modele.Poule;
import ressources.Pages;
import vue.PopupClotureTournoi;
import vue.PopupSuppressionEquipe;
import vue.VueAccueil;
import vue.VueMatchsPouleArbitre;

public class ControleurCloturePoule implements ActionListener {

	private VueMatchsPouleArbitre vue;
	private Poule modele;

	public ControleurCloturePoule(VueMatchsPouleArbitre vue) {
		this.vue = vue;
		this.modele = new Poule(this.vue.getTournoi());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "cloturer":
				Match matchFinal = this.modele.getFinal();
				this.vue.setFinal(matchFinal);
				break;
			case "finFinal":
				try {
					PopupClotureTournoi popup = new PopupClotureTournoi(this.vue.getTournoi().getNom(),this.vue);
	                popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

	                popup.setVisible(true);

	            } catch (Exception exept) {
	                exept.printStackTrace();
	            }
				break;
		}
	}
}
