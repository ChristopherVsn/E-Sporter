package vue;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import composant.buttons.BorderedTransparentButton;
import composant.buttons.GoldUnderlinedButton;
import composant.buttons.PicturedButton;
import composant.header.User;

import composant.table.GoldAndWhiteTable;
import controleur.ControleurTournoiLance;
import modele.EquipeTournoi;
import modele.Phase;

import modele.Tournoi;
import ressources.CharteGraphique;

public class VueTournoiLance extends VueMain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ControleurTournoiLance controleur;

	private final JPanel panelBody = new JPanel();
	private final JPanel panelTable = new JPanel();
	private final JPanel panelFooter = new JPanel();
	private final JPanel panelTop = new JPanel();
	private final JPanel panelBot = new JPanel();
	private final JScrollPane scrollPaneTable = new JScrollPane();
	private final JLabel lblPhase = new JLabel();
	private final JLabel lblFinale = new JLabel();

	private PicturedButton btnInformation;
	private GoldUnderlinedButton btnAccuei;
	private BorderedTransparentButton btnSupprimerTournoi;
	private GoldAndWhiteTable tableMatchs;
	private DefaultTableModel modeleTableMatchs;

	private Tournoi tournoi;

	public VueTournoiLance(Tournoi turnament) {
		this.setUser(User.ADMIN);
		this.setTitle("Gestion de tournoi - " + turnament.getNom());
		this.tournoi = turnament;
		this.controleur = new ControleurTournoiLance(this);
		this.initComponent();
	}

	private void initComponent() {
		this.drawPanels();
		this.drawButtons();
		this.setActions();
		this.drawLabel();
		this.setTable();
		this.remplirTable(this.controleur.getEquipesTournoi());
		this.addComponents();
	}

	private void setActions() {
		this.btnInformation.setActionCommand("information");
		this.btnAccuei.setActionCommand("accueil");
		this.btnSupprimerTournoi.setActionCommand("supprimer");
		this.btnInformation.addActionListener(this.controleur);
		this.btnAccuei.addActionListener(this.controleur);
		this.btnSupprimerTournoi.addActionListener(this.controleur);
	}

	private void drawPanels() {
		this.panelBody.setBorder(new EmptyBorder(0, 100, 0, 100));
		this.panelTable.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.panelFooter.setBorder(new EmptyBorder(0, 0, 50, 0));
		this.panelTop.setBorder(new EmptyBorder(30, 0, 30, 0));
		this.panelBot.setBorder(new EmptyBorder(30, 0, 30, 0));
		this.scrollPaneTable.setBorder(new EmptyBorder(0, 0, 0, 0));

		this.panelBody.setOpaque(false);
		this.panelTable.setOpaque(false);
		this.panelFooter.setOpaque(false);
		this.panelTop.setOpaque(false);
		this.panelBot.setOpaque(false);
		this.scrollPaneTable.setOpaque(false);
		this.scrollPaneTable.getViewport().setOpaque(false);

		this.panelBody.setLayout(new BorderLayout());
		this.panelTable.setLayout(new BorderLayout());
		this.panelFooter.setLayout(new BorderLayout());
		this.panelTop.setLayout(new BorderLayout());
		this.panelBot.setLayout(new BorderLayout());
	}

	private void addComponents() {
		add(this.panelBody, BorderLayout.CENTER);
		this.panelBody.add(this.panelFooter, BorderLayout.SOUTH);
		this.panelBody.add(this.panelTop, BorderLayout.NORTH);
		this.panelBody.add(this.panelTable, BorderLayout.CENTER);
		this.panelTable.add(this.scrollPaneTable, BorderLayout.CENTER);
		this.panelTable.add(this.panelBot, BorderLayout.SOUTH);
		this.panelTop.add(this.lblPhase, BorderLayout.WEST);
		this.panelTop.add(this.btnInformation, BorderLayout.EAST);
		this.panelBot.add(this.lblFinale, BorderLayout.WEST);
		this.panelFooter.add(this.btnAccuei, BorderLayout.WEST);
		this.panelFooter.add(this.btnSupprimerTournoi, BorderLayout.EAST);
		this.scrollPaneTable.setViewportView(tableMatchs);
	}

	private void drawButtons() {
		ImageIcon information = new ImageIcon(getClass().getResource("/pictures/icon/info.png"));
		this.btnInformation = new PicturedButton(information);
		this.btnInformation.resizeIcon(30);
		this.btnAccuei = new GoldUnderlinedButton("Retour");
		this.btnSupprimerTournoi = new BorderedTransparentButton("Suppression du tournoi");
	}

	private void drawLabel() {
		String textLabel = "Phase en cours : " + this.controleur.getPhaseEnCours().toString();
		String textFinal = "Finale : ";
		if (this.controleur.getPhaseEnCours() == Phase.POULE) {
			textFinal += "En attente";
		} else {
			textFinal += this.controleur.getFinale();
		}

		lblPhase.setText(textLabel);
		lblFinale.setText(textFinal);

		lblPhase.setForeground(CharteGraphique.WHITE);
		lblFinale.setForeground(CharteGraphique.WHITE);

		lblPhase.setFont(CharteGraphique.ITALIC_TITLE);
		lblFinale.setFont(CharteGraphique.BOLD_TITLE);
	}

	private void setTable() {
		this.tableMatchs = new GoldAndWhiteTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}

	public void remplirTable(List<EquipeTournoi> equipes) {
		this.modeleTableMatchs = new DefaultTableModel(new Object[][] {},
				new Object[] { "CLASSEMENT", "EQUIPE", "MATCHS JOUES", "VICTOIRES" });
		for (EquipeTournoi equipe : equipes) {
			this.modeleTableMatchs.addRow(new Object[] { equipe.getRank(), equipe.getEquipe(), equipe.getNbMatchJoues(),
					equipe.getNbVictoires() });
		}
		this.tableMatchs.setModel(modeleTableMatchs);
	}

	public Tournoi getTournoi() {
		return this.tournoi;
	}
}
