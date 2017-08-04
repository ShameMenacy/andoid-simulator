package vn.ugame.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import vn.ugame.exception.DataConnectionFailed;

public class ConnectionFactory {

    private static ConnectionFactory instance;

    public static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }    
    private String userName = "root";
    private String password = "123123";
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "";
    private String serverName = "localhost:3306";
    private String database = "AppMagazine";
    private String encoding = "characterEncoding=UTF-8";

    private ConnectionFactory() {
    }

    public Connection createDBConnection() throws DataConnectionFailed {
        try {
            Class.forName(driver);
            url = "jdbc:mysql://" + serverName + "/" + database + "?" + encoding;
            // Load the JDBC driver
            return DriverManager.getConnection(url, userName, password);            
        } catch (Exception ex){
            throw new DataConnectionFailed(ex.toString());
        }
    } 
}
