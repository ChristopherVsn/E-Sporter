package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ressources.CharteGraphique;


public class PopupClotureTournoi extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel textAreaMessage = new JLabel();
	private final JLabel lblCloture = new JLabel("Cloture");
	private String nomTournoi;

	public PopupClotureTournoi(String nomTournoi) {
		this.nomTournoi = nomTournoi;
		initComponant();
	}

	private void initComponant() {
		setPosition();
		setBehavior();
		drawPanel();
		setTextArea();
		setJLabel();
		addComponent();
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
	}

	private void setJLabel() {
		lblCloture.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblCloture.setFont(CharteGraphique.TITLE);
		this.lblCloture.setForeground(CharteGraphique.WHITE);
		this.lblCloture.setBorder(new EmptyBorder(10, 0, 10, 0));
		this.lblCloture.setVerticalAlignment(JLabel.CENTER);
	}

	private void setTextArea() {
		textAreaMessage.setForeground(CharteGraphique.GRAY);
		textAreaMessage.setBorder(null);
		textAreaMessage.setOpaque(false);
		textAreaMessage.setFont(CharteGraphique.BUTTON_TEXT);
		textAreaMessage.setText("<html><p style=\"text-align:center;\">Le tournoi <span style=\"color:rgb("
				+ CharteGraphique.GOLD.getRed() + ", " + CharteGraphique.GOLD.getGreen() + ", "
				+ CharteGraphique.GOLD.getBlue() + ");\">" + this.nomTournoi
				+ "</span> est clos, vous allez être déconnecté de votre session</p><html>");
	}

	private void addComponent() {
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.add(textAreaMessage, BorderLayout.CENTER);
		contentPanel.add(this.lblCloture, BorderLayout.NORTH);
	}

	public String getNomTournoi() {
		return this.nomTournoi;
	}

}
