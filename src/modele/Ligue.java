package modele;

/**
 * Enumération représentant différents niveaux de ligues d'un tournoi avec des valeurs associées et des noms.
 */
public enum Ligue {
    LOCAL(1, "Local"),
    REGIONAL(1.5, "Regional"),
    NATIONAL(2, "National"),
    INTERNATIONAL(2.5, "International"),
    INTERNATIONAL_CLASSE(3, "International_Classe");

    private final double valeur;
    private final String nom;

    /**
     * Constructeur privé de la classe Ligue.
     *
     * @param valeur Valeur associée à la ligue
     * @param nom    Nom de la ligue
     */
    private Ligue(double valeur, String nom) {
        this.valeur = valeur;
        this.nom = nom;
    }

    /**
     * Obtient la valeur associée à la ligue.
     *
     * @return Valeur associée à la ligue
     */
    public double getValeur() {
        return valeur;
    }

    /**
     * Obtient le nom de la ligue.
     *
     * @return Nom de la ligue
     */
    public String getNom() {
        return nom;
    }

    /**
     * Obtient une instance de l'enum à partir de sa représentation sous forme de chaîne.
     *
     * @param ligueStr Représentation sous forme de chaîne de la ligue
     * @return Instance de l'enum correspondante
     * @throws IllegalArgumentException Si aucune correspondance n'est trouvée pour la représentation de la ligue
     */
    public static Ligue fromString(String ligueStr) {
        for (Ligue ligue : values()) {
            if (ligue.name().equalsIgnoreCase(ligueStr)) {
                return ligue;
            }
        }
        throw new IllegalArgumentException("Aucune correspondance pour la ligue : " + ligueStr);
    }

}
