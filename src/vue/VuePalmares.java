package vue;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Calendar;
import java.util.List;

import java.util.LinkedList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import composant.buttons.GoldUnderlinedButton;
import composant.combobox.RoundedComboBox;
import composant.header.User;
import controleur.ControleurPalmares;
import modele.EquipeSaison;
import ressources.CharteGraphique;
import ui.ButtonTableType;
import ui.ListButtonCellEditor;
import ui.ListButtonCellRender;
import ui.ObjectDesigner;

public class VuePalmares extends VueMain {

	private JPanel panelBody;
	private JPanel panelFooter;
	private JTable tablePalmares;

	private static final int YEAR_START = 2018;
	private static final String[] EN_TETE = new String[] { "CLASSEMENT", "EQUIPE", "POINTS", "" };

	private RoundedComboBox comboBoxYear = new RoundedComboBox();
	private DefaultTableModel modelTablePalmares;
	private ControleurPalmares controleur;

	public VuePalmares() {
		this.controleur = new ControleurPalmares(this);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				double responsiveSizeWidth = VuePalmares.this.getWidth() * 0.1;
				double responsiveSizeHeight = VuePalmares.this.getHeight() * 0.05;
				VuePalmares.this.panelBody
						.setBorder(new EmptyBorder(50, (int) responsiveSizeWidth, 30, (int) responsiveSizeWidth));
				VuePalmares.this.panelFooter.setBorder(new EmptyBorder((int) responsiveSizeHeight, 0, 0, 0));
			}
		});

		this.setUser(User.ADMIN);
		this.setTitle("Palmares");
		modelTablePalmares = new DefaultTableModel(new String[][] {}, EN_TETE);

		panelBody = new JPanel();
		panelBody.setOpaque(false);
		panelBody.setBorder(new EmptyBorder(50, 0, 50, 0));
		add(panelBody, BorderLayout.CENTER);
		panelBody.setLayout(new BorderLayout(0, 0));

		panelFooter = new JPanel();
		panelFooter.setBorder(new EmptyBorder(20, 0, 0, 0));
		panelFooter.setOpaque(false);
		panelBody.add(panelFooter, BorderLayout.SOUTH);
		panelFooter.setLayout(new BorderLayout(0, 0));

		GoldUnderlinedButton btnAccueil = new GoldUnderlinedButton("Accueil");
		btnAccueil.addActionListener(controleur);
		btnAccueil.setActionCommand("retour");
		panelFooter.add(btnAccueil, BorderLayout.WEST);

		JPanel panelBodyTable = new JPanel();
		panelBodyTable.setBorder(new EmptyBorder(30, 0, 0, 0));
		panelBodyTable.setBackground(CharteGraphique.BACKGROUND);
		panelBodyTable.setOpaque(false);
		panelBody.add(panelBodyTable, BorderLayout.CENTER);
		panelBodyTable.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPanePalmares = new JScrollPane();
		scrollPanePalmares.setOpaque(false);
		scrollPanePalmares.setBorder(new EmptyBorder(0, 0, 0, 0));
		// scrollPanePalmares.getColumnHeader().setBackground(CharteGraphique.BACKGROUND);
		scrollPanePalmares.getViewport().setBackground(CharteGraphique.BACKGROUND);
		panelBodyTable.add(scrollPanePalmares, BorderLayout.CENTER);

		tablePalmares = new JTable();
		tablePalmares.setEnabled(true);
		tablePalmares.setShowVerticalLines(false);
		tablePalmares.setGridColor(CharteGraphique.GRAY_LIGHT);
		tablePalmares.setBackground(CharteGraphique.BACKGROUND);
		tablePalmares.setForeground(CharteGraphique.WHITE);
		tablePalmares.setFont(CharteGraphique.TEXT);
		tablePalmares.setBorder(null);
		tablePalmares.setRowHeight(30);

		tablePalmares.getTableHeader().setFont(CharteGraphique.TABLE_TITLE);
		tablePalmares.getTableHeader().setOpaque(false);
		tablePalmares.getTableHeader().setForeground(CharteGraphique.GOLD);
		tablePalmares.getTableHeader().setBackground(CharteGraphique.BACKGROUND);
		tablePalmares.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, CharteGraphique.GRAY_LIGHT));

		tablePalmares.setModel(modelTablePalmares);
		scrollPanePalmares.setViewportView(tablePalmares);
		tablePalmares.setBackground(CharteGraphique.BACKGROUND);

		JPanel panelBodySaison = new JPanel();
		panelBodySaison.setBorder(null);
		panelBodySaison.setBackground(CharteGraphique.BACKGROUND);
		panelBody.add(panelBodySaison, BorderLayout.NORTH);
		panelBodySaison.setLayout(new BorderLayout(0, 0));

		comboBoxYear.setModel(makeModelSeason());
		panelBodySaison.add(comboBoxYear, BorderLayout.WEST);
		comboBoxYear.addItemListener(controleur);
		initialiserTableau();
	}

	private static DefaultComboBoxModel<String> makeModelSeason() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		Integer anneeCourante = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = anneeCourante; i > YEAR_START; i--) {
			model.addElement(Integer.toString(i));
		}
		return model;
	}

	public void updateModelTablePalmares(List<EquipeSaison> equipes) {
		this.modelTablePalmares.setRowCount(0);
		for (EquipeSaison e : equipes) {
			this.modelTablePalmares
					.addRow(new String[] { String.valueOf(e.getWr()), e.getNom(), String.valueOf(e.getScore()), null });
		}

		// list parametrée
		List<ButtonTableType> btns = new LinkedList<>();

		// mes boutons
		ButtonTableType detail = new ButtonTableType(new ImageIcon(getClass().getResource("/pictures/icon/info.png")),
				15, controleur);
		detail.setActionCommand("detail");
		btns.add(detail);
		ObjectDesigner.setTableUI(tablePalmares);

		tablePalmares.getColumn("").setCellRenderer(new ListButtonCellRender(btns));
		tablePalmares.getColumn("").setCellEditor(new ListButtonCellEditor(btns));

		this.tablePalmares.setModel(modelTablePalmares);
	}

	public int getSaison() {
		return Integer.valueOf(comboBoxYear.getSelectedItem().toString());
	}

	public void initialiserTableau() {
		this.controleur.initialiseTableau();
	}

	// private String[] getAnnees() {
	// Calendar cal = Calendar.getInstance();
	// int anneeEnCours = cal.get(Calendar.YEAR);
	// int nbAnnees = anneeEnCours - 2019;
	// String[] s = new String[nbAnnees + 1];
	// for (int i = 0; i <= nbAnnees; i++) {
	// s[i] = String.valueOf(2019 + nbAnnees - i);
	// }
	// return s;
	// }
	public int getRowSelectedLine() {
		return tablePalmares.getSelectedRow();
	}

	public String getRowValue() {
		String s = "";
		s += modelTablePalmares.getValueAt(tablePalmares.getSelectedRow(), 1).toString();
		return s;
	}
}
