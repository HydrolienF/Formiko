package fr.formiko.views;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Joueur;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.formiko.triche;
import fr.formiko.usuel.listes.List;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.save;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;

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
  //colors
  private static String yellow = (char)27+"[1;33m";
  private static String red = (char)27+"[1;31m";
  private static String neutral = (char)27+"[0;m";
  private static String green=(char)27+"[1;32m";

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
    tToPrint=save.listSave();
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
        byte d = str.sToBy(input);
        pa.setDifficulté(d);
        break;
        case 3:
        double v = str.sToD(input);
        pa.setVitesseDeJeu(v);
        break;
        //TODO
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
    //tToPrint=save.listOptions();
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
    tab[0]=g.get("doAntAction");//TODO add to translation.
    tab[1]=g.get("selectAnt");//TODO add to translation.
    tab[2]=g.get("endTurn");//TODO add to translation.
    tab[3]=g.get("pauseActionGame");//TODO add to translation.
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
    for (int i=-10;i>-16 ;i-- ) {
      tToPrint[k]=g.get("bouton.nom."+i);
      k++;
    }
    int choice = -1;
    while (choice!=6) {
      paint();
      choice=getActionMenu(tToPrint.length);
      switch (choice) {
        case 1 :
        System.out.println("save game");//TODO
        break;
        case 2 :
        menuOptions();
        //when menu option will return it will end pauseActionGame & continue in actionGame.
        break;
        case 3 :
        case 4 :
        case 5 :
        return choice;
      }
    }
    return 0; //case 6.
  }

  /**
  *{@summary Change the value of the loked Case.}<br>
  *We need to repaint the information about this Case.<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  public boolean setLookedCase(CCase cc){
    if (!actionGameOn) {return false;}
    //TODO
    return false;
  }
  /**
  *{@summary Return the chosen value for ant action.}<br>
  *This action can only be run if action game is on.<br>
  *@return Return ant choice.
  *@version 1.33
  */
  public int getAntChoice(int t[]){
    if (!actionGameOn) {return -1;}
    String ts [] = new String[15];
    for (int i=0;i<12 ;i++ ) {
      ts[i]=g.get("bouton.desc."+(20+i));
      if(!tableau.estDansT(t,i)){
        ts[i]+=" ("+g.get("unaviable")+")"; //TODO add to translation.
      }
    }
    ts[12]=g.getM("setPlayingAnt");//TODO add to translation.
    ts[13]=g.getM("endTurn");//TODO add to translation.
    ts[14]=g.getM("endGame");//TODO add to translation.
    tToPrint = ts;
    int choice = -1;
    do {
      paint();
      choice = getActionMenu(tToPrint.length)-1;
    } while (choice <12 && !tableau.estDansT(t,choice));
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
    Main.getPartie().getGc().afficheCarte();
    //System.out.println(g.get("playingPlayer")+ " : ");
    //System.out.println(playingPlayer);
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
  private String getAllyAntInColor(Creature c){
    String r = "";
    if(Main.getOs().isLinux()){
      if (c.getStade()==0) {
        if(c.getAction()==c.getActionMax()){
          r+=green;
        }else if (c.getAction()<=0) {
          r+=red;
        }else{
          r+=yellow;
        }
      }
    }
    r+=c;
    if(Main.getOs().isLinux()){
      r+=neutral;
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
}
