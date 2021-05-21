package fr.formiko.usuel;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.types.str;

import java.io.IOException;

/**
*{@summary Error class call to print error message.}<br>
*@author Hydrolien
*@version 1.41
*/
public class erreur {
  public static boolean muet=false;
  //colors
  private static String yellow = (char)27+"[1;33m";
  private static String red = (char)27+"[1;31m";
  private static String neutral = (char)27+"[0;m";
  private static String blue = (char)27+"[1;34m";

  public static void setMuet(boolean b){muet=b;}
  private static void println(String s){
    print(s+"\n");
  }
  private static void print(String s){
    if(!muet){
      System.out.print(s);
    }
  }
  /**
  *{@summary Return last method &#38; class that was runing before this class.}<br>
  *It will be like this "className.functionName llineNumber".<br>
  *@version 1.41
  */
  //public only for test
  public static String getCurentClassAndMethodName(){
    StackTraceElement[] stackTrace = new Throwable().getStackTrace();
    int lenst = stackTrace.length;
    int k=0;
    String className;
    do {
      k++;
      className = stackTrace[k].getFileName();
      try {
        className = className.substring(0,className.length()-5);
      }catch (Exception e) {}
    } while (k<lenst && (className.equals("erreur")));
    return className+"."+stackTrace[k].getMethodName()+" l"+stackTrace[k].getLineNumber();
  }
  /**
  *{@summary Show curent stack trace without error file part &#38; stop game.}<br>
  *@version 1.41
  */
  public static void forceStop(){
    if(muet){return;}
    println(g.get("erreur",2,"trouver si dessous la raison et la liste des fonctions qui était en cours lors de l'arrêt forcé")+" :");
    StackTraceElement[] stackTrace = new Throwable().getStackTrace();
    int lenst = stackTrace.length;
    int k=0;
    for (StackTraceElement st : stackTrace) {
      k++;
      String className = st.getFileName();
      try {
        className = className.substring(0,className.length()-5);
      }catch (Exception e) {}
      if(!className.equals("erreur")){
        System.out.println("\t"+st);
      }
    }
    System.exit(-1);
  }

  public static void erreur(String message, String correction, boolean fatale){
    String m = "";
    if (fatale){
      m = g.get("erreur",3,"fatale")+" ";
    }
    String preMessage = "";
    try {
      if(Main.getOs().isLinux()){preMessage = "["+red+g.get("erreur").toUpperCase()+neutral+"] ";}
      else{preMessage=g.get("erreur").toUpperCase();}
    }catch (Exception e) {
      preMessage=g.get("erreur").toUpperCase();
    }
    print(preMessage + "("+getCurentClassAndMethodName()+") ");
    println(str.sToSMaj(message)+".");
    if (!correction.equals("")){
      println(g.get("erreur",6,"Correction apportée")+" : " + correction);
    }
    if (fatale){
      forceStop();
    }
  }
  public static void erreur(String message, String correction){
    erreur(message, correction, false);
  }
  public static void erreur(String message, boolean fatale){
    erreur(message, "", fatale);
  }
  public static void erreur(String message){
    erreur(message, false);
  }
  public static void alerte(String message, String correction){
    String preMessage = "";
    try {
      if(Main.getOs().isLinux()){preMessage = "["+yellow+g.get("alerte").toUpperCase()+neutral+"] ";}
      else{preMessage=g.get("alerte").toUpperCase();}
    }catch (Exception e) {
      preMessage=g.get("alerte").toUpperCase();
    }
    print(preMessage+"("+getCurentClassAndMethodName()+") ");
    if (!message.equals("")) {println(str.sToSMaj(message)+".");}
    if (correction != null && !correction.equals("")){
      println(g.get("erreur",6,"Correction apportée")+" : " + correction);
    }
  }
  public static void alerte(String message){
    alerte(message,"");
  }
  public static void alerte(){
    alerte("");
  }
  /**
  *{@summary Print info about important thing that are not important as alerte or error.}<br>
  *@version 1.37
  */
  public static void info(String message){
    String preMessage = "";
    try {
      if(Main.getOs().isLinux()){preMessage = "["+blue+g.get("info").toUpperCase()+neutral+"] ";}
      else{preMessage=g.get("info").toUpperCase();}
    }catch (Exception e) {
      preMessage=g.get("info").toUpperCase();
    }
    print(preMessage + "("+getCurentClassAndMethodName()+") ");
    println(message);
  }


  public static void erreurPasEncoreImplemente(){
    erreur(g.get("erreur",9,"La fonctionnalité n'as pas encore été implémenté"));
  }
  public static void erreurChargementImage(String nomImage){
    erreur(g.get("erreur",11,"Le chargement de l'image") +" "+nomImage+" "+ g.get("erreur",12,"n'as pas fonctionné. Assurer vous que le fichier image/ contient bien l'image en question."));
  }
  public static void erreurChargementImage(){ erreurChargementImage(g.get("erreur",10,"nom inconnu"));}
  public static void erreurGXVide(String GX){
    erreur(g.get("erreur",13,"la liste")+" "+GX+" "+g.get("erreur",14,"est vide"));
  }public static void erreurGXVide(){ erreurGXVide(g.get("erreur",15,"liste inconnue"));}
  public static void erreurPause(int x){
    erreur(g.get("erreur",16,"la tentative de pause a échouée (durée de pause"+" : "+x+" )"));
  }
  public static void erreurType(String type){
    erreur(g.getM("erreur.17")+g.get(":")+type);
  }
  // public static void erreurMissingFolder(String folderName){
  //   erreur("Can not create all file of "+folderName+" folder");
  //   throw new MissingFolderException(folderName);
  // }
  public static void alerteGUI2Dfail(String cause){
    alerte(g.getM("alerteGUI2Dfail"));
  }
}
