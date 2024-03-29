package composant.table;

import java.awt.Image;

import javax.swing.ImageIcon;

import composant.buttons.PicturedButton;

/**
 * Un couple de <code>PicturedButton</code> lié à deux Equipes affichant une
 * étoile pleine pour une équipe gagnante et une étoile vide pour une équipe
 * perdante.
 * 
 * @author Vivien
 *
 */
public class CoupleStar {

	private PicturedButton equipe1;
	private PicturedButton equipe2;
	private String winner;
	private String nomEquipe1;
	private String nomEquipe2;

	/**
	 * Créer le couple représentant un match à partir des 2 équipes qui se
	 * rencontrent.
	 * 
	 * @param nomEquipe1 la première équipe du match
	 * @param nomEquipe2 la seconde équipe du match
	 */
	public CoupleStar(String nomEquipe1, String nomEquipe2) {
		ImageIcon empty = new ImageIcon(getClass().getResource("/pictures/icon/star_empty.png"));
		this.equipe1 = new PicturedButton(empty);
		this.equipe2 = new PicturedButton(empty);
		this.equipe1.resizeIcon(15);
		this.equipe2.resizeIcon(15);
		this.equipe1.setActionCommand("equipe1");
		this.equipe2.setActionCommand("equipe2");
		this.winner = "indefini";
		this.nomEquipe1 = nomEquipe1;
		this.nomEquipe2 = nomEquipe2;
	}

	/**
	 * @return l'étoile de l'équipe 1
	 */
	public PicturedButton getEquipe1() {
		return this.equipe1;
	}

	/**
	 * @return l'étoile de l'équipe 2
	 */
	public PicturedButton getEquipe2() {
		return this.equipe2;
	}

	/**
	 * Définit le vainqueur du match en modifiant les étoiles des PicturedButton. Si
	 * l'équipe 1 gagne, son étoile devient pleine et celle de l'équipe 2 devient
	 * vide. Réciproquement.
	 * 
	 * @param equipe l'équipe 1 ou 2 gagnant le match.
	 */
	public void setWinner(EquipeMatch equipe) {
		ImageIcon winner = new ImageIcon(getClass().getResource("/pictures/icon/star_full.png"));
		ImageIcon looser = new ImageIcon(getClass().getResource("/pictures/icon/star_empty.png"));
		Image win = (Image) winner.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		Image loose = (Image) looser.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);

		switch (equipe) {
		case EQUIPE_1:
			((ImageIcon) this.equipe1.getIcon()).setImage(win);
			((ImageIcon) this.equipe2.getIcon()).setImage(loose);
			this.winner = EquipeMatch.EQUIPE_1.name();
			break;
		case EQUIPE_2:
			((ImageIcon) this.equipe1.getIcon()).setImage(loose);
			((ImageIcon) this.equipe2.getIcon()).setImage(win);
			this.winner = EquipeMatch.EQUIPE_2.name();
			break;
		}
	}

	/**
	 * @return le nom de l'équipe gagnante du match
	 */
	public String getWinner() {
		return this.winner;
	}

	/**
	 * @return le nom de l'équipe 1
	 */
	public String getNomEquipe1() {
		return this.nomEquipe1;
	}

	/**
	 * @return le nom de l'équipe 2
	 */
	public String getNomEquipe2() {
		return this.nomEquipe2;
	}

	@Override
	public String toString() {
		return "winner : " + this.winner;
	}
}
