package composant.combobox;

import java.awt.Cursor;

import javax.swing.JComboBox;

import ressources.CharteGraphique;

/**
 * Un comboBox ayant les bords arrondis en état de repos et les bords du haut arrondis, mais ceux du bas dur en état de selection d'élément
 * @author Vivien
 *
 */
public class RoundedComboBox extends JComboBox<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  Crée un nouveau <code>RoundedComboBox</code>
	 */
	public RoundedComboBox() {
		setBorder(new RoundedCornerBorder());
		setFont(CharteGraphique.TEXT);
		addPopupMenuListener(new ComboBoxControler());
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setFocusable(false);
	}
}
