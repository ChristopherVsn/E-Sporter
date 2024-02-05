package vue;

import javax.swing.JPanel;
import modele.Tournoi;

public class VueSelectionVainqueurMatch extends JPanel {

    /**
     * Create the panel.
     */
    private Tournoi t;

    public VueSelectionVainqueurMatch(Tournoi t) {
        this.t = t;
    }

    public Tournoi getTournoi() {
        return this.t;
    }
}
