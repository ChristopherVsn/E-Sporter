package modele;
/**
 * Représente une équipe dans le contexte d'un match avec des statistiques de match.
 */
public class EquipeMatch extends EquipeSaison {
	
	private int nbMatchsJouer;
	private int nbMatchsWin;
	
	/**
     * Constructeur de la classe EquipeMatch avec World Rank spécifié.
     * 
     * @param nom    nom de l'équipe
     * @param pays   pays de l'équipe
     * @param score  score de l'équipe
     * @param wr     World Rank de l'équipe
     */
	public EquipeMatch(String nom, String pays, int score, int wr) {
		super(nom, pays, 0, score, wr);
		this.nbMatchsJouer = 0;
		this.nbMatchsWin = 0;
	}
	
	/**
     * Obtient le nombre de matchs joués par l'équipe.
     * 
     * @return nombre de matchs joués
     */
	public int getNbMatchsPlayed() {
		return this.nbMatchsJouer;
	}
	
	/**
     * Obtient le nombre de matchs remportés par l'équipe.
     * 
     * @return nombre de matchs remportés
     */	
	public int getNbMatchsWin() {
		return this.nbMatchsWin;
	}
	
	/**
     * Modifie le nombre de matchs joués par l'équipe.
     * 
     * @param nb nombre de matchs joués
     */
	public void setNbMatchsPlayed(int nb) {
		this.nbMatchsJouer = nb;
	}
	
	/**
     * Modifie le nombre de matchs remportés par l'équipe.
     * 
     * @param nb nombre de matchs remportés
     */
	public void setNbMachsWin(int nb) {
		this.nbMatchsWin = nb;
	}
	

}
