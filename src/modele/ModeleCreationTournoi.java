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

    /**
     * Initialise le modèle de création de tournoi.
     */
    public ModeleCreationTournoi() {
        this.impTournoiDAO = new ImpTournoiDAO();
    }

    /**
     * Ajoute un tournoi à la base de données.
     *
     * @param tournoi Le tournoi à ajouter.
     */
    public void addTournoi(Tournoi tournoi) {
    	this.impTournoiDAO.add(tournoi);
		tournoi.setIdTournoi(this.impTournoiDAO.getId(tournoi));
    }

    /**
     * Génère un mot de passe aléatoire de 10 caractères.
     *
     * @return Le mot de passe généré.
     */
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
    /**
     * Génère un login aléatoire de 8 caractères.
     *
     * @return Le login généré.
     */
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

    /**
     * Obtient la liste de tous les tournois.
     *
     * @return La liste de tous les tournois.
     */
    public List<Tournoi> getAll() {
        return this.impTournoiDAO.getAll();
    }

    /**
     * Vérifie si un tournoi existe déjà.
     *
     * @param t Le tournoi à vérifier.
     * @return true si le tournoi existe déjà, sinon false.
     */
    public boolean existe(Tournoi t) {
        if (this.impTournoiDAO.getByName(t.getNom()) != null) {
            return true;
        }
        return false;
    }
    
    /**
     * Vérifie la validité des informations d'un tournoi.
     *
     * @param t Le tournoi à vérifier.
     * @return Un message d'erreur si le tournoi n'est pas valide, sinon une chaîne vide.
     */
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
