package modele;

import java.util.Map;

public class Matchs {
    private int idMatch;
    private String nomTournoi;
    private Map<String, Integer> equipes;
	private int idTournoi;

    public Matchs(int idMatch, String nomTournoi, Map<String, Integer> equipes, int idTournoi) {
        this.idMatch = idMatch;
        this.nomTournoi = nomTournoi;
        this.equipes = equipes;
        this.idTournoi = idTournoi;
    }
    
    public int getIdTournoi() {
    	return this.idTournoi;
    }

    public int getIdMatch() {
        return idMatch;
    }

    public String getNomTournoi() {
        return nomTournoi;
    }

    public Map<String, Integer> getEquipes() {
        return equipes;
    }

    public void setIdMatch(int idMatch) {
        this.idMatch = idMatch;
    }

    public void setNomTournoi(String nomTournoi) {
        this.nomTournoi = nomTournoi;
    }

    public void setEquipes(Map<String, Integer> equipes) {
        this.equipes = equipes;
    }

    public String getNomEquipe1() {
        return this.equipes.keySet().toArray()[0].toString();
    }

    public String getNomEquipe2() {
        return this.equipes.keySet().toArray()[1].toString();
    }
}