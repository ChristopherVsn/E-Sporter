package composant.textfield;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;

import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import ressources.CharteGraphique;
import ressources.Images;

/**
 * Un TextField permettant la saisie de mot de passe avec possibilité de rendre
 * le mot de passe visible ou non
 * 
 * @author Vivien
 *
 */
public class PasswordField extends JPasswordField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return le texte d'indication en placeHolder du
	 *         <code>PasswordField</code>
	 */
	public String getLabelText() {
		return labelText;
	}

	/**
	 * Défini le texte d'indication du <code>PasswordField</code>
	 * @param labelText le texte d'indication.
	 */
	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}

	private final Animator animator;
	private boolean animateHinText = true;
	private float location;
	private boolean show;
	private String labelText = "";
	private final Image eye;
	private final Image eye_hide;
	private boolean hide;
	private boolean selected;

	public PasswordField() {
		setBorder(new EmptyBorder(20, 3, 10, 30));
		setSelectionColor(new Color(76, 204, 255));
		this.selected = false;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				int x = PasswordField.this.getWidth() - 50;
				if (me.getX() > x && PasswordField.this.selected) {
					hide = !hide;
					if (hide) {
						setEchoChar('●');
					} else {
						setEchoChar((char) 0);
					}
				}
				repaint();
			}
		});

		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent fe) {
				showing(false);
				PasswordField.this.setBackground(CharteGraphique.WHITE);
				PasswordField.this.setForeground(CharteGraphique.BLACK);
				PasswordField.this.setBorder(new RoundedCornerBorderTightPlain(CharteGraphique.BLACK));
				PasswordField.this.selected = true;
			}

			@Override
			public void focusLost(FocusEvent fe) {
				showing(true);
				PasswordField.this.setBackground(CharteGraphique.GRAY_DARK);
				PasswordField.this.setForeground(CharteGraphique.GRAY_DARK.darker().darker());
				PasswordField.this.setBorder(new RoundedCornerBorderTightEmpty());
				PasswordField.this.selected = false;
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent me) {
				int x = PasswordField.this.getWidth() - 50;
				if (me.getX() > x && PasswordField.this.selected) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				} else {
					setCursor(new Cursor(Cursor.TEXT_CURSOR));
				}

			}
		});

		TimingTarget target = new TimingTargetAdapter() {
			@Override
			public void begin() {
				animateHinText = String.valueOf(getPassword()).equals("");
			}

			@Override
			public void timingEvent(float fraction) {
				location = fraction;
				repaint();
			}

		};

		eye = Images.EYE_OPEN.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		eye_hide = Images.EYE_CLOSED.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		animator = new Animator(300, target);
		hide = true;
		animator.setResolution(0);
		animator.setAcceleration(0.5f);
		animator.setDeceleration(0.5f);
	}

	private void showing(boolean action) {
		if (animator.isRunning()) {
			animator.stop();
		} else {
			location = 1;
		}
		animator.setStartFraction(1f - location);
		show = action;
		location = 1f - location;
		animator.start();
	}

	@Override
	public void paint(Graphics grphcs) {
		super.paint(grphcs);
		Graphics2D g2 = (Graphics2D) grphcs;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		createHintText(g2);
		if (this.selected) {
			createShowHide(g2);
		}
		g2.dispose();
	}

	private void createShowHide(Graphics2D g2) {
		int x = this.getWidth() - 40;
		int y = (this.getHeight() - 30) / 2;
		g2.drawImage(hide ? this.eye_hide : this.eye, x, y, null);
	}

	private void createHintText(Graphics2D g2) {
		Insets in = getInsets();
		g2.setColor(CharteGraphique.GRAY_DARK.darker().darker());
		FontMetrics ft = g2.getFontMetrics();
		Rectangle2D r2 = ft.getStringBounds(labelText, g2);
		double height = getHeight() - in.top - in.bottom;
		double textY = (height - r2.getHeight()) / 2;
		double size = 0;
		double side = in.left + 15;
		boolean select = this.getBorder() instanceof RoundedCornerBorderTightPlain;
		boolean text = !(this.getPassword().length == 0);
		if (animateHinText) {
			if (show) {
				size += 17 * (1 - location);
				side = side - (15 * (1 - location));
			} else {
				size += 17 * location;
				side = side - (15 * location);
			}
		} else {
			size = 17;
			side = side - 15;
		}
		if (!select && !text) {
			g2.drawString(labelText, (int) side, (int) (in.top + textY + ft.getAscent() - size - 5));
		} else {
			g2.drawString(labelText, (int) side, (int) (in.top + textY + ft.getAscent() - size));
		}
	}

	@Override
	public void setText(String string) {
		if (!String.valueOf(getPassword()).equals(string)) {
			showing(string.equals(""));
		}
		super.setText(string);
	}

}