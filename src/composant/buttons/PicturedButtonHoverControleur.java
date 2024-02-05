package composant.buttons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PicturedButtonHoverControleur implements MouseListener{
	
	private PicturedButtonHover btn;

	/**
	 * Crée un controleur pour <code>PicturedButtonHover</code> permettant la détection du survol de la souris
	 * @param picturedButtonHover le <code>PicturedButtonHover</code> cible
	 */
	public PicturedButtonHoverControleur(PicturedButtonHover picturedButtonHover) {
		this.btn = picturedButtonHover;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.btn.setHover(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.btn.setHover(false);
	}

}
