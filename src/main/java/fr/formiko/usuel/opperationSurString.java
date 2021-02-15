package fr.formiko.usuel;

//def par défaut des fichiers depuis 0.79.5

public class opperationSurString{
  public static boolean contientChar(String s, char c){
    int lens = s.length();
    for (int i=0;i<lens ;i++ ) {
      if(s.charAt(i) == c){ return true;}
    }
    return false;
  }
  public static boolean contientChar(String s, char tc []){
    int lentc = tc.length;
    for (int i=0;i<lentc ;i++ ) {
      if(contientChar(s,tc[i])){ return true;}
    }
    return false;
  }
  public static String retirerChar(String s, char c){
    int lens = s.length();
    String sr = "";
    for (int i=0;i<lens ;i++ ) {
      if(s.charAt(i) != c){
        sr = sr + s.charAt(i);
      }
    }
    return sr;
  }
  public static String retirerChar(String s, char tc[]){
    // permet de retirer tt les char contenue dans tc de la String s.
    int lens = s.length();
    String sr = "";
    for (int i=0;i<lens ;i++ ) { // on copie uniquement les chars autorisé.
      if(!charEstDansTc(s.charAt(i), tc)){ sr = sr + s.charAt(i);}
    }
    return sr;
  }
  private static boolean charEstDansTc(char c, char tc[]){
    int lentc = tc.length;
    for (int i=0;i<lentc ; i++) {
      if (tc[i]==c){ return true;}
    }
    return false;
  }
}
