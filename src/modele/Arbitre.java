package modele;

import java.util.Objects;

public class Arbitre {

	private int idArbitre;
	private String nom;
	private String prenom;

	public Arbitre(int idArbitre, String nom, String prenom) {
		this.idArbitre = idArbitre;
		this.nom = nom;
		this.prenom = prenom;
	}

	public Arbitre(String nomArbitre, String prenomArbitre) {
    }

    public String getNom() {
		return this.nom;
	}

	public String getPrenom() {
		return this.prenom;
	}
	
	public int getIdArbitre() {
		return this.idArbitre;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public void setIdArbitre(int id) {
		this.idArbitre = id;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Arbitre arbitre = (Arbitre) obj;
	    return Objects.equals(nom, arbitre.nom) && Objects.equals(prenom, arbitre.prenom);
	}
	
	@Override
	public String toString() {
		return this.nom + " " + this.prenom;
	}


}
