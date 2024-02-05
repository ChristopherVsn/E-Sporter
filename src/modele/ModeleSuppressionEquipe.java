package modele;

import java.util.Calendar;
import java.util.List;

import DAO.ImpEquipeDAO;

/**
 * Modèle pour la suppression d'une équipe.
 */
public class ModeleSuppressionEquipe {
    private ImpEquipeDAO impEquipeDAO;

    /**
     * Constructeur par défaut.
     * Initialise l'objet ImpEquipeDAO utilisé pour l'accès aux données.
     */
    public ModeleSuppressionEquipe(){
        this.impEquipeDAO = new ImpEquipeDAO();
    }

    /**
     * Récupère la liste de toutes les équipes de la saison en cours.
     *
     * @return La liste des équipes de la saison en cours.
     */
    public List<EquipeSaison> getAll()  {
        return EquipesSaison.getInstance(Calendar.getInstance().get(Calendar.YEAR)).getEquipes();
    }

    /**
     * Supprime une équipe.
     *
     * @param equipe L'équipe à supprimer.
     */
    public void deleteEquipe(EquipeSaison equipe)  {
        // Supprime l'équipe de la base de données
        this.impEquipeDAO.deleteSaison(equipe);
        // Supprime l'équipe de la liste des équipes de la saison en cours
        EquipesSaison.getInstance(Calendar.getInstance().get(Calendar.YEAR)).deleteEquipeSaison(equipe);
    }

    /**
     * Récupère une équipe par son nom.
     *
     * @param nomEquipe Le nom de l'équipe à récupérer.
     * @return L'équipe correspondant au nom spécifié.
     */
    public EquipeSaison getEquipeByName(String nomEquipe)  {
        return this.impEquipeDAO.getByName(nomEquipe);
    }

}
