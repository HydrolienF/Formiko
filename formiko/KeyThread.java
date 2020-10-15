package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5


public class KeyThread extends Thread {
  // CONSTRUCTEUR ---------------------------------------------------------------
  public KeyThread(){
    super("KeyThread");
  }
  // Fonctions propre -----------------------------------------------------------
  public void run(){
    debug.débogage("Lancement de l'écoute du clavier");
    while(Main.getEcouteClavier()){
      // Idéalement il faudrait un "si la fenetre est au premier plan"
      try {
        Main.getF().requestFocus();
      }catch (Exception e) {}
    }
  }
}