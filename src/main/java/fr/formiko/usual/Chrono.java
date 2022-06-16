package fr.formiko.usual;

/**
*{@summary Time counter}<br>
*cf https://fr.jeffprod.com/blog/2015/un-chronometre-en-java/
*@author Hydrolien, JeffProd
*@lastEditedVersion 1.46
*/
public class Chrono {

  private long tempsDepart=0;
  private long tempsFin=0;
  private long pauseDepart=0;
  private long pauseFin=0;
  private long duree=0;
  private final int id; private static int idCpt=0;
  private static Chrono ch;

  // CONSTRUCTORS --------------------------------------------------------------
  public Chrono(){
    id=idCpt; idCpt++;
  }
  // GET SET -------------------------------------------------------------------
  public int getId(){ return id;}
  public long getDuree(){return duree;}
  public long getDureeSec(){return duree/1000;}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){return timeToHMS(getDureeSec());}
  /**
  *{@summary Standard equals function.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@lastEditedVersion 1.31
  */
  @Override
  public boolean equals(Object o){
    if(o==null || !(o instanceof Chrono)){return false;}
    return getId()==((Chrono)(o)).getId();
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
    updateDuree();
    tempsDepart=0;
    tempsFin=0;
    pauseDepart=0;
    pauseFin=0;
  }
  /**
  *{@summary update duree to be able to read it.}<br>
  *@lastEditedVersion 1.46
  */
  public void updateDuree(){
    duree=(System.currentTimeMillis()-tempsDepart) - (pauseFin-pauseDepart);
  }

  //static
  public static String timeToHMS(long tempsS){
    // IN : (long) temps en secondes
    // OUT : (String) temps au format texte : "1 h 26 min 3 s"
    return Temps.msToTime(tempsS);
  }
  /**
   *{@summary Initializes Chrono ch.}
   *@lastEditedVersion 1.23
   */
  public static void iniCh(){
    ch = new Chrono();
  }
  public static void debutCh(){
    if(ch==null){iniCh();}
    debutCh(ch);
  }
  public static int endCh(String s){return endCh(s,ch);}

  public static void debutCh(Chrono chTemp){startCh(chTemp);}
  /**
   * Start Chrono
   * @lastEditedVersion 1.1
   */
  public static void startCh(Chrono chTemp){
    if(!debug.getPerformance()){ return;}
    chTemp.start();
  }
  /**
   * {@summary Stop Chrono and print a message about Chrono duration.}<br>
   * The message will be print in console only if debug.setPerformance is true.<br>
   * Message will be print only if the do more than 20ms.<br>
   * @return duration time.
   * @lastEditedVersion 1.18
   */
  public static int endCh(String s,Chrono chTemp){ // fin du Chrono.
    if(!debug.getPerformance()){ return 0;}
    String s2 = g.getM(s);
    if (s2.length()!=0){ s=s2;}
    chTemp.stop();long lon = chTemp.getDuree();
    if(lon<20){return (int)lon;}
    String s3 = ""; if(!chTemp.equals(ch)){s3 = " ("+g.get("actionSecondaire")+" "+ch.getId()+")";}
    erreur.info("time for "+ s + " : "+lon+" ms"+s3); //affichage du chrono.
    return (int)lon;
  }
}
