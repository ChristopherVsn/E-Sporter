package composant.table;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import ressources.CharteGraphique;

/**
 * Une table ayant pour design un fond bleu, pas de lignes pour les colonnes,
 * les en-tetes de colonnes de couleur Gold et le contenu de couleur Blanche.
 * 
 * @author Vivien
 *
 */
public class GoldAndWhiteTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Créer une nouvelle <code>GoldAndWhiteTable</code>
	 */
	public GoldAndWhiteTable() {
		initComponent();

	}

	/**
	 * Initie la table en définissant l'ensemble de ses paramètres.
	 */
	private void initComponent() {
		setBehavior();
		drawTable();
		setGrid();
		setText();
	}

	/**
	 * Change la couleur de la grille de table et rend invisible les lignes des
	 * colonnes
	 */
	private void setGrid() {
		setGridColor(CharteGraphique.GRAY_LIGHT);
		setShowVerticalLines(false);
	}

	/**
	 * Défini le comportement de la table : les cellules ne peuvent pas être
	 * séléctionnées, les lignes ont une hauteur fixe de 30px, les colonnes ne
	 * peuvent pas être modifiée (taille et position)
	 */
	private void setBehavior() {
		setFocusable(false);
		setRowSelectionAllowed(false);
		setRowHeight(30);
		getTableHeader().setResizingAllowed(false);
		getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * Définie l'UX des valeurs de la table : Les titres sont de couleur
	 * <code>Gold</code>, taille 16, Font <code>Time New Romans</code>. Le contenu
	 * est de couleur <code>White</code>, taille 16, Font <code>Calibri</code>
	 */
	private void setText() {
		setFont(CharteGraphique.TEXT);
		setForeground(CharteGraphique.WHITE);
		getTableHeader().setFont(CharteGraphique.TABLE_TITLE);
		getTableHeader().setForeground(CharteGraphique.GOLD);
	}

	/**
	 * Dessine la table : Couleur de fond, bord, opacité.
	 */
	private void drawTable() {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setBackground(CharteGraphique.BACKGROUND);
		setSelectionBackground(CharteGraphique.BACKGROUND);
		getTableHeader().setBackground(CharteGraphique.BACKGROUND);
		getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, CharteGraphique.GRAY_LIGHT));
		getTableHeader().setOpaque(false);
	}
}
