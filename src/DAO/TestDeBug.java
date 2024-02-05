package DAO;

import java.util.List;

import modele.Administrateur;
import modele.EquipeSaison;
import modele.EquipesSaison;

public class TestDeBug {

	public static void main(String[] args) throws Exception {
		ImpAdministrateurDAO imp = new ImpAdministrateurDAO();
		List<Administrateur> equipe = imp.getAll();
		for(Administrateur e : equipe) {
			System.out.println(e.getLogin());
			System.out.println(e.getPassword());
		}
	}

}
