package modele;

import java.util.Calendar;
import java.util.List;

import DAO.ImpEquipeDAO;

public class ModeleSuppressionEquipe {
    private ImpEquipeDAO impEquipeDAO;


    public ModeleSuppressionEquipe(){
        this.impEquipeDAO = new ImpEquipeDAO();
    }

    public List<EquipeSaison> getAll()  {
        return EquipesSaison.getInstance(Calendar.getInstance().get(Calendar.YEAR)).getEquipes();
    }

    public void deleteEquipe(EquipeSaison equipe)  {
        this.impEquipeDAO.deleteSaison(equipe);
        EquipesSaison.getInstance(Calendar.getInstance().get(Calendar.YEAR)).deleteEquipeSaison(equipe);

    }

    public EquipeSaison getEquipeByName(String nomEquipe)  {
        return this.impEquipeDAO.getByName(nomEquipe);
    }

}
