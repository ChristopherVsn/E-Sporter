package vue;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import composant.buttons.GoldUnderlinedButton;
import composant.header.User;
import controleur.ControleurListeTournoi;
import modele.Tournoi;
import ressources.CharteGraphique;
import ressources.Images;
import ui.ButtonTableType;
import ui.ListButtonCellEditor;
import ui.ListButtonCellRender;
import ui.ObjectDesigner;

public class VueListeTournois extends VueMain {

	private JPanel panelBody;
	private JPanel panelFooter;
	private JTable tableTournoi;
	private DefaultTableModel modelTableTournois;
	private ControleurListeTournoi controleur;

	public VueListeTournois() {
		this.controleur = new ControleurListeTournoi(this);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				double responsiveSizeWidth = VueListeTournois.this.getWidth() * 0.1;
				double responsiveSizeHeight = VueListeTournois.this.getHeight() * 0.05;
				VueListeTournois.this.panelBody
						.setBorder(new EmptyBorder(50, (int) responsiveSizeWidth, 30, (int) responsiveSizeWidth));
				VueListeTournois.this.panelFooter.setBorder(new EmptyBorder((int) responsiveSizeHeight, 0, 0, 0));
			}
		});

		this.setUser(User.ADMIN);
		this.setTitle("Tournoi - Tournois créés");

		panelBody = new JPanel();
		panelBody.setBorder(new EmptyBorder(50, 0, 50, 0));
		panelBody.setOpaque(false);
		add(panelBody, BorderLayout.CENTER);
		panelBody.setLayout(new BorderLayout(0, 0));

		panelFooter = new JPanel();
		panelFooter.setBorder(new EmptyBorder(20, 0, 0, 0));
		panelFooter.setOpaque(false);
		panelBody.add(panelFooter, BorderLayout.SOUTH);
		panelFooter.setLayout(new BorderLayout(0, 0));

		GoldUnderlinedButton btnRetour = new GoldUnderlinedButton("Retour");
		btnRetour.addActionListener(controleur);
		btnRetour.setActionCommand("Retour");
		panelFooter.add(btnRetour, BorderLayout.WEST);

		GoldUnderlinedButton btnEnregistrerTournoi = new GoldUnderlinedButton("Enregistrer un nouveau tournoi");
		btnEnregistrerTournoi.addActionListener(controleur);
		btnEnregistrerTournoi.setActionCommand("CreationTournoi");
		panelFooter.add(btnEnregistrerTournoi, BorderLayout.EAST);

		JPanel panelBodyTable = new JPanel();
		panelBodyTable.setBorder(new EmptyBorder(30, 0, 0, 0));
		panelBodyTable.setBackground(CharteGraphique.BACKGROUND);
		panelBodyTable.setOpaque(false);
		panelBody.add(panelBodyTable, BorderLayout.CENTER);
		panelBodyTable.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPanePalmares = new JScrollPane();
		scrollPanePalmares.setOpaque(false);
		scrollPanePalmares.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPanePalmares.getViewport().setBackground(CharteGraphique.BACKGROUND);
		panelBodyTable.add(scrollPanePalmares, BorderLayout.CENTER);

		tableTournoi = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 3;
			}
		};

		// controleur
		this.controleur = new ControleurListeTournoi(this);

		scrollPanePalmares.setViewportView(tableTournoi);
		tableTournoi.setBackground(CharteGraphique.BACKGROUND);

		this.remplirTable(this.controleur.getAll());
	}

	/**
	 * @param tournois représente une liste de tournoi
	 *                 On y redefini le modele pour éviter des erreurs de type
	 *                 indexOutOfBounds
	 *                 On utilisera le controleur puis le modele pour obtenir la
	 *                 liste
	 */

	public void remplirTable(List<Tournoi> tournois) {
		modelTableTournois = new DefaultTableModel(
				new String[][] {}, new String[] { "TOURNOI", "LIGUE", "DATES", "" });
		tableTournoi.setModel(modelTableTournois);
		for (

		Tournoi tournoi : tournois) {
			this.modelTableTournois.addRow(new String[] { tournoi.getNom(), tournoi.getLigue().toString(),
					tournoi.getDateDebut() + " - " + tournoi.getDateFin() });
		}
		tableTournoi.setSelectionBackground(CharteGraphique.BACKGROUND);
		tableTournoi.setRowSelectionAllowed(false);

		ObjectDesigner.setTableUI(tableTournoi);
		// list parametrée
		List<ButtonTableType> btns = new LinkedList<>();

		// mes boutons
		ButtonTableType delete = new ButtonTableType(Images.TRASH,
				15, controleur);
		ButtonTableType edit = new ButtonTableType(Images.EDIT, 15,
				controleur);
		delete.setActionCommand("delete");
		edit.setActionCommand("edit");
		btns.add(edit);
		btns.add(delete);

		// passage en parametres
		tableTournoi.getColumn("").setCellRenderer(new ListButtonCellRender(btns));
		tableTournoi.getColumn("").setCellEditor(new ListButtonCellEditor(btns));
	}
}
