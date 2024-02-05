package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import composant.buttons.GoldUnderlinedButton;
import composant.header.User;
import controleur.ControleurSuppressionEquipe;
import modele.EquipeSaison;
import ressources.CharteGraphique;
import ui.ButtonTableType;
//import ui.HeaderDeleteRenderer;
import ui.ListButtonCellEditor;
import ui.ListButtonCellRender;
import ui.ObjectDesigner;

public class VueSuppressionEquipe extends VueMain {
	private JTable table;
	private JButton button = new JButton();
	private DefaultTableModel modeleTable;

	private JPanel panelBody;
	private JPanel panelFooter;
	private ControleurSuppressionEquipe controleur;

	/**
	 * Create the panel.
	 * 
	 * @throws Exception
	 */
	public VueSuppressionEquipe() {
		this.controleur = new ControleurSuppressionEquipe(this);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				double responsiveSizeWidth = VueSuppressionEquipe.this.getWidth() * 0.1;
				double responsiveSizeHeight = VueSuppressionEquipe.this.getHeight() * 0.05;
				VueSuppressionEquipe.this.panelBody
						.setBorder(new EmptyBorder(50, (int) responsiveSizeWidth, 30, (int) responsiveSizeWidth));
				VueSuppressionEquipe.this.panelFooter.setBorder(new EmptyBorder((int) responsiveSizeHeight, 0, 0, 0));
				// System.out.println("large : " + VueSuppressionEquipe.this.getWidth() + " | h
				// : "
				// + VueSuppressionEquipe.this.getHeight());
			}
		});

		this.setHeader();

		button.addActionListener(e -> JOptionPane.showMessageDialog(null, "Voulez-vous supprimer l'équipe  ?"));
		modeleTable = new DefaultTableModel(new Object[][] {},
				new String[] { "EQUIPE", "SUPPRIMER" });

		panelBody = new JPanel();
		panelBody.setOpaque(false);
		panelBody.setBorder(new EmptyBorder(50, 0, 50, 0));
		add(panelBody, BorderLayout.CENTER);
		panelBody.setLayout(new BorderLayout(0, 0));

		panelFooter = new JPanel();
		panelFooter.setOpaque(false);
		panelBody.add(panelFooter, BorderLayout.SOUTH);
		panelFooter.setLayout(new BorderLayout(0, 0));

		GoldUnderlinedButton btnRetour = new GoldUnderlinedButton("Retour");
		btnRetour.setActionCommand("Retour");
		btnRetour.addActionListener(controleur);

		panelFooter.add(btnRetour, BorderLayout.WEST);

		GoldUnderlinedButton btnEnregistrer = new GoldUnderlinedButton("Enregistrer une équipe");
		btnEnregistrer.setActionCommand("ajout");
		btnEnregistrer.addActionListener(controleur);

		panelFooter.add(btnEnregistrer, BorderLayout.EAST);

		JScrollPane scrollPane = new JScrollPane();
		ObjectDesigner.setScrollPaneUI(scrollPane);
		panelBody.add(scrollPane, BorderLayout.CENTER);

		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0;
			}
		};

		this.remplirTable(this.controleur.getAll());

		scrollPane.setViewportView(table);
	}

	private void setHeader() {
		this.setUser(User.ADMIN);
		LocalDate current_date = LocalDate.now();
		int year = current_date.getYear();
		this.setTitle("Equipes - Saison " + year);
	}

	public int getRowSelectedLine() {
		return table.getSelectedRow();
	}

	public String getRowValue() {
		String s = "";
		s += modeleTable.getValueAt(table.getSelectedRow(), 0).toString();
		return s;
	}

	public void remplirTable(List<EquipeSaison> equipeSaisons) {
		DefaultTableModel modeleTable = new DefaultTableModel(new Object[][] {},
				new String[] { "EQUIPE", "" });
		table.setModel(modeleTable);
		for (EquipeSaison equipeSaison : equipeSaisons) {
			modeleTable.addRow(new Object[] { equipeSaison.getNom(), null });
		}
		table.setRowSelectionAllowed(false);
		table.setBackground(new Color(0, 0, 128));
		table.setRowHeight(30);
		table.setModel(modeleTable);
		table.setSelectionBackground(CharteGraphique.BACKGROUND);
		table.getTableHeader().setReorderingAllowed(false);

		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setMinWidth(150);
		table.getColumnModel().getColumn(1).setMaxWidth(150);

		// controleur

		// list parametrée
		List<ButtonTableType> btns = new LinkedList<>();

		// mes boutons
		ButtonTableType detail = new ButtonTableType(new ImageIcon(getClass().getResource("/pictures/icon/info.png")),
				15, controleur);
		detail.setActionCommand("detail");

		ButtonTableType delete = new ButtonTableType(new ImageIcon(getClass().getResource("/pictures/icon/trash.png")),
				15, controleur);
		delete.setActionCommand("delete");
		btns.add(detail);
		btns.add(delete);

		// pasage en parametres
		table.getColumn("").setCellRenderer(new ListButtonCellRender(btns));
		table.getColumn("").setCellEditor(new ListButtonCellEditor(btns));
		//table.getColumn("SUPPRIMER").setHeaderRenderer(new HeaderDeleteRenderer());
		ObjectDesigner.setTableUI(table);

	}
}
