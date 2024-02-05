package composant.buttons;

import javax.swing.ImageIcon;

public class PicturedButtonHover extends PicturedButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon hover;
	private ImageIcon base;
	private PicturedButtonHoverControleur controleur = new PicturedButtonHoverControleur(this);

	/**
	 * Créer un nouveau <code>PicturedButtonHover</code>
	 * @param img l'image de base du <code>PicturedButtonHover</code>
	 * @param hover l'image du <code>PicturedButtonHover</code> lorsqu'il est survolé par la souris.
	 */
	public PicturedButtonHover(ImageIcon img, ImageIcon hover) {
		super(img);
		this.hover = hover;
		this.base = img;
		this.addMouseListener(controleur);
	}
	
	public void setHover(boolean hover) {
		if(hover) {
			this.changeIcon(this.hover);
		} else {
			this.changeIcon(this.base);
		}
	}
	
	public void changeIcons(ImageIcon base, ImageIcon hover) {
		this.changeIcon(base);
		this.base = base;
		this.hover = hover;
	}
}
