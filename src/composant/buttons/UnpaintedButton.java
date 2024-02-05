package composant.buttons;

import java.awt.Cursor;
import java.awt.Insets;

import javax.swing.JButton;

import ressources.CharteGraphique;

/**
 * @author wiski
 *
 *	@apiNote
 *	L'unpaintedButton est un bouton sans fond ni bord qui affiche uniquement le
 *	texte du boutton au format suivant : 
 *	Couleur du texte : 	Blanc, Font : Calibri, 20
 */
public class UnpaintedButton extends JButton{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Crée un UnpaintedButton ayant pour texte par défaut celui passé en paramètre.
	 * @param name le texte du boutton
	 * 		
	 */
	public UnpaintedButton(String name) {
		setText(name);
		drawButton();
	}
	
	/**
	 * Crée un UnpaintedButton n'ayant aucun texte par défaut.
	 */
	public UnpaintedButton() {
		drawButton();
	}
	
	/**
	 * Configure le design de l'UnpaintedButton
	 */
	private void drawButton() {
		setFocusPainted(false);
		setOpaque(false);
		setBorderPainted(false);
		setMargin(new Insets(0, 0, 2, 0));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setFont(CharteGraphique.BUTTON_TEXT);
		setForeground(CharteGraphique.WHITE);
	}
}
