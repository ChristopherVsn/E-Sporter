package ui;

import java.awt.Cursor;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonIconAction extends JButton {
	
	private ImageIcon icon;
	
	public ButtonIconAction(ImageIcon icon) {
		super();
		this.icon = icon;
		setIcon(this.icon);
		setOpaque(false);
		setFocusPainted(false);
		setBorderPainted(false);
		setMargin(new Insets(2, 2, 2, 2));
		setCursor(new Cursor(Cursor.HAND_CURSOR));	
	}
}
