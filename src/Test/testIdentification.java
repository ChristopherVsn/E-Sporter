package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DAO.ConnexionDB;
import DAO.ImpTournoiDAO;
import modele.Administrateur;
import modele.ModeleCreationTournoi;
import modele.ModeleIdentification;
import modele.Phase;
import modele.Tournoi;

public class testIdentification {

	private Connection connection;
	private Tournoi tournoi;
	private Administrateur user;
	private Administrateur admin;
	private ModeleIdentification modele;
	private ModeleCreationTournoi modeleT;

	@Before
	public void setUp() {
		ConnexionDB.getConnexionDB().setAutoCommit(false);
		this.connection = ConnexionDB.getConnexionDB().getConnexion();
		this.tournoi = new Tournoi("LEC", "Regional", "15-08-2024", "18-08-2024", "log", "pwd");
		this.modeleT = new ModeleCreationTournoi();
		this.modeleT.addTournoi(this.tournoi);
		this.admin = new Administrateur("admin", "admin");
		this.modele = new ModeleIdentification();
	}

	@Test
	public void logAdminOk() {
		assertTrue(this.modele.isUserAdmin(this.admin));
	}
	
	@Test
	public void logAdminPasOk() {
		this.user = new Administrateur("admin", "mdpIncorrect");
		assertFalse(this.modele.isUserAdmin(this.user));
		
		this.user = new Administrateur("ADMIN", "ADMIN");
		assertFalse(this.modele.isUserAdmin(this.user));
		
		this.user = new Administrateur("logIncorrect", "admin");
		assertFalse(this.modele.isUserAdmin(this.user));
		
		this.user = new Administrateur("logIncorrect", "mdpIncorrect");
		assertFalse(this.modele.isUserAdmin(this.user));
	}
	
	@Test
	public void logArbitreOk() {
		ImpTournoiDAO imp = new ImpTournoiDAO();
		this.user = new Administrateur("log", "pwd");
		Tournoi t = this.modele.getTournoi(user);
		t = imp.getByName(t.getNom());
		assertEquals(t.getNom(), this.tournoi.getNom());
		assertEquals(t.getIdTournoi(), this.tournoi.getIdTournoi());
	}
	
	@Test
	public void logArbitreTournoiFini() {
		ImpTournoiDAO imp = new ImpTournoiDAO();
		this.user = new Administrateur("tournoiD", "mdpTD");
		Tournoi t = this.modele.getTournoi(user);
		t = imp.getByName(t.getNom());
		assertEquals(t.getNom(), "TournoiD");
		assertEquals(this.modele.getPhase(t), Phase.CLOSED);
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
