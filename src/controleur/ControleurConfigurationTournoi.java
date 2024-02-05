package controleur;

import java.awt.event.ActionEvent;

import javax.swing.JTable;

import modele.Arbitre;
import modele.EquipeSaison;
import modele.ModeleGestionTournoi;
import ressources.Pages;
import ui.ButtonTableActionListener;
import vue.VueConfigurationTournoi;
import vue.VueTournoiLance;

public class ControleurConfigurationTournoi implements ButtonTableActionListener {
	public enum Etat {
		ATTENTE_SELECTION, SELECTION_EN_COURS, LANCEMENT_TOURNOI
	}

	private VueConfigurationTournoi vue;
	private VueTournoiLance vueLancement;
	private ModeleGestionTournoi modele;
	private Etat etat;
	private EquipeSaison equipeSelectionne;
	private Arbitre arbitreSelectionne;
	private JTable table;
	private int row;

	/**
	 * Initialise le modèle
	 * @param vue du tournoi
	 */
	public ControleurConfigurationTournoi(VueConfigurationTournoi vue) {
		this.vue = vue;
		this.modele = new ModeleGestionTournoi(this.vue.getTournoi());
		this.etat = Etat.SELECTION_EN_COURS;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (this.etat) {
		case SELECTION_EN_COURS:
			if (this.vue.estBoutonRetour(e)) {
				this.vue.changePage(Pages.LISTE_TOURNOIS);
			}
			
			// si on choisit un arbitre disponible
			if (this.vue.isArbitreSelect()) {
				String str = this.vue.getComboBoxArbitres().getSelectedItem().toString();
				String[] arb = str.split(" ");
				this.arbitreSelectionne = this.modele.getArbitre(arb[0], arb[1]);
				this.modele.selectArbitre(arbitreSelectionne);
				majArbitres();
			}
			// si on choisit une équipe disponible
			else if (this.vue.isEquipeSelect()) {
				equipeSelectionne = this.modele
						.getEquipeByName(this.vue.getComboBoxEquipes().getSelectedItem().toString());
				this.modele.selectEquipe(equipeSelectionne);
				majEquipes();
			}
			// si on sélectionne un arbitre pour le supprimer
			else if (e.getActionCommand().equals("deleteArbitre")) {
				this.arbitreSelectionne = this.modele.getArbitre(this.table.getValueAt(row, 0).toString(),
						this.table.getValueAt(row, 1).toString());
				this.modele.unselectArbitre(arbitreSelectionne);
				majArbitres();
			}
			// si on sélectionne une équipe pour supprimer
			else if (e.getActionCommand().equals("deleteEquipe")) {
				equipeSelectionne = this.modele.getEquipeByName(this.table.getValueAt(row, 0).toString());
				this.modele.unselectEquipe(equipeSelectionne);
				majEquipes();
			}
			
			//on dégrise le bouton de lancement si les contraintes sont ok
			this.vue.activeLancement(this.modele.checkContraintes());
			
			// si on lance, on génère les matchs dans la BD
			if (e.getActionCommand().equals("lancerTournoi")) {
				this.modele.generateMatch();
				this.modele.addArbitres();
				this.vueLancement = new VueTournoiLance(this.vue.getTournoi());
				this.vue.changePage(vueLancement);
				this.etat = Etat.LANCEMENT_TOURNOI;
			}
		case LANCEMENT_TOURNOI:
			break;
		}
	}

	/**
	 * permet de lier à la table associée
	 */
	@Override
	public void setTable(JTable table) {
		this.table = table;
	}

	/**
	 * permet de lier à la ligne associée
	 */
	@Override
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @param e qui va manipuler le bouton, et si le bouton est un bouton retour, va
	 *          rediriger
	 */
	private void checkRetour(ActionEvent e) {
		if (this.vue.estBoutonRetour(e)) {
			this.vue.changePage(Pages.LISTE_TOURNOIS);
		}
	}

	/**
	 * Met à jour toutes les données des tables
	 */
	public void majDonnees() {
		this.majArbitres();
		this.majEquipes();
	}

	/**
	 * Met à jour les données des équipes uniquement
	 */
	private void majEquipes() {
		this.vue.remplirComboBoxEquipes(this.modele.getEquipesDispos()); 
		this.vue.remplirListeEquipesParticipantes(this.modele.getEquipesSelectionnees());
	}

	/**
	 * Met à jour les données des arbitres uniquement
	 */
	private void majArbitres() {
		this.vue.remplirComboBoxArbitres(this.modele.getArbitresDispos());
		this.vue.remplirListeArbitresTournoi(this.modele.getArbitresSelectionnes());
	}
}