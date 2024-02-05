package ui;

import java.awt.Component;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author RAVEN
 */
public class ListButtonCellRender extends DefaultTableCellRenderer {
	
	private List<ButtonTableType> btns;

	
	public ListButtonCellRender(List<ButtonTableType> btns) {
		this.btns = btns;
	}

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSeleted, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(jtable, o, isSeleted, bln1, row, column);
       // jtable.getModel().getCo
        jtable.addMouseMotionListener(new TableIconsHover(this.btns.size() * 30));
        PanelButtons action = new PanelButtons(jtable, this.btns);
        action.setBackground(com.getBackground());
        jtable.setValueAt(action, row, column);
        return action;
    }
}