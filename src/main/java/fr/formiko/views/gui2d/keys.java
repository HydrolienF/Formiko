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
*@lastEditedVersion 1.40
*/
public class keys {
  /**
  *{@summary Add keys listeners for all gui part.}
  *@lastEditedVersion 1.40
  */
  public static void addBindings(){
    addActionToActionMap();
    InputMap inputMap = FPanel.getView().getPp().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

    inputMap.put(KeyStroke.getKeyStroke((char)27),"escape");
    inputMap.put(KeyStroke.getKeyStroke('\n'), "enter");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "upArrowT");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "downArrowT");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "leftArrowT");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "rightArrowT");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "upArrowF");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "downArrowF");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "leftArrowF");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "rightArrowF");
    for (int i=20; i<31; i++ ) {
      inputMap.put(KeyStroke.getKeyStroke((char)Main.getKey(i+"")), "antAction");
    }

    // inputMap.put(KeyStroke.getKeyStroke('S',InputEvent.CTRL_DOWN_MASK), "save");
  }
  /**
  *{@summary Add actions to use with listeners for all gui part.}
  *@lastEditedVersion 1.40
  */
  private static void addActionToActionMap(){
    ActionMap actionMap = FPanel.getView().getPp().getActionMap();

    //globals actions
    Action actionA = new AbstractAction() {
      /**
      *{@summary Show or hide escape panel or do return action.}
      *@lastEditedVersion 1.49
      */
      public void actionPerformed(ActionEvent actionEvent) {
        if(FPanel.getView().getActionGameOn()){
          if(FPanel.getView().getPe().getVisible()){
            FPanel.getView().getPe().setVisible(false);
          }else{
            FPanel.getView().setMessageDesc("");
            FPanel.getView().getPe().setVisible(true);
          }
        }else{
          // FPanel.getView().getPm().getReturnButton().doClick();
          // FPanel.getView().getPm().getReturnButton().processMouseEvent​(new MouseEvent());
          action.doAction(FPanel.getView().getPm().getReturnButton().getActionB());
        }
      }
    };
    actionMap.put("escape",actionA);

    actionA = new AbstractAction() {
      /**
      *{@summary Try to go to next FPanelDialogue.}
      *@lastEditedVersion 1.40
      */
      public void actionPerformed(ActionEvent actionEvent) {
        try {
          FPanel.getView().getPd().clicEn(0,0);
        }catch (Exception e) {}
      }
    };
    actionMap.put("space",actionA);

    actionA = new AbstractAction() {
      /**
      *{@summary Launch game or swap plaing ant or end turn.}
      *@lastEditedVersion 1.40
      */
      public void actionPerformed(ActionEvent actionEvent) {
        if (FPanel.getView().getPch()!=null && FPanel.getView().getPch().canBeClose()) {
          FPanel.getView().closeFPanelChargement();
        }else if (FPanel.getView().getPd()!=null && FPanel.getView().getPd().isVisible()) {
          try {
            FPanel.getView().getPd().clicEn(0,0);
          }catch (Exception e) {}
        }else if(FPanel.getView().getPcp()!=null && FPanel.getView().getPcp().getLaunchButton()!=null){
          action.doAction(FPanel.getView().getPcp().getLaunchButton().getActionB());
        }else if(FPanel.getView().getPnp()!=null && FPanel.getView().getPnp().getLaunchButton()!=null){
          action.doAction(FPanel.getView().getPnp().getLaunchButton().getActionB());
        // if ant isn't null and an ant have still action to do
      }else if(Main.getPlayingAnt()!=null && !Main.getPlayingAnt().getFere().getGc().haveDoneAllActionAviable()){
          FPanel.getView().getPb().setActionF(-2);
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
      *@lastEditedVersion 1.40
      */
      public void actionPerformed(ActionEvent actionEvent) {
        if(Main.getPlayingAnt()==null){return;}
        char c = actionEvent.getActionCommand().charAt(0);
        for (int i=20;i<31 ;i++ ) {
          try {
            debug.débogage("comparaisons de la clé avec "+Main.getKey(i+""));
            if(c==Main.getKey(i+"")){ // les actions des fourmis
              FPanel.getView().getPb().setActionF(i-20);return;
            }
          }catch (Exception e) {
            erreur.erreur("la clé d'action de fourmi "+i+" n'as pas été trouvée.");
          }
        }
      }
    };
    actionMap.put("antAction",actionA);
    //map move actions
    actionMap.put("upArrowT", new arrowAction(false, false, true));
    actionMap.put("downArrowT", new arrowAction(false, true, true));
    actionMap.put("rightArrowT", new arrowAction(true, true, true));
    actionMap.put("leftArrowT", new arrowAction(true, false, true));
    actionMap.put("upArrowF", new arrowAction(false, false, false));
    actionMap.put("downArrowF", new arrowAction(false, true, false));
    actionMap.put("rightArrowF", new arrowAction(true, true, false));
    actionMap.put("leftArrowF", new arrowAction(true, false, false));
  }
}
/**
*{@summary Do a map move as an arrow action.}<br>
*@author Hydrolien
*@lastEditedVersion 2.14
*/
class arrowAction extends AbstractAction {
  private boolean inX;
  private boolean up;
  private double speed;
  /**
  *{@summary Main constructor.}<br>
  *@param inX true if we are moving in x, false if we are moving in y
  *@param up true if windows should up in X or in Y
  *@param keyRelease if true speed is set to 0 else speed is set to 1
  *@lastEditedVersion 2.14
  */
  public arrowAction(boolean inX, boolean up, boolean keyRelease){
    this.inX=inX;
    this.up=up;
    if(keyRelease){
      speed=0;
    }else{
      speed=1;
    }
  }
  /**
  *{@summary Strandard to string.}
  *@lastEditedVersion 2.14
  */
  public String toString(){
    return "inX: "+inX+" up: "+up+" speed: "+speed;
  }
  /**
  *{@summary Do a map move action.}
  *@lastEditedVersion 2.14
  */
  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    if(FPanel.getView().getActionGameOn() && FPanel.getView().getPmmo()!=null){
      FPanel.getView().getPmmo().setOver(inX, up, speed);
    }
  }
}
