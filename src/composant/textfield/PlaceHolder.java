package composant.textfield;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import ressources.CharteGraphique;

/**
 * Une classe écoutant les TextFields permettant l'affichage d'un placeHolder
 * sur ces Text Fields
 * 
 * @author Vivien
 *
 */
public class PlaceHolder implements FocusListener {

	private String text;
	private Color foreground;
	private Color hover;

	/**
	 * Crée un nouveau <code>PlaceHolder</code>
	 * 
	 * @param text       le texte du placeHolder
	 * @param hover      la couleur du texte en placeHolder
	 * @param foreground la couleur du texte en écriture
	 */
	public PlaceHolder(String text, Color hover, Color foreground) {
		this.text = text;
		this.hover = hover;
		this.foreground = foreground;
	}

	@Override
	public void focusGained(FocusEvent e) {
		JTextField tf = (JTextField) e.getSource();
		if (tf.getText().equals(this.text)) {
			tf.setFont(CharteGraphique.TEXT);
			tf.setForeground(this.foreground);
			tf.setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		JTextField tf = (JTextField) e.getSource();
		if (tf.getText().equals("")) {
			tf.setFont(CharteGraphique.PLACEHOLDER_TEXT);
			tf.setForeground(this.hover);
			tf.setText(this.text);
		}
	}
}
