package composant.buttons;

import java.awt.Cursor;
import java.awt.Insets;
import javax.swing.JButton;

import ressources.CharteGraphique;

public class UnpaintedButton extends JButton{
	
	private static final long serialVersionUID = 1L;

	public UnpaintedButton(String name) {
		setText(name);
		drawButton();
	}
	
	public UnpaintedButton() {
		drawButton();
	}
	
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
