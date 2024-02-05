package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import composant.buttons.GoldUnderlinedButton;
import composant.buttons.PicturedButton;
import composant.combobox.RoundedComboBox;
import composant.combobox.RoundedCornerBorder;
import composant.header.User;
import controleur.ControleurCreationEquipe;
import modele.Equipe;
import modele.ModeleCreationEquipe;
import ressources.CharteGraphique;

public class VueCreationEquipe extends VueMain {

	private JPanel panelBody;
	private JPanel panelFooter;
	private JLabel lblWRValue;
	private List<JTextField> players = new LinkedList<>();
	private RoundedComboBox comboBoxEquipe = new RoundedComboBox();
	private GoldUnderlinedButton btnEnregistrer;
	private ControleurCreationEquipe controleur;
	private DefaultComboBoxModel modeleEquipe;
	private RoundedComboBox comboBoxPays = new RoundedComboBox(); 
	private ModeleCreationEquipe modele = new ModeleCreationEquipe();

	public VueCreationEquipe() {
		this.controleur = new ControleurCreationEquipe(this);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				double responsiveSizeWidth = VueCreationEquipe.this.getWidth() * 0.1;
				double responsiveSizeHeight = VueCreationEquipe.this.getHeight() * 0.05;
				VueCreationEquipe.this.panelBody
						.setBorder(new EmptyBorder(50, (int) responsiveSizeWidth, 30, (int) responsiveSizeWidth));
				VueCreationEquipe.this.panelFooter.setBorder(new EmptyBorder((int) responsiveSizeHeight, 0, 0, 0));
			}
		});

		this.setHeader();

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
		btnRetour.setActionCommand("Retour");
		btnRetour.addActionListener(controleur);
		panelFooter.add(btnRetour, BorderLayout.WEST);

		btnEnregistrer = new GoldUnderlinedButton("Valider l'enregistrement");
		btnEnregistrer.setEnabled(false);
		btnEnregistrer.addActionListener(controleur);
		btnEnregistrer.setActionCommand("Valider l'enregistrement");
		panelFooter.add(btnEnregistrer, BorderLayout.EAST);

		JPanel panelBodyFields = new JPanel();
		panelBodyFields.setOpaque(false);
		panelBody.add(panelBodyFields, BorderLayout.CENTER);
		panelBodyFields.setLayout(new BorderLayout(0, 0));

		JPanel panelBodyTeam = new JPanel();
		panelBodyTeam.setOpaque(false);
		panelBodyTeam.setBorder(new EmptyBorder(0, 0, 0, 100));
		panelBodyFields.add(panelBodyTeam, BorderLayout.WEST);
		panelBodyTeam.setLayout(new BorderLayout(0, 0));

		JPanel panelTeamFields = new JPanel();
		panelTeamFields.setPreferredSize(new Dimension(330, 10));
		panelTeamFields.setOpaque(false);
		panelTeamFields.setBorder(null);
		panelBodyTeam.add(panelTeamFields, BorderLayout.CENTER);
		panelTeamFields.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, ColumnSpec.decode("25dlu"),
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow(2)"), },
				new RowSpec[] { RowSpec.decode("30dlu"), FormSpecs.DEFAULT_ROWSPEC, RowSpec.decode("15dlu"),
						FormSpecs.DEFAULT_ROWSPEC, RowSpec.decode("15dlu"), FormSpecs.DEFAULT_ROWSPEC,
						RowSpec.decode("15dlu"), FormSpecs.DEFAULT_ROWSPEC, RowSpec.decode("15dlu"),
						FormSpecs.DEFAULT_ROWSPEC, }));

		try {
			setTeam(panelTeamFields);
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}
		JPanel panelComboEquipe = new JPanel();
		panelComboEquipe.setBorder(null);
		panelComboEquipe.setPreferredSize(new Dimension(10, 35));
		panelComboEquipe.setBackground(CharteGraphique.BACKGROUND);
		// panelComboEquipe.setOpaque(false);
		panelBodyTeam.add(panelComboEquipe, BorderLayout.NORTH);
		panelComboEquipe.setLayout(new BorderLayout(0, 0));
		modeleEquipe = new DefaultComboBoxModel(new String[] { "" });
		this.remplirComboBox();
		comboBoxEquipe.addItemListener(controleur);
		comboBoxEquipe.getEditor().getEditorComponent().addKeyListener(controleur);
		comboBoxEquipe.addActionListener(controleur);
		comboBoxEquipe.setEditable(true);
		comboBoxEquipe.setFocusable(true);
		comboBoxEquipe.setModel(modeleEquipe);

		panelComboEquipe.add(comboBoxEquipe, BorderLayout.CENTER);

		JPanel panelBodyInformations = new JPanel();
		panelBodyInformations.setOpaque(false);
		panelBodyFields.add(panelBodyInformations, BorderLayout.CENTER);
		panelBodyInformations.setLayout(new BorderLayout(0, 0));

		JPanel panelPays = new JPanel();
		panelPays.setOpaque(false);
		panelPays.setBackground(CharteGraphique.BACKGROUND);
		panelBodyInformations.add(panelPays, BorderLayout.NORTH);
		panelPays.setLayout(new BorderLayout(0, 0));
		comboBoxPays.setModel(makeModelPays());

		panelPays.add(comboBoxPays, BorderLayout.WEST);

		JPanel panelScoreRank = new JPanel();
		panelScoreRank.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("65dlu"), ColumnSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, },
				new RowSpec[] { RowSpec.decode("30dlu"), RowSpec.decode("70dlu"), RowSpec.decode("40dlu"),
						RowSpec.decode("70dlu"), }));

		panelScoreRank.setOpaque(false);
		panelScoreRank.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelBodyInformations.add(panelScoreRank, BorderLayout.CENTER);

		JPanel WRPanel = new JPanel();
		WRPanel.setOpaque(false);
		WRPanel.setBorder(new LineBorder(new Color(255, 255, 255), 3));
		panelScoreRank.add(WRPanel, "1, 2, 2, 1, fill, fill");
		WRPanel.setLayout(new GridLayout(2, 0, 0, 0));

		JPanel WRPanelTitle = new JPanel();
		WRPanelTitle.setOpaque(false);
		WRPanelTitle.setBorder(new EmptyBorder(10, 0, 0, 0));
		WRPanel.add(WRPanelTitle);

		JLabel lblWR = new JLabel("World Rank");
		lblWR.setHorizontalTextPosition(SwingConstants.CENTER);
		lblWR.setHorizontalAlignment(SwingConstants.CENTER);
		lblWR.setForeground(Color.WHITE);
		lblWR.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 30));
		WRPanelTitle.add(lblWR);

		lblWRValue = new JLabel("Rank Number <rank>");
		lblWRValue.setHorizontalTextPosition(SwingConstants.CENTER);
		lblWRValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblWRValue.setForeground(new Color(255, 255, 255));
		lblWRValue.setFont(new Font("Calibri", Font.PLAIN, 14));
		WRPanel.add(lblWRValue);

		btnRetour.addActionListener(controleur);
		this.initialisationWr();

	}

	private void setHeader() {
		this.setUser(User.ADMIN);
		this.setTitle("Equipes - Création d'équipe");
	}

	private void setTeam(JPanel panel) {
		if (!(panel.getLayout() instanceof FormLayout)) {
			throw new IllegalArgumentException("Initialisation d'un panel ayant un Layout différent de FormLayout");
		}
		panel.setBackground(CharteGraphique.BACKGROUND);
		File dossier = new File("src//pictures//icon//lanes");
		for (int i = 1; i < 6; i++) {
			// Définition de l'icon
			String emplacement;
			try {
				emplacement = dossier.getCanonicalPath() + File.separator + dossier.list()[i - 1];
				ImageIcon lane = new ImageIcon(emplacement);
				PicturedButton icon = new PicturedButton(lane);
				icon.resizeIcon(35);
				icon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				// Création et configuration des textFields
				JTextField tf = new JTextField();
				tf.setBorder(new RoundedCornerBorder());
				tf.setPreferredSize(new Dimension(100, 35));
				tf.setFont(CharteGraphique.TEXT);
				tf.addKeyListener(controleur);
				this.players.add(tf);

				// Définition des contraintes de positionnement
				String constraintTextField = "6, " + 2 * i + ", fill, default";
				String constraintIcon = "2, " + 2 * i + ", center, default";

				// Ajout des items
				panel.add(tf, constraintTextField);
				panel.add(icon, constraintIcon);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private static DefaultComboBoxModel<String> makeModelPays() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for (Pays p : Pays.values()) {
			model.addElement(p.getNom());
		}
		return model;
	}

	public boolean informationSaisies() {
		for (JTextField tf : this.players) {
			if (tf.getText().isEmpty()) {
				return false;
			}
		}
		if (this.comboBoxEquipe.getSelectedItem() != null) {
			return !(this.comboBoxEquipe.getSelectedItem().toString().isEmpty());
		}
		return false;
	}

	public void initialisationWr() {
		this.controleur.initialisationWr();
	}

	public String getNomEquipe() {
		if (this.comboBoxEquipe.getSelectedItem() != null) {
			return this.comboBoxEquipe.getSelectedItem().toString();
		}
		return "";
	}

	public String getPays() {
		return this.comboBoxPays.getSelectedItem().toString();
	}

	public Boolean teamNameIsEmpty() {
		return this.comboBoxEquipe.getSelectedItem().toString().isEmpty();
	}

	public void resetChamps() {
		for (JTextField tf : this.players) {
			tf.setText("");
		}
	}

	public void setWorldRank(String string) {
		this.lblWRValue.setText(string);
	}

	public List<String> getJoueurs() {
		List<String> joueurs = new LinkedList<>();
		for (JTextField tf : this.players) {
			joueurs.add(tf.getText());
		}
		return joueurs;
	}

	public void setButtonValidateEnable(boolean state) {
		this.btnEnregistrer.setEnabled(state);
	}

	public JComboBox getComboBoxEquipe() {
		return this.comboBoxEquipe;
	}

	public void remplirComboBox() {
		try {
			List<Equipe> equipes = this.modele.getEquipesNonInscrites();
			modeleEquipe.removeAllElements();
			for (Equipe equipe : equipes) {
				modeleEquipe.addElement(equipe.getNom());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void afficherMessageErreur(String mess) {
		JOptionPane.showMessageDialog(this, mess, "Erreur", JOptionPane.ERROR_MESSAGE);
	}

}