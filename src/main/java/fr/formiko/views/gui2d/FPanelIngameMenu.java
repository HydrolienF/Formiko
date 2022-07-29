package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.erreur;
import fr.formiko.usual.structures.listes.Liste;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;

/**
*{@summary The top rigth ingame panel.}<br>
*@lastEditedVersion 2.28
*@author Hydrolien
*/
public class FPanelIngameMenu extends FPanel {
  private Liste<FButtonPGO> buttonList;
  private int radius;
  /**
  *{@summary Main constructor.}<br>
  *@lastEditedVersion 2.28
  */
  public FPanelIngameMenu(){
    super();
    setOpaque(false);
    buttonList = new Liste<FButtonPGO>();
    radius=(int)(Main.getOp().getFontSizeTitle()/1.5);
    setSize((int)((radius*1.5)*2), radius);
    addButtons();
    int k=0;
    for (FButtonPGO fb : buttonList) {
      fb.setSize(radius, radius);
      fb.setLocation((k++)*(getWidth()-(getHeight()/2))/(buttonList.length())+(getHeight()/2),0);
      fb.setBorderPainted(false);
      add(fb);
    }
  }
  /**
  *{@summary Add all button to the FPanel.}<br>
  *@lastEditedVersion 2.28
  */
  private void addButtons(){
    buttonList.add(new FButtonPGO(398, getMusicImage(), () -> {
      return getView().getPmu().isVisible();
    }));
    buttonList.add(new FButtonPGO(399, getEscapeImage(), () -> {
      return getView().getPe().isVisible();
    }));
  }
  /**
  *{@summary Return a text image.}<br>
  *@lastEditedVersion 2.28
  */
  private BufferedImage getEscapeImage(){
    BufferedImage bi = new BufferedImage(getHeight(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = bi.getGraphics();
    g.setColor(Color.BLACK);
    if(g instanceof Graphics2D){
      Graphics2D g2d = (Graphics2D)g;
      g2d.setStroke(new BasicStroke(2));
    }
    int len = 4;
    for (int i=0; i<len;i++ ) {
      int x = (int)(((i*0.7)+1)*getHeight()/len);
      g.drawLine((getHeight())/4,x,(getHeight()*3)/4,x);
    }
    return bi;
  }
  /**
  *{@summary Return a music image.}<br>
  *@lastEditedVersion 2.28
  */
  private BufferedImage getMusicImage(){
    BufferedImage bi = new BufferedImage(getHeight(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = bi.getGraphics();
    g.setColor(Color.MAGENTA);
    if(g instanceof Graphics2D){
      Graphics2D g2d = (Graphics2D)g;
      g2d.setStroke(new BasicStroke(2));
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //good for all drawLine, drawCircle etc.
    }
    drawLine(g, 0.3, 0.25, 0.8, 0.25);
    drawLine(g, 0.3, 0.35, 0.8, 0.35);
    drawLine(g, 0.3, 0.25, 0.25, 0.65);
    drawLine(g, 0.8, 0.25, 0.75, 0.65);
    int radius = (int)(0.20*getHeight());
    g.fillOval((int)(0.6*getHeight()), (int)(0.62*getHeight()), radius, radius);
    g.fillOval((int)(0.1*getHeight()), (int)(0.62*getHeight()), radius, radius);
    return bi;
  }
  /**
  *{@summary Draw a line.}<br>
  *All double param should be in [0;1].
  *They will be multiply by the max size of the image.
  *@lastEditedVersion 2.28
  */
  private void drawLine(Graphics g, double x1, double y1, double x2, double y2){
    g.drawLine((int)(x1*getHeight()), (int)(y1*getHeight()), (int)(x2*getHeight()), (int)(y2*getHeight()));
  }
  /**
  *{@summary Update the color of the button escape.}<br>
  *This is call when escape have been press.
  *@lastEditedVersion 2.28
  */
  public void updateColorButtonEscape(){
    for (FButtonPGO fb : buttonList) {
      if(fb.getActionB()==399){
        fb.updateColor();
        return;
      }
    }
  }
}
