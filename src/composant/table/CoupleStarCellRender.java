package composant.table;

import java.awt.Component;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import composant.buttons.PicturedButton;

/**
 *
 * @author Vivien
 */
public class CoupleStarCellRender extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CoupleStar> couples;
	private int columnEquipe1;
	private int columnEquipe2;

	/**
	 * Crée un <code>CoupleStarCellRender</code> permettant de définir le rendu des
	 * colonnes d'une table pour une table de séléction de données avec des étoiles.
	 * 
	 * @param couples       le couple permettant de stocker les informations d'une
	 *                      ligne
	 * @param columnEquipe1 la première colonne faisant apparaitre une étoile
	 * @param columnEquipe2 la seconde colonne faisant apparaitre une étoile
	 */
	public CoupleStarCellRender(List<CoupleStar> couples, int columnEquipe1, int columnEquipe2) {
		this.couples = couples;
		this.columnEquipe1 = columnEquipe1;
		this.columnEquipe2 = columnEquipe2;
	}

	@Override
	public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSeleted, boolean bln1, int row,
			int column) {
		PicturedButton star = null;
		if (column == this.columnEquipe1) {
			star = this.couples.get(row).getEquipe1();
		}
		if (column == this.columnEquipe2) {
			star = this.couples.get(row).getEquipe2();
		}
		return star;
	}
}