package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTable;

public class DeletePanel extends JPanel{
	
	DeleteButton btn;
	private ButtonTableActionListener action;
	
	public DeletePanel(ButtonTableActionListener action) {
		this.btn = new DeleteButton();
		this.setLayout(new BorderLayout());
		this.add(btn, BorderLayout.EAST);
		this.action = action;
	}

	public void initEvent(int row, JTable table) {
		this.action.setTable(table);
		this.action.setRow(row);
		this.btn.addActionListener(this.action);
	}
}