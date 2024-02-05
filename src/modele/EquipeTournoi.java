package modele;

public class EquipeTournoi {
	
	private String equipe;
	private int rank;
	private int nbMatchJoues;
	private int nbVictoires;
	
	public EquipeTournoi(String equipe, int rank, int nbMatchJoues, int nbVictoires) {
		this.equipe = equipe;
		this.rank = rank;
		this.nbMatchJoues = nbMatchJoues;
		this.nbVictoires = nbVictoires;
	}
	
	public String getEquipe() {
		return this.equipe;
	}
	
	public int getRank() {
		return this.rank;
	}
	
	public int getNbMatchJoues() {
		return this.nbMatchJoues;
	}
	
	public int getNbVictoires() {
		return this.nbVictoires;
	}
	
	@Override 
	public String toString() {
		return this.getRank() + " " + this.getEquipe() + " " + this.getNbMatchJoues() + " " + this.getNbVictoires();
	}

}
