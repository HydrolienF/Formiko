package fr.formiko.usuel;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.types.str;

import java.io.IOException;

/**
*{@summary Error class call to print error message.}<br>
*@author Hydrolien
*@version 2.11
*/
public class erreur {
  public static boolean muet=false;

  public static boolean getMuet(){return muet;}
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
  public static String getCurentClassAndMethodName(int classDepth){
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
    String cmName = "";
    for (int i=0;i<classDepth ; i++) {
      if(i>0){ cmName+=" - ";}
      try {
        className = stackTrace[k+i].getFileName();
        try {
          className = className.substring(0,className.length()-5);
        }catch (Exception e) {}
        cmName+= className+"."+stackTrace[k+i].getMethodName()+" l"+stackTrace[k+i].getLineNumber();
      }catch (Exception e) {
        cmName+="null";
      }
    }
    return cmName;
  }
  public static String getCurentClassAndMethodName(){return getCurentClassAndMethodName(1);}
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

  public static void erreur(String message, String correction, boolean fatale, int classDepth){
    String m = "";
    if (fatale){
      m = g.get("erreur",3,"fatale")+" ";
    }
    String preMessage = "";
    try {
      preMessage = "["+color.RED+g.get("erreur").toUpperCase()+color.NEUTRAL+"] ";
    }catch (Exception e) {
      preMessage=g.get("erreur").toUpperCase();
    }
    print(preMessage + "("+getCurentClassAndMethodName(classDepth)+") ");
    println(str.sToSMaj(message)+".");
    if (!correction.equals("")){
      println(g.get("erreur",6,"Correction apportée")+" : " + correction);
    }
    if (fatale){
      forceStop();
    }
  }
  public static void erreur(String message, String correction){
    erreur(message, correction, false, 1);
  }
  public static void erreur(String message, boolean fatale, int classDepth){
    erreur(message, "", fatale, classDepth);
  }
  public static void erreur(String message, boolean fatale){
    erreur(message, "", fatale, 1);
  }
  public static void erreur(String message, int classDepth){
    erreur(message, false, classDepth);
  }
  public static void erreur(String message){
    erreur(message, 1);
  }
  public static void alerte(String message, String correction){
    String preMessage = "";
    try {
      preMessage = "["+color.YELLOW+g.get("alerte").toUpperCase()+color.NEUTRAL+"] ";
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
  *@version 2.11
  */
  public static void info(String message, int classDepth){
    String preMessage = "";
    try {
      preMessage = "["+color.BLUE+g.get("info").toUpperCase()+color.NEUTRAL+"] ";
    }catch (Exception e) {
      preMessage=g.get("info").toUpperCase();
    }
    print(preMessage + "("+getCurentClassAndMethodName(classDepth)+") ");
    println(message);
  }
  public static void info(String message){info(message,1);}


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
  public static void alerteGUI2Dfail(String cause){
    alerte(g.getM("alerteGUI2Dfail"));
  }
}
