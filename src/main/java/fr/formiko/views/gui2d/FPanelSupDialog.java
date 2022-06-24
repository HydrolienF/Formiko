package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.usual.Time;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.maths.math;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
*{@summary Map mouse listener panel.}<br>
*@author Hydrolien
*@lastEditedVersion 2.19
*/
public class FPanelSupDialog extends FPanel {

  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelSupDialog(){}
  /**
  *{@summary Build this.}<br>
  *It add a MouseListener that clic to pass dialog.
  *@lastEditedVersion 2.19
  */
  public void build(){
    addMouseListener(new MouseListenerEmpty() {
      /**
      *{@summary Check if FPanelDialogue is waiting to go to next dialog.}<br>
      *@lastEditedVersion 2.19
      */
      @Override
      public void mouseReleased(MouseEvent e) {
        checkFPanelDialogue(e);
      }
    });
  }
  // GET SET -------------------------------------------------------------------
  /**
  *{@summary Update size to max one.}<br>
  *@lastEditedVersion 2.19
  */
  public void updateSizeMax(){
    setSize(Main.getDimX(), Main.getDimY());
  }
  /**
  *{@summary Update size to 0,0.}<br>
  *@lastEditedVersion 2.19
  */
  public void updateSizeMin(){
    setSize(0,0);
  }
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Paint nothing.}<br>
  *@lastEditedVersion 2.19
  */
  @Override
  public void paintComponent(Graphics g){
    //do nothing
    // g.setColor(new Color(100,0,0,100));
    // g.fillRect(0,0,getWidth(),getHeight());
  }
  /**
  *{@summary Check if FPanelDialogue is waiting to go to next dialog.}<br>
  *@lastEditedVersion 2.19
  */
  public boolean checkFPanelDialogue(MouseEvent e){
    return checkFPanelDialogue(e.getX(), e.getY());
  }
  /**
  *{@summary Check if FPanelDialogue is waiting to go to next dialog.}<br>
  *@lastEditedVersion 2.19
  */
  public boolean checkFPanelDialogue(int x, int y){
    try {
      boolean b = getView().getPd().clicEn(x,y);
      if(b){
        updateSizeMin();
      }
      return b;
    }catch (Exception e2) {
      return false;
    }
  }
}
