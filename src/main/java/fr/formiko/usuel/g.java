package fr.formiko.usuel;

import fr.formiko.usuel.types.str;

import java.util.HashMap;

/**
*{@summary Get a translation class.}<br>
*@author Hydrolien
*@version 1.33
*/
public class g {
  /***
   *Contain the Strings in the chosen language.
   *@version 1.1
   */
  private static HashMap<String, String> map = new HashMap<String, String>(); // g.get(key) permet d'obtenir le texte associé.
  // GET SET ----------------------------------------------------------
  public static HashMap<String, String> getMap(){return map;}
  public static void setMap(HashMap<String, String> m){map=m;}
  public static String getElementMap(String key){ return map.get(key);}
  // Fonctions propre -----------------------------------------------------------
  /**
  *{@summary A get methode.}<br>
  *Difference with main get methode is that key = f+"."+x that's all.<br>
  *@param s String return if key is not found.
  *@return value that corresponds to key or s if there is no value fo key.
  *@version 1.33
  */
  public static String get(String f, int x,String s){
    return get(f+"."+x,s);
  }
  /**
  *{@summary main get methode with a replace value if it fail.}<br>
  *Null values are supported.<br>
  *@param key Map key.<br>
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
  *{@summary main get methode without a replace value if it fail.}<br>
  *the String return if key is not found will be key.<br>
  *@param key Map key.
  *@return value that corresponds to key or s if there is no value fo key.
  *@version 1.5
  */
  public static String get(String key){
    if(key.equals("n")){return get(key,"");} // le fait qu'un mots subisse l'action n'as d'impacte qu'en eo.
    return get(key,key);
  }
  /**
  *{@summary main get methode.}<br>
  *return String will have a 1a char in uppercase.<br>
  *the String return if key is not found will be key.<br>
  *@param key Map key.
  *@return value that corresponds to key or s if there is no value fo key.
  *@version 1.5
  */
  public static String getM(String key){
    String s = get(key);
    return str.sToSMaj(s);
  }
  /**
  *{@summary A get methode.} <br>
  *This get methode will return 1 or 2 value split by a / if get(key) & get(key2) do not return the same String.<br>
  *@param key Map key1.
  *@param key2 Map key2.
  *@return value that corresponds to key and key2 or value1/value2 if that's not the same.
  *@version 1.5
  */
  public static String getOu(String key, String key2){
    String value1 = get(key,"");
    String value2 = get(key2,"");
    //si aucune key n'as été trouvé.
    if(value1.equals("") && value2.equals("")){ return key+"/"+key2;}
    //si une des key n'as pas été trouvée
    if(value1.equals("")){value1 = value2;}
    if(value2.equals("")){value2 = value1;}
    //si les 2 valeurs sont égales :
    if(value1.equals(value2)){return value1;}
    //si les 2 valeurs sont différentes :
    return value1+"/"+value2;
  }
  /**
  *{@summary test if key is in the HashMap.}
  *@param key key to test.
  *@return try if it exist.
  *@version 1.33
  */
  public static boolean exist(String key){
    return getElementMap(key)!=null;
  }
}
