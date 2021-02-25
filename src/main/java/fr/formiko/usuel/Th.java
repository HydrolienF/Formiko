package fr.formiko.usuel;

import fr.formiko.views.gui2d.ini;
import fr.formiko.usuel.Chrono;

public class Th extends Thread{
  public int x;
  public Th(int x){
    this.x=x;
  }
  // CONSTRUCTEUR ---------------------------------------------------------------

  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  @Override
  public void run(){
    if(x==1){
      chargementDesGraphismesAutonomes();
    }else if(x==2){
      Chrono ch2 = new Chrono();
      Chrono.debutCh(ch2);
      ini.initialiserPanneauJeuEtDépendance();
      Chrono.endCh(g.get("chargementPanneauJeuEtDépendance"),ch2);
    }
  }
  public synchronized void chargementDesGraphismesAutonomes(){
    Chrono ch2 = new Chrono();
    Chrono.debutCh(ch2);
    ini.initialiserAutreELémentTournés();//2
    Chrono.endCh(g.get("chargementDesElémentsTourné"),ch2);
  }
}
