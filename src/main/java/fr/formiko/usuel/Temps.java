package fr.formiko.usuel;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.ecrireUnFichier;
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.types.str;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *{@summary Time information about game.}<br>
 *It contain date of 1a launch. Date of last launch and time played.
 *@author Hydrolien
 *@version 1.4
 */
public class Temps {
  /***
   *{@summary Date of 1a lauch.}<br>
   *@version 1.4
   */
  private long date1;
  /***
   *{@summary Date of last lauch.}<br>
   *@version 1.4
   */
  private long date2;
  /***
   *{@summary Time played.}<br>
   *@version 1.4
   */
  private long tempsEnJeux;
  /***
   *{@summary DateFormat.}<br>
   *@version 1.4
   */
  private String df = "yyyy/MM/dd HH:mm"; //international (Especialy Asia Europe)
  // private String df = "dd/MM/yyyy HH:mm"; // USA
  // private String df = "MM/dd/yyyy HH:mm"; // Europe, Africa, Asia, Oceania & America


  // CONSTRUCTORS --------------------------------------------------------------
  public Temps(){chargerTemps();}
  // GET SET -------------------------------------------------------------------
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
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Return a string representing time as in date format df.}
  *@version 1.23
  */
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
    r+=msToTime(tempsEnJeux);r+="\n";
    /*En français ca donne :
    Date de 1ère connection : 02/09/2020 19:41
    Date de dernière connection : 02/09/2020 20:19
    Temps en jeux total : 3 min 46 s
    */
    return r;
  }
  /**
  *{@summary Load all time informations save in Temps.txt.}
  *@version 1.23
  */
  public void chargerTemps(){
    //lecture du fichier Temps.txt
    String t [] = lireUnFichier.lireUnFichier(Main.getFolder().getFolderTemporary()+"Temps.txt");
    if(t.length<3){
      initialiserFichierTemps();
      t = lireUnFichier.lireUnFichier(Main.getFolder().getFolderTemporary()+"Temps.txt");
    } //normalement a ce stade la initialiserFichierTemps a déja réparer le fichier temps mais au cas ou on vérifi que tout est bon.
    if(t.length>=3){
      date1 = str.sToL(t[0]);
      date2 = str.sToL(t[1]);
      tempsEnJeux = str.sToL(t[2]);
    }else{
      erreur.erreur("Le fichier Temps.txt est corrompu.","les variables de temps ont été remises à 0.");
      initialiserFichierTemps();
      date1 = System.currentTimeMillis();
      date2 = System.currentTimeMillis();
      tempsEnJeux = 0;
    }
  }
  /**
  *{@summary Save time information in Temps.txt.}
  *@version 1.23
  */
  public void sauvegarder(){
    GString gs = new GString();
    gs.add(""+date1);
    gs.add(""+date2);
    gs.add(""+tempsEnJeux);
    ecrireUnFichier.ecrireUnFichier(gs,Main.getFolder().getFolderTemporary()+"Temps.txt");
  }
  //static ---------------------------------------------------------------------------
  //TODO add une méthode qui return un String de date le plus adapté possible avec un nombre défini d'unité allant de jours a ms.
  //par défaut on a 2 unité. ex : x jours y heures  ex2 : x min y s
  /**
  *{@summary return time with as specify number of unit.}<br>
  *If language file is not initialize, it will use french letter: 3j 23h 59min 10,1267s
  *@param ms times in ms.
  *@param nbrOfUnit number of units to include in the return string.
  *@param dayOn enable or disable day as a unit.
  *@version 2.7
  */
  public static String msToTime(long ms, int nbrOfUnit, boolean dayOn){
    if(nbrOfUnit<1){return "";}
    String ts [] = {"t.j","t.h","t.min","t.s","t.ms"};
    long tl [] = msToTimeLongArray(ms,dayOn);
    int k=0; int i=0;
    String r = "";
    while(k<nbrOfUnit && i<5){
      if(tl[i]>0){
        if(!r.equals("")){r+=" ";}
        if(i==3 && k+1 < nbrOfUnit && tl[i+1]>0){ //si on doit traiter les s et les ms ensembles.
          String s = ""+tl[i+1];
          while(s.length()<3){
            s="0"+s;
          }
          while(s.length() > 1 && s.charAt(s.length()-1)=='0'){
            s=s.substring(0,s.length()-1);
          }
          r+= tl[i]+g.get("t.,",",")+s+g.get(ts[i],ts[i].substring(2));
          k++;i++;
        }else{
          r+= tl[i]+g.get(ts[i],ts[i].substring(2));
        }
        k++;
      }
      i++;//pour ne pas sortir du tableau.
    }
    if(r.equals("")){
      r = tl[4]+g.get(ts[4],ts[4].substring(2));
    }
    return r;
  }
  public static String msToTime(long ms){return msToTime(ms,2,true);}
  /**
  *{@summary return time on a long [].}
  *@param ms times in ms.
  *@param dayOn enable or disable day as a unit.
  *@version 1.23
  */
  public static long [] msToTimeLongArray(long ms, boolean dayOn){
    long tr [] = new long[5];
    if(ms<0){
      tr[4]=-1;
      return tr;
    }
    int nbrMsD = 86400000; int nbrMsH = 3600000; int nbrMsM = 60000; int nbrMsS = 1000;
    long d,h,m,s;
    if(dayOn){
      d = ms / nbrMsD;
      h = (ms % nbrMsD) / nbrMsH;
    }else{
      d=0;
      h = ms / nbrMsH;
    }
    m = (ms % nbrMsH) / nbrMsM;
    s = (ms % nbrMsM) / nbrMsS;
    ms = ms % nbrMsS;
    tr[0]=d;tr[1]=h;tr[2]=m;tr[3]=s;tr[4]=ms;
    return tr;
  }
  /**
  *{@summary return current date + current hours.}
  *@version 1.23
  */
  public static String getDatePourSauvegarde(){
    String df2 = "yyyy-MM-dd HH;mm;ss";
    SimpleDateFormat sdf = new SimpleDateFormat(df2);
    return sdf.format(System.currentTimeMillis());
  }
  /**
  *{@summary Initialize time file.}
  *@version 1.23
  */
  public static void initialiserFichierTemps(){
    GString gs = new GString();
    gs.add(""+System.currentTimeMillis());
    gs.add(""+System.currentTimeMillis());
    gs.add("0");
    ecrireUnFichier.ecrireUnFichier(gs,Main.getFolder().getFolderTemporary()+"Temps.txt");
  }
  /**
  *{@summary Try to stop execution of the programme during some ms.}
  *@param ms number of ms to wait before continue.
  *@version 2.13
  */
  public static void sleep(int ms){
    pause(ms);
  }
  /**
  *{@summary Try to stop execution of the programme during 50 ms.}
  *@version 2.13
  */
  public static void sleep(){
    pause(50);
  }
  /**
  *{@summary Try to stop execution of the programme during some ms.}
  *@param ms number of ms to wait before continue.
  *@version 1.23
  */
  public static void pause(int ms){
    if(ms<1){erreur.erreurPause(ms);}
    try {
      Thread.sleep(ms);
    } catch (InterruptedException ie) {
      erreur.erreurPause(ms);
    }
  }
  /**
  *{@summary Try to stop execution of the thread during some ms.}
  *@param ms number of ms to wait before continue.
  *@param th the thread to stop.
  *@version 2.13
  */
  public static void pause(int ms, Thread th){
    if(ms<1){erreur.erreurPause(ms);}
    try {
      th.sleep(ms);
    } catch (InterruptedException ie) {
      erreur.erreurPause(ms);
    }
  }
  public static String msToS(int x){return msToS((long)x);}
  /**
  *{@summary Transform ms to s.}
  *@version 1.23
  */
  public static String msToS(long x){
    String sr = x/1000+g.get(",")+x%1000+"s";
    return sr;
  }
  // public static String msToHMS(long ms){
  //   long tempsS = ms/1000;
  //   long h = tempsS / 3600;
  //   long m = (tempsS % 3600) / 60;
  //   long s = tempsS % 60;
  //   String r="";
  //   if(h>0) {r+=h+" "+g.get("t.h")+" ";}
  //   if(m>0) {r+=m+" "+g.get("t.min")+" ";}
  //   if(s>0) {r+=s+" "+g.get("t.s");}
  //   if(h<=0 && m<=0 && s<=0) {r="0 "+g.get("t.s");}
  //   return r;
  // }
  /**
  *{@summary Print current date.}
  *@version 1.23
  */
  public static void affDateDuJour(String format){
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    Date date2b = new Date(System.currentTimeMillis());
    System.out.println(sdf.format(date2b));
  }
  public static void affDateDuJour(){affDateDuJour("dd/MM/yyyy");}
}
