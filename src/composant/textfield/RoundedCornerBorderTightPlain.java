package composant.textfield;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Objects;

import javax.swing.border.AbstractBorder;

import ressources.CharteGraphique;

/**
 * Une classe qui extend la classe <code>AbstractBorder</code> déssinant un bord solid, arrondi, léger pour son component
 * @author Vivien
 *
 */
public class RoundedCornerBorderTightPlain extends AbstractBorder{
	
	private Color border;
	
	/**
	 * Crée un nouveau <code>RoundedCornerBorderTightPlain</code>
	 * @param border la couleur du bord.
	 */
	public RoundedCornerBorderTightPlain(Color border) {
		this.border = border;
	}
	
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int r = 10;
		Area round = new Area(new RoundRectangle2D.Double(x, y, width, height, r, r));
		Container parent = c.getParent();
		if (Objects.nonNull(parent)) {
			g2.setPaint(parent.getBackground());
			Area corner = new Area(new Rectangle2D.Double(x - 1, y - 1, width + 2, height + 2));
			corner.subtract(round);
			g2.fill(corner);
		}
		Area border = new Area(new RoundRectangle2D.Double(x + 2, y + 2, width - 4, height - 4, r - 2, r - 2));
		round.subtract(border);
		g2.setPaint(this.border);
		g2.fill(round);
		g2.dispose();
	}
	
	public void setBorderColor(Color border) {
		this.border = border;
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(15, 12, 0, 8);
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.set(15, 12, 0, 8);
		return insets;
	}

}
