package composant.combobox;

import java.awt.Cursor;

import javax.swing.JComboBox;

import ressources.CharteGraphique;

public class RoundedComboBox extends JComboBox<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoundedComboBox() {
		setBorder(new RoundedCornerBorder());
		setFont(CharteGraphique.TEXT);
		addPopupMenuListener(new ComboBoxControler());
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setFocusable(false);
	}
}
