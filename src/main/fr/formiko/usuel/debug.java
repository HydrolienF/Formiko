package fr.formiko.usuel;
import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.79.5

public class debug{
  private static boolean affLesEtapesDeRésolution = true;
  private static boolean affG = true;
  private static boolean affLesDescriptionsDesExeptions = true;
  private static boolean affLesPerformances=true;

// GET SET -----------------------------------------------------------------------
  public static void setAffLesEtapesDeRésolution (boolean b){affLesEtapesDeRésolution = b;}
  public static void setAffLesDescriptionsDesExeptions (boolean b){affLesDescriptionsDesExeptions = b;}
  public static boolean getAffLesDescriptionsDesExeptions (){return affLesDescriptionsDesExeptions;}
  public static boolean getAffLesEtapesDeRésolution (){return affLesEtapesDeRésolution;}
  public static boolean getAffLesPerformances(){ return affLesPerformances;}
  public static void setAffLesPerformances(boolean b){affLesPerformances=b;}
  public static boolean getAffG(){ return affG;}
  public static void setAffG(boolean b){ affG=b;}
  public static void setDPG(boolean b){setAffG(b); setAffLesEtapesDeRésolution(b);setAffLesPerformances(b);}
  //fonction propre ----------------------------------------------------------------
  public static void débogage(String s){
    if (affLesEtapesDeRésolution) { System.out.println(s); }
  }
  public static void débogage(int s){ débogage(s+"");}
  public static void performances(String s){
    if (affLesPerformances){ System.out.println(s);}
  }
  public static void g(String s){
    if (affG){ System.out.println(s);}
  }
  public static void g(String s, int x, int y){
    g(s+" : x="+x+", y="+y);
  }
}
