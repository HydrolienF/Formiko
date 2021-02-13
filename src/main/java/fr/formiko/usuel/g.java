package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.types.str;
import java.util.Map;
import java.util.HashMap;

/**
*{@summary Get a translation class.<br>}
*@author Hydrolien
*@version 1.5
*/
public class g {
  /***
   *Contain the Strings in the chosen language.
   *@version 1.1
   */
  private static Map<String, String> map = new HashMap<String, String>(); // g.get(clé) permet d'obtenir le texte associé.
  // GET SET ----------------------------------------------------------
  public static Map<String, String> getMap(){return map;}
  public static void setMap(Map m){map=m;}
  public static String getElementMap(String clé){ return map.get(clé);}
  // Fonctions propre -----------------------------------------------------------
  public static String get(String f, int x,String s){
    return get(f+"."+x,s);
  }
  /**
  *{@summary main get methode.<br>}
  *Null values are supported.<br>
  *@param key Map key.
  *@param s String return if key is not found.
  *@return value that corresponds to key or s if there is no value fo key.
  *@version 1.5
  */
  public static String get(String key, String s){
    if(key==null){if(s==null){s="";}return s;}
    try {
      String sr = getElementMap(key);
      if(sr.length()>6 && sr.substring(sr.length()-6).equals("[auto]")){sr=sr.substring(0,sr.length()-6);}//on retir [auto] a la fin de la String si besoin
      else if(sr.length()>2 && sr.substring(sr.length()-2).equals("[]")){sr=sr.substring(0,sr.length()-2);}//on retire les simple crochet aussi.
      if(sr.equals("ø")){return "";}
      if(sr!=null && sr.length()>0){ return sr;}
      return s;
    }catch (Exception e){if(s==null){s="";}return s;}
  }
  /**
  *{@summary main get methode.<br>}
  *the String return if key is not found will be key.
  *@param key Map key.
  *@return value that corresponds to key or s if there is no value fo key.
  *@version 1.5
  */
  public static String get(String key){
    if(key.equals("n")){return get(key,"");} // le fait qu'un mots subisse l'action n'as d'impacte qu'en eo.
    return get(key,key);
  }
  /**
  *{@summary main get methode.<br>}
  *return String will have a 1a char in uppercase.
  *@param key Map key.
  *@return value that corresponds to key or s if there is no value fo key.
  *@version 1.5
  */
  public static String getM(String key){
    String s = get(key);
    return str.sToSMaj(s);
  }
  /**
  *{@summary main get methode.}
  *@param key Map key1.
  *@param key2 Map key2.
  *@return value that corresponds to key and key2 or value1/value2 if that's not the same.
  *@version 1.5
  */
  public static String getOu(String key, String key2){
    String value1 = get(key,"ø");
    String value2 = get(key2,"ø");
    //si aucune clé n'as été trouvé.
    if(value1.equals("ø") && value2.equals("ø")){ return key+"/"+key2;}
    //si une des clé n'as pas été trouvée
    if(value1.equals("ø")){value1 = value2;}
    if(value2.equals("ø")){value2 = value1;}
    //si les 2 valeurs sont égales :
    if(value1.equals(value2)){return value1;}
    //si les 2 valeurs sont différentes :
    return value1+"/"+value2;
  }
}
