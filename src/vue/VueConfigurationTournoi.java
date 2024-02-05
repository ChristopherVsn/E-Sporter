package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import composant.buttons.GoldUnderlinedButton;
import composant.combobox.RoundedComboBox;
import composant.header.User;
import controleur.ControleurConfigurationTournoi;
import modele.Tournoi;
import ressources.CharteGraphique;
import ui.ButtonTableType;
import ui.ListButtonCellEditor;
import ui.ListButtonCellRender;
import ui.ObjectDesigner;


public class VueConfigurationTournoi extends VueMain {
	private ControleurConfigurationTournoi controleur;
	private Tournoi tournoi;

	private JPanel panelBody;
	private JPanel panelFooter;
	private JTable tableArbitres;
	private JTable tableEquipes;

	private DefaultTableModel tableModelArbitresParticipants;
	private DefaultTableModel tableModelEquipesParticipantes;

	private DefaultComboBoxModel<String> comboBoxModelArbitres;
	private DefaultComboBoxModel<String> comboBoxModelEquipes;
	private RoundedComboBox comboBoxArbitres;
	private RoundedComboBox comboBoxEquipes;
	private List<ButtonTableType> btns;
	private List<ButtonTableType> btnsEq;
	private GoldUnderlinedButton btnLancer;

	public VueConfigurationTournoi(Tournoi t) {
		this.tournoi = t;
		this.controleur = new ControleurConfigurationTournoi(this);
		this.tableModelArbitresParticipants = new DefaultTableModel(new Object[][] {},
				new String[] { "Nom", "Prénom", "" });

		this.tableModelEquipesParticipantes = new DefaultTableModel(new Object[][] {}, new String[] { "Equipes", "" });

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				double responsiveSizeWidth = VueConfigurationTournoi.this.getWidth() * 0.1;
				double responsiveSizeHeight = VueConfigurationTournoi.this.getHeight() * 0.05;
				VueConfigurationTournoi.this.panelBody
						.setBorder(new EmptyBorder(50, (int) responsiveSizeWidth, 30, (int) responsiveSizeWidth));
				VueConfigurationTournoi.this.panelFooter
						.setBorder(new EmptyBorder((int) responsiveSizeHeight, 0, 0, 0));
			}
		});
		String nomTournoi = t.getNom();
		this.setUser(User.ADMIN);
		this.setTitle("Gestion de tournoi - " + nomTournoi);

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
		panelFooter.add(btnRetour, BorderLayout.WEST);
		btnRetour.addActionListener(controleur);

		btnLancer = new GoldUnderlinedButton("Lancer le tournoi");
		btnLancer.addActionListener(controleur);
		btnLancer.setActionCommand("lancerTournoi");
		btnLancer.setEnabled(false);
		panelFooter.add(btnLancer, BorderLayout.EAST);

		JPanel panelArbitres = new JPanel();
		panelArbitres.setBackground(CharteGraphique.BACKGROUND);
		panelArbitres.setOpaque(false);
		panelArbitres.setPreferredSize(new Dimension(300, 10));
		panelBody.add(panelArbitres, BorderLayout.WEST);
		panelArbitres.setLayout(new BorderLayout(0, 0));

		JPanel panelArbitreSelection = new JPanel();
		panelArbitreSelection.setBorder(new EmptyBorder(0, 0, 20, 0));
		panelArbitreSelection.setBackground(CharteGraphique.BACKGROUND);
		panelArbitreSelection.setOpaque(false);
		panelArbitres.add(panelArbitreSelection, BorderLayout.NORTH);
		panelArbitreSelection.setLayout(new BorderLayout(0, 0));

		comboBoxArbitres = new RoundedComboBox();
		comboBoxArbitres.addActionListener(this.controleur);
		panelArbitreSelection.add(comboBoxArbitres, BorderLayout.WEST);

		ImageIcon add = new ImageIcon(getClass().getResource("/pictures/icon/add.png"));
//		PicturedButton btnAjouterArbitre = new PicturedButton(add);
//		btnAjouterArbitre.resizeIcon(35);
//		btnAjouterArbitre.setActionCommand("addArbitre");
//		btnAjouterArbitre.addActionListener(this.controleur);
//		panelArbitreSelection.add(btnAjouterArbitre, BorderLayout.EAST);

		JScrollPane scrollPaneArbitre = new JScrollPane();
		panelArbitres.add(scrollPaneArbitre, BorderLayout.CENTER);

		JPanel panelEquipes = new JPanel();
		panelEquipes.setOpaque(false);
		panelEquipes.setPreferredSize(new Dimension(300, 10));
		panelBody.add(panelEquipes, BorderLayout.EAST);
		panelEquipes.setLayout(new BorderLayout(0, 0));

		JPanel panelEquipesSelection = new JPanel();
		panelEquipesSelection.setBorder(new EmptyBorder(0, 0, 20, 0));
		panelEquipesSelection.setBackground(CharteGraphique.BACKGROUND);
		panelEquipesSelection.setOpaque(false);
		panelEquipes.add(panelEquipesSelection, BorderLayout.NORTH);
		panelEquipesSelection.setLayout(new BorderLayout(0, 0));

		comboBoxEquipes = new RoundedComboBox();
		comboBoxEquipes.addActionListener(this.controleur);
		panelEquipesSelection.add(comboBoxEquipes, BorderLayout.WEST);

//		PicturedButton btnAjouterEquipe = new PicturedButton(add);
//		btnAjouterEquipe.resizeIcon(35);
//		btnAjouterEquipe.setActionCommand("addEquipe");
//		btnAjouterEquipe.addActionListener(controleur);
//		panelEquipesSelection.add(btnAjouterEquipe, BorderLayout.EAST);
//		btnAjouterEquipe.setMargin(new Insets(0, 0, 0, 0));

		JScrollPane scrollPaneEquipes = new JScrollPane();
		panelEquipes.add(scrollPaneEquipes, BorderLayout.CENTER);

		tableArbitres = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return ((column != 0) && (column != 1));
			}
		};

		tableEquipes = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0;
			}
		};

		tableArbitres.setRowSelectionAllowed(false);
		tableArbitres.setBackground(new Color(255, 255, 255));
		tableArbitres.setRowHeight(30);
		tableArbitres.setModel(this.tableModelArbitresParticipants);
		tableArbitres.setSelectionBackground(CharteGraphique.BACKGROUND);
		tableArbitres.getTableHeader().setReorderingAllowed(false);

		tableArbitres.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableArbitres.getColumnModel().getColumn(1).setMinWidth(150);
		tableArbitres.getColumnModel().getColumn(1).setMaxWidth(150);


		tableEquipes.setRowSelectionAllowed(false);
		tableEquipes.setBackground(new Color(255, 255, 255));
		tableEquipes.setRowHeight(30);
		tableEquipes.setModel(tableModelEquipesParticipantes);
		tableEquipes.setSelectionBackground(CharteGraphique.BACKGROUND);
		tableEquipes.getTableHeader().setReorderingAllowed(false);

		tableEquipes.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableEquipes.getColumnModel().getColumn(1).setMinWidth(150);
		tableEquipes.getColumnModel().getColumn(1).setMaxWidth(150);

		// boutons supprimer
		this.btns = new LinkedList<>();
		this.btnsEq = new LinkedList<>();

		ButtonTableType btnDeleteArbitre = new ButtonTableType(
				new ImageIcon(getClass().getResource("/pictures/icon/trash.png")), 15, controleur);
		btnDeleteArbitre.setActionCommand("deleteArbitre");
		btns.add(btnDeleteArbitre);

		ButtonTableType btnDeleteEquipe = new ButtonTableType(
				new ImageIcon(getClass().getResource("/pictures/icon/trash.png")), 15, controleur);
		btnDeleteEquipe.setActionCommand("deleteEquipe");
		btnsEq.add(btnDeleteEquipe);

		tableArbitres.getColumn("").setCellRenderer(new ListButtonCellRender(btns));
		tableArbitres.getColumn("").setCellEditor(new ListButtonCellEditor(btns));

		tableEquipes.getColumn("").setCellRenderer(new ListButtonCellRender(btnsEq));
		tableEquipes.getColumn("").setCellEditor(new ListButtonCellEditor(btnsEq));

		scrollPaneArbitre.setViewportView(tableArbitres);
		scrollPaneEquipes.setViewportView(tableEquipes);

		ObjectDesigner.setTableUI(tableArbitres, tableEquipes);
		ObjectDesigner.setScrollPaneUI(scrollPaneArbitre, scrollPaneEquipes);
		this.controleur.majDonnees();
	}

	public RoundedComboBox getComboBoxArbitres() {
		return this.comboBoxArbitres;
	}

	public JComboBox<String> getComboBoxEquipes() {
		return this.comboBoxEquipes;
	}

	public void remplirComboBoxArbitres(List<String> arbitres) {
		this.comboBoxModelArbitres = new DefaultComboBoxModel<>();
		this.comboBoxModelArbitres.addElement("Sélectionnez un arbitre");
		for (String arb : arbitres) {
			this.comboBoxModelArbitres.addElement(arb);
		}
		comboBoxArbitres.setModel(comboBoxModelArbitres);
	}

	public void remplirComboBoxEquipes(List<String> equipes) {
		this.comboBoxModelEquipes = new DefaultComboBoxModel<>();
		this.comboBoxModelEquipes.addElement("Sélectionnez une équipe");
		for (String equipe : equipes) {
			this.comboBoxModelEquipes.addElement(equipe);
		}
		this.comboBoxEquipes.setModel(comboBoxModelEquipes);
	}

	public void remplirListeArbitresTournoi(List<String> arbitres) {
		this.tableModelArbitresParticipants.setRowCount(0);
		this.tableModelArbitresParticipants.setColumnCount(0);
		this.tableModelArbitresParticipants.addColumn("Nom");
		this.tableModelArbitresParticipants.addColumn("Prénom");
		this.tableModelArbitresParticipants.addColumn("");
		tableArbitres.getColumn("").setCellRenderer(new ListButtonCellRender(btns));
		tableArbitres.getColumn("").setCellEditor(new ListButtonCellEditor(btns));
		for (String a : arbitres) {
			this.tableModelArbitresParticipants.addRow(a.split(" "));
		}
	}

	public void remplirListeEquipesParticipantes(List<String> equipes) {
		this.tableModelEquipesParticipantes.setRowCount(0);
		this.tableModelEquipesParticipantes.setColumnCount(0);
		this.tableModelEquipesParticipantes.addColumn("Equipes");
		this.tableModelEquipesParticipantes.addColumn("");
		tableEquipes.getColumn("").setCellRenderer(new ListButtonCellRender(btnsEq));
		tableEquipes.getColumn("").setCellEditor(new ListButtonCellEditor(btnsEq));
		for (String equipe : equipes) {
			Object[] ligne = { equipe };
			tableModelEquipesParticipantes.addRow(ligne);
		}
	}

	public void activeLancement() {
		this.btnLancer.setEnabled(true);
	}

	public boolean isArbitreSelect() {
		return (!this.comboBoxArbitres.getSelectedItem().equals("Sélectionnez un arbitre"));
	}

	public boolean isEquipeSelect() {
		return (!this.comboBoxEquipes.getSelectedItem().equals("Sélectionnez une équipe"));
	}

	/**
	 * @return le tournoi donné à la vue
	 */
	public Tournoi getTournoi() {
		return this.tournoi;
	}

	public boolean estBoutonRetour(ActionEvent e) {
		return e.getActionCommand() == "Retour";
	}
}
