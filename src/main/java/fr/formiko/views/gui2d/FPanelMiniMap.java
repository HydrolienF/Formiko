package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
*{@summary Panel that contain MiniMap.}<br>
*@author Hydrolien
*@version 2.5
*/
public class FPanelMiniMap extends FPanel {

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *@version 2.5
  */
  public FPanelMiniMap() {
    super();
    int x=25;
    setSize(Main.getTailleElementGraphique(16*x),Main.getTailleElementGraphique(9*x));
    FBorder fBorder = new FBorder();
    fBorder.setColor(Main.getData().getButonBorderColor());
    setBorder(fBorder);
  }
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics gTemp){
    //TODO
  }
  // SUB-CLASS -----------------------------------------------------------------
}
