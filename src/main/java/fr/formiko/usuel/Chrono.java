package fr.formiko.usuel;

//def par défaut des fichiers depuis 0.79.5

public class Chrono { //https://fr.jeffprod.com/blog/2015/un-chronometre-en-java/

  private long tempsDepart=0;
  private long tempsFin=0;
  private long pauseDepart=0;
  private long pauseFin=0;
  private long duree=0;
  private final int id; private static int idCpt=0;
  private static Chrono ch;

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
  /**
  *{@summary Standard equals function.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@version 1.31
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
    duree=(tempsFin-tempsDepart) - (pauseFin-pauseDepart);
    tempsDepart=0;
    tempsFin=0;
    pauseDepart=0;
    pauseFin=0;
  }

  //static
  public static String timeToHMS(long tempsS){
    // IN : (long) temps en secondes
    // OUT : (String) temps au format texte : "1 h 26 min 3 s"
    return Temps.msToHMS(tempsS);
  }
  /**
   *{@summary Initializes Chrono ch.}
   *@version 1.23
   */
  public static void iniCh(){
    ch = new Chrono();
  }
  public static void debutCh(){
    if(ch==null){iniCh();}
    debutCh(ch);
  }
  public static void endCh(String s){endCh(s,ch);}
  /**
   * Start Chrono
   * @version 1.1
   */
  public static void debutCh(Chrono chTemp){ //début du Chrono.
    //if(chTemp == null){chTemp = new Chrono();}
    if(!debug.getAffLesPerformances()){ return;}
    chTemp.start();
  }
  /**
   * {@summary Stop Chrono and print a message about Chrono duration.}<br>
   * The message will be print in console only if debug.setAffLesPerformances is true.<br>
   *Message will be print only if the do more than 20ms.
   * @version 1.18
   */
  public static void endCh(String s,Chrono chTemp){ // fin du Chrono.
    if(!debug.getAffLesPerformances()){ return;}
    String s2 = g.getM(s);
    if (s2.length()!=0){ s=s2;}
    chTemp.stop();long lon = chTemp.getDuree();
    if(!debug.getAffLesEtapesDeRésolution() && lon<20){return;}
    String s3 = ""; if(!chTemp.equals(ch)){s3 = " ("+g.get("actionSecondaire")+" "+ch.getId()+")";}
    debug.performances("temps pour "+ s + " : "+lon+" ms"+s3); //affichage du chrono.
  }
}
