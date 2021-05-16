package fr.formiko.views.gui2d;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.GCreature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Joueur;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.formiko.ThScript;
import fr.formiko.formiko.triche;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.Th;
import fr.formiko.usuel.ThTriche;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.List;
import fr.formiko.usuel.sauvegarderUnePartie;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;
import fr.formiko.views.View;
import fr.formiko.formiko.GJoueur;

import java.util.LinkedList;
import java.util.Scanner;

/**
 *{@summary View Graphics User Interface in 2 dimention.}<br>
 *@author Hydrolien
 *@version 1.44
 */
public class ViewGUI2d implements View {
  private CCase lookedCCase=null;
  private boolean actionGameOn;
  private ThTriche trich; //écoute de commande triche dans le terminal.
  /***
  *Main windows
  *@version 1.1
  */
  private Fenetre f;
  private boolean needToWaitForGameLaunch=true;
  // GET SET -------------------------------------------------------------------
  public boolean getActionGameOn(){return actionGameOn;}
  //Graphics components.
  public Fenetre getF(){return f;}
  public PanneauPrincipal getPp(){ return getF().getPp();}
  public PanneauJeu getPj(){ return getPp().getPj();}
  public PanneauMenu getPm(){ return getPp().getPm();}
  public PanneauNouvellePartie getPnp(){ return getPm().getPnp();}
  public PanneauBouton getPb(){ return getPj().getPb();}
  public PanneauCarte getPc(){ return getPj().getPc();}
  public PanneauInfo getPi(){ return getPb().getPi();}
  public PanneauZoom getPz(){ return getPb().getPz();}
  public PanneauAction getPa(){ return getPb().getPa();}
  public PanneauChargement getPch(){ try {return getPj().getPch();}catch (Exception e) {return null;}}
  public PanneauSup getPs(){ try {return getPj().getPs();}catch (Exception e) {return null;}}
  public PanneauEchap getPe(){ return getPj().getPe();}
  public PanneauDialogue getPd(){ try {return getPj().getPd();}catch (Exception e) {return null;}}
  public PanneauDialogueInf getPdi(){ return getPj().getPdi();}
  /**  // FUNCTIONS -----------------------------------------------------------------
  *{@summary Initialize all the thing that need to be Initialize before using view.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.42
  */
  public boolean ini(){
    erreur.alerte("re ini");//@a
    actionGameOn=false;
    Main.startCh();
    if(f!=null) {f.dispose();}
    f = new Fenetre();
    iniThTriche();
    Main.getData().setImageIniForNewGame(false);//force reload of ant images.
    Main.endCh("iniView");Main.startCh();
    ini.initialiserToutLesPaneauxVide();
    Main.endCh("chargementPanneauVide");
    loadGraphics();
    System.out.println(getPm());//@a
    return true;
  }
  /**
  *{@summary close all the thing that need to be close after using view.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.44
  */
  public boolean close(){
    actionGameOn=false;
    try {
      getF().quit();
    }catch (Exception e) {
      erreur.erreur("Fail to close windows","close full game");
      System.exit(2);
    }
    return true;
  }
  /**
  *{@summary Refrech actual view.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.42
  */
  public boolean paint(){
    if(f==null){erreur.alerte("La fenetre est null & ne peu pas être redessinée.");}
    getF().repaint();
    return true;
  }
  /**
  *{@summary Load main menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.44
  */
  public boolean menuMain(){
    // if(actionGameOn){action.retournerAuMenu();}
    actionGameOn=false;
    if(f==null || getPm()==null){ini();}
    getPm().buildPanneauMenu(3,0);
    paint();
    if(needToWaitForGameLaunch){
      // waitForGameLaunch();
      needToWaitForGameLaunch=false;
    }else{
      erreur.info("don't need to wait for game launch");
    }
    return true;
  }
  /**
  *{@summary Load new game menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.44
  */
  public boolean menuNewGame(){
    // if(actionGameOn){action.retournerAuMenu();}
    actionGameOn=false;
    if(f==null || getPm()==null){ini();}
    getPm().buildPanneauMenu(3,1);
    paint();
    return true;
  }
  /**
  *{@summary Load the save load menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.44
  */
  public boolean menuLoadAGame(){
    // if(actionGameOn){action.retournerAuMenu();}
    actionGameOn=false;
    if(f==null || getPm()==null){ini();}
    getPm().removeP();
    getPm().addPcp();
    return true;
  }
  /**
  *{@summary personalise a game menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.44
  */
  public boolean menuPersonaliseAGame(){
    // if(actionGameOn){action.retournerAuMenu();}
    actionGameOn=false;
    if(f==null || getPm()==null){ini();}
    getPm().addPnp();
    return true;
  }
  /**
  *{@summary options menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.44
  */
  public boolean menuOptions(){
    // if(actionGameOn){action.retournerAuMenu();}
    actionGameOn=false;
    if(f==null || getPm()==null){ini();}
    erreur.erreurPasEncoreImplemente();
    return true;
  }
  /**
  *{@summary Launch action game part.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.42
  */
  public boolean actionGame(){
    actionGameOn=true;
    if(f==null){ini();}
    if(action.getPartie()!=null){
      Main.setPartie(action.getPartie());
      action.setPartie(null);
    }else if(Partie.getScript()==null || Partie.getScript().equals("")){
      Main.setPartie(Partie.getDefautlPartie());
    }//partie can still be null here if script!=""
    Main.startCh();
    getPp().removePm();//on retire le menu
    Main.endCh("chargementPanneauChargementEtSuppressionMenu");
    getPj().addPch();//on met le panneau de chargement au 1a plan.
    Main.startCh();
    getPb().addPz();
    Main.endCh("ajoutPanneauZoom");Main.startCh();
    if(Partie.getScript().equals("tuto")){
      Main.iniCpt();
      Partie.setPartieTutoInMain();
    }
    Main.getPartie().initialisationElément();
    Main.getData().chargerImages(); //on a besoin du bon zoom pour effectuer cette action.
    action.doActionPj(8);
    Main.endCh("chargementImagesDelaCarte");

    String s = g.get("chargementFini");
    if (debug.getAffLesPerformances()==true){s=s +" "+ "("+Temps.msToS(Main.getLonTotal())+")";}
    Main.setMessageChargement(s);
    if(!Main.getOp().getAttendreAprèsLeChargementDeLaCarte() || Main.getPremierePartie()){
      closePanneauChargement();
      paint();
    }else{
      getPch().addBt();
    }
    Main.getPartie().getGj().prendreEnCompteLaDifficulté();//setFood acording to difficutly.
    if(!Partie.getScript().equals("")){
      ThScript ths = new ThScript(Partie.getScript()+".formiko");
      Main.setScript(ths);
      Main.launchScript();
    }
    Main.getPartie().jeu(); //lance le jeux.
    erreur.alerte("jeu is over");//@a
    return true;
  }


  //Only on action game mode :
  /**
  *{@summary Stop game and print the escape menu.}<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.42
  */
  public int pauseActionGame(){
    if (!actionGameOn) {return -1;}
    return 0;
  }
  /**
  *{@summary Stop game and print the end menu.}<br>
  *This action can only be run if action game is on.<br>
  *@param withButton true if we need to add button "return to main menu" and "next level".
  *@param nextLevel the number of the next level to link to the button. -1 = no next level.
  *@param message message to print.
  *@param gj sorted player list to print.
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.46
  */
  public boolean endActionGame(boolean withButton, int nextLevel, String message, GJoueur gj){
    erreur.info("message");//@a
    System.out.println(gj);//@a
    try {
      getPj().addPfp(message, gj);
    }catch (Exception e) {
      return false;
    }
    return true;
  }
  /**
  *{@summary Change the value of the loked Case.}<br>
  *We need to repaint the information about this Case.<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.46
  */
  public boolean setLookedCCase(CCase cc){
    if (!actionGameOn) {return false;}
    if(cc==null){
      getPb().setDesc("");
      getPc().setIdCurentFere(-1);
    }else{
      getPb().setDesc(cc.getContenu().toString());
      GCreature gAnt = cc.getContenu().getGc();
      getPc().setIdCurentFere(-1);
      for (Creature f : gAnt.toList() ) {
        if(f instanceof Fourmi){
          getPc().setIdCurentFere(((Fourmi)(f)).getFere().getId());
          break;
        }
      }
    }
    getPc().setLookedCCase(cc);
    return true;
  }
  /**
  *{@summary Return the value of the looked CCase.}<br>
  *This action can only be run if action game is on.<br>
  *@return lookedCCase.
  *@version 1.46
  */
  public CCase getLookedCCase(){
    if (!actionGameOn) {return null;}
    return getPc().getLookedCCase();
  }
  /**
  *{@summary Return the chosen value for ant action.}<br>
  *This action can only be run if action game is on.<br>
  *@return Return ant choice.
  *@version 1.42
  */
  public int getAntChoice(int t[]){
    if (!actionGameOn) {return -1;}
    return 0;
  }
  /**
  *{@summary Return the chosen CCase.}<br>
  *It is used to move ant.
  *@version 1.42
  */
  public CCase getCCase(){
    if (!actionGameOn) {return null;}
    return null;
  }
  /**
  *{@summary Print a message.}<br>
  *If message.equals("") we may need to delete last message, but we don't need to print a new message.<br>
  *@param message the message to print.
  *@param doWeNeedToDoNextCmdNow true if we need to do next commande now.
  *@version 1.44
  */
  public void message(String message, boolean doWeNeedToDoNextCmdNow){
    if (!actionGameOn) {return;}
    if(f==null){
      System.out.println("message force initialisation");//@a
      ini();
    }
    try {
      getPj().initialiserPd(message);
    }catch (Exception e) {
      erreur.alerte("can't print message : "+message);
    }
    try {
      getPdi().removeBSuivant();
    }catch (Exception e) {}
    try {
      Main.getScript().setCmdSuivante(doWeNeedToDoNextCmdNow);
      if(!doWeNeedToDoNextCmdNow){
        getPdi().addBSuivant();
        Fourmi.setBActualiserTaille(true);//écoute de toute la fenetre.
      }else{
        getPs().actualiserTaille();//écoute normale
      }
    }catch (Exception e) {//par défaut on attend avant de passer a la commande suivante.
      Main.getScript().setCmdSuivante(false);
      getPdi().addBSuivant();
      Fourmi.setBActualiserTaille(true);//écoute de toute la fenetre.
    }
  }
  /**
  *{@summary Print a loading message.}<br>
  *@param message the message to print.
  *@param percentageDone the percentage of loading curently done.
  *@version 1.46
  */
  public void loadingMessage(String message, int percentageDone){
    if (getPch()==null) {return;}
    try {
      getPch().setTexte(message);
      //TODO update barre de chargement with percentageDone
    }catch (NullPointerException e) {
      erreur.alerte("Fail to print loadingMessage");
    }
  }
  /**
  *{@summary Print a message in a new window.}<br>
  *@param message the message to print.
  *@version 1.46
  */
  public void popUpMessage(String message){
    if (getPch()!=null) {return;}
    //Main.getPartie().getPlayingAnt() is null but window didn't clear all data.
    getPb().setVisiblePa(false);
    getPj().setDesc("");
    getPb().removePi();
    paint();
    getPj().alerte(message);
  }

  /**
  *{@summary remove PanneauChargement & listen mouse clic on the map.}<br>
  *@version 1.44
  */
  public void closePanneauChargement(){
    if (!actionGameOn) {return;}
    getPj().removePch();
    getPs().build();
  }
  /**
  *{@summary set playing ant.}<br>
  *This action can only be run if action game is on.<br>
  *@version 1.46
  */
  public void setPlayingAnt(Fourmi f){
    if (!actionGameOn) {return;}
    // if (!f.getFere().getJoueur().getIa()) {
    //   getPj().setFActuelle(null);
    //   getPb().setVisiblePa(false);
    // }
    // getPs().setIdFourmiAjoué(-1);
  }
  //private---------------------------------------------------------------------
  /**
  *Load graphics.
  *@version 1.42
  */
  private void loadGraphics(){
    Main.startCh();
    if(Main.getChargementPendantLesMenu()){chargementDesGraphismesAutonomes();}
    else{ini.initialiserPanneauJeuEtDépendance();ini.initialiserAutreELémentTournés();}
    Main.endCh("chargementDesGraphismesAutonomes");
  }
  /**
  *Load graphics during menu time.
  *@version 1.1
  */
  private void chargementDesGraphismesAutonomes(){
    if(Main.getPremierePartie()){ini.initialiserPanneauJeuEtDépendance();}
    else{
      Th thTemp = new Th(1);
      thTemp.start();
    }
    Th thTemp2 = new Th(2);
    thTemp2.start();
  }
  /**
  *{@summary Initialize cheat code listener if it haven't been yet.}<br>
  *@version 1.42
  */
  private void iniThTriche(){
    try {
      if(trich == null || !trich.isAlive()){
        triche.ini();
        trich = new ThTriche();//écoute des codes triche.
        trich.start();
      }
    }catch (Exception e) {
      erreur.erreur("Impossible de lancer l'écoute des codes triches.");
    }
  }

  public synchronized void waitForGameLaunch(){
    if(!Main.getPremierePartie()){
      boolean b=false;
      while(!b){Temps.pause(10);b=getPm().getLancer();}
    }else{
      //play launching video
    }
    actionGame();
  }
}
