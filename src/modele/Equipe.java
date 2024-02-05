package modele;

import java.util.LinkedList;
import java.util.List;

public class Equipe {
	private String nom;
	private String pays;
	private List<Joueur> joueurs;

	/**
	 * @param nom
	 * @param pays
	 */
	public Equipe(String nom, String pays) {
		this.nom = nom;
		this.pays = pays;
		this.joueurs = new LinkedList<>();
	}
	/**
	 * @return nom de l'équipe
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * @return pays de l'équipe
	 */
	public String getPays() {
		return this.pays;
	}

	/**
	 * Permets de modifier le nom de l'équipe
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Permets de modifier le pays de l'Equipe
	 * @param pays
	 */
	public void setPays(String pays) {
		this.pays = pays;
	}


	/**
	 * ajoute à l'Equipe un Joueur
	 * @param Joueur
	 * @throws Exception
	 */
	public void addJoueur(Joueur j) throws Exception {
		if (this.joueurs.size() < 5)this.joueurs.add(j);
	}
	
	/**
	 * @return la liste de joueurs
	 */
	public List<Joueur> getJoueur(){
		return this.joueurs;
	}
}