package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import vue.PopupSuppressionEquipe;

public class TrashActionCommand implements ActionListener {

	private String message;
	private boolean affiche;

	public TrashActionCommand(String message, boolean affiche) {
		this.message = message;
		this.affiche = affiche;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.affiche) {
			try {
				PopupSuppressionEquipe dialog = new PopupSuppressionEquipe(this.message, null);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (Exception exept) {
				exept.printStackTrace();
			}
		}

	}

}
