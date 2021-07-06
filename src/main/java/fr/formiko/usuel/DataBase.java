package fr.formiko.usuel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
*@author Traqueur#3620
*/
public class DataBase {

    private Connection connection;
    private String host;
    private String database;
    private String username;
    private String password;
    private int port;

    public DataBase(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        connectDatabase();
    }

    public void connectDatabase() {
        if (isConnected()) {
            return;
        }
        try {
          System.out.println("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database);
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/"
                    + this.database , this.username, this.password);
                    //+ "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC"
            // LoggerUtils.success("Database connected!!");
            System.out.println("Database connected!!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Unable to connect on database...");
            System.exit(0);
        }
    }

    public boolean isConnected() {
        return this.connection != null;
    }
}
