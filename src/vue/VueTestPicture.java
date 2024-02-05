package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import composant.textfield.TextFieldPlaceHolderMovable;

public class VueTestPicture extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextFieldPlaceHolderMovable btnTest;
	
	public VueTestPicture() {
		setBackground(Color.RED);
	}
	
//	@Override
//	protected void paintComponent(Graphics grphcs) {
//		super.paintComponent(grphcs);
//		Graphics2D g2d = (Graphics2D) grphcs;
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		g2d.setPaint(Color.RED);
//		
//
//		// Remplissage du panel avec ce dégradé
//
//
//		// Création de la forme de remplissage du panel pour ce dégradé
//		Polygon p = new Polygon();
//		p.addPoint(0, 50);
//		p.addPoint(0, getHeight());
//		p.addPoint(getWidth() - 50, getHeight());
//		p.addPoint(getWidth() - 50, 50);
//
//		
//		Ellipse2D.Double E=new Ellipse2D.Double(getWidth() - 100,0,100,100);
//		Area t = new Area(p);
//		Area e = new Area(E);
//		t.add(e);
//		
//		
//
//		// Remplissage de la forme suivant jonction du premier dégradé, avec le second
//		// dégradé
//		g2d.fill(t);
//	}
	
	
	public static void main(String [] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (UnsupportedLookAndFeelException ignored) {
			Toolkit.getDefaultToolkit().beep();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			ex.printStackTrace();
			return;
		}
		JFrame f = new JFrame();
		f.setUndecorated(true);
		f.setShape(getShape());
		f.getContentPane().setLayout(new GridLayout(1, 1));
		f.setPreferredSize(new Dimension(500,500));
		f.getContentPane().add(new VueTestPicture());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		//f.setBackground(CharteGraphique.EMPTY);
		f.pack();
	}
	
	public static Area getShape() {
		Polygon p = new Polygon();
		p.addPoint(0, 20);
		p.addPoint(0, 200);
		p.addPoint(350 - 20, 200);
		p.addPoint(350 - 20, 20);

		
		Ellipse2D.Double E=new Ellipse2D.Double(350 - 40,0,40,40);
		Area t = new Area(p);
		Area e = new Area(E);
		t.add(e);
		return t;
	}
}
