package fr.formiko.usual;

//def par défaut des fichiers depuis 0.79.5
/**
*Clear console.
*@author Hydrolien
*@lastEditedVersion 1.0
*/
public class clear {
  private static boolean affClear = true;
  // GET SET ----------------------------------------------------------------------
  public static boolean getAffClear(){return affClear;}
  public static void setAffClear(boolean b){affClear = b;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *Clear console.
  @lastEditedVersion 1.0
  */
  public static void clear(){
    if (affClear) { erreur.print("\033[H\033[2J"); } //permet d'éffacer la console.
  }
}
