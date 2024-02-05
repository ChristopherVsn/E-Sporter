package composant.header;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import composant.buttons.PicturedButton;
import composant.buttons.PicturedButtonHover;
import ressources.CharteGraphique;
import ressources.Images;

public class Header extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JLabel labelTitle = new JLabel("<title undefined>");
	private final JLabel labelUserName = new JLabel("<user undefined>");;

	private final JPanel margins = new JPanel();
	private final JPanel informations = new JPanel();;
	private final JPanel user = new JPanel();
	private final JPanel right = new JPanel();
	private final JPanel actions = new JPanel();
	private final JPanel btnActions = new JPanel();
	private final HeaderActions controleur = new HeaderActions(this);

	private PicturedButton btnLogo;
	private PicturedButton btnDisconnect;
	private PicturedButton btnIconUser;
	private PicturedButtonHover btnClose;
	private PicturedButtonHover btnFullScreen;
	private PicturedButtonHover btnReduce;

	public Header() {
		initComponent();
	}

	private void initComponent() {
		drawPanels();
		setMargins();
		drawButtons();
		implementButtons();
		setTitle();
		drawUser();
		addComponents();
	}

	private void drawUser() {
		labelUserName.setFont(CharteGraphique.BUTTON_TEXT);
		labelUserName.setForeground(CharteGraphique.GRAY_LIGHT);
		labelUserName.setBorder(new EmptyBorder(5, 5, 0, 5));
	}

	private void addComponents() {
		add(this.margins, BorderLayout.CENTER);
		this.actions.add(this.btnActions, BorderLayout.EAST);
		this.margins.add(this.informations, BorderLayout.WEST);
		this.margins.add(this.right, BorderLayout.EAST);
		this.right.add(this.user, BorderLayout.EAST);
		this.right.add(this.actions, BorderLayout.NORTH);
		this.informations.add(this.btnLogo);
		this.informations.add(this.labelTitle);
		this.user.add(this.btnIconUser);
		this.user.add(this.labelUserName);
		this.user.add(this.btnDisconnect);
		this.actions.add(this.btnActions, BorderLayout.EAST);
		this.btnActions.add(this.btnClose, BorderLayout.EAST);
		this.btnActions.add(this.btnFullScreen, BorderLayout.CENTER);
		this.btnActions.add(this.btnReduce, BorderLayout.WEST);
	}

	private void drawPanels() {
		setBorder(new MatteBorder(0, 0, 3, 0, CharteGraphique.GOLD));
		setBackground(CharteGraphique.BACKGROUND);
		setLayout(new BorderLayout(0, 0));
		right.setLayout(new BorderLayout());
		right.setOpaque(false);
		actions.setOpaque(false);
		btnActions.setLayout(new BorderLayout());
		actions.setLayout(new BorderLayout());
		actions.setPreferredSize(new Dimension(0, 20));
		user.setBorder(new EmptyBorder(5, 0, 0, 0));
		user.setOpaque(false);
		margins.setOpaque(false);
		informations.setOpaque(false);
		btnActions.setOpaque(false);
	}

	private void setMargins() {
		margins.setBorder(new EmptyBorder(5, 5, 5, 5));
		margins.setLayout(new BorderLayout(0, 0));
	}

	private void setTitle() {
		this.labelTitle.setBorder(new EmptyBorder(5, 0, 0, 0));
		this.labelTitle.setFont(CharteGraphique.TITLE);
		this.labelTitle.setForeground(CharteGraphique.WHITE);
	}

	private void drawButtons() {
		ImageIcon logo = new ImageIcon(getClass().getResource("/pictures/icon/logo.png"));
		ImageIcon disconnect = new ImageIcon(getClass().getResource("/pictures/icon/deconnexion.png"));
		ImageIcon user = new ImageIcon(getClass().getResource("/pictures/icon/user.png"));
		
		btnClose = new PicturedButtonHover(Images.CLOSE, Images.CLOSE_HOVER);
		btnReduce = new PicturedButtonHover(Images.REDUCE, Images.REDUCE_HOVER);
		btnLogo = new PicturedButton(logo);
		btnDisconnect = new PicturedButton(disconnect);
		btnIconUser = new PicturedButton(user);
		
		if(this.isFullScreen()) {
			btnFullScreen = new PicturedButtonHover(Images.DOUBLE_SCREEN, Images.DOUBLE_SCREEN_HOVER);
		} else {
			btnFullScreen = new PicturedButtonHover(Images.SIMPLE_SCREEN, Images.SIMPLE_SCREEN_HOVER);
		}

		
		btnLogo.resizeIcon(60);
		btnDisconnect.resizeIcon(30);
		btnIconUser.resizeIcon(30);
		btnClose.resizeIcon(35, 20);
		btnFullScreen.resizeIcon(35, 20);
		btnReduce.resizeIcon(35, 20);

		btnLogo.setBorder(new EmptyBorder(0, 5, 0, 15));
		btnIconUser.setBorder(new EmptyBorder(0, 5, 0, 5));
		
		btnIconUser.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	private void implementButtons() {
		btnDisconnect.setActionCommand("disconnect");
		btnLogo.setActionCommand("accueil");
		btnClose.setActionCommand("close");
		btnReduce.setActionCommand("reduce");
		
		if(this.isFullScreen()) {
			System.out.println("full");
			btnFullScreen.setActionCommand("minScreen");
		} else {
			System.out.println("min");
			btnFullScreen.setActionCommand("fullScreen");
		}
		
		btnClose.addActionListener(this.controleur);
		btnFullScreen.addActionListener(this.controleur); 
		btnReduce.addActionListener(this.controleur); 
		btnLogo.addActionListener(controleur);
		btnDisconnect.addActionListener(controleur);
	}

	private boolean isFullScreen() {
		int heightScreen = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int widthScreen = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		Frame f = this.getFrame();
		if(f != null) {
			System.out.println("frame not null");
			int heightFrame = f.getHeight();
			int widthFrame = f.getWidth();
			return ((heightFrame == heightScreen) && (widthFrame == widthScreen));
		}
		return false;
	}

	public void setUser(User user) {
		this.labelUserName.setText(user.getUser());
		switch(user) {
			case ADMIN:
				this.btnLogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
				this.btnLogo.addActionListener(controleur);
				break;
			case REFEREE:
				this.btnLogo.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				this.btnLogo.removeActionListener(controleur);
				break;
		}
	}

	public void setTitle(String title) {
		this.labelTitle.setText(title);
	}
	
	public void BtnCloseHover(boolean hover) {
		btnClose.setBackground(CharteGraphique.RED);
		this.btnClose.setOpaque(hover);
	}
	
	public void BtnScreenHover(boolean hover) {
		this.btnFullScreen.setBackground(CharteGraphique.BLUR);
		this.btnFullScreen.setOpaque(hover);
	}
	
	public void BtnReduceHover(boolean hover) {
		this.btnReduce.setBackground(CharteGraphique.BLUR);
		this.btnReduce.setOpaque(hover);
	}
	
	public void setFullScreen() {
		this.setIconFullScreen();
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		this.getFrame().setSize(new Dimension(width, height));
		this.getFrame().setLocation(0, 0);
	}
	
	public void setIconFullScreen() {
		this.btnFullScreen.setActionCommand("minScreen");
		this.btnFullScreen.changeIcons(Images.DOUBLE_SCREEN, Images.DOUBLE_SCREEN_HOVER);
	}
	
	public void setIconMinScreen() {
		this.btnFullScreen.setActionCommand("fullScreen");
		this.btnFullScreen.changeIcons(Images.SIMPLE_SCREEN, Images.SIMPLE_SCREEN_HOVER);
	}
	
	public void setMinScreen() {
		this.setIconMinScreen();
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		this.getFrame().setSize(new Dimension(1280, 720));
		this.getFrame().setLocation((width - this.getFrame().getWidth())/2, (height - this.getFrame().getHeight())/2);
	}
	
	public Frame getFrame() {
		return (JFrame) SwingUtilities.getWindowAncestor(this);
	}
}
