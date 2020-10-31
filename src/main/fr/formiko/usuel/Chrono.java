package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

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
  public long getDuree(){return duree;}
  public long getDureeSec(){return duree/1000;}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){return timeToHMS(getDureeSec());}
  public boolean equals(Chrono ch){
    if(ch==null){return false;}
    return ch.getId()==id;
  }

  public void start(){
    tempsDepart=System.currentTimeMillis();
    tempsFin=0;
    pauseDepart=0;
    pauseFin=0;
    duree=0;
  }
  public void pause(){
    if(tempsDepart==0) {return;}
    pauseDepart=System.currentTimeMillis();
  }
  public void resume(){
    if(tempsDepart==0) {return;}
    if(pauseDepart==0) {return;}
    pauseFin=System.currentTimeMillis();
    tempsDepart=tempsDepart+pauseFin-pauseDepart;
    tempsFin=0;
    pauseDepart=0;
    pauseFin=0;
    duree=0;
  }
  public void stop(){
    if(tempsDepart==0) {return;}
    tempsFin=System.currentTimeMillis();
    duree=(tempsFin-tempsDepart) - (pauseFin-pauseDepart);
    tempsDepart=0;
    tempsFin=0;
    pauseDepart=0;
    pauseFin=0;
  }

  public static String timeToHMS(long tempsS){
    // IN : (long) temps en secondes
    // OUT : (String) temps au format texte : "1 h 26 min 3 s"
    return Temps.msToHMS(tempsS);
  }
}
