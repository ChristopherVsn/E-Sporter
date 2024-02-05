package composant.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

import composant.buttons.PicturedButton;
import modele.ModeleSelectionVainqueurMatch;
import vue.VueMatchsPouleArbitre;

/**
 * Classe permettant d'écouter les objets <code>CoupleStar</code> afin de lier
 * le clique sur un <code>CoupleStar</code> à une sélection d'un vainqueur de
 * match représenté par le CoupleStar.
 * 
 * @author Vivien
 *
 */
public class CoupleStarActionListener implements ActionListener {

	private int idMatch;
	private CoupleStar couple;
	private JTable table;
	private ModeleSelectionVainqueurMatch modele;
	private VueMatchsPouleArbitre vue;

	/**
	 * Crée un controleur pour <code>CoupleStar</code>
	 * 
	 * @param idMatch l'id du match représenté par le <code>CoupleStar</code>
	 * @param couple  le <code>CoupleStar</code> représentant physiquement le match
	 *                dans la table
	 * @param table   la table présentant les <code>CoupleStar</code>
	 * @param vue     la vue ou se trouvant la Table présentant les
	 *                <code>CoupleStar</code>
	 */
	public CoupleStarActionListener(int idMatch, CoupleStar couple, JTable table, VueMatchsPouleArbitre vue) {
		this.idMatch = idMatch;
		this.couple = couple;
		this.table = table;
		this.modele = new ModeleSelectionVainqueurMatch(vue.getTournoi());
		this.vue = vue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PicturedButton p = (PicturedButton) e.getSource();
		int scoreEquipe1 = 0;
		int scoreEquipe2 = 0;
		if (p.getActionCommand().equals("equipe1")) {
			this.couple.setWinner(EquipeMatch.EQUIPE_1);
			scoreEquipe1 = 3;
			scoreEquipe2 = 1;
		}
		if (p.getActionCommand().equals("equipe2")) {
			this.couple.setWinner(EquipeMatch.EQUIPE_2);
			scoreEquipe1 = 1;
			scoreEquipe2 = 3;
		}
		this.table.repaint();
		this.modele.setScoreEquipe(this.couple.getNomEquipe1(), scoreEquipe1, this.idMatch);
		this.modele.setScoreEquipe(this.couple.getNomEquipe2(), scoreEquipe2, this.idMatch);
		switch (this.modele.getPhase()) {
		case POULE:
			this.vue.enableCloture(false);
			break;
		case FINALE:
			this.vue.enableCloture(true);
			break;
		case CLOSED:
			this.vue.enableCloture(true);
			break;
		default:
			break;
		}
	}
}
