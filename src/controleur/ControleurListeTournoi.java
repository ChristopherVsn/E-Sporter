package controleur;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;

import modele.ModeleListeTournois;
import modele.Tournoi;
import ressources.Pages;
import ui.ButtonTableActionListener;
import vue.PopupSuppressionTournoi;
import vue.VueAccueil;
import vue.VueConfigurationTournoi;
import vue.VueCreationTournoi;
import vue.VueListeTournois;

public class ControleurListeTournoi implements ButtonTableActionListener {
	private VueListeTournois vue;
	private ModeleListeTournois modele;
	private JTable table;
	private int row;

	public ControleurListeTournoi(VueListeTournois vueListeTournois) {
		this.vue = vueListeTournois;
		this.modele = new ModeleListeTournois();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		switch (b.getActionCommand()) {
			case "edit":
				VueConfigurationTournoi vueConfig = new VueConfigurationTournoi(
					this.modele.getByName(this.table.getValueAt(this.row, 0).toString()));
				this.vue.changePage(vueConfig);
				break;
			case "delete":
				PopupSuppressionTournoi popup = new PopupSuppressionTournoi(
					this.modele.getByName(this.table.getValueAt(this.row, 0).toString()), this.vue);
				popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				popup.setVisible(true);
				break;
			case "Retour":
				this.vue.changePage(Pages.ACCUEIL);
				break;
			case "CreationTournoi":
				this.vue.changePage(Pages.CREATION_TOURNOI);
				Pages.CREATION_TOURNOI.resetField();
				break;
		}
	}

	/**
	 * permet d'associer le tableau des tournois au controleur
	 */
	@Override
	public void setTable(JTable table) {
		this.table = table;
	}

	@Override
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return la liste des tournois de la saison du modele
	 */
	public List<Tournoi> getAll() {
		return this.modele.getAll();
	}
}
