package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JTable;

import modele.Arbitre;
import modele.ModeleArbitre;
import ressources.Pages;
import ui.ButtonTableActionListener;
import vue.VueAccueil;
import vue.VueGestionArbitres;

public class ControleurGestionArbitres implements ButtonTableActionListener, KeyListener {
	public enum Etat {
		ATTENTE_SAISIE, CHAMPS_REMPLIS
	}

	private VueGestionArbitres vue;
	private ModeleArbitre modele;
	private Etat etat;
	private JTable table;
	private int row;

	private Arbitre arbitreSelectionne;

	public ControleurGestionArbitres(VueGestionArbitres vue) {
		this.vue = vue;
		this.modele = new ModeleArbitre();
		this.etat = Etat.ATTENTE_SAISIE;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// si retour, retour
		this.checkRetour(e);

		// suppression arbitre
		if (e.getActionCommand().equals("deleteArbitre")) {
			this.arbitreSelectionne = this.modele.getArbitre(this.table.getValueAt(row, 0).toString(),
					this.table.getValueAt(row, 1).toString());
			this.modele.deleteArbitreBD(arbitreSelectionne);
			majListeArbitres();
		}

		// si on a les champs remplis
		if (this.etat.equals(Etat.CHAMPS_REMPLIS)) {
			if (!this.vue.champsContiennentUniquementDesLettres()) {
				this.vue.afficherMessageErreur("Les noms et prénoms ne doivent comporter que des lettres");
				this.etat = Etat.ATTENTE_SAISIE;
			} else if (e.getActionCommand().equals("addArbitre")) {
				Arbitre nvArbitre = new Arbitre(0, this.vue.getNomArbitre(), this.vue.getPrenomArbitre());
				if (this.modele.ajouterArbitreBD(nvArbitre)) {
					this.vue.viderChamps();
					majListeArbitres();
					this.vue.setBoutonValidateVisible(false);
					this.etat = Etat.ATTENTE_SAISIE;
				} else {
					this.vue.afficherMessageErreur("Arbitre déjà présent.e dans la base de données");
				}
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
//		 updateState();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		updateState();
	}

	@Override
	public void keyPressed(KeyEvent e) {
//		 updateState();
	}

	/**
	 * permet d'associer le tableau des arbitres au controleur
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
	 * actualise dans la vue les arbitres de la saison
	 */
	public void majListeArbitres() {
		this.vue.remplirListeArbitres(getAllArbitres());
	}

	/**
	 * @return les arbitres de la saison du modele
	 */
	public List<Arbitre> getAllArbitres() {
		return this.modele.getAllArbitres();
	}

	/**
	 * à chaque pression du clavier sur un champ de texte, on va vérifier si les
	 * champs sont remplis ou non
	 */
	private void updateState() {
		switch (this.etat) {
		case ATTENTE_SAISIE:
			if (this.vue.getNomArbitre().isEmpty()) {
				this.vue.genereTFNom();
			}
			if (this.vue.getPrenomArbitre().isEmpty()) {
				this.vue.genereTFPrenom();
			}
			if (this.vue.informationSaisies()) {
				this.vue.setBoutonValidateVisible(true);
				this.etat = Etat.CHAMPS_REMPLIS;
			}
			break;
		case CHAMPS_REMPLIS:
			if (!this.vue.informationSaisies()) {
				this.vue.setBoutonValidateVisible(false);
				this.etat = Etat.ATTENTE_SAISIE;
			}
		}
	}


	/**
	 * @param e qui va manipuler le bouton, et si le bouton est un bouton retour, va
	 *          rediriger
	 */
	private void checkRetour(ActionEvent e) {
		if (this.vue.estBoutonRetour(e)) {
			this.vue.changePage(Pages.ACCUEIL);
		}
	}

}
