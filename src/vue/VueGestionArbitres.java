package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import composant.buttons.BorderedTransparentButton;
import composant.buttons.GoldUnderlinedButton;
import composant.header.User;
import composant.table.GoldAndWhiteTable;
import composant.textfield.RoundedTextField;
import controleur.ControleurGestionArbitres;
import modele.Arbitre;
import ressources.CharteGraphique;
import ui.ButtonTableType;
import ui.ListButtonCellEditor;
import ui.ListButtonCellRender;

public class VueGestionArbitres extends VueMain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DefaultTableModel modeleTableArbitre;

	private JPanel panelBody;
	private JPanel panelFooter;
	private RoundedTextField textFieldNomArbitre;
	private RoundedTextField textFieldPrenomArbitre;
	private List<JTextField> champsArbitre = new LinkedList<>();
	private JTable tableArbitres;
	private JScrollPane scrollPaneTableArbitres;
	private JPanel panelAjouterArbitre;
	private ControleurGestionArbitres controleur;
	private List<ButtonTableType> btns;
	private GoldUnderlinedButton btnRetour;

	private BorderedTransparentButton btnAjouterArbitre;

	public VueGestionArbitres() {
		this.controleur = new ControleurGestionArbitres(this);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				double responsiveSizeWidth = VueGestionArbitres.this.getWidth() * 0.1;
				double responsiveSizeHeight = VueGestionArbitres.this.getHeight() * 0.05;
				VueGestionArbitres.this.panelBody
						.setBorder(new EmptyBorder(50, (int) responsiveSizeWidth, 30, (int) responsiveSizeWidth));
				VueGestionArbitres.this.panelFooter.setBorder(new EmptyBorder((int) responsiveSizeHeight, 0, 0, 0));
				VueGestionArbitres.this.scrollPaneTableArbitres
						.setPreferredSize(new Dimension((int) (responsiveSizeWidth * 3), 0));
				VueGestionArbitres.this.panelAjouterArbitre
						.setPreferredSize(new Dimension((int) (responsiveSizeWidth * 2), 0));
			}
		});

		setHeader();

		panelBody = new JPanel();
		panelBody.setBackground(CharteGraphique.BACKGROUND);
		panelBody.setBorder(new EmptyBorder(50, 0, 50, 0));
		panelBody.setOpaque(false);
		add(panelBody, BorderLayout.CENTER);
		panelBody.setLayout(new BorderLayout(0, 0));

		panelFooter = new JPanel();
		panelFooter.setBorder(new EmptyBorder(20, 0, 0, 0));
		panelFooter.setOpaque(false);
		panelBody.add(panelFooter, BorderLayout.SOUTH);
		panelFooter.setLayout(new BorderLayout(0, 0));

		JPanel panelBtnRetour = new JPanel();
		panelBtnRetour.setOpaque(false);
		panelFooter.add(panelBtnRetour, BorderLayout.WEST);
		panelBtnRetour.setLayout(new BorderLayout(0, 0));

		JPanel panelGoldUnderlineRetour = new JPanel();
		panelGoldUnderlineRetour.setPreferredSize(new Dimension(10, 1));
		panelGoldUnderlineRetour.setBackground(CharteGraphique.GOLD);
		panelBtnRetour.add(panelGoldUnderlineRetour, BorderLayout.SOUTH);

		this.btnRetour = new GoldUnderlinedButton("Retour");
		this.btnRetour.addActionListener(controleur);
		panelBtnRetour.add(btnRetour, BorderLayout.CENTER);

		panelAjouterArbitre = new JPanel();
		panelAjouterArbitre.setBackground(new java.awt.Color(0, true));
		panelAjouterArbitre.setPreferredSize(new Dimension(200, 10));
		panelBody.add(panelAjouterArbitre, BorderLayout.EAST);
		panelAjouterArbitre.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("default:grow"), },
				new RowSpec[] {
						RowSpec.decode("30px"),
						FormSpecs.DEFAULT_ROWSPEC,
						RowSpec.decode("20px"),
						FormSpecs.DEFAULT_ROWSPEC,
						RowSpec.decode("20px"),
						FormSpecs.DEFAULT_ROWSPEC, }));

		textFieldNomArbitre = new RoundedTextField("Nom...");
		genereTFNom();
		textFieldNomArbitre.addKeyListener(controleur);
		this.champsArbitre.add(textFieldNomArbitre);

		textFieldPrenomArbitre = new RoundedTextField("Prenom...");
		genereTFPrenom();
		textFieldPrenomArbitre.addKeyListener(controleur);
		this.champsArbitre.add(textFieldPrenomArbitre);

		this.setBackground(CharteGraphique.BACKGROUND);
		panelAjouterArbitre.setBackground(new Color(123, 32, 115, 50));
		panelAjouterArbitre.setOpaque(false);

		btnAjouterArbitre = new BorderedTransparentButton("Ajouter");
		btnAjouterArbitre.setPreferredSize(new Dimension(0, 35));

		btnAjouterArbitre.setFont(CharteGraphique.TEXT);
		btnAjouterArbitre.addActionListener(controleur);
		btnAjouterArbitre.setEnabled(false);
		btnAjouterArbitre.setActionCommand("addArbitre");

		panelAjouterArbitre.add(btnAjouterArbitre, "2, 6, fill, fill");

		scrollPaneTableArbitres = new JScrollPane();
		scrollPaneTableArbitres.setOpaque(false);
		scrollPaneTableArbitres.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPaneTableArbitres.getViewport().setBackground(CharteGraphique.BACKGROUND);
		scrollPaneTableArbitres.getViewport().setOpaque(false);
		panelBody.add(scrollPaneTableArbitres, BorderLayout.WEST);

		tableArbitres = new GoldAndWhiteTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0 && column != 1;
			}
		};

		modeleTableArbitre = new DefaultTableModel(new Object[][] {},
				new String[] { "Nom", "Prénom", "" });
		tableArbitres.setModel(modeleTableArbitre);
		tableArbitres.setFocusable(false);

		scrollPaneTableArbitres.setViewportView(tableArbitres);

		btns = new LinkedList<>();

		// mes boutons
		ButtonTableType delete = new ButtonTableType(new ImageIcon(getClass().getResource("/pictures/icon/trash.png")),
				15, this.controleur);
		delete.setActionCommand("deleteArbitre");
		btns.add(delete);

		tableArbitres.getColumn("").setCellRenderer(new ListButtonCellRender(btns));
		tableArbitres.getColumn("").setCellEditor(new ListButtonCellEditor(btns));
		this.controleur.majListeArbitres();
	}

	private void setHeader() {
		this.setUser(User.ADMIN);
		this.setTitle("Arbitre - Gestion des arbitres");
	}

	public void genereTFPrenom() {
		textFieldPrenomArbitre.setBorder(new EmptyBorder(0, 8, 0, 8));
		textFieldPrenomArbitre.setPreferredSize(new Dimension(7, 30));
		textFieldPrenomArbitre.setFont(CharteGraphique.PLACEHOLDER_TEXT);
		textFieldPrenomArbitre.setForeground(CharteGraphique.GRAY_LIGHT);
		textFieldPrenomArbitre.setText("Prenom...");
		panelAjouterArbitre.add(textFieldPrenomArbitre, "2, 4, 2, 1, fill, default");
		textFieldPrenomArbitre.setColumns(10);
	}

	public void genereTFNom() {
		textFieldNomArbitre.setBorder(new EmptyBorder(0, 8, 0, 8));
		textFieldNomArbitre.setPreferredSize(new Dimension(7, 30));
		textFieldNomArbitre.setFont(CharteGraphique.PLACEHOLDER_TEXT);
		textFieldNomArbitre.setForeground(CharteGraphique.GRAY_LIGHT);
		textFieldNomArbitre.setText("Nom...");
		panelAjouterArbitre.add(textFieldNomArbitre, "2, 2, 2, 1, fill, default");
		textFieldNomArbitre.setColumns(10);
		textFieldNomArbitre.getInsets().set(4, 12, 2, 8);
	}

	/**
	 * rend la page jolie
	 */
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

	/**
	 * @param e bouton
	 * @return si c'est le boutonRetour ou non
	 */
	public boolean estBoutonRetour(ActionEvent e) {
		return e.getSource() == btnRetour;
	}

	/**
	 * on donne une liste Arbitre @param arbitres qui permet de remplir la table
	 * Arbitres
	 */
	public void remplirListeArbitres(List<Arbitre> arbitres) {
		this.modeleTableArbitre.setRowCount(0);
		this.modeleTableArbitre.setColumnCount(0);
		this.modeleTableArbitre.addColumn("Nom");
		this.modeleTableArbitre.addColumn("Prénom");
		this.modeleTableArbitre.addColumn("");
		tableArbitres.getColumn("").setCellRenderer(new ListButtonCellRender(btns));
		tableArbitres.getColumn("").setCellEditor(new ListButtonCellEditor(btns));
		for (Arbitre a : arbitres) {
			Object[] ligne = { a.getNom(), a.getPrenom() };
			this.modeleTableArbitre.addRow(ligne);
		}
	}

	/**
	 * @return si les champs nom et prénom sont remplis ou initialisés
	 */
	public boolean informationSaisies() {
		for (JTextField tf : this.champsArbitre) {
			if (tf.getText().isEmpty() || tf.getText().equals("Prenom...") || tf.getText().equals("Nom...")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return si les champs sont bien des lettres uniquememnt
	 */
	public boolean champsContiennentUniquementDesLettres() {
		Pattern pattern = Pattern.compile("^[a-zA-ZÀ-ÿ '-]+$");
		for (JTextField tf : this.champsArbitre) {
			if (!pattern.matcher(tf.getText()).matches()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return le champ nom Arbitre
	 */
	public String getNomArbitre() {
		return this.textFieldNomArbitre.getText();
	}

	/**
	 * @return le champ prénom arbitre
	 */
	public String getPrenomArbitre() {
		return this.textFieldPrenomArbitre.getText();
	}

	/**
	 * grise ou non le bouton en fonction du @param b
	 */
	public void setBoutonValidateVisible(Boolean b) {
		btnAjouterArbitre.setEnabled(b);
	}

	/**
	 * Vide les champs de saisie
	 */
	public void viderChamps() {
		this.genereTFNom();
		this.genereTFPrenom();
	}

	/**
	 * génère un message d'erreur @param mess
	 */
	public void afficherMessageErreur(String mess) {
		JOptionPane.showMessageDialog(this, mess, "Erreur", JOptionPane.ERROR_MESSAGE);
	}
}
