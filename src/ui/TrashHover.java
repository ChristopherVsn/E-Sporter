package ui;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTable;

public class TrashHover extends MouseMotionAdapter{
	
	@Override
	public void mouseMoved(MouseEvent e) {
		JTable table = (JTable) e.getSource();
		if (e.getX() > table.getWidth() - 20) {
			table.setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else {
			table.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

}
