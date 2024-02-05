package modele;

import java.security.SecureRandom;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import DAO.ImpTournoiDAO;

public class ModeleCreationTournoi {
    private ImpTournoiDAO impTournoiDAO;
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public ModeleCreationTournoi() {
        this.impTournoiDAO = new ImpTournoiDAO();
    }

    public void addTournoi(Tournoi tournoi) {
    	this.impTournoiDAO.add(tournoi);
		tournoi.setIdTournoi(this.impTournoiDAO.getId(tournoi));
    }

    public static String generatePassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder motDePasse = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(ALPHABET.length());
            char randomChar = ALPHABET.charAt(randomIndex);
            motDePasse.append(randomChar);
        }
        return motDePasse.toString();
    }

    public static String generateLogin() {
        SecureRandom random = new SecureRandom();
        StringBuilder login = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(ALPHABET.length());
            char randomChar = ALPHABET.charAt(randomIndex);
            login.append(randomChar);
        }
        return login.toString();
    }

    public List<Tournoi> getAll() {
        return this.impTournoiDAO.getAll();
    }

    public boolean existe(Tournoi t) {
        if (this.impTournoiDAO.getByName(t.getNom()) != null) {
            return true;
        }
        return false;
    }
    
    public String verificationTournoiValide(Tournoi t) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate debut = LocalDate.parse(t.getDateDebut(), formatter);
        LocalDate fin = LocalDate.parse(t.getDateFin(), formatter);
        LocalDate aujourdHui = LocalDate.now();
        
        if (this.existe(t)) {
            return "Le tournoi existe déjà !";
        }
        else if (debut.isBefore(aujourdHui) && fin.isBefore(aujourdHui)) {
            return "Les dates sont passées";
        }
        else if (debut.isAfter(fin)) {
            return "La date de début est après la date de fin";
        }
        else if (debut.isBefore(aujourdHui)){
            return "La date de début est passée";
        }
        else if (debut.getYear() != aujourdHui.getYear() && fin.getYear() != aujourdHui.getYear()) {
            return "Les dates doivent être dans la saison " + aujourdHui.getYear();
        }
        else if (fin.getYear() != aujourdHui.getYear()) {
        	return "La date de fin doit être dans la saison " + aujourdHui.getYear();
        }
        return "";
    }
}
