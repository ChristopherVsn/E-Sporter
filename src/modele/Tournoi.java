package modele;

public class Tournoi {

	private String nom;
	private Ligue ligue;
	private String dateDebut;
	private String dateFin;
	private String login;
	private String motDePasse;
	private int idTournoi;
	private String annee;

	public Tournoi(int idTournoi, String nom, String ligue, String dateDebut, String dateFin, String login,
			String motDePasse) {
		this.nom = nom;
		this.ligue = Ligue.fromString(ligue);
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.login = login;
		this.motDePasse = motDePasse;
		this.idTournoi = idTournoi;
	}

	public Tournoi(String nomTournoi, String ligue2, String dateDebut2, String dateFin2, String generateLogin,
			String generatePassword) {
		this.nom = nomTournoi;
		this.ligue = Ligue.fromString(ligue2);

		this.dateDebut = dateDebut2;
		this.dateFin = dateFin2;
		this.login = generateLogin;
		this.motDePasse = generatePassword;
	}

	public Tournoi(String nomTournoi, String ligue, String dateDebut2, String dateFin2, String generateLogin,
			String generatePassword, String annee) {
		this.nom = nomTournoi;
		this.annee = annee;
		this.ligue = Ligue.fromString(ligue);
		this.dateDebut = dateDebut2;
		this.dateFin = dateFin2;
		this.login = generateLogin;
		this.motDePasse = generatePassword;
	}

	public String getNom() {
		return nom;
	}

	public Ligue getLigue() {
		return ligue;
	}

	public String getDateDebut() {
		return dateDebut;
	}

	public String getDateFin() {
		return dateFin;
	}

	public String getLogin() {
		return login;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	@Override
	public String toString() {
		return "Tournoi{" + "nom='" + nom + '\'' + ", ligue=" + ligue + ", dateDebut='" + dateDebut + '\''
				+ ", dateFin='" + dateFin + '\'' + ", login='" + login + '\'' + ", motDePasse='" + motDePasse + '\''
				+ '}';
	}

	public int getIdTournoi() {
		return this.idTournoi;
	}
	
	public void setIdTournoi(int id) {
		this.idTournoi = id;
	}

	public String getAnnee() {
		return this.annee;
	}
}
