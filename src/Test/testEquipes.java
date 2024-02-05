package Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import DAO.ConnexionDB;
import DAO.ImpEquipeDAO;
import DAO.ImpPalmares;
import junit.framework.TestCase;
import modele.Equipe;
import modele.EquipeSaison;
import modele.EquipesSaison;
import modele.ModeleDetailEquipe;

public class testEquipes extends TestCase {
	private Connection connection;
	private ImpEquipeDAO impEquipeDAO;
	private ImpPalmares impPalm;
	private List<EquipeSaison> equipes;

	@BeforeClass
	public void setUp() {
		ConnexionDB.getConnexionDB().setAutoCommit(false);
		this.connection = ConnexionDB.getConnexionDB().getConnexion();
		this.impEquipeDAO = new ImpEquipeDAO();
		this.impPalm = new ImpPalmares();
	}

	@Test
	public void testCreationNouvelleEquipe() {
		String nomEquipeTest = "EquipeBizarreQuiExistyePas";
		boolean equipeExiste = this.impEquipeDAO.rechercheEquipe(nomEquipeTest);
		assertFalse(equipeExiste);

		Equipe nouvelleEquipe = new Equipe(nomEquipeTest, "France");
		this.impEquipeDAO.add(nouvelleEquipe);
		assertTrue(this.impEquipeDAO.rechercheEquipe(nomEquipeTest));
	}

	@Test
	public void testSuppressionEquipe() {
		Equipe ancienneEquipe = new Equipe("Bibou", "France");
		this.impEquipeDAO.add(ancienneEquipe);

		assertTrue(this.participeSaison(ancienneEquipe));
		EquipeSaison bibou = this.impEquipeDAO.getByName(ancienneEquipe.getNom());
		this.impEquipeDAO.deleteSaison(bibou);
		assertFalse(this.participeSaison(ancienneEquipe));
	}

	// this.equipes = EquipesSaison.getInstance(2024).getEquipes();
	// for(EquipeSaison e : this.equipes) {
	// (e.toString() + " score : " + e.getScore());
	// }

	private boolean participeSaison(Equipe ancienneEquipe) {
		List<EquipeSaison> allE = this.impEquipeDAO.getAllByAnnee(2024);
		for (EquipeSaison e : allE) {
			if (e.getNom().equals(ancienneEquipe.getNom())) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void testScoreNouvelleEquipe() throws Exception {
		Equipe e1 = new Equipe("KC", "France");
		this.impEquipeDAO.add(e1);
		List<EquipeSaison> allE = this.impPalm.getByAnnee(2024);
		assertEquals(0, this.getScore(e1, allE));
	}

	private int getScore(Equipe e1, List<EquipeSaison> allE) {
		for (EquipeSaison e : allE) {
			if (e.getNom().equals(e1.getNom())) {
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
}
