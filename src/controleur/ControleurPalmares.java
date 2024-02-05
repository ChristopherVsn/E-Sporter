package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JTable;

import modele.EquipesSaison;
import ressources.Pages;
import ui.ButtonTableActionListener;
import vue.VueAccueil;
import vue.VueDetailEquipe;
import vue.VuePalmares;

public class ControleurPalmares implements ItemListener, ButtonTableActionListener {
	private EquipesSaison modele;
	private VuePalmares vue;
	private JTable table;
	private int row;

	/**
	 * @param vue de type VuePalmares
	 */
	public ControleurPalmares(VuePalmares vue) {
		this.vue = vue;
		Calendar cal = Calendar.getInstance();
		int anneeEnCours = cal.get(Calendar.YEAR);
		this.modele = EquipesSaison.getInstance(anneeEnCours);
	}

	/**
	 * Remplis le modele de la table qui affichera le palmares d'une saison
	 */
	public void initialiseTableau() {
		this.vue.updateModelTablePalmares(this.modele.getEquipes());
	}

	/**
	 * S'occupe du changement de la saison via la ComboBox,
	 * Puis actualise le palmares en fonction de la saison choisise.
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		try {
			this.modele = EquipesSaison.getInstance(this.vue.getSaison());
			this.vue.updateModelTablePalmares(this.modele.getEquipes());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Permet de fermer la vue.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		switch (bouton.getActionCommand()) {
			case "retour":
				try {
					this.vue.changePage(Pages.ACCUEIL);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				break;
			case "detail":
				try {
					this.vue.changePage(new VueDetailEquipe(
							this.modele.getEquipesByName(this.table.getValueAt(this.row, 1).toString()), vue));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				break;
		}
	}

	@Override
	public void setTable(JTable table) {
		this.table = table;
	}

	@Override
	public void setRow(int row) {
		this.row = row;
	}

}
