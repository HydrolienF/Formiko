package fr.formiko.formiko;

import fr.formiko.usual.*;
import fr.formiko.usual.images.*;
import fr.formiko.usual.maths.math;
import fr.formiko.usual.media.audio.MusicPlayer;
import fr.formiko.usual.structures.listes.*;
import fr.formiko.usual.types.str;
import fr.formiko.views.View;
import fr.formiko.views.ViewCLI;
import fr.formiko.views.ViewNull;
import fr.formiko.views.gui2d.*;

import java.awt.Font;
import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
*{@summary Launch class.}<br>
*Main file have all the shortcut on getter or setter that are curently used
*@author Hydrolien
*@lastEditedVersion 1.38
*/

/*
Les descriptions sont de la forme si dessu avec la premiere phrase décris la class ou la fonction ou meme la variable.
phrase courte.
utiliser "it" ou "elle" pour expliquer le fonctionnement d'une class ou méthode.(Ou juste la 3a personne du sigulier "Return true if...")
détailler le focntionnement des méthodes.
penser a indiquer @param et @return sauf si il n'y en a pas.
on peu utiliser des balistes html dans les commentaires. La plus utile étant <br>
*/

public class Main {
  /***
  *{@summary The compatible version for the options file, keys and backups.}
  *@lastEditedVersion 1.1
  */
  //null save var
  /** Use only after iniOp(). */
  private static Options op;
  /** Use only before iniOp() to avoid nullPointerException. */
  private static Options tempOp;
  /** Data use by GUI */
  private static Data data=new Data();
  /** View use everywere to update user interface */
  private static View view=new ViewNull();

  // private static Os os;

  private static Chrono ch;
  private static long lon;
  private static long lonTotal;
  private static long tempsDeDébutDeJeu;
  private static Partie pa;
  private static byte niveauDeDétailDeLAffichage=3;
  private static Pixel pi;
  private static HashMap<String, Integer> key; //keyboard key.
  private static int avancementChargement;
  private static Temps tem;
  //private static ThGraphisme tg;//actualise la fenetre tt avec 20 seconde de pause entre chaque actualisation.
  private static boolean retournerAuMenu;
  private static boolean tuto=false;
  private static ThScript ths;
  //private static ThMusique thm;

  private static boolean modeCLI=false;

  private static int cptMessageChargement=0;
  private static MusicPlayer mp;

  private static boolean needToInitialize; //TODO OP use to avoid using op==null
  private static boolean openMenuFirst;
  private static boolean wantToQuit;

  /**
   * {@summary Lauch the game.}<br>
   * It can have some args to do special thing.<br>
   * -d - set on the debug mode.<br>
   * trad make sur that every language file is 100% translated. It can auto translate some texte if the python file and translation api are on the same folder.<br>
   * op save the Options.txt file<br>
   * Others args are not fuly usable for now.<br>
   * @param args[] It can contain -d, trad, son, op, test, supprimer
   * @lastEditedVersion 1.39
   */
  public static void main (String [] args){
    if(args==null || (args.length==1 && args[0]==null)){args = new String[0];}
    debug.setMessage(false);
    debug.setPerformance(false);
    debug.setAffG(false);
    openMenuFirst=true;
    color.iniColor();
    wantToQuit=false;
    // handleQuit(); //Do some weard thing so it's disable for now.
    //iniThings that can't be null :
    // view = new ViewNull();
    // os = new Os();
    // data = new Data();
    //args part
    if(args.length!=0){
      if(args.length==1 && args[0] != null){
        args = args[0].split(" ");
      }
      int k=0;
      while(args.length > k){// if there is options starting with "-"
        if(args[k] != null && args[k].length()>1 && args[k].substring(0,1).equals("-")){
          launchOptions.launchOptionsMinor(args[k].substring(1));
          args = tableau.remove(args, k);
        }else{
          k++;
        }
      }
    }
    if(args.length>0 && args[0] != null){
      launchOptions.launchOptionsMajor(args);
      quit();
    }else{ // si il n'y a pas d'options ou que des options a "-".
      // LE JEU -------------------------------------------------------------------
      boolean continuerJeu=true;
      int k2=0;
      while(continuerJeu && k2<10){
        // setPartie(null);
        k2++;
        erreur.info("game launch");
        continuerJeu = launch();//on attend ici tant que le joueur veux jouer.
        debug.débogage("ReLancement du jeu");
        try {
          getF().dispose();
        }catch (Exception e) {
          erreur.alerte("Window can not be dispose.");
        }
        setFirstGame(false);
        Partie.setScript(null);
        setRetournerAuMenu(false);
        openMenuFirst=true;
        // op=null;//force la réinitialisation de tout.
        Images.clearPartielTemporaire();
      }
    }
    quit();//en théorie on arrive pas là.
  }
  /**
   * {@summary pre launch.}<br>
   * @lastEditedVersion 1.44
   */
  public static void iniLaunch(){
    if(op==null){initialisation();}
    if(Partie.getScript()==null || Partie.getScript().equals("")){
      if(getFirstGame()){Partie.setScript("tuto");}
      else{Partie.setScript("");}
    }
    iniCpt();
    pa = new Partie(0,0,new Carte(new GCase(1,1)),1.0); //new empty game
  }
  /**
   * {@summary Launch in the void main if there is not other args than -something (ex : -d).}<br>
   * @lastEditedVersion 1.44
   */
  public static boolean launch(){
    iniLaunch();
    if(mp==null){
      mp = new MusicPlayer(getFolder(), getOp().getBMusic(), getOp().getVolMusic());
      if(getFirstGame()){
        getFolder().downloadMusicData(mp);
        mp.addNextMusic("Ride of the Valkyries - Wagner.mp3", true);
      }
    }
    if(!getFirstGame()){
      mp.addNextMusic("Beyond The Warriors - Guifrog.mp3", true);
      mp.play();
    }
    view.menuMain();
    if (!modeCLI) {
      ((ViewGUI2d)(view)).waitForGameLaunch();
    }
    return true;
  }

  // GET SET ----------------------------------------------------------
  public static boolean isCLI(){return modeCLI;}
  public static byte getNiveauDeDétailDeLAffichage(){return niveauDeDétailDeLAffichage;}
  public static Espece getEspece(){return getEspeceById(0);}
  public static Espece getEspeceById(int id){ return getGe().getEspeceById(id);}
  public static GEspece getGEspece(){ return getGe();}
  public static Joueur getJoueurById(int id){ return getGj().getJoueurById(id);}
  public static Fourmiliere getFourmiliereById(int id){ return getJoueurById(id).getFere();}
  public static FFrame getF(){ try {return ((ViewGUI2d)view).getF();} catch (Exception e) {return null;}}
  public static Options getOp(){if(op!=null){return op;}else{if(tempOp==null){tempOp = Options.newDefaultOptions();} return tempOp;}}
  public static void saveOp(boolean threaded){if(op!=null){op.saveOptions(threaded);}}
  public static Chrono getCh(){ return ch;}
  public static int getKey(String clé){ return key.get(clé); }
  public static Partie getPartie(){ return pa;}
  public static void setPartie(Partie p){pa=p;}
  public static Pixel getPiFond(){ return pi;}
  public static int getAvancementChargement(){ return avancementChargement;}
  public static void setAvancementChargement(int x){avancementChargement=x;}
  public static Temps getTemps(){ return tem;}
  public static boolean getRetournerAuMenu(){return retournerAuMenu;}
  public static void setRetournerAuMenu(boolean b){retournerAuMenu=b;}
  public static Os getOs(){return Os.getOs();}
  public static void setOs(Os o){Os.setOs(o);}
  public static Folder getFolder(){return Folder.getFolder();}
  public static void setFolder(Folder f){Folder.setFolder(f);}
  public static void setTuto(boolean b){tuto=b;}
  public static boolean getFirstGame(){return Folder.getFirstGame();}
  public static void setFirstGame(boolean b){Folder.setFirstGame(b);}
  public static Data getData(){return data;}
  public static void setData(Data d){data=d;}
  public static View getView(){return view;}
  public static void setView(View v){view=v;}
  public static void setModeCLI(boolean b){modeCLI=b;}
  public static long getLonTotal(){return lonTotal;}
  public static MusicPlayer getMp(){return mp;}
  //shortcut
  public static Fourmi getPlayingAnt(){ try {return getPartie().getPlayingAnt();}catch (Exception e) {return null;}}
  public static void setPlayingAnt(Fourmi f){ getPartie().setPlayingAnt(f); getView().setPlayingAnt(f);}
  public static Joueur getPlayingJoueur(){ try {return Joueur.getPlayingJoueur();}catch (Exception e) {return null;}}
  //view
  public static boolean getActionGameOn(){return getView().getActionGameOn();}
  //other
  public static boolean estWindows(){return getOs().isWindows();}
  public static String get(String clé){ return g.get(clé);}
  public static Script getScript(){if(ths!=null) {return ths.getScript();}else{return null;}}
  public static ThScript getThScript(){return ths;}
  public static void setScript(ThScript t){ths=t;}
  public static void launchScript(){ths.start();}
  public static void stopScript(){if(ths!=null) {ths.interrupt();}}
  //musique
  /*public static ThMusique getThm(){return thm;}
  public static Musique getMusique(){return getThm().getM();}
  public static void setMusique(Musique m){getThm().setM(m);}
  public static void setMusiqueSuivante(){getThm().setM();}*/
  //view
  public static void repaint(){getView().paint();}
  public static void doAction(int x){action.doAction(x);}

  public static int getDimX(){ try {return ((ViewGUI2d)(getView())).getWidth();}catch (Exception e){erreur.erreur("Impossible de récupérer les dim de Pp");return 1;}}
  public static int getDimY(){ try {return ((ViewGUI2d)(getView())).getHeight();}catch (Exception e){erreur.erreur("Impossible de récupérer les dim de Pp");return 1;}}
  public static int getWidth(){return getDimX();}
  public static int getHeight(){return getDimY();}
  public static int getTailleElementGraphique(int x){ return math.min(getTailleElementGraphiqueY(x),getTailleElementGraphiqueX(x));}
  public static int getTailleElementGraphiqueX(int x){ return (x*getDimX())/1920;}
  public static int getTailleElementGraphiqueY(int x){ return (x*getDimY())/1080;}
  public static double getRacioEspaceLibre(){return 900.0/1080.0;}
  public static int getDimXCarte(){return (int)(1920.0*getRacioEspaceLibre());}
  //options
  public static byte getLanguage(){ return getOp().getLanguage();}
  public static void setLanguage(int x){ getOp().setLanguage(x);iniLangue();}
  public static int getbuttonSizeZoom(){return getOp().getbuttonSizeZoom();}
  public static int getbuttonSizeAction(){return getOp().getbuttonSizeAction();}
  public static int getbuttonSizeTX(){return getOp().getbuttonSizeTX();}
  public static int getMaxMessageDisplay(){ return getOp().getMaxMessageDisplay();}
  public static boolean getDrawGrid(){ return getOp().getDrawGrid();}
  public static boolean getOrientedObjectOnMap(){ return getOp().getOrientedObjectOnMap();}
  public static boolean getForceQuit(){ return getOp().getForceQuit();}
  public static byte getBorderButtonSize(){ return getOp().getBorderButtonSize();}
  public static boolean getDrawRelationsIcons(){return getOp().getDrawRelationsIcons();}
  public static Font getFont1(){ return getOp().getFont1();}
  public static Font getFont1(double d){ return getOp().getFont1(d);}
  public static void setFont1(Font f){ getOp().setFont1(f);}
  public static Font getFont2(){ return getOp().getFont2();}
  public static void setFont2(Font f){ getOp().setFont2(f);}
  public static int getFontSizeText(){ return getOp().getFontSizeText();}
  public static int getFontSizeTitle(){ return getOp().getFontSizeTitle();}
  public static boolean getFullscreen(){ return getOp().getFullscreen();}
  public static boolean getLoadingDuringMenus(){ return getOp().getLoadingDuringMenus();}
  public static boolean getKeepFilesRotated(){ return getOp().getKeepFilesRotated();}
  public static int getSizeOfMapLines(){return getOp().getSizeOfMapLines();}
  public static int getPositionCase(){return getOp().getPositionCase();}
  public static byte getRealisticSize(){return getOp().getRealisticSize();}
  //partie
  public static GInsecte getGi(){return pa.getGi();}
  public static GJoueur getListeJoueur(){return pa.getGj();}
  public static GJoueur getGj(){return pa.getGj();}
  public static GCase getGc(){if(getPartie()==null){return null;}return getPartie().getGc();}
  public static CCase getCCase(int x, int y){if(getGc()==null){return null;}return getGc().getCCase(x,y);}
  public static void setNbrDeTour(int x){pa.setNbrDeTour(x);}
  public static void setTour(int x){pa.setTour(x);}
  public static int getNbrDeTour(){return pa.getNbrDeTour();}
  public static int getTour(){return pa.getTour();}
  public static byte getDifficulté(){return pa.getDifficulté();}
  public static void setDifficulté(byte x){pa.setDifficulté(x);}
  public static void setDifficulté(int x){setDifficulté((byte)x);}
  public static LocalDateTime getDateDeCréation(){return pa.getDateDeCréation();}
  public static int [] getTableauDesEspecesAutorisée(){ return pa.getTableauDesEspecesAutorisée();}
  public static int getNbrDeJoueur(){ return pa.getNbrDeJoueurDansLaPartie();}
  public static Carte getMap(){ return pa.getCarte();}
  public static Carte getCarte(){ return getMap();}
  public static double getVitesseDeJeu(){return pa.getVitesseDeJeu();}
  public static GEspece getGe(){return Partie.getGe();}

  public static boolean getOpenMenuFirst(){return openMenuFirst;}
  public static void dontOpenMenuFirst(){openMenuFirst=false;}
  // Fonctions propre -------------------------------------------------
  /**
   * Initializes Options, key, language, time data, musique, os value. And check the integrity of the file tree.
   * @lastEditedVersion 2.7
   */
  public static void initialisation(){
    tempsDeDébutDeJeu=System.currentTimeMillis();
    setFirstGame(false);
    Fourmi.setUneSeuleAction(-1);
    if(!erreur.getMuet()){ //if not in test.
      if (modeCLI) {
        if (view!=null && !(view instanceof ViewCLI)) {
          view = new ViewCLI();
        }
      }else{
        if (view!=null && !(view instanceof ViewGUI2d)) {
          view = new ViewGUI2d();
        }
      }
    }
    setFolder(new Folder(Main.getView()));
    getFolder().ini();
    new Save();
    setMessageChargement("chargementDesOptions");startCh();
    chargerLesTraductions.setRep(null); //TOFIX some test fail without it
    chargerLesTraductions.iniTLangue();
    if(op==null){iniOp();}
    if(!debug.getMessage()){//si elle n'ont pas été activé par "-d"
      debug.setMessage(getOp().getMessage());
    }
    if(!debug.getPerformance()){//si elle n'ont pas été activé par "-p"
      debug.setPerformance(getOp().getPerformance());
    }
    if(!debug.getAffG()){//si elle n'ont pas été activé par "-g"
      debug.setAffG(getOp().getAffG());
    }
    endCh("chargementDesOptions");
    setMessageChargement("chargementDesTouches");startCh();
    key = chargerLesTouches.chargerLesTouches("1.49.9", getFolder().getFolderMain());
    endCh("chargementDesTouches");
    setMessageChargement("chargementDesLangues");
    iniLangue();
    startCh();
    tem = new Temps(getFolder().getFolderTemporary()+"Temps.txt", getFirstGame());
    erreur.info("OS: "+getOs());
    erreur.info("language: "+chargerLesTraductions.getLanguageAsString(getOp().getLanguage()));
    erreur.info(tem.toString());
    endCh("chargementDesDonnéesTemporelles");
    setMessageChargement("chargementDesEspeceDeFourmi");startCh();
    Partie.iniGe(); // chargement des Especes.
    GIndividu.loadIndividus(); // chargement de leur individu.
    endCh("chargementDesIndividuDeFourmi");startCh();
    Insecte.setGie(); // chargement des Insectes.
    endCh("chargementDesEspeceDInsecte");
    iniCpt();
  }public static void ini(){initialisation();}
  /**
   *{@summary Initializes counter cpt of IEspece, Joueur, Fourmiliere ,ObjetAId.}
   *@lastEditedVersion 1.7
   */
  public static void iniCpt(){
    IEspece.ini();
    Joueur.ini();
    Fourmiliere.ini();
    ObjetAId.ini();
  }
  /**
   * Load Options.
   * @lastEditedVersion 1.1
   */
  public static void iniOp(){
    op = new Options();
    getOp().iniOptions();
    tempOp=null;
  }
  /**
   *{@summary Load language.}<br>
   *If language are not fully translated, it will add data of default language.
   *@lastEditedVersion 1.1
   */
  public static void iniLangue(){
    startCh();
    HashMap<String,String> hm = chargerLesTraductions.chargerLesTraductions(getLanguage());
    g.setMap(hm);
    if(getLanguage()>2){ //language 0,1 & 2 are full translated.
      chargerLesTraductions.completMapWithFullTranslatedLanguage();
    }
    endCh("chargementDesLangues");
  }
  /**
   * {@summary Print on the window a message about game loading.}<br>
   * If you tried to use it before the creating of a new FPanelChargement, message will not appear on the window.
   * @lastEditedVersion 1.46
   */
  public static void setMessageChargement(String key){
    int percentageDone = (100*cptMessageChargement) / 7;
    cptMessageChargement++;
    String message = g.getM(key);
    if(percentageDone<100){message+="...";}
    getView().loadingMessage(message, percentageDone);
  }
  //chrono shortcut
  public static void startCh(){Chrono.startCh();}
  /**
  *{@summary End the Chrono &#38; return the time.}
  *@lastEditedVersion 2.24
  */
  public static int endCh(String s){
    int time=Chrono.endCh(s);
    lonTotal+=time;
    return time;
  }
  public static void startCh(Chrono chTemp){Chrono.startCh(chTemp);}
  /**
  *{@summary End the Chrono &#38; return the time.}
  *@lastEditedVersion 2.24
  */
  public static int endCh(String s,Chrono chTemp){
    int time=Chrono.endCh(s,chTemp);
    lonTotal+=time;
    return time;
  }
  /**
   * {@summary Try to exit normally.}<br>
   * Save score informations.<br>
   * Clear temporary Images.<br>
   * Save time played.<br>
   * Stop java with code 0.<br>
   * If something went wrong stop java with code 1.<br>
   * @lastEditedVersion 1.1
   */
  public static void quit(){
    wantToQuit=true;
    try {
      // if(pa!=null){
      //   startCh();
      //   pa.enregistrerLesScores();
      //   endCh("enregistementDesScores");
      // }
      startCh();
      if(getKeepFilesRotated()){Images.clearPartielTemporaire();}
      else{Images.clearTemporaire();}
      saveOp(false);
      endCh("vidageDesFichiersImages");
      String s = "toutes les opérations longues ";
      debug.performances("temps pour "+ s + " : "+lonTotal+" ms");
      long tempsDeFinDeJeu=System.currentTimeMillis();
      long tempsJeuEcoulé = tempsDeFinDeJeu-tempsDeDébutDeJeu;
      tem.addTempsEnJeux(tempsJeuEcoulé);tem.actualiserDate2();tem.sauvegarder();
      erreur.println(g.getM("tempsJeuEcoulé")+" : "+Temps.msToTime(tempsJeuEcoulé,2,false));
      erreur.println(g.getM("messageQuitter"));
      System.exit(0);
    }catch (Exception e) {
      erreur.alerte("Game fail to close normally.");
      System.exit(1); //une erreur a la fermeture.
    }
  }
  public static void quitter(){quit();}
  /**
  *{@summary Launch normal exit on shutDown.}
  *From ctr-c for exemple.<br>
  *@lastEditedVersion 1.25
  */
  public static void handleQuit(){
    Runtime.getRuntime().addShutdownHook(new Thread() {
      /** This handler will be called on Control-C pressed */
      @Override
      public void run() {
        if(!wantToQuit){
          quit();
        }
      }
    });
  }
  /**
   * {@summary Play a turn.}<br>
   * 1a updating Case resources.<br>
   * 2a Make humain player and AI play.<br>
   * 3a Make Insecte play.<br>
   * 4a Add new Insectes.<br>
   * @lastEditedVersion 1.1
   */
  public static void tour(){
    if(!getPartie().getContinuerLeJeu()){
      return;
    }
    if(!getPartie().getLaunchingFromSave()){
      getGc().tourCases(); //actualisation des ressources sur les cases.
      getGi().preTurn(); //actualisation des actions des insectes
      for (Joueur j : getGj()) {
        j.getFere().preTurn();
      }
    }else{
      getPartie().setLaunchingFromSave(false);
    }
    getGj().jouer();
    getGi().tourInsecte();

    if(getPartie().getAppartionInsecte()){
      int nbrDInsecteRestant = math.max( getGc().length()/5 -  getGi().getGiVivant().length(),0);
      int x2 = math.min( getGc().length()/20, nbrDInsecteRestant);
      String s = g.get("SpawnOf")+" "+x2+" "+g.get("insecte")+g.get("s");
      new Message(s);
      getGi().add((x2*9)/10); //les insectes vivants n'apparaissent pas sur des cases déja occupé.
      getGi().add(x2/10);
    }
  }
}
