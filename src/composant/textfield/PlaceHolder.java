package composant.textfield;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import ressources.CharteGraphique;

public class PlaceHolder implements FocusListener{
	
	private String text;
	private Color foreground;
	private Color hover;
	
	public PlaceHolder(String text, Color hover, Color foreground) {
		this.text = text;
		this.hover = hover;
		this.foreground = foreground;
	}

	@Override
	public void focusGained(FocusEvent e) {
		JTextField tf = (JTextField)e.getSource();
		if(tf.getText().equals(this.text)) {
			tf.setFont(CharteGraphique.TEXT);
			tf.setForeground(this.foreground);
			tf.setText("");
		}
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		JTextField tf = (JTextField)e.getSource();
		if(tf.getText().equals("")) {
			tf.setFont(CharteGraphique.PLACEHOLDER_TEXT);
			tf.setForeground(this.hover);
			tf.setText(this.text);
		}
	}
}
