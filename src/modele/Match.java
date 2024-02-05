package modele;

import java.util.Map;

public class Match {
    private int idMatch;
    private String nomTournoi;
    private Map<String, Integer> equipes;
	private int idTournoi;

    public Match(int idMatch, String nomTournoi, Map<String, Integer> equipes, int idTournoi) {
        this.idMatch = idMatch;
        this.nomTournoi = nomTournoi;
        this.equipes = equipes;
        this.idTournoi = idTournoi;
    }
    
    public String getWinner() {
    	return this.equipes.entrySet().stream().max((entry1, entry2)-> Integer.compare(entry1.getValue(),entry2.getValue())).get().getKey();
    }
    
    public String getLoser() {
    	return this.equipes.entrySet().stream().min((entry1, entry2)-> Integer.compare(entry1.getValue(),entry2.getValue())).get().getKey();
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
    
    public boolean isWinnerDefined() {
    	for(Integer score:this.equipes.values()) {
    		if (score != 0) {
    			return true;
    		}
    	}
    	return false;
    }
    
    @Override
    public String toString() {
    	return "match n°" + this.idMatch + " => " + this.getNomEquipe1() + " (" + this.equipes.get(this.getNomEquipe1()) +  ") - " +
    this.getNomEquipe2() +" (" + this.equipes.get(this.getNomEquipe2()) + ")";
    }
}