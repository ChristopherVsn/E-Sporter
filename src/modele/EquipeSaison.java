package modele;

/**
 * Représente une équipe dans le contexte d'une saison spécifique.
 */
public class EquipeSaison extends Equipe {
	
	private int saison;
	private int wr;
	private int score;
	
	/**
     * Constructeur de la classe EquipeSaison avec World Rank spécifié.
     * 
     * @param nom    nom de l'équipe
     * @param pays   pays de l'équipe
     * @param saison année de la saison
     * @param score  score de l'équipe
     * @param wr     World Rank de l'équipe
     */
	public EquipeSaison(String nom, String pays, int saison, int score, int wr) {
		super(nom, pays);
		this.saison = saison;
		this.score = score;
		this.wr= wr;
	}
	
	/**
     * Constructeur de la classe EquipeSaison avec World Rank non spécifié (initialisé à -1).
     * 
     * @param nom    nom de l'équipe
     * @param pays   pays de l'équipe
     * @param saison année de la saison
     * @param score  score de l'équipe
     */
	public EquipeSaison(String nom, String pays, int saison, int score) {
		super(nom, pays);
		this.saison = saison;
		this.score = score;
		this.wr= -1;
	}
	
	/**
     * Obtient l'année de la saison.
     * 
     * @return année de la saison
     */
	public int getSaison() {
		return saison;
	}
	
	/**
     * Obtient le score de l'équipe.
     * 
     * @return score de l'équipe
     */
	public int getScore() {
		return score;
	}
	
	/**
     * Obtient le World Rank de l'équipe. Si le World Rank n'est pas initialisé, retourne -1.
     * 
     * @return World Rank de l'équipe, sinon -1
     */
	public int getWr() {
		return wr;
	}
	
	/**
     * Modifie l'année de la saison.
     * 
     * @param saison année de la saison
     */
	public void setSaison(int saison) {
		this.saison = saison;
	}
	
	/**
     * Modifie le score de l'équipe.
     * 
     * @param score score de l'équipe
     */
	public void setScore(int score) {
		this.score=  score;
	}
	
	/**
     * Modifie le World Rank de l'équipe.
     * 
     * @param wr World Rank de l'équipe
     */
	public void setWr(int wr) {
		this.wr =wr;
	}
	
	/**
     * Redéfinition de la méthode toString.
     * 
     * @return une représentation textuelle de l'équipe avec son nom, son score et son World Rank.
     */
	@Override
	public String toString() {
		return "equipe :" + this.getNom() + " score : " + this.getScore() + " wr : " + this.getWr();
	}
}
