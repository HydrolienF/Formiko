package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.erreur;
import fr.formiko.usual.structures.listes.Liste;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class FPanelIngameMenu extends FPanel {
  private Liste<FButtonPGO> buttonList;
  private int radius;
  // TODO add an escape button with 3 barres that can show or hide FPanelEchap
  // TODO add a music button that can show or hide FPanelMusic
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
  *@lastEditedVersion 2.10
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
  private BufferedImage getMusicImage(){
    BufferedImage bi = new BufferedImage(getHeight(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    return bi;
  }

  public void updateColorButtonEscape(){
    for (FButtonPGO fb : buttonList) {
      if(fb.getActionB()==399){
        fb.updateColor();
        return;
      }
    }
  }
}
