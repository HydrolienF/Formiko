package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
*{@summary Key file that will launch all keys commands in gui2d.}<br>
*@author Hydrolien
*@version 1.40
*/
public class keys {
  /**
  *{@summary Add keys listeners for all gui part.}
  *@version 1.40
  */
  public static void addBindings(){
    addActionToActionMap();
    InputMap inputMap = Main.getPp().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

    inputMap.put(KeyStroke.getKeyStroke((char)27),"escape");
    inputMap.put(KeyStroke.getKeyStroke('\n'), "enter");
    for (int i=20; i<31; i++ ) {
      inputMap.put(KeyStroke.getKeyStroke((char)Main.getKey(i+"")), "antAction");
    }

    // inputMap.put(KeyStroke.getKeyStroke('S',InputEvent.CTRL_DOWN_MASK), "save");
  }
  /**
  *{@summary Add actions to use with listeners for all gui part.}
  *@version 1.40
  */
  private static void addActionToActionMap(){
    ActionMap actionMap = Main.getF().getPp().getActionMap();

    //globals actions
    Action action = new AbstractAction() {
      /**
      *{@summary Show or hide escape panel.}
      *@version 1.40
      */
      public void actionPerformed(ActionEvent actionEvent) {
        if(!Main.getView().getActionGameOn()){return;}
        if(Main.getPe().getVisible()){
          Main.getPe().setVisible(false);
        }else{
          Main.getPj().setDesc("");
          Main.getPe().setVisible(true);
        }
      }
    };
    actionMap.put("escape",action);

    action = new AbstractAction() {
      /**
      *{@summary Try to go to next PanneauDialogue.}
      *@version 1.40
      */
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          Main.getPd().clicEn(0,0);
        }catch (Exception e) {}
      }
    };
    actionMap.put("space",action);

    action = new AbstractAction() {
      /**
      *{@summary Launch game or swap plaing ant or end turn.}
      *@version 1.40
      */
      public void actionPerformed(ActionEvent actionEvent) {
        // if (Main.getPd()!=null) {
        //   try {
        //     Main.getPd().clicEn(0,0);
        //   }catch (Exception e) {}
        if (Main.getPch()!=null) {
          Main.finLancementNouvellePartie();
        }else if(Main.getPlayingAnt()!=null){
          //TODO passer le tour ou a la prochaine Fourmi qui a des actions.
        }
      }
    };
    actionMap.put("enter",action);

    //ants actions
    action = new AbstractAction() {
      /**
      *{@summary Do an ant action.}
      *@version 1.40
      */
      public void actionPerformed(ActionEvent actionEvent) {
        if(Main.getPlayingAnt()==null){return;}
        char c = actionEvent.getActionCommand().charAt(0);
        System.out.println(actionEvent);
        System.out.println(c);
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
    };
    actionMap.put("antAction",action);
  }
}
