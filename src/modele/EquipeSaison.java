package modele;

public class EquipeSaison extends Equipe {
	
	private int saison;
	private int wr;
	private int score;
	
	/**
	 * Equipe saison
	 * @param nom
	 * @param pays
	 * @param saison
	 * @param score
	 * @param world rank
	 */
	public EquipeSaison(String nom, String pays, int saison, int score, int wr) {
		super(nom, pays);
		this.saison = saison;
		this.score = score;
		this.wr= wr;
	}
	
	/**
	 * Equipe saison
	 * @param nom
	 * @param pays
	 * @param saison
	 * @param score
	 */
	public EquipeSaison(String nom, String pays, int saison, int score) {
		super(nom, pays);
		this.saison = saison;
		this.score = score;
		this.wr= -1;
	}
	
	/**
	 * @return l'année de la saison
	 */
	public int getSaison() {
		return saison;
	}
	
	/**
	 * @return le score de l'équipe
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * @return le world rank, si le world rank n'est pas initialisé -1
	 */
	public int getWr() {
		return wr;
	}
	
	/**
	 * Permets de modifier l'année de la saison
	 * @param saison
	 */
	public void setSaison(int saison) {
		this.saison = saison;
	}
	
	/**
	 * Permets de modifier le socre de l'équipe
	 * @param score
	 */
	public void setScore(int score) {
		this.score=  score;
	}
	
	/**
	 * Permets de modifier le world rank de l'équipe
	 * @param world rank
	 */
	public void setWr(int wr) {
		this.wr =wr;
	}
	
	@Override
	public String toString() {
		return "equipe :" + this.getNom() + " score : " + this.getScore() + " wr : " + this.getWr();
	}
}
