package fr.formiko.formiko;

import fr.formiko.usuel.*;
import fr.formiko.usuel.images.*;
import fr.formiko.usuel.structures.listes.*;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.media.audio.MusicPlayer;
import fr.formiko.usuel.types.str;
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
*@version 1.38
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
  *@version 1.1
  */
  private static String versionActuelle = "1.49.9";
  private static Options op;
  private static Chrono ch;
  private static long lon;
  private static long lonTotal;
  private static long tempsDeDébutDeJeu;
  private static Partie pa;
  private static byte niveauDeDétailDeLAffichage=3;
  private static Pixel pi;
  private static HashMap<String, Integer> key; //keyboard key.
  private static int avancementChargement;
  private static boolean affGraine=false;//tant que les espece granivore ne sont pas pleinement opérationelle.
  private static Temps tem;
  //private static ThGraphisme tg;//actualise la fenetre tt avec 20 seconde de pause entre chaque actualisation.
  private static boolean retournerAuMenu;
  private static Os os;
  private static Folder folder;
  private static boolean tuto=false;
  private static ThScript ths;
  //private static ThMusique thm;
  private static boolean premierePartie=false;
  private static Data data;
  private static View view;

  private static boolean modeCLI=false;

  private static int cptMessageChargement=0;
  private static MusicPlayer mp;

  /**
   * {@summary Lauch the game.}<br>
   * It can have some args to do special thing.<br>
   * -d - set on the debug mode.<br>
   * trad make sur that every language file is 100% translated. It can auto translate some texte if the python file and translation api are on the same folder.<br>
   * op save the Options.txt file<br>
   * Others args are not fuly usable for now.<br>
   * @param args[] It can contain -d, trad, son, op, test, supprimer
   * @version 1.39
   */
  public static void main (String [] args){
    if(args==null || (args.length==1 && args[0]==null)){args = new String[0];}
    debug.setAffLesEtapesDeRésolution(false);
    debug.setAffLesPerformances(false);
    debug.setAffG(false);
    if(args.length!=0){
      if(args.length==1 && args[0] != null){
        args = args[0].split(" ");
      }
      int k=0;
      while(args.length > k){//si il y a des options a "-"
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
      quitter();
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
          // getView().close();
        }catch (Exception e) {
          erreur.alerte("Window can not be dispose.");
        }
        setRetournerAuMenu(false);
        op=null;//force la réinitialisation de tout.
        image.clearPartielTemporaire();
      }
    }
    quitter();//en théorie on arrive pas là.
  }
  /**
   * {@summary pre launch.}<br>
   * @version 1.44
   */
  public static void iniLaunch(){
    if(getOp()==null){initialisation();}
    if(premierePartie){Partie.setScript("tuto");}
    else{Partie.setScript("");}
    iniCpt();
    pa = new Partie(0,0,new Carte(new GCase(1,1)),1.0); //new empty game
  }
  /**
   * {@summary Launch in the void main if there is not other args than -something (ex : -d).}<br>
   * @version 1.44
   */
  public static boolean launch(){
    iniLaunch();
    if(mp==null){
      mp = new MusicPlayer();
      if(premierePartie){
        getFolder().downloadMusicData();
        mp.addNextMusic("Ride of the Valkyries - Wagner.mp3", true);
      }
    }
    if(!premierePartie){
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
  public static String getVersionActuelle(){return versionActuelle;}
  public static Espece getEspece(){return getEspeceParId(0);}
  public static Espece getEspeceParId(int id){ return getGe().getEspeceParId(id);}
  public static GEspece getGEspece(){ return getGe();}
  public static Joueur getJoueurParId(int id){ return Main.getGj().getJoueurParId(id);}
  public static Fourmiliere getFourmiliereParId(int id){ return getJoueurParId(id).getFere();}
  public static FFrame getF(){ try {return ((ViewGUI2d)view).getF();} catch (Exception e) {return null;}}
  public static Options getOp(){return op;}
  public static Chrono getCh(){ return ch;}
  public static int getKey(String clé){ return key.get(clé); }
  public static Partie getPartie(){ return pa;}
  public static void setPartie(Partie p){pa=p;}
  public static Pixel getPiFond(){ return pi;}
  public static int getAvancementChargement(){ return avancementChargement;}
  public static void setAvancementChargement(int x){avancementChargement=x;}
  public static boolean getAffGraine(){return affGraine;}
  public static Temps getTemps(){ return tem;}
  public static boolean getRetournerAuMenu(){return retournerAuMenu;}
  public static void setRetournerAuMenu(boolean b){retournerAuMenu=b;}
  public static Os getOs(){return os;}
  public static void setOs(Os o){os=o;}
  public static Folder getFolder(){return folder;}
  public static void setFolder(Folder f){folder=f;}
  public static void setTuto(boolean b){tuto=b;}
  public static boolean getPremierePartie(){return premierePartie;}
  public static void setPremierePartie(boolean b){premierePartie=b;}
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
  public static boolean estWindows(){return os.getId()==1;}
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
  public static byte getLanguage(){ return op.getLanguage();}
  public static void setLangue(int x){ op.setLangue(x);iniLangue();}
  public static int getbuttonSizeZoom(){return op.getbuttonSizeZoom();}
  public static int getTailleBoutonAction(){return op.getTailleBoutonAction();}
  public static int getTailleBoutonTX(){return op.getTailleBoutonTX();}
  public static int getNbrMessageAfficher(){ return op.getNbrMessageAfficher();}
  public static boolean getDessinerGrille(){ return op.getDessinerGrille();}
  public static boolean getElementSurCarteOrientéAprèsDéplacement(){ return op.getElementSurCarteOrientéAprèsDéplacement();}
  public static boolean getForcerQuitter(){ return op.getForcerQuitter();}
  public static byte getBordureBouton(){ return op.getBordureBouton();}
  public static boolean getDessinerIcone(){return op.getDessinerIcone();}
  public static Font getFont1(){ return op.getFont1();}
  public static Font getFont1(double d){ return op.getFont1(d);}
  public static void setFont1(Font f){ op.setFont1(f);}
  public static Font getFont2(){ return op.getFont2();}
  public static void setFont2(Font f){ op.setFont2(f);}
  public static int getTaillePolice1(){ return op.getTaillePolice1();}
  public static int getTaillePolice2(){ return op.getTaillePolice2();}
  public static boolean getPleinEcran(){ return op.getPleinEcran();}
  public static boolean getChargementPendantLesMenu(){ return op.getChargementPendantLesMenu();}
  public static boolean getGarderLesGraphismesTourné(){ return op.getGarderLesGraphismesTourné();}
  public static int getDimLigne(){return op.getDimLigne();}
  public static int getPositionCase(){return op.getPositionCase();}
  public static byte getTailleRealiste(){return op.getTailleRealiste();}
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
  public static GEspece getGe(){return pa.getGe();}
  //ini
  public static void initialiserElémentTournés(){ ini.initialiserElémentTournés();}
  public static void initialiserAutreELémentTournés(){ ini.initialiserAutreELémentTournés();}
  // Fonctions propre -------------------------------------------------
  /**
   * Initializes Options, key, language, time data, musique, os value. And check the integrity of the file tree.
   * @version 1.1
   */
  public static void initialisation(){
    tempsDeDébutDeJeu=System.currentTimeMillis();
    os = new Os();
    setPremierePartie(false);
    Fourmi.setUneSeuleAction(-1);
    folder = new Folder();
    getFolder().ini();
    if(view==null){
      view = new ViewNull();
    }
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
    setMessageChargement("chargementDesOptions");startCh();
    chargerLesTraductions.iniTLangue();
    iniOp();
    if(!debug.getAffLesEtapesDeRésolution()){//si elle n'ont pas été activé par "-d"
      debug.setAffLesEtapesDeRésolution(op.getAffLesEtapesDeRésolution());
    }
    if(!debug.getAffLesPerformances()){//si elle n'ont pas été activé par "-p"
      debug.setAffLesPerformances(op.getAffLesPerformances());
    }
    if(!debug.getAffG()){//si elle n'ont pas été activé par "-g"
      debug.setAffG(op.getAffG());
    }
    endCh("chargementDesOptions");
    setMessageChargement("chargementDesTouches");startCh();
    key = chargerLesTouches.chargerLesTouches(getVersionActuelle());
    endCh("chargementDesTouches");
    setMessageChargement("chargementDesLangues");
    iniLangue();
    startCh();
    tem = new Temps();
    endCh("chargementDesDonnéesTemporelles");
    setMessageChargement("chargementDesEspeceDeFourmi");startCh();
    Partie.iniGe(); // chargement des Especes.
    GIndividu.loadIndividus(); // chargement de leur individu.
    endCh("chargementDesIndividuDeFourmi");startCh();
    Insecte.setGie(); // chargement des Insectes.
    endCh("chargementDesEspeceDInsecte");
    data = new Data();
    iniCpt();
  }public static void ini(){initialisation();}
  /**
   *{@summary Initializes counter cpt of IEspece, Joueur, Fourmiliere ,ObjetAId.}
   *@version 1.7
   */
  public static void iniCpt(){
    IEspece.ini();
    Joueur.ini();
    Fourmiliere.ini();
    ObjetAId.ini();
  }
  /**
   * Load Options.
   * @version 1.1
   */
  public static void iniOp(){
    //op = chargerLesOptions.chargerLesOptions(getVersionActuelle());
    op = new Options();
    op.iniOptions();
  }
  /**
   *{@summary Load language.}<br>
   *If language are not fully translated, it will add data of default language.
   *@version 1.1
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
   * If you tried to use it before the creating of a new PanneauChargement, message will not appear on the window.
   * @version 1.46
   */
  public static void setMessageChargement(String key){
    int percentageDone = (100*cptMessageChargement) / 7;
    cptMessageChargement++;
    String message = g.getM(key);
    if(percentageDone<100){message+="...";}
    getView().loadingMessage(message, percentageDone);
    // System.out.println(message+" "+percentageDone+"%");
  }
  // public static void setMessageChargement(String key){setMessageChargement(key, false);}
  /**
   * Sould transforme a GCase to a Image that can be used for mini-map.<br>
   * @version 1.1
   */
  public static void gcToImage(){
    startCh();
    Carte mapo = new Carte("miniWorld");
    GCase gc = mapo.getGc();
    endCh("chargementCarteEtGc");startCh();
    Img img = gc.getImg();
    endCh("récupérationDeLImage");startCh();
    img.sauvegarder("carte.png");
    endCh("sauvegardeLeLImage");
    //debug.setAffLesEtapesDeRésolution(false);
  }
  //chrono shortcut
  public static void startCh(){Chrono.debutCh();}
  public static void endCh(String s){lonTotal+=Chrono.endCh(s);}
  public static void startCh(Chrono chTemp){Chrono.debutCh(chTemp);}
  public static void endCh(String s,Chrono chTemp){ // fin du Chrono.
    lonTotal+=Chrono.endCh(s,chTemp);
  }
  /**
   * {@summary Try to exit normally.}<br>
   * Save score informations.<br>
   * Clear temporary Images.<br>
   * Save time played.<br>
   * Stop java with code 0.<br>
   * If something went wrong stop java with code 1.<br>
   * @version 1.1
   */
  public static void quitter(){
    try {
      if(pa!=null){
        startCh();
        pa.enregistrerLesScores();
        endCh("enregistementDesScores");
      }
      startCh();
      if(getGarderLesGraphismesTourné()){image.clearPartielTemporaire();}
      else{image.clearTemporaire();}
      endCh("vidageDesFichiersImages");
      String s = "toutes les opérations longues ";
      debug.performances("temps pour "+ s + " : "+lonTotal+" ms");
      long tempsDeFinDeJeu=System.currentTimeMillis();
      long tempsJeuEcoulé = tempsDeFinDeJeu-tempsDeDébutDeJeu;
      tem.addTempsEnJeux(tempsJeuEcoulé);tem.actualiserDate2();tem.sauvegarder();
      System.out.println(g.getM("tempsJeuEcoulé")+" : "+Temps.msToTime(tempsJeuEcoulé,2,false));
      System.out.println(g.getM("messageQuitter"));
      System.exit(0);
    }catch (Exception e) {
      erreur.alerte("Game fail to close normally.");
      System.exit(1); //une erreur a la fermeture.
    }
  }
  /**
   * {@summary Play a turn.}<br>
   * 1a updating Case resources.<br>
   * 2a Make humain player and AI play.<br>
   * 3a Make Insecte play.<br>
   * 4a Add new Insectes.<br>
   * @version 1.1
   */
  public static void tour(){
    if(!getPartie().getContinuerLeJeu()){
      return;
    }
    getGc().tourCases(); //actualisation des ressources sur les cases.
    getGi().preTour(); //actualisation des actions des insectes
    getGj().jouer();
    getGi().tourInsecte();
    if(Main.getPartie().getAppartionInsecte()){
      int nbrDInsecteRestant = math.max( getGc().getNbrDeCase()/5 -  getGi().getGiVivant().length(),0);
      int x2 = math.min( getGc().getNbrDeCase()/20, nbrDInsecteRestant);
      String s = g.get("SpawnOf")+" "+x2+" "+g.get("insecte")+g.get("s");
      new Message(s);
      getGi().add((x2*9)/10); //les insectes vivants n'apparaissent pas sur des cases déja occupé.
      getGi().add(x2/10);
    }
  }


}
