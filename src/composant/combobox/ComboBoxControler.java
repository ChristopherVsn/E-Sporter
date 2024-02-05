package composant.combobox;

import javax.swing.JComboBox;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * Une classe écoutant les popup menus des objets <code>JComboBox</code> 
 * afin d'en changer leur Border selon l'état du popup-menu. 
 * Lorsque le Popup-menu est visible, le border devient un <code>KamabokoBorder</code>
 * sinon il devient un <code>RoundedCornerBorder</code>
 * @author Vivien
 *
 */
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
