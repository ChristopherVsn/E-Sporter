package composant.textfield;

import java.awt.Graphics;

import ressources.CharteGraphique;

public class GoldUnderlinedTextField extends PlaceHolderTextField{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GoldUnderlinedTextField(String placeHolder) {
		super(placeHolder);
		setOpaque(false);
		setBorder(null);
		setPlaceHolderColors(CharteGraphique.GRAY_LIGHT, CharteGraphique.WHITE);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
        g.setColor(CharteGraphique.GOLD);
        g.fillRect(0, getHeight() - 1, getWidth(), 1);
        super.paintComponent(g);
	}
}