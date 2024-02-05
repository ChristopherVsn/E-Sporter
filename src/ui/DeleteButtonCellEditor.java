package ui;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;

/**
 *
 * @author RAVEN
 */

public class DeleteButtonCellEditor extends DefaultCellEditor {
	
	private ButtonTableActionListener action;

    public DeleteButtonCellEditor(ButtonTableActionListener action) {
        super(new JCheckBox());
		this.action = action;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        DeletePanel panel = new DeletePanel(this.action);
        panel.initEvent(row, jtable);
        panel.setBackground(jtable.getSelectionBackground());
        return panel;
    }
}