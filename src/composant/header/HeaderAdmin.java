package composant.header;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class HeaderAdmin extends Header{
	
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
          } catch (UnsupportedLookAndFeelException ignored) {
            Toolkit.getDefaultToolkit().beep();
          } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
            return;
          }

        JFrame f = new JFrame();
        f.getContentPane().setLayout(new GridLayout(1, 1));
        f.setPreferredSize(new Dimension(900, 640));
        //f.setMinimumSize(new Dimension(1375,900));
        //f.setResizable(false);
        f.getContentPane().add(new Header());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Création equipes");
        f.setVisible(true);
        f.pack();
    }
	
	public HeaderAdmin() {
		
	}

}
