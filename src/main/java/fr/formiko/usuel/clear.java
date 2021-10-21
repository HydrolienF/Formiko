package fr.formiko.usuel;

//def par défaut des fichiers depuis 0.79.5
/**
*Clear console.
*@author Hydrolien
*@version 1.0
*/
public class clear {
  private static boolean affClear = true;
  // GET SET ----------------------------------------------------------------------
  public static boolean getAffClear(){return affClear;}
  public static void setAffClear(boolean b){affClear = b;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *Clear console.
  @version 1.0
  */
  public static void clear(){
    if (affClear) { System.out.print("\033[H\033[2J"); } //permet d'éffacer la console.
  }
}
