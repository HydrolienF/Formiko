package fr.formiko.usuel;

public class clear {
  private static boolean affClear = true;
  // GET SET -----------------------------------------------------------------------
  public static boolean getAffClear(){
    return affClear;
  }
  public static void setAffClear(boolean b){
    affClear = b;
  }
  // Fonctions propre -----------------------------------------------------------
  public static void clear(){
    if (affClear) { System.out.print("\033[H\033[2J"); } //permet d'éffacer la console.
  }
}