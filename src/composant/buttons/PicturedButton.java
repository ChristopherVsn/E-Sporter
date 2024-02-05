package composant.buttons;

import java.awt.Image;

import javax.swing.ImageIcon;

public class PicturedButton extends UnpaintedButton {

	private static final long serialVersionUID = 1L;
	private ImageIcon img;
	private int width;
	private int height;


	public PicturedButton(ImageIcon img) {
		this.img = img;
		this.setIcon(img);
		this.width = 50;
		this.height = 50;
	}

	public void resizeIcon(int size) {
		this.width = size;
		this.height = size;
		Image image = this.img.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
		ImageIcon newImg = new ImageIcon(image);
		this.setIcon(newImg);
	}

	public void resizeIcon(int width, int height) {
		this.width = width;
		this.height = height;
		Image image = this.img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon newImg = new ImageIcon(image);
		this.setIcon(newImg);
	}
	
	private void resizeIcon() {
		Image image = this.img.getImage().getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH);
		ImageIcon newImg = new ImageIcon (image);
		this.setIcon(newImg);
	}
	
	public void changeIcon(ImageIcon img) {
		this.img = img;
		this.resizeIcon();
	}
	
	public ImageIcon getIconBase() {
		return this.img;
	}

}





























