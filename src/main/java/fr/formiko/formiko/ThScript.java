package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.79.5

public class ThScript extends Thread{
  private Script scr;
  private boolean needToStop;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public ThScript(String s){
    scr = new Script(s);
    needToStop=false;
  }
  // GET SET --------------------------------------------------------------------
  public Script getScript(){return scr;}
  // Fonctions propre -----------------------------------------------------------
  @Override
  public void run(){
    scr.script();
  }
  @Override
  public void interrupt(){
    needToStop=true;
  }
  @Override
  public boolean isInterrupted(){
    return needToStop;
  }
}
