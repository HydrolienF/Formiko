package fr.formiko.usuel;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.chargerLesTraductions;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.ecrireUnFichier;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.types.str;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class trad {
  private static String sep = ":";
  // FUNCTIONS -----------------------------------------------------------------
  @SuppressWarnings("unchecked")
  public static void copieTrads(){
    String tLangue[] = chargerLesTraductions.getTLangue();
    int lentl = tLangue.length;
    // on récupère les traductions déja effectuée.
    Map<String,String> trad [];
    try {
      trad = new Map[lentl];//un tableau de map
      trad[0] = chargerLesTraductions.chargerLesTraductions(0);
      for (int i=2;i<lentl ;i++ ) {
        trad[i-1]=chargerLesTraductions.chargerLesTraductions(i);
      }
      int k=0;
      for (String s :tLangue ) {
        if(!s.equals("fr")){
          copieTradBase(s,trad[k]);k++;
        }
      }
    }catch (Exception e) {
      erreur.erreur("La mise au format standard des traductions a échouée.");
    }
  }
  public static void copieTradBase(String se, Map<String,String> map){
    String t [] = lireUnFichier.lireUnFichier(Main.getFolder().getFolderStable()+Main.getFolder().getFolderLanguages()+"fr.txt");
    GString gs = new GString();
    for (String s : t) {
      if(chargerLesTraductions.estLigneDeTrad(s) && !str.contient(s,"[]",2)){//si c'est une ligne de trad qui ne correspond pas a un nom propre.
        if(str.contient(s,"test:",0)){gs.add("test:test"+str.sToSMaj(se));}
        else{gs.add(ligneTradBase(s,map));}//edited
      }else{
        gs.add(s);//not edited
      }
    }
    ecrireUnFichier.ecrireUnFichier(gs,Main.getFolder().getFolderStable()+Main.getFolder().getFolderLanguages()+se+".txt");
  }
  public static String ligneTradBase(String s, Map<String,String> map){
    String s2 = debutDeLigne(s);
    boolean changé=false;
    for (String s4 : map.keySet()) { // s4 vaut les clés.
      if (s4.equals(s2)){ //si on reconnait la clé dans la map.
        s2 = s2+sep+map.get(s4);
        changé=true; break;
      }
    }
    //dans ce cas on n'enregistre pas la valeur de la traduction :
    if(!changé){s2 = s2 +sep;}
    //Si la ligne ce termine par [], on ne le modifie pas car c'est un nom propre.
    return s2;
  }
  public static String debutDeLigne(String s){
    int lens = s.length();
    String sr="";int i=0;char c = ' ';
    if (lens !=0 ){ c = s.charAt(i);}
    if(lens > 1 && c=='\\' && s.charAt(1)=='\\'){return s;}
    for (i=1;i<lens && c!=':';i++ ) {
      sr = sr+c;
      c = s.charAt(i);
    }
    return sr;
  }
  /**
  *{@summary Translate all web site file for curent language.}<br>
  *It need to have the good path to web site file.
  *@version 1.48
  */
  public static void translateWebSiteFiles(String pathToWebSiteFile){
    // String pathToWebSiteFile = "../HydrolienF.github.io/docs/";
    String language = chargerLesTraductions.getLanguage(Main.getLanguage());
    File index = new File(pathToWebSiteFile+"Newindex.html");
    GString gsr = new GString();
    if(index.exists()){
      GString gs = lireUnFichier.lireUnFichierGs(index);
      for (String s : gs ) {
        gsr.add(replaceTranslation(s));
      }
    }else{
      String path = "unknow";
      try {
        path = index.getCanonicalPath();
      }catch (Exception e) {}
      erreur.erreur("can't read "+path);
    }
    ecrireUnFichier.ecrireUnFichier(gsr,pathToWebSiteFile+language+"/"+"index.html");

    File download = new File(pathToWebSiteFile+"Newdownload.html");
    gsr = new GString();
    if(index.exists()){
      GString gs = lireUnFichier.lireUnFichierGs(download);
      for (String s : gs ) {
        gsr.add(replaceTranslation(s));
      }
    }else{
      String path = "unknow";
      try {
        path = index.getCanonicalPath();
      }catch (Exception e) {}
      erreur.erreur("can't read "+path);
    }
    ecrireUnFichier.ecrireUnFichier(gsr,pathToWebSiteFile+language+"/"+"download.html");
  }
  /**
  *{@summary Translate a String by replacing €{key} by the translation of key.}<br>
  *@param s the String to translate.
  *@version 1.48
  */
  //TODO test
  public static String replaceTranslation(String s){
    // replaceAll​("€[^€]", String replacement)
    String sr = "";
    boolean euro=false;
    boolean readingKey=false;
    String key="";
    //.toCharArray() is maybe not the best solution but I think that always using .charAt(i); for long String will cost more time.
    for (char c : s.toCharArray()) {
      if (readingKey) { //if were reading the key.
        if(c=='}'){ //if it just end.
          readingKey=false;
          sr+=g.get(key);
          key="";
        }else{ //if were still readinf the key.
          key+=c;
        }
      }else if(c=='€'){ //if next char may be key.
        euro=true;
      }else{
        if(euro){ //if last char where €.
          euro=false;
          if(c=='{'){ //if key start.
            readingKey=true;
          }else{ //if it wasn't a key.
            sr+='€';
            sr+=c;
          }
        }else{ //if no key start & not reading key.
          sr+=c;
        }
      }
    }
    return sr;
  }
}
