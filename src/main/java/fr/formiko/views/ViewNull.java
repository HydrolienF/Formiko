package fr.formiko.views;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Fourmi;

/**
 *{@summary Null view. A simple view who do nothing when a view action is launch.}<br>
 *@author Hydrolien
 *@version 1.33
 */
public class ViewNull implements View {
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean ini(){
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean close(){
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
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean menuNewGame(){
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean menuLoadAGame(){
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean menuPersonaliseAGame(){
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean menuOptions(){
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@version 1.33
  */
  public boolean actionGame(){
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
  public boolean setPlayingAnt(Fourmi f){
    return true;
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
