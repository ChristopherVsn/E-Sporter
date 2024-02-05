package ui;

import java.awt.Cursor;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.MatteBorder;

public class DeleteButton extends JButton{

	public DeleteButton() {
		ImageIcon logo = new ImageIcon(DeleteButton.class.getResource("/pictures/icon/trash.png"));
		setIcon(ImageEditor.resize(logo, 15));
		setOpaque(false);
		setFocusPainted(false);
		setBorderPainted(false);
		setMargin(new Insets(2, 2, 2, 2));
		setCursor(new Cursor(Cursor.HAND_CURSOR));	
	}
}