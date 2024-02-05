package composant.buttons;

import java.awt.Image;

import javax.swing.ImageIcon;

public class PicturedButton extends UnpaintedButton {

	private static final long serialVersionUID = 1L;
	private ImageIcon img;
	private int width;
	private int height;


	/**
	 * Créer un <code>PicturedButton</code> ayant pour image celle passée en paramètre et pour dimensions d'image de base de 50px de large pour 50px de haut.
	 * @param img l'image du <code>PicturedButton</code> qui apparaitra visuellement pour représenter le boutton.
	 */
	public PicturedButton(ImageIcon img) {
		this.img = img;
		this.setIcon(img);
		this.width = 50;
		this.height = 50;
	}

	/**
	 * Redimensionne la taille de l'image dans le <code>PicturedButton</code> pour une dimension carré.
	 * @param size la nouvelle hauteur largeur que vous voulez donner à l'image du boutton.
	 */
	public void resizeIcon(int size) {
		this.width = size;
		this.height = size;
		Image image = this.img.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
		ImageIcon newImg = new ImageIcon(image);
		this.setIcon(newImg);
	}

	/**
	 * Redmiensionne la taille de l'image dans le <code>PicturedButton</code> selon les dimensions souhaitée
	 * @param width la nouvelle largeur de l'image du <code>PicturedButton</code>
	 * @param height la nouvelle hauteur du <code>PicturedButton</code>
	 */
	public void resizeIcon(int width, int height) {
		this.width = width;
		this.height = height;
		Image image = this.img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon newImg = new ImageIcon(image);
		this.setIcon(newImg);
	}
	
	/**
	 * Actualise la taille de l'image du <code>PicturedButton</code> en fonctions des attributs <code>width</code> et <code>height</code> du <code>PicturedButton</code>
	 */
	private void resizeIcon() {
		Image image = this.img.getImage().getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH);
		ImageIcon newImg = new ImageIcon (image);
		this.setIcon(newImg);
	}
	
	
	/**
	 * Change l'image du <code>PicturedButton</code>
	 * @param img la nouvelle image du <code>PicturedButton</code>
	 */
	public void changeIcon(ImageIcon img) {
		this.img = img;
		this.resizeIcon();
	}
	
	/**
	 * Permet de récupérer l'image en cours du <code>PicturedButton</code>
	 * @return 
	 * L'image de base utilisée pour créer le <code>PicturedButton</code> ou l'image utilisée lors du dernier appel à la méthode <code>changeIcon</code> si cet appel a eu lieu.
	 */
	public ImageIcon getIconBase() {
		return this.img;
	}

}





























