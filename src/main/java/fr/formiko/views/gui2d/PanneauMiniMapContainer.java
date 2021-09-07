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
*{@summary Panel that contain MiniMap, fBEndTurn button &#38; graphics options buttons.}<br>
*@author Hydrolien
*@version 2.5
*/
public class PanneauMiniMapContainer extends Panneau {
  private static int BUTTON_RADIUS=18;
  private FButtonEndTurn fBEndTurn;
  private PanneauMiniMap pmm;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *@version 2.5
  */
  public PanneauMiniMapContainer() {
    super();
    fBEndTurn = new FButtonEndTurn();
    add(fBEndTurn);
    pmm = new PanneauMiniMap();
    pmm.setLocation(Main.getTailleElementGraphiqueX(BUTTON_RADIUS),Main.getTailleElementGraphiqueY(BUTTON_RADIUS));
    add(pmm);
    setSize(pmm.getWidth()+Main.getTailleElementGraphiqueX(BUTTON_RADIUS), pmm.getHeight()+Main.getTailleElementGraphiqueY(BUTTON_RADIUS));
    setLocation(Panneau.getView().getWidth()-getWidth(), Panneau.getView().getHeight()-getHeight());
  }

  // GET SET -------------------------------------------------------------------
  /**
  *{@summary Shortcut for fBEndTurn.}<br>
  *@version 2.5
  */
  public void setAllActionDone(boolean b){
    fBEndTurn.setAllActionDone(b);
  }
  /**
  *{@summary Shortcut for fBEndTurn.}<br>
  *@version 2.5
  */
  public void setFbetEnabled(boolean b){
    fBEndTurn.setEnabled(b);
  }

  // FUNCTIONS -----------------------------------------------------------------
  //Panneau.getPmmc().setFbetEnabled(false);


  // SUB-CLASS -----------------------------------------------------------------
  /**
  *{@summary FButton to end turn.}<br>
  *This button is green when player still have action to do otherwise red.
  *@author Hydrolien
  *@version 2.5
  */
  class FButtonEndTurn extends FButton {
    private int lineSize;
    private Color color;

    // CONSTRUCTORS --------------------------------------------------------------
    /**
    *{@summary Main constructor.}<br>
    *@version 2.5
    */
    public FButtonEndTurn() {
      super("", Panneau.getView().getPj(), Main.getTailleElementGraphique(200));
      setSize(Main.getTailleElementGraphique(BUTTON_RADIUS*2));
      setLocation(0,0);
      lineSize = Main.getTailleElementGraphique(3);
      setBorderPainted(false);
      boolean b = false;
      try {
        b=Main.getPlayingJoueur().getFere().getGc().haveDoneAllActionAviable();
      }catch (NullPointerException e) {}
      setAllActionDone(b);
    }

    // GET SET -------------------------------------------------------------------
    /**
    *{@summary change color of the button depending of player's actions left.}<br>
    *Green if player still have action to do otherwise red.
    *@version 2.5
    */
    public void setAllActionDone(boolean b){
      if(b){
        color = Color.RED;
      }else{
        color = Color.GREEN;
      }
    }
    /**
    *{@summary setEnabled that also hide button if disable.}<br>
    *@version 2.5
    */
    @Override
    public void setEnabled(boolean b){
      super.setVisible(b);
      super.setEnabled(b);
    }

    // FUNCTIONS -----------------------------------------------------------------
    /**
    *{@summary Draw function for the FButton.}<br>
    *@version 2.5
    */
    public void paintComponent(Graphics gTemp){
      Graphics2D g = (Graphics2D)gTemp;
      BasicStroke line = new BasicStroke(lineSize);
      g.setStroke(line);
      g.setColor(color);
      g.fillOval(lineSize/2,lineSize/2,getWidth()-lineSize,getHeight()-lineSize);
      g.setColor(Color.BLACK);
      g.drawOval(lineSize/2,lineSize/2,getWidth()-lineSize,getHeight()-lineSize);
    }
  }
}
