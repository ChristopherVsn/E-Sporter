package ui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class TableMouseRender extends MouseMotionAdapter {

	private int column;

	public TableMouseRender(int column) {
		this.column = column;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		JTable table = (JTable) e.getSource();
		Point point = e.getPoint();
		int colum = table.columnAtPoint(point);
		int row = table.rowAtPoint(point);

		if (colum == this.column) {
			table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else {
			table.setCursor(Cursor.getDefaultCursor());
		}
	}

}
