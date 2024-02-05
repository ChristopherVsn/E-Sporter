package modele;
/**
 * Représente une équipe dans le contexte d'un tournoi.
 */
public class EquipeTournoi {
	
	private String equipe;
	private int rank;
	private int nbMatchJoues;
	private int nbVictoires;
	
	/**
     * Constructeur de la classe EquipeTournoi.
     * 
     * @param equipe nom de l'équipe
     * @param rank classement de l'équipe
     * @param nbMatchJoues nombre de matchs joués par l'équipe
     * @param nbVictoires nombre de victoires de l'équipe
     */
	
	public EquipeTournoi(String equipe, int rank, int nbMatchJoues, int nbVictoires) {
		this.equipe = equipe;
		this.rank = rank;
		this.nbMatchJoues = nbMatchJoues;
		this.nbVictoires = nbVictoires;
	}
	
	/**
     * Retourne le nom de l'équipe.
     * 
     * @return nom de l'équipe
     */
	public String getEquipe() {
		return this.equipe;
	}
	
	/**
     * Retourne le classement de l'équipe.
     * 
     * @return classement de l'équipe
     */
	public int getRank() {
		return this.rank;
	}
	
	/**
     * Retourne le nombre de matchs joués par l'équipe.
     * 
     * @return nombre de matchs joués par l'équipe
     */
	public int getNbMatchJoues() {
		return this.nbMatchJoues;
	}
	
	/**
     * Retourne le nombre de victoires de l'équipe.
     * 
     * @return nombre de victoires de l'équipe
     */
	public int getNbVictoires() {
		return this.nbVictoires;
	}
	
	/**
     * Redéfinition de la méthode toString.
     * 
     * @return une représentation textuelle de l'équipe avec son classement, son nom,
     *         le nombre de matchs joués et le nombre de victoires.
     */
	@Override 
	public String toString() {
		return this.getRank() + " " + this.getEquipe() + " " + this.getNbMatchJoues() + " " + this.getNbVictoires();
	}

}
