package ui;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTable;

public class TableIconsHover extends MouseMotionAdapter{
	
	private int size;
	
	public TableIconsHover(int size) {
		this.size = size;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		JTable table = (JTable) e.getSource();
		if (e.getX() > table.getWidth() - this.size) {
			table.setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else {
			table.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

}
