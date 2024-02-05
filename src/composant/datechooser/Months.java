package composant.datechooser;
import java.awt.Color;
import java.awt.GridLayout;
import java.lang.reflect.Array;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public final class Months extends JPanel {

	private Event event;

	public Months() {
		initComponents();
	}

	private void addEvent() {
		for (int i = 0; i < getComponentCount(); i++) {
			((Button) getComponent(i)).setEvent(event);
		}
	}

	private void initComponents() {

		setBackground(Color.WHITE);
		setLayout(new GridLayout(4, 4));

		String[] month = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		for (int i = 0; i < Array.getLength(month); i++) {
			Button btnMonth = new Button();
			btnMonth.setBackground(Color.WHITE);
			btnMonth.setForeground(new Color(75, 75, 75));
			btnMonth.setText(month[i]);
			btnMonth.setName(Integer.toString(i + 1));
			btnMonth.setOpaque(true);
			btnMonth.addMouseListener(new ButtonHover());
			add(btnMonth);
		}
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
		addEvent();
	}
}
