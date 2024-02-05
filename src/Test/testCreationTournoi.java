package Test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import DAO.ConnexionDB;
import modele.ModeleCreationTournoi;
import modele.Tournoi;

public class testCreationTournoi {

	private Connection connection;
	private Tournoi tournoi;
	private ModeleCreationTournoi modele;
	
	@Before
	public void setUp() {
		ConnexionDB.getConnexionDB().setAutoCommit(false);
		this.connection = ConnexionDB.getConnexionDB().getConnexion();
		
		this.tournoi = new Tournoi("LEC", "Regional", "15-08-2024", "18-08-2024", "log", "pwd");
		this.modele = new ModeleCreationTournoi();
		this.modele.addTournoi(this.tournoi);
	}
	
	@Test
	public void tournoiExistant() {
		this.tournoi = new Tournoi("LEC", "National", "16-08-2024", "18-08-2024", "log", "pwd");
		assertEquals("Le tournoi existe déjà !", this.modele.verificationTournoiValide(tournoi));
	}
	
	@Test
	public void datesPassees() {
		this.tournoi = new Tournoi("T2", "National", "16-08-2023", "18-08-2023", "log", "pwd");
		assertEquals("Les dates sont passées", this.modele.verificationTournoiValide(tournoi));
	}
	
	@Test
	public void datesDebutAvantFin() {
		this.tournoi = new Tournoi("T2", "National", "19-08-2024", "18-08-2024", "log", "pwd");
		assertEquals("La date de début est après la date de fin", this.modele.verificationTournoiValide(tournoi));
	}
	
	@Test
	public void datesDebutPassee() {
		this.tournoi = new Tournoi("T2", "National", "16-08-2023", "18-08-2024", "log", "pwd");
		assertEquals("La date de début est passée", this.modele.verificationTournoiValide(tournoi));
	}
	
	@Test
	public void datesSaison() {
		this.tournoi = new Tournoi("T2", "National", "16-08-2025", "18-08-2025", "log", "pwd");
		assertEquals("Les dates doivent être dans la saison 2024", this.modele.verificationTournoiValide(tournoi));
	}
	
	@Test
	public void dateFinDansSaison() {
		this.tournoi = new Tournoi("T2", "National", "19-08-2024", "18-08-2025", "log", "pwd");
		assertEquals("La date de fin doit être dans la saison 2024", this.modele.verificationTournoiValide(tournoi));
	}
	
	@After
	public void tearDown() {
		try {
			this.connection.rollback();
			ConnexionDB.getConnexionDB().setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
