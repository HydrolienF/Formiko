package fr.formiko.usuel;
import fr.formiko.usuel.g;

public class erreur {
  public static String lieu0 = g.get("erreur",1,"un lieu non précisé");

  public static void arretForcé(){
    System.out.println(g.get("erreur",2,"trouver si dessous la raison et la liste des fonctions qui était en cours lors de l'arrêt forcé")+".");
    try {
      //@SuppressWarnings("unchecked")
      int x = 7/0;
    } catch (Exception e){
      e.printStackTrace();
    }
    System.exit(0);
  }

  public static void erreur(String message, String lieu, String correction, boolean fatale){
    String m = "";
    if (fatale){
      m = g.get("erreur",3,"fatale")+" ";
    }
    System.out.println(g.get("erreur",4,"Une erreur")+" " + m + g.get("erreur",5,"c'est produite dans")+" " + lieu + " : ");
    System.out.println(message);
    if (!correction.equals("")){
      System.out.println(g.get("erreur",6,"Correction apportée")+" : " + correction);
    }
    if (fatale){
      arretForcé();
    }
  }
  public static void erreur(String message, String lieu, String correction){
    erreur(message, lieu, correction, false);
  }
  public static void erreur(String message, String lieu, boolean fatale){
    erreur(message, lieu, "", fatale);
  }
  public static void erreur(String message, String lieu){
    erreur(message, lieu, false); // les erreurs sont non fatale par défaut.
  }
  public static void erreur(String message){
    erreur(message, lieu0);
  }
  public static void erreur(String message, boolean fatale){
    erreur(message, lieu0, fatale);
  }
  public static void alerte(String message, String lieu, String correction){
    System.out.println(g.get("erreur",7,"Quelque chose d'anormale est arrivé dans")+" "+ lieu +", "+g.get("erreur",8,"il n'y a peut-être pas de raison de s'inquiéter"));
    if (!message.equals("")) System.out.println(message);
    if (!correction.equals("")){
      System.out.println(g.get("erreur",6,"Correction apportée")+" : " + correction);
    }
  }
  public static void alerte(String message, String lieu){
    alerte(message,lieu,"");
  }
  public static void alerte(String message){
    alerte(message, lieu0);
  }
  public static void alerte(){
    alerte("");
  }


  public static void erreurPasEncoreImplémenté(String lieu){
    erreur(g.get("erreur",9,"La fonctionnalité n'as pas encore été implémenté"),lieu);
  }
  public static void erreurPasEncoreImplémenté(){ erreurPasEncoreImplémenté(lieu0);}
  public static void erreurChargementImage(String nomImage){
    erreur(g.get("erreur",11,"Le chargement de l'image") +" "+nomImage+" "+ g.get("erreur",12,"n'as pas fonctionné. Assurer vous que le fichier data/imageFormiko/ contient bien l'image en question."));
  }
  public static void erreurChargementImage(){ erreurChargementImage(g.get("erreur",10,"nom inconnu"));}
  public static void erreurGXVide(String GX){
    erreur(g.get("erreur",13,"la liste")+" "+GX+" "+g.get("erreur",14,"est vide"));
  }public static void erreurGXVide(){ erreurGXVide(g.get("erreur",15,"liste inconnue"));}
  public static void erreurPause(int x){
    erreur(g.get("erreur",16,"la tentative de pause a échouée (durée de pause"+" : "+x+" )"));
  }
  public static void erreurType(String type, String lieu){
    erreur(g.getM("erreur.17")+g.get(":")+type,lieu);
  }public static void erreurType(String type){erreurType(type,lieu0);}
}