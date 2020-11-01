package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.Temps;

public class ThGraphisme extends Thread{
  public ThGraphisme(){}
  // CONSTRUCTEUR ---------------------------------------------------------------

  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  @Override
  public void run(){
    while(true){
      Temps.pause(20);
      try {
        //Main.repaint();
        Main.getF().revalidate();
      }catch (Exception e) {}
    }
  }

}
