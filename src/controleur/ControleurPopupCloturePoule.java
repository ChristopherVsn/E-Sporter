package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modele.Poule;
import vue.PopupClotureTournoi;
import vue.VueIdentification;
import vue.VueMatchsPouleArbitre;

public class ControleurPopupCloturePoule implements ActionListener {
	private Poule modele;
	private VueMatchsPouleArbitre vue;
	private PopupClotureTournoi vuePopup;

	public ControleurPopupCloturePoule(VueMatchsPouleArbitre vueTournoiLance, PopupClotureTournoi vuePopup) {
		this.vue = vueTournoiLance;
		this.vuePopup = vuePopup;
		this.modele = new Poule(vueTournoiLance.getTournoi());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "Valider":
				try {
					this.vuePopup.dispose();
					this.vue.changePage(new VueIdentification());
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				break;
			case "Annuler":
				this.vuePopup.dispose();
				break;
		}
	}

	/**
	 * @return la liste des équipes de la saison du modele
	 */
	// public List<EquipeSaison> getAll() throws Exception {
	// return this.modele.getAll();
	// }
}
