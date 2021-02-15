package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.conversiondetype.str;

public class len{

  public static int len(String s){
    return s.length();
  }
  public static int len(int x){
    return str.str(x).length();
  }
  // tableaux
  public static int len(String t []){
    return t.length;
  }
  public static int len(int t []){
    return t.length;
  }
  public static int len(double t []){
    return t.length;
  }
  public static int len(float t []){
    return t.length;
  }
  public static int len(char t []){
    return t.length;
  }

}
