package ui;

import java.awt.event.ActionListener;
import javax.swing.JTable;

public interface ButtonTableActionListener extends ActionListener{
	
	public void setTable(JTable table);
	
	public void setRow(int row);

}
