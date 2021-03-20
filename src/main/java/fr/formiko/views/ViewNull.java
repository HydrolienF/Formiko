package fr.formiko.views;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Fourmi;

/**
 *{@summary Null view. A simple view who do nothing when a view action is launch.}<br>
 *@author Hydrolien
 *@version 1.33
 */
public class ViewNull implements View {
  private boolean actionGameOn=true;
  public boolean getActionGameOn(){return actionGameOn;}
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean ini(){
    actionGameOn=false;
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean close(){
    actionGameOn=false;
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean paint(){
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean menuMain(){
    actionGameOn=false;
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean menuNewGame(){
    actionGameOn=false;
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean menuLoadAGame(){
    actionGameOn=false;
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean menuPersonaliseAGame(){
    actionGameOn=false;
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean menuOptions(){
    actionGameOn=false;
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean actionGame(){
    actionGameOn=true;
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public int pauseActionGame(){
    return 0;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean setLookedCase(CCase cc){
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return -1;
  *@version 1.33
  */
  public int getAntChoice(int t[]){
    return -1;
  }
}
