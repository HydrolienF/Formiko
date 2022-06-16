package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
//def par défaut des fichiers depuis 0.79.5

/**
*{@summary To run a level.}<br>
*It launch a script of cheat code.
*@author Hydrolien
*@lastEditedVersion 1.51
*/
public class ThScript extends Thread{
  private Script scr;
  private boolean needToStop;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main contructor.}<br>
  *@lastEditedVersion 1.51
  */
  public ThScript(String s){
    scr = new Script(s);
    needToStop=false;
  }
  // GET SET -------------------------------------------------------------------
  public Script getScript(){return scr;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Main methode to launch script.}<br>
  *It is call with Main.launchScript().
  *@lastEditedVersion 1.51
  */
  @Override
  public void run(){
    scr.script();
  }
  /**
  *{@summary To stop thread.}<br>
  *@lastEditedVersion 1.51
  */
  @Override
  public void interrupt(){
    needToStop=true;
  }
  /**
  *{@summary Used by the script to know if it should stop himself.}<br>
  *@lastEditedVersion 1.51
  */
  @Override
  public boolean isInterrupted(){
    return needToStop;
  }
}
