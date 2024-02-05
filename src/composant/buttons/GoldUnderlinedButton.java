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
	
	/**
	 * Créer un nouveau <code>GoldUnderlinedButton</code> sans texte et ayant pour couleur d'underline par défaut <code>Gold</code>
	 */
	public GoldUnderlinedButton() {
		super();
		this.underline = CharteGraphique.GOLD;
		setBorder(null);
		setOpaque(false);
	}
	
	/**
	 * Créer un nouveau <code>GoldUnderlinedButton</code> avec texte et ayant pour couleur d'underline par défaut <code>Gold</code>
	 * @param text le contenu textuel du <code>GoldUnderlinedButton</code> 
	 */
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
