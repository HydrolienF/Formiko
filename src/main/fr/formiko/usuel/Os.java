package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.79.5
/**
*{@summary Do OS search.<br>}
*id (var that represent OS) can be used to do special actions like graphics action that are need to work on a specific OS as Windows that do weard thing with java Panel.<br>
*@author Hydrolien
*@version 0.91
*/
public class Os {
  /***
  *{@summary Save OS.<br>}
  *0=linux, 1=Windows, 2=Mac, -1=unknowOS
  */
  private byte id;
  private static String OS = System.getProperty("os.name").toLowerCase();
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Os(){iniOs();}
  // GET SET --------------------------------------------------------------------
  public byte getId(){return id;}
  public void setId(byte x){id=x;}
  public boolean isWindows(){return id==1;}
  public boolean isLinux(){return id==0;}
  public boolean isMac(){return id==2;}
  // Fonctions propre -----------------------------------------------------------
  /**
  *Return the name of the OS.
  */
  public String toString(){
    return(g.get("os."+id));
  }
  /**
  *Check if OS is WindowsOs.
  */
  private static boolean iniIsWindows() {
    return (OS.indexOf("win") >= 0);
  }
  /**
  *Check if OS is MacOs.
  */
  private static boolean iniIsMac() {
    return (OS.indexOf("mac") >= 0);
  }
  /**
  *Check if OS is Unix (linux).
  */
  private static boolean iniIsUnix() {
    return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
  }
  /**
  *Save the OS in the int id.
  */
  public void iniOs(){
    if(iniIsWindows()){
      setId((byte)1);
    }else if(iniIsUnix()){
      setId((byte)0);
    }else if(iniIsMac()){
      setId((byte)2);
    }else{
      setId((byte)-1);
    }
  }
}
