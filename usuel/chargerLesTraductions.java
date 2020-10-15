package fr.formiko.usuel;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import java.util.Map;
import java.util.HashMap;
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.conversiondetype.str;
import fr.formiko.usuel.liste.GString;
import java.io.*;

public class chargerLesTraductions {
  private static Map<String, String> map;
  private static String rep="data/langue/";
  private static String tLangue[]={"eo","fr","en","es","de","ru","ar"};
  // get set -------------------------------------------------------------------
  public static String getLangue(int x){
    String tLangue[] = getTLangue();
    try {
      return tLangue[x];
    }catch (Exception e) {
      erreur.erreur("langue non reconnu parmi les "+tLangue.length+" langue disponible.","chargerLesTraductions.getLangue","\"en\" retourné");
      return "en";
    }
  }
  public static String [] getTLangue(){
    return tLangue;
  }
  public static int getLangue(String s){
    int k=0;
    for (String s2 : tLangue) {
      if(s2.equals(s)){return k;}
      k++;
    }
    return 2;
  }
  public static String getRep(){return rep;}
  public static void iniTLangue(){
    try {
      String t []=lireUnFichier.lireUnFichier(rep+"langue.csv");
      tLangue=new String[t.length];int k=0;
      for (String s : t ) {
        String s2 = s.split(",")[0];
        tLangue[k]=s2;k++;
      }
    }catch (Exception e) {
      erreur.erreur("Impossible de charger tLangue.");
    }
  }
  public static void créerLesFichiers(){
    String tLangue[]=getTLangue();
    for (String s :tLangue ) {
      File f = new File(rep+s+".txt");
      try {
        f.createNewFile();
      }catch (Exception e) {
        erreur.erreur("Impossible de créer un fichier de trad","chargerLesTraductions.créerLesFichiers");
      }
    }
  }
  public static boolean estLigneDeTrad(String s){
    if(s.length()<2){return false;}
    if(s.charAt(0)=='/' && s.charAt(1)=='/'){return false;}
    if(str.nbrDeX(s,':')<1){return false;}//si il y a un nombre différent de fois ":" que 1 fois.
    //return s.length()>2 && (s.charAt(0)!='\\' || s.charAt(1)!='\\');// si la ligne n'est pas vide ou pas un commentaire.
    return true;
  }
  public static String [] getTableauDesTrad(int langue){
    //String tDéfaut [] = lireUnFichier.lireUnFichier(rep+"fr.txt");
    String t [] = new String[0];
    try{
      debug.débogage("chargement de la langue "+getLangue(langue));
      t=lireUnFichier.lireUnFichier(rep+getLangue(langue)+".txt");
    }catch (Exception e) {
      erreur.erreur("Echec du chargement de la langue spécifiée","fr choisi par défaut");
      t=lireUnFichier.lireUnFichier(rep+"fr.txt");
    }
    return t;
  }
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
  // Fonctions propre -----------------------------------------------------------
  public static Map<String, String> chargerLesTraductions(int langue){
    debug.débogage("Chargement des textes");//on lit le fichier de langue
    map = new HashMap<>();
    String t[] = getTableauDesTrad(langue);
    String t2[] = getTableauDesCmd();
    for (String s : t) {//on ajoute toutes les lignes qu'on peu ajouter.
      ajouterObjetMap(s);
    }
    for (String s : t2) {//on ajoute toutes les commande qu'on peu ajouter.
      ajouterObjetMap(s);
    }
    return map;
  }
  public static Map<String, String> chargerLesTraductionsSansCommande(int langue){
    map = new HashMap<>();
    String t[] = getTableauDesTrad(langue);
    for (String s : t) {//on ajoute toutes les lignes qu'on peu ajouter.
      ajouterObjetMap(s);
    }
    return map;
  }
  private static void ajouterObjetMap(String s){
    // si la ligne est vide ou si elle commence par "//", on return directement.
    if(!estLigneDeTrad(s)){return;}
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

    i++;
    if(i<lens-1){
      c = s.charAt(i);
      if(c!='\\'){s2 = s2+c;}//maj(c);}
    }

    while(i<lens-1){
      i++; c = s.charAt(i);
      if(c!='\\'){s2 = s2+c;}
    }
    debug.débogage("Ajout du couple clé valeur  "+s1+" : "+s2);
    map.put(s1,s2);
  }
  public static void ajouterTradAuto(){
    int lentl=getTLangue().length;
    new ThTrad(0);
    for (int i=2;i<lentl ;i++ ) {
      new ThTrad(i);
    }
  }
  private static char maj(char c){
    if(c < 123 && c > 96){ return (char) (c-32);}
    if(c=='é'){ return 'É';}
    if(c=='è'){ return 'È';}
    if(c=='ë'){ return 'Ë';}
    if(c=='ê'){ return 'Ê';}
    if(c=='ĉ'){ return 'Ĉ';}
    if(c=='ç'){ return 'Ç';}
    if(c=='ĵ'){ return 'Ĵ';}
    if(c=='â'){ return 'Â';}
    if(c=='ĝ'){ return 'Ĝ';}

    return c;
  }
  public static int getPourcentageTraduit(int langue){
    //int x = chargerLesTraductions(langue).size();fore
    //int x = 0; Map <String,String> map = chargerLesTraductions(langue);
    int x = 0;
    String [] t= new String [0];
    try {
      t=lireUnFichier.lireUnFichier(rep+getLangue(langue)+".txt");
    }catch (Exception e) {}
      for (String s : t ) {
        if(estLigneDeTrad(s)){
          if(fini(s)){ x++;}
        }
      }
    int xFr = chargerLesTraductionsSansCommande(1).size();
    return (x*100)/xFr;
  }
  public static int getPourcentageTraduitAutomatiquement(int langue){
    int x = 0;
    String [] t= new String [0];
    try {
      t=lireUnFichier.lireUnFichier(rep+getLangue(langue)+".txt");
    }catch (Exception e) {}
      for (String s : t ) {
        if(s.length()>6 && s.substring(s.length()-6).equals("[auto]")){
          x++;
        }
      }
    int xFr = chargerLesTraductionsSansCommande(1).size();
    return (x*100)/xFr;
  }
  public static void affPourcentageTraduit(){
    int lentl=getTLangue().length;
    for (int i=0;i<lentl ;i++) {
      String s = "";int x=getPourcentageTraduitAutomatiquement(i); if(x>0){s=" ("+x+"% traduit automatiquement)";}
      int y = getPourcentageTraduit(i);
      if(x>0){
        System.out.println(getLangue(i)+" : "+y+"%"+s);
      }
    }
  }
  public static boolean fini(String s){
    int lens = s.length();
    for (int i=0;i<lens-1;i++ ) {
      if (s.charAt(i)==':'){ return true;} // si il y a au moins 1 char après les :
    }return false; // sinon
  }
}