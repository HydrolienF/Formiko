package fr.formiko.usuel;

import fr.formiko.usuel.listes.GString;

/**
*{@summary Create badges for github readme.}<br>
*@version 1.46
*@author Hydrolien
*/
public class createBadges{
  private static String PATH_TO_JSON = ".github/badges/json/";
  /**
  *{@summary Create badges for github readme.}<br>
  *@version 1.46
  */
  public static void createBadges(){
    createVersionsBadges();
    createDocBadges();
    createLanguagesBadges();
  }
  /**
  *{@summary Create a badge for github readme.}<br>
  *@param label the 1a text part.
  *@param message the 2a text part.
  *@param color the color of the 2a part.
  *@version 1.46
  */
  private static GString createBadge(String label, String message, String color){
    GString gs = new GString();
    gs.add("{");
    gs.add("\t\"schemaVersion\": 1,");
    gs.add("\t\"label\": \""+label+"\",");
    gs.add("\t\"message\": \""+message+"\",");
    gs.add("\t\"color\": \""+color+"\",");
    gs.add("}");
    return gs;
  }
  /**
  *{@summary Create version badges for github readme.}<br>
  *@version 1.46
  */
  private static void createVersionsBadges(){
    GString gsIn = lireUnFichier.lireUnFichierGs("version.md");
    String version = "x.x.x";
    if(gsIn.length()>0){version = gsIn.getItem(0);}
    GString gsOut = createBadge("Version", version,"blue");
    ecrireUnFichier.ecrireUnFichier(gsOut, PATH_TO_JSON+"versionBadges.json");
  }
  /**
  *{@summary Create doc badges for github readme.}<br>
  *@version 1.46
  */
  private static void createDocBadges(){
  //   GString gs = createBadge("Version","1.43.6","blue");
  //   ecrireUnFichier.ecrireUnFichier(gs,PATH_TO_JSON+"versionBadges.json");
  }
  /**
  *{@summary Create language badges for github readme.}<br>
  *@version 1.46
  */
  private static void createLanguagesBadges(){
  //   GString gs = createBadge("Version","1.43.6","blue");
  //   ecrireUnFichier.ecrireUnFichier(gs,PATH_TO_JSON+"versionBadges.json");
  }
}
