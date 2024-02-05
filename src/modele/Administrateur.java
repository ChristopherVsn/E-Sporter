package modele;
/**
 * Représente un administrateur dans le système.
 */
public class Administrateur {

	private String login;
	private String password;
	
	/**
     * Constructeur de la classe Administrateur avec le login et le mot de passe de l'administrateur.
     *
     * @param login    Login de l'administrateur
     * @param password Mot de passe de l'administrateur
     */
	public Administrateur(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	 /**
     * Obtient le login de l'administrateur.
     *
     * @return Login de l'administrateur
     */
	public String getLogin() {
		return this.login;
	}
	/**
     * Obtient le mot de passe de l'administrateur.
     *
     * @return Mot de passe de l'administrateur
     */
	public String getPassword() {
		return this.password;
	}
}
