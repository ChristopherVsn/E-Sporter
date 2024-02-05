package ui;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import ressources.CharteGraphique;

public class ObjectDesigner {


	
	public static void setScrollPaneUI(JScrollPane... scroll) {
		for(JScrollPane sc:scroll) {
			sc.getViewport().setBackground(CharteGraphique.BACKGROUND);
			sc.getViewport().setBorder(null);
			sc.setBorder(new EmptyBorder(0,0,0,0));
			sc.setOpaque(false);
		}
	}
	
	
	public static void setTableUI(JTable... table) {
		for(JTable j:table) {
			j.setRowHeight(30);
			j.setBorder(null);
			j.setFocusable(false);
			j.setShowVerticalLines(false);
			j.setRowSelectionAllowed(false);
			j.setFont(CharteGraphique.TEXT);
			j.setForeground(CharteGraphique.WHITE);
			j.setGridColor(CharteGraphique.GRAY_LIGHT);
			j.setBackground(CharteGraphique.BACKGROUND);
			j.setSelectionBackground(CharteGraphique.BACKGROUND);

			j.getTableHeader().setFont(CharteGraphique.TABLE_TITLE);
			j.getTableHeader().setForeground(CharteGraphique.GOLD);
			j.getTableHeader().setBackground(CharteGraphique.BACKGROUND);
			j.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, CharteGraphique.GRAY_LIGHT));
			j.getTableHeader().setOpaque(false);
			j.getTableHeader().setResizingAllowed(false);
			j.getTableHeader().setReorderingAllowed(false);
		}
	}
	
	public static void setTitleUI(JLabel... title) {
		for(JLabel label:title) {
			label.setFont(CharteGraphique.TITLE);
			label.setForeground(CharteGraphique.WHITE);
			label.setBorder(new MatteBorder(0, 0, 1, 0, CharteGraphique.GOLD));
			label.setHorizontalAlignment(JLabel.LEADING);
		}
	}
}
