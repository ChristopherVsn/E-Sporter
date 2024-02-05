package modele;

public enum Ligue {
    LOCAL(1, "Local"),
    REGIONAL(1.5, "Regional"),
    NATIONAL(2, "National"),
    INTERNATIONAL(2.5, "International"),
    INTERNATIONAL_CLASSE(3, "International_Classe");

    private final double valeur;
    private final String nom;

    private Ligue(double valeur, String nom) {
        this.valeur = valeur;
        this.nom = nom;
    }

    public double getValeur() {
        return valeur;
    }

    public String getNom() {
        return nom;
    }

    public static Ligue fromString(String ligueStr) {
        for (Ligue ligue : values()) {
            if (ligue.name().equalsIgnoreCase(ligueStr)) {
                return ligue;
            }
        }
        throw new IllegalArgumentException("Aucune correspondance pour la ligue : " + ligueStr);
    }

}
