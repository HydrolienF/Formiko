package fr.formiko.views;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.GJoueur;
import fr.formiko.formiko.Joueur;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.ObjetSurCarteAId;
import fr.formiko.formiko.Partie;
import fr.formiko.formiko.triche;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.color;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.Liste;
import fr.formiko.usuel.sauvegarderUnePartie;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;
import fr.formiko.views.cli.*;

import java.util.LinkedList;
import java.util.Scanner;

/**
 *{@summary View Console Line Interface.}<br>
 *@author Hydrolien
 *@version 1.33
 */
public class ViewCLI implements View {
  private Scanner scannerAnswer;
  private String menuName;
  private String tToPrint[];
  private CLIMap cLIMap;
  private static String sep = "--------------------------------------------------------------------------------";

  private boolean actionGameOn;
  public boolean getActionGameOn(){return actionGameOn;}
  /**
  *{@summary Initialize all the thing that need to be Initialize before using view.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  public boolean ini(){
    actionGameOn=false;
    menuName="";
    tToPrint=null;
    try {
      scannerAnswer = new Scanner(System.in);
    }catch (Exception e) {
      return false;
    }
    triche.ini();
    return true;
  }
  /**
  *{@summary close all the thing that need to be close after using view.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  public boolean close(){
    actionGameOn=false;
    try {
      scannerAnswer.close();
    }catch (Exception e) {
      return false;
    }
    return true;
  }
  /**
  *{@summary Refrech actual view.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  public boolean paint(){
    if(scannerAnswer==null){return false;}
    if(actionGameOn){
      System.out.println(sep);
      printMap();
      System.out.println(sep);
      printFereInColor();
      System.out.println(sep);
      printArray();
    }else{
      System.out.println(sep);
      if(menuName.equals("")){
        printArray();
      }else{
        printActionMenu();
      }
    }
    return true;
  }
  /**
  *{@summary Load main menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  public boolean menuMain(){
    actionGameOn=false;
    if(scannerAnswer==null){ini();}
    Main.stopScript();
    menuName="menuP";
    // if(Main.getPremierePartie()){Main.setPartie(Partie.getPartieTuto());return true;}
    paint();
    int action = getActionMenu(4);
    switch (action) {
      case 1 :
      menuNewGame();
      break;
      case 2 :
      menuLoadAGame();
      break;
      case 3 :
      menuOptions(); //it can return false
      menuMain();
      break;
      case 4 :
      Main.quitter();
      break;
    }
    return true;
  }
  /**
  *{@summary Load new game menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.34
  */
  public boolean menuNewGame(){
    actionGameOn=false;
    if(scannerAnswer==null){ini();}
    menuName="menuN";
    paint();
    int action = getActionMenu(4);
    switch (action) {
      case 1 :
      Main.setPartie(Partie.getDefautlPartie());
      actionGame();
      break;
      case 2 :
      menuPersonaliseAGame();
      break;
      case 3 :
      Partie.setPartieTutoInMain();
      actionGame();
      break;
      case 4 :
      menuMain();
      break;
    }

    return true;
  }
  /**
  *{@summary Load the game load menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  public boolean menuLoadAGame(){
    actionGameOn=false;
    if(scannerAnswer==null){ini();}
    menuName="";
    tToPrint=sauvegarderUnePartie.listSave();
    if(tToPrint.length==0){return menuMain();}
    //add a line "backToMainMenu" to tToPrint.
    tToPrint = tableau.addX(tToPrint,g.get("bouton.nom.-13"));
    paint();
    int choice = getActionMenu(tToPrint.length);
    if(choice==tToPrint.length){
      menuMain();
    }else{
      Partie pa = Partie.getPartieSave(tToPrint[choice-1]);
      Main.setPartie(pa);
      actionGame();
    }
    return true;
  }
  /**
  *{@summary personalise a game menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.35
  */
  public boolean menuPersonaliseAGame(){
    actionGameOn=false;
    if(scannerAnswer==null){ini();}
    menuName="";
    tToPrint=new String [11]; int k=0;
    tToPrint[k]=g.get("choixCarte");k++;
    tToPrint[k]=g.get("choixDif");k++;
    tToPrint[k]=g.get("choixVitesseDeJeu");k++;
    tToPrint[k]=g.get("nbrDeTour");k++;
    tToPrint[k]=g.get("casesSombres");k++;
    tToPrint[k]=g.get("casesNuageuses");k++;
    tToPrint[k]=g.get("nbrDeJoueur");k++;
    tToPrint[k]=g.get("nbrDIa");k++;
    tToPrint[k]=g.get("nbrDeFourmi");k++;
    tToPrint[k]=g.get("lancerPartie");k++;
    tToPrint[k]=g.get("retour");k++;
    int choice = -1;
    Partie pa = Partie.getDefautlPartie();
    while (choice!=10) {
      paint();
      choice = getActionMenu(tToPrint.length);
      if(choice==11){
        menuNewGame();
        paint();
        return true;
      }else if(choice==10){
        Main.setPartie(pa);
        actionGame();
        return true;
      }
      String input = scannerAnswer.nextLine();
      switch (choice) {
        case 1:
        pa.getCarte().setMap(input);
        break;
        case 2:
        pa.setDifficulté(str.sToBy(input,pa.getDifficulté()));
        break;
        case 3:
        pa.setVitesseDeJeu(str.sToD(input,pa.getVitesseDeJeu()));
        break;
        case 4:
        pa.setNbrDeTour(str.sToI(input,pa.getNbrDeTour()));
        break;
        case 5:
        pa.getCarte().setCasesSombres(str.sToB(input));
        break;
        case 6:
        pa.getCarte().setCasesNuageuses(str.sToB(input));
        break;
        //next setter do not exist yet & need to call back initialisationElément() to be concidered.
        case 7:
        erreur.erreurPasEncoreImplemente();
        //pa.setNbrDeJoueur(str.sToI(input,pa.getNbrDeJoueur()));
        break;
        case 8:
        //pa.setNbrDIa(str.sToI(input,pa.setNbrDIa()));
        break;
        case 9:
        //pa.setNbrDeFourmi(str.sToI(input,pa.setNbrDeFourmi()));
        break;
      }
    }
    return false;
  }
  /**
  *{@summary Load the options menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  public boolean menuOptions(){
    actionGameOn=false;
    if(scannerAnswer==null){ini();}
    erreur.info(g.getM("optionsCanBeEditedIn")+" data/Options.md.");
    // menuName="menuO";
    // //tToPrint=sauvegarderUnePartie.listOptions();
    // tToPrint = new String[0]; //to replace by a real choice.
    // if(tToPrint.length==0){return false;}
    // paint();
    // int choice = getActionMenu(tToPrint.length);
    // menuMain();
    return true;
  }
  /**
  *{@summary Launch action game part.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  public boolean actionGame(){
    actionGameOn=true;
    if(scannerAnswer==null){ini();}
    menuName="";
    Main.setPlayingAnt(null);
    Main.getPartie().initialisationElément();
    int toDoAfter = 0;
    String tab [] = new String[4];
    tab[0]=g.get("doAntAction");
    tab[1]=g.get("selectAnt");
    tab[2]=g.get("endTurn");
    tab[3]=g.get("pauseActionGame");
    tToPrint = tab;
    String s = g.get("chargementFini");
    if (debug.getAffLesPerformances()==true){s=s +" "+ "("+Temps.msToS(Main.getLonTotal())+")";}
    Main.setMessageChargement(s);
    Main.getPartie().launchGame(); //MAIN GAME PART
    //after a game :
    Main.setPartie(null);
    switch (toDoAfter) {
      case 3:
      menuLoadAGame();
      break;
      case 4:
      menuMain();
      break;
      case 5:
      Main.quitter();
    }
    return false;
  }


  //Only on action game mode :
  /**
  *{@summary Stop game and print the escape menu.}<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  public int pauseActionGame(){
    if (!actionGameOn) {return -1;}
    tToPrint = new String[6];
    int k=0;
    for (int i=-10;i>-16 ;i--) {
      tToPrint[k]=g.get("bouton.nom."+i);
      k++;
    }
    int choice = -1;
    while (choice!=6) {
      paint();
      choice=getActionMenu(tToPrint.length);
      switch (choice) {
        case 1 :
        sauvegarderUnePartie.sauvegarder(Main.getPartie(),getSaveName()+".save");
        System.out.println("saveDone");
        return 1;
        case 2 :
        menuOptions();
        return 2;
        case 3 :
        menuLoadAGame();
        return 3;
        case 4 :
        menuMain();
        return 4;
        case 5 :
        Main.quitter();
        return 5;
        default :
        System.out.println("return O");
        return 0;
      }
    }
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
  public boolean endActionGame(boolean withButton, int nextLevel, String message, GJoueur gj, boolean canResumeGame){
    erreur.info("message");
    System.out.println(gj);
    //TODO to update.
    menuMain();
    return true;
  }

  /**
  *{@summary Change the value of the loked Case.}<br>
  *We need to repaint the information about this Case.<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.39
  */
  public boolean setLookedCCase(CCase cc){
    if (!actionGameOn || cLIMap==null) {return false;}
    cLIMap.setLookedCCase(cc);
    return true;
  }
  /**
  *{@summary Return the value of the looked CCase.}<br>
  *This action can only be run if action game is on and cLIMap have been created.<br>
  *@return lookedCCase.
  *@version 1.46
  */
  public CCase getLookedCCase(){
    if (!actionGameOn || cLIMap==null) {return null;}
    return cLIMap.getLookedCCase();
  }
  /**
  *{@summary get a CCase from the payer.}<br>
  *It read Strings like this: "b13", "2c"<br>
  *This action can only be run if action game is on.<br>
  *@return Selected CCase or null if it fail.
  *@version 1.39
  */
  public CCase getCCase(){
    String s = popUpQuestion(g.getM("were")+" ? ("+g.get("enterCoordinateAs")+" G12)");
    return getCCaseFromString(s);
  }
  /**
  *{@summary Return the chosen value for ant action.}<br>
  *This action can only be run if action game is on.<br>
  *@return Return ant choice.
  *@version 1.33
  */
  public int getAntChoice(int t[]){
    if (!actionGameOn) {return -1;}
    if (t==null) {return -1;}
    String ts [] = new String[17];
    for (int i=0;i<12 ;i++ ) {
      ts[i]="";
      if(!tableau.estDansT(t,i) && Main.getOs().isLinux()){
        ts[i]+=color.NEUTRAL_CROSS_OUT;
      }
      ts[i]+=g.get("bouton.desc."+(20+i));
      if(!tableau.estDansT(t,i)){
        if (Main.getOs().isLinux()) {
          ts[i]+=color.NEUTRAL;
        }
        ts[i]+=" ("+g.get("unaviable")+")";
      }
    }
    ts[12]=g.getM("setPlayingAnt");
    ts[13]=g.getM("endTurn");
    ts[14]=g.getM("endGame");
    ts[15]=g.getM("pauseActionGame");
    ts[16]=g.getM("set")+" "+g.get("la")+" "+g.get("lookedCase");
    tToPrint = ts;
    int choice = -1;
    do {
      paint();
      choice = getActionMenu(tToPrint.length)-1;
      if(choice==15){pauseActionGame();tToPrint=ts;}
      if(choice==16){setLookedCCase(getCCase());tToPrint=ts;}
    } while ((choice <12 || choice>14) && !tableau.estDansT(t,choice));
    if(choice==12){Main.setPlayingAnt(getAntFromFere());}
    Main.getPlayingAnt().setBActionHaveChange(true);
    return choice;
  }
  /**
  *{@summary Print a message.}<br>
  *If message.equals("") we may need to delete last message, but we don't need to print a new message.<br>
  *@param message the message to print
  *@param doWeNeedToDoNextCmdNow true if we need to do next commande now.
  *@version 1.44
  */
  public void message(String message, boolean doWeNeedToDoNextCmdNow){
    //TODO print it at a special location when printing all game info.
    System.out.println(message);
  }
  /**
  *{@summary Print a loading message.}<br>
  *@param message the message to print.
  *@param percentageDone the percentage of loading curently done.
  *@version 1.46
  */
  public void loadingMessage(String message, int percentageDone){
    erreur.info(message+" "+percentageDone+"%");
  }
  /**
  *{@summary Print a message in a new window.}<br>
  *@param message the message to print.
  *@version 1.46
  */
  public void popUpMessage(String message){
    System.out.println(sep);
    message(message, false);
  }
  /**
  *{@summary Print a question in a new window.}<br>
  *@param message the message to print.
  *@return the answer.
  *@version 1.50
  */
  public String popUpQuestion(String message){
    System.out.println(sep);
    message(message,false);
    String s = scannerAnswer.nextLine();
    return s;
  }
  /**
  *{@summary set playing ant.}<br>
  *This action can only be run if action game is on.<br>
  *@version 1.46
  */
  public void setPlayingAnt(Fourmi f){
    if (!actionGameOn) {return;}
    //nothing more to do
  }
  /***
  *{@summary move ObjetSurCarteAId.}<br>
  *This action can only be run if action game is on.<br>
  *This action do noting on view cli.
  *@param o object to move.
  *@param from CCase that o leave.
  *@param to CCase were o is going.
  *@version 2.1
  */
  public void move(ObjetSurCarteAId o, CCase from, CCase to){}

  //private---------------------------------------------------------------------
  /**
  *{@summary Select an ant from playingAnt anthill.}<br>
  *@version 1.33
  */
  private Fourmi getAntFromFere(){
    int len = Main.getPlayingAnt().getFere().getGc().length();
    String t [] = new String[len];
    Liste<Creature> list = Main.getPlayingAnt().getFere().getGc();
    int k=0;
    for (Creature c : list ) {
      t[k]=getAllyAntInColor(c); k++;
    }
    tToPrint=t;
    printArray();
    int choice = getActionMenu(len);
    Creature c = list.getItem(choice-1);
    if(c instanceof Fourmi){
      return (Fourmi) c;
    }
    erreur.erreurType("Fourmi");
    return null;
  }

  //private functions
  /**
  *{@summary Print all action aviable in a menu.}<br>
  *@version 1.33
  */
  private void printActionMenu(){
    LinkedList<String> list = new LinkedList<String>();
    int i=1;
    while(g.exist(menuName+"."+i)){
      list.add(g.get(menuName+"."+i));
      i++;
    }
    i=1;
    for (String s : list ) {
      System.out.println(i+" : "+s);
      i++;
    }
  }
  /**
  *{@summary Print tToPrint.}<br>
  *@version 1.33
  */
  private void printArray(){
    if(tToPrint==null){return;}
    int i=1;
    for (String s : tToPrint ) {
      System.out.println(i+" : "+s);
      i++;
    }
  }
  /**
  *{@summary Print map of the actual Partie.}<br>
  *@version 1.33
  */
  private void printMap(){
    if(cLIMap==null){
      cLIMap = new CLIMap(Main.getPartie().getGc());
    }
    System.out.println(cLIMap);
  }
  /**
  *{@summary Print anthill of the playing ant.}<br>
  *@version 1.33
  */
  private void printFereInColor(){
    if(Main.getPlayingAnt()==null){return;}
    System.out.println(g.getM("fourmilière")+" : ");
    Liste<Creature> lgc = Main.getPlayingAnt().getFere().getGc();
    for (Creature c : lgc ) {
      if (c.equals(Main.getPlayingAnt())){
        System.out.print("-- ! -- ");
      }
      System.out.println(getAllyAntInColor(c));
    }
  }
  /**
  *{@summary Return the color depending of the status of c for playingAnt.}<br>
  *param c The Creature to inspect to know if it is ally, enemy or neutral (or equals to playingAnt).<br>
  *@version 1.38
  */
  private String getAllyAntInColor(Creature c){
    String r = "";
    if(Main.getOs().isLinux()){
      if (c.getStade()==0) {
        if(c.getAction()==c.getActionMax()){
          r+=color.GREEN;
        }else if (c.getAction()<=0) {
          r+=color.RED;
        }else{
          r+=color.YELLOW;
        }
      }
    }
    r+=c;
    if(Main.getOs().isLinux()){
      r+=color.NEUTRAL;
    }
    return r;
  }
  /**
  *{@summary Return an aviable action in a menu.}<br>
  *stop stop the game.
  *cheat alowed to write 1 cheat code.
  *@version 1.33
  */
  private int getActionMenu(int maxValue){
    int returnValue = -1;
    while(returnValue < 1 || returnValue > maxValue){
      System.out.println(g.get("choix")+" : ");
      String input = scannerAnswer.nextLine();
      try {
        returnValue = (int) str.sToLThrows(input);
      }catch (Exception e) {
        if(input.equals("stop")){
          Main.quitter();
        }else if(input.equals("cheat")){
          input = scannerAnswer.nextLine();
          triche.commande(input);
        }
      }
    }
    return returnValue;
  }
  /**
  *{@summary Ask a save name to the user.}<br>
  *If nothing is choose, save will have defaultName.<br>
  *Save name will be file save for every os.<br>
  *@version 1.39
  */
  private String getSaveName(){
    String saveName = g.getM("sauvegarde")+" "+sauvegarderUnePartie.getSave().getIdS();
    saveName+="  "+Temps.getDatePourSauvegarde();
    saveName = str.sToFileName(saveName);//le pseudo pourrai contenir des char interdits sur des fichiers.
    String t [] = new String[2];
    System.out.println(sep);
    System.out.println(g.getM("set")+" "+g.get("le")+" "+g.get("saveName")+".");
    System.out.println(t[1]=g.getM("defaultName")+" : "+saveName);
    String input = scannerAnswer.nextLine();
    if(!input.equals("")){
      saveName = str.sToFileName(input);
    }
    return saveName;
  }
  /**
  *{@summary transforme a String to a CCase and return it.}<br>
  *This action can only be run if action game is on.<br>
  *@param s String like this: "b13", "2c"<br>
  *@return Selected CCase or null if it fail.
  *@version 1.39
  */
  //public only for test
  public CCase getCCaseFromString(String s){
    s = s.toLowerCase();
    String numbers="";
    String letters="";
    int len = s.length();
    for (int i=0; i<len;i++) {
      char c = s.charAt(i);
      if(c>47 && c<58){
        numbers+=c;
      }else if(c>96 && c<123){
        letters+=c;
      }
    }
    if(numbers.equals("") || letters.equals("")){
      erreur.alerte(g.get("caseUnkwnowed"));
      return null;
    }
    int y = 0;
    if(letters.length()>2){
      erreur.erreurPasEncoreImplemente();
      return null;
    }else if (letters.length()==2) {
      y = (letters.charAt(0)-96)*26 + letters.charAt(1)-97;
    }else{
      y = letters.charAt(0)-97;
    }
    int x = str.sToI(numbers);
    CCase cc = Main.getGc().getCCase(x,y);
    if(cc==null){
      erreur.alerte(g.get("theCaseDoNotExist"));
    }
    return cc;
  }
}
