package fr.formiko.views.gui2d;

import fr.formiko.usual.erreur;
import fr.formiko.usual.structures.listes.Liste;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;

/**
*{@summary A panel that contain a FButtonPGO list.}<br>
*@lastEditedVersion 2.28
*@author Hydrolien
*/
public class FPanelRoundButtonsContainer extends FPanel {
  protected Liste<FButtonPGO> buttonList;
  protected int radius;
  protected double margin;
  /**
  *{@summary Main constructor.}<br>
  *@param radius radius of the buttons
  *@param margin space between 2 buttons that depend of button size
  *@lastEditedVersion 2.28
  */
  public FPanelRoundButtonsContainer(int radius, double margin){
    super();
    this.radius=radius;
    this.margin=margin;
    setOpaque(false);
    buttonList = new Liste<FButtonPGO>();
    updateSize();
    addButtons();
    updateSize();
    int k=0;
    for (FButtonPGO fb : buttonList) {
      fb.setSize(radius, radius);
      fb.setLocation((k++)*(getWidth()-(getHeight()/2))/(buttonList.length())+(getHeight()/2),0);
      fb.setBorderPainted(false);
      add(fb);
    }
  }
  /**
  *{@summary Update panel size depending of buttons, radius &#38; margin.}<br>
  *@lastEditedVersion 2.28
  */
  protected void updateSize(){
    setSize((int)((radius*(1.0+margin))*buttonList.length()), radius);
  }
  /***
  *{@summary Function to override to add all buttons.}<br>
  *@lastEditedVersion 2.28
  */
  protected void addButtons(){}

  /**
  *{@summary Draw a line.}<br>
  *All double param should be in [0;1].
  *They will be multiply by the max size of the image.
  *@lastEditedVersion 2.28
  */
  protected void drawLine(Graphics g, double x1, double y1, double x2, double y2){
    g.drawLine((int)(x1*getHeight()), (int)(y1*getHeight()), (int)(x2*getHeight()), (int)(y2*getHeight()));
  }
}
