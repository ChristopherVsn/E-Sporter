package composant.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.EmptyBorder;

import ressources.CharteGraphique;

public class BorderedTransparentButton extends UnpaintedButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color couleur;
	
	/**
	 * Crée un nouveau <code>BorderedTransparentButton</code> ayant pour texte celui passé en paramètres et pour couleur par défaut <code>WHITE</code>
	 * @param text le contenu du <code>BorderedTransparentButton</code>
	 */
	public BorderedTransparentButton(String text) {
		super(text);
		initComponent();
	}
	
	/**
	 * Crée un nouveau <code>BorderedTransparentButton</code> sans texte et pour couleur par défaut <code>WHITE</code>
	 */
	public BorderedTransparentButton() {
		super();
		initComponent();
		
	}
	
	/**
	 * Change la couleur du <code>BorderedTransparentButton</code>
	 * @param color la couleur du <code>BorderedTransparentButton</code>
	 */
	public void setColor(Color color) {
		this.couleur = color;
		this.setBackground(color);
		this.setForeground(color);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Area corner = new Area(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
		Area round = new Area(new RoundRectangle2D.Double(3, 3, getWidth() - 6, getHeight() - 6, 25, 25));
		corner.subtract(round);
		g2d.setColor(getBackground());
		g2d.fill(corner);
		g2d.dispose();
	}
	
	@Override
	public void setEnabled(boolean enable) {
		super.setEnabled(enable);
		if(enable) {
			this.setColor(this.couleur);
		} else {
			this.setBackground(Color.GRAY);
		}
	}
	
	/**
	 * Défini les bords et la couleur par défaut du <code>BorderedTransparentButton</code>
	 */
	private void initComponent() {
		this.setBorder(new EmptyBorder(8,12,3,12));
		this.setColor(CharteGraphique.WHITE);
	}
}
