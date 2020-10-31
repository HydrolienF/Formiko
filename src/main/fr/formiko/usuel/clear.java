package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

public class clear {
  private static boolean affClear = true;
  // GET SET -----------------------------------------------------------------------
  public static boolean getAffClear(){return affClear;}
  public static void setAffClear(boolean b){affClear = b;}
  // Fonctions propre -----------------------------------------------------------
  public static void clear(){
    if (affClear) { System.out.print("\033[H\033[2J"); } //permet d'éffacer la console.
  }
}
