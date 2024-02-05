package DAO;

import java.util.List;

import modele.Arbitre;
import modele.Tournoi;

public class ApplicationTestDAO {

	public static void main(String args[]) {
		ImpTournoiDAO tdao = new ImpTournoiDAO();
		Tournoi t = tdao.getByName("pipi Christopher");
		System.out.println(t.getNom());
		List<Arbitre> list = tdao.getArbitresByTournoi(t);
		list.stream().forEach(System.out::println);
	}
}
