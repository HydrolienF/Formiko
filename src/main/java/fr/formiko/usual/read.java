package fr.formiko.usual;

import fr.formiko.usual.types.str;

import java.util.Scanner;

public class read {
  private static String message = "";

  private static void setMessage(String variable){
    message = g.get("read",1,"Veuillez entrer une valeur pour la variable")+" \"" + variable + "\" "+g.get("read",2,"(ne rien entrer ne modifie pas la valeur de la variable)")+" : ";
  }
  // get
  public static String readString(){
    Scanner sc = new Scanner(System.in); // on lit ce qu'a écrit l'utilisateur.
    String s = sc.nextLine();
    if (s.equals("STOP")) { System.exit(0);}
    return s;
  }
  public static String getString(String nomDeLaVariable, String laVariable){
    setMessage(nomDeLaVariable);
    return getString( nomDeLaVariable, laVariable, message);
  }
  public static String getString(String nomDeLaVariable, String laVariable, String message){
    erreur.println(message);
    String s =	readString();
    if (s.equals("")) { return laVariable;} // si l'utilisateur ne rentre rien la variable reste la même.
    return s;
  }

  public static int getInt(int min, int max, String nomDeLaVariable, int laVariable, String message){
    int x = min-1; int k=0;
    while (x==min-1 && k<13){ // tant que le nombre qu'on veut n'est pas reconnu comme un nombre dans le bon intervale.
      if(k>10){erreur.println(g.get("read",3,"Il semblerai que vous soyez bloqué. Tapper STOP pour interompre ce programme."));}
      String s = getString(nomDeLaVariable, str.str(laVariable), message);
      try {
        x = (int)str.sToLThrows(s);  //on essaye d'en faire un nombre
      } catch(Exception e) {
        erreur.alerte(g.get("read",4,"la chaine de charcatère")+" \"" + s + "\" "+g.get("read",5,"n'est pas un nombre"));
        x = min-1; // si on y arrive pas on lui met la mauvaise valeur.
      }
      if (x<min | x>max || x==-1){ //si x n'est pas dans l'intervale [min;max]
        erreur.println(g.get("read",6,"Il semblerai que la valeur donnée ne soit pas bonne. Celle ci devrait être comprise dans l'intervale")+" ["+min+";"+max+"]");
        x = min-1;
      } // sinon il garde sa valeur et on ne rentrera plus dans la boucle tant que.
    }
    return x;
  }
  public static int getInt(int min,int max,String nomDeLaVariable, int laVariable){
    setMessage(nomDeLaVariable);
    return getInt(min,max,nomDeLaVariable,laVariable, message);
  }
  public static byte getByte(int min,int max,String nomDeLaVariable, int laVariable){
    return (byte) getInt(min,max,nomDeLaVariable,laVariable);
  }
  public static byte getByte(int min,int max,String nomDeLaVariable, int laVariable, String message){
    return (byte) getInt(min,max,nomDeLaVariable,laVariable, message);
  }
  public static boolean getOuiOuNon() {
    return getOuiOuNon(g.get("read",7,"Oui ou Non ?"));
  }
  public static boolean getOuiOuNon(String message) {
    erreur.print(message);
    String s = readString();
    if (s.equals("oui") || s.equals("Oui") ||s.equals("O") || s.equals("o")  || s.equals("OUI") || s.equals("yes") || s.equals("y") || s.equals("si") || s.equals("jes") || s.equals("Jes") || s.equals("j")) {
      return true;
    } else if (s.equals("non") || s.equals("Non") ||s.equals("N") || s.equals("n") || s.equals("NON") || s.equals("no") || s.equals("ne") || s.equals("Ne") || s.equals("Ne") || s.equals("NO")) {
      return false;
    } else {
      erreur.println(g.get("read",8,"Ecrivez votre réponse sous la forme \"o\" (pour oui) ou \"n\" (pour non)"));
      return getOuiOuNon();
    }
  }
  public static boolean getBooolean(String nomDeLaVariable, boolean laVariable){
    int i=0;
    while (i<10){ // tant que le nombre qu'on veut est mauvais
      setMessage(nomDeLaVariable);
      String s = readString();
      if (s.equals("")) { return laVariable;} // si l'utilisateur ne rentre rien la variable reste la même.
      if (s.equals("1") || s.equals("true")) { return true;}
      if (s.equals("0") || s.equals("false")) { return false;}
      erreur.alerte(g.get("read",9,"L'entrée de l'utilisateur n'as pas été reconue."));
      i++; // au bout de 10 échec la boucle s'arrète.
    }
    erreur.println(g.get("read",3,"Il semblerai que vous soyez bloqué. Tapper STOP pour interompre ce programme."));
    return getBooolean(nomDeLaVariable, laVariable);
  }

}
