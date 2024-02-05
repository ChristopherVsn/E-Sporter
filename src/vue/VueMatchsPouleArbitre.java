package vue;

import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import composant.buttons.GoldUnderlinedButton;
import composant.header.Header;
import composant.header.User;
import composant.table.CoupleStar;
import composant.table.CoupleStarCellEditor;
import composant.table.CoupleStarCellRender;
import composant.table.EquipeMatch;
import composant.table.GoldAndWhiteTable;
import controleur.ControleurCloturePoule;
import modele.Match;
import modele.ModeleSelectionVainqueurMatch;
import modele.Tournoi;
import ressources.CharteGraphique;
import ressources.Pages;

public class VueMatchsPouleArbitre extends VueMain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tournoi turnament;
	private final Header header = new Header();
	private final JPanel panelBtnCloture = new JPanel();
	private final JPanel panelTable = new JPanel();
	private final JScrollPane scrollPaneTable = new JScrollPane();
	private final GoldUnderlinedButton btnCloture = new GoldUnderlinedButton("Cloturer la poule");
	private GoldAndWhiteTable tableMatchs;
	private DefaultTableModel modeleTableMatchs;
	private ModeleSelectionVainqueurMatch modele;
	private List<CoupleStar> cplStar = new LinkedList<>();
	private ControleurCloturePoule controleurCloturePoule;

	public VueMatchsPouleArbitre(Tournoi turnament) {
		this.turnament = turnament;
		this.modele = new ModeleSelectionVainqueurMatch(turnament);
		this.controleurCloturePoule = new ControleurCloturePoule(this);
		initComponent();
	}

	private void initComponent() {
		drawPanels();
		setHeader();
		setButtonCloture();
		setTable();
		addComponents();
		switch (this.modele.getPhase()) {
		case POULE:
			this.remplirTable(this.modele.getAll());
			break;
		case FINALE:
			this.setFinal(this.modele.getFinalistes());
			break;
		case CLOSED:
			this.changePage(Pages.IDENTIFICATION);
			break;
			default:
				break;
		}
	}

	private void drawPanels() {
		setBackground(CharteGraphique.BACKGROUND);
		setLayout(new BorderLayout(0, 0));
		this.panelTable.setLayout(new BorderLayout(0, 0));

		this.panelBtnCloture.setBorder(new EmptyBorder(0, 0, 50, 0));
		this.panelTable.setBorder(new EmptyBorder(50, 100, 20, 100));
		this.scrollPaneTable.setBorder(new EmptyBorder(0, 0, 0, 0));

		this.panelBtnCloture.setOpaque(false);
		this.panelTable.setOpaque(false);
		this.scrollPaneTable.setOpaque(false);
		this.scrollPaneTable.getViewport().setOpaque(false);
	}

	private void setHeader() {
		this.header.setUser(User.REFEREE);
		this.header.setTitle(this.turnament.getNom() + " - Matchs de poule");
	}

	private void setTable() {
		this.tableMatchs = new GoldAndWhiteTable() {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, true, false, true };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
	}

	private void setButtonCloture() {
		this.btnCloture.setEnabled(false);
		this.btnCloture.setActionCommand("cloturer");
		this.btnCloture.addActionListener(controleurCloturePoule);
	}

	private void addComponents() {
		add(this.header, BorderLayout.NORTH);
		add(this.panelBtnCloture, BorderLayout.SOUTH);
		add(this.panelTable, BorderLayout.CENTER);
		this.panelBtnCloture.add(this.btnCloture);
		this.panelTable.add(this.scrollPaneTable, BorderLayout.CENTER);
		this.scrollPaneTable.setViewportView(tableMatchs);
	}

	public Tournoi getTournoi() {
		return this.turnament;
	}

	public String getEquipeGagnantsMatchs(int idMatch) {
		return this.tableMatchs.getValueAt(idMatch, 1).toString();
	}

	public void remplirTable(List<Match> matchs) {
		this.modeleTableMatchs = new DefaultTableModel(new Object[][] {},
				new Object[] { "MATCH", "EQUIPE 1", "", "EQUIPE 2", "" });
		for (Match match : matchs) {
			this.modeleTableMatchs.addRow(new String[] { Integer.toString(match.getIdMatch()), match.getNomEquipe1(),
					"", match.getNomEquipe2(), "" });
			CoupleStar matchEnCours = new CoupleStar(match.getNomEquipe1(), match.getNomEquipe2());
			if (isWinnerDefined(match)) {
				matchEnCours.setWinner(getEquipeWinner(match));
			}
			this.cplStar.add(matchEnCours);
		}
		this.tableMatchs.setModel(modeleTableMatchs);

		CoupleStarCellRender renderer = new CoupleStarCellRender(cplStar, 2, 4);
		CoupleStarCellEditor editor = new CoupleStarCellEditor(cplStar, 2, 4, this);

		tableMatchs.getColumnModel().getColumn(2).setCellRenderer(renderer);
		tableMatchs.getColumnModel().getColumn(2).setCellEditor(editor);
		tableMatchs.getColumnModel().getColumn(4).setCellRenderer(renderer);
		tableMatchs.getColumnModel().getColumn(4).setCellEditor(editor);
	}

	private boolean isWinnerDefined(Match match) {
		return match.getEquipes().get(match.getNomEquipe1()) != 0 || match.getEquipes().get(match.getNomEquipe2()) != 0;
	}

	private EquipeMatch getEquipeWinner(Match match) {
		if (match.getEquipes().get(match.getNomEquipe1()) > match.getEquipes().get(match.getNomEquipe2())) {
			return EquipeMatch.EQUIPE_1;
		}
		return EquipeMatch.EQUIPE_2;
	}

	public String getWinnerMatch(int idMatch) {
		return this.cplStar.get(idMatch).getWinner();
	}

	public void viderTable() {
		this.cplStar.clear();
	}

	public boolean equipesGagnantesSelectionner() {
		for (int i = 0; i < this.cplStar.size(); i++) {
			if (this.cplStar.get(i).getWinner() == "indefini") {
				return false;
			}
		}
		return true;
	}

	public void enableCloture(boolean b) {
		this.btnCloture.setEnabled(b);
	}

	public void setFinal(Match match) {
		this.viderTable();
		List<Match> matchs = new LinkedList<>();
		matchs.add(match);
		this.cplStar.clear();
		this.enableCloture(false);
		this.cplStar.add(new CoupleStar(match.getNomEquipe1(), match.getNomEquipe2()));
		this.remplirTable(matchs);
		this.setTable();
		this.setTitle(this.turnament.getNom() + " - Phase Finale");
		this.btnCloture.setText("Cloturer le Tournoi");
		this.btnCloture.setActionCommand("finFinal");
		this.btnCloture.setEnabled(false);
		;
	}

	public int getCountRowTable() {
		return this.tableMatchs.getRowCount();
	}
}
