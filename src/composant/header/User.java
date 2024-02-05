package composant.header;

public enum User {
	ADMIN("Administrateur"), REFEREE("Arbitre");
	
	private String userName;

	private User(String userName) {
		this.userName = userName;
	}
	
	public String getUser() {
		return this.userName;
	}
}
