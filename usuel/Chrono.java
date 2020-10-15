package fr.formiko.usuel;

public class Chrono { //https://fr.jeffprod.com/blog/2015/un-chronometre-en-java/

  private long tempsDepart=0;
  private long tempsFin=0;
  private long pauseDepart=0;
  private long pauseFin=0;
  private long duree=0;
  private final int id; private static int idCpt;

  // CONSTRUCTEUR ---------------------------------------------------------------
  public Chrono(){
    id=idCpt; idCpt++;
  }
  // GET SET --------------------------------------------------------------------
  public int getId(){ return id;}
  // Fonctions propre -----------------------------------------------------------
  public boolean equals(Chrono ch){
    try {
      return ch.getId()==id;
    }catch (Exception e) {
      erreur.erreur("Impossible de comparez les Chronos car un élément est null.","Chrono.equals");
    }
    return false;
  }

  public void start()
      {
      tempsDepart=System.currentTimeMillis();
      tempsFin=0;
      pauseDepart=0;
      pauseFin=0;
      duree=0;
      }

  public void pause()
      {
      if(tempsDepart==0) {return;}
      pauseDepart=System.currentTimeMillis();
      }

  public void resume()
      {
      if(tempsDepart==0) {return;}
      if(pauseDepart==0) {return;}
      pauseFin=System.currentTimeMillis();
      tempsDepart=tempsDepart+pauseFin-pauseDepart;
      tempsFin=0;
      pauseDepart=0;
      pauseFin=0;
      duree=0;
      }

  public void stop()
      {
      if(tempsDepart==0) {return;}
      tempsFin=System.currentTimeMillis();
      duree=(tempsFin-tempsDepart) - (pauseFin-pauseDepart);
      tempsDepart=0;
      tempsFin=0;
      pauseDepart=0;
      pauseFin=0;
      }

  public long getDureeSec()
      {
      return duree/1000;
      }

  public long getDureeMs()
      {
      return duree;
      }

  public String getDureeTxt()
      {
      return timeToHMS(getDureeSec());
      }

  public static String timeToHMS(long tempsS) {

      // IN : (long) temps en secondes
      // OUT : (String) temps au format texte : "1 h 26 min 3 s"

      int h = (int) (tempsS / 3600);
      int m = (int) ((tempsS % 3600) / 60);
      int s = (int) (tempsS % 60);

      String r="";
      if(h>0) {r+=h+" h ";}
      if(m>0) {r+=m+" min ";}
      if(s>0) {r+=s+" s";}
      if(h<=0 && m<=0 && s<=0) {r="0 s";}
      return r;
      }
  } // class Chrono