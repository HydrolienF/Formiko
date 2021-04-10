package fr.formiko.formiko;

import fr.formiko.usuel.*;
import fr.formiko.usuel.images.*;
import fr.formiko.usuel.listes.*;
import fr.formiko.usuel.maths.math;
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
*{@summary Launch class }<br>
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
  private static String versionActuelle = "1.33";
  private static Options op;
  private static Chrono ch;
  private static long lon;
  private static long lonTotal;
  private static long tempsDeDébutDeJeu;
  private static Partie pa;
  private static byte niveauDeDétailDeLAffichage=3;
  private static Pixel pi;
  private static HashMap<String, Integer> key; //keyboard key.
  private static boolean ecouteClavier;
  private static int avancementChargement;
  private static boolean affGraine=true;//tant que les espece granivore ne sont pas pleinement opérationelle.
  private static Temps tem;
  private static ThTriche trich; //écoute de commande triche dans le terminal.
  //private static ThGraphisme tg;//actualise la fenetre tt avec 20 seconde de pause entre chaque actualisation.
  private static boolean retournerAuMenu;
  private static Os os;
  private static Folder folder;
  private static boolean tuto=false;
  private static ThScript ths;
  //private static ThMusique thm;
  private static boolean premierePartie=false;
  private static boolean jeuEnCours;
  private static Data data;
  private static View view;

  private static boolean modeCLI=false;

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
    debug.setAffLesEtapesDeRésolution(false);
    debug.setAffLesPerformances(false);
    debug.setAffG(false);
    int k=0;
    while(args.length > k){//si il y a des options a "-"
      if(args[k].length()>1 && args[k].substring(0,1).equals("-")){
        launchOptions(args[k].substring(1));
        args = tableau.retirer(args, k);
      }else{
        k++;
      }
    }
    if(args.length>0){
      if(args[0].equals("trad")){
        initialisation();
        tradCmd();
      }else if(args[0].equals("son")){
        //System.out.println(Musique.getMusiqueAlleatoire());
      }else if(args[0].equals("op")){
        //initialisation();
        os = new Os();
        folder = new Folder();
        iniOp();
        op.saveOptions();
      }else if(args[0].equals("supprimer")){
        initialisation();
        //diff.nbrDeLigneDiff("usuel/GString.java","../Formiko108/usuel/GString.java");
        if(args.length == 4){
          String s = args[1];
          //c'est pas nésséssaire sur le terminal linux mais au cas ou
          if(s.charAt(0)=='"' && s.charAt(s.length()-1)=='"'){
            s = s.substring(1,s.length()-1);
          }
          String f = args[2];
          byte x = str.sToBy(args[3]);
          modificationDeFichier.retirerLesLignesR(s,f,x);//3== ca doit etre une ligne complète.
        }else{
          erreur.alerte("arguments de supprimer incorecte");
        }
      }else if(args[0].equals("test")){
        System.out.println("test");
      }else if(args[0].equals("trad2")){
        initialisation();
        chargerLesTraductions.iniTLangue();
        chargerLesTraductions.créerLesFichiers();
        g.setMap(chargerLesTraductions.chargerLesTraductions(1));//chargement des langues.
        HashMap<String, String> mapEo = chargerLesTraductions.chargerLesTraductions(0);//chargement des langues.
        trad.copieTradBase("eo",mapEo);
        //chargerLesTraductions.addTradAuto();
      }else if (args[0].equals("rbt") || args[0].equals("rognerBordTransparent")){
        initialisation();
        try {
          rbtCmd(args);
        }catch (Exception e) {
          erreur.erreur("echec de rognage de l'image");
        }
      }else if(args[0].equals("stats")){
        if(args.length>1){
          stats.statsJavadoc(args[1]);
        }else{
          stats.statsJavadoc("src/main/",true);
        }
      }else if(args[0].equals("cptPixels")){
        if(args.length>1){
          //image.setREPTEXTUREPACK("docs/cc/images");
          debug.débogage("chargement de l'image");
          Img img = new Img(image.getImage(args[1],"docs/cc/images/"));
          debug.débogage("Image chargée");
          img.compterChaquePixelToHtml();
        }else{
          erreur.alerte("arguments de cptPixels incorecte");
        }
      }else if(args[0].equals("cleanFolder")){
        folder = new Folder();
        folder.cleanFolder();
      }else{
        erreur.erreur("Votre options a "+(args.length)+" agruments n'as pas été reconnue");
      }
      quitter();
    }else{ // si il n'y a pas d'options ou que des options a "-".
      // LE JEU -------------------------------------------------------------------
      boolean continuerJeu=true;
      while(continuerJeu){
        continuerJeu = launch();//on attend ici tant que le joueur veux jouer.
        debug.débogage("ReLancement du jeu");
        try {
          getF().dispose();
        }catch (Exception e) {
          erreur.info("Window can not be dispose.");
        }
        retournerAuMenu=false;
        //op=null;//force la réinitialisation de tout.
        image.clearPartielTemporaire();
      }
    }
    quitter();//en théorie on arrive pas là.
  }
  /**
   * {@summary pre launch.}<br>
   * @version 1.7
   */
  public static void iniLaunch(){
    //premierePartie=true;
    if(premierePartie){tuto=true;}
    avancementChargement=-1;
    //on initialise ici si ça n'as pas déja été fait par une options.
    setJeuEnCours(false);
    if(getOp()==null){initialisation();}
    iniCpt();
    avancementChargement=0;
    ecouteClavier=true;

    startCh();
    pa = new Partie(0,0,new Carte(new GCase(1,1)),1.0); //nouvelle partie vide.
    endCh("chargementPartieEtCarteBlanche");
  }
  /**
   * {@summary Launch in the void main if there is not other args than -something (ex : -d).}<br>
   * @version 1.33
   */
  public static boolean launch(){
    iniLaunch();
    //===
    if (modeCLI) {
      view = new ViewCLI();
      view.ini();
      view.menuMain();
      quitter();
    }else{
      view = new ViewGUI2d();
      view.ini();
    }
    pa = attenteDeLancementDePartie();
    lancementNouvellePartie();
    Boolean b = pa.jeu(); //lance le jeux.
    //===
    if(b){return true;}
    return false;
  }
  /**
   * {@summary Wait until player launch a new game, the tutorial or Load a game.}<br>
   * @version 1.7
   */
  public static Partie attenteDeLancementDePartie(){
    //attente
    debug.débogage("attente de lancement de la partie");
    Main.repaint();
    boolean b=false;
    while(!b && !premierePartie){Temps.pause(10);b=getPm().getLancer();}
    return action.getPartie();
    //debug.débogage("lancementNouvellePartie");
  }
  /**
   * {@summary Launch a new game.}<br>
   * @version 1.14
   */
  public static void lancementNouvellePartie(){ //Nouveau système de lancement de partie :
    startCh();
    getPp().removePm();//on retire le menu
    endCh("chargementPanneauChargementEtSuppressionMenu");//startCh();
    if(premierePartie){tuto=true;} if(tuto){pa=Partie.getPartieTuto();}
    else if(pa==null){pa=Partie.getDefautlPartie();}
    getPj().addPch();//on met le panneau de chargement au 1a plan.
    getF().printAll(getF().getGraphics());
    //endCh("chargementDézoom");
    //pa.setEnCours(true); //lance l'affichage de la Partie.
    //startCh();
    //la ligne qui suis n'as d'effet que si elle n'as pas déjà été appliqué a la partie.
    pa.initialisationElément(); // pour l'instant ce bout de code ne marche pas ayeur qu'ici.
    startCh();
    Main.getPb().addPz();
    endCh("ajoutPanneauZoom");startCh();
    getData().chargerImages(); //on a besoin du bon zoom pour effectuer cette action.
    if(Main.getDimY()!=1080 || getPartie().getGc().getNbrY()!=9){
      getPj().dézoomer((byte)2);//on met la carte a la taille la plus grande possible pour qu'on voit tout.
    }
    endCh("chargementImagesDelaCarte");
    String s = g.get("chargementFini");
    if (debug.getAffLesPerformances()==true){s=s +" "+ "("+Temps.msToS(lonTotal)+")";}
    setMessageChargement(s);
    getPch().addBt();
    //affichageDeLaPageDeChargement
    boolean b=!op.getAttendreAprèsLeChargementDeLaCarte();
    if(premierePartie){b=true;}
    //attente de validation du panneau de chargement.
    while(!b){Temps.pause(10);b=getPch().getLancer();}
    view.actionGame();
    getPj().removePch();
    getPs().construire();
    getGj().prendreEnCompteLaDifficulté();
    if(premierePartie){tuto=true;}
    if(tuto){iniParamètreCarteTuto();}
    /*else{//si ce n'est pas le tuto on change la musique.
      thm.stopThm();
      thm = new ThMusique();
      thm.start();
    }*/
  }
  /**
   * {@summary Initializes the tutorial parameters.}<br>
   * @version 1.1
   */
  public static void iniParamètreCarteTuto(){
    Fourmiliere fere = Main.getGj().getDébut().getContenu().getFere();
    CCase ccIni = Main.getPartie().getGc().getCCase(0,1);
    fere.setCc(ccIni);
    fere.getGc().getDébut().getContenu().setCCase(ccIni);
    Insecte i = new Insecte(Main.getPartie().getGc().getCCase(1,1),0,100,0);
    i.setNourritureFournie(200);
    i.setEstMort(false);
    i.setType(8);
    getGi().addInsecte(i);
    ths = new ThScript("tuto.formiko");
    ths.start();
  }
  /**
   * Allow the player to get back to main menu.
   * @version 1.1
   */
  public static void retourAuMenu(){
    Carte mapo = new Carte(new GCase(1,1));
    pa = new Partie(0,0,mapo,1.0); //nouvelle partie vide.
    debug.débogage("lancement du retour au menu");
    Main.getPp().removePj();
    //Main.getPp().removePm();
    ini.initialiserToutLesPaneauxVide();
    ini.initialiserPanneauJeuEtDépendance();
    getPm().construitPanneauMenu(3);
    repaint();
    debug.débogage("fin du retour au menu");
    attenteDeLancementDePartie();
  }

  // GET SET ----------------------------------------------------------
  public static byte getNiveauDeDétailDeLAffichage(){return niveauDeDétailDeLAffichage;}
  public static String getVersionActuelle(){return versionActuelle;}
  public static Espece getEspece(){return getEspeceParId(0);}
  public static Espece getEspeceParId(int id){ return getGe().getEspeceParId(id);}
  public static GEspece getGEspece(){ return getGe();}
  public static Joueur getJoueurParId(int id){ return Main.getGj().getJoueurParId(id);}
  public static Fourmiliere getFourmiliereParId(int id){ return getJoueurParId(id).getFere();}
  public static Fenetre getF(){ return ((ViewGUI2d)view).getF();}
  // public static Fenetre getF(){ return f;}
  public static Options getOp(){return op;}
  public static Chrono getCh(){ return ch;}
  public static int getKey(String clé){ int r = key.get(clé);if(r==-1){return -1;}return r; }
  public static Partie getPartie(){ return pa;}
  public static void setPartie(Partie p){pa=p;}
  public static Pixel getPiFond(){ return pi;}
  public static boolean getEcouteClavier(){ return ecouteClavier;}
  public static void setEcouteClavier(Boolean b){ecouteClavier=b;}
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
  public static boolean getJeuEnCours(){return jeuEnCours;}
  public static void setJeuEnCours(boolean b){jeuEnCours=b;}
  public static Data getData(){return data;}
  public static ThTriche getThTriche(){return trich;}
  public static View getView(){return view;}
  //racourci
  public static boolean estWindows(){return os.getId()==1;}
  public static String get(String clé){ return g.get(clé);}
  public static Script getScript(){return ths.getScript();}
  //musique
  /*public static ThMusique getThm(){return thm;}
  public static Musique getMusique(){return getThm().getM();}
  public static void setMusique(Musique m){getThm().setM(m);}
  public static void setMusiqueSuivante(){getThm().setM();}*/
  //graphique
  public static PanneauPrincipal getPp(){ return getF().getPp();}
  public static void repaint(){try { getF().repaint();}catch (Exception e) {view.paint();}}
  //public static synchronized void repaint(){try { f.paintAll(f.getGraphics());}catch (Exception e) {}}
  public static PanneauJeu getPj(){ return getPp().getPj();}
  public static PanneauMenu getPm(){ return getPp().getPm();}
  public static PanneauNouvellePartie getPnp(){ return getPm().getPnp();}
  public static PanneauBouton getPb(){ return getPj().getPb();}
  public static PanneauCarte getPc(){ return getPj().getPc();}
  public static PanneauInfo getPi(){ return getPb().getPi();}
  public static PanneauZoom getPz(){ return getPb().getPz();}
  public static PanneauAction getPa(){ return getPb().getPa();}public static PanneauAction getPac(){return getPa();}
  public static Fourmi getPlayingAnt(){ try {return getPartie().getPlayingAnt();}catch (Exception e) {return null;}}
  public static void setPlayingAnt(Fourmi f){ getPartie().setPlayingAnt(f);}
  public static Joueur getPlayingJoueur(){ try {return getPartie().getPlayingJoueur();}catch (Exception e) {return null;}}
  public static PanneauChargement getPch(){ try {return getPj().getPch();}catch (Exception e) {return null;}}
  public static PanneauSup getPs(){ try {return getPj().getPs();}catch (Exception e) {return null;}}
  public static PanneauEchap getPe(){ return getPj().getPe();}
  public static PanneauDialogue getPd(){ try {return getPj().getPd();}catch (Exception e) {return null;}}
  public static PanneauDialogueInf getPdi(){ return getPj().getPdi();}
  public static int getDimX(){ try {return getPp().getWidth();}catch (Exception e){erreur.erreur("Impossible de récupérer les dim de Pp");return 1;}}
  public static int getDimY(){ try {return getPp().getHeight();}catch (Exception e){erreur.erreur("Impossible de récupérer les dim de Pp");return 1;}}
  public static int getWidth(){return getDimX();}
  public static int getHeight(){return getDimY();}
  public static int getTailleElementGraphique(int x){ return math.min(getTailleElementGraphiqueY(x),getTailleElementGraphiqueX(x));}
  public static int getTailleElementGraphiqueX(int x){ return (x*getDimX())/1920;}
  public static int getTailleElementGraphiqueY(int x){ return (x*getDimY())/1080;}
  public static double getRacioEspaceLibre(){return 900.0/1080.0;}
  public static int getDimXCarte(){System.out.println(getRacioEspaceLibre());return (int)(1920.0*getRacioEspaceLibre());}
  //options
  public static byte getLanguage(){ return op.getLanguage();}
  public static void setLangue(int x){ op.setLangue(x);iniLangue();}
  public static int getbuttonSizeZoom(){return op.getbuttonSizeZoom();}
  public static int getTailleBoutonAction(){return op.getTailleBoutonAction();}
  public static int getTailleBoutonTX(){return op.getTailleBoutonTX();}
  public static boolean getMouvementRapide(){ return op.getMouvementRapide();}
  public static void setMouvementRapide(boolean b){ op.setMouvementRapide(b);}
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
    folder = new Folder();
    getFolder().ini();
    view = new ViewNull();
    //if(!arbo.arborécenceIntacte()){arbo.réparationArboréscence();}
    setMessageChargement("chargementDesOptions");startCh();
    chargerLesTraductions.iniTLangue();
    iniOp();
    sauvegarderUnePartie.setSave(Save.getSave());
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
    triche.ini();
    trich = new ThTriche();//écoute des codes triche.
    startCh();
    tem = new Temps();
    endCh("chargementDesDonnéesTemporelles");
    setMessageChargement("chargementDesEspeceDeFourmi");startCh();
    Partie.iniGe(); // chargement des Especes.
    GIndividu.chargerLesIndividus(); // chargement de leur individu.
    endCh("chargementDesIndividuDeFourmi");startCh();
    Insecte.setGie(); // chargement des Insectes.
    endCh("chargementDesEspeceDInsecte");
    data = new Data();
    iniCpt();
  }
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
    //TODO
    //op = chargerLesOptions.chargerLesOptions(getVersionActuelle());
    op = new Options();
    op.iniOptions();
  }
  /**
   * Load language.
   * @version 1.1
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
   * @version 1.14
   */
  public static void setMessageChargement(String s){
    //s c'est un truc du genre "chargementDesLangues"
    String s2 = g.getM(s)+"..."; //g.getM() permet d'aller chercher la traduction dans la table de hachage HashMap<String, String> map.
    try {
      getPch().setTexte(s2); //envoie a la fenetre le message d'avancement du chargement.
    }catch (Exception e) { // si quelque chose ce passe mal on envoie un message a la console.
      //erreur.alerte("Un message de chargement n'est pas arrivé a destination.");
    }
  }
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
  public static void endCh(String s){Chrono.endCh(s);}
  public static void startCh(Chrono chTemp){Chrono.debutCh(chTemp);}
  public static void endCh(String s,Chrono chTemp){ // fin du Chrono.
    lon = chTemp.getDuree(); lonTotal=lonTotal+lon;
    Chrono.endCh(s,chTemp);
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
      startCh();
      pa.enregistrerLesScores();
      endCh("enregistementDesScores");
      startCh();
      if(getGarderLesGraphismesTourné()){image.clearPartielTemporaire();}
      else{image.clearTemporaire();}
      endCh("vidageDesFichiersImages");
      String s = "toutes les opérations longues ";
      debug.performances("temps pour "+ s + " : "+lonTotal+" ms");
      long tempsDeFinDeJeu=System.currentTimeMillis();
      long tempsJeuEcoulé = tempsDeFinDeJeu-tempsDeDébutDeJeu;
      tem.addTempsEnJeux(tempsJeuEcoulé);tem.actualiserDate2();tem.sauvegarder();
      sauvegarderUnePartie.getSave().save();//sauvegarde de l'idS (id de sauvegarde) + de futur valeur importante.
      System.out.println(g.getM("tempsJeuEcoulé")+" : "+Temps.msToTime(tempsJeuEcoulé,2,false));
      System.out.println(g.getM("messageQuitter"));
      System.exit(0);
    }catch (Exception e) {
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
      new Message("Ajout de "+x2+" insectes");
      getGi().addInsecte((x2*9)/10); //les insectes vivants n'apparaissent pas sur des cases déja occupé.
      getGi().addInsecte(x2/10);
    }
  }
  /**
  *{@summary Update translation}
  *@version 1.21
  */
  public static void tradCmd(){
    startCh();
    chargerLesTraductions.iniTLangue();
    chargerLesTraductions.créerLesFichiers();
    endCh("créerLesFichiers");startCh();
    g.setMap(chargerLesTraductions.chargerLesTraductions(1));//chargement des langues.
    endCh("chargerLesTraductions");startCh();
    trad.copieTrads();
    endCh("copieTrads");startCh();
    chargerLesTraductions.affPourcentageTraduit();
    endCh("affPourcentageTraduit");startCh();
    /*chargerLesTraductions.addTradAuto();
    endCh("addTradAuto");startCh();
    chargerLesTraductions.affPourcentageTraduit();
    endCh("affPourcentageTraduit");*/
  }
  /**
  *{@summary trim the image from args.}
  *@version 1.21
  */
  public static void rbtCmd(String args[]){
    String name = "";
    name = args[1];int k=2;
    while(name!=null){
      debug.débogage("=============================Chargement de l'image "+name);
      //Image i = image.getImage(nom,image.getREP());
      Img img = new Img(image.getImage(name,image.getREP()));
      debug.débogage("=============================Ronage de l'image "+name);
      img.rognerBordTransparent();
      img.actualiserImage();
      debug.débogage("=============================Sauvegarde de l'image "+name);
      img.sauvegarder(image.getREP(),name+".png");
      try {
        name=args[k++];
      }catch (Exception e) {
        name=null;
      }
    }
  }
  /**
  *{@summary Launch a -options.}<br>
  *-options affect game but launch it, when non "-" options never launch game.<br>
  *Here is the list of the aviable -options.<br>
  *<ul>
  *<li>-d Print all the debugs infos.
  *<li>-p Print performances info for long action.
  *<li>-g Print the graphics debugs infos.
  *<li>-rg Reload all the graphics saved in data/temporary/images.
  *<li>-cli Launch game but in Console Line Interface.
  *</ul>
  *@version 1.39
  */
  private static void launchOptions(String stringOptions){
    switch(stringOptions){
      case "d":
      debug.setAffLesEtapesDeRésolution(true);
      break;
      case "p":
      debug.setAffLesPerformances(true);
      break;
      case "g":
      debug.setAffG(true);
      break;
      case "rg":
      case "reload--graphics":
      initialisation();
      getOp().setGarderLesGraphismesTourné(false);
      break;
      case "cli":
      modeCLI=true;
      break;
      default:
      erreur.alerte("Unknow cli options : "+stringOptions);
    }
  }
}
