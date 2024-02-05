package vue;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import DAO.ImpEquipeDAO;
import DAO.ImpTournoiDAO;
import modele.Equipe;
import modele.Tournoi;
import ressources.Images;
import ressources.Pages;
import modele.DataBase;
public class ApplicationTest {

	public static void main(String[] args) {
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
		f.setLayout(new GridLayout(1, 1));
		f.setPreferredSize(new Dimension(1280, 720));
		// f.setMinimumSize(new Dimension(1375,900));
		// f.setResizable(false);
		ImpTournoiDAO tournoi = new ImpTournoiDAO();

		Tournoi t = tournoi.getByName("TournoiA");
		ImpEquipeDAO equipe = new ImpEquipeDAO();
		Equipe e = equipe.getByName("Equipe6");

		f.add(Pages.IDENTIFICATION);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Création equipes");
		FrameDragListener frameDragListener = new FrameDragListener(f);
		f.addMouseListener(frameDragListener);
		f.addMouseMotionListener(frameDragListener);
		f.setIconImage(Images.LOGO_SHIELD.getImage());

		f.setVisible(true);
		f.pack();
	}

	public static class FrameDragListener extends MouseAdapter {

		private final JFrame frame;
		private Point mouseDownCompCoords = null;

		public FrameDragListener(JFrame frame) {
			this.frame = frame;
		}

		public void mouseReleased(MouseEvent e) {
			mouseDownCompCoords = null;
		}

		public void mousePressed(MouseEvent e) {
			mouseDownCompCoords = e.getPoint();
		}

		public void mouseDragged(MouseEvent e) {
			Point currCoords = e.getLocationOnScreen();
			frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
		}
	}
}