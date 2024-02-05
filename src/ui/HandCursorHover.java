package ui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HandCursorHover extends MouseAdapter {
	
	@Override
	public void mouseEntered(MouseEvent e) {
		Component com = (Component)e.getSource();
		com.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Component com = (Component)e.getSource();
		com.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
}
