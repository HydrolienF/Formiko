package fr.formiko.views;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Joueur;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.formiko.triche;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.color;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.List;
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
    if(actionGameOn){
      System.out.println(sep);
      printMap();
      System.out.println(sep);
      printFereInColor();
      System.out.println(sep);
      printArray();
    }else{
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
    menuName="menuP";
    paint();
    int action = getActionMenu(4);
    switch (action) {
      case 1 :
      menuNewGame();
      break;
      case 2 :
      if(!menuLoadAGame()){ //it can return false if there is no game to load.
        menuMain();
      }
      break;
      case 3 :
      if(!menuOptions()){ //it can return false
        menuMain();
      }
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
  *@version 1.33
  */
  public boolean menuNewGame(){
    actionGameOn=false;
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
      Main.setPartie(Partie.getPartieTuto());
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
    menuName="";
    tToPrint=sauvegarderUnePartie.listSave();
    if(tToPrint.length==0){return menuMain();}
    //add a line "backToMainMenu" to tToPrint.
    tToPrint = tableau.ajouterX(tToPrint,g.get("bouton.nom.-13"));
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
  *@version 1.33
  */
  public boolean menuPersonaliseAGame(){
    actionGameOn=false;
    menuName="";
    tToPrint=new String [10]; int k=0;
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
    int choice = -1;
    Partie pa = Partie.getDefautlPartie();
    while (choice!=10) {
      paint();
      choice = getActionMenu(tToPrint.length);
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
        erreur.erreurPasEncoreImplemente("ViewCLI");
        //pa.setNbrDeJoueur(str.sToI(input,pa.getNbrDeJoueur()));
        // break;
        case 8:
        //pa.setNbrDIa(str.sToI(input,pa.setNbrDIa()));
        // break;
        case 9:
        //pa.setNbrDeFourmi(str.sToI(input,pa.setNbrDeFourmi()));
        break;
      }
    }
    Main.setPartie(pa);
    actionGame();
    return true;
  }
  /**
  *{@summary Load the options menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  public boolean menuOptions(){
    actionGameOn=false;
    menuName="menuO";
    //tToPrint=sauvegarderUnePartie.listOptions();
    tToPrint = new String[0]; //TODO replace by a real choice.
    if(tToPrint.length==0){return false;}
    paint();
    int choice = getActionMenu(tToPrint.length);
    menuMain();
    return true;
  }
  /**
  *{@summary Launch action game part.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  public boolean actionGame(){
    actionGameOn=true;
    menuName="";
    Main.getPartie().setPlayingAnt(null);
    Main.getPartie().initialisationElément();
    int toDoAfter = 0;
    String tab [] = new String[4];
    tab[0]=g.get("doAntAction");
    tab[1]=g.get("selectAnt");
    tab[2]=g.get("endTurn");
    tab[3]=g.get("pauseActionGame");
    tToPrint = tab;
    Main.getPartie().launchGame();
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
        System.out.println("saveDone"); //TODO to translate
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
  *{@summary Change the value of the loked Case.}<br>
  *We need to repaint the information about this Case.<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.39
  */
  public boolean setLookedCase(CCase cc){
    if (!actionGameOn || cLIMap==null) {return false;}
    if (cc == null) {cLIMap.setLookedCase(null);}
    cLIMap.setLookedCase(cc.getContenu());
    return false;
  }
  /**
  *{@summary get a CCase from the payer.}<br>
  *It read Strings like this: "b13", "2c"<br>
  *This action can only be run if action game is on.<br>
  *@return Selected CCase or null if it fail.
  *@version 1.39
  */
  public CCase getCCase(){
    CCase cc = null;
    String s = scannerAnswer.nextLine();
    //TODO à finir
    //split number part & char part.
    //x = number
    //y = str.sToInt(char).
    //cc = Main.getGc().getCCase(x,y);
    return cc;
  }
  /**
  *{@summary Return the chosen value for ant action.}<br>
  *This action can only be run if action game is on.<br>
  *@return Return ant choice.
  *@version 1.33
  */
  public int getAntChoice(int t[]){
    if (!actionGameOn) {return -1;}
    String ts [] = new String[16];
    for (int i=0;i<12 ;i++ ) {
      ts[i]=g.get("bouton.desc."+(20+i));
      if(!tableau.estDansT(t,i)){
        ts[i]+=" ("+g.get("unaviable")+")";
      }
    }
    ts[12]=g.getM("setPlayingAnt");
    ts[13]=g.getM("endTurn");
    ts[14]=g.getM("endGame");
    ts[15]=g.getM("pauseGame"); //TODO to translate
    tToPrint = ts;
    int choice = -1;
    do {
      paint();
      choice = getActionMenu(tToPrint.length)-1;
      if(choice==15){pauseActionGame();tToPrint=ts;}
    } while ((choice <12 || choice==15) && !tableau.estDansT(t,choice));
    if(choice==12){Main.getPartie().setPlayingAnt(getAntFromFere());}
    return choice;
  }
  /**
  *{@summary Select an ant from playingAnt anthill.}<br>
  *@version 1.33
  */
  private Fourmi getAntFromFere(){
    int len = Main.getPartie().getPlayingAnt().getFere().getGc().length();
    String t [] = new String[len];
    List<Creature> list = Main.getPartie().getPlayingAnt().getFere().getGc().toList();
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
    erreur.erreurType("Fourmi","ViewCLI.getAntFromFere");
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
    cLIMap = new CLIMap(Main.getPartie().getGc());
    System.out.println(cLIMap);
  }
  /**
  *{@summary Print anthill of the playing ant.}<br>
  *@version 1.33
  */
  private void printFereInColor(){
    if(Main.getPartie().getPlayingAnt()==null){return;}
    System.out.println(g.getM("fourmilière")+" : ");
    List<Creature> lgc = Main.getPartie().getPlayingAnt().getFere().getGc().toList();
    for (Creature c : lgc ) {
      if (c.equals(Main.getPartie().getPlayingAnt())){
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
    System.out.println(g.getM("chooseSaveName")+"."); //TODO translate
    System.out.println(t[1]=g.getM("defaultName")+" : "+saveName);//TODO translate
    String input = scannerAnswer.nextLine();
    if(!input.equals("")){
      saveName = str.sToFileName(input);
    }
    return saveName;
  }
}
