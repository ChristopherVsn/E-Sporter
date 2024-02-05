package vue;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import composant.header.Header;
import composant.header.User;
import ressources.CharteGraphique;

public class VueMain extends JPanel {
	
	protected final Header panelHeader = new Header();
	
	public VueMain() {
		initComponent();
	}
	
	private void initComponent() {
		drawPanel();
		setHeader();
	}

	private void drawPanel() {
		setBackground(CharteGraphique.BACKGROUND);
		setBorder(null);
		setLayout(new BorderLayout(0, 0));
	}
	
	private void setHeader() {
		add(panelHeader, BorderLayout.NORTH);
	}

	public void changePage(JPanel nouvelleVue) {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		if (frame != null) {
			frame.getContentPane().removeAll();
			frame.getContentPane().add(nouvelleVue);
			frame.revalidate();
			frame.repaint();
		}
	}
	
	public void setUser(User user) {
		this.panelHeader.setUser(user);
	}
	
	public void setTitle(String title) {
		this.panelHeader.setTitle(title);
	}
}
