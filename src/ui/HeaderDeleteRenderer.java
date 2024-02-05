package ui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import ressources.CharteGraphique;

public class HeaderDeleteRenderer extends DefaultTableCellRenderer {
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int col) {
		JPanel test = new JPanel();
		test.setLayout(new BorderLayout());
		JLabel entete = new JLabel("SUPPRIMER");
		entete.setHorizontalAlignment(JLabel.TRAILING);
		entete.setForeground(CharteGraphique.GOLD);
		test.setBackground(CharteGraphique.BACKGROUND);
		entete.setFont(CharteGraphique.TABLE_TITLE);
		entete.setBorder(new EmptyBorder(0,0,0,5));
		test.add(entete, BorderLayout.EAST);
		return test;
	}
}