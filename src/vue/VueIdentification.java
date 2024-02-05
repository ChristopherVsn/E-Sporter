package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import composant.buttons.PicturedButtonHover;
import composant.textfield.PasswordField;
import composant.textfield.RoundedCornerBorderTightEmpty;
import composant.textfield.RoundedCornerBorderTightPlain;
import composant.textfield.TextFieldPlaceHolderMovable;
import controleur.ControleurIdentification;
import modele.Administrateur;
import ressources.CharteGraphique;
import ressources.Images;

public class VueIdentification extends JPanel {
	
	private final JPanel panelIdentification = new JPanel();
	private final JPanel panelActions = new JPanel();
	private final JPanel panelBtnActions = new JPanel();
	private final JPanel panelConnexion  = new JPanel();
	private final JPanel panelStart  = new JPanel();
	
	private JPanel panelPicture;
	private JPanel panelLogo;
	private JPanel panelError;
	
	private PicturedButtonHover btnClose;
	private PicturedButtonHover btnScreen;
	private PicturedButtonHover btnReduce;
	private ControleurIdentification controleur;
	
	private PasswordField password;
	private TextFieldPlaceHolderMovable user;
	private JLabel connexion;
	private JTextArea messageError = new JTextArea();
	private PicturedButtonHover btnStart;
	private boolean error;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public VueIdentification() {
		this.controleur = new ControleurIdentification(this);
		this.initComponent();
	}

	private void initComponent() {
		this.drawImagesPanel();
		this.drawPanels();
		this.drawTextFields();
		this.drawLabel();
		this.drawButton();
		this.setActions();
		this.setTextArea();
		this.addComponents();
	}

	private void drawLabel() {
		this.connexion = new JLabel("Connexion");
		this.connexion.setHorizontalAlignment(SwingConstants.CENTER);
		this.connexion.setForeground(CharteGraphique.BLACK);
		this.connexion.setFont(CharteGraphique.BOLD_TITLE);
	}
	
	private void setTextArea() {
		this.messageError.setLineWrap(true);
		this.messageError.setWrapStyleWord(true);
		this.messageError.setForeground(CharteGraphique.WHITE);
		this.messageError.setAlignmentX(JLabel.CENTER);
		this.messageError.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		this.messageError.setBorder(new EmptyBorder(20,30,30,30));
		this.messageError.setOpaque(false);
		this.messageError.setEditable(false);
		this.messageError.setFont(CharteGraphique.TEXT);
	}

	private void setActions() {
		btnClose.setActionCommand("close");
		btnScreen.setActionCommand("fullScreen");
		btnReduce.setActionCommand("reduce");
		btnStart.setActionCommand("start");
		
		btnClose.addActionListener(this.controleur);
		btnScreen.addActionListener(this.controleur); 
		btnReduce.addActionListener(this.controleur); 
		btnStart.addActionListener(this.controleur);
	}

	private void addComponents() {
		add(panelIdentification, BorderLayout.WEST);
		add(panelPicture, BorderLayout.CENTER);
		panelIdentification.add(panelLogo, BorderLayout.NORTH);
		panelIdentification.add(panelConnexion, BorderLayout.CENTER);
		panelIdentification.add(panelStart, BorderLayout.SOUTH);
		panelPicture.add(panelActions, BorderLayout.NORTH);
		panelActions.add(panelBtnActions, BorderLayout.EAST);
		panelBtnActions.add(btnClose, BorderLayout.EAST);
		panelBtnActions.add(btnScreen, BorderLayout.CENTER);
		panelBtnActions.add(btnReduce, BorderLayout.WEST);
		panelConnexion.add(this.connexion, "2, 2, 2, 1, fill, default");
		panelConnexion.add(this.user, "2, 4, 2, 1, fill, default");
		panelConnexion.add(this.password, "2, 6, 2, 1, fill, default");
		panelError.add(this.messageError);
		panelStart.add(btnStart, BorderLayout.CENTER);
	}

	private void drawButton() {
		btnClose = new PicturedButtonHover(Images.CLOSE, Images.CLOSE_HOVER);
		btnReduce = new PicturedButtonHover(Images.REDUCE, Images.REDUCE_HOVER);
		btnStart = new PicturedButtonHover(Images.START, Images.START);
		if(this.isFullScreen()) {
			btnScreen = new PicturedButtonHover(Images.DOUBLE_SCREEN, Images.DOUBLE_SCREEN_HOVER);
		} else {
			btnScreen = new PicturedButtonHover(Images.SIMPLE_SCREEN, Images.SIMPLE_SCREEN_HOVER);
		}
		
		btnClose.resizeIcon(35,20);
		btnScreen.resizeIcon(35,20);
		btnReduce.resizeIcon(35,20);
		btnStart.resizeIcon(60,60);
		
		btnStart.setEnabled(false);
	}
	
	public Frame getFrame() {
		return (JFrame) SwingUtilities.getWindowAncestor(this);
	}

	private void drawTextFields() {
		this.user = new TextFieldPlaceHolderMovable();
		this.password = new PasswordField();
		
		this.user.setBorder(new RoundedCornerBorderTightEmpty());
		this.password.setBorder(new RoundedCornerBorderTightEmpty());
		
		this.user.setFont(CharteGraphique.BOLD_TEXT);
		this.password.setFont(CharteGraphique.BOLD_TEXT);
		
		this.user.setForeground(CharteGraphique.GRAY_DARK.darker().darker());
		this.password.setForeground(CharteGraphique.GRAY_DARK.darker().darker());
		
		this.user.setBackground(CharteGraphique.GRAY_DARK);
		this.password.setBackground(CharteGraphique.GRAY_DARK);
		
		this.user.setLabelText("NOM D'UTILISATEUR");
		this.password.setLabelText("MOT DE PASSE");
		
		this.user.setPreferredSize(new Dimension(0,55));
		this.password.setPreferredSize(new Dimension(0,55));
		
		this.user.addCaretListener(controleur);
		this.password.addCaretListener(controleur);
		
		this.password.addFocusListener(controleur);
		this.error = false;
		
		this.password.addKeyListener(controleur);
		this.user.addKeyListener(controleur);
	}
	
	public void setFullScreen() {
		this.setIconFullScreen();
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		this.getFrame().setSize(new Dimension(width, height));
		this.getFrame().setLocation(0, 0);
	}
	
	public void setIconFullScreen() {
		this.btnScreen.setActionCommand("minScreen");
		this.btnScreen.changeIcons(Images.DOUBLE_SCREEN, Images.DOUBLE_SCREEN_HOVER);
	}
	
	public void setIconMinScreen() {
		this.btnScreen.setActionCommand("fullScreen");
		this.btnScreen.changeIcons(Images.SIMPLE_SCREEN, Images.SIMPLE_SCREEN_HOVER);
	}
	
	public void setMinScreen() {
		this.setIconMinScreen();
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		this.getFrame().setSize(new Dimension(1280, 720));
		this.getFrame().setLocation((width - this.getFrame().getWidth())/2, (height - this.getFrame().getHeight())/2);
	}
	
	
	private void drawImagesPanel() {
		this.panelPicture = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics grphcs) {
				super.paintComponent(grphcs);
				Graphics2D g2D;
				g2D = (Graphics2D) grphcs;
				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				ImageIcon image = new ImageIcon(getClass().getResource("/pictures/img/identification.png"));
				Image identification = image.getImage();
				AffineTransform aTran = new AffineTransform();
				float width = getWidth();
				float facteurDeReduction = width / 1100;
				aTran.scale(facteurDeReduction, facteurDeReduction);
				aTran.translate(-200, 0);
				g2D.drawImage(identification, aTran, this);
			}
		};
		
		this.panelLogo = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics grphcs) {
				super.paintComponent(grphcs);
				Graphics2D g2D;
				g2D = (Graphics2D) grphcs;
				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				ImageIcon image = new ImageIcon(getClass().getResource("/pictures/img/white_logo.png"));
				Image identification = image.getImage();
				AffineTransform aTran = new AffineTransform();
				float facteurDeReduction = 1.0F;
				aTran.translate((this.getWidth() - identification.getWidth(null))/2, 0);
				aTran.scale(facteurDeReduction, facteurDeReduction);
				g2D.drawImage(identification, aTran , null);
			}
		};
		
		this.panelError = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics grphcs) {
				super.paintComponent(grphcs);
				Graphics2D g2D;
				g2D = (Graphics2D) grphcs;
				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				int width = this.getWidth();
				int height = this.getHeight();
				int middle = (int)width/2;
				g2D.setPaint(CharteGraphique.VIOLET_DARK);
				g2D.fillRoundRect(0, 0, width, height - 10, 25, 25);
				
				// dessin de la fleche
				int xTriangle [] = {middle - 10, middle + 10, middle};
				int yTriangle [] = {height - 10, height - 10, height};
				Polygon triangle = new Polygon(xTriangle, yTriangle, xTriangle.length);
				g2D.fillPolygon(triangle);
			}
		};
		panelError.setPreferredSize(new Dimension(10,100));
		panelError.setLayout(new BorderLayout());
		panelError.setOpaque(false);
	}

	private void drawPanels() {
		setLayout(new BorderLayout());
		panelIdentification.setLayout(new BorderLayout());
		panelBtnActions.setLayout(new BorderLayout());
		panelActions.setLayout(new BorderLayout());
		panelPicture.setLayout(new BorderLayout());
		
		panelIdentification.setPreferredSize(new Dimension(400, 0));
		panelActions.setPreferredSize(new Dimension(0, 25));
		panelLogo.setPreferredSize(new Dimension(10,200));
		panelStart.setPreferredSize(new Dimension(10, 70));
		
		panelIdentification.setBackground(CharteGraphique.WHITE);
		panelConnexion.setBackground(CharteGraphique.WHITE);
		
		panelIdentification.setBorder(new EmptyBorder(50, 50, 100, 50));

		panelLogo.setOpaque(false);
		panelActions.setOpaque(false);
		panelBtnActions.setOpaque(false);
		panelConnexion.setOpaque(false);
		panelStart.setOpaque(false);
		
		panelConnexion.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("default:grow"), },
				new RowSpec[] {
						RowSpec.decode("20px"),
						FormSpecs.DEFAULT_ROWSPEC,
						RowSpec.decode("20px"),
						FormSpecs.DEFAULT_ROWSPEC,
						RowSpec.decode("10px"),
						FormSpecs.DEFAULT_ROWSPEC}));
	}

	public void fermerVue(JPanel vueActuelle, JPanel nouvelleVue) {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(vueActuelle);
		if (frame != null) {
			frame.getContentPane().removeAll();
			frame.getContentPane().add(nouvelleVue);
			frame.revalidate();
			frame.repaint();
		}
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
	
	public void setButtonStartEnable(boolean enable) {
		btnStart.setEnabled(enable);
		if(enable) {
			btnStart.changeIcons(Images.START_OK, Images.START_OK_HOVER);
		} else {
			btnStart.changeIcons(Images.START, Images.START);
		}
	}
	
	@SuppressWarnings("deprecation")
	public Administrateur getUser() {
		return new Administrateur(this.user.getText(), this.password.getText());
	}
	
	public void showMessageError(String message) {
		this.user.setFocusBackgroundColor(CharteGraphique.VIOLET_LIGHT);
		this.user.setFocusBorderColor(CharteGraphique.VIOLET);
		this.user.setFocusPlaceHolderColor(CharteGraphique.VIOLET_DARK);
		this.user.requestFocus();
		this.password.setBackground(CharteGraphique.VIOLET_LIGHT);
		this.resetPassword();
		this.messageError.setText(message);
		
		panelConnexion.remove(this.connexion);
		panelConnexion.add(this.panelError, "2, 2, 2, 1, fill, default");
		
		this.error = true;

		this.validate();
		this.repaint();
	}
	
	public void changePasswordBackground(Color back) {
		this.password.setBackground(back);
	}

	public void hideMessageError() {
		this.user.setBackground((CharteGraphique.WHITE));
		this.user.setBorder(new RoundedCornerBorderTightPlain(CharteGraphique.BLACK));
		this.user.setFocusPlaceHolderColor(CharteGraphique.GRAY.darker().darker());
		
		panelConnexion.remove(this.panelError);
		panelConnexion.add(this.connexion, "2, 2, 2, 1, fill, default");
		this.validate();
		this.repaint();
		this.error = false;
	}
	
	public boolean isError() {
		return this.error;
	}
	
	public void resetPassword() {
		this.password.setText("");
	}

	public void resetIdentifiants() {
		this.user.setText("");
		this.password.setText("");
	}

	public boolean isStartEnable() {
		return this.btnStart.isEnabled();
	}


}
