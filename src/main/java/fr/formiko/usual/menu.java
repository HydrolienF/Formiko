package fr.formiko.usual;

public class menu {
  public static byte menu (String t [],String s){
    // Idéalement on affiche les info dans une mini fenêtre ou après avoir nétoyé le terminal.
    int lent = t.length;
    for (int i=0; i<lent; i++){
      erreur.println((i+1) + " : " + t[i]);
    }
    byte j=-1;
    while (j==-1){
      j = read.getByte(1,lent,"j",j,s+" : ");
    }
    return j;
  }
  public static byte menu(String t[]){ return menu(t,g.get("choix","choix"));}
  public static String menuStr (String t []){
    // Idéalement on affiche les info dans une mini fenêtre ou après avoir nétoyé le terminal.
    int lent = t.length;
    for (int i=0; i<lent; i++){
      erreur.println((i+1) + " : " + t[i]);
    }
    int j=-1;
    while (j==-1){
      j = read.getInt(1,lent,"j",j,g.get("choix","choix")+" : ");
    }
    return t[j];
  }
}
