package ui;

import java.awt.Component;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author RAVEN
 */

public class ListButtonCellEditor extends DefaultCellEditor {
	
	private List<ButtonTableType> btns;


    public ListButtonCellEditor(List<ButtonTableType> btns) {
        super(new JCheckBox());
		this.btns = btns;

    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        PanelButtons panel = new PanelButtons(jtable, this.btns);
        panel.initEvent(row);
        panel.setBackground(jtable.getSelectionBackground());
        jtable.setValueAt(panel, row, column);
        return panel;
    }
}