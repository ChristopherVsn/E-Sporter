package composant.datechooser;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;

@SuppressWarnings("serial")
public final class Years extends javax.swing.JPanel {

    private Event event;
    private int startYear;

    public Years() {
        initComponents();
    }

    public int showYear(int year) {
        year = calculateYear(year);
        for (int i = 0; i < getComponentCount(); i++) {
            JButton cmd = (JButton) getComponent(i);
            cmd.setText(year + "");
            year++;
        }
        return startYear;
    }

    private int calculateYear(int year) {
        year -= year % 10;
        startYear = year;
        return year;
    }

    private void addEvent() {
        for (int i = 0; i < getComponentCount(); i++) {
            ((Button) getComponent(i)).setEvent(event);
        }
    }


    private void initComponents() {
    	
        setBackground(Color.WHITE);
        setLayout(new GridLayout(5, 4));
        
        for(int i = 0; i < 20; i++) {
        	Button btnYear = new Button();
        	btnYear.setBackground(Color.WHITE);
        	btnYear.setForeground(new Color(75, 75, 75));
        	btnYear.setName("year");
        	btnYear.setOpaque(true);
        	add(btnYear);
        }
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
        addEvent();
    }

    public int next(int year) {
        showYear(year + 20);
        return startYear;
    }

    public int back(int year) {
        showYear(year - 20);
        return startYear;
    }
}
