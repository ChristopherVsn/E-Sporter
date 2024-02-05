package composant.textfield;

import java.awt.Graphics;

import ressources.CharteGraphique;

/**
 * Un Text Field transparent ayant une ligne couleur Gold en underline
 * 
 * @author Vivien
 *
 */
public class GoldUnderlinedTextField extends PlaceHolderTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crée le nouveau <code>GoldUnderlinedTextField</code> avec un texte en
	 * placeHolder
	 * 
	 * @param placeHolder le texte en placeHolder
	 */
	public GoldUnderlinedTextField(String placeHolder) {
		super(placeHolder);
		setOpaque(false);
		setBorder(null);
		setPlaceHolderColors(CharteGraphique.GRAY_LIGHT, CharteGraphique.WHITE);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(CharteGraphique.GOLD);
		g.fillRect(0, getHeight() - 1, getWidth(), 1);
		super.paintComponent(g);
	}
}