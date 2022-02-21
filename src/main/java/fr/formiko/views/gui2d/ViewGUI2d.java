package fr.formiko.views.gui2d;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.GCreature;
import fr.formiko.formiko.GJoueur;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.ObjetSurCarteAId;
import fr.formiko.formiko.Partie;
import fr.formiko.formiko.interfaces.TourFourmiNonIa;
import fr.formiko.formiko.triche;
import fr.formiko.usuel.DiscordIntegration;
import fr.formiko.usuel.Info;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.Th;
import fr.formiko.usuel.ThTriche;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.types.str;
import java.awt.Window;
import javax.swing.FocusManager;
import fr.formiko.views.View;

import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JColorChooser;
import javax.swing.UIManager;

/**
*{@summary View Graphics User Interface in 2 dimention.}<br>
*@author Hydrolien
*@lastEditedVersion 1.44
*/
public class ViewGUI2d implements View {
  // private CCase lookedCCase=null;
  private boolean actionGameOn;
  private ThTriche trich; //écoute de commande triche dans le terminal.
  /***
  *Main windows
  *@lastEditedVersion 1.1
  */
  private FFrameMain f;
  private FFrameLauncher fl;
  private boolean needToWaitForGameLaunch=true;
  private Timer timer;
  // private boolean canRefresh=true;
  private int curentFPS=0;
  private CCase ccaseClicked;
  private boolean moveMode=false;
  private volatile boolean launchFromPm;
  private boolean bladeChanged;
  // GET SET -------------------------------------------------------------------
  public boolean getActionGameOn(){return actionGameOn;}
  //Graphics components.
  public FFrameMain getF(){return f;}
  public FFrameLauncher getFl(){return fl;}
  public FPanelPrincipal getPp(){ try{return getF().getPp();}catch (NullPointerException e) {return null;}}

  public FPanelJeu getPj(){ if(getPp()!=null){return getPp().getPj();}else{return null;}}
  public FPanelMenu getPm(){ if(getPp()!=null){return getPp().getPm();}else{return null;}}
  public FPanelNouvellePartie getPnp(){ try{return getPm().getPnp();}catch (NullPointerException e){return null;}}
  public FPanelGEtiquetteJoueur getPGej(){ try{return getPnp().getPGej();}catch (NullPointerException e){return null;}}
  public JColorChooser getJcc(){ try{return getPnp().getJcc();}catch (NullPointerException e){return null;}}
  public FPanelChoixPartie getPcp(){ try{return getPm().getPcp();}catch (NullPointerException e){return null;}}
  public FPanelBouton getPb(){ try{return getPj().getPb();}catch (NullPointerException e){return null;}}
  public FPanelCarte getPc(){ try{return getPj().getPc();}catch (NullPointerException e){return null;}}
  public FPanelInfo getPi(){ try{return getPb().getPi();}catch (NullPointerException e){return null;}}
  public FPanelInfoText getPij(){ try{return getPb().getPij();}catch (NullPointerException e){return null;}}
  public FPanelZoom getPz(){ return getPb().getPz();}
  public FPanelAction getPa(){ return getPb().getPa();}
  public FPanelChargement getPch(){ try {return getPj().getPch();}catch (NullPointerException e) {return null;}}
  public FPanelSup getPs(){ if(getPj()!=null){return getPj().getPs();}else{return null;}}
  public FPanelSupDialog getPsd(){ if(getPj()!=null){return getPj().getPsd();}else{return null;}}
  public FPanelEchap getPe(){ return getPj().getPe();}
  public FPanelDialogue getPd(){ try {return getPj().getPd();}catch (NullPointerException e) {return null;}}
  public FPanelDialogueInf getPdi(){ return getPj().getPdi();}
  public FPanelMiniMapContainer getPmmc(){try {return getPb().getPmmc();}catch(NullPointerException e){return null;}}
  public FPanelPanelMove getPmmo(){if(getPj()!=null){return getPj().getPmmo();}else{return null;}}
  public int getCurentFPS(){return curentFPS;}
  public void setCurentFPS(int x){curentFPS=x;}
  public int getWidth(){try {return getPp().getWidth();}catch (NullPointerException e) {return 0;}}
  public int getHeight(){try {return getPp().getHeight();}catch (NullPointerException e) {return 0;}}
  // public Case getCaseClicked(){return caseClicked;}
  // public void setCaseClicked(Case c){caseClicked=c;}
  public void setLaunchFromPm(boolean b){launchFromPm=b;}
  public boolean getBladeChanged(){return bladeChanged;}
  public void setBladeChanged(boolean b){bladeChanged=b;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Initialize all the thing that need to be Initialize before using view.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@lastEditedVersion 1.42
  */
  public boolean ini(){
    actionGameOn=false;
    Main.startCh();
    if(f!=null) {f.dispose();}
    f = new FFrameMain();
    iniFont();
    iniThTriche();
    iniDiscordIntergation();
    Main.getData().setImageIniForNewGame(false);//force reload of ant images.
    Main.endCh("iniView");Main.startCh();
    ini.initializeEmptyFPanel();
    Main.endCh("chargementFPanelVide");
    loadGraphics();
    // if(Main.getOp().getModeFPS()){
      launchFrameRefresh();
    // }
    getPp().updateVersionLabel();
    return true;
  }
  /**
  *{@summary close all the thing that need to be close after using view.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@lastEditedVersion 1.44
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
  *{@summary Refrech actual view without constant fps.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@lastEditedVersion 1.47
  */
  public boolean paint(){
    if(!Main.getOp().getModeFPS()){
      if(f==null){erreur.alerte("La fenetre est null & ne peu pas être redessinée."); return false;}
      getF().repaint(10);
    }
    return true;
  }
  /**
  *{@summary Refrech actual view with constant fps.}<br>
  *It use timer &#38; patch all Java swing issues.
  *@return Return true if it work well. (Nothing goes wrong.)
  *@lastEditedVersion 1.47
  */
  public boolean paintGUI(){
    if(f==null){erreur.alerte("La fenetre est null & ne peu pas être redessinée."); return false;}
    // erreur.info("repaint.",3);
    getF().repaint(10);
    return true;
  }
  /**
  *{@summary Load main menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@lastEditedVersion 1.44
  */
  public boolean menuMain(){
    // if(actionGameOn){action.retournerAuMenu();}
    actionGameOn=false;
    DiscordIntegration.setNeedToUpdateActivity(true);
    if(f==null || getPm()==null){ini();}
    Main.stopScript();
    if(Main.getPremierePartie()){
      getPm().askLanguage();
    }else if(Main.getOpenMenuFirst()){
      getPm().buildFPanelMenu(3,0);
    }else{
      setLaunchFromPm(true);
    }
    paint();
    // if(needToWaitForGameLaunch){
    //   needToWaitForGameLaunch=false;
    // }else{
    //   erreur.info("don't need to wait for game launch");
    // }
    return true;
  }
  /**
  *{@summary Load new game menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@lastEditedVersion 1.44
  */
  public boolean menuNewGame(){
    // if(actionGameOn){action.retournerAuMenu();}
    actionGameOn=false;
    DiscordIntegration.setNeedToUpdateActivity(true);
    if(f==null || getPm()==null){ini();}
    getPm().buildFPanelMenu(3,1);
    paint();
    return true;
  }
  /**
  *{@summary Load the save load menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@lastEditedVersion 1.44
  */
  public boolean menuLoadAGame(){
    // if(actionGameOn){action.retournerAuMenu();}
    actionGameOn=false;
    DiscordIntegration.setNeedToUpdateActivity(true);
    if(f==null || getPm()==null){ini();}
    getPm().removeP();
    getPm().addPcp();
    return true;
  }
  /**
  *{@summary personalise a game menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@lastEditedVersion 1.47
  */
  public boolean menuPersonaliseAGame(){
    // if(actionGameOn){action.retournerAuMenu();}
    actionGameOn=false;
    DiscordIntegration.setNeedToUpdateActivity(true);
    if(f==null || getPm()==null){ini();}
    getPm().addPnp();
    paint();
    return true;
  }
  /**
  *{@summary options menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@lastEditedVersion 1.44
  */
  public boolean menuOptions(){
    // if(actionGameOn){action.retournerAuMenu();}
    actionGameOn=false;
    DiscordIntegration.setNeedToUpdateActivity(true);
    if(f==null || getPm()==null){ini();}
    erreur.erreurPasEncoreImplemente();
    return true;
  }
  /**
  *{@summary Launch action game part.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@lastEditedVersion 2.6
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
    else{
      Main.setPartie(Partie.getDefautlPartie());//TODO checkout if Partie is set in scriptX.formiko
    }
    Main.startCh();
    getPp().removePm();//on retire le menu
    getPs().updateSize();//update Ps just in case it was to big from an other script.
    Main.endCh("chargementFPanelChargementEtSuppressionMenu");
    getPj().iniPch();//on met le panneau de chargement au 1a plan.
    DiscordIntegration.setNeedToUpdateActivity(true);
    Main.startCh();
    getPb().addPz();
    Main.endCh("ajoutFPanelZoom");Main.startCh();
    if(Partie.getScript().equals("tuto")){
      Main.iniCpt();
      Partie.setPartieTutoInMain();
    }
    Main.getPartie().initialisationElément();
    // Main.getData().chargerImages(); //It will be call by the next line "action.doActionPj(8);"
    action.doActionPj(8); //unzoom
    Main.endCh("chargementImagesDelaCarte");

    String s = g.get("chargementFini");
    if (debug.getPerformance()==true){s=s +" "+ "("+Temps.msToS(Main.getLonTotal())+")";}
    Main.setMessageChargement(s);
    if(!Main.getOp().getWhaitBeforeLaunchGame() || Main.getPremierePartie() || !Main.getOpenMenuFirst()){
      closeFPanelChargement();
      paint();
    }else{
      if(getPch()!=null){ //it can have been remove by key "enter".
        getPch().addBt();
      }
    }
    Main.getPartie().jeu(); //launch game
    return true;
  }


  //Only on action game mode :
  /**
  *{@summary Stop game and print the escape menu.}<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@lastEditedVersion 1.42
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
  *@lastEditedVersion 1.46
  */
  public boolean endActionGame(boolean withButton, int nextLevel, String message, GJoueur gj, boolean canResumeGame){
    try {
      //TODO add withButton & next level
      getPj().addPfp(message, gj, withButton, canResumeGame);
    }catch (Exception e) {
      erreur.alerte("can't print FPanelFinPartie.");
      return false;
    }
    return true;
  }
  /**
  *{@summary Change the value of the loked Case.}<br>
  *We need to repaint the information about this Case.<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@lastEditedVersion 1.46
  */
  public boolean setLookedCCase(CCase cc){
    if (!actionGameOn) {return false;}
    if(cc==null){
      setMessageDesc("");
      getPc().setIdCurentFere(-1);
    }else{
      setMessageDesc(cc.getContent().toStringShort());
      GCreature gAnt = cc.getContent().getGc();
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
  *@lastEditedVersion 1.46
  */
  public CCase getLookedCCase(){
    if (!actionGameOn) {return null;}
    return getPc().getLookedCCase();
  }
  /**
  *{@summary Return the chosen value for ant action.}<br>
  *This action can only be run if action game is on.<br>
  *@return Return ant choice.
  *@lastEditedVersion 1.42
  */
  public int getAntChoice(int t[]){
    if (!actionGameOn) {return -1;}
    if(t!=null){
      // getPb().removePa();
      getPb().addPa(t);
    }
    int r = getPb().getActionF();
    getPb().setActionF(-1);
    return r;
  }
  /**
  *{@summary Return the chosen CCase.}<br>
  *It is used to move ant.
  *@lastEditedVersion 2.11
  */
  public CCase getCCase(){
    if (!actionGameOn) {return null;}
    // System.out.println("getCCase");
    moveMode=true;
    while(ccaseClicked==null){
      Temps.sleep();
      // System.out.println("cpu use");
    }
    // while(ccaseClicked==null){
    //   Thread.onSpinWait();
    //   System.out.println("cpu use");
    // }
    moveMode=false;
    CCase tempCCase = ccaseClicked;
    ccaseClicked=null;
    return tempCCase;
  }
  public void setCCase(CCase cc){ccaseClicked=cc;}
  /**
  *{@summary Print a message.}<br>
  *If message.equals("") we may need to delete last message, but we don't need to print a new message.<br>
  *@param message the message to print.
  *@param doWeNeedToDoNextCmdNow true if we need to do next commande now.
  *@lastEditedVersion 1.44
  */
  public void message(String message, boolean doWeNeedToDoNextCmdNow){
    if (!actionGameOn) {return;}
    if(f==null){
      erreur.info("message() force initialisation");
      ini();
    }
    try {
      boolean needToStayMaxSize = !doWeNeedToDoNextCmdNow;
      getPj().initialiserPd(message, needToStayMaxSize);
    }catch (Exception e) {
      erreur.alerte("can't print message : "+message);
    }
    try {
      getPdi().removeBSuivant();
    }catch (Exception e) {}
    try {
      if(Main.getScript()!=null){
        Main.getScript().setCmdSuivante(doWeNeedToDoNextCmdNow);
      }
      if(!doWeNeedToDoNextCmdNow){
        getPdi().addBSuivant();
        getPj().getPsd().updateSizeMax();
      }else{
        getPs().updateSize();//écoute normale
      }
    }catch (Exception e) {//par défaut on attend avant de passer a la commande suivante.
      erreur.alerte("can't print message : "+message);
      Main.getScript().setCmdSuivante(false);
      getPdi().addBSuivant();
    }
  }
  /**
  *{@summary Print a loading message.}<br>
  *@param message the message to print.
  *@param percentageDone the percentage of loading curently done.
  *@lastEditedVersion 1.46
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
  *@lastEditedVersion 1.46
  */
  public void popUpMessage(String message){
    if (getPch()!=null) {return;}
    //Main.getPartie().getPlayingAnt() is null but window didn't clear all data.
    getPb().setVisiblePa(false);
    setMessageDesc("");
    // getPb().removePi();
    paint();
    getPj().alerte(message);
  }
  /**
  *{@summary Print a question in a new window.}<br>
  *@param message the message to print.
  *@return the answer.
  *@lastEditedVersion 1.50
  */
  public String popUpQuestion(String message){
    if (getPch()!=null) {return "";}
    getPb().setVisiblePa(false);
    setMessageDesc("");
    // getPb().removePi();
    paint();
    String s = getPj().question(message);
    getPb().setVisiblePa(true);
    return s;
  }

  /**
  *{@summary remove FPanelChargement &#38; listen mouse clic on the map.}<br>
  *@lastEditedVersion 1.44
  */
  public void closeFPanelChargement(){
    if (!actionGameOn) {return;}
    getPj().removePch();
    getPs().build();
  }
  /**
  *{@summary set playing ant.}<br>
  *This action can only be run if action game is on.<br>
  *@lastEditedVersion 1.54
  */
  public void setPlayingAnt(Fourmi f){
    if (!actionGameOn) {return;}
    if(f!=null){
      if(!f.getIa()){
        getPb().addPI();
        getPb().addPIJ();
        // getPb().setVisiblePa(true);
        // updateIcon();
      }
    }else{
      getPb().removePi();
      getPb().setVisiblePa(false);
      Main.getPartie().setAntIdToPlay(-1);
    }
    // if (!f.getFere().getJoueur().getIa()) {
    //   getPb().setVisiblePa(false);
    // }
    // Main.getPartie().setAntIdToPlay(-1);
  }
  /**
  *{@summary Move ObjetSurCarteAId.}<br>
  *This action can only be run if action game is on.<br>
  *@param o object to move.
  *@param from CCase that o leave.
  *@param to CCase were o is going.
  *@lastEditedVersion 2.1
  */
  public void move(ObjetSurCarteAId o, CCase from, CCase to){
    if(!Main.getOp().getInstantaneousMovement()){
      ThMove.updateTo(to, o.getId());
      ThMove th = new ThMove(o, from, to);
      // th.start();
    }
  }
  /**
  *{@summary Wait for end turn if we need.}
  *@lastEditedVersion 2.5
  */
  public void waitForEndTurn() {
    getPmmc().setAllActionDone(true);
    Main.getPartie().setAntIdToPlay(-1);

    if(doNotNeedToEndTurnAuto()) { // || any ant have action to do.
      while(!Main.getPlayingJoueur().getIsTurnEnded() && !Main.getRetournerAuMenu()) {
        Temps.pause(50);
        //here were waiting for the final clic on the red button.
        if(Main.getPartie().getAntIdToPlay()!=-1){
          // erreur.info("action for ant "+Main.getPartie().getAntIdToPlay());
          // Main.setPlayingAnt(Main.getPlayingJoueur().getFere().getGc().getFourmiParId(Main.getPartie().getAntIdToPlay()));
          ((TourFourmiNonIa) Main.getPlayingJoueur().getFere().getGc().getFourmiParId(Main.getPartie().getAntIdToPlay()).tour).allowToDisableAutoMode();
        }
      }
    }
    Main.getPartie().setAntIdToPlay(-1);
    Main.getPlayingJoueur().setIsTurnEnded(false);
    // fere.getJoueur().setIsTurnEnded(true);
    getPmmc().setAllActionDone(false);
    // erreur.info("stop waiting for end turn");
  }
  /**
  *{@summary A loop to wait for game launch.}<br>
  *@lastEditedVersion 1.46
  */
  public void waitForGameLaunch(){
    // if(!Main.getPremierePartie()){
    while(!launchFromPm){
      // try {
      //   wait();
      // }catch (Exception e) {}
      Temps.sleep();
      // Thread.onSpinWait(); //don't stop the thread, probably because it's the main tread
      // System.out.println("CPU USE");
    }
    launchFromPm=false;
    actionGame();
  }
  /**
  *{@summary Initialize the game launcher.}
  *@lastEditedVersion 2.7
  */
  public void iniLauncher(){
    if(fl==null){
      fl = new FFrameLauncher();
    }
  }
  /**
  *{@summary Close the game launcher.}
  *@lastEditedVersion 2.7
  */
  public void closeLauncher(){
    fl.setVisible(false);
    fl.dispose();
    fl=null;
  }
  /**
  *{@summary Update downloading message.}
  *@param message the message
  *@lastEditedVersion 2.7
  */
  public void setDownloadingMessage(String message){
    fl.setDownloadingMessage(message);
  }
  /**
  *{@summary Update downloading %age.}
  *@param state the state as a %age
  *@lastEditedVersion 2.7
  */
  public void setDownloadingValue(int state){
    fl.setDownloadingValue(state);
  }
  /**
  *{@summary Hide or show buttonRetry of FFrameLauncher.}
  *@lastEditedVersion 2.7
  */
  public void setButtonRetryVisible(boolean visible){
    fl.setButtonRetryVisible(visible);
  }

  //not in View interface
  /**
  *{@summary set the description message.}<br>
  *Description message can be mouse located or not.
  *Mouse located message are used when they concerne only small grpahics items.<br>
  *Null safe.<br>
  *@param message the message to print as description
  *@param mouseLocated if true message is print at mouse location, only if mouse don't move
  *@lastEditedVersion 2.7
  */
  public void setMessageDesc(String message, boolean mouseLocated){
    if(mouseLocated){
      if(getPj()!=null){getPj().updateThreadMessagesDesc(message);}
      if(getPb()!=null){getPb().setDesc("");}
    }else{
      if(getPb()!=null){getPb().setDesc(message);}
    }
  }
  /***
  *{@summary set the description message.}<br>
  *Description message can be mouse located or not.
  *Mouse located message are used when they concerne only small grpahics items.<br>
  *MouseLocated is false.
  *Null safe.<br>
  *@param message the message to print as description
  *@lastEditedVersion 2.7
  */
  public void setMessageDesc(String message){setMessageDesc(message, false);}
  /***
  *{@summary True if in moveMode.}
  *@lastEditedVersion 2.11
  */
  public boolean getMoveMode(){return moveMode;}
  public void setMoveMode(boolean b){moveMode=b;}
  /**
  *{@summary Center map panel over a Case.}
  *@param c case to center over
  *@lastEditedVersion 2.14
  */
  public void centerOverCase(Case c){
    getPmmo().centerOver((int)((c.getX()+0.5)*getPc().getTailleDUneCase()), (int)((c.getY()+0.5)*getPc().getTailleDUneCase()));
  }

  /**
  *{@summary True if grass blades are enable.}
  *@lastEditedVersion 2.16
  */
  public boolean isBladesEnable(){
    setBladeChanged(true);
    return true;
  }
  /**
  *{@summary Make user choose in a String array.}
  *@param array the array where to choose
  *@lastEditedVersion 2.17
  */
  @Override
  public String makeUserChooseOnArray(String array[], String varName){
    FOptionPane opane = new FOptionPane(getF());
    if(varName!=null){
      opane.addText(str.toMaj(varName)+" : ");
    }
    opane.addComboBox(array);
    opane.build();
    return opane.getContent();
  }
  /**
  *{@summary Make user choose an int in [min, max].}
  *@param min the min value
  *@param max the max value
  *@param varName the name of the variable tp choose
  *@lastEditedVersion 2.17
  */
  @Override
  public int makeUserChooseInt(int min, int max, String varName){
    FOptionPane opane = new FOptionPane(getF());
    if(varName!=null){
      opane.addText(varName+" : ");
    }
    opane.addSliderAndIntField(min, max, max/2);
    opane.build();
    return str.sToI(opane.getContent());
  }

  public Data getData(){return Main.getData();}

  //private---------------------------------------------------------------------
  /**
  *Load graphics during menu time.
  *@lastEditedVersion 2.6
  */
  private void loadGraphics(){
    if(Main.getPremierePartie() || !Main.getOpenMenuFirst()){ini.initializeFPanelJeuAndSubpanel();}
    else{
      Th thTemp = new Th(1);
      thTemp.start();
      Th thTemp2 = new Th(2);
      thTemp2.start();
    }
  }
  /**
  *{@summary Initialize cheat code listener if it haven't been yet.}<br>
  *@lastEditedVersion 1.42
  */
  private void iniThTriche(){
    try {
      if(trich == null || !trich.isAlive()){
        triche.ini();
        trich = new ThTriche();//écoute des codes triche.
        trich.start();
      }
    }catch (Exception e) {
      erreur.erreur("Unable to launch cheat code listening");
    }
  }
  /**
  *{@summary Initialize the discord integration.}<br>
  *@lastEditedVersion 2.10
  */
  private void iniDiscordIntergation(){
    try {
      new Thread(() -> {
        DiscordIntegration.discordRPC();
      }).start();
    }catch (Exception e) {
      erreur.erreur("Unable to launch Discord rich presence");
      e.printStackTrace();
    }
  }
  /**
  *{@summary Launch refrech of main Frame.}<br>
  *It have been add to solve all GUI issues of Java Swing.<br>
  *It use 1 timer and a simple refrech task repeat fps times per second.<br>
  *@lastEditedVersion 1.47
  */
  private void launchFrameRefresh(){
    timer = new Timer();
    // int k=0;
    int secToRefresh = 1000/Main.getOp().getFps();
    timer.schedule(new TimerTaskViewGUI2d(this){
      @Override
      /**
      *{@summary Repaint if Frame is not null & showing in screen.}
      */
      public void run(){
        if(getF()!=null && getF().isFocused()){ // isShowing() can also be used, but it can't see it window is fully hide by other 1.
          if(!paintGUI()){ //try to paint
            erreur.alerte("can't paint");
          }
        }else{
          getPp().updateTimeFromLastRefresh();
        }
      }
    }, 0, secToRefresh);
    if(debug.getPerformance()){
      timer.schedule(new TimerTaskViewGUI2d(this){
        @Override
        /**
        *{@summary Print curent fps.}
        */
        public void run(){
          Window activeWindow = javax.swing.FocusManager.getCurrentManager().getActiveWindow();
          // System.out.println(getF().equals(activeWindow));
          // erreur.info("max fps : "+Main.getOp().getFps()+" curent fps : "+(view.getCurentFPS()));
          // erreur.info("max fps : "+Main.getOp().getFps()+" curent fps : "+(view.getCurentFPS()/10));
          view.setCurentFPS(0);
        }
      }, 0, 1000);
      // }, 0, 10000);
    }
  }
  /**
  *{@summary Tool to print mains FPanelx infos.}<br>
  *@lastEditedVersion 1.47
  */
  private void printPanelInfo(){
    erreur.info("pp : "+getPp());
    erreur.info("pm : "+getPm());
    erreur.info("pj : "+getPj());
    if(getPb()!=null && getPb().isVisible()){
      erreur.info("pb : "+getPb());
    }
  }
  /**
  *{@summary ini font at default value for all graphics components.}<br>
  *@lastEditedVersion 2.2
  */
  private void iniFont(){
    // UIManager.getLookAndFeelDefaults().put("defaultFont", Main.getFont1());
    // public static void setUIFont (javax.swing.plaf.FontUIResource f){
    Font f = Main.getOp().getFont1();
    java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
    while (keys.hasMoreElements()) {
      Object key = keys.nextElement();
      Object value = UIManager.get(key);
      if (value instanceof javax.swing.plaf.FontUIResource){
        UIManager.put(key, f);
      }
    }
    // UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("Arial", Font.BOLD, 14));
    // setForeground(Color.BLACK);
  }
  /**
  *{@summary return endTurnAuto depending of Options &#38; endTurn button.}<br>
  *@lastEditedVersion 2.5
  */
  private boolean doNotNeedToEndTurnAuto(){
    boolean everyoneInAutoMode=false; //au moins 1 fourmi sans auto mode.
    try {
      if(Main.getPlayingJoueur().getFere().getGc().isAllInAutoMode()){everyoneInAutoMode=true;}
    }catch (Exception e) {
      erreur.alerte("Fail to launch auto end turn.");
    }
    return ((getPmmc().getFbetEnabled() && !Main.getOp().getEndTurnAuto()) || everyoneInAutoMode) && !Main.getPlayingJoueur().getIa();
  }
}

/**
*{@summary A simple TimerTask extends class with a ViewGUI2d.}<br>
*run methode is Override depending of the action that we need to do.
*@lastEditedVersion 1.47
*@author Hydrolien
*/
class TimerTaskViewGUI2d extends TimerTask {
  protected static ViewGUI2d view;
  /**
  *{@summary Main constructor.}
  *@lastEditedVersion 2.5
  */
  public TimerTaskViewGUI2d(ViewGUI2d view){
    TimerTaskViewGUI2d.view=view;
  }
  /** Simple run methode that do nothing.*/
  @Override
  public void run(){}
}
