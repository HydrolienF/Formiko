package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.formiko.Main;
import fr.formiko.graphisme.ini;

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
      Main.débutCh(ch2);
      ini.initialiserPanneauJeuEtDépendance();
      Main.finCh(g.get("chargementPanneauJeuEtDépendance"),ch2);
    }
  }
  public synchronized void chargementDesGraphismesAutonomes(){
    Chrono ch2 = new Chrono();
    Main.débutCh(ch2);
    ini.initialiserAutreELémentTournés();//2
    Main.finCh(g.get("chargementDesElémentsTourné"),ch2);
  }
}
