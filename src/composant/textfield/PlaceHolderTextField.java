package composant.textfield;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;

import ressources.CharteGraphique;

/**
 * Un JTextField avec un PlaceHolder
 * 
 * @author Vivien
 *
 */
public class PlaceHolderTextField extends JTextField {

	private static final long serialVersionUID = 1L;
	private String placeHolderText;
	private PlaceHolder listener;

	/**
	 * Crée un nouveau <code>PlaceHolderTextField</code>
	 * 
	 * @param placeHolder le texte du placeHolder.
	 */
	public PlaceHolderTextField(String placeHolder) {
		super(placeHolder);
		this.placeHolderText = placeHolder;
		this.initComponent();
	}

	/**
	 * Défini les couleurs du texte en placeHolder et en écriture
	 * 
	 * @param hover la couleur du texte en PlaceHolder
	 * @param text  la couleur du texte en écriture
	 */
	public void setPlaceHolderColors(Color hover, Color text) {
		this.setForeground(hover);
		this.removeFocusListener(this.listener);
		this.listener = new PlaceHolder(this.placeHolderText, hover, text);
		this.addFocusListener(this.listener);
	}

	private void initComponent() {
		this.listener = new PlaceHolder(this.placeHolderText, CharteGraphique.GRAY_LIGHT, CharteGraphique.BLACK);
		this.addFocusListener(this.listener);
		this.setFont(CharteGraphique.PLACEHOLDER_TEXT);
		this.setForeground(CharteGraphique.GRAY_LIGHT);
	}

	/**
	 * @return si le Text Field est vide au regard du texte en écriture.
	 */
	public boolean isEmpty() {
		return (this.getText().equals(this.placeHolderText) && this.getFont().equals(CharteGraphique.PLACEHOLDER_TEXT));
	}
}
