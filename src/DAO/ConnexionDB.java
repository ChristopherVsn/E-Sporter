package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDB {
    private static ConnexionDB connexionDB;
    private Connection connection;
    private ConnexionDB() {
        String urlConnexion = "jdbc:derby:BDD;create=true";
        try {
            this.connection = DriverManager.getConnection(urlConnexion);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public Connection getConnexion(){
        return this.connection;
    }
    
    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
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