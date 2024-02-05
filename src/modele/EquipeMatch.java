package modele;

public class EquipeMatch extends EquipeSaison {
	
	private int nbMatchsJouer;
	private int nbMatchsWin;
	
	public EquipeMatch(String nom, String pays, int score, int wr) {
		super(nom, pays, 0, score, wr);
		this.nbMatchsJouer = 0;
		this.nbMatchsWin = 0;
	}
	
	public int getNbMatchsPlayed() {
		return this.nbMatchsJouer;
	}
	
	public int getNbMatchsWin() {
		return this.nbMatchsWin;
	}
	
	public void setNbMatchsPlayed(int nb) {
		this.nbMatchsJouer = nb;
	}
	
	public void setNbMachsWin(int nb) {
		this.nbMatchsWin = nb;
	}
	

}
