package fr.formiko.views.gui2d;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Joueur;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.formiko.triche;
import fr.formiko.usuel.Temps;
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
 *@version 1.42
 */
public class ViewGUI2d implements View {
  private boolean actionGameOn;
  /***
  *Main windows
  *@version 1.1
  */
  private Fenetre f;
  public Fenetre getF(){return f;}
  public boolean getActionGameOn(){return actionGameOn;}
  /***
  *{@summary Initialize all the thing that need to be Initialize before using view.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.42
  */
  public boolean ini(){
    actionGameOn=false;
    f = new Fenetre();
    // try {
    //   Main.getThTriche().start();
    // }catch (Exception e) {
    //   erreur.erreur("Impossible de lancer l'écoute des codes triches.");
    // }
    // Main.getData().setImageIniForNewGame(false);//force reload of ant images.
    return true;
  }
  /***
  *{@summary close all the thing that need to be close after using view.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.42
  */
  public boolean close(){
    actionGameOn=false;
    return true;
  }
  /***
  *{@summary Refrech actual view.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.42
  */
  public boolean paint(){
    Main.repaint();
    return true;
  }
  /***
  *{@summary Load main menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.42
  */
  public boolean menuMain(){
    actionGameOn=false;
    return true;
  }
  /***
  *{@summary Load new game menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.42
  */
  public boolean menuNewGame(){
    actionGameOn=false;
    return true;
  }
  /***
  *{@summary Load the save load menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.42
  */
  public boolean menuLoadAGame(){
    actionGameOn=false;
    return true;
  }
  /***
  *{@summary personalise a game menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.42
  */
  public boolean menuPersonaliseAGame(){
    actionGameOn=false;
    return true;
  }
  /***
  *{@summary options menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.42
  */
  public boolean menuOptions(){
    actionGameOn=false;
    return true;
  }
  /***
  *{@summary Launch action game part.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.42
  */
  public boolean actionGame(){
    actionGameOn=true;
    return true;
  }


  //Only on action game mode :
  /***
  *{@summary Stop game and print the escape menu.}<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.42
  */
  public int pauseActionGame(){
    if (!actionGameOn) {return -1;}
    return 0;
  }
  /***
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
  /***
  *{@summary Return the chosen value for ant action.}<br>
  *This action can only be run if action game is on.<br>
  *@return Return ant choice.
  *@version 1.42
  */
  public int getAntChoice(int t[]){
    if (!actionGameOn) {return -1;}
    return 0;
  }
  /***
  *{@summary Return the chosen CCase.}<br>
  *It is used to move ant.
  *@version 1.42
  */
  public CCase getCCase(){
    if (!actionGameOn) {return null;}
    return null;
  }
}
