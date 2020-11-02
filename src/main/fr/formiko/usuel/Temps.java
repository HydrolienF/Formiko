package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.conversiondetype.str;
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.ecrireUnFichier;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *{@summary Time information about game.<br/>}
 *It contain date of 1a launch. Date of last launch and time played.
 *@author Hydrolien
 *@version 1.4
 */
public class Temps {
  /**
   *{@summary Date of 1a lauch.<br/>}
   *@version 1.4
   */
  private long date1;
  /**
   *{@summary Date of last lauch.<br/>}
   *@version 1.4
   */
  private long date2;
  /**
   *{@summary Time played.<br/>}
   *@version 1.4
   */
  private long tempsEnJeux;
  /**
   *{@summary DateFormat.<br/>}
   *@version 1.4
   */
  private String df = "dd/MM/yyyy HH:mm";


  // CONSTRUCTEUR ---------------------------------------------------------------
  public Temps(){chargerTemps();}
  // GET SET --------------------------------------------------------------------
  public long getDate1(){ return date1;}
  public void setDate1(long x){date1=x;}
  public long getDate2(){ return date2;}
  public void setDate2(long x){date2=x;}
  public void actualiserDate2(){date2=System.currentTimeMillis();}
  public long getTempsEnJeux(){ return tempsEnJeux;}
  public void setTempsEnJeux(long x){tempsEnJeux=x;}
  public void addTempsEnJeux(long x){ setTempsEnJeux(getTempsEnJeux()+x);}
  public String getDf(){return df;}
  public void setDf(String s){df=s;}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    String r="";
    SimpleDateFormat sdf = new SimpleDateFormat(df);
    r+=g.getM("date1") + " : ";
    Date date1b = new Date(date1);
    r+=sdf.format(date1b);r+="\n";
    r+=g.getM("date2") + " : ";
    Date date2b = new Date(date2);
    r+=sdf.format(date2b);r+="\n";
    r+=g.getM("tempsEnJeux") + " : ";
    r+=msToHMS(tempsEnJeux);r+="\n";
    /*En français ca donne :
    Date de 1ère connection : 02/09/2020 19:41
    Date de dernière connection : 02/09/2020 20:19
    Temps en jeux total : 3 min 46 s
    */
    return r;
  }
  public void afficheToi(){System.out.println(this);}
  public void chargerTemps(){
    //lecture du fichier data/Temps.txt
    String t [] = lireUnFichier.lireUnFichier("data/Temps.txt");
    if(t.length<3){
      initialiserFichierTemps();
      t = lireUnFichier.lireUnFichier("data/Temps.txt");
    } //normalement a ce stade la initialiserFichierTemps a déja réparer le fichier temps mais au cas ou on vérifi que tout est bon.
    if(t.length>=3){
      date1 = str.sToL(t[0]);
      date2 = str.sToL(t[1]);
      tempsEnJeux = str.sToL(t[2]);
    }else{
      erreur.erreur("Le fichier data/Temps.txt est corrompu.","Temps.chargerTemps","les variables de temps ont été remises à 0.");
      initialiserFichierTemps();
      date1 = System.currentTimeMillis();
      date2 = System.currentTimeMillis();
      tempsEnJeux = 0;
    }
  }
  public void sauvegarder(){
    GString gs = new GString();
    gs.add(""+date1);
    gs.add(""+date2);
    gs.add(""+tempsEnJeux);
    ecrireUnFichier.ecrireUnFichier(gs,"data/Temps.txt");
  }
  //static ---------------------------------------------------------------------------
  //TODO ajouter une méthode qui return un STring de date le plus adapté possible avec un nombre défini d'unité allant de jours a ms.
  //par défaut on a 2 unité. ex : x jours y heures  ex2 : x min y s
  public static String getDatePourSauvegarde(){
    String df2 = "dd-MM-yyyy HH-mm-ss";
    SimpleDateFormat sdf = new SimpleDateFormat(df2);
    return sdf.format(System.currentTimeMillis());
  }
  public static void initialiserFichierTemps(){
    GString gs = new GString();
    gs.add(""+System.currentTimeMillis());
    gs.add(""+System.currentTimeMillis());
    gs.add("0");
    ecrireUnFichier.ecrireUnFichier(gs,"data/Temps.txt");
  }
  public static void pause(int millis){
    if(millis<1){erreur.erreurPause(millis);}
    try {
        Thread.sleep(millis);
    } catch (InterruptedException ie) {
        erreur.erreurPause(millis);
    }
  }
  public static String msToS(int x){return msToS((long)x);}
  public static String msToS(long x){
    String sr = x/1000+g.get(",")+x%1000+"s";
    return sr;
  }
  public static String msToHMS(long ms){
    long tempsS = ms/1000;
    //long h = (long) (tempsS / 3600);
    //long m = (long) ((tempsS % 3600) / 60);
    //long s = (long) (tempsS % 60);
    long h = tempsS / 3600;
    long m = (tempsS % 3600) / 60;
    long s = tempsS % 60;
    String r="";
    if(h>0) {r+=h+" "+g.get("t.h")+" ";}
    if(m>0) {r+=m+" "+g.get("t.min")+" ";}
    if(s>0) {r+=s+" "+g.get("t.s");}
    if(h<=0 && m<=0 && s<=0) {r="0 "+g.get("t.s");}
    return r;
  }
  public static void affDateDuJour(){
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date date2b = new Date(System.currentTimeMillis());
    System.out.println(sdf.format(date2b));
  }
}
