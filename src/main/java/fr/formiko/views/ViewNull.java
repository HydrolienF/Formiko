package fr.formiko.views;

import fr.formiko.formiko.CSquare;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.GJoueur;
import fr.formiko.formiko.ObjetSurCarteAId;
import fr.formiko.usual.CheckFunction;

/**
 *{@summary Null view. A simple view who do nothing when a view action is launch.}<br>
 *@author Hydrolien
 *@lastEditedVersion 1.33
 */
public class ViewNull implements View {
  private boolean actionGameOn=true;
  public boolean getActionGameOn(){return actionGameOn;}
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@lastEditedVersion 1.33
  */
  public boolean ini(){
    actionGameOn=false;
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@lastEditedVersion 1.33
  */
  public boolean close(){
    actionGameOn=false;
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@lastEditedVersion 1.33
  */
  public boolean paint(){
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@lastEditedVersion 1.33
  */
  public boolean menuMain(){
    actionGameOn=false;
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@lastEditedVersion 1.33
  */
  public boolean menuNewGame(){
    actionGameOn=false;
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@lastEditedVersion 1.33
  */
  public boolean menuLoadAGame(){
    actionGameOn=false;
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@lastEditedVersion 1.33
  */
  public boolean menuPersonaliseAGame(){
    actionGameOn=false;
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@lastEditedVersion 1.33
  */
  public boolean menuOptions(){
    actionGameOn=false;
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@lastEditedVersion 1.33
  */
  public boolean actionGame(){
    actionGameOn=true;
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@lastEditedVersion 1.33
  */
  public int pauseActionGame(){
    return 0;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@lastEditedVersion 1.33
  */
  public boolean endActionGame(boolean withButton, int nextLevel, String message, GJoueur gj, boolean canResumeGame){
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@lastEditedVersion 1.33
  */
  public boolean setLookedCSquare(CSquare cc){
    return true;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return true if nothing have been print or paint.
  *@lastEditedVersion 1.46
  */
  public CSquare getLookedCSquare(){
    return null;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return -1;
  *@lastEditedVersion 1.33
  */
  public int getAntChoice(int t[]){
    return -1;
  }
  /**
  *{@summary A null view action that do nothing.}<br>
  *@return Return null;
  *@lastEditedVersion 1.39
  */
  public CSquare getCSquare(){
    return null;
  }
  /***
  *{@summary A null view action that do nothing.}<br>
  *@lastEditedVersion 1.44
  */
  public void message(String message, boolean doWeNeedToDoNextCmdNow){}
  /***
  *{@summary A null view action that do nothing.}<br>
  *@lastEditedVersion 1.46
  */
  public void loadingMessage(String message, int percentageDone){}
  /***
  *{@summary A null view action that do nothing.}<br>
  *@lastEditedVersion 1.46
  */
  public void popUpMessage(String message){}
  /***
  *{@summary A null view action that do nothing.}<br>
  *@lastEditedVersion 1.50
  */
  public String popUpQuestion(String message){return "";}
  /***
  *{@summary A null view action that do nothing.}<br>
  *@lastEditedVersion 2.27
  */
  public boolean popUpQuestionYN(String message, boolean important, CheckFunction cf){return false;}
  /***
  *{@summary A null view action that do nothing.}<br>
  *@lastEditedVersion 1.46
  */
  public void setPlayingAnt(Fourmi f){}
  /***
  *{@summary A null view action that do nothing.}<br>
  *@lastEditedVersion 2.1
  */
  public void move(ObjetSurCarteAId o, CSquare from, CSquare to){}
}
