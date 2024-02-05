package ui;

import javax.swing.JComboBox;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class ComboBoxControler implements PopupMenuListener {

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		JComboBox<?> j = (JComboBox<?>)e.getSource();
		j.setBorder(new RoundedCornerBorder());
	}
	
	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		JComboBox<?> j = (JComboBox<?>)e.getSource();
		j.setBorder(new KamabokoBorder());
	}

	@Override
	public void popupMenuCanceled(PopupMenuEvent e) {
	}

}
