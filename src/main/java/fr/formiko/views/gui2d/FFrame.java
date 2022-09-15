package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.CheckFunction;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
*{@summary Main container in GUI.}<br>
*@author Hydrolien
*@lastEditedVersion 2.7
*/
public class FFrame extends JFrame {

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *@param title title of the Frame
  *@param width with of the Frame
  *@param height height of the Frame
  *@param fullScreen true if Frame need to be in full screen
  *@lastEditedVersion 2.7
  */
  protected FFrame(String title, int width, int height, boolean fullScreen){
    if(fullScreen){
      if(Main.getOs().getId()==1){ //On Windows
        setExtendedState(JFrame.MAXIMIZED_BOTH); //n'as pas l'effet de plein écran sur Linux.
        setUndecorated(true);
      }else{
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(this);
      }
    }
    setTitle(title);
    setSize(width,height);// (x,y) en pixel
    setLocationRelativeTo(null); // fenetre centrée
    //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // default action
    //Our default close action to end game properly
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run(){iniCloseButton();}
    });
  }
  //TODO create a new builder with .setTitle() .setWidth .setHeight .setFullScreen .setContentPane
  // public static FFrame newFFrame(){
  //   FFrameBuilder fb = new FFrameBuilder();
  // }
  // TODO class build of FFrame builder call new FFrame with param + setContentPane + endIni.

  // GET SET -------------------------------------------------------------------
  public static int getScreenWidth(){ return Toolkit.getDefaultToolkit().getScreenSize().width;}
  public static int getScreenHeight(){ return Toolkit.getDefaultToolkit().getScreenSize().height;}

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Personalise our close button to end game properly.}
  *@lastEditedVersion 2.7
  */
  private void iniCloseButton(){
    addWindowListener(new WindowAdapter() {
      /**
      *{@summary Close view.}
      *@lastEditedVersion 2.7
      */
      @Override
      public void windowClosing(WindowEvent e) {
        Main.getView().close();
      }
      // @Override
      // public void windowStateChanged(WindowEvent e) {
      //   if (e.getNewState() == WindowEvent.WINDOW_LOST_FOCUS) {
      //     e.getWindow().setVisible(false);
      //   }
      // }
    });
  }
  /**
  *{@summary Function to end game properly.}
  *If the Options is enable, we ask a validation to end game.
  *@lastEditedVersion 2.27
  */
  public void quit(){
    try {
      boolean needToClose=true;
      if (!Main.getFop().getBoolean("forceQuit")){
        CheckFunction cf = new CheckFunction(){
          /**
          *{@summary Update Options value if user have enable checkbox.}<br>
          *@lastEditedVersion 2.26
          */
          @Override
          protected void exec(){
            Main.getFop().set("forceQuit",true);
            Main.saveOp();
          }
        };
        cf.setText(g.get("dontAskAgain"));
        needToClose = Main.getView().popUpQuestionYN("quitterJeu", true, cf);
      }
      if(needToClose){
        Main.getF().setVisible(false);
        Main.getF().dispose();
        Main.quitter();
      }//else do nothing
    }catch (Exception e2) {
      erreur.alerte("Normal close of the FFrame fail "+e2);
      Main.quitter();
    }
  }
  /**
  *{@summary Function to end ini by setting resizable, visible &#38; on top.}
  *@lastEditedVersion 2.7
  */
  public void endIni(){
    setResizable(Main.getFop().getBoolean("fullscreen"));
    setVisible(true);
    toFront();
  }
  /**
  *{@summary Function to set window in first plan.}<br>
  *Default toFront don't work on Windows, but this one is working.
  *@lastEditedVersion 2.7
  */
  @Override
  public void toFront(){
    super.setAlwaysOnTop(true);
    super.toFront();
    super.requestFocus();
    super.setAlwaysOnTop(false);
  }
}
