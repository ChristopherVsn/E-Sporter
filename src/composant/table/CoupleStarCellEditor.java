package composant.table;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import composant.buttons.PicturedButton;
import vue.VueMatchsPouleArbitre;

/**
 *
 * @author Vivien
 */

public class CoupleStarCellEditor extends DefaultCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CoupleStar> couples;
	private int columnEquipe1;
	private int columnEquipe2;
	private VueMatchsPouleArbitre vue;

	/**
	 * Crée un <code>CoupleStarCellRender</code> permettant de définir le rendu des
	 * colonnes d'une table lorsque l'on cherche à éditer le contenu d'une colonne pour une table de séléction de données avec des étoiles.
	 * 
	 * @param couples       le couple permettant de stocker les informations d'une
	 *                      ligne
	 * @param columnEquipe1 la première colonne faisant apparaitre une étoile pouvant être édité
	 * @param columnEquipe2 la seconde colonne faisant apparaitre une étoile pouvant être édité
	 */
	public CoupleStarCellEditor(List<CoupleStar> couples, int columnEquipe1, int columnEquipe2, VueMatchsPouleArbitre vue) {
		super(new JCheckBox());
		this.couples = couples;
		this.columnEquipe1 = columnEquipe1;
		this.columnEquipe2 = columnEquipe2;
		this.vue = vue;
	}

	@Override
	public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
		PicturedButton star = null;
		if (column == this.columnEquipe1) {
			star = this.couples.get(row).getEquipe1();
		} else if (column == this.columnEquipe2) {
			star = this.couples.get(row).getEquipe2();
		}
		ActionListener[] tmp = star.getActionListeners();
		if (tmp.length != 0) {
			ActionListener clear = tmp[0];
			star.removeActionListener(clear);
		}
		String firstColumnValue = jtable.getValueAt(row, 0).toString();
		Integer idMatch = Integer.valueOf(firstColumnValue);
		star.addActionListener(new CoupleStarActionListener(idMatch, this.couples.get(row), jtable, this.vue));
		return star;
	}
}