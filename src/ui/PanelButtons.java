package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;

import composant.buttons.PicturedButton;
import ressources.CharteGraphique;

public class PanelButtons extends JPanel {

	private List<ButtonTableType> btns;
	private List<PicturedButton> icons;
	private JTable table;

	public PanelButtons(JTable table, List<ButtonTableType> btns) {
		this.table = table;
		this.btns = btns;
		this.setLayout(new BorderLayout());
		JPanel border = new JPanel();
		border.setLayout(new FlowLayout());
		border.setBackground(CharteGraphique.BACKGROUND);
		this.add(border, BorderLayout.EAST);
		this.icons = new LinkedList<>();
		for (ButtonTableType btn : this.btns) {
			PicturedButton icon = new PicturedButton(btn.getIcon());
			icon.resizeIcon(btn.getSize());
			icon.addActionListener(btn.getAction());
			icon.setActionCommand(btn.getActionCommand());
			border.add(icon);
			this.icons.add(icon);
		}
	}
	
	public PicturedButton getPicturedButtonByCommand(String command) {
		PicturedButton btn = null;
		for(PicturedButton icon:this.icons) {
			if(icon.getActionCommand().equals(command)) {
				btn = icon;
			}
		}
		return btn;
	}

	public void initEvent(int row) {
		for (PicturedButton icon : this.icons) {
			for (ActionListener al : icon.getActionListeners()) {
				if (al instanceof ButtonTableActionListener) {
					ButtonTableActionListener bal = (ButtonTableActionListener) al;
					bal.setTable(this.table);
					bal.setRow(row);
				}
			}
		}
	}
}