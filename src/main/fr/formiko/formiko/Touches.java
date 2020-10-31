package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

public class Touches implements KeyListener, Serializable{
  public void keyPressed(KeyEvent evt){}

  public void keyReleased(KeyEvent evt){}

  public void keyTyped(KeyEvent evt) {
    char c = evt.getKeyChar();
    int x = c+0;
    debug.débogage("la touche \""+c+"\" a été pressée. Clé = "+x);
    if (c == Main.getKey("échap")){ //touche échap
      if(Main.getPe().getAff()){
        Main.getPe().setVisible(false);
      }else{
        Main.getPe().setVisible(true);
      }
    }else if(Main.getFActuelle()!=null){
      //toutes les touches liée au fourmi
      for (int i=20;i<31 ;i++ ) {
        try {
          debug.débogage("comparaisons de la clé avec "+Main.getKey(i+""));
          if(c==Main.getKey(i+"")){ // les actions des fourmis
            Main.getPb().setActionF(i-20);return;
          }
        }catch (Exception e) {
          erreur.erreur("la clé d'action de fourmi "+i+" n'as pas été trouvée.");
        }
      }
    }
  }
}
