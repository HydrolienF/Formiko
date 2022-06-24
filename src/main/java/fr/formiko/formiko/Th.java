package fr.formiko.formiko;

import fr.formiko.usual.Chrono;
import fr.formiko.views.gui2d.ini;
import fr.formiko.usual.g;
import fr.formiko.usual.erreur;

public class Th extends Thread {
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
      Chrono.startCh(ch2);
      ini.initializeFPanelJeuAndSubpanel();
      Chrono.endCh(g.get("chargementFPanelJeuEtDépendance"),ch2);
    }
    erreur.info("th"+x+" over");
  }
  public synchronized void chargementDesGraphismesAutonomes(){
    Chrono ch2 = new Chrono();
    Chrono.startCh(ch2);
    Main.getData().tournerLesFleches(); //only item that still need to be turn.
    Chrono.endCh(g.get("chargementDesElémentsTourné"),ch2);
    Chrono.startCh(ch2);
    Main.getData().chargerImagesIni();
    Chrono.endCh("Load default size images",ch2);
  }
}
