package modele;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DAO.ImpTournoiDAO;

public class ModeleListeTournois {

    private ImpTournoiDAO impTournoiDAO;

    public ModeleListeTournois() {
        this.impTournoiDAO = new ImpTournoiDAO();
    }

    /**
     * @return tous les tournois de la saison et qui ne sont pas encore terminés
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
     * Compare les dates @param date1 et @param date2
     * 
     * @return -1 si date1 est avant date2, 1 si date1 est après date2, 0 si date1
     *         est égale à date2
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
     * supprime des tournois de la saison le tournoi @param t donné
     */
    public void deleteTournoi(Tournoi t) {
        this.impTournoiDAO.delete(t);
    }

    /**
     * @return un tournoi de nom @param nameTournoi
     */
    public Tournoi getByName(String nameTournoi) {
        return this.impTournoiDAO.getByName(nameTournoi);
    }

}
