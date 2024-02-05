package composant.datechooser;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ressources.CharteGraphique;

public class ButtonHover extends MouseAdapter {

	@Override
	public void mouseEntered(MouseEvent e) {
		Button b = (Button) e.getSource();
		if (!b.getText().isEmpty()) {
			b.setCursor(new Cursor(Cursor.HAND_CURSOR));
			if (!(b.getBackground().equals(CharteGraphique.TURQUOISE_RADIANT))
					&& !(b.getBackground().equals(CharteGraphique.TURQUOISE_LIGHT))) {
				b.setBackground(CharteGraphique.GRAY_LIGHT.brighter());
				b.setForeground(CharteGraphique.WHITE);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Button b = (Button) e.getSource();
		if (!b.getText().isEmpty()) {
			b.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			if (!(b.getBackground().equals(CharteGraphique.TURQUOISE_RADIANT))
					&& !(b.getBackground().equals(CharteGraphique.TURQUOISE_LIGHT))) {
				b.setBackground(CharteGraphique.WHITE);
				b.setForeground(CharteGraphique.BLACK_LIGHT);
			}
		}
	}
}
