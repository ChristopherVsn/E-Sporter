package vue;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import composant.buttons.GoldUnderlinedButton;
import composant.buttons.PicturedButton;
import composant.combobox.RoundedComboBox;
import composant.datechooser.DateChooser;
import composant.header.User;
import composant.textfield.PlaceHolderTextField;
import composant.textfield.RoundedTextField;
import controleur.ControleurCreationTournoi;
import modele.Ligue;
import ressources.CharteGraphique;
import ressources.Images;
import ui.ObjectDesigner;

public class VueCreationTournoi extends VueMain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelBody;
	private RoundedTextField textFieldNomTournoi;
	private PlaceHolderTextField textFieldDateDebut;
	private PlaceHolderTextField textFieldDateFin;
	private RoundedComboBox comboBoxNotoriete;
	private DateChooser dateDebut = new DateChooser();
	private DateChooser dateFin = new DateChooser();
	private JPanel panelLogo = new JPanel();
	private PicturedButton logoCrown;
	private DefaultComboBoxModel<String> modeleLigue = new DefaultComboBoxModel<String>();
	private ControleurCreationTournoi controleur;
	private GoldUnderlinedButton btnLancerTournoi;
	private GoldUnderlinedButton btnRetour;

	/**
	 * Create the panel.
	 * 
	 * @throws Exception
	 */
	public VueCreationTournoi() {

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				double responsiveSizeWidth = VueCreationTournoi.this.getWidth() * 0.1;
				VueCreationTournoi.this.panelBody
						.setBorder(new EmptyBorder(50, (int) responsiveSizeWidth, 30, (int) responsiveSizeWidth));
				double crownSize = VueCreationTournoi.this.getWidth() * 0.2;
				VueCreationTournoi.this.panelLogo.setBorder(new EmptyBorder(0, (int) crownSize, 0, 0));
				VueCreationTournoi.this.logoCrown.resizeIcon((int) crownSize);

			}
		});
		this.controleur = new ControleurCreationTournoi(this);

		this.setUser(User.ADMIN);
		this.setTitle("Tournois - Création d'un tournoi");

		panelBody = new JPanel();
		panelBody.setOpaque(false);
		panelBody.setBorder(new EmptyBorder(50, 0, 50, 0));
		add(panelBody, BorderLayout.CENTER);
		panelBody.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panelBodyInformations = new JPanel();
		panelBodyInformations.setBorder(new EmptyBorder(0, 0, 0, 50));
		panelBody.add(panelBodyInformations);
		panelBodyInformations.setLayout(new BorderLayout(0, 0));
		panelBodyInformations.setOpaque(false);

		JPanel panelTurnamentName = new JPanel();
		panelTurnamentName.setBorder(new EmptyBorder(0, 0, 30, 0));
		panelBodyInformations.add(panelTurnamentName, BorderLayout.NORTH);
		panelTurnamentName.setOpaque(false);
		panelTurnamentName.setBackground(CharteGraphique.BACKGROUND);
		panelTurnamentName.setLayout(new BorderLayout(0, 0));

		textFieldNomTournoi = new RoundedTextField("Nom du tournoi...");
		textFieldNomTournoi.setPreferredSize(new Dimension(100, 35));
		textFieldNomTournoi.setPlaceHolderColors(CharteGraphique.GRAY_LIGHT, CharteGraphique.BLACK);
		textFieldNomTournoi.addKeyListener(controleur);
		panelTurnamentName.add(textFieldNomTournoi, BorderLayout.CENTER);

		JPanel panelSelection = new JPanel();
		panelSelection.setOpaque(false);
		panelBodyInformations.add(panelSelection, BorderLayout.WEST);
		panelSelection.setLayout(new BorderLayout(0, 0));

		JPanel panelNotoriete = new JPanel();
		panelNotoriete.setOpaque(false);
		panelSelection.add(panelNotoriete, BorderLayout.NORTH);
		panelNotoriete.setLayout(new BorderLayout(0, 0));
		panelNotoriete.setBackground(CharteGraphique.BACKGROUND);

		comboBoxNotoriete = new RoundedComboBox();
		panelNotoriete.add(comboBoxNotoriete, BorderLayout.NORTH);
		this.remplirComboBoxLigue();
		comboBoxNotoriete.setModel(modeleLigue);

		JPanel panelDate = new JPanel();
		panelDate.setOpaque(false);
		panelSelection.add(panelDate, BorderLayout.CENTER);
		panelDate.setLayout(
				new FormLayout(new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
						new RowSpec[] { RowSpec.decode("50dlu"), RowSpec.decode("36px"), RowSpec.decode("20dlu"),
								RowSpec.decode("36px"), }));

		JPanel panelDateDebut = new JPanel();
		panelDateDebut.setOpaque(false);
		panelDate.add(panelDateDebut, "2, 2, fill, fill");
		panelDateDebut.setLayout(new BorderLayout(0, 0));

		textFieldDateDebut = new PlaceHolderTextField("Date de début...");
		textFieldDateDebut.setOpaque(false);
		textFieldDateDebut.setBorder(null);
		panelDateDebut.add(textFieldDateDebut, BorderLayout.CENTER);

		PicturedButton btnDateDebut = new PicturedButton(Images.CALENDAR);
		btnDateDebut.resizeIcon(25);
		btnDateDebut.setBorder(new EmptyBorder(2, 2, 2, 2));
		btnDateDebut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateDebut.showPopup();
			}
		});
		panelDateDebut.add(btnDateDebut, BorderLayout.EAST);

		JPanel GoldUnderlineDateDebut = new JPanel();
		GoldUnderlineDateDebut.setBackground(CharteGraphique.GOLD);
		GoldUnderlineDateDebut.setPreferredSize(new Dimension(0, 1));
		panelDateDebut.add(GoldUnderlineDateDebut, BorderLayout.SOUTH);

		JPanel panelDateFin = new JPanel();
		panelDateFin.setOpaque(false);
		panelDate.add(panelDateFin, "2, 4, fill, fill");
		panelDateFin.setLayout(new BorderLayout(0, 0));

		textFieldDateFin = new PlaceHolderTextField("Date de fin...");
		textFieldDateFin.setOpaque(false);
		textFieldDateFin.setBorder(null);
		panelDateFin.add(textFieldDateFin, BorderLayout.CENTER);

		PicturedButton btnDateFin = new PicturedButton(Images.CALENDAR);
		btnDateFin.resizeIcon(25);
		btnDateFin.setBorder(new EmptyBorder(2, 2, 2, 2));
		btnDateFin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateFin.showPopup();
			}
		});
		panelDateFin.add(btnDateFin, BorderLayout.EAST);

		JPanel GoldUnderlineDateFin = new JPanel();
		GoldUnderlineDateFin.setBackground(CharteGraphique.GOLD);
		GoldUnderlineDateFin.setPreferredSize(new Dimension(0, 1));
		panelDateFin.add(GoldUnderlineDateFin, BorderLayout.SOUTH);

		JPanel panelButtons = new JPanel();
		panelButtons.setOpaque(false);
		panelButtons.setLayout(new BorderLayout(0, 0));

		this.btnRetour = new GoldUnderlinedButton("Retour");
		this.btnRetour.setActionCommand("Retour");
		this.btnRetour.addActionListener(controleur);
		panelButtons.add(this.btnRetour, BorderLayout.WEST);

		this.btnLancerTournoi = new GoldUnderlinedButton("Créer le tournoi");
		this.btnLancerTournoi.setActionCommand("CreationTournoi");
		this.btnLancerTournoi.setEnabled(false);
		this.btnLancerTournoi.addActionListener(controleur);
		panelButtons.add(this.btnLancerTournoi, BorderLayout.EAST);

		panelBodyInformations.add(panelButtons, BorderLayout.SOUTH);

		this.dateDebut.setTextRefernce(textFieldDateDebut);
		this.dateFin.setTextRefernce(textFieldDateFin);

		textFieldDateDebut.addCaretListener(controleur);
		textFieldDateFin.addCaretListener(controleur);

		panelLogo.setBorder(new EmptyBorder(0, 150, 0, 0));
		panelLogo.setOpaque(false);
		panelBody.add(panelLogo);
		panelLogo.setLayout(new BorderLayout(0, 0));

		logoCrown = new PicturedButton(Images.CROWN);
		logoCrown.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		panelLogo.add(logoCrown, BorderLayout.CENTER);
	}

	@Override
	protected void paintComponent(Graphics grphcs) {

		super.paintComponent(grphcs);
		Graphics2D g2d = (Graphics2D) grphcs;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Création du premier dégradé : vert vers turquoise en partant du coin
		// inferieur droit
		GradientPaint gp = new GradientPaint((int) (getWidth() * 0.8), getHeight() - 35,
				CharteGraphique.TURQUOISE_RADIANT, (int) (getWidth() * 0.8) + 100, getHeight(),
				CharteGraphique.GREEN_RADIANT);
		g2d.setPaint(gp);

		// Remplissage du panel avec ce dégradé
		g2d.fillRect(0, 0, getWidth(), getHeight());

		// Création du second dégradé : turquoise vers background en partant de la
		// jonction du premier dégradé
		gp = new GradientPaint((int) (getWidth() * 0.7) - (int) (getWidth() * 0.2),
				getHeight() - (int) (getWidth() * 0.07), CharteGraphique.BACKGROUND, (int) (getWidth() * 0.7),
				getHeight(), CharteGraphique.TURQUOISE_RADIANT);
		g2d.setPaint(gp);

		// Création de la forme de remplissage du panel pour ce dégradé
		Polygon p = new Polygon();
		p.addPoint(0, getHeight());
		p.addPoint(0, 0);
		p.addPoint((int) (getWidth() * 0.7) + (int) (getHeight() * 0.35), 0);
		p.addPoint((int) (getWidth() * 0.7), getHeight());

		// Remplissage de la forme suivant jonction du premier dégradé, avec le second
		// dégradé
		g2d.fillPolygon(p);
	}

	public String getNomTournoi() {
		return this.textFieldNomTournoi.getText();
	}

	public String getLigue() {
		return comboBoxNotoriete.getSelectedItem().toString();
	}

	public String getDateDebut() {
		return textFieldDateDebut.getText();
	}

	public String getDateFin() {
		return textFieldDateFin.getText();
	}

	public boolean informationsSaisies() {
		return (!this.textFieldDateDebut.isEmpty() && !this.textFieldDateFin.isEmpty()
				&& !this.textFieldNomTournoi.isEmpty());
	}

	public void remplirComboBoxLigue() {
		this.modeleLigue.removeAllElements();
		Ligue[] valeurs = Ligue.values();
		for (Ligue ligue : valeurs) {
			this.modeleLigue.addElement(ligue.getNom());
		}
	}

	public void setBoutonValidateVisible(boolean b) {
		this.btnLancerTournoi.setEnabled(b);
	}

	public void resetDateField() {
		this.dateDebut.setTextRefernce(textFieldDateDebut);
		this.dateFin.setTextRefernce(textFieldDateFin);
	}

	public void afficherMessageErreur(String mess) {
		JOptionPane.showMessageDialog(this, mess, "Erreur", JOptionPane.ERROR_MESSAGE);
	}
	
	public void resetField() {
		this.btnLancerTournoi.setEnabled(false);
		this.textFieldDateDebut.setText("Date de début...");
		this.textFieldDateFin.setText("Date de fin...");
		this.textFieldNomTournoi.setText("Nom du tournoi...");
		
		this.textFieldNomTournoi.setFont(CharteGraphique.PLACEHOLDER_TEXT);
		this.textFieldDateFin.setFont(CharteGraphique.PLACEHOLDER_TEXT);
		this.textFieldDateDebut.setFont(CharteGraphique.PLACEHOLDER_TEXT);
		
		this.textFieldNomTournoi.setForeground(CharteGraphique.GRAY_LIGHT);
		this.textFieldDateFin.setForeground(CharteGraphique.GRAY_LIGHT);
		this.textFieldDateDebut.setForeground(CharteGraphique.GRAY_LIGHT);

		this.comboBoxNotoriete.setSelectedIndex(0);
	}
}
