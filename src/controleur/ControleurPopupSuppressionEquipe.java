package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.PopupSuppressionEquipe;
import vue.VueSuppressionEquipe;
import modele.ModeleSuppressionEquipe;
import java.util.List;

import modele.EquipeSaison;

public class ControleurPopupSuppressionEquipe implements ActionListener {
	private ModeleSuppressionEquipe modele;
	private PopupSuppressionEquipe vue;
	private VueSuppressionEquipe vueSuppressionEquipe;

	public ControleurPopupSuppressionEquipe(PopupSuppressionEquipe vue, VueSuppressionEquipe vueSuppressionEquipe) {
		this.vue = vue;
		this.modele = new ModeleSuppressionEquipe();
		this.vueSuppressionEquipe = vueSuppressionEquipe;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Supprimer":
			try {
				this.modele.deleteEquipe(this.modele.getEquipeByName(this.vue.getEquipe()));
				this.vueSuppressionEquipe.remplirTable(this.modele.getAll());
				this.vue.dispose();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			break;
		case "Annuler":
			this.vue.dispose();
			break;
		}
	}

	/**
	 * @return la liste des équipes de la saison du modele
	 */
	public List<EquipeSaison> getAll() throws Exception {
		return this.modele.getAll();
	}
}
