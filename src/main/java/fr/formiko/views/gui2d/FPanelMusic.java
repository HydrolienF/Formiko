package fr.formiko.views.gui2d;

import fr.formiko.usual.structures.listes.Liste;
import fr.formiko.formiko.Main;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.RenderingHints;

/**
*{@summary A music panel with all the possible music action}
*:<br>
*Play/Pause, next, last<br>
*It also show current music name &#38; author.<br>
*@lastEditedVersion 2.28
*@author Hydrolien
*/
public class FPanelMusic extends FPanelRoundButtonsContainer {
  /**
  *{@summary Main constructor.}<br>
  *@lastEditedVersion 2.28
  */
  public FPanelMusic(){
    super((int)(Main.getOp().getFontSizeTitle()/1.8), 0.3);
  }
  /**
  *{@summary Add all button to the FPanel.}<br>
  *@lastEditedVersion 2.28
  */
  @Override
  protected void addButtons(){
    buttonList.add(new FButtonPGO(395, getArrowImage(false), () -> {
      return false;
    }));
    buttonList.add(new FButtonPGO(396, getPauseImage(), () -> {
      return !Main.getMp().isRunning();
    }));
    buttonList.add(new FButtonPGO(397, getArrowImage(true), () -> {
      return false;
    }));
  }
  /**
  *{@summary Return a next or last image.}<br>
  *@param turnRigth if trun arrow is turn to the rigth
  *@lastEditedVersion 2.28
  */
  public BufferedImage getArrowImage(boolean turnRigth){
    BufferedImage bi = new BufferedImage(getHeight(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = bi.getGraphics();
    g.setColor(Color.BLACK);
    if(g instanceof Graphics2D){
      Graphics2D g2d = (Graphics2D)g;
      g2d.setStroke(new BasicStroke(2));
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //good for all drawLine, drawCircle etc.
    }
    drawLine(g, 0.3, 0.3, 0.3, 0.7);
    drawLine(g, 0.7, 0.3, 0.7, 0.7);
    if(turnRigth){
      drawLine(g, 0.7, 0.5, 0.3, 0.7);
      drawLine(g, 0.7, 0.5, 0.3, 0.3);
    }else{
      drawLine(g, 0.3, 0.5, 0.7, 0.7);
      drawLine(g, 0.3, 0.5, 0.7, 0.3);
    }
    return bi;
  }
  /**
  *{@summary Return a pause image.}<br>
  *@lastEditedVersion 2.28
  */
  public BufferedImage getPauseImage(){
    BufferedImage bi = new BufferedImage(getHeight(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = bi.getGraphics();
    g.setColor(Color.BLACK);
    if(g instanceof Graphics2D){
      Graphics2D g2d = (Graphics2D)g;
      g2d.setStroke(new BasicStroke(3));
    }
    drawLine(g, 0.4, 0.2, 0.4, 0.8);
    drawLine(g, 0.6, 0.2, 0.6, 0.8);
    return bi;
  }
  /**
  *{@summary Update the color of the button pause.}<br>
  *This is call when music have changed or being pause/resume.
  *@lastEditedVersion 2.28
  */
  public void updateColorButtonPause(){
    for (FButtonPGO fb : buttonList) {
      if(fb.getActionB()==396){
        fb.updateColor();
        return;
      }
    }
  }
}
