package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.views.gui2d.action;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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
    InputMap inputMap = Panneau.getView().getPp().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

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
    ActionMap actionMap = Panneau.getView().getPp().getActionMap();

    //globals actions
    Action actionA = new AbstractAction() {
      /**
      *{@summary Show or hide escape panel or do return action.}
      *@version 1.49
      */
      public void actionPerformed(ActionEvent actionEvent) {
        if(Panneau.getView().getActionGameOn()){
          if(Panneau.getView().getPe().getVisible()){
            Panneau.getView().getPe().setVisible(false);
          }else{
            Panneau.getView().setMessageDesc("");
            Panneau.getView().getPe().setVisible(true);
          }
        }else{
          // Panneau.getView().getPm().getReturnButton().doClick();
          // Panneau.getView().getPm().getReturnButton().processMouseEvent​(new MouseEvent());
          action.doAction(Panneau.getView().getPm().getReturnButton().getActionB());
        }
      }
    };
    actionMap.put("escape",actionA);

    actionA = new AbstractAction() {
      /**
      *{@summary Try to go to next PanneauDialogue.}
      *@version 1.40
      */
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          Panneau.getView().getPd().clicEn(0,0);
        }catch (Exception e) {}
      }
    };
    actionMap.put("space",actionA);

    actionA = new AbstractAction() {
      /**
      *{@summary Launch game or swap plaing ant or end turn.}
      *@version 1.40
      */
      public void actionPerformed(ActionEvent actionEvent) {
        if (Panneau.getView().getPch()!=null && Panneau.getView().getPch().canBeClose()) {
          Panneau.getView().closePanneauChargement();
        }else if (Panneau.getView().getPd()!=null && Panneau.getView().getPd().isVisible()) {
          try {
            Panneau.getView().getPd().clicEn(0,0);
          }catch (Exception e) {}
        }else if(Panneau.getView().getPcp()!=null && Panneau.getView().getPcp().getLaunchButton()!=null){
          action.doAction(Panneau.getView().getPcp().getLaunchButton().getActionB());
        }else if(Panneau.getView().getPnp()!=null && Panneau.getView().getPnp().getLaunchButton()!=null){
          action.doAction(Panneau.getView().getPnp().getLaunchButton().getActionB());
        // if ant isn't null and an ant have still action to do
      }else if(Main.getPlayingAnt()!=null && !Main.getPlayingAnt().getFere().getGc().haveDoneAllActionAviable()){
          Panneau.getView().getPb().setActionF(-2);
        }else{
        // if we need to play next turn.
          try {
            action.doActionPj(200);
          }catch (Exception e) {}
        }
      }
    };
    actionMap.put("enter",actionA);

    //ants actions
    actionA = new AbstractAction() {
      /**
      *{@summary Do an ant action.}
      *@version 1.40
      */
      public void actionPerformed(ActionEvent actionEvent) {
        if(Main.getPlayingAnt()==null){return;}
        char c = actionEvent.getActionCommand().charAt(0);
        for (int i=20;i<31 ;i++ ) {
          try {
            debug.débogage("comparaisons de la clé avec "+Main.getKey(i+""));
            if(c==Main.getKey(i+"")){ // les actions des fourmis
              Panneau.getView().getPb().setActionF(i-20);return;
            }
          }catch (Exception e) {
            erreur.erreur("la clé d'action de fourmi "+i+" n'as pas été trouvée.");
          }
        }
      }
    };
    actionMap.put("antAction",actionA);
  }
}
