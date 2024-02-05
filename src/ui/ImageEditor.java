package ui;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageEditor {

	public static ImageIcon resize(ImageIcon icon, int size) {
		Image image = icon.getImage();
		Image newimg = image.getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		return new ImageIcon(newimg);
	}
	
	public static ImageIcon resize(ImageIcon icon, int width, int height) {
		Image image = icon.getImage();
		Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		return new ImageIcon(newimg);
	}
}
