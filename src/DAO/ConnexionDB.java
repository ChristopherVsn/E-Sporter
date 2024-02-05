package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Singleton représentant la connexion à la base de données.
 */
public class ConnexionDB {
    private static ConnexionDB connexionDB;
    private Connection connection;
    /**
     * Constructeur privé de la classe ConnexionDB.
     * La connexion à la base de données est établie lors de la création de l'instance.
     */
    private ConnexionDB() {
        String urlConnexion = "jdbc:derby:BDD;create=true";
        try {
            this.connection = DriverManager.getConnection(urlConnexion);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtient l'instance unique de ConnexionDB (singleton).
     *
     * @return instance unique de ConnexionDB
     */
    public static synchronized ConnexionDB getConnexionDB()    {
        if (connexionDB == null) {
        	synchronized(ConnexionDB.class) {
        		if (connexionDB == null) {
        			connexionDB = new ConnexionDB();
        		}
        	}
        }
        return connexionDB;
    }

    /**
     * Obtient la connexion à la base de données.
     *
     * @return connexion à la base de données
     */
    public Connection getConnexion(){
        return this.connection;
    }
    
    /**
     * Ferme la connexion à la base de données.
     * Si la connexion est déjà fermée ou nulle, aucune action n'est effectuée.
     */
    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Définit le mode autocommit de la connexion.
     *
     * @param commit true pour activer le mode autocommit, false sinon
     */
    public void setAutoCommit(boolean commit) {
        if (this.connection != null) {
            try {
                this.connection.setAutoCommit(commit);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}