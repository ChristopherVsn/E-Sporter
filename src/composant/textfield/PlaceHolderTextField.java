package composant.textfield;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;

import ressources.CharteGraphique;

public class PlaceHolderTextField extends JTextField {
	
	private static final long serialVersionUID = 1L;
	private String placeHolderText;
	private PlaceHolder listener;
	
	public PlaceHolderTextField(String placeHolder) {
		super(placeHolder);
		this.placeHolderText = placeHolder;
		this.initComponent();
	}
	
	public void setPlaceHolderColors(Color hover, Color text) {
		this.setForeground(hover);
		this.removeFocusListener(this.listener);
		this.listener = new PlaceHolder(this.placeHolderText, hover, text);
		this.addFocusListener(this.listener);
	}
	
	private void initComponent() {
		this.listener = new PlaceHolder(this.placeHolderText, CharteGraphique.GRAY_LIGHT, CharteGraphique.BLACK);
		this.addFocusListener(this.listener);
		this.setFont(CharteGraphique.PLACEHOLDER_TEXT);
		this.setForeground(CharteGraphique.GRAY_LIGHT);
	}
	
	public boolean isEmpty() {
		return (this.getText().equals(this.placeHolderText) && this.getFont().equals(CharteGraphique.PLACEHOLDER_TEXT));
	}
}
