package Test;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DAO.ConnexionDB;
import DAO.ImpEquipeDAO;
import modele.Equipe;
import modele.EquipeSaison;

public class testDesincription {

	private Connection connection;
	private ImpEquipeDAO impEquipes;
	private LinkedList<Equipe> equipes;

	@Before
	public void setUp(){
		ConnexionDB.getConnexionDB().setAutoCommit(false);
		this.connection = ConnexionDB.getConnexionDB().getConnexion();
		
		this.impEquipes = new ImpEquipeDAO();
		
		this.equipes = new LinkedList<Equipe>();
		this.equipes.add(new Equipe("Test1", "fr"));
		this.equipes.add(new Equipe("Test2", "fr"));
		this.equipes.add(new Equipe("Test3", "fr"));
		this.equipes.add(new Equipe("Test4", "fr"));
		
		for(Equipe e : this.equipes) {
			this.impEquipes.add(e);
		}
			
		for(EquipeSaison eAnnee : this.impEquipes.getAllByAnnee(2024)) {
			System.out.println(eAnnee.getNom());
		}
		
	}

	@Test
	public void testDesinscriptionEquipe() throws Exception {
		for(EquipeSaison eAnnee : this.impEquipes.getAllByAnnee(2024)) {
			this.impEquipes.deleteSaison(eAnnee);
		}
        assertTrue(this.impEquipes.getAllByAnnee(2024).isEmpty());
	}

	
	@After
	public void tearDown() throws SQLException {
		this.connection.rollback();
		ConnexionDB.getConnexionDB().setAutoCommit(true);
		this.connection.close();
	}
	

}
