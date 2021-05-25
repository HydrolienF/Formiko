package fr.formiko.usuel;

import fr.formiko.usuel.listes.GString;
import fr.formiko.formiko.Main;

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
    Main.initialisation();
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
  *{@summary Create a badge for github readme.}<br>
  *@param label the 1a text part.
  *@param value the 2a text part.
  *@param color the color of the 2a part.
  *@version 1.46
  */
  private static GString createBadge(String label, int value, String color){
    GString gs = new GString();
    gs.add("{");
    gs.add("\t\"schemaVersion\": 1,");
    gs.add("\t\"label\": \""+label+"\",");
    gs.add("\t\"message\": "+value+",");
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
  //TODO update the versions use by web site.
  /**
  *{@summary Create doc badges for github readme.}<br>
  *@version 1.46
  */
  private static void createDocBadges(){
    stats.statsJavadoc("src/main/",true);
    GString gs = createBadge("Lines",stats.sommeNbrDeLigneG,"blue");
    ecrireUnFichier.ecrireUnFichier(gs,PATH_TO_JSON+"linesBadges.json");
    gs = createBadge("Classes",stats.sommeDesClassG,"blue");
    ecrireUnFichier.ecrireUnFichier(gs,PATH_TO_JSON+"classesBadges.json");
    int percentageDoc = (stats.sommeDesComG*100)/stats.sommeDesFctLG;
    gs = createBadge("Doc",percentageDoc+"%",getColor(percentageDoc));
    ecrireUnFichier.ecrireUnFichier(gs,PATH_TO_JSON+"docBadges.json");
  }
  /**
  *{@summary Create language badges for github readme.}<br>
  *@version 1.46
  */
  private static void createLanguagesBadges(){
    chargerLesTraductions.iniTLangue();
    chargerLesTraductions.créerLesFichiers();
    g.setMap(chargerLesTraductions.chargerLesTraductions(1));
    int val = chargerLesTraductions.getPourcentageTraduit(0);
    GString gs = createBadge("Doc", val+"%", getColor(val));
    ecrireUnFichier.ecrireUnFichier(gs,PATH_TO_JSON+"eoBadges.json");
    val = chargerLesTraductions.getPourcentageTraduit(1);
    gs = createBadge("Doc", val+"%", getColor(val));
    ecrireUnFichier.ecrireUnFichier(gs,PATH_TO_JSON+"frBadges.json");
    val = chargerLesTraductions.getPourcentageTraduit(2);
    gs = createBadge("Doc", val+"%", getColor(val));
    ecrireUnFichier.ecrireUnFichier(gs,PATH_TO_JSON+"enBadges.json");
  }
  /**
  *{@summary return a color depending of percentage.}<br>
  *@param percentage the percentage to choose the color.
  *@return a color from red to green.
  *@version 1.46
  */
  private static String getColor(int percentage){
    String color;
    if(percentage>80){
      color="green";
    }else if(percentage>60){
      color="yellow";
    }else if(percentage>20){
      color="orrange";
    }else{
      color = "red";
    }
    return color;
  }
}
