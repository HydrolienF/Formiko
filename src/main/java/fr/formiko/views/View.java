package fr.formiko.views;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Fourmi;

/**
 *{@summary Main view interface.}<br>
 *All views need to implement this interface !<br>
 *All action sould be independent and should be launchable after 1 launch of view.ini().<br>
 *@author Hydrolien
 *@version 1.33
 */
public interface View {
  boolean getActionGameOn();
  /***
  *{@summary Initialize all the thing that need to be Initialize before using view.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  boolean ini();
  /***
  *{@summary close all the thing that need to be close after using view.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  boolean close();
  /***
  *{@summary Refrech actual view.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  boolean paint();
  /***
  *{@summary Load main menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  boolean menuMain();
  /***
  *{@summary Load new game menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  boolean menuNewGame();
  /***
  *{@summary Load the save load menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  boolean menuLoadAGame();
  /***
  *{@summary personalise a game menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  boolean menuPersonaliseAGame();
  /***
  *{@summary options menu.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  boolean menuOptions();
  /***
  *{@summary Launch action game part.}<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  boolean actionGame();


  //Only on action game mode :
  /***
  *{@summary Stop game and print the escape menu.}<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  int pauseActionGame();
  /***
  *{@summary Change the value of the looked CCase.}<br>
  *We need to repaint the information about this Case.<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@version 1.33
  */
  boolean setLookedCCase(CCase cc);
  /***
  *{@summary Return the value of the looked CCase.}<br>
  *This action can only be run if action game is on.<br>
  *@return lookedCCase
  *@version 1.46
  */
  CCase getLookedCCase();
  /***
  *{@summary Return the chosen value for ant action.}<br>
  *This action can only be run if action game is on.<br>
  *@return Return ant choice.
  *@version 1.33
  */
  int getAntChoice(int t[]);
  /***
  *{@summary Return the chosen CCase.}<br>
  *It is used to move ant.
  *@version 1.39
  */
  CCase getCCase();
  /***
  *{@summary Print a message.}<br>
  *If message.equals("") we may need to delete last message, but we don't need to print a new message.<br>
  *@param message the message to print.
  *@param doWeNeedToDoNextCmdNow true if we need to do next commande now.
  *@version 1.44
  */
  void message(String message, boolean doWeNeedToDoNextCmdNow);
  /***
  *{@summary Print a loading message.}<br>
  *@param message the message to print.
  *@version 1.46
  */
  void loadingMessage(String message);
}
