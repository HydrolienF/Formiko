package fr.formiko.views.gui2d;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Creature;
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

import java.util.LinkedList;
import java.util.Scanner;

/**
 *{@summary View Graphics User Interface in 2 dimention.}<br>
 *@author Hydrolien
 *@version 1.44
 */
public class ViewGUI2d implements View {
  private boolean actionGameOn;
  private ThTriche trich; //écoute de commande triche dans le terminal.
  /***
  *Main windows
  *@version 1.1
  */
  private Fenetre f;
  // GET SET -------------------------------------------------------------------
  public boolean getActionGameOn(){return actionGameOn;}
  //Graphics components.
  public Fenetre getF(){return f;}
  public PanneauPrincipal getPp(){return Main.getPp();}
  public PanneauMenu getPm(){return Main.getPm();}
  public PanneauJeu getPj(){return Main.getPj();}
  public PanneauBouton getPb(){return Main.getPb();}
  public PanneauChargement getPch(){return Main.getPch();}
  public PanneauSup getPs(){return Main.getPs();}
  /**  // FUNCTIONS -----------------------------------------------------------------
  *{@summary Initialize all the thing that need to be Initialize before using view.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.42
  */
  public boolean ini(){
    actionGameOn=false;
    Main.startCh();
    f = new Fenetre();
    iniThTriche();
    Main.getData().setImageIniForNewGame(false);//force reload of ant images.
    Main.endCh("iniView");Main.startCh();
    ini.initialiserToutLesPaneauxVide();
    Main.endCh("chargementPanneauVide");
    loadGraphics();
    //menu
    // Main.startCh();
    // getPm().construitPanneauMenu(3);
    // Main.endCh("chargementPanneauMenu");
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
    getF().repaint();
    return true;
  }
  /**
  *{@summary Load main menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.44
  */
  public boolean menuMain(){
    actionGameOn=false;
    getPm().removeP();
    getPm().construitPanneauMenu(3);
    paint();
    if(!Main.getPremierePartie()){
      boolean b=false;
      while(!b){Temps.pause(10);b=getPm().getLancer();}
    }else{
      //play launching video
    }
    Main.setPartie(action.getPartie());
    System.out.println("end launch from gui");//@a
    actionGame();
    // while(!b){Temps.pause(1000000);}
    return true;
  }
  /**
  *{@summary Load new game menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.44
  */
  public boolean menuNewGame(){
    actionGameOn=false;
    getPm().removeP();
    getPm().construitPanneauMenu(3);
    getPm().setMenu(1);
    getPm().actualiserText();
    paint();
    return true;
  }
  /**
  *{@summary Load the save load menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.44
  */
  public boolean menuLoadAGame(){
    actionGameOn=false;
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
    actionGameOn=false;
    getPm().addPnp();
    return true;
  }
  /**
  *{@summary options menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.44
  */
  public boolean menuOptions(){
    actionGameOn=false;
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
    // if(Partie.getScript().equals("tuto")){pa=Partie.getPartieTuto();}
    if(Main.getPartie()==null){Main.setPartie(Partie.getDefautlPartie());}
    Main.startCh();
    Main.getPp().removePm();//on retire le menu
    Main.endCh("chargementPanneauChargementEtSuppressionMenu");//startCh();
    getPj().addPch();//on met le panneau de chargement au 1a plan.
    //la ligne qui suis n'as d'effet que si elle n'as pas déjà été appliqué a la partie.
    Main.getPartie().initialisationElément(); // pour l'instant ce bout de code ne marche pas ayeur qu'ici.
    Main.startCh();
    getPb().addPz();
    Main.endCh("ajoutPanneauZoom");Main.startCh();
    Main.getData().chargerImages(); //on a besoin du bon zoom pour effectuer cette action.
    if(Partie.getScript().equals("tuto")){
      Main.iniCpt();
      Partie.setPartieTutoInMain();
    }
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
  *{@summary Change the value of the loked Case.}<br>
  *We need to repaint the information about this Case.<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.42
  */
  public boolean setLookedCase(CCase cc){
    if (!actionGameOn) {return false;}
    return true;
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
    Main.getPj().initialiserPd(message);
    try {
      Main.getPdi().removeBSuivant();
    }catch (Exception e) {}
    try {
      Main.getScript().setCmdSuivante(doWeNeedToDoNextCmdNow);
      if(!doWeNeedToDoNextCmdNow){
        Main.getPdi().addBSuivant();
        Fourmi.setBActualiserTaille(true);//écoute de toute la fenetre.
      }else{
        Main.getPs().actualiserTaille();//écoute normale
      }
    }catch (Exception e) {//par défaut on attend avant de passer a la commande suivante.
      Main.getScript().setCmdSuivante(false);
      Main.getPdi().addBSuivant();
      Fourmi.setBActualiserTaille(true);//écoute de toute la fenetre.
    }
  }


  public void closePanneauChargement(){
    //remove PanneauChargement & listen mouse clic.
    getPj().removePch();
    getPs().construire();
    // Main.getPm().setLancer(true); //TODO to remove
    // Main.launchScript();
    // actionGame();
  }
  //private
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
}
