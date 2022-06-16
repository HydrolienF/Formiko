package fr.formiko.usual;
import fr.formiko.usual.erreur; import fr.formiko.usual.g;
//def par défaut des fichiers depuis 0.79.5

public class debug{
  private static boolean affLesEtapesDeRésolution = true;
  private static boolean affG = true;
  private static boolean affLesDescriptionsDesExeptions = true;
  private static boolean affLesPerformances=true;

// GET SET ----------------------------------------------------------------------
  public static void setMessage (boolean b){affLesEtapesDeRésolution = b;}
  public static void setAffLesDescriptionsDesExeptions (boolean b){affLesDescriptionsDesExeptions = b;}
  public static boolean getAffLesDescriptionsDesExeptions (){return affLesDescriptionsDesExeptions;}
  public static boolean getMessage (){return affLesEtapesDeRésolution;}
  public static boolean getPerformance(){ return affLesPerformances;}
  public static void setPerformance(boolean b){affLesPerformances=b;}
  public static boolean getAffG(){ return affG;}
  public static void setAffG(boolean b){ affG=b;}
  public static void setDPG(boolean b){setAffG(b); setMessage(b);setPerformance(b);}
  //fonction propre ----------------------------------------------------------------
  public static void débogage(String s){
    if (affLesEtapesDeRésolution) { erreur.println(s); }
  }
  public static void débogage(int s){ débogage(s+"");}
  public static void debug(String s){débogage(s);}
  public static void performances(String s){
    if (affLesPerformances){ erreur.println(s);}
  }
  public static void g(String s){
    if (affG){ erreur.println(s);}
  }
  public static void g(String s, int x, int y){
    g(s+" : x="+x+", y="+y);
  }
}
