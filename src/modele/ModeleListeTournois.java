package modele;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DAO.ImpTournoiDAO;

/**
 * Modèle pour la gestion de la liste des tournois.
 */
public class ModeleListeTournois {

    private ImpTournoiDAO impTournoiDAO;

    /**
     * Constructeur par défaut.
     * Initialise l'objet ImpTournoiDAO utilisé pour l'accès aux données.
     */
    public ModeleListeTournois() {
        this.impTournoiDAO = new ImpTournoiDAO();
    }

    /**
     * Récupère la liste de tous les tournois de la saison qui ne sont pas encore terminés.
     *
     * @return La liste des tournois en cours.
     */
    public List<Tournoi> getAll() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedCurrentDate = currentDate.format(formatter);

        List<Tournoi> tournois = new ArrayList<>();
        for (Tournoi t : this.impTournoiDAO.getAll()) {
            if (this.impTournoiDAO.getNbMatchsByTournoi(t) <= 0
                    && compareDates(t.getDateDebut(), formattedCurrentDate) >= 0) {
                tournois.add(t);
            }
        }
        return tournois;
    }

    /**
     * Compare les dates fournies.
     *
     * @param date1 Première date à comparer.
     * @param date2 Deuxième date à comparer.
     * @return -1 si date1 est avant date2, 1 si date1 est après date2, 0 si date1 est égale à date2.
     */
    private int compareDates(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate1 = LocalDate.parse(date1, formatter);
        LocalDate localDate2 = LocalDate.parse(date2, formatter);

        if (localDate1.isBefore(localDate2)) {
            return -1;
        } else if (localDate1.isAfter(localDate2)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Supprime un tournoi de la saison.
     *
     * @param t Le tournoi à supprimer.
     */
    public void deleteTournoi(Tournoi t) {
        this.impTournoiDAO.delete(t);
    }

    /**
     * Récupère un tournoi par son nom.
     *
     * @param nameTournoi Le nom du tournoi à récupérer.
     * @return Le tournoi correspondant au nom spécifié.
     */
    public Tournoi getByName(String nameTournoi) {
        return this.impTournoiDAO.getByName(nameTournoi);
    }

}
