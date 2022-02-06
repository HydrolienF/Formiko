package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.math;

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
      @Override
      public void mouseReleased(MouseEvent e) {
        vérifierFPanelDialogue(e);
      }
    });
  }
  // GET SET -------------------------------------------------------------------
  public void actualiserTailleMax(){
    setSize(Main.getDimX(), Main.getDimY());
  }
  public void actualiserTailleMin(){
    setSize(0,0);
  }
  // FUNCTIONS -----------------------------------------------------------------
  @Override
  public void paintComponent(Graphics g){
    //do nothing
    // g.setColor(new Color(100,0,0,100));
    // g.fillRect(0,0,getWidth(),getHeight());
  }
  public boolean vérifierFPanelDialogue(MouseEvent e){
    try {
      boolean b = getView().getPd().clicEn(e.getX(),e.getY());
      if(b){
        actualiserTailleMin();
      }
      return b;
    }catch (Exception e2) {
      return false;
    }
  }
}
