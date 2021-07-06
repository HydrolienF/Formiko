package fr.formiko.usuel;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;


public class DB {
  private String bdd = "hydrolien_formikodb";
  private String url = "https://phpmyadmin.alwaysdata.com/phpmyadmin/" + bdd;
  private String user = "hydrolien";
  private String passwd = ""; //TODO find a way to save the password without puting it there.
  public DB(){
    //aucune des url ne fonctionne ;(
    // url = "mysql-hydrolien.alwaysdata.net/hydrolien_formikodb";
    url = "mysql-hydrolien.alwaysdata.net//server:3306;DatabaseName=hydrolien_formikodb";
    // url = "mysql-hydrolien.alwaysdata.net";
    try {
      // a l'execution j'ai pour les 3 lignes si dessous "The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary."
      // Class.forName("com.mysql.jdbc.Driver").newInstance();
      // Class.forName("com.mysql.jdbc.Driver");
      // Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn = DriverManager.getConnection(url, user, passwd);
      System.out.println("Connecter");
    } catch (Exception e){
      e.printStackTrace();
      System.out.println("Erreur");
      System.exit(0);
    }
  }
}
