package Test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DAO.ConnexionDB;
import DAO.ImpEquipeDAO;
import DAO.ImpMatchsDAO;
import DAO.ImpPalmares;
import DAO.ImpTournoiDAO;
import modele.Equipe;
import modele.EquipeSaison;
import modele.EquipeTournoi;
import modele.EquipesSaison;
import modele.Match;
import modele.ModeleCreationTournoi;
import modele.Poule;
import modele.Tournoi;

public class testMatchs {

	private Connection connection;
	private Tournoi tournoi;
	private ModeleCreationTournoi modeleT;
	private List<Equipe> equipes;
	private ImpMatchsDAO impMatch;
	private ImpEquipeDAO impEquipes;
	private ImpTournoiDAO impTournois;
	private int idTournoi;
	private Poule poule;
	private Match finale;
	private ImpPalmares impPalm;

	@Before
	public void setUp() {
		ConnexionDB.getConnexionDB().setAutoCommit(false);
		this.connection = ConnexionDB.getConnexionDB().getConnexion();

		this.modeleT = new ModeleCreationTournoi();
		this.impMatch = new ImpMatchsDAO();
		this.impEquipes = new ImpEquipeDAO();
		this.impTournois = new ImpTournoiDAO();
		this.impPalm = new ImpPalmares();
	}

	@Test
	public void testFinale() {
		this.genererTournoi();
		this.genererMatchsPoule();
		this.genererFinale();
		assertEquals("match n°6 => A (0) - D (0)", this.finale.toString());
		this.impMatch.setScoreEquipe(6, "D", 1, "LEC"); this.impMatch.setScoreEquipe(6, "A", 3, "LEC");
		this.finale = poule.getFinal();
		assertEquals("match n°6 => A (3) - D (1)", this.finale.toString());
	}
	
	@Test
	public void testScoresTournoi() {
		this.genererTournoi();
		this.genererMatchsPoule();
		this.genererFinale();
		this.impMatch.setScoreEquipe(6, "D", 1, "LEC"); this.impMatch.setScoreEquipe(6, "A", 3, "LEC");
		
		
		List<EquipeSaison> eki = EquipesSaison.getInstance(2024).getEquipes();
		
		String scores = "";
		for(Equipe e : this.equipes) {
			scores += ("equipe " + e.getNom() + " : " + this.getScore(e, eki) + "\r\n");
		}
		
//		for(EquipeTournoi e2 : this.impTournois.getEquipesTournoi(tournoi)) {
//			System.out.println(e2.toString());
//			System.out.println(e2.getEquipe() + e2.getRank());
//		}
		
		assertEquals(scores, "equipe A : 435\r\n"
				+ "equipe B : 157\r\n"
				+ "equipe C : 90\r\n"
				+ "equipe D : 285\r\n");
	}

	private void genererTournoi() {
		this.tournoi = new Tournoi("LEC", "Regional", "15-08-2024", "18-08-2024", "log2", "pwd2");
		this.modeleT.addTournoi(this.tournoi);
		
		this.equipes = new LinkedList<Equipe>();
		this.equipes.add(new Equipe("A", "fr"));
		this.equipes.add(new Equipe("B", "fr"));
		this.equipes.add(new Equipe("C", "fr"));
		this.equipes.add(new Equipe("D", "fr"));
		
		for(Equipe e : this.equipes) {
			this.impEquipes.add(e);
		}

		this.idTournoi = this.impTournois.getId(this.tournoi);
		this.generateMatch("LEC", idTournoi, this.equipes);
	}

	private void genererMatchsPoule() {
		this.impMatch.setScoreEquipe(0, "A", 3, "LEC"); this.impMatch.setScoreEquipe(0, "B", 1, "LEC");
		this.impMatch.setScoreEquipe(1, "A", 3, "LEC"); this.impMatch.setScoreEquipe(1, "C", 1, "LEC");
		this.impMatch.setScoreEquipe(2, "A", 1, "LEC"); this.impMatch.setScoreEquipe(2, "D", 3, "LEC");
		this.impMatch.setScoreEquipe(3, "C", 1, "LEC"); this.impMatch.setScoreEquipe(3, "B", 3, "LEC");
		this.impMatch.setScoreEquipe(4, "B", 1, "LEC"); this.impMatch.setScoreEquipe(4, "D", 3, "LEC");
		this.impMatch.setScoreEquipe(5, "C", 1, "LEC"); this.impMatch.setScoreEquipe(5, "D", 3, "LEC");
	}
	
	private void genererFinale() {
		this.poule = new Poule(this.tournoi);
		this.finale = this.poule.getFinal();
	}
	
	private int getScore(Equipe e1, List<EquipeSaison> allE) {
		for(EquipeSaison e : allE) {
			if(e.getNom().equals(e1.getNom())){
				return e.getScore();
			}
		}
		return -1;
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

	private void generateMatch(String nomTournoi, int idTournoi, List<Equipe> equipes) {
		int n = 0;
		for (int i = 0; i < equipes.size(); i++) {
			for (int j = i + 1; j < equipes.size(); j++) {
				HashMap<String, Integer> map = new HashMap<>();
				map.put(equipes.get(i).getNom(), 0);
				map.put(equipes.get(j).getNom(), 0);
				this.impMatch.add(new Match(n, nomTournoi, map, idTournoi));
				n++;
			}
		}
	}
}
