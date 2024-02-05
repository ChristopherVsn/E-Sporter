package composant.textfield;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import ressources.CharteGraphique;
import ressources.Images;

public class TextFieldPlaceHolderMovable extends JTextField {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    private final Animator animator;
    private boolean animateHinText = true;
    private float location;
    private boolean show;
    private String labelText = "";
    private boolean hide;
    private Color background;
    private Color border;
    private Color placeHolder;

    public TextFieldPlaceHolderMovable() {
        setBorder(new EmptyBorder(20, 3, 10, 30));
        setSelectionColor(new Color(76, 204, 255));
        this.border = CharteGraphique.BLACK;
        this.background = CharteGraphique.WHITE;
        this.placeHolder = CharteGraphique.GRAY_DARK.darker().darker();
        
        
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent fe) {
                showing(false);
                TextFieldPlaceHolderMovable.this.setBackground(TextFieldPlaceHolderMovable.this.background);
                TextFieldPlaceHolderMovable.this.setForeground(CharteGraphique.BLACK);
                TextFieldPlaceHolderMovable.this.setBorder(new RoundedCornerBorderTightPlain(TextFieldPlaceHolderMovable.this.border));
            }

            @Override
            public void focusLost(FocusEvent fe) {
                showing(true);
                TextFieldPlaceHolderMovable.this.setBackground(CharteGraphique.GRAY_DARK);
                TextFieldPlaceHolderMovable.this.setForeground(CharteGraphique.GRAY_DARK.darker().darker());
                TextFieldPlaceHolderMovable.this.setBorder(new RoundedCornerBorderTightEmpty());
                TextFieldPlaceHolderMovable.this.setFocusBackgroundColor(CharteGraphique.WHITE);
                TextFieldPlaceHolderMovable.this.setFocusBorderColor(CharteGraphique.BLACK);
                TextFieldPlaceHolderMovable.this.placeHolder = CharteGraphique.GRAY_DARK.darker().darker();
            }
        });
        
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                animateHinText = String.valueOf(getText()).equals("");
            }

            @Override
            public void timingEvent(float fraction) {
                location = fraction;
                repaint();
            }

        };
        
        animator = new Animator(300, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
    }
    
    public void setFocusBackgroundColor(Color background) {
    	this.background = background;
    }
    
    public void setFocusBorderColor(Color border) {
    	this.border = border;
    }
    
    public void setFocusPlaceHolderColor(Color placeHolder) {
    	this.placeHolder = placeHolder;
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
        g2.dispose();
    }


    private void createHintText(Graphics2D g2) {
        Insets in = getInsets();
        g2.setColor(this.placeHolder);
        FontMetrics ft = g2.getFontMetrics();
        Rectangle2D r2 = ft.getStringBounds(labelText, g2);
        double height = getHeight() - in.top - in.bottom;
        double textY = (height - r2.getHeight()) / 2;
        double size = 0;
        double side = in.left + 15;
        boolean select = this.getBorder() instanceof RoundedCornerBorderTightPlain;
        boolean text = !(this.getText().isEmpty());
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
        if(!select && ! text) {
        	g2.drawString(labelText, (int)side, (int) (in.top + textY + ft.getAscent() - size - 5));
        } else {
            g2.drawString(labelText, (int)side, (int) (in.top + textY + ft.getAscent() - size));
        }
    }


    @Override
    public void setText(String string) {
        if (!String.valueOf(getText()).equals(string)) {
            showing(string.equals(""));
        }
        super.setText(string);
    }
}
