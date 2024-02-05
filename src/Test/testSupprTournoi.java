package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DAO.ConnexionDB;
import DAO.ImpEquipeDAO;
import DAO.ImpMatchsDAO;
import DAO.ImpPalmares;
import DAO.ImpTournoiDAO;
import modele.Equipe;
import modele.Match;
import modele.ModeleCreationTournoi;
import modele.Phase;
import modele.ModeleDetailEquipe;
import modele.Poule;
import modele.Tournoi;

public class testSupprTournoi {

	private Connection connection;
	private Tournoi tournoi;
	private LinkedList<Equipe> equipes;
	private ModeleCreationTournoi modeleT;
	private ImpEquipeDAO impEquipes;
	private int idTournoi;
	private ImpTournoiDAO impTournois;
	private Poule poule;
	private ImpMatchsDAO impMatch;
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
	public void testPhasesTournoi() {
		//création du tournoi
		this.genererTournoi();
		assertEquals(Phase.NOT_STARTED, this.impTournois.getTurnamentPhase(this.tournoi));
		//génération des matchs
		this.generateMatch("LEC", this.impTournois.getId(this.tournoi), this.equipes);
		assertEquals(Phase.POULE, this.impTournois.getTurnamentPhase(this.tournoi));
		//génération de la finale
		this.genererFinale();
		assertEquals(Phase.FINALE, this.impTournois.getTurnamentPhase(this.tournoi));
		//cloture de la finale
		this.impMatch.setScoreEquipe(7, "D", 1, "LEC"); this.impMatch.setScoreEquipe(7, "A", 3, "LEC");
		assertEquals(Phase.CLOSED, this.impTournois.getTurnamentPhase(this.tournoi));
	}

	@Test
	public void testSuppressionTournoi() {
		int idT = 0;
		
		//création du tournoi
		this.genererTournoi();
		idT = this.impTournois.getId(this.tournoi);
		assertNotEquals(Optional.empty(), this.impTournois.getById(idT));
		
		this.generateMatch("LEC", this.impTournois.getId(this.tournoi), this.equipes);
		idT = this.impTournois.getId(this.tournoi);
		assertEquals(4, this.impTournois.getNbEquipesByTournoi(this.tournoi));
		assertNotEquals(Optional.empty(), this.impTournois.getById(idT));
		
		this.impTournois.delete(this.tournoi);
		idT = this.impTournois.getId(this.tournoi);
		System.out.println(idT);
		assertEquals(0, this.impTournois.getNbEquipesByTournoi(this.tournoi));
		assertEquals(Optional.empty(), this.impTournois.getById(idT));
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
	
	@Test
	public void testHistoriqueTournoi() {
		Equipe nouvelleEquipe = new Equipe("KCorp", "France");
		this.impEquipes.add(nouvelleEquipe);
		ModeleDetailEquipe modele = new ModeleDetailEquipe(nouvelleEquipe);
		assertEquals(modele.getTournois().size(), 0);
		assertEquals(modele.getScore(nouvelleEquipe), "0");
		
		this.tournoi = new Tournoi("LEC", "Regional", "15-08-2024", "18-08-2024", "log2", "pwd2");
		this.modeleT.addTournoi(this.tournoi);
		
		this.equipes = new LinkedList<Equipe>();
		this.equipes.add(new Equipe("B", "fr"));
		this.equipes.add(new Equipe("C", "fr"));
		this.equipes.add(new Equipe("D", "fr"));
		
		for(Equipe e : this.equipes) {
			this.impEquipes.add(e);
		}
		this.equipes.add(nouvelleEquipe);

		this.idTournoi = this.impTournois.getId(this.tournoi);
		this.generateMatch("LEC", idTournoi, this.equipes);
		
		this.impMatch.setScoreEquipe(1, "KCorp", 3, "LEC"); this.impMatch.setScoreEquipe(1, "B", 1, "LEC");
		this.impMatch.setScoreEquipe(2, "KCorp", 3, "LEC"); this.impMatch.setScoreEquipe(2, "C", 1, "LEC");
		this.impMatch.setScoreEquipe(3, "KCorp", 1, "LEC"); this.impMatch.setScoreEquipe(3, "D", 3, "LEC");
		this.impMatch.setScoreEquipe(4, "C", 1, "LEC"); this.impMatch.setScoreEquipe(4, "B", 3, "LEC");
		this.impMatch.setScoreEquipe(5, "B", 1, "LEC"); this.impMatch.setScoreEquipe(5, "D", 3, "LEC");
		this.impMatch.setScoreEquipe(6, "C", 1, "LEC"); this.impMatch.setScoreEquipe(6, "D", 3, "LEC");
		
		this.poule = new Poule(this.tournoi);
		this.finale = poule.getFinal();
		this.impMatch.setScoreEquipe(7, "D", 1, "LEC"); this.impMatch.setScoreEquipe(7, "KCorp", 3, "LEC");
		
		modele = new ModeleDetailEquipe(nouvelleEquipe);
		assertEquals(modele.getTournois().size(), 1);
		assertTrue(modele.tournoiFini(tournoi));
		assertEquals(modele.getScore(nouvelleEquipe), "0");
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
	}
	
	private void genererMatchsPoule() {
		this.impMatch.setScoreEquipe(1, "A", 3, "LEC"); this.impMatch.setScoreEquipe(1, "B", 1, "LEC");
		this.impMatch.setScoreEquipe(2, "A", 3, "LEC"); this.impMatch.setScoreEquipe(2, "C", 1, "LEC");
		this.impMatch.setScoreEquipe(3, "A", 1, "LEC"); this.impMatch.setScoreEquipe(3, "D", 3, "LEC");
		this.impMatch.setScoreEquipe(4, "C", 1, "LEC"); this.impMatch.setScoreEquipe(4, "B", 3, "LEC");
		this.impMatch.setScoreEquipe(5, "B", 1, "LEC"); this.impMatch.setScoreEquipe(5, "D", 3, "LEC");
		this.impMatch.setScoreEquipe(6, "C", 1, "LEC"); this.impMatch.setScoreEquipe(6, "D", 3, "LEC");
	}
	
	private void genererFinale() {
		this.genererMatchsPoule();
		this.poule = new Poule(this.tournoi);
		this.finale = poule.getFinal();
	}
	
	private void generateMatch(String nomTournoi, int idTournoi, List<Equipe> equipes) {
		int n = 1;
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
	
	private void clotureFinale() {
		
	}
}
