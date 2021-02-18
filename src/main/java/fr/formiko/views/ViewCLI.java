package fr.formiko.views;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.formiko.triche;
import fr.formiko.usuel.g;
import fr.formiko.usuel.sauvegarderUnePartie;
import fr.formiko.usuel.save;
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
  private boolean actionGameOn;
  private String tToPrint[];

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
      //TODO
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
      System.out.println("open options.");//@a
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
      System.out.println("launch a new game");//@a

      //actionGame();
      break;
      case 2 :
      System.out.println("launch a personalised game");//@a
      break;
      case 3 :
      System.out.println("tuto");//@a
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
    if(tToPrint.length==0){return false;}
    paint();
    int choice = getActionMenu(tToPrint.length);
    Partie pa = sauvegarderUnePartie.charger(tToPrint[choice-1]);
    System.out.println(pa);//@a
    return true;
  }
  /***
  *{@summary Launch action game part.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  public boolean actionGame(){
    actionGameOn=true;
    return false;
  }


  //Only on action game mode :
  /**
  *{@summary Stop game and print the escape menu.}<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  public boolean pauseActionGame(){
    //bouton.nom.-10 to -15
    return false;
  }
  /**
  *{@summary change the value of the playing ant.}<br>
  *We need to repaint the information about this playingAnt.<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  public boolean setPlayingAnt(){
    if (!actionGameOn) {return false;}
    return false;
  }
  /**
  *{@summary Change the value of the loked Case.}<br>
  *We need to repaint the information about this Case.<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  public boolean setLookedCase(){
    if (!actionGameOn) {return false;}
    return false;
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
