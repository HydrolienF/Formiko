package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.41.2
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.conversiondetype.str;
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.ecrireUnFichier;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Temps {
  private long date1;
  private long date2;
  private long tempsEnJeux;

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
  // Fonctions propre -----------------------------------------------------------
  public void afficheToi(){
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    System.out.print(g.getM("date1") + " : ");
    Date date1b = new Date(date1);
    System.out.println(sdf.format(date1b));
    System.out.print(g.getM("date2") + " : ");
    Date date2b = new Date(date2);
    System.out.println(sdf.format(date2b));
    System.out.print(g.getM("tempsEnJeux") + " : ");
    System.out.println(msToHMS(tempsEnJeux));
    /*En français ca donne :
    Date de 1ère connection : 02/09/2020 19:41
    Date de dernière connection : 02/09/2020 20:19
    Temps en jeux total : 3 min 46 s
    */
  }
  public void chargerTemps(){
    //lecture du fichier data/Temps.txt
    String t [] = lireUnFichier.lireUnFichier("data/Temps.txt");
    if(t.length<3){
      initialiserFichierTemps();
      t = lireUnFichier.lireUnFichier("data/Temps.txt");
    }
    try {
      date1 = str.sToL(t[0]);
      date2 = str.sToL(t[1]);
      tempsEnJeux = str.sToL(t[2]);
    }catch (Exception e) {
      erreur.erreur("Le fichier data/Temps.txt est corrompu.","Temps.chargerTemps","les variables de temps ont été remises à 0.");
      initialiserFichierTemps();
      date1 = System.currentTimeMillis();
      date2 = System.currentTimeMillis();
      tempsEnJeux = 0;
    }
  }
  public static void initialiserFichierTemps(){
    GString gs = new GString();
    gs.add(""+System.currentTimeMillis());
    gs.add(""+System.currentTimeMillis());
    gs.add("0");
    ecrireUnFichier.ecrireUnFichier(gs,"data/Temps.txt");
  }
  public void sauvegarder(){
    GString gs = new GString();
    gs.add(""+date1);
    gs.add(""+date2);
    gs.add(""+tempsEnJeux);
    ecrireUnFichier.ecrireUnFichier(gs,"data/Temps.txt");
  }
  //static ---------------------------------------------------------------------------
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