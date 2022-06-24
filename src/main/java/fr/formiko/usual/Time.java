package fr.formiko.usual;

import fr.formiko.usual.ecrireUnFichier;
import fr.formiko.usual.ReadFile;
import fr.formiko.usual.structures.listes.GString;
import fr.formiko.usual.types.str;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
*{@summary Time information about game.}<br>
*It contain date of 1a launch. Date of last launch and time played.
*@author Hydrolien
*@lastEditedVersion 2.25
*/
public class Time {
  /***
  *{@summary Date of 1a lauch.}<br>
  *@lastEditedVersion 1.4
  */
  private long date1;
  /***
  *{@summary Date of last lauch.}<br>
  *@lastEditedVersion 1.4
  */
  private long date2;
  /***
  *{@summary Time played.}<br>
  *@lastEditedVersion 1.4
  */
  private long tempsEnJeux;
  /***
  *{@summary DateFormat.}<br>
  *@lastEditedVersion 1.4
  */
  // private String df = "yyyy/MM/dd HH:mm"; //international (Especialy Asia Europe)
  // private String df = "dd/MM/yyyy HH:mm"; // USA
  // private String df = "MM/dd/yyyy HH:mm"; // Europe, Africa, Asia, Oceania & America
  /** Date format used to print any date value */
  private static String DATE_FORMAT;
  /** Time file contains first launch date, last launch date &#38; time played. */
  private static String TIME_FILE;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *@param timeFilePath path to the time file
  *@lastEditedVersion 2.25
  */
  public Time(String timeFilePath, boolean toInitialize){
    TIME_FILE=timeFilePath;
    if(toInitialize){
      initialiserFichierTime();
    }
    chargerTime();
  }
  // GET SET -------------------------------------------------------------------
  public long getDate1(){ return date1;}
  public void setDate1(long x){date1=x;}
  public long getDate2(){ return date2;}
  public void setDate2(long x){date2=x;}
  public void actualiserDate2(){date2=System.currentTimeMillis();}
  public long getTimeEnJeux(){ return tempsEnJeux;}
  public void setTimeEnJeux(long x){tempsEnJeux=x;}
  public void addTimeEnJeux(long x){ setTimeEnJeux(getTimeEnJeux()+x);}
  public static String getTimeFile(){return TIME_FILE;}
  public static void setTimeFile(String tf){TIME_FILE=tf;}
  public static String getDateFormat(){return DATE_FORMAT;}
  public static void setDateFormat(String df){DATE_FORMAT=df;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Return a string representing time as in Options date format.}
  *@lastEditedVersion 1.23
  */
  public String toString(){
    String r="";
    SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat());
    r+=g.getM("date1") + " : ";
    Date date1b = new Date(date1);
    r+=sdf.format(date1b);r+="\n";
    r+=g.getM("date2") + " : ";
    Date date2b = new Date(date2);
    r+=sdf.format(date2b);r+="\n";
    r+=g.getM("tempsEnJeux") + " : ";
    r+=msToTime(tempsEnJeux);r+="\n";
    /*In french it print:
    Date de 1ère connection : 02/09/2020 19:41
    Date de dernière connection : 02/09/2020 20:19
    Time en jeux total : 3 min 46 s
    */
    return r;
  }
  /**
  *{@summary Load all time informations save in Time.txt.}
  *@lastEditedVersion 1.25
  */
  public void chargerTime(){
    //lecture du fichier Time.txt
    String t [] = ReadFile.readFileArray(getTimeFile());
    if(t.length<3){
      initialiserFichierTime();
      t = ReadFile.readFileArray(getTimeFile());
    } //normalement a ce stade la initialiserFichierTime a déja réparer le fichier temps mais au cas ou on vérifi que tout est bon.
    if(t.length>=3){
      date1 = str.sToL(t[0]);
      date2 = str.sToL(t[1]);
      tempsEnJeux = str.sToL(t[2]);
    }else{
      erreur.erreur("Le fichier Time.txt est corrompu.","les variables de temps ont été remises à 0.");
      initialiserFichierTime();
      date1 = System.currentTimeMillis();
      date2 = System.currentTimeMillis();
      tempsEnJeux = 0;
    }
  }
  /**
  *{@summary Save time information in Time.txt.}
  *@lastEditedVersion 1.25
  */
  public void sauvegarder(){
    GString gs = new GString();
    gs.add(""+date1);
    gs.add(""+date2);
    gs.add(""+tempsEnJeux);
    ecrireUnFichier.ecrireUnFichier(gs,getTimeFile());
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
  *@lastEditedVersion 2.7
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
  *@lastEditedVersion 1.23
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
  *@lastEditedVersion 2.16
  */
  public static String getDatePourSauvegarde(){
    String dateStr = getDateFormat().replace('/','-').replace(':','-');
    return new SimpleDateFormat(dateStr).format(System.currentTimeMillis());
  }
  /**
  *{@summary Initialize time file.}
  *@lastEditedVersion 1.23
  */
  public static void initialiserFichierTime(){
    GString gs = new GString();
    gs.add(""+System.currentTimeMillis());
    gs.add(""+System.currentTimeMillis());
    gs.add("0");
    ecrireUnFichier.ecrireUnFichier(gs,getTimeFile());
  }
  /**
  *{@summary Try to stop execution of the programme during some ms.}
  *@param ms number of ms to wait before continue.
  *@lastEditedVersion 2.13
  */
  public static void sleep(int ms){
    pause(ms);
  }
  /**
  *{@summary Try to stop execution of the programme during 50 ms.}
  *@lastEditedVersion 2.13
  */
  public static void sleep(){
    pause(50);
  }
  /**
  *{@summary Try to stop execution of the programme during some ms.}
  *@param ms number of ms to wait before continue.
  *@lastEditedVersion 1.23
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
  *@lastEditedVersion 2.13
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
  *@lastEditedVersion 1.23
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
  // /***
  // *{@summary Print current date.}
  // *@lastEditedVersion 1.23
  // */
  // public static void affDateDuJour(String format){
  //   SimpleDateFormat sdf = new SimpleDateFormat(format);
  //   Date date2b = new Date(System.currentTimeMillis());
  //   erreur.println(sdf.format(date2b));
  // }
  // public static void affDateDuJour(){affDateDuJour("dd/MM/yyyy");}
}
