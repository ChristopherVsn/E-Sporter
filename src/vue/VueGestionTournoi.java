package vue;

import javax.swing.JPanel;

import controleur.ControleurGestionTournoi;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import modele.Equipe;
import modele.EquipeSaison;
import modele.Tournoi;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class VueGestionTournoi extends JPanel {
	private ControleurGestionTournoi controleur;
	private Tournoi tournoi;
	private DefaultComboBoxModel<String> comboBoxModelEquipes;
	private DefaultTableModel tableModelEquipesParticipantes;
	private JComboBox<String> comboBoxEquipes;
	private JTable tableEquipesParticipantes;
	private JButton supprimerEquipe;

	/**
	 * Create the panel.
	 *
	 * @throws Exception
	 */
	public VueGestionTournoi(Tournoi t) throws Exception {
		this.tournoi = t;
		this.controleur = new ControleurGestionTournoi(this);
		JLabel lblNomTournoi = new JLabel("Nom : " + this.tournoi.getNom());
		add(lblNomTournoi);
		comboBoxEquipes = new JComboBox<>();
		add(comboBoxEquipes);
		tableEquipesParticipantes = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return column != 0;
			}
		};
		comboBoxEquipes.addActionListener(this.controleur);
		this.tableModelEquipesParticipantes = new DefaultTableModel(new Object[][] {},
				new String[] { "EQUIPE" });

		tableEquipesParticipantes.setModel(tableModelEquipesParticipantes);
		tableEquipesParticipantes.addMouseListener(this.controleur);
		JButton ajouterEquipe = new JButton(" + ");
		ajouterEquipe.setActionCommand("addEquipe");
		ajouterEquipe.addActionListener(this.controleur);

		add(ajouterEquipe);

		supprimerEquipe = new JButton(" - ");
		supprimerEquipe.setActionCommand("deleteEquipe");
		add(supprimerEquipe);
		add(tableEquipesParticipantes);
		supprimerEquipe.addActionListener(this.controleur);
		this.remplirListeEquipesParticipantes(this.controleur.getEquipesParticipantes());
		this.remplirComboBoxEquipes(this.controleur.getEquipes());
	}

	public void remplirComboBoxEquipes(List<String> equipes) {
		this.comboBoxModelEquipes = new DefaultComboBoxModel<>();
		this.comboBoxModelEquipes.addElement("Sélectionnez une équipe");
		for (String equipe : equipes) {
			this.comboBoxModelEquipes.addElement(equipe);
		}
		this.comboBoxEquipes.setModel(comboBoxModelEquipes);
	}

	public void remplirListeEquipesParticipantes(List<String> equipes) {
		this.tableModelEquipesParticipantes.setRowCount(0);
		for (String equipe : equipes) {
			Object[] ligne = { equipe };
			tableModelEquipesParticipantes.addRow(ligne);
		}
	}

	public Tournoi getTournoi() {
		return this.tournoi;
	}

	public JComboBox<String> getComboBoxEquipes() {
		return this.comboBoxEquipes;
	}

	public JTable getTableEquipesParticipantes() {
		return this.tableEquipesParticipantes;
	}

	public boolean isRowSelected() {
		return this.tableEquipesParticipantes.getSelectedRow() >= 0
				&& this.tableEquipesParticipantes.getSelectedColumn() >= 0;
	}
	public String getRowSelected(){
		return (String) this.tableEquipesParticipantes.getValueAt(this.tableEquipesParticipantes.getSelectedRow(), this.tableEquipesParticipantes.getSelectedColumn());
	}
}
