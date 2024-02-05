package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import modele.Arbitre;
import modele.Tournoi;
import ressources.CharteGraphique;

public class PopupInfoTournoi extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tournoi turnament;
	private List<Arbitre> arbitres;
	private final JPanel contentPanel = new JPanel();
	private JTextArea textAreaMessage = new JTextArea();
	private String nomEquipe;


	public PopupInfoTournoi(Tournoi turnament, List<Arbitre> arbitres) {
		this.turnament = turnament;
		this.arbitres = arbitres;
		initComponant();
	}

	private void initComponant() {
		setPosition();
		setBehavior();
		drawPanel();
		addComponent();
		setTextArea();
		setTextContent();
	}

	private void setPosition() {
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		setBounds(width / 2 - 200, height / 2 - 100, 400, 245);
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
	
	private void setTextArea() {
		textAreaMessage.setLineWrap(true);
		textAreaMessage.setWrapStyleWord(true);
		textAreaMessage.setForeground(CharteGraphique.WHITE);
		textAreaMessage.setAlignmentX(JLabel.CENTER);
		textAreaMessage.setBorder(null);
		textAreaMessage.setOpaque(false);
		textAreaMessage.setEditable(false);
		textAreaMessage.setFont(CharteGraphique.BUTTON_TEXT);
	}
	
	private void setTextContent() {
		String temp = "";
		if(this.arbitres.size() == 1) {
			temp = "Arbitre : ";
		} else {
			temp = "Arbitres : ";
		}
		for(Arbitre arbitre:this.arbitres) {
			temp += arbitre.getNom() + " " + arbitre.getPrenom() + ", ";
		}
		String arbitres = temp.substring(0, temp.length() - 2);
		arbitres += "\n\n";
		String login = "Login : " + this.turnament.getLogin() + "\n\n";
		String mdp = "Password : " + this.turnament.getMotDePasse();
		textAreaMessage.setText(arbitres + login + mdp);
	}
	
	private void addComponent() {
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.add(textAreaMessage, BorderLayout.CENTER);
	}

	public String getEquipe() {
		return this.nomEquipe;
	}

}
