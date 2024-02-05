package Test;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DAO.ConnexionDB;
import DAO.ImpEquipeDAO;
import DAO.ImpMatchsDAO;
import DAO.ImpTournoiDAO;
import modele.Equipe;
import modele.ModeleCreationTournoi;
import modele.Tournoi;

public class testPoules {

	private Connection connection;
	
	@Before
	public void setUp() {
		ConnexionDB.getConnexionDB().setAutoCommit(false);
		this.connection = ConnexionDB.getConnexionDB().getConnexion();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
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
