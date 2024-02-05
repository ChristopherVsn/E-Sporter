package composant.buttons;

import java.awt.Color;
import java.awt.Graphics;

import ressources.CharteGraphique;

public class GoldUnderlinedButton extends UnpaintedButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color underline;
	
	public GoldUnderlinedButton() {
		super();
		this.underline = CharteGraphique.GOLD;
		setBorder(null);
		setOpaque(false);
	}
	
	public GoldUnderlinedButton(String text) {
		super(text);
		this.underline = CharteGraphique.GOLD;
	}

	@Override
	protected void paintComponent(Graphics g) {
        g.setColor(this.underline);
        g.fillRect(0, getHeight() - 1, getWidth(), 1);
        super.paintComponent(g);
	}
	
	@Override
	public void setEnabled(boolean enable) {
		super.setEnabled(enable);
		if(enable) {
			this.underline = CharteGraphique.GOLD;
		} else {
			this.underline = Color.GRAY;
		}
	}
}
