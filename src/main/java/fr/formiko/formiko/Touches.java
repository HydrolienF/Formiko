package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

/**
*Key file that implements KeyListener and listend all key during all the game.
*@author Hydrolien
*@version 1.13
*/
public class Touches implements KeyListener, Serializable{
  public void keyTyped(KeyEvent evt){}

  public void keyPressed(KeyEvent evt){}

  /**
  *If a key is clic.
  *@version 1.13
  */
  @Override
  public void keyReleased(KeyEvent evt) {
    char c = evt.getKeyChar();
    int x = c+0;
    debug.débogage("la touche \""+c+"\" a été pressée. Clé = "+x);
    if (c == Main.getKey("echap")){ //touche échap
      if(Main.getJeuEnCours()){return;}
      if(Main.getPe().getAff()){
        Main.getPe().setVisible(false);
      }else{
        Main.getPj().setDesc("");
        Main.getPe().setVisible(true);
      }
    }else if(Main.getPlayingAnt()!=null){
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
    }else if (c == Main.getKey("space")){
      //on simule un clic sur le PanneauDialogue.
      try {
        Main.getPd().clicEn(0,0);
      }catch (Exception e) {}
    }else{
      debug.débogage("La touche \""+c+"\" a été pressée sans qu'une action ne soit déclanchée.");
    }
  }
}
