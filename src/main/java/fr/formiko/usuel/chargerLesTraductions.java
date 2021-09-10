package fr.formiko.usuel;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;

import java.io.File;
import java.util.HashMap;

/**
*{@summary Loard translation file class.}<br>
*@author Hydrolien
*@version 1.5
*/
public class chargerLesTraductions {
  private static HashMap<String, String> map;
  private static String rep=Main.getFolder().getFolderStable()+Main.getFolder().getFolderLanguages();
  private static String tLangue[]=null;
  private static int defaultLanguage=2;
  // get set -------------------------------------------------------------------
  public static String [] getTLangue(){return tLangue;}
  public static void setTLangue(String t []){tLangue=t;}
  public static String getRep(){return rep;}
  public static void setRep(String s){rep = str.sToDirectoryName(s);}
  public static void setRep(){setRep(Main.getFolder().getFolderStable()+Main.getFolder().getFolderLanguages());}
  public static HashMap<String, String> getMap(){return map;}
  public static void iniMap(){map = new HashMap<>();}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary get the int that corresponds to the language String.}<br>
  *@param x language id.
  *@return language String in ISO code 639-1 if tLangue is correct.
  *@version 1.5
  */
  public static String getLanguage(int x){
    if(tLangue == null || x<0 || x>=tLangue.length){
      int l = 0;
      if(tLangue!=null){l=tLangue.length;}
      erreur.erreur("langue non reconnu parmi les "+l+" langue(s) disponible(s).","\"en\" retourné");
      return "en";
    }
    return tLangue[x];
  }
  /**
  *{@summary get the String that corresponds to the language int.}<br>
  *An error will return 2, the id of "en" (english) (except if tLangue isn't correctly loard (return -1;))
  *@param s language String in ISO code 639-1.
  *@version 1.37
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
  /**
  *Return defaultLanguage if it is aviable or -1.
  *@version 1.37
  */
  public static int getDefautlLanguage(){
    if(tLangue.length<defaultLanguage+1){return -1;}
    return defaultLanguage;
  }
  /**
  *{@summary Loard language file "langue.csv".}<br>
  *If it fail only "en" will be aviable.
  *@version 1.5
  */
  public static boolean iniTLangue(){
    try {
      String t []=lireUnFichier.lireUnFichier(rep+"langue.csv");
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
  *@version 1.5
  */
  public static boolean créerLesFichiers(){
    for (String s :tLangue ) {
      File f = new File(rep+str.sToFileName(s)+".txt");
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
  *@version 1.5
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
  *@version 1.7
  */
  public static String [] getTableauDesTrad(int langue){
    //String tDéfaut [] = lireUnFichier.lireUnFichier(rep+"fr.txt");
    String t [] = new String[0];
    try{
      debug.débogage("chargement de la langue "+getLanguage(langue));
      t=lireUnFichier.lireUnFichier(rep+getLanguage(langue)+".txt");
    }catch (Exception e) {
      erreur.erreur("Echec du chargement de la langue spécifiée","en choisi par défaut");
      t=lireUnFichier.lireUnFichier(rep+"en.txt");
    }
    return t;
  }
  /**
  *{@summary Load translation for nation name.}<br>
  *@version 1.26
  */
  public static String []getTableauDesNationsName(){
    String t [] = new String[0];
    try{
      t=lireUnFichier.lireUnFichier("docs/cc/"+"nationsName"+".csv");
    }catch (Exception e) {
      erreur.erreur("Echec du chargement de nationsName");
    }
    return t;
  }
  /**
  *{@summary get an array of command.}<br>
  *@version 1.7
  */
  public static String [] getTableauDesCmd(){
    String t [] = new String[0];
    try{
      debug.débogage("chargement des commandes");
      t=lireUnFichier.lireUnFichier(rep+"cmd"+".txt");
    }catch (Exception e) {
      erreur.erreur("Echec du chargement des commandes");
    }
    return t;
  }
  /**
  *{@summary Load translation for a given language.}<br>
  *It included command.
  *@param langue id if the language
  *@version 1.7
  */
  public static HashMap<String, String> chargerLesTraductions(int langue){
    debug.débogage("Chargement des textes");//on lit le fichier de langue
    map = chargerLesTraductionsSansCommande(langue);
    String t2[] = getTableauDesCmd();
    for (String s : t2) {//on ajoute toutes les commande qu'on peu add.
      addObjetMap(s);
    }
    return map;
  }
  /**
  *{@summary Load translation for a given language.}<br>
  *It don't included command.
  *@param langue id if the language
  *@version 1.7
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
  *@version 1.26
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
  *@version 1.7
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
  *@version 1.7
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
  *@version 1.7
  */
  public static int getPourcentageTraduit(int langue){
    int x = 0;
    String [] t= new String [0];
    try {
      t=lireUnFichier.lireUnFichier(rep+getLanguage(langue)+".txt");
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
  *@version 1.7
  */
  public static int getPourcentageTraduitAutomatiquement(int langue){
    int x = 0;
    String [] t= new String [0];
    try {
      t=lireUnFichier.lireUnFichier(rep+getLanguage(langue)+".txt");
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
  *@version 1.7
  */
  public static void affPourcentageTraduit(){
    if(tLangue==null){iniTLangue();}
    int lentl=getTLangue().length;
    for (int i=0;i<lentl ;i++) {
      String s = "";int x=getPourcentageTraduitAutomatiquement(i); if(x>0){s=" ("+x+"% traduit automatiquement)";}
      int y = getPourcentageTraduit(i);
      if(x>0){
        System.out.println(getLanguage(i)+" : "+y+"%"+s);
      }
    }
  }
  public static boolean fini(String s){return !str.contient(s,":",2);}
  /**
  *{@summary Add untranslated key to the actual Map.}<br>
  *For all key in english map, if key isn't in g.getMap() we add it.
  *@version 1.33
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
