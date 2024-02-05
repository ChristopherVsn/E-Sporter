package Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DAO.ConnexionDB;
import DAO.ImpEquipeDAO;
import DAO.ImpMatchsDAO;
import DAO.ImpPalmares;
import DAO.ImpTournoiDAO;
import junit.framework.TestCase;
import modele.EquipeSaison;
import modele.EquipesSaison;
import modele.Match;
import modele.ModeleCreationTournoi;
import modele.Poule;
import modele.Tournoi;

public class TestPalmares extends TestCase {
	private Connection connection;
	private ImpPalmares impPalmares;
	private ImpEquipeDAO impEquipeDAO;
	private List<EquipeSaison> equipes;
	private EquipesSaison equipesSaison;
	
	private Tournoi tournoi;
	private ModeleCreationTournoi modeleT;
	private ImpTournoiDAO impTournois;
	
	private ImpMatchsDAO impMatch;
	
	@Before
	public void setUp(){
		ConnexionDB.getConnexionDB().setAutoCommit(false);
		this.connection = ConnexionDB.getConnexionDB().getConnexion();
		
		this.impPalmares = new ImpPalmares();
		this.impEquipeDAO = new ImpEquipeDAO();
		this.impMatch = new ImpMatchsDAO();
		this.impTournois = new ImpTournoiDAO();
		
		this.equipes = EquipesSaison.getInstance(2024).getEquipes();
		for(EquipeSaison e : this.equipes) {
			System.out.println(e.toString());
		}
		
		this.modeleT = new ModeleCreationTournoi();
		
		this.tournoi = new Tournoi("LEC", "Regional", "15-08-2024", "18-08-2024", "log2", "pwd2");
		this.modeleT.addTournoi(this.tournoi);
		
		int idTournoi = this.impTournois.getId(this.tournoi);
		this.impTournois.generateMatch("LEC", idTournoi, this.equipes);
	}

	@Test
	public void testPalm() throws Exception {
//		for(Match m : this.impMatch.getMatchsByTournoi(this.tournoi)) {
//			System.out.println(m.toString());
//		}
		
		this.impMatch.setScoreEquipe(1, "EquipeD", 1, "LEC");this.impMatch.setScoreEquipe(1, "EquipeC", 3, "LEC");
		this.impMatch.setScoreEquipe(2, "EquipeC", 1, "LEC");this.impMatch.setScoreEquipe(2, "EquipeB", 3, "LEC");
		this.impMatch.setScoreEquipe(3, "EquipeC", 1, "LEC");this.impMatch.setScoreEquipe(3, "EquipeA", 3, "LEC");
		this.impMatch.setScoreEquipe(4, "EquipeD", 1, "LEC");this.impMatch.setScoreEquipe(4, "EquipeB", 3, "LEC");
		this.impMatch.setScoreEquipe(5, "EquipeD", 1, "LEC");this.impMatch.setScoreEquipe(5, "EquipeA", 3, "LEC");
		this.impMatch.setScoreEquipe(6, "EquipeB", 1, "LEC");this.impMatch.setScoreEquipe(6, "EquipeA", 3, "LEC");
		
		Poule poule = new Poule(this.tournoi);
		Match finale = poule.getFinal();
		assertEquals("match n°7 => EquipeB (0) - EquipeA (0)", finale.toString());

		this.impMatch.setScoreEquipe(7, "EquipeB", 1, "LEC");this.impMatch.setScoreEquipe(7, "EquipeA", 3, "LEC");
		finale = poule.getFinal();
//		System.out.println(finale.toString());
		for (Match m : this.impMatch.getMatchsByTournoi(this.tournoi)) {
			System.out.println(m.toString());
		}
		
		this.equipes = EquipesSaison.getInstance(2024).getEquipes();
		for(EquipeSaison e : this.equipes) {
			System.out.println(e.toString());
		}
		
//		Palmares p = impPalmares.getByAnnee(2023);
//		List<Equipe> equipes = p.getPalmares();
//		equipes.stream()
//			.forEach(e -> {
//				try {
//					System.out.println(e.getNom() + " " + impEquipeDAO.getScore(e));
//				} catch (Exception e5) {
//					e5.printStackTrace();
//				}
//			});
	}

	@After
	public void tearDown(){
		try {
			this.connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ConnexionDB.getConnexionDB().setAutoCommit(true);
	}
}
