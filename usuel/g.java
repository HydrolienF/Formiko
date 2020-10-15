package fr.formiko.usuel;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.formiko.Main;
public class g {

  // Fonctions propre -----------------------------------------------------------
  public static String get(String f, int x,String s){
    return get(f+"."+x,s);
  }
  public static String get(String clé, String s){
    try {
      String sr = Main.getMap(clé);
      if(sr.length()>6 && sr.substring(sr.length()-6).equals("[auto]")){sr=sr.substring(0,sr.length()-6);}//on retir [auto] a la fin de la String si besoin
      else if(sr.length()>2 && sr.substring(sr.length()-2).equals("[]")){sr=sr.substring(0,sr.length()-2);}//on retire les simple crochet aussi.
      if(sr.equals("ø")){return "";}
      if(sr!=null && sr.length()>0){ return sr;} return s;
    }catch (Exception e) {return s;}
  }
  public static String get(String clé){
    if(clé.equals("n")){return get(clé,"");} // le fait qu'un mots subisse l'action n'as d'impacte qu'en eo.
    return get(clé,clé);
  }
  public static String getM(String clé){
    String s = get(clé);
    try {
      return s.substring(0,1).toUpperCase()+s.substring(1); // 1a char en majuscule.
    }catch (Exception e) {
      return s;
    }
  }
}