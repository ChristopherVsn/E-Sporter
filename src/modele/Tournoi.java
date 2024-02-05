package modele;
/**
 * Classe représentant un tournoi avec ses attributs tels que le nom, la ligue, les dates de début et de fin,
 * le login et le mot de passe d'accès.
 */
public class Tournoi {

	private String nom;
	private Ligue ligue;
	private String dateDebut;
	private String dateFin;
	private String login;
	private String motDePasse;
	private int idTournoi;
	private String annee;

	/**
     * Constructeur de la classe Tournoi avec un identifiant.
     *
     * @param idTournoi      Identifiant du tournoi
     * @param nom            Nom du tournoi
     * @param ligue          Ligue associée au tournoi
     * @param dateDebut      Date de début du tournoi
     * @param dateFin        Date de fin du tournoi
     * @param login          Login pour l'accès au tournoi
     * @param motDePasse     Mot de passe pour l'accès au tournoi
     */
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

	 /**
     * Constructeur de la classe Tournoi sans identifiant.
     *
     * @param nomTournoi       Nom du tournoi
     * @param ligue2           Ligue associée au tournoi
     * @param dateDebut2       Date de début du tournoi
     * @param dateFin2         Date de fin du tournoi
     * @param generateLogin    Login pour l'accès au tournoi
     * @param generatePassword Mot de passe pour l'accès au tournoi
     */
	public Tournoi(String nomTournoi, String ligue2, String dateDebut2, String dateFin2, String generateLogin,
			String generatePassword) {
		this.nom = nomTournoi;
		this.ligue = Ligue.fromString(ligue2);

		this.dateDebut = dateDebut2;
		this.dateFin = dateFin2;
		this.login = generateLogin;
		this.motDePasse = generatePassword;
	}

	 /**
     * Constructeur de la classe Tournoi avec année.
     *
     * @param nomTournoi       Nom du tournoi
     * @param ligue            Ligue associée au tournoi
     * @param dateDebut2       Date de début du tournoi
     * @param dateFin2         Date de fin du tournoi
     * @param generateLogin    Login pour l'accès au tournoi
     * @param generatePassword Mot de passe pour l'accès au tournoi
     * @param annee            Année du tournoi
     */
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

	/**
     * Obtient le nom du tournoi.
     *
     * @return le nom du tournoi.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Obtient la ligue associée au tournoi.
     *
     * @return la ligue du tournoi.
     */
    public Ligue getLigue() {
        return ligue;
    }

    /**
     * Obtient la date de début du tournoi.
     *
     * @return la date de début du tournoi.
     */
    public String getDateDebut() {
        return dateDebut;
    }

    /**
     * Obtient la date de fin du tournoi.
     *
     * @return la date de fin du tournoi.
     */
    public String getDateFin() {
        return dateFin;
    }

    /**
     * Obtient le login pour l'accès au tournoi.
     *
     * @return le login du tournoi.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Obtient le mot de passe pour l'accès au tournoi.
     *
     * @return le mot de passe du tournoi.
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du tournoi.
     *
     * @return une chaîne de caractères décrivant le tournoi.
     */
    @Override
    public String toString() {
        return "Tournoi{" + "nom='" + nom + '\'' + ", ligue=" + ligue + ", dateDebut='" + dateDebut + '\''
                + ", dateFin='" + dateFin + '\'' + ", login='" + login + '\'' + ", motDePasse='" + motDePasse + '\''
                + '}';
    }

    /**
     * Obtient l'identifiant du tournoi.
     *
     * @return l'identifiant du tournoi.
     */
    public int getIdTournoi() {
        return this.idTournoi;
    }
    
    /**
     * Définit l'identifiant du tournoi.
     *
     * @param id l'identifiant à définir pour le tournoi.
     */
    public void setIdTournoi(int id) {
        this.idTournoi = id;
    }

    /**
     * Obtient l'année du tournoi.
     *
     * @return l'année du tournoi.
     */
    public String getAnnee() {
        return this.annee;
    }
}
