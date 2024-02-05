package modele;

import java.util.LinkedList;
import java.util.List;

public class Equipes {

	private LinkedList<Equipe> equipes;

	public Equipes() throws Exception {
		this.equipes = new LinkedList<>();
	}

	/**
	 * @return une liste d'équipes
	 */
	public List<Equipe> getEquipes() {
		return equipes;
	}

	/**
	 * retire une équipe de la liste
	 * 
	 * @param équipe
	 */
	public void retirerEquipe(Equipe equipe) {
		this.equipes.remove(equipes);
	}

	/**
	 * ajoute une Equipe a la liste
	 * 
	 * @param équipe
	 */
	public void ajouterEquipe(Equipe equipe) {
		this.equipes.add(equipe);
	}

	/**
	 * recherche une équipe par son nom
	 * 
	 * @param nom
	 * @return équipe trouvée sinon null
	 */
	public Equipe getEquipeByName(String nom) {
		for (Equipe equipe : this.equipes) {
			if (equipe.getNom().equals(nom))
				return equipe;
		}
		return null;
	}

}
