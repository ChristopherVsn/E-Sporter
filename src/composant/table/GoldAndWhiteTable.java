package composant.table;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import ressources.CharteGraphique;

public class GoldAndWhiteTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GoldAndWhiteTable() {
		initComponent();
		// this.addMouseMotionListener(new MouseMotionListener() {
		//
		// @Override
		// public void mouseDragged(MouseEvent e) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void mouseMoved(MouseEvent e) {
		// System.out.println("x : " + e.getX());
		//
		// }
		//
		// });
	}

	private void initComponent() {
		setBehavior();
		drawTable();
		setGrid();
		setText();
	}

	private void setGrid() {
		setGridColor(CharteGraphique.GRAY_LIGHT);
		setShowVerticalLines(false);
	}

	private void setBehavior() {
		setFocusable(false);
		setRowSelectionAllowed(false);
		setRowHeight(30);
		getTableHeader().setResizingAllowed(false);
		getTableHeader().setReorderingAllowed(false);
	}

	private void setText() {
		setFont(CharteGraphique.TEXT);
		setForeground(CharteGraphique.WHITE);
		getTableHeader().setFont(CharteGraphique.TABLE_TITLE);
		getTableHeader().setForeground(CharteGraphique.GOLD);
	}

	private void drawTable() {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setBackground(CharteGraphique.BACKGROUND);
		setSelectionBackground(CharteGraphique.BACKGROUND);
		getTableHeader().setBackground(CharteGraphique.BACKGROUND);
		getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, CharteGraphique.GRAY_LIGHT));
		getTableHeader().setOpaque(false);
	}
}
