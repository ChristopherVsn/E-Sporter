package modele;

public class Joueur {
    private String pseudo;
    private String nomEquipe;

    public Joueur(String pseudo, String nomEquipe) {
        this.pseudo = pseudo;
        this.nomEquipe = nomEquipe;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public String getnomEquipe() {
        return this.nomEquipe;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

}