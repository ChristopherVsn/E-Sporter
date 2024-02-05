package composant.textfield;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.EmptyBorder;

/**
 * Un TextField avec les bords arrondis et un placeHolder
 * @author Vivien
 *
 */
public class RoundedTextField extends PlaceHolderTextField {

	private static final long serialVersionUID = 1L;
	private static final int RADIUS = 25;
	private Shape shape;
    
    
    /**
     * Crée un nouveau <code>RoundedTextField</code>
     * @param placeHolderText le texte en placeHolder.
     */
    public RoundedTextField(String placeHolderText) {
    	super(placeHolderText);
		this.setBorder(new EmptyBorder(5,12,5,12));
		setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
         g.setColor(getBackground());
         g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, RADIUS, RADIUS);
         super.paintComponent(g);
    }
    
    
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, RADIUS, RADIUS);
         }
         return shape.contains(x, y);
    }
}