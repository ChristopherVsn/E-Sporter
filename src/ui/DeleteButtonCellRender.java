package ui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author RAVEN
 */
public class DeleteButtonCellRender extends DefaultTableCellRenderer {
	
	private ButtonTableActionListener action;
	
	public DeleteButtonCellRender(ButtonTableActionListener action) {
		this.action = action;
	}

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSeleted, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(jtable, o, isSeleted, bln1, row, column);
        DeletePanel action = new DeletePanel(this.action);
        action.setBackground(com.getBackground());
        return action;
    }
}