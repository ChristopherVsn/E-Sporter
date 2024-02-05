package controleur;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import composant.textfield.TextFieldPlaceHolderMovable;
import modele.Administrateur;
import modele.ModeleIdentification;
import modele.Tournoi;
import ressources.CharteGraphique;
import ressources.Pages;
import vue.VueIdentification;

public class ControleurIdentification implements ActionListener, CaretListener, FocusListener, KeyListener {

	private VueIdentification vue;
	private ModeleIdentification modele;

	public ControleurIdentification(VueIdentification vue) {
		this.vue = vue;
		this.modele = new ModeleIdentification();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "reduce":
			this.vue.getFrame().setState(Frame.ICONIFIED);
			break;
		case "close":
			this.vue.getFrame().dispose();
			break;
		case "fullScreen":
			this.vue.setFullScreen();
			break;
		case "minScreen":
			this.vue.setMinScreen();
			break;
		case "start":
			Administrateur user = this.vue.getUser();
			Tournoi t = this.modele.getTournoi(user);
			if (this.modele.isUserAdmin(user)) {
				this.vue.fermerVue(this.vue, Pages.ACCUEIL);
				this.vue.resetIdentifiants();
				this.vue.setButtonStartEnable(false);
			} else if (t != null) {
				switch (this.modele.getPhase(t)) {
				case CLOSED:
					this.vue.showMessageError("Identifiants expirés : Tournois clos.");
					break;
				case FINALE:
					this.vue.fermerVue(this.vue, this.modele.getView(t));
					this.vue.resetIdentifiants();
					this.vue.setButtonStartEnable(false);
					break;
				case NOT_STARTED:
					this.vue.showMessageError("Vos identifiants de connexion ne correspondent à aucun compte valide.");
					break;
				case POULE:
					this.vue.fermerVue(this.vue, this.modele.getView(t));
					this.vue.resetIdentifiants();
					this.vue.setButtonStartEnable(false);
					break;
				default:
					break;
				}
			} else {
				this.vue.showMessageError("Vos identifiants de connexion ne correspondent à aucun compte valide.");
			}
		}
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		this.vue.setButtonStartEnable(modele.isUserEmpty(this.vue.getUser()));
		if (this.vue.isError()) {
			this.vue.hideMessageError();
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		System.out.println("cacaca");
		if (this.vue.isError()) {
			this.vue.hideMessageError();
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getSource() instanceof TextFieldPlaceHolderMovable) {
			this.vue.changePasswordBackground(CharteGraphique.GRAY_DARK);
		}
		if (e.getKeyChar() == '\n' && this.vue.isStartEnable()) {
			this.vue.changePasswordBackground(CharteGraphique.VIOLET_LIGHT);
			Administrateur user = this.vue.getUser();
			Tournoi t = this.modele.getTournoi(user);
			if (this.modele.isUserAdmin(user)) {
				this.vue.fermerVue(this.vue, Pages.ACCUEIL);
				this.vue.resetIdentifiants();
				this.vue.setButtonStartEnable(false);
			} else if (t != null) {
				switch (this.modele.getPhase(t)) {
				case CLOSED:
					this.vue.showMessageError("Identifiants expirés : Tournois clos.");
					break;
				case FINALE:
					this.vue.fermerVue(this.vue, this.modele.getView(t));
					this.vue.resetIdentifiants();
					this.vue.setButtonStartEnable(false);
					break;
				case NOT_STARTED:
					this.vue.showMessageError("Vos identifiants de connexion ne correspondent à aucun compte valide.");
					break;
				case POULE:
					this.vue.fermerVue(this.vue, this.modele.getView(t));
					this.vue.resetIdentifiants();
					this.vue.setButtonStartEnable(false);
					break;
				default:
					break;
				}
			} else {
				this.vue.showMessageError("Vos identifiants de connexion ne correspondent à aucun compte valide.");
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
