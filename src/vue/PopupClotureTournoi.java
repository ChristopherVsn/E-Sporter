package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import composant.buttons.GoldUnderlinedButton;
import controleur.ControleurPopupCloturePoule;
import ressources.CharteGraphique;

public class PopupClotureTournoi extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private final JPanel buttonPane = new JPanel();
	private JTextArea textAreaMessage = new JTextArea();
	private String nomTournoi;
	private ControleurPopupCloturePoule controleur;

	public PopupClotureTournoi(String nomTournoi, VueMatchsPouleArbitre vue) {
		this.nomTournoi = nomTournoi;
		this.controleur = new ControleurPopupCloturePoule(vue, this);
		initComponant();
	}

	private void initComponant() {
		setPosition();
		setBehavior();
		drawPanel();
		addComponent();
		setTextArea();
	}

	private void setPosition() {
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		setBounds(width / 2 - 200, height / 2 - 100, 400, 200);
		setPreferredSize(new Dimension(300, 200));
	}

	private void setBehavior() {
		this.setResizable(false);
		this.setModal(true);
	}

	private void drawPanel() {
		getContentPane().setBackground(CharteGraphique.BACKGROUND);
		setBackground(CharteGraphique.BACKGROUND);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setOpaque(false);
		contentPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
		contentPanel.setLayout(new BorderLayout(0, 0));
		buttonPane.setOpaque(false);
		buttonPane.setBorder(new EmptyBorder(0, 50, 30, 50));
		buttonPane.setLayout(new BorderLayout(0, 0));
	}

	private void setTextArea() {
		textAreaMessage.setLineWrap(true);
		textAreaMessage.setWrapStyleWord(true);
		textAreaMessage.setForeground(CharteGraphique.WHITE);
		textAreaMessage.setAlignmentX(JLabel.CENTER);
		textAreaMessage.setBorder(null);
		textAreaMessage.setOpaque(false);
		textAreaMessage.setEditable(false);
		textAreaMessage.setFont(CharteGraphique.BUTTON_TEXT);
		textAreaMessage.setText("Voulez-vous cloturer le tournoi " + this.nomTournoi + " ?");
	}

	private void addComponent() {
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		contentPanel.add(textAreaMessage, BorderLayout.CENTER);

		GoldUnderlinedButton supprimer = new GoldUnderlinedButton("Valider");
		GoldUnderlinedButton annuler = new GoldUnderlinedButton("Annuler");

		supprimer.addActionListener(controleur);
		annuler.addActionListener(controleur);

		buttonPane.add(supprimer, BorderLayout.EAST);
		buttonPane.add(annuler, BorderLayout.WEST);
	}

	public String getNomTournoi() {
		return this.nomTournoi;
	}

}
