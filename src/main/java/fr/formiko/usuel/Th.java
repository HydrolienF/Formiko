package fr.formiko.usuel;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Chrono;
import fr.formiko.views.gui2d.ini;

public class Th extends Thread{
  public int x;
  public Th(int x){
    this.x=x;
  }
  // CONSTRUCTORS --------------------------------------------------------------

  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  @Override
  public void run(){
    if(x==1){
      chargementDesGraphismesAutonomes();
    }else if(x==2){
      Chrono ch2 = new Chrono();
      Chrono.debutCh(ch2);
      ini.initializeFPanelJeuAndSubpanel();
      Chrono.endCh(g.get("chargementFPanelJeuEtDépendance"),ch2);
    }
  }
  public synchronized void chargementDesGraphismesAutonomes(){
    Chrono ch2 = new Chrono();
    Chrono.debutCh(ch2);
    Main.getData().tournerLesFleches(); //only item that still need to be turn.
    Chrono.endCh(g.get("chargementDesElémentsTourné"),ch2);
  }
}
