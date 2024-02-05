package modele;

import java.util.Objects;
/**
 * Représente un arbitre dans le contexte du modèle.
 */
public class Arbitre {

	private int idArbitre;
	private String nom;
	private String prenom;

	/**
     * Constructeur de la classe Arbitre avec l'identifiant, le nom et le prénom de l'arbitre.
     *
     * @param idArbitre Identifiant de l'arbitre
     * @param nom       Nom de l'arbitre
     * @param prenom    Prénom de l'arbitre
     */
	public Arbitre(int idArbitre, String nom, String prenom) {
		this.idArbitre = idArbitre;
		this.nom = nom;
		this.prenom = prenom;
	}

	/**
     * Constructeur de la classe Arbitre avec le nom et le prénom de l'arbitre.
     * L'identifiant n'est pas spécifié lors de la création.
     *
     * @param nomArbitre    Nom de l'arbitre
     * @param prenomArbitre Prénom de l'arbitre
     */
	public Arbitre(String nomArbitre, String prenomArbitre) {
    }

	/**
     * Obtient le nom de l'arbitre.
     *
     * @return Nom de l'arbitre
     */
    public String getNom() {
		return this.nom;
	}

    /**
     * Obtient le prénom de l'arbitre.
     *
     * @return Prénom de l'arbitre
     */
	public String getPrenom() {
		return this.prenom;
	}
	
	 /**
     * Obtient l'identifiant de l'arbitre.
     *
     * @return Identifiant de l'arbitre
     */
	public int getIdArbitre() {
		return this.idArbitre;
	}

	 /**
     * Définit le nom de l'arbitre.
     *
     * @param nom Nouveau nom de l'arbitre
     */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
     * Définit le prénom de l'arbitre.
     *
     * @param prenom Nouveau prénom de l'arbitre
     */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	/**
     * Définit l'identifiant de l'arbitre.
     *
     * @param id Nouvel identifiant de l'arbitre
     */
	public void setIdArbitre(int id) {
		this.idArbitre = id;
	}
	
	/**
     * Compare l'arbitre actuel à un autre objet pour l'égalité.
     *
     * @param obj Objet à comparer
     * @return true si les arbitres sont égaux, false sinon
     */
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Arbitre arbitre = (Arbitre) obj;
	    return Objects.equals(nom, arbitre.nom) && Objects.equals(prenom, arbitre.prenom);
	}
	
	/**
     * Produit une représentation textuelle de l'arbitre.
     *
     * @return Chaîne de caractères représentant l'arbitre
     */
	@Override
	public String toString() {
		return this.nom + " " + this.prenom;
	}


}
