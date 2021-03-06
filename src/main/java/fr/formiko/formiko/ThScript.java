package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.79.5

/**
*{@summary To run a level.}<br>
*It launch a script of cheat code.
*@author Hydrolien
*@version 1.51
*/
public class ThScript extends Thread{
  private Script scr;
  private boolean needToStop;
  // CONSTRUCTEUR ---------------------------------------------------------------
  /**
  *{@summary Main contructor.}<br>
  *@version 1.51
  */
  public ThScript(String s){
    scr = new Script(s);
    needToStop=false;
  }
  // GET SET --------------------------------------------------------------------
  public Script getScript(){return scr;}
  // Fonctions propre -----------------------------------------------------------
  /**
  *{@summary Main methode to launch script.}<br>
  *It is call with Main.launchScript().
  *@version 1.51
  */
  @Override
  public void run(){
    scr.script();
  }
  /**
  *{@summary To stop thread.}<br>
  *@version 1.51
  */
  @Override
  public void interrupt(){
    needToStop=true;
  }
  /**
  *{@summary Used by the script to know if it should stop himself.}<br>
  *@version 1.51
  */
  @Override
  public boolean isInterrupted(){
    return needToStop;
  }
}
