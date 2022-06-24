package fr.formiko.usual;

import fr.formiko.usual.ReadFile;
import fr.formiko.usual.structures.listes.GString;
import fr.formiko.usual.tableau;
import fr.formiko.usual.types.str;
import fr.formiko.usual.Info;

import java.io.File;
import java.util.HashMap;

/**
*{@summary Loard translation file class.}<br>
*@author Hydrolien
*@lastEditedVersion 2.25
*/
public class chargerLesTraductions {
  private static HashMap<String, String> map;
  private static String rep;
  private static String tLangue[]=null;
  private static int defaultLanguage=2;
  // get set -------------------------------------------------------------------
  public static String [] getTLangue(){return tLangue;}
  public static void setTLangue(String t []){tLangue=t;}
  /**
  *{@summary Return the translation folder (if it have been set) or the default one.}<br>
  *@lastEditedVersion 2.25
  */
  public static String getRep(){
    if(rep==null){
      return Folder.getFolder().getFolderStable()+Folder.getFolder().getFolderLanguages();
    }
    return rep;
  }
  public static void setRep(String s){rep = str.sToDirectoryName(s);}
  public static HashMap<String, String> getMap(){return map;}
  public static void iniMap(){map = new HashMap<>();}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Get the int that corresponds to the language String.}<br>
  *@param id language id
  *@param fisrtPartie if true don't print error concerning missing data
  *@return language String in ISO code 639-1 if tLangue is correct.
  *@lastEditedVersion 2.25
  */
  public static String getLanguage(int id, boolean firstPartie){
    if(tLangue == null || id<0 || id>=tLangue.length){
      int l = 0;
      if(tLangue!=null){l=tLangue.length;}
      if(!firstPartie){
        erreur.erreur("langue "+id+" non reconnu parmi les "+l+" langue(s) disponible(s).",10);
      }
      // new Info("langue non reconnu parmi les "+l+" langue(s) disponible(s).").setType(2).setFix("\"en\" retourné").setClassDepth(2).print();
      return "en";
    }
    return tLangue[id];
  }
  /**
  *{@summary Get the int that corresponds to the language String.}<br>
  *@param id language id
  *@return language String in ISO code 639-1 if tLangue is correct.
  *@lastEditedVersion 2.25
  */
  public static String getLanguage(int id){
    return getLanguage(id, false);
  }
  /**
  *{@summary get the String that corresponds to the language int.}<br>
  *An error will return 2, the id of "en" (english) (except if tLangue isn't correctly loard (return -1;))
  *@param s language String in ISO code 639-1.
  *@lastEditedVersion 1.37
  */
  public static int getLanguage(String s){
    if(tLangue == null){ return -1;}
    if (s==null || s.equals("")) { return getDefautlLanguage();}
    int k=0;
    for (String s2 : tLangue) {
      if(s2.equals(s)){return k;}
      k++;
    }
    return getDefautlLanguage();
  }
  public static String getLanguageAsString(int x){return getLanguage(x);}
  public static String getLanguageAsString(String x){return x;}
  /**
  *Return defaultLanguage if it is aviable or -1.
  *@lastEditedVersion 1.37
  */
  public static int getDefautlLanguage(){
    if(tLangue.length<defaultLanguage+1){return -1;}
    return defaultLanguage;
  }
  /**
  *{@summary Loard language file "langue.csv".}<br>
  *If it fail only "en" will be aviable.
  *@lastEditedVersion 1.5
  */
  public static boolean iniTLangue(){
    try {
      String t []=ReadFile.readFileArray(getRep()+"langue.csv");
      if(t==null){throw new NullPointerException();}//on passe dans le catch.
      if(t.length==0){throw new Exception();}//TODO find a better Exception
      tLangue=new String[t.length];int k=0;
      for (String s : t ) {
        String s2 = s.split(",")[0];
        tLangue[k]=s2;k++;
      }
      return true;
    }catch (Exception e) {
      erreur.erreur("Impossible de charger tLangue.");
      tLangue=new String [1]; tLangue[0]="en";
      return false;
    }
  }
  /**
  *{@summary Check that every language file exists and create is if it's need.}<br>
  *@lastEditedVersion 1.5
  */
  public static boolean créerLesFichiers(){
    for (String s :tLangue ) {
      File f = new File(getRep()+str.sToFileName(s)+".txt");
      try {
        f.createNewFile();
      }catch (Exception e) {
        erreur.erreur("Impossible de créer un fichier de trad");
        return false;
      }
    }
    return true;
  }
  /**
  *{@summary Check that the line is a translation line.}<br>
  *@param s line to check
  *@lastEditedVersion 1.5
  */
  public static boolean estLigneDeTrad(String s){
    if(s==null){return false;}
    if(s.length()<2){return false;}//minimum = "c:"
    //if(s.charAt(0)=='/' && s.charAt(1)=='/'){return false;}
    if(str.contient(s,"//",0)){return false;}
    if(str.contient(s,":",0)){return false;}
    if(str.nbrDeX(s,':')==0){return false;}//si il y a un nombre différent de fois ":" que 1 fois.
    //return s.length()>2 && (s.charAt(0)!='\\' || s.charAt(1)!='\\');// si la ligne n'est pas vide ou pas un commentaire.
    return true;
  }



  /**
  *{@summary get an array of translation for a given language.}<br>
  *@param langue id if the language
  *@lastEditedVersion 1.7
  */
  public static String [] getTableauDesTrad(int langue){
    //String tdefault [] = ReadFile.readFileArray(getRep()+"fr.txt");
    String t [] = new String[0];
    try{
      debug.débogage("chargement de la langue "+getLanguage(langue));
      t=ReadFile.readFileArray(getRep()+getLanguage(langue)+".txt");
    }catch (Exception e) {
      erreur.erreur("Echec du chargement de la langue spécifiée","en choisi par défaut");
      t=ReadFile.readFileArray(getRep()+"en.txt");
    }
    return t;
  }
  /**
  *{@summary Load translation for nation name.}<br>
  *@lastEditedVersion 1.26
  */
  public static String []getTableauDesNationsName(){
    String t [] = new String[0];
    try{
      t=ReadFile.readFileArray("docs/cc/"+"nationsName"+".csv");
    }catch (Exception e) {
      erreur.erreur("Echec du chargement de nationsName");
    }
    return t;
  }
  /**
  *{@summary get an array of command.}<br>
  *@lastEditedVersion 1.7
  */
  public static String [] getTableauDesCmd(){
    String t [] = new String[0];
    try{
      debug.débogage("chargement des commandes");
      t=ReadFile.readFileArray(getRep()+"cmd"+".txt");
    }catch (Exception e) {
      erreur.erreur("Echec du chargement des commandes");
    }
    return t;
  }
  /**
  *{@summary Load translation for a given language.}<br>
  *It included command.
  *@param langue id if the language
  *@lastEditedVersion 1.7
  */
  public static HashMap<String, String> chargerLesTraductions(int langue){
    debug.débogage("Chargement des textes");//on lit le fichier de langue
    map = chargerLesTraductionsSansCommande(langue);
    String t2[] = getTableauDesCmd();
    for (String s : t2) {//on ajoute toutes les commandes qu'on peu add.
      addObjetMap(s);
    }
    return map;
  }
  /**
  *{@summary Load translation for a given language.}<br>
  *It don't included command.
  *@param langue id if the language
  *@lastEditedVersion 1.7
  */
  public static HashMap<String, String> chargerLesTraductionsSansCommande(int langue){
    iniMap();
    String t[] = getTableauDesTrad(langue);
    for (String s : t) {//on ajoute toutes les lignes qu'on peu add.
      addObjetMap(s);
    }
    return map;
  }
  /**
  *{@summary Load translation for nation name.}<br>
  *@lastEditedVersion 1.26
  */
  public static HashMap<String, String> chargerLesNationsName(){
    iniMap();
    String t[] = getTableauDesNationsName();
    for (String s : t) {//on ajoute toutes les lignes qu'on peu add.
      addObjetMap(s);
    }
    return map;
  }
  /**
  *{@summary Add a translated line on the actual map.}<br>
  *@param s Translated line.
  *@lastEditedVersion 1.7
  */
  public static void addObjetMap(String s){
    if(!estLigneDeTrad(s)){return;}
    //getPosDu1a ":"
    //coupe en 2.
    int lens = s.length();
    debug.débogage("Ligne non vide et non commentaire idendifiée");
    String s1 = "";
    String s2 = "";
    int i=0;
    debug.débogage("élément de "+lens+"identifié");
    char c = s.charAt(i);
    while(c != ':' && i<lens-1){
      if(c!='\\'){s1 = s1+c;}
      i++; c = s.charAt(i);
    }
    //lower char as 1a char
    if(i<lens-1){
      i++; c = s.charAt(i);
      s2 = s2+(c+"").toLowerCase();
    }
    while(i<lens-1){
      i++; c = s.charAt(i);
      s2 = s2+c;
    }
    debug.débogage("Ajout du couple clé valeur  "+s1+" : "+s2);
    map.put(s1,s2);
  }
  /**
  *{@summary Add auto translation for every languages.}<br>
  *Please refert to ThTrad to have more informations
  *@lastEditedVersion 1.7
  */
  public static void addTradAuto(){
    if(tLangue==null){iniTLangue();}
    int lentl=getTLangue().length;
    new ThTrad(0);
    for (int i=2;i<lentl ;i++ ) {
      new ThTrad(i);
    }
  }
  /**
  *{@summary Count the %age translated.}<br>
  *It don't included command (that are not translate).
  *@param langue id if the language
  *@lastEditedVersion 1.7
  */
  public static int getPourcentageTraduit(int langue){
    int x = 0;
    String [] t= new String [0];
    try {
      t=ReadFile.readFileArray(getRep()+getLanguage(langue)+".txt");
    }catch (Exception e) {}
      for (String s : t ) {
        if(estLigneDeTrad(s)){
          if(fini(s)){ x++;}
        }
      }
    int xFr = chargerLesTraductionsSansCommande(1).size();
    if (xFr==0){return -1;}
    return (x*100)/xFr;
  }
  /**
  *{@summary Count the %age translated automatically.}<br>
  *It don't included command (that are not translate).
  *@param langue id if the language
  *@lastEditedVersion 1.7
  */
  public static int getPourcentageTraduitAutomatiquement(int langue){
    int x = 0;
    String [] t= new String [0];
    try {
      t=ReadFile.readFileArray(getRep()+getLanguage(langue)+".txt");
    }catch (Exception e) {}
      for (String s : t ) {
        if(s.length()>6 && s.substring(s.length()-6).equals("[auto]")){
          x++;
        }
      }
    int xFr = chargerLesTraductionsSansCommande(1).size();
    return (x*100)/xFr;
  }
  /**
  *{@summary Print getPourcentageTraduitAutomatiquement and getPourcentageTraduit data for every languages.}<br>
  *@lastEditedVersion 1.7
  */
  public static void affPourcentageTraduit(){
    if(tLangue==null){iniTLangue();}
    int lentl=getTLangue().length;
    for (int i=0;i<lentl ;i++) {
      String s = "";int x=getPourcentageTraduitAutomatiquement(i); if(x>0){s=" ("+x+"% traduit automatiquement)";}
      int y = getPourcentageTraduit(i);
      if(x>0){
        erreur.println(getLanguage(i)+" : "+y+"%"+s);
      }
    }
  }
  public static boolean fini(String s){return !str.contient(s,":",2);}
  /**
  *{@summary Add untranslated key to the actual Map.}<br>
  *For all key in english map, if key isn't in g.getMap() we add it.
  *@lastEditedVersion 1.33
  */
  public static void completMapWithFullTranslatedLanguage(){
    HashMap<String,String> hm = chargerLesTraductions(getLanguage("en"));
    for (String key : hm.keySet() ) {
      if(!g.exist(key)){
        g.getMap().put(key,hm.get(key));
      }
    }
  }
}
