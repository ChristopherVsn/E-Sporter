package ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

public class KamabokoBorder extends RoundedCornerBorder {

	  @Override public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	    Graphics2D g2 = (Graphics2D) g.create();
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    double r = 14d;
	    double w = width ;
	    double h = height - 1;

	    Path2D p = new Path2D.Double();
	    p.moveTo(x, y + h);
	    p.lineTo(x, y + r);
	    p.quadTo(x, y, x + r, y);
	    p.lineTo(x + w - r, y);
	    p.quadTo(x + w, y, x + w, y + r);
	    p.lineTo(x + w, y + h);
	    p.closePath();
	    Area round = new Area(p);

	    Container parent = c.getParent();
	    if (Objects.nonNull(parent)) {
	      g2.setPaint(parent.getBackground());
	      Area corner = new Area(new Rectangle2D.Double(x - 0.5 , y - 0.5, width + 1, height + 1));
	      corner.subtract(round);
	      g2.fill(corner);
	    }

	    g2.setPaint(c.getForeground());
	    g2.draw(round);
	    g2.dispose();
	  }
	}