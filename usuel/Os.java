package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

public class Os {
  private byte id; //0=linux, 1=Windows, 2=Mac, -1=inconnu
  private static String OS = System.getProperty("os.name").toLowerCase();
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Os(){iniOs();}
  // GET SET --------------------------------------------------------------------
  public byte getId(){return id;}
  public void setId(byte x){id=x;}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    return(g.get("os."+id));
  }
  private static boolean isWindows() {
    return (OS.indexOf("win") >= 0);
  }
  private static boolean isMac() {
    return (OS.indexOf("mac") >= 0);
  }
  private static boolean isUnix() {
    return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
  }
  public void iniOs(){
    if(isWindows()){
      setId((byte)1);
    }else if(isUnix()){
      setId((byte)0);
    }else if(isMac()){
      setId((byte)2);
    }else{
      setId((byte)-1);
    }
  }
}