package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;

import modele.EquipeSaison;
import modele.Joueur;
import modele.ModeleCreationEquipe;
import ressources.Pages;
import vue.VueCreationEquipe;
import vue.VueSuppressionEquipe;

public class ControleurCreationEquipe implements ActionListener, KeyListener, ItemListener {
	private enum Etat {
		ATTENTE_INFORMATIONS, INFORMATIONS_SAISIES
	}

	private VueCreationEquipe vue;
	private Etat etat;
	private ModeleCreationEquipe modele;

	/**
	 * initialisation du contrôleur
	 * 
	 * @param Vue creation equipe
	 */
	public ControleurCreationEquipe(VueCreationEquipe v) {
		this.vue = v;
		this.etat = Etat.ATTENTE_INFORMATIONS;
		this.modele = new ModeleCreationEquipe();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		// interaction de l'utilisateur sur les boutons
		if (source instanceof JButton) {
			JButton b = (JButton) e.getSource();
			// bouton retourn
			if (b.getActionCommand().equals("Retour")) {
				try {
					this.vue.changePage(new VueSuppressionEquipe());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			// bouton de validation et vérification que tous les champs sont remplis
			if (this.etat.equals(Etat.INFORMATIONS_SAISIES)
					&& b.getActionCommand().equals("Valider l'enregistrement")) {
				EquipeSaison equipe = new EquipeSaison(this.vue.getNomEquipe(), this.vue.getPays(),
						Calendar.getInstance().get(Calendar.YEAR), 0);
				List<Joueur> joueurs = this.modele.stringToJoueur(this.vue.getJoueurs(), equipe.getNom());
				try {
					// Vérification de la validité de tous les champs
					if (this.checkUp(joueurs, equipe)) {
						addEquipe(equipe, joueurs);
						this.vue.resetChamps();
						this.vue.remplirComboBox();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				this.vue.resetChamps();

			}
			// interaction sur les champs de saisie
		} else {
			try {
				this.vue.setWorldRank(this.modele.getWorldRank(this.vue.getNomEquipe()));
				updateState();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void addEquipe(EquipeSaison equipe, List<Joueur> joueurs) {
			switch (this.modele.addEquipe(equipe)) {
			case 0:
				this.ajoutJoueurs(joueurs);
				break;
			case 1:
				this.vue.afficherMessageErreur("Erreur dans l'ajout de l'equipe dans la base de données\n");
				break;
			case 2:
				this.vue.afficherMessageErreur("Equipe déjà presente dans la saison\n");
				break;
			case 3:
				this.vue.afficherMessageErreur("Erreur dans l'ajout de l'équipe à la saison\n");
				break;
			}
	}

	/**
	 * ajoute les joueurs à la base de données
	 * 
	 * @param une liste de joueurs
	 */
	private void ajoutJoueurs(List<Joueur> joueurs) {
		try {
			if(!this.modele.addJoueurs(joueurs)) {
				this.vue.afficherMessageErreur("Erreur dans l'ajout d'un joueur contacter l'equipe technique\n");
			} 
			this.vue.changePage(new VueSuppressionEquipe());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


	/**
	 * vérification que les informations saisies peuvent être enregistrées dans la
	 * base de données
	 * 
	 * @param liste  des joueurs
	 * @param equipe
	 * @return si les données sont correctes
	 */
	private boolean checkUp(List<Joueur> joueurs, EquipeSaison equipe) {
		switch (this.modele.testJoueurSaisie(joueurs)) {
		case 2:
			this.vue.afficherMessageErreur("Nom d'un joueur trop long 20 caractères max\n");
			return false;
		case 1:
			this.vue.afficherMessageErreur("Doublon lors de la saisie des joueurs\n");
			return false;
		default:
			if (equipe.getNom().length() > 50) {
				this.vue.afficherMessageErreur("Nom de l'équipe trop long 50 caractères max\n");
				return false;
			}
			String message = this.modele.joueurValide(joueurs);
			System.out.println(message);
			if(!message.equals("true")) {
				this.vue.afficherMessageErreur("Joueur " + message + " déjà présent dans une autre équipe de la saison\n");
				return false;
			}
			return true;

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		updateState();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		updateState();
	}

	/**
	 * dégrisement du bouton de validation
	 */
	private void updateState() {
		switch (this.etat) {
		// cas où tous les champs sont saisis
		case ATTENTE_INFORMATIONS:
			if (this.vue.informationSaisies()) {
				this.vue.setButtonValidateEnable(true);
				this.etat = Etat.INFORMATIONS_SAISIES;
			}
			break;
		// cas où tous les champs ne sont plus saisis
		case INFORMATIONS_SAISIES:
			if (!this.vue.informationSaisies()) {
				this.vue.setButtonValidateEnable(false);
				this.etat = Etat.ATTENTE_INFORMATIONS;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		updateState();
	}

	/**
	 * changement de l'affichage du world rank
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		try {
			if (this.modele.getAllEquipes().contains(this.modele.getEquipeByName(this.vue.getNomEquipe()))) {
				this.vue.setWorldRank(this.modele.getWorldRank(this.vue.getNomEquipe()));
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * initialisation de world rank
	 */
	public void initialisationWr() {
		try {
			this.vue.setWorldRank(this.modele.getWorldRank(this.vue.getNomEquipe()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}